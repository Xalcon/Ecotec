Param(
    [Parameter(Mandatory=$True)]
    [System.IO.FileInfo]$imgPath,
    [Parameter(Mandatory=$True)]
    [int]$srcColor,
    [Parameter(Mandatory=$True)]
    [int]$dstColor
)
if(!$imgPath.Exists)
{
    Write-Error "file '$imgPath' not found"
    exit
}
$w = new-object System.Diagnostics.Stopwatch
$w.Start();
# Open the image
$bitmap = [System.Drawing.Bitmap]::FromFile($imgPath);

# copy image into rgba byte array
$imgRect = New-Object System.Drawing.Rectangle 0, 0, $bitmap.Width, $bitmap.Height
$bitmapData = $bitmap.LockBits($imgRect, [System.Drawing.Imaging.ImageLockMode]::ReadWrite, $bitmap.PixelFormat)
$pixelCount = $bitmap.Width * $bitmap.Height
$pixels = New-Object int[] $pixelCount
[System.Runtime.InteropServices.Marshal]::Copy($bitmapData.Scan0, $pixels, 0, $pixelCount)

# modify the image data
$m = 0
for($i = 0; $i -lt $pixelCount; $i++)
{
    if($pixels[$i] -eq $srcColor)
    {
        $pixels[$i] = $dstColor
        $m++
    }
}

# copy manipulated buffer back to the image
[System.Runtime.InteropServices.Marshal]::Copy($pixels, 0, $bitmapData.Scan0, $pixelCount)

# save everything
$bitmap.UnlockBits($bitmapData);
$bitmap.Save("$($imgPath.BaseName).new.png", [System.Drawing.Imaging.ImageFormat]::Png)
$bitmap.Dispose()
$w.Stop()
Write-Host "Replaced $m pixels in $($w.ElapsedMilliseconds) ms"