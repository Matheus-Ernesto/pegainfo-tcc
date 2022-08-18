<?php
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
                
    $resultado = $conexao->Testar();
    echo $resultado;
?>