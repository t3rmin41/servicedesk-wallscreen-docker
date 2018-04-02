$environment = $args[0]
if ($($args.Count) -ge 1) {
	$databaseAbsoluteDir = "C:\eclipse-oxygen-x64\workspace\servicedesk-wallscreen\db\$environment"
    $databaseRelativeDir = ".\db\$environment"
	$databaseFiles = get-childitem $databaseAbsoluteDir  -file -Recurse
    $backupAbsoluteDir = "C:\eclipse-oxygen-x64\workspace\servicedesk-wallscreen\db-backup\$environment"
	$backupRelativeDir = ".\db-backup\$environment"
    foreach ($file in $databaseFiles) {
		$originalFilename = $file.BaseName+$file.Extension
		$archivedFilenameBase = (get-date -Format "yyyy-MM-dd_HH-mm-ss")
		$newFileName = $archivedFilenameBase+"-"+$originalFilename
		Copy-Item $file.FullName -Destination "$backupDir\$newFileName"
	}

	#Do the cleanup
	$backupFiles = Get-ChildItem $backupAbsoluteDir | Sort-Object LastWriteTime -Descending | Select -Skip 10
	foreach ($backupfile in $backupFiles) {
		$backupFilename = $backupfile.FullName
		echo "$backupFilename will be deleted" | Out-File ".\debug.log" 
		Remove-Item $backupfile.FullName -Force
	}
}
if ($($args.Count) -lt 1) {
	echo "No environment passed"
}
