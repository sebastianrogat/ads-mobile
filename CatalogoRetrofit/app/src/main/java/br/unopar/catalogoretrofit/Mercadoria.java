package br.unopar.catalogoretrofit;

import com.google.gson.annotations.SerializedName;

public class Mercadoria {
    @SerializedName("ID")
    private int id;
    @SerializedName("Nome")
    private String nome;
    @SerializedName("Preco")
    private double preco;

    public Mercadoria() {
    }

    public Mercadoria(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome;
    }
}
