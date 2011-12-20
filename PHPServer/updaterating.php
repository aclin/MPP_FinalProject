<?php
	require('dbaccess.php');
	$json = $_SERVER['HTTP_JSON'];	
	$data = json_decode($json);
	/*$log = fopen("log.txt", "w") or die("can't open file");
	fwrite($log, $json."\n");
	fclose($log);*/
	
	$con = mysql_connect("localhost", $user, $access);
	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}
	mb_language('uni');
	mb_internal_encoding('UTF-8');
	mysql_query("SET character_set_client=utf8", $con);
	mysql_query("SET character_set_connection=utf8", $con);
	mysql_query("SET character_set_results=utf8", $con);
	mysql_query("SET NAMES 'utf8'",$con);
	
	$command = $data->Command;
	switch ($command) {
		case "Good":
			mysql_select_db("ajatest", $con);
			$name = $data->Name;
			$q = "UPDATE college_eecs_csie SET Good = Good + 1 WHERE Name='".$name."'";
			mysql_query($q);
			$q = "UPDATE college_eecs_csie SET Rate = Good / (Good + Bad) WHERE Name='".$name."'";
			mysql_query($q);
			break;
		case "Bad":
			mysql_select_db("ajatest", $con);
			$name = $data->Name;
			$q = "UPDATE college_eecs_csie SET Bad = Bad + 1 WHERE Name='".$name."'";
			mysql_query($q);
			$q = "UPDATE college_eecs_csie SET Rate = Good / (Good + Bad) WHERE Name='".$name."'";
			mysql_query($q);
			break;
	}
	//while ($row = mysql_fetch_assoc($sql)) $output[] = $row;
	//print(json_encode($output));
	mysql_close($con);
?>