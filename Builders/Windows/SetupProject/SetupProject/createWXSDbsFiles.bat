"%WIX%\bin\heat.exe" dir "..\..\..\..\..\..\..\dsb-gui\build\distributions" -ag -sfrag -dr "INSTALLFOLDER" -cg DsbFilesGroup -var var.DsbPath -out ..\..\..\DsbFiles.wxs
powershell -Command "(gc ..\..\..\DsbFiles.wxs) -replace '<Component ', '<Component Win64=\"yes\" ' | Out-File ..\..\..\DsbFiles.wxs" -encoding UTF8