
@echo off
setLocal EnableDelayedExpansion

if "%JEN_HOME%" == "" (
	set JEN_HOME=%HOMEDRIVE%%HOMEPATH%\.jen
)

if not exist %JEN_HOME% (
	java -jar target\jenever-0.0.1-jar-with-dependencies.jar --init
	
) 

echo %JEN_HOME%\config.bat

java -jar target\jenever-0.0.1-jar-with-dependencies.jar %*


