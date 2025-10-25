param(
  [string]$AdminUser = "root",
  [string]$DbName = "inventory_db",
  [string]$AppUser = "asus",
  [string]$AppPassword = "3005",
  [string]$DbHost = "localhost"
)

Write-Host "=== Inventory DB Setup ===" -ForegroundColor Cyan
Write-Host "Admin User: $AdminUser" -ForegroundColor Gray
Write-Host "Target DB:  $DbName" -ForegroundColor Gray
Write-Host "App User:   $AppUser" -ForegroundColor Gray

$sec = Read-Host -Prompt "Enter MySQL admin password (for $AdminUser)" -AsSecureString
$ptr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($sec)
$plain = [Runtime.InteropServices.Marshal]::PtrToStringAuto($ptr)
[Runtime.InteropServices.Marshal]::ZeroFreeBSTR($ptr)

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$repoRoot = Resolve-Path (Join-Path $root "..")
$schema = Join-Path $repoRoot "setup_database.sql"
if (-not (Test-Path $schema)) {
  Write-Host "ERROR: setup_database.sql not found at $schema" -ForegroundColor Red
  exit 1
}

$tmpSql = Join-Path $env:TEMP "ims_setup_$(Get-Date -Format yyyyMMddHHmmss).sql"
@(
  "CREATE DATABASE IF NOT EXISTS `$DbName CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;".Replace("`$DbName", $DbName),
  "CREATE USER IF NOT EXISTS '$AppUser'@'localhost' IDENTIFIED BY '$AppPassword';",
  "GRANT ALL PRIVILEGES ON `$DbName.* TO '$AppUser'@'localhost';".Replace("`$DbName", $DbName),
  "FLUSH PRIVILEGES;",
  "USE `$DbName;".Replace("`$DbName", $DbName),
  "-- Load schema and sample data"
) | Set-Content -Encoding UTF8 $tmpSql

# Append the schema file contents
Get-Content -Raw $schema | Add-Content -Encoding UTF8 $tmpSql

Write-Host "Running MySQL setup..." -ForegroundColor Cyan

$mysqlArgs = @("-u", $AdminUser, "-h", $DbHost, "-p$plain")
$psi = New-Object System.Diagnostics.ProcessStartInfo
$psi.FileName = "mysql"
$psi.Arguments = ($mysqlArgs -join ' ')
$psi.RedirectStandardInput = $true
$psi.RedirectStandardError = $true
$psi.RedirectStandardOutput = $true
$psi.UseShellExecute = $false
$p = New-Object System.Diagnostics.Process
$p.StartInfo = $psi
$null = $p.Start()
(Get-Content -Raw $tmpSql) | ForEach-Object { $p.StandardInput.WriteLine($_) }
$p.StandardInput.Close()
$p.WaitForExit()

$out = $p.StandardOutput.ReadToEnd()
$err = $p.StandardError.ReadToEnd()
if ($out) { Write-Host $out }
if ($err) { Write-Host $err -ForegroundColor Yellow }

Remove-Item -Force $tmpSql

if ($p.ExitCode -eq 0) {
  Write-Host "Database setup completed successfully." -ForegroundColor Green
  exit 0
} else {
  Write-Host "Database setup reported exit code $($p.ExitCode)." -ForegroundColor Yellow
  exit $p.ExitCode
}
