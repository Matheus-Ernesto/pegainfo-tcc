<?php
    $produto = $_POST['modeloProduto'];
    $tabelas = array("d_celulares","d_tablets","p_gabinete","p_processador",
    "p_memoria_ram","p_placa_de_video","p_memoria_armaz","p_placa_mae","d_desktops","d_notebooks");
    $contador = 0;
    do {
        $comando = "
        SELECT * FROM mydb.".$tabelas[$contador]." 
        WHERE BINARY mydb.".$tabelas[$contador].
        ".modelo LIKE BINARY '%".$produto."%'
        ";
        $conexao = new mysql_con("localhost","mydb","cliente", "cliente");
        $resultado = $conexao->Comando($comando);
        if (isset($resultado[0]['modelo'])) {
            if(isset($resultado[0]['visualizacao']) && $resultado[0]['visualizacao'] != ""){
                $comando2 = "
                UPDATE mydb.".$tabelas[$contador]." 
                SET ".$tabelas[$contador].".visualizacao = '1' + ".$tabelas[$contador].".visualizacao 
                WHERE BINARY mydb.".$tabelas[$contador].".modelo LIKE BINARY '%".$resultado[0]['modelo']."%';";
                $conexao2 = new mysql_con("localhost","mydb","cliente", "cliente");
                $resultado2 = $conexao2->Comando($comando2);
            }else {
                $comando2 = "
                UPDATE mydb.".$tabelas[$contador]." 
                SET ".$tabelas[$contador].".visualizacao = '1' 
                WHERE BINARY mydb.".$tabelas[$contador].".modelo LIKE BINARY '%".$resultado[0]['modelo']."%';";
                $conexao2 = new mysql_con("localhost","mydb","cliente", "cliente");
                $resultado2 = $conexao2->Comando($comando2);
            }
            
            switch ($tabelas[$contador]) {
                case "d_celulares":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => $resultado[0]['data_lancamento']
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Lançamento',
                            "dado" => $resultado[0]['data_lancamento']
                        ),
                        array(
                            "coluna" => 'Quantidade de RAM',
                            "dado" => $resultado[0]['qnt_ram'] . "gb"
                        ),
                        array(
                            "coluna" => 'Processador',
                            "dado" => $resultado[0]['processador']
                        ),
                        array(
                            "coluna" => 'Tipo de tela',
                            "dado" => $resultado[0]['tela_tipo']
                        ),
                        array(
                            "coluna" => 'Tamanho (Polegadas)',
                            "dado" => $resultado[0]['polegadas_tela'] . '"'
                        ),
                        array(
                            "coluna" => 'Câmeras',
                            "dado" => $resultado[0]['cameras']
                        ),
                        array(
                            "coluna" => 'Sistema Operacional',
                            "dado" => $resultado[0]['sistema_op']
                        ),
                        array(
                            "coluna" => 'Quantidade de Memória',
                            "dado" => $resultado[0]['qnt_rom'] . "gb"
                        ),
                        array(
                            "coluna" => 'Bateria',
                            "dado" => $resultado[0]['bateria'] . "mAh"
                        ),
                        array(
                            "coluna" => 'Resolução de Tela',
                            "dado" => $resultado[0]['resolu_tela']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "p_placa_de_video":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Fabricante',
                            "dado" => $resultado[0]['fabricante']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Série',
                            "dado" => $resultado[0]['serie']
                        ),
                        array(
                            "coluna" => 'Memória',
                            "dado" => $resultado[0]['memoria']
                        ),
                        array(
                            "coluna" => 'Clock de Memória',
                            "dado" => $resultado[0]['mem_clock']
                        ),
                        array(
                            "coluna" => 'Clock',
                            "dado" => $resultado[0]['gpu_clock']
                        ),
                        array(
                            "coluna" => 'CUDAs',
                            "dado" => $resultado[0]['nucleo_cuda']
                        ),
                        array(
                            "coluna" => 'Dimensão',
                            "dado" => $resultado[0]['dimensao']
                        ),
                        array(
                            "coluna" => 'Peso',
                            "dado" => $resultado[0]['peso'] / 100 . "Kg"
                        ),
                        array(
                            "coluna" => 'Tipos de Saídas',
                            "dado" => $resultado[0]['tipo_saida']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "p_memoria_armaz":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Tipo de Armazenamento',
                            "dado" => $resultado[0]['tipo_armaz']
                        ),
                        array(
                            "coluna" => 'Capacidade',
                            "dado" => $resultado[0]['capacidade'] . "gb"
                        ),
                        array(
                            "coluna" => 'Velocidade de Rotação/ Velocidade de Acesso',
                            "dado" => $resultado[0]['vel_rotacao']
                        ),
                        array(
                            "coluna" => 'Tamanho',
                            "dado" => $resultado[0]['tamanho'] . '"'
                        ),
                        array(
                            "coluna" => 'Interface',
                            "dado" => $resultado[0]['interface']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "d_tablets":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => $resultado[0]['data_lancamento']
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Lançamento',
                            "dado" => $resultado[0]['data_lancamento']
                        ),
                        array(
                            "coluna" => 'Quantidade de RAM',
                            "dado" => $resultado[0]['qnt_ram'] . "gb"
                        ),
                        array(
                            "coluna" => 'Processador',
                            "dado" => $resultado[0]['processador']
                        ),
                        array(
                            "coluna" => 'Tipo de tela',
                            "dado" => $resultado[0]['tela_tipo']
                        ),
                        array(
                            "coluna" => 'Tamanho (Polegadas)',
                            "dado" => $resultado[0]['polegadas_tela'] . '"'
                        ),
                        array(
                            "coluna" => 'Câmeras',
                            "dado" => $resultado[0]['cameras']
                        ),
                        array(
                            "coluna" => 'Sistema Operacional',
                            "dado" => $resultado[0]['sistema_op']
                        ),
                        array(
                            "coluna" => 'Quantidade de Memória',
                            "dado" => $resultado[0]['qnt_rom'] . "gb"
                        ),
                        array(
                            "coluna" => 'Bateria',
                            "dado" => $resultado[0]['bateria'] . "mAh"
                        ),
                        array(
                            "coluna" => 'Resolução de Tela',
                            "dado" => $resultado[0]['resolu_tela']
                        ),
                        array(
                            "coluna" => 'Acompanha caneta',
                            "dado" => $resultado[0]['caneta']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "p_gabinete":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Forma',
                            "dado" => $resultado[0]['fator_forma']
                        ),
                        array(
                            "coluna" => 'Slots Expansíveis',
                            "dado" => $resultado[0]['slot_expans']
                        ),
                        array(
                            "coluna" => 'Dimensões',
                            "dado" => $resultado[0]['dimensao']
                        ),
                        array(
                            "coluna" => 'Entradas',
                            "dado" => $resultado[0]['entradas']
                        ),
                        array(
                            "coluna" => 'Quantidade de entradas',
                            "dado" => $resultado[0]['qtn_entradas']
                        ),
                        array(
                            "coluna" => 'Quantidade de baias',
                            "dado" => $resultado[0]['qtn_baias']
                        ),
                        array(
                            "coluna" => 'Tamanho das baias',
                            "dado" => $resultado[0]['tam_baias']
                        ),
                        array(
                            "coluna" => 'Quantidade de coolers',
                            "dado" => $resultado[0]['qtn_cooler']
                        ),
                        array(
                            "coluna" => 'Tamanho de cooler',
                            "dado" => $resultado[0]['cooler_tam']
                        ),
                        array(
                            "coluna" => 'Compatibilidade com placas-mães',
                            "dado" => $resultado[0]['compatibilidade']
                        ),
                        array(
                            "coluna" => 'Compatibilidade com placas de vídeo',
                            "dado" => $resultado[0]['comp_placa_graf']
                        ),
                        array(
                            "coluna" => 'Compatibilidade com coolers de processador',
                            "dado" => $resultado[0]['comp_cooler_cpu']
                        ),
                        array(
                            "coluna" => 'Peso',
                            "dado" => (int)$resultado[0]['peso'] / 10000 . " Kg"
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "p_processador":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => $resultado[0]['data_lancamento']
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Fabricante',
                            "dado" => $resultado[0]['fabricante']
                        ),
                        array(
                            "coluna" => 'Lançamento',
                            "dado" => $resultado[0]['data_lancamento']
                        ),
                        array(
                            "coluna" => 'Litografia',
                            "dado" => $resultado[0]['litografia'] . "nm"
                        ),
                        array(
                            "coluna" => 'Quantidade de Núcleos',
                            "dado" => $resultado[0]['qtn_nucleo']
                        ),
                        array(
                            "coluna" => 'Quantidade de Threads',
                            "dado" => $resultado[0]['qtn_thread']
                        ),
                        array(
                            "coluna" => 'Frequência',
                            "dado" => $resultado[0]['freq_ghz'] . " gHz"
                        ),
                        array(
                            "coluna" => 'TDP',
                            "dado" => $resultado[0]['tdp'] . " W"
                        ),
                        array(
                            "coluna" => 'Cache',
                            "dado" => $resultado[0]['cache']
                        ),
                        array(
                            "coluna" => 'Chip Gráfico Integrado',
                            "dado" => $resultado[0]['chip_graf']
                        ),
                        array(
                            "coluna" => 'Soquete',
                            "dado" => $resultado[0]['soquete']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "p_placa_mae":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Formato',
                            "dado" => $resultado[0]['formato']
                        ),
                        array(
                            "coluna" => 'Chipset',
                            "dado" => $resultado[0]['chipset']
                        ),
                        array(
                            "coluna" => 'Slots de memória DIMM',
                            "dado" => $resultado[0]['slots_dimm']
                        ),
                        array(
                            "coluna" => 'Quantidade de Slots PCI',
                            "dado" => $resultado[0]['slots_pci']
                        ),
                        array(
                            "coluna" => 'Soquete',
                            "dado" => $resultado[0]['soquete']
                        ),
                        array(
                            "coluna" => 'Entradas USB',
                            "dado" => $resultado[0]['usb']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "p_memoria_ram":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Tipo de Encaixe',
                            "dado" => $resultado[0]['tipo']
                        ),
                        array(
                            "coluna" => 'Tamanho',
                            "dado" => $resultado[0]['tamanho'] . "gb"
                        ),
                        array(
                            "coluna" => 'Canal',
                            "dado" => $resultado[0]['canal']
                        ),
                        array(
                            "coluna" => 'Frequência',
                            "dado" => $resultado[0]['frequencia'] . "mHz"
                        ),
                        array(
                            "coluna" => 'Latência',
                            "dado" => $resultado[0]['latencia_cas'] . " pulsos"
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "d_desktops":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Modelo',
                            "dado" => $resultado[0]['modelo']
                        ),
                        array(
                            "coluna" => 'Gabinete',
                            "dado" => $resultado[0]['gabinete']
                        ),
                        array(
                            "coluna" => 'Processador',
                            "dado" => $resultado[0]['processador']
                        ),
                        array(
                            "coluna" => 'Memória de armazenamento',
                            "dado" => $resultado[0]['mem_armaz']
                        ),
                        array(
                            "coluna" => 'Memória Ram',
                            "dado" => $resultado[0]['mem_ram']
                        ),
                        array(
                            "coluna" => 'Fonte',
                            "dado" => $resultado[0]['fonte']
                        ),
                        array(
                            "coluna" => 'Placa Mãe',
                            "dado" => $resultado[0]['placa_mae']
                        ),
                        array(
                            "coluna" => 'Placa de vídeo',
                            "dado" => $resultado[0]['placa_de_video']
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                case "d_notebooks":
                    $resultado_formatado = array(
                        array(
                            "Modelo" => $resultado[0]['modelo'],
                            "Ano" => "Sem data"
                        ),
                        array(
                            "coluna" => 'Marca',
                            "dado" => $resultado[0]['marca']
                        ),
                        array(
                            "coluna" => 'Memória de Armazenamento',
                            "dado" => $resultado[0]['mem_armaz']
                        ),
                        array(
                            "coluna" => 'Tamanho de Memória de Armaz.',
                            "dado" => $resultado[0]['tam_mem_armaz'] . "gb"
                        ),
                        array(
                            "coluna" => 'Velocidade de Rotação/Velocidade de Acesso',
                            "dado" => $resultado[0]['vel_rotacao']
                        ),
                        array(
                            "coluna" => 'Tipo de RAM',
                            "dado" => $resultado[0]['tipo_ram']
                        ),
                        array(
                            "coluna" => 'Quantidade de RAM',
                            "dado" => $resultado[0]['qtn_ram']
                        ),
                        array(
                            "coluna" => 'Processador',
                            "dado" => $resultado[0]['processador']
                        ),
                        array(
                            "coluna" => 'Cache',
                            "dado" => $resultado[0]['cache']
                        ),
                        array(
                            "coluna" => 'Tipo de Tela',
                            "dado" => $resultado[0]['tipo_tela']
                        ),
                        array(
                            "coluna" => 'Proporção',
                            "dado" => $resultado[0]['proporcao']
                        ),
                        array(
                            "coluna" => 'Resolução',
                            "dado" => $resultado[0]['resolucao']
                        ),
                        array(
                            "coluna" => 'Entradas',
                            "dado" => $resultado[0]['entradas']
                        ),
                        array(
                            "coluna" => 'Quantidade de Entradas',
                            "dado" => $resultado[0]['qtn_entradas']
                        ),
                        array(
                            "coluna" => 'Dimensões',
                            "dado" => $resultado[0]['dimensao']
                        ),
                        array(
                            "coluna" => 'Placa de Vídeo',
                            "dado" => $resultado[0]['placa_video']
                        ),
                        array(
                            "coluna" => 'Peso',
                            "dado" => $resultado[0]['peso'] / 1000 . "Kg"
                        )
                    );
                    echo json_encode($resultado_formatado);
                    break;
                default:
                    echo json_encode($resultado);
                    break;
            }
            break;
        } else {
        $contador++;
        }
    } while(true);
?>