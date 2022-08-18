<?php
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $conexao = new mysql_con("localhost","mydb","cliente","cliente");
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
            echo json_encode(array("nome" => "--","status" => "Tem conta, senha incorreta"));
        }
        else
        {
            $nome = explode('@',$email);
            $comando = sprintf("
            INSERT IGNORE INTO mydb.u_usuarios (nome, email, senha) VALUES
            ('%s','%s','%s')",$nome[0], $email, $senha);
            
            $resultado = $conexao->Comando($comando);
            if ($resultado == true)
            {
                echo json_encode(array("nome" => "--","status" => "Cadastrado"));
            }
            else 
            {
                echo json_encode(array("nome" => "--","status" => "Erro Desconhecido"));
            }
        }
    }
?>