package br.unopar.catmer.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by adria on 03/01/2016.
 */
public class Mercadoria {
    private long id;
    private String nome, fabricante, descricao;
    private double preco;
    private Bitmap foto;

    public Mercadoria(String nome, Bitmap foto, double preco, String fabricante, String descricao) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.descricao = descricao;
        this.preco = preco;
        this.foto = foto;
    }

    public Mercadoria(long id, String nome,  byte[] foto, double preco, String fabricante, String descricao) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.descricao = descricao;
        this.preco = preco;
        setFotoFromByteArray(foto);
    }

    public Mercadoria() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return nome;
    }

    public byte[] getFotoAsByte() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            return stream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public void setFotoFromByteArray(byte[] data) {
        if(data == null || data.length == 0) {
            return;
        }

        foto = BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
