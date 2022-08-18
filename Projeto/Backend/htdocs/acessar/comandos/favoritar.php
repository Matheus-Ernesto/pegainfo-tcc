<?php
    $email_usuario = $_POST['email'];
    $modelo = $_POST['modelo'];

    $comando = "SELECT * FROM mydb.u_favoritos 
    WHERE BINARY usuario LIKE BINARY '%".$email_usuario."%' AND
        BINARY modelo LIKE BINARY '%".$modelo."%';
    ";
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    if(isset($resultado[0]['usuario'])){
        echo json_encode(array('status' => 'Ja Favoritado'));
    }else {
        $comando = sprintf("INSERT IGNORE INTO u_favoritos (usuario, modelo) VALUES
        ('%s','%s')", $email_usuario, $modelo);
        $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
        $resultado = $conexao->Comando($comando);
        if($resultado){
            echo json_encode(array('status' => 'Favoritado'));
        }else {
            echo json_encode($resultado);
        }
    }
?>