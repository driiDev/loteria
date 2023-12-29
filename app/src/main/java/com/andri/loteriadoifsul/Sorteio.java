package com.andri.loteriadoifsul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Sorteio {
    @SerializedName("tema")
    @Expose
    private String tema;
    @SerializedName("numero")
    @Expose
    private int numero;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("numerosSorteados")
    @Expose
    private List<Integer> numerosSorteados;

    public String getTema() {

        return tema;
    }

    public void setTema(String tema) {

        this.tema = tema;
    }

    public int getNumero() {

        return numero;
    }

    public void setNumero(int numero) {

        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }

    public void setNumerosSorteados(List<Integer> numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }

    @Override
    public String toString() {
        return "Sorteio{" +
                "tema='" + tema + '\'' +
                ", numero=" + numero +
                ", data='" + data + '\'' +
                ", numerosSorteados=" + numerosSorteados +
                '}';
    }
}
