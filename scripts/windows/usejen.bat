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

set command=classpath_mod %*

for /f "delims=" %i in ('%command%') do (
	set NEW_ARGS=%i 
)

echo %NEW_ARGS%

doskey java=java %NEW_ARGS%
doskey javac=javac %NEW_ARGS%

set NEW_ARGS=

