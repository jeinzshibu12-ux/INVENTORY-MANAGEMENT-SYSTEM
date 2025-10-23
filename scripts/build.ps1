param(
    [string]$ProjectRoot = (Get-Location).Path
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
    # Fallback to common install path for Microsoft OpenJDK 21
    $msJdk = 'C:\Program Files\Microsoft\jdk-21.0.8.9-hotspot'
    if (Test-Path $msJdk) { return $msJdk }
    # Use JAVA_HOME only if it looks like a directory containing bin\javac.exe
    if ($env:JAVA_HOME) {
        $maybe = $env:JAVA_HOME
        if (Test-Path (Join-Path $maybe 'bin/javac.exe')) { return $maybe }
    }
    throw 'Could not determine JDK home. Set JAVA_HOME to a JDK folder or configure .vscode/settings.json.'
}

$jdkHome = Get-JdkHome
$javac = Join-Path $jdkHome 'bin/javac.exe'
if (-not (Test-Path $javac)) { throw "javac not found at $javac" }

$srcDir = Join-Path $ProjectRoot 'src'
$binDir = Join-Path $ProjectRoot 'bin'
New-Item -ItemType Directory -Force -Path $binDir | Out-Null

# Classpath uses ';' on Windows. Include bin for incremental compiles and MySQL driver.
$cp = @(
    $binDir,
    (Join-Path $ProjectRoot 'mysql-connector-j-9.5.0.jar')
) -join ';'

# Collect all .java source files
$javaFiles = Get-ChildItem -LiteralPath $srcDir -Recurse -Filter *.java | ForEach-Object { $_.FullName }
if (-not $javaFiles -or $javaFiles.Count -eq 0) {
    Write-Host 'No Java source files found under src/.' -ForegroundColor Yellow
    exit 0
}

Write-Host "Compiling $($javaFiles.Count) files with JDK at: $jdkHome" -ForegroundColor Cyan

& $javac -d $binDir -cp $cp -encoding UTF-8 @javaFiles

Write-Host 'Build succeeded. Classes in bin/.' -ForegroundColor Green
