@echo off
java -server -Xms512m -Xmx1536m -XX:NewSize=32m -XX:MaxPermSize=128m -XX:+UseConcMarkSweepGC -XX:+ExplicitGCInvokesConcurrent -XX:+AggressiveOpts -cp bin;lib/*; org.arios.Main false true false true true
pause