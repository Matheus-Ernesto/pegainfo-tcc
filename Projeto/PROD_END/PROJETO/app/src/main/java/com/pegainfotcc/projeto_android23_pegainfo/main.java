package com.pegainfotcc.projeto_android23_pegainfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class main extends AppCompatActivity {

    WebConn conector;
    Testador alerta;
    Utilidades util;

    SharedPreferences sharedPref;
    SharedPreferences.Editor SPEditor;
    String bckComando = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testarConexao();
    }

    @Override
    public void onBackPressed(){
        switch (bckComando){
            case "home":
                TelaPrincipal();
                break;
            case "comparar":
                TelaPrincipalDef();
                util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_comparar);
                PrincipalComparar();
                break;
            case "menu":
                TelaPrincipalDef();
                util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_menu);
                PrincipalMenu();
                break;
            case "conta":
                TelaPrincipalDef();
                util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_conta);
                PrincipalConta();
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    public void testarConexao(){
        setContentView(R.layout.tela_carregando);

        alerta = new Testador(this);
        conector = new WebConn(this);
        util = new Utilidades(this);

        sharedPref = getPreferences(MODE_PRIVATE);
        SPEditor = sharedPref.edit();
        SPEditor.apply();

        Response.Listener resposta = response -> {
            if(response.toString().equals("Sucesso")){
                Login();
            }else {
                alerta.Popup("Erro: Servidor Desconfigurado",true);
            }
        };

        Response.ErrorListener erro = error -> {
            setContentView(R.layout.tela_sem_conexao);
            findViewById(R.id.btn_Recarregar).setOnClickListener(v -> testarConexao());
        };
        conector.testar(resposta, erro);
    }

    public void Login(){
        if(!sharedPref.getString("SAVED_EMAIL","").equals("")
                && !sharedPref.getString("SAVED_SENHA","").equals("")){
            TelaPrincipal();
        }
        else {
            setContentView(R.layout.tela_login);

            findViewById(R.id.btn_xml_EntrarSemConta).setOnClickListener(v -> TelaPrincipal());
            View.OnClickListener Clicado_ComConta_Cadastrar = v -> {
                EditText edt_email = findViewById(R.id.edt_xml_Email);
                EditText edt_senha = findViewById(R.id.edt_xml_Senha);
                String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();

                Response.Listener resposta = response -> {
                    try {
                        String status_result = new
                                JSONObject(response.toString()).getString("status");
                        String nome_result = new
                                JSONObject(response.toString()).getString("nome");

                        switch (status_result) {
                            case "Logado":
                                alerta.Popup("Seja bem-vindo, " + nome_result,
                                        false);
                                SPEditor.putString("SAVED_EMAIL",email);
                                SPEditor.putString("SAVED_SENHA",senha);
                                SPEditor.apply();
                                TelaPrincipal();
                                break;
                            case "Tem conta, senha incorreta":
                                alerta.Popup("Conta existente, porém senha incorreta",
                                        false);
                                break;
                            case "Cadastrado":
                                alerta.Popup("Conta cadastrada, seja bem-vindo",
                                        false);
                                SPEditor.putString("SAVED_EMAIL",email);
                                SPEditor.putString("SAVED_SENHA",senha);
                                SPEditor.apply();
                                TelaPrincipal();
                                break;
                            default:
                                alerta.Popup("Aviso: " + status_result, false);
                                break;
                        }
                    } catch (JSONException e) {
                        alerta.Popup(e.getMessage(),false);
                    }
                };

                if(email.equals("") && senha.equals(""))
                {
                    alerta.Popup("Preencha o formulário antes de entrar",false);
                }
                else if(email.equals(""))
                {
                    alerta.Popup("Preencha o email antes entrar",false);
                }
                else if(senha.equals(""))
                {
                    alerta.Popup("Preencha o senha antes entrar",false);
                }
                else if(!email.contains("@") && !email.contains(".") && email.length() <= 4)
                {
                    alerta.Popup("Utilize um email válido", false);
                }
                else
                {
                    if(v == findViewById(R.id.btn_xml_Cadastrar)){
                        conector.cadastrar(resposta,null,email,senha);
                    } else {
                        conector.logar(resposta,null,email,senha);
                    }

                }
            };

            findViewById(R.id.btn_xml_EntrarComConta).setOnClickListener(Clicado_ComConta_Cadastrar);
            findViewById(R.id.btn_xml_Cadastrar).setOnClickListener(Clicado_ComConta_Cadastrar);
        }
    }

    public void TelaPrincipal(){
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal);

        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_home);


        TabLayout tabsLayt = findViewById(R.id.tab_menus);
        tabsLayt.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 1:
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_menu);
                        PrincipalMenu();
                        break;
                    case 2:
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_comparar);
                        PrincipalComparar();
                        break;
                    case 3:
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_sobre);
                        PrincipalSobre();
                        break;
                    case 4:
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_conta);
                        PrincipalConta();
                        break;
                    default:
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_home);
                        PrincipalHome();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_home);
                    PrincipalHome();
                }else if(tab.getPosition() == 1){
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_menu);
                    PrincipalMenu();
                }else if(tab.getPosition() == 2){
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_comparar);
                    PrincipalComparar();
                }else if(tab.getPosition() == 3){
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_sobre);
                    PrincipalSobre();
                }else if(tab.getPosition() == 4){
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_conta);
                    PrincipalConta();
                }
            }
        });
        PrincipalHome();
        TelaPesquisar();
    }
    public void TelaPrincipalDef(){
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal);

        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_home);

        TabLayout tabsLayt = findViewById(R.id.tab_menus);
        tabsLayt.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 1:
                        conector.cancelarConexoes(0);
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_menu);
                        PrincipalMenu();
                        break;
                    case 2:
                        conector.cancelarConexoes(0);
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_comparar);
                        PrincipalComparar();
                        break;
                    case 3:
                        conector.cancelarConexoes(0);
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_sobre);
                        PrincipalSobre();
                        break;
                    case 4:
                        conector.cancelarConexoes(0);
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_conta);
                        PrincipalConta();
                        break;
                    default:
                        conector.cancelarConexoes(0);
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_home);
                        PrincipalHome();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    conector.cancelarConexoes(0);
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_home);
                    PrincipalHome();
                }else if(tab.getPosition() == 1){
                    conector.cancelarConexoes(0);
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_menu);
                    PrincipalMenu();
                }else if(tab.getPosition() == 2){
                    conector.cancelarConexoes(0);
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_comparar);
                    PrincipalComparar();
                }else if(tab.getPosition() == 3){
                    conector.cancelarConexoes(0);
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_sobre);
                    PrincipalSobre();
                }else if(tab.getPosition() == 4){
                    conector.cancelarConexoes(0);
                    util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_conta);
                    PrincipalConta();
                }
            }
        });
        TelaPesquisar();
    }

    public void PrincipalHome(){
        bckComando = "";
        conector.cancelarConexoes(0);
        Response.Listener resposta = response -> {
            if (response.toString().startsWith("sqlError")){
                alerta.Popup("Erro:" + response.toString(),true);
            }else {
                util.listaJanela(response,
                        findViewById(R.id.scrlLay_Novos_Produtos), 1, v -> {
                    ImageView modeloImagem = findViewById(v.getId());
                    String modeloTexto = modeloImagem.getContentDescription().toString();
                    bckComando = "home";
                    util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                });
            }
        };
        conector.pesquisar(resposta,null,"novos");

        Response.Listener resposta_2 = response -> {
            if (response.toString().startsWith("sqlError")){
                alerta.Popup("Erro:" + response.toString(),true);
            }else {
                util.listaJanela(response,
                        findViewById(R.id.scrlLay_Mais_Populares), 1, v -> {
                            ImageView modeloImagem = findViewById(v.getId());
                            String modeloTexto = modeloImagem.getContentDescription().toString();
                            bckComando = "home";
                            util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                });
            }
        };
        conector.pesquisar(resposta_2,null, "populares");

        findViewById(R.id.imv_Banner_Home).setOnClickListener(v -> {
            PrincipalMontar();
        });
    }

    public void PrincipalMenu(){
        conector.cancelarConexoes(0);
        ImageView.OnClickListener menuSelect = v -> {
            bckComando = "menu";
            String texto = "";
            switch (v.getId()){
                case R.id.img_xml_Celulares:
                    texto = "d_celulares";
                    break;
                case R.id.img_xml_Tablets:
                    texto = "d_tablets";
                    break;
                case R.id.img_xml_Notebooks:
                    texto = "d_notebooks";
                    break;
                case R.id.img_xml_Pacotes:
                    texto = "d_desktops";
                    break;
                default:
                    texto = "d_celulares";
                    break;
            }
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Popup("Erro:" + response.toString(),true);
                }else {
                    util.listaJanela(response, findViewById(R.id.lay_janela), 0, v1 -> {
                        ImageView modeloImagem = findViewById(v1.getId());
                        String modeloTexto = modeloImagem.getContentDescription().toString();
                        util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                    });
                }
            };
            conector.pesquisar(resposta,null,texto);
        };

        findViewById(R.id.img_xml_Celulares).setOnClickListener(menuSelect);
        findViewById(R.id.img_xml_Tablets).setOnClickListener(menuSelect);
        findViewById(R.id.img_xml_Notebooks).setOnClickListener(menuSelect);
        findViewById(R.id.img_xml_Pacotes).setOnClickListener(menuSelect);
        findViewById(R.id.img_xml_Favoritos).setOnClickListener(v -> {
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Popup("Erro:" + response.toString(),true);
                }else {
                    util.listaJanela(response, findViewById(R.id.lay_janela), 0, v1 -> {
                        ImageView modeloImagem = findViewById(v1.getId());
                        String modeloTexto = modeloImagem.getContentDescription().toString();
                        util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                    });
                }
            };
            Map<String, String> dados = new HashMap<>();
            dados.put("comando","verFavoritos");
            dados.put("email",sharedPref.getString("SAVED_EMAIL",""));

            conector.conectarPOST(resposta, dados);

        });
        findViewById(R.id.img_xml_Setups).setOnClickListener(v -> {
            conector.cancelarConexoes(0);
            Response.Listener respostaX = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Popup("Erro:" + response.toString(),true);
                }else {
                    bckComando = "menu";
                    util.listaJanela(response, findViewById(R.id.lay_janela), 2, v1 -> {
                        ImageView modeloImagem = findViewById(v1.getId());
                        String modeloTexto = modeloImagem.getContentDescription().toString();
                        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_produto);
                        Response.Listener resposta2 = response2 -> {
                            if (response2.toString().startsWith("sqlError")) {
                                alerta.Dialogo_Simples("Erro de Servidor");
                            } else {
                                try {
                                    JSONObject texto_json = new JSONArray(
                                            response2.toString()).getJSONObject(0);
                                    TextView txv_modeloTexto = findViewById(R.id.txv_TituloModelo);
                                    txv_modeloTexto.setText(texto_json.getString("Modelo"));
                                    TextView anoTexto = findViewById(R.id.txv_Ano);
                                    anoTexto.setText(texto_json.getString("Ano"));
                                    ConstraintLayout lay_dados = findViewById(R.id.lay_Especificacao);

                                    findViewById(R.id.img_favoritar).setVisibility(View.GONE);

                                    conector.baixarImagem("Setup",
                                            findViewById(R.id.img_produtoUnico));

                                    //CRIAR KEYS E VALUES
                                    JSONArray iter = new JSONArray(response2.toString());
                                    int cont = 1;
                                    int margem_altura = util.DPparaPixel(8);

                                    while (cont < iter.length()) {
                                        JSONObject linha = iter.getJSONObject(cont);
                                        ConstraintSet lay_listaSet = new ConstraintSet();
                                        int altura;
                                        if(linha.has("dado") || !linha.isNull("dado")){
                                            altura = (
                                                    linha.getString("dado").length()
                                                            >= 25) ? 64 : 48;
                                        }else {
                                            altura = 48;
                                        }

                                        //KEY
                                        TextView produtoKey = new TextView(this);
                                        produtoKey.setText(linha.getString("coluna") + ':');
                                        produtoKey.setHeight(util.DPparaPixel(altura));
                                        produtoKey.setWidth(util.DPparaPixel(200));
                                        produtoKey.setTextColor(getColor(R.color.tema));
                                        produtoKey.setId(View.generateViewId());
                                        util.bordaTXV(produtoKey);
                                        produtoKey.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        produtoKey.setGravity(Gravity.CENTER);
                                        lay_dados.addView(produtoKey);
                                        //VALUE
                                        TextView produtoValue = new TextView(this);
                                        if(linha.has("dado") || !linha.isNull("dado")){
                                                produtoValue.setText(linha.getString("dado"));
                                        }else{
                                            produtoValue.setText("--");
                                        }
                                        produtoValue.setHeight(util.DPparaPixel(altura));
                                        produtoValue.setWidth(util.DPparaPixel(200));
                                        produtoValue.setTextColor(getColor(R.color.tema));
                                        produtoValue.setId(View.generateViewId());
                                        util.bordaTXV(produtoValue);
                                        produtoValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        produtoValue.setGravity(Gravity.CENTER);
                                        lay_dados.addView(produtoValue);

                                        lay_listaSet.clone(lay_dados);
                                        //KEYS
                                        lay_listaSet.connect(produtoKey.getId(), ConstraintSet.TOP,
                                                lay_dados.getId(), ConstraintSet.TOP, margem_altura);
                                        lay_listaSet.connect(produtoKey.getId(), ConstraintSet.LEFT,
                                                lay_dados.getId(), ConstraintSet.LEFT,
                                                util.DPparaPixel(0));
                                        //VALUES
                                        lay_listaSet.connect(produtoValue.getId(), ConstraintSet.TOP,
                                                lay_dados.getId(), ConstraintSet.TOP, margem_altura);
                                        lay_listaSet.connect(produtoValue.getId(), ConstraintSet.LEFT,
                                                lay_dados.getId(), ConstraintSet.LEFT,
                                                util.DPparaPixel(200));
                                        //
                                        lay_listaSet.applyTo(lay_dados);
                                        margem_altura += util.DPparaPixel(altura);

                                        cont++;
                                    }

                                } catch (JSONException e) {
                                    alerta.Popup(e.getMessage(), false);
                                }
                            }
                        };
                        conector.verSetupUnico(resposta2,null,modeloTexto);
                    });
                }
            };
            conector.verSetup(respostaX,null,sharedPref.getString("SAVED_EMAIL",""));
        });
        findViewById(R.id.img_xml_Computadores).setOnClickListener(v -> {
            bckComando = "menu";
            util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_menu_computadores);
            PrincipalComputadores();
        });
    }

    public void PrincipalComputadores(){
        conector.cancelarConexoes(0);
        View.OnClickListener menuPCSelect = v -> {
            String texto = "";
            switch (v.getId()){
                case R.id.img_xml_menu_gabinetes:
                    texto = "p_gabinete";
                    break;
                case R.id.img_xml_menu_memoria:
                    texto = "p_memoria_armaz";
                    break;
                case R.id.img_xml_menu_memoria_Ram:
                    texto = "p_memoria_ram";
                    break;
                case R.id.img_xml_menu_placa_mae:
                    texto = "p_placa_mae";
                    break;
                case R.id.img_xml_menu_processador:
                    texto = "p_processador";
                    break;
                case R.id.img_xml_menu_placa_video:
                    texto = "p_placa_de_video";
                    break;
                default:
                    texto = "p_gabinete";
                    break;
            }

            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Popup("Erro:" + response.toString(),true);
                }else {
                    ConstraintLayout lay_jan = findViewById(R.id.lay_janela);
                    lay_jan.removeAllViews();
                    util.listaJanela(response, findViewById(R.id.lay_janela), 0, v1 -> {
                        ImageView modeloImagem = findViewById(v1.getId());
                        String modeloTexto = modeloImagem.getContentDescription().toString();
                        bckComando = "menu";
                        util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                    });
                }
            };
            conector.pesquisar(resposta,null,texto);
        };

        findViewById(R.id.img_xml_menu_gabinetes).setOnClickListener(menuPCSelect);
        findViewById(R.id.img_xml_menu_memoria).setOnClickListener(menuPCSelect);
        findViewById(R.id.img_xml_menu_memoria_Ram).setOnClickListener(menuPCSelect);
        findViewById(R.id.img_xml_menu_placa_mae).setOnClickListener(menuPCSelect);
        findViewById(R.id.img_xml_menu_placa_video).setOnClickListener(menuPCSelect);
        findViewById(R.id.img_xml_menu_processador).setOnClickListener(menuPCSelect);

    }

    public void PrincipalComparar(){
        bckComando = "";
        conector.cancelarConexoes(0);
        findViewById(R.id.btn_Comparar).setOnClickListener(v -> PrincipalCompararSelect());
    }

    public void PrincipalCompararSelect(){
        bckComando = "comparar";
        conector.cancelarConexoes(0);
        Spinner spinCategorias = findViewById(R.id.spin_categorias);
        String categoria = spinCategorias.getSelectedItem().toString();
        String tabela = "";

        switch (categoria){
            case "Celulares":
                tabela = "d_celulares";
                break;
            case "Tablets":
                tabela = "d_tablets";
                break;
            case "Gabinetes":
                tabela = "p_gabinete";
                break;
            case "Memórias de Armazenamento":
                tabela = "p_memoria_armaz";
                break;
            case "Placas-Mãe":
                tabela = "p_placa_mae";
                break;
            case "Processador":
                tabela = "p_processador";
                break;
            case "Memórias RAM":
                tabela = "p_memoria_ram";
                break;
            case "Placas de vídeo":
                tabela = "p_placa_de_video";
                break;
            case "Notebooks":
                tabela = "d_notebooks";
                break;
            default:
                tabela = "d_celulares";
                break;
        }

        util.trocaJanela(findViewById(R.id.lay_janela),R.layout.tela_principal_comparar_select);

        Spinner spinner = findViewById(R.id.spin_produtos);
        String[] modelosCompararPOST = {"","",""};
        String finalTabela = tabela;
        Button btnAtualizar = findViewById(R.id.btn_Plus);

        View.OnClickListener removerColuna = v -> {
            int x = Character.getNumericValue(v.getContentDescription().charAt(7));
            if(x == 1){
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < spinner.getCount(); i++) {
                    arrayList.add(spinner.getItemAtPosition(i).toString());
                }
                arrayList.add(modelosCompararPOST[0]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                modelosCompararPOST[0] = "";
                util.compararProdutos(modelosCompararPOST, finalTabela,
                        findViewById(R.id.lay_tabela), conector);
            }else if(x == 2){
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < spinner.getCount(); i++) {
                    arrayList.add(spinner.getItemAtPosition(i).toString());
                }
                arrayList.add(modelosCompararPOST[1]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                modelosCompararPOST[1] = "";
                util.compararProdutos(modelosCompararPOST, finalTabela,
                        findViewById(R.id.lay_tabela), conector);
            }else{
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < spinner.getCount(); i++) {
                    arrayList.add(spinner.getItemAtPosition(i).toString());
                }
                arrayList.add(modelosCompararPOST[2]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                modelosCompararPOST[2] = "";
                util.compararProdutos(modelosCompararPOST, finalTabela,
                        findViewById(R.id.lay_tabela), conector);
            }
            if(modelosCompararPOST[0].equals("")){
                findViewById(R.id.img_remover1).setVisibility(View.INVISIBLE);
            }if(modelosCompararPOST[1].equals("")){
                findViewById(R.id.img_remover2).setVisibility(View.INVISIBLE);
            }if(modelosCompararPOST[2].equals("")){
                findViewById(R.id.img_remover3).setVisibility(View.INVISIBLE);
            }
        };

        findViewById(R.id.img_remover1).setOnClickListener(removerColuna);
        findViewById(R.id.img_remover1).setVisibility(View.INVISIBLE);
        findViewById(R.id.img_remover2).setOnClickListener(removerColuna);
        findViewById(R.id.img_remover2).setVisibility(View.INVISIBLE);
        findViewById(R.id.img_remover3).setOnClickListener(removerColuna);
        findViewById(R.id.img_remover3).setVisibility(View.INVISIBLE);

        btnAtualizar.setOnClickListener(v -> {
            if(spinner.getItemAtPosition(
                    spinner.getSelectedItemPosition()).toString().equals("<-Selecione->")){
                alerta.Popup("Selecione um produto antes",true);
            }else {
                if (modelosCompararPOST[0].toString().length() <= 2) {
                    modelosCompararPOST[0] = spinner.getSelectedItem().toString();
                    findViewById(R.id.img_remover1).setVisibility(View.VISIBLE);
                } else if (modelosCompararPOST[1].toString().length() <= 2) {
                    modelosCompararPOST[1] = spinner.getSelectedItem().toString();
                    findViewById(R.id.img_remover2).setVisibility(View.VISIBLE);
                } else if (modelosCompararPOST[2].toString().length() <= 2) {
                    modelosCompararPOST[2] = spinner.getSelectedItem().toString();
                    findViewById(R.id.img_remover3).setVisibility(View.VISIBLE);
                } else {
                    alerta.Popup("Retire antes algum modelo (Máximo de 3 modelos para comparar)", false);
                    return;
                }
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < spinner.getCount(); i++) {
                    if (i == spinner.getSelectedItemPosition()) {
                        continue;
                    }
                    arrayList.add(spinner.getItemAtPosition(i).toString());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(arrayAdapter);
                util.compararProdutos(modelosCompararPOST, finalTabela,
                        findViewById(R.id.lay_tabela), conector);
            }
        });

        Response.Listener resposta = response -> {
            try{
                if(response.toString().length() <= 5){
                    alerta.Popup("Sem produtos para comparar",true);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add("Sem produtos");
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                            R.layout.spinner_item, arrayList);
                    arrayAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    btnAtualizar.setOnClickListener(v ->
                            alerta.Popup("Sem produtos para buscar",true));
                }else {
                    JSONArray lista = new JSONArray(response.toString());
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add("<-Selecione->");
                    for(int i = 0;i < lista.length();i++){
                        arrayList.add(lista.getJSONObject(i).getString("modelo"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                            R.layout.spinner_item, arrayList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                }

            } catch(JSONException e){
                alerta.Popup(e.getMessage(),false);
            }
        };
        conector.pesquisar(resposta,null,tabela);
    }

    public void PrincipalSobre(){
        bckComando = "home";
        conector.cancelarConexoes(0);
        View.OnClickListener imagemRetratil = v -> {
            ImageView imagem = (ImageView) v;
            if(imagem.getElevation() == util.DPparaPixel(-1)){
                imagem.setElevation(util.DPparaPixel(2));
                imagem.clearColorFilter();
            }else {
                imagem.setElevation(util.DPparaPixel(-1));
                imagem.setColorFilter(getColor(R.color.black_nine), PorterDuff.Mode.DARKEN);
            }
            v.requestLayout();
        };
        View.OnClickListener abrirLink = v -> {
            if(v.getId() == R.id.btn_Sobre_Processador){
                conector.abrirLINK("https://tecnoblog.net/384499/o-que-e-um-processador/");
            }else if(v.getId() == R.id.btn_Sobre_Ram){
                conector.abrirLINK("https://www.techtudo.com.br/noticias/2012/02/o-que-e-memoria-ram-e-qual-sua-funcao.ghtml");
            }else if(v.getId() == R.id.btn_Sobre_Ssd){
                conector.abrirLINK("https://www.zoom.com.br/notebook/deumzoom/o-que-e-ssd");
            }else if(v.getId() == R.id.btn_Sobre_HD){
                conector.abrirLINK("https://www.zoom.com.br/pc-computador/deumzoom/o-que-e-um-hd");
            }else if(v.getId() == R.id.btn_Sobre_Fonte){
                conector.abrirLINK("https://www.tecmundo.com.br/fonte/20987-o-que-e-uma-fonte-de-energia-e-como-ela-e-por-dentro.htm");
            }else if(v.getId() == R.id.btn_Sobre_PlacaMae){
                conector.abrirLINK("https://kazuk.com.br/blog/o-que-e-a-placa-mae/");
            }
        };
        findViewById(R.id.imgv_Sobre_Processador).setOnClickListener(imagemRetratil);
        findViewById(R.id.imgv_Sobre_Ram).setOnClickListener(imagemRetratil);
        findViewById(R.id.imgv_Sobre_Ssd).setOnClickListener(imagemRetratil);
        findViewById(R.id.imgv_Sobre_HD).setOnClickListener(imagemRetratil);
        findViewById(R.id.imgv_Sobre_Fonte).setOnClickListener(imagemRetratil);
        findViewById(R.id.imgv_Sobre_PlacaMae).setOnClickListener(imagemRetratil);
        findViewById(R.id.btn_Sobre_HD).setOnClickListener(abrirLink);
        findViewById(R.id.btn_Sobre_Fonte).setOnClickListener(abrirLink);
        findViewById(R.id.btn_Sobre_PlacaMae).setOnClickListener(abrirLink);
        findViewById(R.id.btn_Sobre_Processador).setOnClickListener(abrirLink);
        findViewById(R.id.btn_Sobre_Ram).setOnClickListener(abrirLink);
        findViewById(R.id.btn_Sobre_Ssd).setOnClickListener(abrirLink);
    }

    public void PrincipalConta(){
        conector.cancelarConexoes(0);
        if(sharedPref.getString("SAVED_EMAIL", "").length() >= 2){

            findViewById(R.id.btn_MudarNomeOutros).setOnClickListener(v -> PrincipalContaMudarDados());
            findViewById(R.id.btn_TrocarConta).setOnClickListener(v -> PrincipalContaTrocarConta());
            findViewById(R.id.btn_Deletar).setOnClickListener(v -> PrincipalContaDeletar());

        }else {
            alerta.Popup("Entre antes para ter uma conta e poder edita-la.",false);
            findViewById(R.id.btn_MudarNomeOutros).setEnabled(false);
            findViewById(R.id.btn_TrocarConta).setOnClickListener(v -> Login());
            findViewById(R.id.btn_Deletar).setEnabled(false);
        }
        findViewById(R.id.btn_Juridico).setOnClickListener(v -> PrincipalContaJuridico());
    }

    public void PrincipalContaMudarDados(){
        bckComando = "conta";
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_conta_mudardados);

        EditText NomeEdt = findViewById(R.id.edt_SobreNome);
        EditText EmailEdt = findViewById(R.id.edt_SobreEmail);

        Response.Listener resposta = response -> {
            try {
                JSONObject Resultado = new JSONObject(response.toString());
                NomeEdt.setText(Resultado.getString("nome"));
                EmailEdt.setText(Resultado.getString("email"));
            } catch (JSONException e) {
                alerta.Dialogo_Simples("Erro: " + e.getMessage());
            }
        };
        conector.coletarConta(resposta,null,sharedPref.getString("SAVED_EMAIL",""),
                sharedPref.getString("SAVED_SENHA",""));

        findViewById(R.id.btn_MDMudar).setOnClickListener(v -> {
            Response.Listener resposta1 = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Popup("Este email já está em uso",true);
                }else {
                    alerta.Popup("Dados modificados", false);
                    SPEditor.putString("SAVED_EMAIL", EmailEdt.getText().toString());
                    SPEditor.apply();
                    TelaPrincipal();
                }
            };

            conector.mudarDados(resposta1, null,
                    NomeEdt.getText().toString(),
                    EmailEdt.getText().toString(),
                    sharedPref.getString("SAVED_EMAIL",""),
                    sharedPref.getString("SAVED_SENHA","")
            );

        });
    }

    public void PrincipalContaDeletar(){
        bckComando = "conta";
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_conta_deletar);

        EditText EmailEdt = findViewById(R.id.edtv_Deletar_Email);
        EditText SenhaEdt = findViewById(R.id.edtv_Deletar_Senha);

        findViewById(R.id.btn_Deletar_Confirmar_Conta).setOnClickListener(v -> {
            if(EmailEdt.getText().toString().equals(sharedPref.getString("SAVED_EMAIL",""))
                    && SenhaEdt.getText().toString().equals(sharedPref.getString("SAVED_SENHA",""))){
                Response.Listener resposta = response -> {
                    try {
                        JSONObject resultado = new JSONObject(response.toString());
                        if(resultado.getString("Resultado").equals("Conta deletada")){
                            alerta.Popup("Deletado",false);
                            SPEditor.putString("SAVED_EMAIL","");
                            SPEditor.putString("SAVED_SENHA","");
                            SPEditor.apply();
                            Login();
                        }else {
                            alerta.Dialogo_Simples("Erro: " + resultado.getString("Resultado"));
                        }

                    } catch (JSONException e) {
                        alerta.Dialogo_Simples("Erro: " + e.getMessage());
                    }

                };
                conector.deletarConta(resposta, null,
                        sharedPref.getString("SAVED_EMAIL",""),
                        sharedPref.getString("SAVED_SENHA",""));

            }
            else {
                alerta.Popup("Preencha os campos com a conta " +
                                "e senha da conta logada atualmente", false);
            }

        });
    }
    public void PrincipalContaJuridico(){
        bckComando = "conta";
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_conta_juridico);
    }
    public void PrincipalContaTrocarConta(){
        bckComando = "";
        conector.cancelarConexoes(0);
        SPEditor.putString("SAVED_EMAIL","");
        SPEditor.putString("SAVED_SENHA","");
        SPEditor.apply();
        Login();
    }
    public void TelaPesquisar(){
        findViewById(R.id.imgv_pesquisar).setOnClickListener(v -> {
            ConstraintLayout janela = findViewById(R.id.lay_janela);
            EditText edtPesquisa = findViewById(R.id.edt_Pesquisa);
            if(edtPesquisa.getText().length() >= 2){
                Response.Listener respostaPesquisa = response -> {
                    if (response.toString().startsWith("sqlError")){
                        alerta.Popup("Erro: " + response.toString(),true);
                    }else {
                        if(response.toString().length() >= 8){
                            janela.removeAllViews();
                            util.listaJanela(response, janela, 0, v1 -> {
                                ImageView modeloImagem = findViewById(v1.getId());
                                String modeloTexto =
                                        modeloImagem.getContentDescription().toString();
                                util.produtoUnico(modeloTexto,
                                        findViewById(R.id.lay_janela),conector);
                            });
                        }else {
                            alerta.Popup("sem resultados...",true);
                        }
                    }
                };
                conector.pesquisarTudo(respostaPesquisa, null, edtPesquisa.getText().toString());
            }else {
                alerta.Popup("Escreva pelo menos 2 letras para pesquisar um produto",
                        true);
            }
        });
    }
    public void PrincipalMontar(){
        bckComando = "home";
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_home_montar);

        findViewById(R.id.btn_tipoMontar).setOnClickListener(v -> {
            Spinner spinTipoMontar = findViewById(R.id.spin_tipoMontar);
            String escolha = spinTipoMontar.getSelectedItem().toString();

            switch(escolha){
                case "Celulares":
                    PrincipalMontarCelulares();
                    break;
                case "Tablets":
                    PrincipalMontarTablets();
                    break;
                case "Computadores":
                    PrincipalMontarComputadores();
                    break;
                case "Notebooks":
                    PrincipalMontarNotebooks();
                    break;
                default:
                    PrincipalMontarCelulares();
                    break;
            }
        });
    }
    public void PrincipalMontarCelulares(){
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_home_montar_celulares);
        String[] argumentos = {""};
        conector.montarDispositivo(response -> {
            if (response.toString().startsWith("sqlError")){
                alerta.Dialogo_Simples("Erro de servidor: " + response.toString());
            }else {
                ConstraintLayout lay_montagem = findViewById(R.id.scrlLay_produtos_Montagem_celulares);
                lay_montagem.removeAllViews();
                util.listaJanela(response,
                        lay_montagem, 1, v6 -> {
                            ImageView modeloImagem = findViewById(v6.getId());
                            String modeloTexto = modeloImagem.getContentDescription().toString();
                            TelaPrincipalDef();
                            bckComando = "home";
                            util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                        });
            }},null,"d_celulares",
                argumentos[0]
        );

        View.OnClickListener chipClicado = v -> {
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Dialogo_Simples("Erro: " + response.toString());
                }else {
                    ConstraintLayout lay_montagem = findViewById(R.id.scrlLay_produtos_Montagem_celulares);
                    lay_montagem.removeAllViews();
                    util.listaJanela(response,
                            lay_montagem, 1, v1 -> {
                                ImageView modeloImagem = findViewById(v1.getId());
                                String modeloTexto = modeloImagem.getContentDescription().toString();
                                TelaPrincipalDef();
                                bckComando = "home";
                                util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                            });
                }
            };
            Chip chipSelecionado = (Chip) v;
            if (chipSelecionado.isSelected()) {
                argumentos[0] = argumentos[0].replace(
                        chipSelecionado.getText().toString() + "/","");
            } else {
                argumentos[0] += chipSelecionado.getText().toString() + "/";
            }

            conector.montarDispositivo(resposta,null,"d_celulares",
                    argumentos[0]
            );
            chipSelecionado.setSelected(!chipSelecionado.isSelected());
        };
        findViewById(R.id.chip_celulares_bateria).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_jogar).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_tela_grande).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_duradouro).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_fotos).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_instagram).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_whatsapp).setOnClickListener(chipClicado);
        findViewById(R.id.chip_celulares_idoso).setOnClickListener(chipClicado);
    }
    public void PrincipalMontarTablets(){
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_home_montar_tablets);
        String[] argumentos = {""};
        conector.montarDispositivo(response -> {
                    if (response.toString().startsWith("sqlError")){
                        alerta.Dialogo_Simples("Erro de servidor:" + response.toString());
                    }else {
                        ConstraintLayout lay_montagem =
                                findViewById(R.id.scrlLay_produtos_Montagem_tablets);
                        lay_montagem.removeAllViews();
                        util.listaJanela(response,
                                lay_montagem, 1, v1 -> {
                                    ImageView modeloImagem = findViewById(v1.getId());
                                    String modeloTexto = modeloImagem.getContentDescription().toString();
                                    TelaPrincipalDef();
                                    bckComando = "home";
                                    util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                                });
                    }
                },null,"d_tablets",
                argumentos[0]
        );

        View.OnClickListener chipClicado = v -> {
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Popup("Erro de servidor:" + response.toString(),true);
                }else {
                    ConstraintLayout lay_montagem = findViewById(R.id.scrlLay_produtos_Montagem_tablets);
                    lay_montagem.removeAllViews();
                    util.listaJanela(response,
                            lay_montagem, 1, v1 -> {
                                ImageView modeloImagem = findViewById(v1.getId());
                                String modeloTexto = modeloImagem.getContentDescription().toString();
                                TelaPrincipalDef();
                                bckComando = "home";
                                util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                            });
                }
            };
            Chip chipSelecionado = (Chip) v;
            if (chipSelecionado.isSelected()) {
                argumentos[0] = argumentos[0].replace(
                        chipSelecionado.getText().toString() + "/","");
            } else {
                argumentos[0] += chipSelecionado.getText().toString() + "/";
            }

            conector.montarDispositivo(resposta,null,"d_tablets",
                    argumentos[0]
            );
            chipSelecionado.setSelected(!chipSelecionado.isSelected());
        };
        findViewById(R.id.chip_tablets_bateria).setOnClickListener(chipClicado);
        findViewById(R.id.chip_tablets_desenho).setOnClickListener(chipClicado);
        findViewById(R.id.chip_tablets_ligacoes).setOnClickListener(chipClicado);
        findViewById(R.id.chip_tablets_fotos).setOnClickListener(chipClicado);
        findViewById(R.id.chip_tablets_idoso).setOnClickListener(chipClicado);
        findViewById(R.id.chip_tablets_jogos_realistas).setOnClickListener(chipClicado);
        findViewById(R.id.chip_tablets_jogos_simples).setOnClickListener(chipClicado);
    }
    public void PrincipalMontarComputadores(){
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_home_montar_computadores);
        String[] argumentos = {""};

        View.OnClickListener chipClicado = v -> {
            Chip chipSelecionado = (Chip) v;
            if (chipSelecionado.isSelected()) {
                argumentos[0] = argumentos[0].replace(
                        chipSelecionado.getText().toString() + "/","");
            } else {
                argumentos[0] += chipSelecionado.getText().toString() + "/";
            }
            chipSelecionado.setSelected(!chipSelecionado.isSelected());
        };
        findViewById(R.id.chip_desktops_jogos_pesados).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_jogos_simples).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_memoria).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_office).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_ssd).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_duradouro).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_internet).setOnClickListener(chipClicado);
        findViewById(R.id.chip_desktops_idoso).setOnClickListener(chipClicado);

        findViewById(R.id.btn_pc_montar_pecas).setOnClickListener(v -> {
            setContentView(R.layout.tela_principal_menu_computadores_pecas);
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Dialogo_Simples("Erro de servidor: " + response.toString());
                }else {
                    try {
                        JSONObject lista = new JSONObject(response.toString());

                        JSONArray processador = lista.getJSONArray("processador");
                        ArrayList<String> arrayListProcessador = new ArrayList<>();
                        for (int i = 0; i < processador.length(); i++) {
                            arrayListProcessador.add(processador.getString(i));
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.spinner_item, arrayListProcessador);
                        arrayAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item);
                        Spinner spin_processador = findViewById(R.id.spin_montar_processador);
                        spin_processador.setAdapter(arrayAdapter);

                        JSONArray gabinete = lista.getJSONArray("gabinete");
                        ArrayList<String> arrayListGabinete = new ArrayList<>();
                        for (int i = 0; i < gabinete.length(); i++) {
                            arrayListGabinete.add(gabinete.getString(i));
                        }
                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.spinner_item, arrayListGabinete);
                        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner spin_gabinete = findViewById(R.id.spin_montar_gabinete);
                        spin_gabinete.setAdapter(arrayAdapter2);

                        JSONArray placa_mae = lista.getJSONArray("placa_mae");
                        ArrayList<String> arrayListPlacaMae = new ArrayList<>();
                        for (int i = 0; i < placa_mae.length(); i++) {
                            arrayListPlacaMae.add(placa_mae.getString(i));
                        }
                        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.spinner_item, arrayListPlacaMae);
                        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner spin_placa_mae = findViewById(R.id.spin_montar_placa_mae);
                        spin_placa_mae.setAdapter(arrayAdapter3);

                        JSONArray ram = lista.getJSONArray("ram");
                        ArrayList<String> arrayListRam = new ArrayList<>();
                        for (int i = 0; i < ram.length(); i++) {
                            arrayListRam.add(ram.getString(i));
                        }
                        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.spinner_item, arrayListRam);
                        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner spin_ram = findViewById(R.id.spin_montar_ram);
                        spin_ram.setAdapter(arrayAdapter4);

                        JSONArray memoria = lista.getJSONArray("memoria");
                        ArrayList<String> arrayListMemoria = new ArrayList<>();
                        for (int i = 0; i < memoria.length(); i++) {
                            arrayListMemoria.add(memoria.getString(i));
                        }
                        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.spinner_item, arrayListMemoria);
                        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner spin_memoria = findViewById(R.id.spin_montar_memoria);
                        spin_memoria.setAdapter(arrayAdapter5);

                        JSONArray placa_de_video = lista.getJSONArray("placa_de_video");
                        ArrayList<String> arrayListplaca_de_video = new ArrayList<>();
                        for (int i = 0; i < placa_de_video.length(); i++) {
                            arrayListplaca_de_video.add(placa_de_video.getString(i));
                        }
                        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.spinner_item, arrayListplaca_de_video);
                        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner spin_placa_de_video = findViewById(R.id.spin_montar_placa_video);
                        spin_placa_de_video.setAdapter(arrayAdapter6);

                        findViewById(R.id.btn_salvar_montagem).setOnClickListener(v1 -> {
                            if(sharedPref.getString("SAVED_SENHA","").equals("")){
                                alerta.Popup("Você precisa ter uma conta para salvar setups",true);
                            }else {
                                Response.Listener salvando = response1 -> {
                                    if(response1.toString().contains("OK")){
                                        alerta.Popup("Salvo",true);
                                        TelaPrincipal();
                                    }else if(response1.toString().contains("sqlError")){
                                        alerta.Dialogo_Simples("Erro de servidor" + response1.toString());
                                    }else{
                                        alerta.Dialogo_Simples("Erro: " + response1.toString());
                                    }
                                };
                                EditText edtv_nome_setup = findViewById(R.id.edtv_nome_setup);
                                conector.salvarSetup(salvando,null,
                                        sharedPref.getString("SAVED_EMAIL",""),
                                        edtv_nome_setup.getText().toString(),
                                        spin_gabinete.getSelectedItem().toString(),
                                        spin_processador.getSelectedItem().toString(),
                                        spin_memoria.getSelectedItem().toString(),
                                        spin_ram.getSelectedItem().toString(),
                                        spin_placa_mae.getSelectedItem().toString(),
                                        spin_placa_de_video.getSelectedItem().toString());
                            }
                        });
                    } catch (JSONException e) {
                        alerta.Dialogo_Simples("Erro: " + e.getMessage());
                    }
                }
            };
            String argumento_unido = "";
            for (int i=0;i< argumentos.length;i++){
                argumento_unido += argumentos[i] + "/";
            }
            conector.montarComputador(resposta,null,argumento_unido);
        });

    }
    public void PrincipalMontarNotebooks(){
        conector.cancelarConexoes(0);
        setContentView(R.layout.tela_principal_home_montar_notebooks);
        String[] argumentos = {""};
        conector.montarDispositivo(response -> {
                    if (response.toString().startsWith("sqlError")){
                        alerta.Dialogo_Simples("Erro de servidor: " + response.toString());
                    }else {
                        ConstraintLayout lay_montagem =
                                findViewById(R.id.scrlLay_produtos_Montagem_notebooks);
                        lay_montagem.removeAllViews();
                        util.listaJanela(response,
                                lay_montagem, 1, v1 -> {
                                    ImageView modeloImagem = findViewById(v1.getId());
                                    String modeloTexto = modeloImagem.getContentDescription().toString();
                                    TelaPrincipalDef();
                                    bckComando = "home";
                                    util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                                });
                    }
                },null,"d_notebooks",
                argumentos[0]
        );

        View.OnClickListener chipClicado = v -> {
            Response.Listener resposta = response -> {
                if (response.toString().startsWith("sqlError")){
                    alerta.Dialogo_Simples("Erro de servidor: " + response.toString());
                }else {
                    ConstraintLayout lay_montagem = findViewById(R.id.scrlLay_produtos_Montagem_notebooks);
                    lay_montagem.removeAllViews();
                    util.listaJanela(response,
                            lay_montagem, 1, v1 -> {
                                ImageView modeloImagem = findViewById(v1.getId());
                                String modeloTexto = modeloImagem.getContentDescription().toString();
                                TelaPrincipalDef();
                                bckComando = "home";
                                util.produtoUnico(modeloTexto,findViewById(R.id.lay_janela),conector);
                            });
                }
            };
            Chip chipSelecionado = (Chip) v;
            if (chipSelecionado.isSelected()) {
                argumentos[0] = argumentos[0].replace(
                        chipSelecionado.getText().toString() + "/","");
            } else {
                argumentos[0] += chipSelecionado.getText().toString() + "/";
            }

            conector.montarDispositivo(resposta,null,"d_notebooks",
                    argumentos[0]
            );
            chipSelecionado.setSelected(!chipSelecionado.isSelected());
        };
        findViewById(R.id.chip_notebooks_jogos).setOnClickListener(chipClicado);
        findViewById(R.id.chip_notebooks_memoria).setOnClickListener(chipClicado);
        findViewById(R.id.chip_notebooks_ssd).setOnClickListener(chipClicado);
        findViewById(R.id.chip_notebooks_tela).setOnClickListener(chipClicado);
        findViewById(R.id.chip_notebooks_escola).setOnClickListener(chipClicado);
        findViewById(R.id.chip_notebooks_filmes).setOnClickListener(chipClicado);
        findViewById(R.id.chip_notebooks_idoso).setOnClickListener(chipClicado);
    }
}