<?php
    include_once('mysql_api.php');
    
    if (!isset($_POST['comando']))
    {
        echo json_encode(array("nome" => "--", "status" => "Sem POST"));
        die();
    } 
    else 
    {
        include('comandos/'.$_POST['comando'].'.php');
    }
?>