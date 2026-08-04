@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"

set "REPO_URL=https://github.com/nguyenbaolong2504/JavaTechnology.git"
set "BRANCH=main"

echo ==========================================
echo   AUTO PUSH LEN GITHUB - JavaTechnology
echo ==========================================
echo.

git --version >nul 2>&1
if errorlevel 1 (
    echo [LOI] Chua cai Git hoac Git chua co trong PATH.
    pause
    exit /b 1
)

git rev-parse --is-inside-work-tree >nul 2>&1
if errorlevel 1 (
    echo [INFO] Chua co Git repository. Dang khoi tao...
    git init
    if errorlevel 1 goto :error
)

git remote get-url origin >nul 2>&1
if errorlevel 1 (
    echo [INFO] Dang them remote origin...
    git remote add origin "%REPO_URL%"
    if errorlevel 1 goto :error
) else (
    for /f "delims=" %%R in ('git remote get-url origin') do set "CURRENT_REMOTE=%%R"
    if /I not "%CURRENT_REMOTE%"=="%REPO_URL%" (
        echo [INFO] Remote origin hien tai:
        echo        %CURRENT_REMOTE%
        echo [INFO] Dang doi sang:
        echo        %REPO_URL%
        git remote set-url origin "%REPO_URL%"
        if errorlevel 1 goto :error
    )
)

echo [INFO] Repo: %REPO_URL%
echo [INFO] Branch: %BRANCH%
echo.

set /p "MESSAGE=Nhap noi dung commit: "
if "%MESSAGE%"=="" set "MESSAGE=Update Java project"

echo.
echo [1/5] Them file...
git add .
if errorlevel 1 goto :error

git diff --cached --quiet
if errorlevel 1 (
    echo [2/5] Commit...
    git commit -m "%MESSAGE%"
    if errorlevel 1 goto :error
) else (
    echo [2/5] Khong co thay doi moi de commit.
)

echo [3/5] Chuyen/doi ten branch thanh main...
git branch -M %BRANCH%
if errorlevel 1 goto :error

echo [4/5] Dong bo voi GitHub neu repo da co du lieu...
git ls-remote --exit-code --heads origin %BRANCH% >nul 2>&1
if not errorlevel 1 (
    git pull --rebase origin %BRANCH%
    if errorlevel 1 (
        echo.
        echo [LOI] Co conflict khi pull --rebase.
        echo Hay xu ly conflict, sau do chay:
        echo   git add .
        echo   git rebase --continue
        echo Roi chay lai file nay.
        pause
        exit /b 1
    )
) else (
    echo [INFO] Remote chua co branch main, bo qua pull.
)

echo [5/5] Push len GitHub...
git push -u origin %BRANCH%
if errorlevel 1 goto :error

echo.
echo ==========================================
echo       PUSH GITHUB THANH CONG!
echo ==========================================
echo Repo: https://github.com/nguyenbaolong2504/JavaTechnology
pause
exit /b 0

:error
echo.
echo [LOI] Co loi xay ra. Kiem tra thong bao Git o phia tren.
pause
exit /b 1
