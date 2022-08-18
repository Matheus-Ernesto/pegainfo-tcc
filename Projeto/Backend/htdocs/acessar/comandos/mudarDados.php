<?php
    $nome = $_POST['nome'];
    $email = $_POST['email_novo'];
    $email_antigo = $_POST['email'];
    $senha_antiga = $_POST['senha'];

    //SE EXISTIR A CONTA
    $conexao = new mysql_con("localhost","mydb","cliente","cliente");
    $comando = sprintf("
    SELECT email FROM mydb.u_usuarios WHERE 
    BINARY email = BINARY '%s' AND BINARY senha = BINARY '%s'", $email_antigo, $senha_antiga);
    $resultado = $conexao->Comando($comando);
    $retorno = array(
        'resultado_email' => '',
        'resultado_nome' => ''
    );
    if (isset($resultado[0]['email'])) {
        if (isset($email) && $email != "" && $email != $email_antigo) {
            $conexao = new mysql_con("localhost","mydb","cliente","cliente");
            $comando = sprintf("
            UPDATE mydb.u_usuarios 
            SET email = '%s'
            WHERE BINARY email = BINARY '%s' AND BINARY senha = BINARY '%s'",
            $email,$email_antigo, $senha_antiga);
            $resultado = $conexao->Comando($comando);
            if($resultado == true){
                $retorno['resultado_email'] = 'Email Mudado';
            }else {
                $retorno['resultado_email'] = 'Email Mantido';
            }
        }
        if (isset($nome) && $nome != "") {
            $conexao = new mysql_con("localhost","mydb","cliente","cliente");
            $comando = sprintf("
            UPDATE mydb.u_usuarios 
            SET nome = '%s'
            WHERE BINARY email = BINARY '%s' AND BINARY senha = BINARY '%s'",
            $nome,$email_antigo, $senha_antiga);
            $resultado = $conexao->Comando($comando);
            if($resultado == true){
                $retorno['resultado_nome'] = 'Nome Mudado';
            }else {
                $retorno['resultado_nome'] = 'Nome Mantido';
            }
        }
        json_encode($retorno);
    }else {
        json_encode(array('Erro' => 'Sem permissao'));
    }
?>