package br.net.techsoft.techsoftmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.net.techsoft.techsoftmobile.Adapters.ItensVAdapter;
import br.net.techsoft.techsoftmobile.Adapters.ProdutoAdapter;
import br.net.techsoft.techsoftmobile.FTP.Upload;
import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.Util.Util;

public class ProdutosVenda extends ActionBarActivity {

    public ListView listvItensVend;
    ItensVAdapter itensVAdapter;
    Util util = new Util();
    Produto p = new Produto();
    public List<Produto> listaProdutos = new ArrayList<Produto>();
    public List<Produto> listaItens = new ArrayList<Produto>();
    private EditText edtNomeProd, edtQuant, edtValU;
    public TextView edtTItens;
    public AutoCompleteTextView autoCompPro;
    private Button btnReturn, btnAddProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lerTxt();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        edtTItens = (TextView) findViewById(R.id.edtTItens);

        final Cliente cliente = (Cliente) bundle.getSerializable("cliente");
        listaItens = (List<Produto>) bundle.getSerializable("listProd");


///////////////////ListView////////////////////////////////
        listvItensVend = (ListView) findViewById(R.id.listvItensVend);

        if (listaItens != null) {
            itensVAdapter = new ItensVAdapter(this, listaItens);
            listvItensVend.setAdapter(itensVAdapter);
            calcTotalProd(listaItens);
        }


////////////////DECLARACOES//////////////////////////////

        edtNomeProd = (EditText) findViewById(R.id.edtNomeProd);
        edtQuant = (EditText) findViewById(R.id.edtQuant);
        edtValU = (EditText) findViewById(R.id.edtValU);
        edtTItens = (TextView) findViewById(R.id.edtTItens);

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnAddProd = (Button) findViewById(R.id.btnAddProd);

        Log.i("size lista produtos-->", Integer.toString(listaProdutos.size()));

/////////////////AUTOCOMPLETE////////////////////////////////////

        autoCompPro = (AutoCompleteTextView) findViewById(R.id.autoCompPro);
        autoCompPro.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final ProdutoAdapter adp = new ProdutoAdapter(this, R.layout.activity_main, listaProdutos);
        autoCompPro.setAdapter(adp);

        autoCompPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                p = adp.getItem(position);

                edtValU.setText(util.formatarDouble2c(p.getValorU()));


            }
        });

/////////////////BUTTONS/////////////////////////////////////////

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle param = new Bundle();
                param.putSerializable("listProd", (Serializable) listaItens);
                param.putSerializable("cliente",  cliente);

                Intent intent = new Intent(getApplicationContext(), ClienteVenda.class);
                intent.putExtras(param);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        Button btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaItens.clear();
                edtTItens.setText("");
                itensVAdapter.notifyDataSetChanged();
            }
        });

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listaItens.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Nenhum produto digitado!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Date date = new Date();
                        String dataArq = new SimpleDateFormat("ddHHmm").format(date.getTime());
                        String nomeArquivo = dataArq + ".ppt";

                        if (criarArquivo(listaItens, cliente, nomeArquivo)) {
                            Toast.makeText(getApplicationContext(), "Venda salva com sucesso!", Toast.LENGTH_SHORT).show();

                            Upload up = new Upload();
                            up.setNomeArquivo(nomeArquivo);
                            up.execute();

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent1 = new Intent(getApplicationContext(), ClienteVenda.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent1);
                                }
                            }, 2200);

                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao salvar venda!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtQuant.getText().toString().equals("") && !edtQuant.getText().toString().equals("")) {
                    p.setUnidade(Integer.parseInt(edtQuant.getText().toString()));
                    p.setValorU(Double.parseDouble(util.trocarVpP(edtValU.getText().toString())));

                    if (listaItens == null) {
                        listaItens = new ArrayList<Produto>();
                        listaItens.add(p);

                        intancialItensAdp();
                        calcTotalProd(listaItens);


                    } else {
                        int index = util.containsObject(listaItens, p);
                        if (index != -1) {
                            listaItens.get(index).setUnidade(listaItens.get(index).getUnidade() + p.getUnidade());
                            itensVAdapter.notifyDataSetChanged();
                            calcTotalProd(listaItens);

                        } else {
                            listaItens.add(p);
                            itensVAdapter.notifyDataSetChanged();
                            calcTotalProd(listaItens);

                        }
                    }


                    autoCompPro.setText("");
                    edtQuant.setText("");
                    edtValU.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Quantidade Invalida!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void intancialItensAdp(){
        itensVAdapter= new ItensVAdapter(this, listaItens);
        listvItensVend.setAdapter(itensVAdapter);
    }

    public void calcTotalProd(List<Produto> lista) {

        Double total = 0d;
        for (Produto prod : lista) {
            total += prod.getValorU() * prod.getUnidade();
        }

        edtTItens.setText(util.formatarDouble2c(total));

    }
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.idConfig:
//                Intent intent = new Intent(ProdutosVenda.this, Login.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.idAtualizar:
//                intent = new Intent(ProdutosVenda.this, ClienteVenda.class);
//                startActivity(intent);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public boolean lerTxt() {
        List<String> dados = new ArrayList<>();

        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft", "produtos.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                dados.add(linha);
            }

            compararString(dados);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void compararString(List<String> lista) {
        listaProdutos = new ArrayList<>();
        for (int j = 0; j < lista.size(); ) {
            Produto produto = new Produto();
            String flag = ";";
            String cat = new String();
            String id = new String();
            String desc = new String();
            String valU = new String();
            String cor = "#";

            int i = 0;
            int tamLinha = lista.get(j).length() - 1;
            String str = lista.get(j);

            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                cat += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                id += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                desc += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha >= i) {
                valU += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha >= i) {
                cor += str.charAt(i);
                i++;
            }

            produto.setId(Long.parseLong(id));
            produto.setDescricao(desc);
            produto.setValorU(Double.parseDouble(valU));
            produto.setCor(cor);

            listaProdutos.add(produto);
            j++;
        }

    }

    public boolean criarArquivo(List<Produto> lista, Cliente cliente, String nomeArquivo) throws IOException {
        String diretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Techsoft" + File.separator + "Salvo";

        File fileExt = new File(diretorio, nomeArquivo);

        fileExt.getParentFile().mkdirs();

        FileOutputStream fosExt = null;
        fosExt = new FileOutputStream(fileExt);

        String cod, desc, qant, linha, valu, codCli, flag = ";";

        codCli = Long.toString(cliente.getId()) + flag + "\r\n";

        try {
            fosExt.write(codCli.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < lista.size(); ) {
            cod = Long.toString(lista.get(i).getId());
            desc = lista.get(i).getDescricao();
            qant = Integer.toString(lista.get(i).getUnidade());
            valu = Double.toString(lista.get(i).getValorU());
            linha = cod + flag + desc + flag + qant + flag + valu + flag +"\r\n";
            try {
                fosExt.write(linha.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            i++;
        }

        fosExt.close();

        return true;

    }

}
