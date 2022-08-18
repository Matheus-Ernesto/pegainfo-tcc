<?php
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $comando = sprintf("
    SELECT nome FROM mydb.u_usuarios WHERE 
    BINARY email = BINARY '%s' AND BINARY senha = BINARY '%s'", $email, $senha);
    $resultado = $conexao->Comando($comando);

    if (isset($resultado[0]['nome'])) 
    {
        echo json_encode(array("nome" => $resultado[0]['nome'],"status" => "Logado"));
    } 
    else 
    {
        $comando = sprintf("
        SELECT nome FROM mydb.u_usuarios WHERE 
        BINARY email = BINARY '%s'", $email);
        $resultado = $conexao->Comando($comando);
        if(isset($resultado[0]['nome']))
        {
            echo json_encode(array("nome" => "--","status" => "senha incorreta"));
        }
        else
        {
            echo json_encode(array("nome" => "--","status" => "Sem cadastro"));
        }
    }
?>
