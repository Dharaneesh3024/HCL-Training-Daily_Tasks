@echo off
if not exist out mkdir out
javac -d out src\model\*.java src\service\*.java src\app\*.java
java -cp out app.Main
pause
