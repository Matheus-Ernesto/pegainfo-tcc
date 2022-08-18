package com.pegainfotcc.projeto_android23_pegainfo;

import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class Testador {

    Activity act;

    public Testador(Activity act_t){
        act = act_t;
    }

    public void Dialogo_Simples(String conteudo){
        AlertDialog.Builder construtor = new AlertDialog.Builder(act);
        construtor.setTitle("Aviso");
        construtor.setMessage(conteudo);
        AlertDialog alerta = construtor.create();
        alerta.show();
    }

    public void Popup(String conteudo,boolean rapido){
        Toast.makeText(act,
                conteudo,
                (rapido) ? Toast.LENGTH_SHORT: Toast.LENGTH_LONG).show();
    }

}
