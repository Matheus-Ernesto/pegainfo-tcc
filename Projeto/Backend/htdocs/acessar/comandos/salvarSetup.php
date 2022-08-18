<?php
    $nome = $_POST['nome'];
    $gabinete = $_POST['gabinete'];
    $processador = $_POST['processador'];
    $mem_armaz = $_POST['mem_armaz'];
    $mem_ram = $_POST['mem_ram'];
    $placa_mae = $_POST['placa_mae'];
    $placa_de_video = $_POST['placa_de_video'];
    $email = $_POST['email'];

    $comando = "INSERT IGNORE INTO mydb.u_setups (email,nome,gabinete,processador,mem_armaz,mem_ram,placa_mae,placa_de_video) VALUES
    ('".$email."','".$nome."','".$gabinete."','".$processador."','".$mem_armaz."','".$mem_ram."','".$placa_mae."','".$placa_de_video."');
    ";
    $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
    $resultado = $conexao->Comando($comando);
    if($resultado){
        echo 'OK';
    }else {
        echo json_encode(array('status' => $resultado));
    }
?>