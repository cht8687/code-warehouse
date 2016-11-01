rem copy across the snippets

SET "SetupResources=c:\MakerDen\Resources\"
SET "Solution=c:\MakerDen\Source\"

rd /s /q "%userprofile%\documents\Visual Studio 2015\Code Snippets\Visual C#\MakerDen"
md "%userprofile%\documents\Visual Studio 2015\Code Snippets\Visual C#\MakerDen"

xcopy "%SetupResources%VisualStudio\Snippets\*.snippet"  "%userprofile%\documents\Visual Studio 2015\Code Snippets\Visual C#\MakerDen"