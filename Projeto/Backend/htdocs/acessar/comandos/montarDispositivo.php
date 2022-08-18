<?php
    $tipo = $_POST['tipo'];
    $argumentos = $_POST['argumentos'];
    $comando = sprintf("SELECT modelo FROM mydb.%s", $tipo);
    if(strlen($argumentos)>=4){
        $comando .= " WHERE ";
    }
    switch ($tipo) {
        case 'd_celulares':
            if(str_contains($argumentos,"Para Jogos Realistas")){
                $comando .= "qnt_ram>=3 AND ";
            }
            if(str_contains($argumentos,"Com Tela Grande")){
                $comando .= "polegadas_tela>=6 AND ";
            }
            if(str_contains($argumentos,"Com Muita Bateria")){
                $comando .= "bateria>=4000 AND ";
            }
            break;
        case 'd_tablets':
            if(str_contains($argumentos,"Para Desenho")){
                $comando .= "tela_tipo NOT LIKE '%TFT%' AND ";
            }
            if(str_contains($argumentos,"Robusto")){
                $comando .= "qnt_ram>=3 AND ";
            }
            if(str_contains($argumentos,"Com Muita Bateria")){
                $comando .= "bateria>=5000 AND ";
            }
            break;
        case 'd_notebooks':
            if(str_contains($argumentos,"Para Jogos")){
                $comando .= "qtn_ram>=6 AND ";
            }
            if(str_contains($argumentos,"Com Boa Tela")){
                $comando .= "tipo_tela NOT LIKE '%TN%' AND ";
            }
            if(str_contains($argumentos,"Com Bastante Memória")){
                $comando .= "tam_mem_armaz>=256 AND ";
            }
            if(str_contains($argumentos,"Mais Rápido")){
                $comando .= "mem_armaz NOT LIKE '%HD%' AND ";
            }
            break;
        default:
            if(str_contains($argumentos,"Para Jogos Realistas")){
                $comando .= "qnt_ram>=3 AND ";
            }
            if(str_contains($argumentos,"Com Tela Grande")){
                $comando .= "polegadas_tela>=6 AND ";
            }
            if(str_contains($argumentos,"Com Muita Bateria")){
                $comando .= "bateria>=4000 AND ";
            }
            break;
    }
    $comando = rtrim($comando,"AND ");
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    echo json_encode($resultado);
?>