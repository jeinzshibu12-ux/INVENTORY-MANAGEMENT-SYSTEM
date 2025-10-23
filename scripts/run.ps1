param(
    [string]$ProjectRoot = (Get-Location).Path,
    [string]$MainClass = 'ui.Main'
)

$ErrorActionPreference = 'Stop'

function Get-JdkHome {
    $settingsPath = Join-Path $ProjectRoot '.vscode/settings.json'
    if (Test-Path $settingsPath) {
        try {
            $json = Get-Content -Raw -LiteralPath $settingsPath | ConvertFrom-Json -Depth 6
            $runtimes = $json.'java.configuration.runtimes'
            if ($runtimes) {
                $default = $runtimes | Where-Object { $_.default -eq $true }
                if ($default) { return $default.path }
                $v21 = $runtimes | Where-Object { $_.name -eq 'JavaSE-21' }
                if ($v21) { return $v21.path }
            }
        } catch {}
    }
    $msJdk = 'C:\Program Files\Microsoft\jdk-21.0.8.9-hotspot'
    if (Test-Path $msJdk) { return $msJdk }
    if ($env:JAVA_HOME) {
        $maybe = $env:JAVA_HOME
        if (Test-Path (Join-Path $maybe 'bin/java.exe')) { return $maybe }
    }
    throw 'Could not determine JDK home. Set JAVA_HOME to a JDK folder or configure .vscode/settings.json.'
}

$jdkHome = Get-JdkHome
$java = Join-Path $jdkHome 'bin/java.exe'
if (-not (Test-Path $java)) { throw "java not found at $java" }

$binDir = Join-Path $ProjectRoot 'bin'
$mysqlJar = Join-Path $ProjectRoot 'mysql-connector-j-9.5.0.jar'

# Build a Windows classpath using ';'
$cp = @($binDir)
if (Test-Path $mysqlJar) { $cp += $mysqlJar }
$classpath = ($cp -join ';')

Write-Host "Running $MainClass using JDK at: $jdkHome" -ForegroundColor Cyan

& $java -cp $classpath $MainClass
