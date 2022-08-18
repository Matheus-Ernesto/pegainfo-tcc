<?php
    $produto = $_POST['modelo'];
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $comando = "SELECT nome,gabinete,processador,mem_armaz,mem_ram,placa_de_video,placa_mae 
    FROM mydb.u_setups 
    WHERE BINARY nome = BINARY '".$produto."'";
    $resultado = $conexao->Comando($comando);
    $resultado_formatado = array(
        array(
            "Modelo" => $resultado[0]['nome'],
            "Ano" => "Sem data"
        ),
        array(
            "coluna" => 'Processador',
            "dado" => $resultado[0]['processador']
        ),
        array(
            "coluna" => 'Placa de Vídeo',
            "dado" => $resultado[0]['placa_de_video']
        ),
        array(
            "coluna" => 'Placa-Mãe',
            "dado" => $resultado[0]['placa_mae']
        ),
        array(
            "coluna" => 'Memória RAM',
            "dado" => $resultado[0]['mem_ram']
        ),
        array(
            "coluna" => 'Memória de Armazenamento',
            "dado" => $resultado[0]['mem_armaz']
        ),
        array(
            "coluna" => 'Gabinete',
            "dado" => $resultado[0]['gabinete']
        )
    );
    echo json_encode($resultado_formatado);
?>