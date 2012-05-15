@echo off

if "%JEN_HOME%" == "" (
	set JEN_HOME=%HOMEDRIVE%%HOMEPATH%\.jen
)

call %JEN_HOME%\config.bat

if not "%JEN_ENV%" == "" (
	echo Changing java classpath to include %JEN_ENV% classes

	set OLD_CP=%CLASSPATH%

	if not "%CLASSPATH%" == "" (
		set CLASSPATH=%CLASSPATH%;%JEN_HOME%\%JEN_ENV%\*

	) else (
		set CLASSPATH=%JEN_HOME%\%JEN_ENV%\*

	)
)

echo New path = %CLASSPATH%
