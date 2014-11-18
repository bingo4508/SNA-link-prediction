@echo off
cd C:\Users\KuoFeng\Desktop\libsvm-3.19\libsvm-3.19\windows

SETLOCAL ENABLEDELAYEDEXPANSION 
FOR /L %%a in (2101,1,2980 ) do (
echo ²Ä%%a¦¸
set /A b=%%a %% 16


IF "!b!" == "0" (
start /B /wait svm-predict.exe -b 1 I:\node2\%%a train_8feature_new.scale.sub.model D:\OneDrive\GitHub\SNA-link-prediction\alexDataPrepare\SNA_HW2_2\predict\%%a.predict	
	)
IF "!b!" NEQ "0" (
start /B svm-predict.exe -b 1 I:\node2\%%a train_8feature_new.scale.sub.model D:\OneDrive\GitHub\SNA-link-prediction\alexDataPrepare\SNA_HW2_2\predict\%%a.predict

)
)





ENDLOCAL

PAUSE