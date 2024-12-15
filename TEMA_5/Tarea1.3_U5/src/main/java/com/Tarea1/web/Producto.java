package com.Tarea1.web;

public class Producto {

    private int numero;
    private String concepto;
    private double importe;

    // Constructor
    public Producto(int numero, String concepto, double importe) {
        this.numero = numero;
        this.concepto = concepto;
        this.importe = importe;
    }

    // Getters
    public int getNumero() {
        return numero;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getImporte() {
        return importe;
    }
}
