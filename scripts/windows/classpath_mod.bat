@echo off

:loop
	if (%1) == () goto end
	
	set NEW_ARGS=%NEW_ARGS% %1

	if "%1" == "-cp" (
		SHIFT
		goto found_classpath
	) else if "%1" == "-classpath" (
		SHIFT
		goto found_classpath
	) else (
		SHIFT
	)
goto loop

:found_classpath
	set NEW_ARGS=%NEW_ARGS% %CLASSPATH%:%1
	SHIFT
goto loop

:end
	echo %NEW_ARGS%
	set NEW_ARGS=

