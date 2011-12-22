<?php
	require('dbaccess.php');
	require('data.php');
	$json = $_SERVER['HTTP_JSON'];
	$data = json_decode($json);
	$log = fopen("log.txt", "w") or die("can't open file");
	fwrite($log, $json."\n");
	
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
	fwrite($log, "Command: ".$command."\n");
	if ($command == "New") {
		$fname = $data->Firstname;
		$lname = $data->Lastname;
		$major = $data->Major;
		$comment = $data->Comment;
		mysql_select_db("ajatest", $con);
		$q = 'INSERT INTO people VALUES ("'.$fname.'", "'.$lname.'", "'.$major.'", "'.$comment.'")'; 
		mysql_query($q);
		$sql = mysql_query('SELECT * FROM people');
	} elseif ($command == "Read") {
		$dept = $data->Dept;
		fwrite($log, "Dept: ".$dept."\n");
		mysql_select_db("ajatest", $con);
		for ($i = 0; $i < count($departments); $i++) {
			if ($departments[$i] == $dept) {
				break;
			}
		}
		$sql = mysql_query('SELECT * FROM '.$tables[$i].' LIMIT 0, 4');
	}
	
	fclose($log);
	while ($row = mysql_fetch_assoc($sql)) $output[] = $row;
	print(json_encode($output));
	mysql_close($con);
?>
