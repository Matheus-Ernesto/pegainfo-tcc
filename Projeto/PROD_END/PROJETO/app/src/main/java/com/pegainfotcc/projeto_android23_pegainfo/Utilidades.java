 package com.pegainfotcc.projeto_android23_pegainfo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utilidades {

    Activity act;

    public Utilidades(Activity act){
        this.act = act;
    }
    public int DPparaPixel(int pixels){
        final float scale = act.getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

    public void trocaJanela(ConstraintLayout janela, int Stub){
        ViewStub infla = new ViewStub(act.getBaseContext(),Stub);
        janela.removeAllViews();
        janela.addView(infla);
        infla.inflate();
    }

    public void listaJanela(Object resposta, ConstraintLayout lay_lista,
                           int orientacao, View.OnClickListener abrirProduto){
        lay_lista.removeAllViews();
        try {
            if(resposta.toString().equals("[[]]")){
                new Testador(act).Popup("Seção vazia.",true);
            }else {
                JSONArray lista = new JSONArray(resposta.toString());
                int margem_altura = 0;
                int margem_largura = 0;

                int excecaoSetup = 0;

                if (orientacao == 0){
                    margem_altura = DPparaPixel(64);
                    margem_largura = DPparaPixel(32);
                }else if (orientacao == 1){
                    margem_altura = DPparaPixel(0);
                    margem_largura = DPparaPixel(11);
                }else {
                    margem_altura = DPparaPixel(64);
                    margem_largura = DPparaPixel(32);
                    orientacao = 0;
                    excecaoSetup = 1;
                }

                for (int x = 0; x < lista.length(); x++) {
                    JSONObject produto_unico = lista.getJSONObject(x);
                    ImageView produto_fundo = new ImageView(act);
                    TextView produto_nome = new TextView(act);
                    ConstraintSet lay_listaSet = new ConstraintSet();

                    produto_fundo.setImageResource(R.drawable.icones_sem_imagem);
                    String linkImagem = (excecaoSetup == 1) ? "Setup" : produto_unico.getString("modelo");

                    produto_fundo.setBackgroundColor(0xffdddddd);
                    produto_fundo.setAdjustViewBounds(true);
                    produto_fundo.setContentDescription(produto_unico.getString("modelo"));
                    produto_fundo.setScaleType(ImageView.ScaleType.FIT_XY);
                    produto_fundo.setMaxHeight(DPparaPixel(168));
                    produto_fundo.setMaxWidth(DPparaPixel(168));
                    produto_fundo.setId(View.generateViewId());
                    produto_fundo.setLayoutParams(
                            new ConstraintLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                            )
                    );
                    lay_lista.addView(produto_fundo, x);
                    new WebConn(act).baixarImagem(linkImagem,produto_fundo);
                    produto_fundo.setOnClickListener(abrirProduto);

                    produto_nome.setText(produto_unico.getString("modelo"));
                    produto_nome.setMaxHeight(DPparaPixel(32));
                    produto_nome.setMaxWidth(DPparaPixel(160));
                    produto_nome.setTextColor(act.getColor(R.color.black));
                    produto_nome.setBackgroundColor(act.getColor(R.color.white));
                    produto_nome.setId(View.generateViewId());
                    lay_lista.addView(produto_nome, x + 1);

                    lay_listaSet.clone(lay_lista);
                    lay_listaSet.connect(produto_fundo.getId(),
                            ConstraintSet.TOP, lay_lista.getId(),
                            ConstraintSet.TOP, margem_altura);
                    lay_listaSet.connect(produto_fundo.getId(),
                            ConstraintSet.LEFT, lay_lista.getId(),
                            ConstraintSet.LEFT, margem_largura);

                    lay_listaSet.connect(produto_nome.getId(),
                            ConstraintSet.TOP, lay_lista.getId(),
                            ConstraintSet.TOP, margem_altura + DPparaPixel(136));
                    lay_listaSet.connect(produto_nome.getId(),
                            ConstraintSet.LEFT, lay_lista.getId(),
                            ConstraintSet.LEFT, margem_largura + DPparaPixel(8));
                    lay_listaSet.applyTo(lay_lista);

                    if (orientacao == 0) { //Vertical
                        if ((x + 1) % 2 != 0) { //impar
                            margem_largura += DPparaPixel(179);
                        } else {
                            margem_altura += DPparaPixel(232);
                            margem_largura -= DPparaPixel(179);
                        }
                    } else{
                        margem_largura += DPparaPixel(179);
                    }
                }
            }
        }catch (Exception e){
            new Testador(act).Dialogo_Simples("Erro: " + e.toString());
        }
    }
    public void produtoUnico(String modelo, ConstraintLayout lay_lista, WebConn conexao){
        trocaJanela(act.findViewById(R.id.lay_janela),R.layout.tela_produto);

        try {
            Response.Listener resposta = response -> {

                if (response.toString().startsWith("sqlError")){
                    new Testador(act).Dialogo_Simples("Erro de Servidor:" + response.toString());
                }else {
                    try {
                        JSONObject texto = new JSONArray(response.toString()).getJSONObject(0);
                        TextView modeloTexto = act.findViewById(R.id.txv_TituloModelo);
                        modeloTexto.setText(texto.getString("Modelo"));
                        TextView anoTexto = act.findViewById(R.id.txv_Ano);
                        anoTexto.setText(texto.getString("Ano"));
                        TextView descTexto = act.findViewById(R.id.txv_descricao_produto);
                        descTexto.setText("Descrição: " + texto.getString("Descricao"));
                        ConstraintLayout lay_dados = act.findViewById(R.id.lay_Especificacao);

                        new WebConn(act).baixarImagem(texto.getString("Modelo"),act.findViewById(R.id.img_produtoUnico));

                        ImageView img_favoritar = act.findViewById(R.id.img_favoritar);
                        SharedPreferences sharedPref = act.getPreferences(act.MODE_PRIVATE);
                        String email_b = sharedPref.getString("SAVED_EMAIL","");
                        String modelo_b = null;
                        try {
                            modelo_b = texto.getString("Modelo");
                        } catch (JSONException e) {
                            new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                        }

                        conexao.jaFavoritado(response1 -> {
                            if(response1.toString().contains("Ja Favoritado")){
                                img_favoritar.setContentDescription("Desfavoritar");
                                img_favoritar.setImageResource(R.drawable.icone_coracao);
                            }else {
                                img_favoritar.setContentDescription("Favoritar");
                                img_favoritar.setImageResource(R.drawable.icone_coracao_borda);
                            }
                        },null,email_b,modelo_b);

                        img_favoritar.setOnClickListener(v -> {
                            SharedPreferences sharedPref2 = act.getPreferences(act.MODE_PRIVATE);
                            String email_r = sharedPref2.getString("SAVED_EMAIL","");
                            String modelo_r = null;
                            try {
                                modelo_r = texto.getString("Modelo");
                            } catch (JSONException e) {
                                new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                            }
                            if(email_r.length() >= 2){
                                if(img_favoritar.getContentDescription().toString().equals("Favoritar")){
                                    img_favoritar.setContentDescription("Desfavoritar");
                                    img_favoritar.setImageResource(R.drawable.icone_coracao);
                                    Response.Listener resposta_favoritar = resposta2 -> {
                                        if(resposta2.toString().contains("Favoritado")){
                                            new Testador(act).Popup("Favoritado",true);
                                        }else {
                                            new Testador(act).Dialogo_Simples("Erro: " + resposta2.toString());
                                        }

                                    };
                                    conexao.favoritar(resposta_favoritar,null,email_r,modelo_r);
                                }else {
                                    Response.Listener resposta_favoritar = resposta2 -> {
                                        if(resposta2.toString().contains("Deletado")){
                                            new Testador(act).Popup("Desfavoritado",true);
                                        }else {
                                            new Testador(act).Dialogo_Simples("Erro: " + resposta2.toString());
                                        }
                                    };
                                    conexao.desfavoritar(resposta_favoritar,null,email_r,modelo_r);
                                    img_favoritar.setContentDescription("Favoritar");
                                    img_favoritar.setImageResource(R.drawable.icone_coracao_borda);
                                }
                            }else {
                                new Testador(act).Popup("Entre antes de favoritar algo",true);
                            }
                        });
                        //CRIAR KEYS E VALUES
                        JSONArray iter = new JSONArray(response.toString());
                        int cont = 1;
                        int margem_altura = DPparaPixel(8);

                        while(cont < iter.length()){
                            JSONObject linha = iter.getJSONObject(cont);
                            ConstraintSet lay_listaSet = new ConstraintSet();

                            int altura = (linha.getString("dado").length() >= 25) ? 64 : 48;

                            //KEY
                            TextView produtoKey = new TextView(act);
                            produtoKey.setText(linha.getString("coluna") + ':');
                            produtoKey.setHeight(DPparaPixel(altura));
                            produtoKey.setWidth(DPparaPixel(205));
                            produtoKey.setTextColor(act.getColor(R.color.tema));
                            produtoKey.setId(View.generateViewId());
                            bordaTXV(produtoKey);
                            produtoKey.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            produtoKey.setGravity(Gravity.CENTER);
                            lay_dados.addView(produtoKey);
                            //VALUE
                            TextView produtoValue = new TextView(act);
                            produtoValue.setText(linha.getString("dado"));
                            produtoValue.setHeight(DPparaPixel(altura));
                            produtoValue.setWidth(DPparaPixel(204));
                            produtoValue.setTextColor(act.getColor(R.color.tema));
                            produtoValue.setId(View.generateViewId());
                            bordaTXV(produtoValue);
                            produtoValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            produtoValue.setGravity(Gravity.CENTER);
                            lay_dados.addView(produtoValue);

                            lay_listaSet.clone(lay_dados);
                            //KEYS
                            lay_listaSet.connect(produtoKey.getId(), ConstraintSet.TOP,
                                    lay_dados.getId(), ConstraintSet.TOP, margem_altura);
                            lay_listaSet.connect(produtoKey.getId(), ConstraintSet.LEFT,
                                    lay_dados.getId(), ConstraintSet.LEFT, DPparaPixel(0));
                            //VALUES
                            lay_listaSet.connect(produtoValue.getId(), ConstraintSet.TOP,
                                    lay_dados.getId(), ConstraintSet.TOP, margem_altura);
                            lay_listaSet.connect(produtoValue.getId(), ConstraintSet.LEFT,
                                    lay_dados.getId(), ConstraintSet.LEFT, DPparaPixel(205));
                            //
                            lay_listaSet.applyTo(lay_dados);
                            margem_altura += DPparaPixel(altura);
                            cont++;
                        }

                    } catch (JSONException e) {
                        new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                    }
                }
            };
            conexao.produtoUnico(resposta,null,modelo);

            Response.Listener vendoLoja = response -> {
                if (response.toString().startsWith("sqlError")){
                    new Testador(act).Dialogo_Simples("Erro de Servidor: " + response.toString());
                }else if(response.toString().equals("[[]]")){
                    new Testador(act).Popup("Este produto não tem lojas cadastradas",true);
                }
                else {
                    try{
                        ConstraintLayout lay_lojas = act.findViewById(R.id.lay_Lojas);
                        JSONObject lojas = new JSONArray(response.toString()).getJSONObject(0);
                        ConstraintSet lay_listaSet = new ConstraintSet();

                        //KEYS
                        TextView loja_coluna = new TextView(act);
                        loja_coluna.setText("Lojas:");
                        loja_coluna.setHeight(DPparaPixel(48));
                        loja_coluna.setWidth(DPparaPixel(409));
                        loja_coluna.setTextColor(act.getColor(R.color.tema));
                        loja_coluna.setId(View.generateViewId());
                        bordaTXV(loja_coluna);
                        loja_coluna.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        loja_coluna.setGravity(Gravity.CENTER);
                        lay_lojas.addView(loja_coluna);

                        //VALUES
                        TextView loja1 = new TextView(act);
                        loja1.setText(lojas.getString("loja1"));
                        loja1.setHeight(DPparaPixel(48));
                        loja1.setWidth(DPparaPixel(136));
                        loja1.setTextColor(act.getColor(R.color.tema));
                        loja1.setId(View.generateViewId());
                        bordaTXV(loja1);
                        loja1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        loja1.setGravity(Gravity.CENTER);
                        lay_lojas.addView(loja1);

                        TextView loja2 = new TextView(act);
                        loja2.setText(lojas.getString("loja2"));
                        loja2.setHeight(DPparaPixel(48));
                        loja2.setWidth(DPparaPixel(136));
                        loja2.setTextColor(act.getColor(R.color.tema));
                        loja2.setId(View.generateViewId());
                        bordaTXV(loja2);
                        loja2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        loja2.setGravity(Gravity.CENTER);
                        lay_lojas.addView(loja2);

                        TextView loja3 = new TextView(act);
                        loja3.setText(lojas.getString("loja3"));
                        loja3.setHeight(DPparaPixel(48));
                        loja3.setWidth(DPparaPixel(136));
                        loja3.setTextColor(act.getColor(R.color.tema));
                        loja3.setId(View.generateViewId());
                        bordaTXV(loja3);
                        loja3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        loja3.setGravity(Gravity.CENTER);
                        lay_lojas.addView(loja3);

                        Button link1 = new Button(act);
                        link1.setText("Abrir");
                        link1.setHeight(DPparaPixel(48));
                        link1.setWidth(DPparaPixel(136));
                        link1.setId(View.generateViewId());
                        link1.setOnClickListener(v -> {
                            try {
                                conexao.abrirLINK(lojas.getString("link1"));
                            } catch (JSONException e) {
                                new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                            }
                        });
                        lay_lojas.addView(link1);

                        Button link2 = new Button(act);
                        link2.setText("Abrir");
                        link2.setHeight(DPparaPixel(48));
                        link2.setWidth(DPparaPixel(136));
                        link2.setId(View.generateViewId());
                        link2.setOnClickListener(v -> {
                            try {
                                conexao.abrirLINK(lojas.getString("link2"));
                            } catch (JSONException e) {
                                new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                            }
                        });
                        lay_lojas.addView(link2);

                        Button link3 = new Button(act);
                        link3.setText("Abrir");
                        link3.setHeight(DPparaPixel(48));
                        link3.setWidth(DPparaPixel(136));
                        link3.setId(View.generateViewId());
                        link3.setOnClickListener(v -> {
                            try {
                                conexao.abrirLINK(lojas.getString("link3"));
                            } catch (JSONException e) {
                                new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                            }
                        });
                        lay_lojas.addView(link3);

                        lay_listaSet.clone(lay_lojas);
                        //loja_coluna
                        lay_listaSet.connect(loja_coluna.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(0));
                        lay_listaSet.connect(loja_coluna.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(0));
                        //loja1
                        lay_listaSet.connect(loja1.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(48));
                        lay_listaSet.connect(loja1.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(0));
                        //loja2
                        lay_listaSet.connect(loja2.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(48));
                        lay_listaSet.connect(loja2.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(136));
                        //loja3
                        lay_listaSet.connect(loja3.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(48));
                        lay_listaSet.connect(loja3.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(272));
                        //link1
                        lay_listaSet.connect(link1.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(96));
                        lay_listaSet.connect(link1.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(0));
                        //link2
                        lay_listaSet.connect(link2.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(96));
                        lay_listaSet.connect(link2.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(136));
                        //link3
                        lay_listaSet.connect(link3.getId(), ConstraintSet.TOP,
                                lay_lojas.getId(), ConstraintSet.TOP, DPparaPixel(96));
                        lay_listaSet.connect(link3.getId(), ConstraintSet.LEFT,
                                lay_lojas.getId(), ConstraintSet.LEFT, DPparaPixel(272));
                        lay_listaSet.applyTo(lay_lojas);
                    }catch(JSONException e){
                        new Testador(act).Dialogo_Simples("Erro: " + e.getMessage());
                    }
                }
            };
            conexao.lojaModelo(vendoLoja,null,modelo);
        }catch (Exception e){
        }
    }
    public void compararProdutos(String[] modelos,String tabela, ConstraintLayout lay_tabela, WebConn conexao){
        lay_tabela.removeAllViews();
        if(modelos[0].equals("") && modelos[1].equals("") && modelos[2].equals("")){
            return;
        }

        try {
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    new Testador(act).Dialogo_Simples("Erro de Servidor: " + response.toString());
                }else {
                    try {
                        //CRIAR KEYS E VALUES
                        JSONArray iter = new JSONArray(response.toString());
                        int cont = 0;
                        int margem_altura = DPparaPixel(8);

                        while(cont < iter.length()){
                            JSONObject linha = iter.getJSONObject(cont);
                            ConstraintSet lay_listaSet = new ConstraintSet();

                            int[] larguras = {0, 100, 200, 300};
                            //coluna
                            TextView coluna = new TextView(act);
                            coluna.setText(linha.getString("coluna") + ':');
                            coluna.setHeight(DPparaPixel(96));
                            coluna.setWidth(DPparaPixel(100));
                            coluna.setTextColor(act.getColor(R.color.tema));
                            coluna.setId(View.generateViewId());
                            coluna.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            coluna.setGravity(Gravity.CENTER);
                            bordaTXV(coluna);
                            lay_tabela.addView(coluna);
                            //modelo1
                            TextView modelo1 = new TextView(act);
                            if (linha.getString("modelo1").length() >= 1) {
                                modelo1.setText(linha.getString("modelo1"));
                                modelo1.setHeight(DPparaPixel(96));
                                modelo1.setWidth(DPparaPixel(100));
                                modelo1.setTextColor(act.getColor(R.color.tema));
                                bordaTXV(modelo1);
                                modelo1.setId(View.generateViewId());
                                modelo1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                modelo1.setGravity(Gravity.CENTER);
                                lay_tabela.addView(modelo1);
                            }else {
                                larguras[2] = 100;
                                larguras[3] = 200;
                            }
                            //modelo2
                            TextView modelo2 = new TextView(act);
                            if (linha.getString("modelo2").length() >= 1) {
                                modelo2.setText(linha.getString("modelo2"));
                                modelo2.setHeight(DPparaPixel(96));
                                modelo2.setWidth(DPparaPixel(100));
                                modelo2.setTextColor(act.getColor(R.color.tema));
                                modelo2.setId(View.generateViewId());
                                bordaTXV(modelo2);
                                modelo2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                modelo2.setGravity(Gravity.CENTER);
                                lay_tabela.addView(modelo2);
                            }else if (linha.getString("modelo1").length() >= 1){
                                larguras[3] = 200;
                            }else {
                                larguras[3] = 100;
                            }
                            //modelo3
                            TextView modelo3 = new TextView(act);
                            if (linha.getString("modelo3").length() >= 1) {
                                modelo3.setText(linha.getString("modelo3"));
                                modelo3.setHeight(DPparaPixel(96));
                                modelo3.setWidth(DPparaPixel(100));
                                modelo3.setTextColor(act.getColor(R.color.tema));
                                modelo3.setId(View.generateViewId());
                                bordaTXV(modelo3);
                                modelo3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                modelo3.setGravity(Gravity.CENTER);
                                lay_tabela.addView(modelo3);
                            }

                            lay_listaSet.clone(lay_tabela);
                            //Coluna
                            lay_listaSet.connect(coluna.getId(), ConstraintSet.TOP,
                                    lay_tabela.getId(), ConstraintSet.TOP, margem_altura);
                            lay_listaSet.connect(coluna.getId(), ConstraintSet.LEFT,
                                    lay_tabela.getId(), ConstraintSet.LEFT, DPparaPixel(larguras[0]));
                            //Modelo1
                            lay_listaSet.connect(modelo1.getId(), ConstraintSet.TOP,
                                    lay_tabela.getId(), ConstraintSet.TOP, margem_altura);
                            lay_listaSet.connect(modelo1.getId(), ConstraintSet.LEFT,
                                    lay_tabela.getId(), ConstraintSet.LEFT, DPparaPixel(larguras[1]));
                            act.findViewById(R.id.img_remover1).setX(DPparaPixel(larguras[1]+60));
                            act.findViewById(R.id.img_remover1).requestLayout();
                            //Modelo1
                            lay_listaSet.connect(modelo2.getId(), ConstraintSet.TOP,
                                    lay_tabela.getId(), ConstraintSet.TOP, margem_altura);
                            lay_listaSet.connect(modelo2.getId(), ConstraintSet.LEFT,
                                    lay_tabela.getId(), ConstraintSet.LEFT, DPparaPixel(larguras[2]));
                            act.findViewById(R.id.img_remover2).setX(DPparaPixel(larguras[2]+60));
                            act.findViewById(R.id.img_remover2).requestLayout();
                            //Modelo1
                            lay_listaSet.connect(modelo3.getId(), ConstraintSet.TOP,
                                    lay_tabela.getId(), ConstraintSet.TOP, margem_altura);
                            lay_listaSet.connect(modelo3.getId(), ConstraintSet.LEFT,
                                    lay_tabela.getId(), ConstraintSet.LEFT, DPparaPixel(larguras[3]));
                            act.findViewById(R.id.img_remover3).setX(DPparaPixel(larguras[3]+60));
                            act.findViewById(R.id.img_remover3).requestLayout();
                            //
                            lay_listaSet.applyTo(lay_tabela);
                            margem_altura += DPparaPixel(96);
                            cont++;
                        }

                    } catch (JSONException e) {
                        new Testador(act).Dialogo_Simples("Erro1: " + e.getMessage());
                    }
                }
            };
            conexao.compararProdutos(resposta,null,tabela,modelos);
        }catch (Exception e){
            new Testador(act).Dialogo_Simples("Erro2: " + e.getMessage());
        }
    }
    public void imagemTrocar(ImageView IMGview,String modelo){

    }
    public void bordaTXV(TextView txv){
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(2);
        gd.setStroke(2, act.getResources().getColor(R.color.tema,act.getTheme()));
        txv.setBackground(gd);
    }
    public void exibirChatBot(){

    }
    public void esconderChatBot(){

    }
}
