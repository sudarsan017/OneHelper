@echo off
echo Building OneHelper...

cd app
call mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo Build failed. Exiting...
    exit /b 1
)

cd ..

echo Copying JAR...
set foundJar=false
for %%f in (app\target\*-jar-with-dependencies.jar) do (
    copy "%%f" "onehelper.jar" >nul
    set foundJar=true
)

if "%foundJar%"=="false" (
    echo Fat JAR not found. Check build configuration.
    exit /b 1
)

echo Creating run.bat...

(
echo @echo off
echo if "%%~3"=="" (
echo   echo Usage: run.bat projectName setupName projectPath
echo   exit /b 1
echo )
echo java -jar onehelper.jar %%1 %%2 %%3
) > run.bat

echo Setup complete!
echo Use: run.bat projectName setupName projectPath