<?php
//Classes usadas
class UsuarioObj{
    public $nome;
    public $email;
    public $senha;
}

class mysql_con{
    private String $usuario = "";
    private String $senha = "";
    private String $host = "";
    private String $db = "";

    function __construct(String $host, String $db, String $usuario, String $senha){
        $this->usuario = $usuario;
        $this->senha = $senha;
        $this->host = $host;
        $this->db = $db;
    }

    public function Testar(){
        $conn = mysqli_connect($this->host, $this->usuario, $this->senha,$this->db) or
        die("Erro");
        mysqli_close($conn);
        return "Sucesso";
    }

    public function Comando(String $comando){
        $conn = mysqli_connect($this->host, $this->usuario, $this->senha,$this->db);
        
        $resultado = mysqli_query($conn, $comando) or die("sqlError:" . mysqli_error( $conn));

        mysqli_close($conn);
        
        $resultCorrigido = array(array());
        $contador = 0;
        if(!is_iterable($resultado)){
            return true;
        } else {
            while ($linha = mysqli_fetch_assoc($resultado)) {
                $resultCorrigido[$contador] = $linha;
                $contador++;
            }
            return $resultCorrigido;
        }
        
    }
}
?>