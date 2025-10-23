param(
    [string]$ProjectRoot = (Get-Location).Path
)

$ErrorActionPreference = 'Stop'

function Find-Jdk21Path {
    $candidateBases = @(
        'C:\\Program Files\\Microsoft',
        'C:\\Program Files\\Eclipse Adoptium',
        'C:\\Program Files\\Java'
    )

    foreach ($base in $candidateBases) {
        if (-not (Test-Path $base)) { continue }
        # Try direct children named like jdk-21*
        $dirs = Get-ChildItem -LiteralPath $base -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -like 'jdk-21*' }
        foreach ($d in $dirs) {
            $javaExe = Join-Path $d.FullName 'bin/java.exe'
            if (Test-Path $javaExe) { return $d.FullName }
        }

        # Some vendors nest under vendor-specific subfolders
        $nestedVendors = @('jdk-21*','*OpenJDK*','*Temurin*','*Adoptium*')
        foreach ($nv in $nestedVendors) {
            $nested = Get-ChildItem -LiteralPath $base -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -like $nv }
            foreach ($nd in $nested) {
                $dirs2 = Get-ChildItem -LiteralPath $nd.FullName -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -like 'jdk-21*' }
                foreach ($d in $dirs2) {
                    $javaExe = Join-Path $d.FullName 'bin/java.exe'
                    if (Test-Path $javaExe) { return $d.FullName }
                }
            }
        }
    }
    return $null
}

$jdkPath = Find-Jdk21Path
if (-not $jdkPath) {
    Write-Host "JDK 21 not found yet. Please install it, then rerun this script." -ForegroundColor Yellow
    exit 1
}

$settingsDir = Join-Path $ProjectRoot '.vscode'
$null = New-Item -ItemType Directory -Path $settingsDir -Force
$settingsFile = Join-Path $settingsDir 'settings.json'

$settings = @{
    'java.configuration.runtimes' = @(
        @{ name = 'JavaSE-21'; path = $jdkPath; default = $true }
    )
}

# Preserve any existing settings by merging shallowly
if (Test-Path $settingsFile) {
    try {
        $existing = Get-Content -Raw -LiteralPath $settingsFile | ConvertFrom-Json -Depth 5
    } catch {
        $existing = $null
    }
    if ($existing) {
        $existing.'java.configuration.runtimes' = $settings.'java.configuration.runtimes'
        $settings = $existing
    }
}

$settings | ConvertTo-Json -Depth 10 | Set-Content -LiteralPath $settingsFile -Encoding UTF8

Write-Host "Configured VS Code to use JDK 21 at: $jdkPath" -ForegroundColor Green
