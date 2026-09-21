"""Independent repository harness for the academic CRUD baseline.

The harness is intentionally framework-agnostic: it validates the native
TypeScript/HTML/CSS frontend contract and the Java/MySQL backend without
requiring an AI provider.
"""

from __future__ import annotations

import argparse
import json
import re
import subprocess
import sys
from dataclasses import asdict, dataclass
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
FRONTEND = ROOT / "Codigo" / "academic-crud-frontend"
BACKEND = ROOT / "Codigo" / "academic-crud-backend"
DOC_FILES = [path for path in ROOT.rglob("*") if path.suffix.lower() in {".md", ".txt"} and "Avaliacao" not in path.parts and ".github" not in path.parts]
IGNORED_PARTS = {".git", "target", "node_modules"}
FORBIDDEN_FRONTEND_TERMS = ("Angular 19", "Angular", "angular.json", "ng test", "ng build")
SECRET_PATTERNS = (
    re.compile(r"(?i)(password|passwd|secret|api[_-]?key|token)\s*[:=]\s*['\"][^'\"]+['\"]"),
    re.compile(r"(?i)-----BEGIN (?:RSA|EC|OPENSSH|PRIVATE) KEY-----"),
)


@dataclass
class Check:
    check_id: str
    status: str
    detail: str
    command: str | None = None


def add(checks: list[Check], check_id: str, status: str, detail: str, command: str | None = None) -> None:
    checks.append(Check(check_id, status, detail, command))


def run_command(command: list[str], cwd: Path) -> tuple[int, str]:
    if sys.platform == "win32" and command[0] in {"npm", "mvn"}:
        command[0] += ".cmd"
    try:
        result = subprocess.run(command, cwd=cwd, capture_output=True, text=True, timeout=180)
    except (FileNotFoundError, subprocess.TimeoutExpired) as error:
        return 127, str(error)
    output = (result.stdout + "\n" + result.stderr).strip()
    return result.returncode, output[-4000:]


def check_frontend(checks: list[Check], strict: bool) -> None:
    files = [path for path in FRONTEND.rglob("*") if path.is_file() and not (IGNORED_PARTS & set(path.parts))]
    extensions = {path.suffix.lower() for path in files}
    required = {".ts", ".html", ".css"}
    missing = sorted(required - extensions)
    if missing:
        status = "fail" if strict else "warn"
        add(checks, "02-FRONTEND-STACK", status, f"Arquivos ausentes: {', '.join(missing)}")
    else:
        add(checks, "02-FRONTEND-STACK", "pass", "TypeScript, HTML e CSS presentes")

    forbidden = [path for path in files if path.suffix.lower() in {".ts", ".html", ".css", ".json"} and "angular" in path.name.lower()]
    if forbidden:
        add(checks, "02-FRONTEND-FRAMEWORK", "fail", f"Artefatos Angular encontrados: {', '.join(str(p.relative_to(ROOT)) for p in forbidden)}")
    else:
        add(checks, "02-FRONTEND-FRAMEWORK", "pass", "Nenhum artefato Angular encontrado")

    package_json = FRONTEND / "package.json"
    if package_json.exists():
        try:
            package = json.loads(package_json.read_text(encoding="utf-8"))
            scripts = package.get("scripts", {})
        except (OSError, json.JSONDecodeError) as error:
            add(checks, "02-FRONTEND-TEST", "fail", f"package.json inválido: {error}")
            return
        if "test" not in scripts:
            add(checks, "02-FRONTEND-TEST", "fail", "package.json não possui o script test")
            return
        code, output = run_command(["npm", "run", "test"], FRONTEND)
        add(checks, "02-FRONTEND-TEST", "pass" if code == 0 else "fail", output or "npm test concluído", "npm run test")
    else:
        add(checks, "02-FRONTEND-TEST", "warn", "package.json ainda não existe; testes do front não podem ser executados")


def check_documentation(checks: list[Check]) -> None:
    stale: list[str] = []
    for path in DOC_FILES:
        if IGNORED_PARTS & set(path.parts):
            continue
        text = path.read_text(encoding="utf-8", errors="ignore")
        if any(term in text for term in FORBIDDEN_FRONTEND_TERMS):
            stale.append(str(path.relative_to(ROOT)))
    if stale:
        add(checks, "00-DOCS-STACK", "fail", "Referências Angular obsoletas: " + ", ".join(stale))
    else:
        add(checks, "00-DOCS-STACK", "pass", "Documentação alinhada ao front nativo")


def check_secrets(checks: list[Check]) -> None:
    findings: list[str] = []
    for path in ROOT.rglob("*"):
        if not path.is_file() or IGNORED_PARTS & set(path.parts) or path.stat().st_size > 1_000_000:
            continue
        try:
            text = path.read_text(encoding="utf-8", errors="ignore")
        except OSError:
            continue
        if any(pattern.search(text) for pattern in SECRET_PATTERNS):
            findings.append(str(path.relative_to(ROOT)))
    add(checks, "00-SECURITY-SECRETS", "fail" if findings else "pass", "Possíveis segredos: " + ", ".join(findings) if findings else "Nenhum padrão de segredo encontrado")


def check_backend(checks: list[Check], skip: bool) -> None:
    if skip:
        add(checks, "01-BACKEND-TEST", "skip", "Execução explicitamente desabilitada")
        return
    code, output = run_command(["mvn", "-q", "test"], BACKEND)
    if code == 127:
        add(checks, "01-BACKEND-TEST", "warn", "Maven indisponível; teste não executado", "mvn -q test")
    else:
        add(checks, "01-BACKEND-TEST", "pass" if code == 0 else "fail", output or "mvn test concluído", "mvn -q test")


def main() -> int:
    parser = argparse.ArgumentParser(description="Run independent CRUD baseline checks")
    parser.add_argument("--strict-frontend", action="store_true", help="Fail until the frontend implementation exists")
    parser.add_argument("--skip-backend", action="store_true", help="Skip Maven tests")
    parser.add_argument("--report", type=Path, help="Write the JSON report to this path")
    args = parser.parse_args()

    checks: list[Check] = []
    check_documentation(checks)
    check_secrets(checks)
    check_frontend(checks, args.strict_frontend)
    check_backend(checks, args.skip_backend)

    counts = {status: sum(check.status == status for check in checks) for status in ("pass", "warn", "fail", "skip")}
    report = {
        "harness": "academic-crud-baseline",
        "generated_at": datetime.now(timezone.utc).isoformat(),
        "root": str(ROOT),
        "decision": "blocked" if counts["fail"] else ("approved_with_remarks" if counts["warn"] else "approved"),
        "counts": counts,
        "checks": [asdict(check) for check in checks],
    }
    print(json.dumps(report, ensure_ascii=False, indent=2))
    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(json.dumps(report, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    return 1 if counts["fail"] else 0


if __name__ == "__main__":
    sys.exit(main())
