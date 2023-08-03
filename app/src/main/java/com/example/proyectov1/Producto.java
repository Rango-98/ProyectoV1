package com.example.proyectov1;

public class Producto {

    private int id;
    private int id_proveedor;
    private String sku;
    private String nombre;
    private int cantidad;
    private String categoria;
    private String marca;
    private String modelo;
    private String tipo;
    private float precio_proveedor;
    private float precio_venta;
    private String codigo_barras;
    private String fecha_ingreso;
    public Producto() {
    }

    public Producto(int id, int id_proveedor, String sku, String nombre, int cantidad, String categoria, String marca, String modelo, String tipo, float precio_proveedor, float precio_venta, String codigo_barras, String fecha_ingreso) {
        this.id = id;
        this.id_proveedor = id_proveedor;
        this.sku = sku;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.precio_proveedor = precio_proveedor;
        this.precio_venta = precio_venta;
        this.codigo_barras = codigo_barras;
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getId() {
        return id;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public String getSku() {
        return sku;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public float getPrecio_proveedor() {
        return precio_proveedor;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }
}
