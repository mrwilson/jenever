@echo off

echo Building package from sources
start /wait mvn -DskipTests clean package
echo Done

if "%JEN_HOME%" == "" (
        set JEN_HOME=%HOMEDRIVE%%HOMEPATH%\.jen
)
set JEN_CONFIG=%JEN_HOME%\config

echo Making JEN_HOME directory at %JEN_HOME%
md %JEN_HOME%
md %JEN_HOME%\scripts
echo.>%JEN_HOME%\config.bat
echo Done

echo Copying files to %JEN_HOME%
xcopy /E /Y scripts\windows %JEN_HOME%\scripts\
xcopy /Y target\jenever.jar %JEN_HOME%

echo Install complete.
