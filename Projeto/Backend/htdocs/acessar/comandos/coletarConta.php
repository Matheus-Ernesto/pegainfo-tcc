<?php
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $comando = sprintf("
    SELECT nome,email FROM mydb.u_usuarios WHERE 
    BINARY email = BINARY '%s' AND BINARY senha = BINARY '%s'", $email, $senha);
    $resultado = $conexao->Comando($comando);
    if (isset($resultado[0]['nome'])) 
    {
        echo json_encode(array(
            "nome" => $resultado[0]['nome'],
            "email" => $resultado[0]['email']
        ));

    } 
?>