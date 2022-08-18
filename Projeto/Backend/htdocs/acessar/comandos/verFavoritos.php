<?php
    $email = $_POST['email'];
    $comando = sprintf("SELECT modelo FROM mydb.u_favoritos WHERE
    BINARY usuario = BINARY '%s';", $email);
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    echo json_encode($resultado);
?>