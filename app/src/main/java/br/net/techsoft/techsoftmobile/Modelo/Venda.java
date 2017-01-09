package br.net.techsoft.techsoftmobile.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jhef3y on 01/11/2016.
 */
public class Venda implements Serializable{

    private Long id;
    private Cliente cliente;
    private String situacao;
    private List<Produto> produtos;

    public Venda() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
