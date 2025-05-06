package com.example.modelos;

public class Mesa {
    private int numero;
    private int capacidad;
    private boolean ocupada;
    private boolean reservada;

    public Mesa(int numero) {
        this.numero = numero;
        this.capacidad = 4; // Valor por defecto
        this.ocupada = false;
        this.reservada = false;
    }

    // Getters y setters
    public int getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
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