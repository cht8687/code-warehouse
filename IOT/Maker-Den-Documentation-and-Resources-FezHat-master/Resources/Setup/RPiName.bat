@echo Off
@setlocal ENABLEDELAYEDEXPANSION


SET "SetupResources=c:\MakerDen\Resources\"
SET "Solution=c:\MakerDen\Source\"

SET "RPiName=%~1"

SET "InputFile=%SetupResources%VisualStudio\Launcher.csproj.user"

if "%RPiName%" == "" (
    echo *** Please provide the name of the Raspberry Pi remote device name to register.
    exit /B 1
)


if not exist "%InputFile%" (
    echo *** Xml file with given path does not exist.
    exit /B 2
)

if exist "%InputFile%.RPiName" del /F /Q "%InputFile%.RPiName"

for /F "delims=" %%G in (%InputFile%) do (
    set LINE=%%G
    if not "!LINE!" == "!LINE:RemoteDebugMachine=!" (
        set LINE=!LINE:minwinpc=%RPiName%!
    )
    >> "%InputFile%.RPiName" echo !LINE!
)


@endlocal