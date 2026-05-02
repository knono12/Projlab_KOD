@echo off
setlocal enabledelayedexpansion
set PASS=0
set FAIL=0
set SKIP=0
set FAILED_LIST=
set BIN=skeleton\bin21

rem --- Fordítsa le a teljes skeletont + testhandlert, ha szukseges ---
if not exist %BIN%\testhandler\TestMain.class (
    echo Fordítás folyamatban...
    if not exist %BIN% mkdir %BIN%
    javac -encoding UTF-8 -d %BIN% ^
        skeleton\src\finance\*.java ^
        skeleton\src\accessories\attachments\*.java ^
        skeleton\src\accessories\fuels\*.java ^
        skeleton\src\environment\road\*.java ^
        skeleton\src\environment\lane\lanestates\*.java ^
        skeleton\src\environment\lane\*.java ^
        skeleton\src\environment\nodes\structures\*.java ^
        skeleton\src\environment\nodes\*.java ^
        skeleton\src\environment\*.java ^
        skeleton\src\players\*.java ^
        skeleton\src\skeleton\*.java ^
        skeleton\src\vehicles\*.java ^
        skeleton\src\testhandler\*.java
    if errorlevel 1 (
        echo HIBA: Fordítás sikertelen!
        exit /b 1
    )
    echo Fordítás kész.
    echo.
)

if not exist eredmenyek mkdir eredmenyek

if "%1"=="" (
    for %%f in (tesztek\T*_bemenet_init.txt) do (
        set "fname=%%~nf"
        set "tname=!fname:_bemenet_init=!"
        call :TESZT !tname!
    )
) else (
    call :TESZT %1
)

echo.
echo ==========================================
echo  OSSZESITES
echo ==========================================
echo  Sikeres tesztek: %PASS%
echo  Hibas tesztek:   %FAIL%
if not "%SKIP%"=="0" echo  Athagyott tesztek: %SKIP%
echo ==========================================
if not "%FAIL%"=="0" (
    echo.
    echo  Megbukott tesztek:
    for %%t in (%FAILED_LIST%) do echo    - %%t
)
goto :eof

:TESZT
set "TNAME=%1"
set "INIT=tesztek\%TNAME%_bemenet_init.txt"
set "MOVE=tesztek\%TNAME%_bemenet_move.txt"
set "OUT=eredmenyek\%TNAME%_kimenet.txt"
set "ELVART=elvart\%TNAME%_elvart.txt"

if not exist "%INIT%" (
    echo %TNAME% SKIP  ^(nincs init fajl^)
    set /a SKIP+=1
    goto :eof
)
if not exist "%MOVE%" (
    echo %TNAME% SKIP  ^(nincs move fajl^)
    set /a SKIP+=1
    goto :eof
)
if not exist "%ELVART%" (
    echo %TNAME% SKIP  ^(nincs elvart fajl^)
    set /a SKIP+=1
    goto :eof
)

(type "%INIT%" & echo. & type "%MOVE%") | java -cp %BIN% testhandler.TestMain > "%OUT%" 2>&1

fc /w "%OUT%" "%ELVART%" > nul 2>&1
if %errorlevel%==0 (
    echo %TNAME% PASSED
    set /a PASS+=1
) else (
    echo %TNAME% FAILED
    echo   -- Tenyleges kimenet ^(%OUT%^):
    type "%OUT%"
    echo   -- Elvart kimenet ^(%ELVART%^):
    type "%ELVART%"
    echo.
    set /a FAIL+=1
    set "FAILED_LIST=!FAILED_LIST! !TNAME!"
)
goto :eof
