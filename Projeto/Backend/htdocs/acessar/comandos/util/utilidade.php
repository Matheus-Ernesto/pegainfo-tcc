<?php
function maioresDeTres($valor1,$valor2,$valor3)
{
    if ($valor1 > $valor2 && $valor1 > $valor3) {
        # Valor 1 alto
        if ($valor2 > $valor3) {
            # Valor 2 medio
            # Valor 3 baixo
        }else {
            # Valor 2 baixo
            # Valor 3 medio
        }
    }else if ($valor2 > $valor1 && $valor2 > $valor3) {
        # Valor 2 alto
        if ($valor1 > $valor3) {
            # Valor 1 medio
            # Valor 3 baixo
        }else {
            # Valor 1 baixo
            # Valor 3 medio
        }
    }else if ($valor3 > $valor1 && $valor3 > $valor2) {
        # Valor 3 alto
        if ($valor2 > $valor1) {
        # Valor 1 baixo
        # Valor 2 medio
        }else {
            # Valor 2 baixo
            # Valor 3 medio
        }
    }else if(){

    }
}
?>