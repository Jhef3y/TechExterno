package br.net.techsoft.techsoftmobile.DATA;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.ConsultaClie;
import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.Modelo.Venda;

/**
 * Created by Jhef3y on 01/11/2016.
 */
public class Sop {
    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;
    List<String> dados;
    private List<String> listNomeArq;

    public Sop() {
    }

    public boolean lerPPT(String arq) {
        dados = new ArrayList<>();
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft" + File.separator + "Salvo", arq);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = bufferedReader.readLine()) != null ) {
                dados.add(linha);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Produto> separarProduto() {
        produtos = new ArrayList<>();
        for (int j = 0; j < dados.size(); ) {
            Produto produto = new Produto();
            String flag = ";";
            String cat = new String();
            String id = new String();
            String desc = new String();
            String valU = new String();
            String cor = "#";

            int i = 0;
            int tamLinha = dados.get(j).length() - 1;
            String str = dados.get(j);

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

            produtos.add(produto);
            j++;
        }
        return produtos;
    }

    public List<Produto> separarProduto(int index) {
        produtos = new ArrayList<>();
        for (int j = index; j < dados.size(); ) {
            Produto produto = new Produto();
            String flag = ";";
            String un = new String();
            String id = new String();
            String valU = new String();
            String desc = new String();

            int i = 0;
            int tamLinha = dados.get(j).length() - 1;
            String str = dados.get(j);

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

            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                un += str.charAt(i);
                i++;
            }
            i++;

            while (str.charAt(i) != flag.charAt(0)) {
                valU += str.charAt(i);
                i++;
            }
            i++;

            produto.setId(Long.parseLong(id));
            produto.setDescricao(desc);
            produto.setUnidade(Integer.parseInt(un));
            produto.setValorU(Double.parseDouble(valU));

            produtos.add(produto);
            j++;
        }
        return produtos;
    }

    public List<Cliente> separarCliente() {
        return clientes;
    }

    public List<Venda> separarVenda() {
        vendas = new ArrayList<>();
        listarDir();

        for(int i = 0; i < listNomeArq.size(); i++) {

            lerPPT(listNomeArq.get(i));

            List<Produto> produtosVend = new ArrayList<>();
            List<Cliente> clientesVend = new ArrayList<>();
            Cliente cliente = new Cliente();
            Venda venda = new Venda();

            String cli = dados.get(0);

            if(cli.equals("vazio")){
                return vendas;
            }

            String codCli = new String();
            String flag = ";";

            for (int count = 0; cli.charAt(count) != flag.charAt(0); ) {
                codCli += cli.charAt(count);
                count++;
            }

            List<Cliente> c = new ArrayList<>(new ConsultaClie().preecheLista());
            cliente = c.get(Integer.parseInt(codCli));
            produtosVend = separarProduto(1);



            for (Cliente client : clientesVend) {
                if (client.getId() == Integer.parseInt(codCli)) {
                    cliente = client;
                }
            }


            venda.setCliente(cliente);
            venda.setProdutos(produtosVend);
            Long idVenda = Long.valueOf(listNomeArq.get(i).substring(0, listNomeArq.get(i).length() - 4));
            venda.setId(idVenda);
            vendas.add(venda);
        }

        return vendas;
    }

    public List<String> listarDir() {
        File file;

        listNomeArq = new ArrayList<String>();

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft" + "/Salvo");
        File list[] = file.listFiles();

        if(list == null){
            listNomeArq.add("vazio");
            return listNomeArq;
        }else {

            for (int i = 0; i < list.length; i++) {
                listNomeArq.add(list[i].getName());
                Log.i("Arquivo adicionado: ", list[i].getName());
            }
            return listNomeArq;
        }

    }


}
