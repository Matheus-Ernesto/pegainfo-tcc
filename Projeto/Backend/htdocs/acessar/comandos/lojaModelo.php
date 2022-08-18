<?php
    $modelo = $_POST['modelo'];
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $comando = "SELECT loja1, link1, preco1, loja2, link2, preco2 
    FROM mydb.l_lojas 
    WHERE modelo = '".$modelo."'";
    $resultado = $conexao->Comando($comando);
    echo json_encode($resultado);
?>