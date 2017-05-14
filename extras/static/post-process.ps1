$ScriptPath = Split-Path $MyInvocation.MyCommand.Path | Resolve-Path

$data = (Get-Content machines-def.json) -join "`n" | ConvertFrom-Json
foreach($set in $data.sets)
{
    if($set.postprocess -and $set.postprocess.transparent)
    {
        $file = New-Object System.IO.FileInfo "$ScriptPath\output\$($set.name).png"
        & "$ScriptPath\color_convert.ps1" $file $set.postprocess.transparent
    }
}