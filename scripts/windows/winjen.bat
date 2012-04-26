@echo off
:usejen
	call C:\Users\MrWilson\.jen\config.bat
	if "%JEN_ENV%" == "" (
		set OLD_CP=%CLASSPATH%
		set CLASSPATH=%CLASSPATH%;%JEN_HOME%\%JEN_ENV%\*
	)
	


GOTO:EOF
