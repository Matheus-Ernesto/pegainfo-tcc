<?php
    $argumento = $_POST['argumento'];
    $comando = "";
    if ($argumento == "novos")
    {
        $comando = sprintf("
        SELECT mydb.d_celulares.modelo, mydb.d_celulares.data_lancamento 
        FROM mydb.d_celulares GROUP BY modelo
        UNION
        SELECT mydb.d_tablets.modelo, mydb.d_tablets.data_lancamento 
        FROM mydb.d_tablets GROUP BY modelo
        UNION
        SELECT mydb.p_processador.modelo, mydb.p_processador.data_lancamento 
        FROM mydb.p_processador GROUP BY modelo
        ORDER BY data_lancamento DESC;
        ");
    }else if ($argumento == "populares") {
        $comando = sprintf("
        SELECT mydb.d_celulares.modelo, mydb.d_celulares.visualizacao 
        FROM mydb.d_celulares GROUP BY modelo
        UNION
        SELECT mydb.d_tablets.modelo, mydb.d_tablets.visualizacao 
        FROM mydb.d_tablets GROUP BY modelo
        UNION
        SELECT mydb.p_memoria_armaz.modelo, mydb.p_memoria_armaz.visualizacao 
        FROM mydb.p_memoria_armaz GROUP BY modelo
        UNION
        SELECT mydb.p_memoria_ram.modelo, mydb.p_memoria_ram.visualizacao 
        FROM mydb.p_memoria_ram GROUP BY modelo
        UNION
        SELECT mydb.p_placa_de_video.modelo, mydb.p_placa_de_video.visualizacao 
        FROM mydb.p_placa_de_video GROUP BY modelo
        UNION
        SELECT mydb.p_placa_mae.modelo, mydb.p_placa_mae.visualizacao 
        FROM mydb.p_placa_mae GROUP BY modelo
        UNION
        SELECT mydb.p_processador.modelo, mydb.p_processador.visualizacao 
        FROM mydb.p_processador GROUP BY modelo ORDER BY visualizacao DESC;
        ");
    }else {
        $comando = sprintf("SELECT modelo FROM mydb.%s", $argumento);
    }
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    echo json_encode($resultado);
?>