<?php
    $tabela = $_POST['tabela'];
    $modelos = $_POST['modelos'];
    $modelosAry = explode(";",$modelos);
    $modeloDados[] = array();
    for($i = 0; $i <= 3;$i++){
        if(isset($modelosAry[$i]) && $modelosAry[$i] != ""){
            $comando = "
            SELECT * FROM mydb.".$tabela." 
            WHERE BINARY mydb.".$tabela.".modelo LIKE BINARY '%".$modelosAry[$i]."%'
            ";
            $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
            $resultado = $conexao->Comando($comando);
            
            switch ($tabela) {
                case "d_celulares":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['data_lancamento'],
                        $resultado[0]['marca'],$resultado[0]['qnt_ram']."gb",
                        $resultado[0]['processador'],$resultado[0]['tela_tipo'],
                        $resultado[0]['polegadas_tela'].'"',$resultado[0]['cameras'],
                        $resultado[0]['sistema_op'],$resultado[0]['qnt_rom']."gb",
                        $resultado[0]['bateria']."mAh",$resultado[0]['resolu_tela']
                    );
                    break;
                case "d_tablets":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['data_lancamento'],
                        $resultado[0]['marca'],$resultado[0]['qnt_ram']."gb",
                        $resultado[0]['processador'],$resultado[0]['tela_tipo'],
                        $resultado[0]['polegadas_tela'].'"',$resultado[0]['cameras'],
                        $resultado[0]['sistema_op'],$resultado[0]['qnt_rom']."gb",
                        $resultado[0]['bateria']."mAh",$resultado[0]['resolu_tela'],
                        $resultado[0]['caneta']
                    );
                    break;
                case "p_gabinete":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['marca'],
                        $resultado[0]['fator_forma'],$resultado[0]['slot_expans'],
                        $resultado[0]['dimensao'],$resultado[0]['entradas'],
                        $resultado[0]['qtn_entradas'],$resultado[0]['qtn_baias'],
                        $resultado[0]['tam_baias'],$resultado[0]['qtn_cooler'],
                        $resultado[0]['cooler_tam'],$resultado[0]['compatibilidade'],
                        $resultado[0]['comp_placa_graf'],$resultado[0]['comp_cooler_cpu'],
                        ($resultado[0]['peso']/10000)."kg"
                    );
                    break;
                case "p_memoria_armaz":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['marca'],
                        $resultado[0]['tipo_armaz'],$resultado[0]['capacidade']."gb",
                        $resultado[0]['vel_rotacao'],$resultado[0]['tamanho'].'"',
                        $resultado[0]['interface']
                    );
                    break;
                case "p_memoria_ram":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['marca'],
                        $resultado[0]['tipo'],$resultado[0]['tamanho']."gb",
                        $resultado[0]['canal'],$resultado[0]['frequencia']."mHz",
                        $resultado[0]['latencia_cas']."ms"
                    );
                    break;
                case "p_placa_de_video":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['marca'],
                        $resultado[0]['fabricante'],$resultado[0]['serie'],
                        $resultado[0]['memoria'],$resultado[0]['mem_clock']."mHz",
                        ($resultado[0]['peso']/1000)."kg",$resultado[0]['gpu_clock']."mHz",
                        $resultado[0]['nucleo_cuda'],$resultado[0]['resolucao'],
                        $resultado[0]['tipo_saida'],$resultado[0]['qtn_saida'],
                        $resultado[0]['dimensao']
                    );
                    break;
                case "p_placa_mae":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['marca'],
                        $resultado[0]['formato'],$resultado[0]['chipset'],
                        $resultado[0]['slots_dimm'],$resultado[0]['tipo_dimm'],
                        $resultado[0]['slots_pci'],$resultado[0]['soquete'],
                        $resultado[0]['usb']
                    );
                    break;
                case "p_processador":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['fabricante'],
                        $resultado[0]['data_lancamento'],$resultado[0]['litografia']."nm",
                        $resultado[0]['qtn_nucleo'],$resultado[0]['qtn_thread'],
                        $resultado[0]['freq_ghz']."gHz",$resultado[0]['tdp']."W",
                        $resultado[0]['cache'],$resultado[0]['chip_graf'],
                        $resultado[0]['soquete']
                    );
                    break;
                case "d_notebooks":
                    $modeloDados[$i] = array(
                        $resultado[0]['modelo'],$resultado[0]['marca'],
                        $resultado[0]['mem_armaz'],$resultado[0]['tam_mem_armaz']."gb",
                        $resultado[0]['vel_rotacao'],$resultado[0]['tipo_ram'],
                        $resultado[0]['qtn_ram']."gb",$resultado[0]['processador'],
                        $resultado[0]['cache']."mb",$resultado[0]['tipo_tela'],
                        $resultado[0]['proporcao'],$resultado[0]['resolucao'],
                        $resultado[0]['entradas'],$resultado[0]['qtn_entradas'],
                        $resultado[0]['dimensao'],$resultado[0]['placa_video'],
                        ($resultado[0]['peso']/1000)."kg"
                    );
                    break;
                default:
                    break;
            }
        }else {
            switch ($tabela) {
                case "d_celulares":
                    $modeloDados[$i] = array("","","","","","","","","","","","");
                    break;
                case "d_tablets":
                    $modeloDados[$i] = array("","","","","","","","","","","","","");
                    break;
                case "p_gabinete":
                    $modeloDados[$i] = array("","","","","","","","","","","","","","","");
                    break;
                case "p_memoria_armaz":
                    $modeloDados[$i] = array("","","","","","","");
                    break;
                case "p_memoria_ram":
                    $modeloDados[$i] = array("","","","","","","");
                    break;
                case "p_placa_de_video":
                    $modeloDados[$i] = array("","","","","","","","","","","","","","","","");
                    break;
                case "p_placa_mae":
                    $modeloDados[$i] = array("","","","","","","","","");
                    break;
                case "p_processador":
                    $modeloDados[$i] = array("","","","","","","","","","","");
                    break;
                case "d_notebooks":
                    $modeloDados[$i] = array("","","","","","","","","","","","","","","","","");
                    break;
                default:
                    break;
            }
            
        }
    }
    $colunas;
    switch ($tabela) {
        case "d_celulares":
            $colunas = array(
                'Modelo','Lançamento',
                'Marca','Quantidade de RAM',
                'Processador','Tipo de tela',
                'Tamanho (Polegadas)','Câmeras',
                'Sistema Operacional','Quantidade de Memória',
                'Bateria','Resolução de Tela'
            );
            break;
        case "p_placa_de_video":
            $colunas = array(
                'Modelo','Marca',
                'Fabricante','Série',
                'Memória','Memória de Clock',
                'Peso','Clock da GPU',
                'Núcleos CUDA','Resolução Máxima',
                'Tipo Saída','Quantidade de Saídas',
                'Dimensão'
            );
            break;
        case "d_tablets":
            $colunas = array(
                'Modelo','Lançamento',
                'Marca','Quantidade de RAM',
                'Processador','Tipo de tela',
                'Tamanho (Polegadas)','Câmeras',
                'Sistema Operacional','Quantidade de Memória',
                'Bateria','Resolução de Tela',
                'Acompanha Caneta'
            );
            break;
        case "p_gabinete":
            $colunas = array(
                'Modelo','Marca',
                'Forma','Slots Expansíveis',
                'Dimensões','Entradas',
                'Quantidade de Entradas','Quantidade de Baias',
                'Tamanho das Baias','Quantidade de Coolers',
                'Tamanho de Cooler','Compatibilidade com placas-mães',
                'Compatibilidade com placas de vídeo','Compatibilidade com coolers de processador',
                'Peso'
            );
            break;
        case "p_memoria_armaz":
            $colunas = array(
                'Modelo','Marca',
                'Tipo de Armazenamento','Capacidade',
                'Velocidade de Rotação(HD)/Velocidade de Acesso(SSD)','Tamanho',
                'Interface'
            );
            break;
        case "p_processador":
            $colunas = array(
                'Modelo','Fabricante',
                'Lançamento','Litografia',
                'Quantidade de Núcleos','Quantidade de Threads',
                'Frequência','TDP',
                'Cache','Chip Gráfico Integrado',
                'Soquete'
            );
            break;
        case "p_placa_mae":
            $colunas = array(
                'Modelo','Marca',
                'Formato','Chipset',
                'Slots de memória DIMM',
                'Tipo de Memória RAM','Quantidade de Slots PCI',
                'Soquete','Entradas USB'

            );
            break;
        case "p_memoria_ram":
            $colunas = array(
                'Modelo','Marca',
                'Tipo de Encaixe','Tamanho',
                'Canal','Frequência',
                'Latência CAS'
            );
            break;
        case "d_notebooks":
            $colunas = array(
                'Modelo','Marca',
                'Memória de Armazenamento','Tamanho de Memória de Armaz.',
                'Velocidade de Rotação/Velocidade de Acesso','Tipo de RAM',
                'Quantidade de RAM','Processador',
                'Cache','Tipo de Tela',
                'Proporção','Resolução',
                'Entradas','Quantidade de Entradas',
                'Dimensão','Placa de Vídeo',
                'Peso'
            );
            break;
        default:
            break;
    }
    $retornoCompleto = array();
    for ($i=0; $i < count($colunas); $i++) { 
        $retornoCompleto[$i] = 
            array(
                'coluna' => $colunas[$i],
                'modelo1' => $modeloDados[0][$i],
                'modelo2' => $modeloDados[1][$i],
                'modelo3' => $modeloDados[2][$i]
            );
    }
    echo json_encode($retornoCompleto);
?>