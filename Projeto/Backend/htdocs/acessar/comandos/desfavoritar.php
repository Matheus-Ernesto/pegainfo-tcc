<?php
    $email_usuario = $_POST['email'];
    $modelo = $_POST['modelo'];

    $comando = "DELETE FROM mydb.u_favoritos 
    WHERE BINARY usuario LIKE BINARY '%".$email_usuario."%' AND
        BINARY modelo LIKE BINARY '%".$modelo."%';
    ";
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    if($resultado){
        $comando = "SELECT MAX(idFavoritos) FROM mydb.u_favoritos";
        $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
        $resultado = $conexao->Comando($comando);
        $id = $resultado[0]['MAX(idFavoritos)'];
        $id = ($id >= 1) ? $id : 1;
        $comando = "ALTER TABLE mydb.u_favoritos AUTO_INCREMENT = ".$id."";
        $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
        $resultado = $conexao->Comando($comando);
        if($resultado){
            echo json_encode(array('status' => 'Deletado'));
        }else {
            echo json_encode($resultado);
        }
    }else {
        echo json_encode($resultado);
    }
?>