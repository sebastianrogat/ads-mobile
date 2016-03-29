package br.unopar.catalogoweb.modelo;

import java.io.Serializable;

/**
 * Created by adria on 03/24/2016.
 */
public class Mercadoria implements Serializable {
    private int mID;
    private String mNome;
    private double mPreco;

    public Mercadoria() {
    }

    public Mercadoria(String mNome, double mPreco) {
        this.mNome = mNome;
        this.mPreco = mPreco;
    }

    public Mercadoria(int mID, String mNome, double mPreco) {
        this.mID = mID;
        this.mNome = mNome;
        this.mPreco = mPreco;
    }

    public int getID() {
        return mID;
    }
    public void setID(int ID) {
        mID = ID;
    }

    public String getNome() {
        return mNome;
    }
    public void setNome(String nome) {
        mNome = nome;
    }

    public double getPreco() {
        return mPreco;
    }
    public void setPreco(double preco) {
        mPreco = preco;
    }

    @Override
    public String toString() {
        return mNome;
    }
}

