<?php
    $email_usuario = $_POST['email'];
    $modelo = $_POST['modelo'];

    $comando = "SELECT * FROM mydb.u_favoritos 
    WHERE BINARY usuario = BINARY '".$email_usuario."' AND
        BINARY modelo = BINARY '".$modelo."';
    ";
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    if(isset($resultado[0]['usuario'])){
        echo json_encode(array('status' => 'Ja Favoritado'));
    }else {
        echo json_encode(array('status' => 'Não Favoritado'));
    }
?>