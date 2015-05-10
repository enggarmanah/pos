@echo off
setlocal enabledelayedexpansion
set X=5
set FOLDER_PATH=.
pushd %FOLDER_PATH%
for %%f in (*_24dp.png) do if %%f neq %~nx0 (
    set "filename=%%~nf"
    set "filename=!filename:~0,-%X%!"
    ren "%%f" "!filename!%%~xf"
)
popd