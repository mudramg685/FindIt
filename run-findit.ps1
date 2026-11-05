$ErrorActionPreference = 'Stop'

$previousJavaHome = $env:JAVA_HOME
$previousJavaOptions = $env:JAVA_TOOL_OPTIONS
$previousDbUser = $env:DB_USER
$previousDbPassword = $env:DB_PASSWORD
$securePassword = $null
$passwordPointer = [IntPtr]::Zero

try {
    if (-not $env:JAVA_HOME -or -not (Test-Path (Join-Path $env:JAVA_HOME 'bin\java.exe'))) {
        foreach ($javaPath in (& where.exe java.exe 2>$null)) {
            if ($javaPath -match '(?i)^(.*)\\bin\\java\.exe$') {
                $candidateHome = $Matches[1]
                if (Test-Path (Join-Path $candidateHome 'bin\javac.exe')) {
                    $env:JAVA_HOME = $candidateHome
                    break
                }
            }
        }
    }
    if (-not $env:JAVA_HOME -or -not (Test-Path (Join-Path $env:JAVA_HOME 'bin\java.exe'))) {
        throw 'JDK 17 or newer is required. Install a JDK and open a new terminal.'
    }

    $projectMavenHome = Join-Path $PSScriptRoot '.maven-home'
    $env:JAVA_TOOL_OPTIONS = '-Duser.home="' + $projectMavenHome + '" -Djavax.net.ssl.trustStoreType=Windows-ROOT'
    $env:DB_USER = 'root'
    $securePassword = Read-Host 'Enter your MySQL root password' -AsSecureString
    $passwordPointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
    $env:DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($passwordPointer)

    Push-Location $PSScriptRoot
    try {
        & (Join-Path $PSScriptRoot 'mvnw.cmd') spring-boot:run
        exit $LASTEXITCODE
    }
    finally {
        Pop-Location
    }
}
finally {
    if ($passwordPointer -ne [IntPtr]::Zero) {
        [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($passwordPointer)
    }
    $env:JAVA_HOME = $previousJavaHome
    $env:JAVA_TOOL_OPTIONS = $previousJavaOptions
    $env:DB_USER = $previousDbUser
    $env:DB_PASSWORD = $previousDbPassword
}
