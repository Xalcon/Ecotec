Param(
    [Parameter(Mandatory=$True)]
    [System.IO.FileInfo]$imgPath,
    [Parameter(Mandatory=$True)]
    [int]$srcColor
)
Add-Type -AssemblyName System.Drawing
if(!$imgPath.Exists)
{
    Write-Error "file '$imgPath' not found"
    exit
}
$w = new-object System.Diagnostics.Stopwatch
$w.Start();
# Open the image
$bitmap = [System.Drawing.Bitmap]::FromFile($imgPath);
$bitmap.MakeTransparent([System.Drawing.Color]::FromArgb($srcColor))
$bitmap.Save("$($imgPath.FullName)", [System.Drawing.Imaging.ImageFormat]::Png)
$bitmap.Dispose()
$w.Stop()
Write-Host "Applied transparency in $($w.ElapsedMilliseconds) ms"