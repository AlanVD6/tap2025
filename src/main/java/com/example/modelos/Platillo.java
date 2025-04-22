package com.example.modelos;

    public class Platillo {
        private String nombre;
        private double precio;
        private String descripcion;
        private String imagen; // ruta a la imagen

        public Platillo(String nombre, double precio, String descripcion, String imagen) {
            this.nombre = nombre;
            this.precio = precio;
            this.descripcion = descripcion;
            this.imagen = imagen;
        }

        public String getNombre() { return nombre; }
        public double getPrecio() { return precio; }
        public String getDescripcion() { return descripcion; }
        public String getImagen() { return imagen; }
    }
