<?php

    $argumentos = $_POST['argumentos'];
    $resultadoCompleto = array('processador' => array(),'gabinete' => array(),
    'placa_mae' => array(),'ram' => array(),'placa_de_video' => array(),'memoria' => array());

    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");

    $especificacoes_PROCESSADOR = array(1.8,2,2,"INTEL");
    $especificacoes_RAM = 1;
    $especificacoes_VIDEO = array(800,200);
    $especificacoes_ARMAZ = array("HD",120);;
    if(str_contains($argumentos,"Para Jogos Complexos")){
        $especificacoes_PROCESSADOR = array(2.8,6,6,"INTEL");
        $especificacoes_VIDEO = array(6000,1000);
        $especificacoes_RAM = 16;
    }else if(str_contains($argumentos,"Para Jogos Simples")){
        $especificacoes_PROCESSADOR = array(2.6,4,6,"INTEL");
        $especificacoes_VIDEO = array(2000,500);
        $especificacoes_RAM = 8;
    }else if(str_contains($argumentos,"Para Trabalhos De Office")){
        $especificacoes_PROCESSADOR = array(2.1,2,4,"INTEL");
        $especificacoes_RAM = 4;
    }
    
    if(str_contains($argumentos,"Duradouro")){
        $especificacoes_PROCESSADOR[3] = "AMD";
    }else {
        $especificacoes_PROCESSADOR[3] = "INTEL";
    }

    if(str_contains($argumentos,"Mais rápido")){
        $especificacoes_ARMAZ = array("SSD",256);
    }else if(str_contains($argumentos,"Com Bastante Memória")){
        $especificacoes_ARMAZ = array("HD",512);
    }
    //processador
    $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_processador WHERE 
    freq_ghz>=".$especificacoes_PROCESSADOR[0]." 
    AND qtn_thread>=".$especificacoes_PROCESSADOR[2]." AND qtn_nucleo>=".$especificacoes_PROCESSADOR[1]." 
    AND fabricante LIKE '%".$especificacoes_PROCESSADOR[3]."%'");
    $cont = 0;
    foreach ($resultado as $linha) {
        $resultadoCompleto['processador'][$cont] = $linha['modelo'];
        $cont++;
    }
    //gabinete
    $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_gabinete");
    $cont = 0;
    foreach ($resultado as $linha) {
        $resultadoCompleto['gabinete'][$cont] = $linha['modelo'];
        $cont++;
    }
    //ram
    $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_memoria_ram WHERE 
    tamanho >= ".$especificacoes_RAM."");
    $cont = 0;
    foreach ($resultado as $linha) {
        $resultadoCompleto['ram'][$cont] = $linha['modelo'];
        $cont++;
    }
    //memoria
    $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_memoria_armaz WHERE 
    tipo_armaz LIKE '%".$especificacoes_ARMAZ[0]."%' AND capacidade >= ".$especificacoes_ARMAZ[1]."");
    $cont = 0;
    foreach ($resultado as $linha) {
        $resultadoCompleto['memoria'][$cont] = $linha['modelo'];
        $cont++;
    }
    //placa de video
    $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_placa_de_video WHERE 
    mem_clock >= ".$especificacoes_VIDEO[0]." AND gpu_clock >= ".$especificacoes_VIDEO[1]."");
    $cont = 0;
    foreach ($resultado as $linha) {
        $resultadoCompleto['placa_de_video'][$cont] = $linha['modelo'];
        $cont++;
    }
    //placa mae
    if($especificacoes_PROCESSADOR[3] == "INTEL"){
        $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_placa_mae WHERE
        soquete LIKE '%LGA%'");
        $cont = 0;
        foreach ($resultado as $linha) {
            $resultadoCompleto['placa_mae'][$cont] = $linha['modelo'];
            $cont++;
        }
    }else {
        $resultado = $conexao->Comando("SELECT modelo FROM mydb.p_placa_mae WHERE
        soquete LIKE '%AMD%'");
        $cont = 0;
        foreach ($resultado as $linha) {
            $resultadoCompleto['placa_mae'][$cont] = $linha['modelo'];
            $cont++;
        }
    }
    
    echo json_encode($resultadoCompleto);
?>