package com.example.proyectov1.clases;

public class Carrito {
    private int id;
    private String codigo_carrito;
    private int id_producto;
    private double costo;
    private int cantidad;
    private String fecha_venta;
    private int id_usuario;
    private int carrito;
    private String nombre_producto;


    public Carrito() {
    }

    public Carrito(int id, String codigo_carrito, int id_producto, double costo, int cantidad, String fecha_venta, int id_usuario, int carrito, String nombre_producto) {
        this.id = id;
        this.codigo_carrito = codigo_carrito;
        this.id_producto = id_producto;
        this.costo = costo;
        this.cantidad = cantidad;
        this.fecha_venta = fecha_venta;
        this.id_usuario = id_usuario;
        this.carrito = carrito;
        this.nombre_producto = nombre_producto;
    }

    public int getId() {
        return id;
    }

    public String getCodigo_carrito() {
        return codigo_carrito;
    }

    public int getId_producto() {
        return id_producto;
    }

    public double getCosto() {
        return costo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getCarrito() {
        return carrito;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }
}
