<?php
include_once "./DataBase.class.php";
$db = new DataBase("127.0.0.1","notalk","root","Fenghuayu05.28");

$sid  = $_POST["sid"];
$nickname = $_POST["nickname"];
$sex = $_POST["sex"];
$signature = $_POST["signature"];

$res = $db->select("user","*",array("sid"=>$sid));
$num = count($res);

if($num!=0){
    echo -1;
}else{
    $res = $db->insert("user",array("sid"=>$sid,"nickname"=>$nickname,"sex"=>$sex,"signature"=>$signature));
    echo $res;
}
