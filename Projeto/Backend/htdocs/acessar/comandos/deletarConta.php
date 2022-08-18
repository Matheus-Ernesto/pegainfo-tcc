<?php
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $comando = "
    DELETE FROM mydb.u_usuarios 
    WHERE BINARY mydb.u_usuarios.email 
    LIKE BINARY '%".$email."%' AND BINARY mydb.u_usuarios.senha 
    LIKE BINARY '%".$senha."%';
    
    
    ";
    $resultado = $conexao->Comando($comando);
    if($resultado == true){
        echo json_encode(array('Resultado' => 'Conta deletada'));
    } else {
        echo json_encode(array('Resultado' => 'Erro'));
    }
?>