package com.pegainfotcc.projeto_android23_pegainfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

public class WebConn {

    RequestQueue conexao;
    Testador alerta_sql;
    String link = "http://192.168.0.154/acessar/cliente_service.php";
    String fotos = "http://192.168.0.154/acessar/fotos/";
    Activity act;

    //falha de internet
    Response.ErrorListener falha_padrao = error -> {
        if (error.toString().contains("com.android.volley.TimeoutError") || error.toString().contains("com.android.volley.NoConnectionError")){
            alerta_sql.Popup("Sem Conexão",false);
        }else {
            alerta_sql.Dialogo_Simples("Erro(ErrorListener):" + error.toString());
        }
    };

    public WebConn(Activity act){
        this.act = act;
        alerta_sql = new Testador(act);
        conexao = Volley.newRequestQueue(act);
    }

    public void conectarPOST(Response.Listener<String> resposta, Map<String,String> dados)
    {

        StringRequest requisicao = new StringRequest(Request.Method.POST, link, resposta, falha_padrao){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                return dados;
            }
        };
        conexao.add(requisicao);
    }

    public void conectarPOST(Response.Listener<String> resposta, Response.ErrorListener erro, Map<String,String> dados)
    {
        StringRequest requisicao = new StringRequest(Request.Method.POST, link, resposta, erro){
            @Override
            protected Map<String,String> getParams() {
                return dados;
            }
        };
        conexao.add(requisicao);
    }

    public void baixarImagem(String modelo, ImageView objeto)
    {
            Utilidades util = new Utilidades(act);
            modelo = modelo.toLowerCase();
            modelo = modelo.replace(' ', '_');
            modelo += ".png";
            ImageRequest requisicao = new ImageRequest(fotos + modelo, objeto::setImageBitmap,
                    util.DPparaPixel(168),  util.DPparaPixel(168), null, null);
            objeto.setScaleType(ImageView.ScaleType.FIT_XY);
            conexao.add(requisicao);
    }
    public void cancelarConexoes(int tipo){
        switch (tipo){
            case 1:
                break;
            default:
                conexao.cancelAll(new RequestQueue.RequestFilter() {
                    @Override
                    public boolean apply(Request<?> request) {
                        return true;
                    }
                });
                break;
        }

    }

    public void abrirLINK(String URL){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        act.startActivity(browserIntent);
    }

    public void testar(Response.Listener<String> resposta, Response.ErrorListener erro){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","testar");

        conectarPOST(resposta, erro, dados);
    }
    public void cadastrar(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String senha){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","cadastrar");
        dados.put("email",email);
        dados.put("senha",senha);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void logar(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String senha){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","logar");
        dados.put("email",email);
        dados.put("senha",senha);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void pesquisar(Response.Listener<String> resposta, Response.ErrorListener erro, String argumento){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","pesquisar");
        dados.put("argumento",argumento);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void pesquisarTudo(Response.Listener<String> resposta, Response.ErrorListener erro, String argumento){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","pesquisarTudo");
        dados.put("argumento",argumento);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void coletarConta(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String senha){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","coletarConta");
        dados.put("email",email);
        dados.put("senha",senha);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void mudarDados(Response.Listener<String> resposta, Response.ErrorListener erro, String nome, String email_novo, String email, String senha){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","mudarDados");
        dados.put("nome",nome);
        dados.put("email_novo",email_novo);
        dados.put("email",email);
        dados.put("senha",senha);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void deletarConta(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String senha){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","deletarConta");
        dados.put("email",email);
        dados.put("senha",senha);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void produtoUnico(Response.Listener<String> resposta, Response.ErrorListener erro, String modelo){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","produtoUnico");
        dados.put("modeloProduto",modelo);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void compararProdutos(Response.Listener<String> resposta, Response.ErrorListener erro, String tabela, String[] modelos){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","compararProduto");
        dados.put("tabela",tabela);
        dados.put("modelos",modelos[0]+";"+modelos[1]+";"+modelos[2]);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void favoritar(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String modelo){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","favoritar");
        dados.put("email",email);
        dados.put("modelo",modelo);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void desfavoritar(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String modelo){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","desfavoritar");
        dados.put("email",email);
        dados.put("modelo",modelo);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void jaFavoritado(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String modelo){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","jaFavoritado");
        dados.put("email",email);
        dados.put("modelo",modelo);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void verSetup(Response.Listener<String> resposta, Response.ErrorListener erro, String email){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","verSetup");
        dados.put("email",email);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void montarDispositivo(Response.Listener<String> resposta, Response.ErrorListener erro, String tipo, String argumentos){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","montarDispositivo");
        dados.put("tipo",tipo);
        dados.put("argumentos",argumentos);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void montarComputador(Response.Listener<String> resposta, Response.ErrorListener erro, String argumentos){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","montarComputador");
        dados.put("argumentos",argumentos);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void salvarSetup(Response.Listener<String> resposta, Response.ErrorListener erro, String email, String nome, String gabinete, String processador,
                            String mem_armaz, String mem_ram, String placa_mae, String placa_de_video){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","salvarSetup");
        dados.put("email",email);
        dados.put("nome",nome);
        dados.put("gabinete",gabinete);
        dados.put("processador",processador);
        dados.put("mem_armaz",mem_armaz);
        dados.put("mem_ram",mem_ram);
        dados.put("placa_de_video",placa_de_video);
        dados.put("placa_mae",placa_mae);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void lojaModelo(Response.Listener<String> resposta, Response.ErrorListener erro, String modelo){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","lojaModelo");
        dados.put("modelo",modelo);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
    public void verSetupUnico(Response.Listener<String> resposta, Response.ErrorListener erro, String modelo){
        Map<String, String> dados = new HashMap<>();
        dados.put("comando","verSetupUnico");
        dados.put("modelo",modelo);

        if(erro == null){
            conectarPOST(resposta, dados);
        }else {
            conectarPOST(resposta, erro, dados);
        }
    }
}
