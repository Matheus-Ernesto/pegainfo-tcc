<?php
    $argumento = $_POST['argumento'];
    $comando = "
        SELECT mydb.d_celulares.modelo FROM mydb.d_celulares 
        WHERE mydb.d_celulares.modelo LIKE '%".$argumento."%' GROUP BY modelo
        UNION
        SELECT mydb.d_tablets.modelo FROM mydb.d_tablets 
        WHERE mydb.d_tablets.modelo LIKE '%".$argumento."%' GROUP BY modelo
        UNION
        SELECT mydb.p_memoria_ram.modelo FROM mydb.p_memoria_ram 
        WHERE mydb.p_memoria_ram.modelo LIKE '%".$argumento."%' GROUP BY modelo
        UNION
        SELECT mydb.p_memoria_armaz.modelo FROM mydb.p_memoria_armaz 
        WHERE mydb.p_memoria_armaz.modelo LIKE '%".$argumento."%' GROUP BY modelo
        UNION
        SELECT mydb.p_placa_mae.modelo FROM mydb.p_placa_mae 
        WHERE mydb.p_placa_mae.modelo LIKE '%".$argumento."%' GROUP BY modelo
        UNION
        SELECT mydb.p_gabinete.modelo FROM mydb.p_gabinete 
        WHERE mydb.p_gabinete.modelo LIKE '%".$argumento."%' GROUP BY modelo
        UNION
        SELECT mydb.p_processador.modelo FROM mydb.p_processador 
        WHERE mydb.p_processador.modelo LIKE '%".$argumento."%' GROUP BY modelo
        ORDER BY modelo ASC;
    ";
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    echo json_encode($resultado);
?>