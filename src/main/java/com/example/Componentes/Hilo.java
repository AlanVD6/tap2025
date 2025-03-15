package com.example.Componentes;

import javafx.scene.control.ProgressBar;

public class Hilo extends Thread {
    private ProgressBar pgbRuta;
    public Hilo(String nombre, ProgressBar pgb) {
        super(nombre);
        this.pgbRuta = pgb;
    }

    @Override
    public void run() {
        super.run();
        double avance =0;
        while(avance < 1){

            avance += Math.random()*.01;
            this.pgbRuta.setProgress(avance);
            try{
                sleep((long)(Math.random() * 500));

            }catch (InterruptedException i){}
        }
    }

}
