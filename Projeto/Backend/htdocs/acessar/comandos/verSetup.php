<?php
    $email = $_POST['email'];
    $comando = sprintf("SELECT nome AS modelo FROM mydb.u_setups WHERE
    BINARY email = BINARY '%s';", $email);
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    echo json_encode($resultado);
?>