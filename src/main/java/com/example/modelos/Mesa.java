package com.example.modelos;

public class Mesa {
    private int numero;
    private boolean ocupada;
    private boolean reservada;

    public Mesa(int numero) {
        this.numero = numero;
        this.ocupada = false;
        this.reservada = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void ocupar() {
        this.ocupada = true;
    }

    public void desocupar() {
        this.ocupada = false;
    }

    public void reservar() {
        this.reservada = true;
    }

    public void cancelarReserva() {
        this.reservada = false;
    }
}