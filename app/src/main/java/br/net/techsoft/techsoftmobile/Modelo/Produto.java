package br.net.techsoft.techsoftmobile.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Jhef3y on 02/08/2016.
 */
public class Produto implements Serializable{
    private Long id;
    private String descricao;
    private Double valorU;
    private int unidade = 1;
    private String cor;

    public Produto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorU() {
        return valorU;
    }

    public void setValorU(Double valorU) {
        this.valorU = valorU;
    }

    public int getUnidade() {
        return unidade;
    }

    public void setUnidade(int unidade) {
        this.unidade = unidade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
