
@echo off
setLocal EnableDelayedExpansion

if "%JEN_HOME%" == "" (
	set JEN_HOME=%HOMEDRIVE%%HOMEPATH%\.jen
)

if not exist %JEN_HOME% (
	java -jar %JEN_HOME%\jenever.jar --init
	
) 

call %JEN_HOME%\config.bat

java -jar %JEN_HOME%\jenever.jar %*


