package org.example.salarymanager;

import android.graphics.Bitmap;

import java.util.Date;

public class Gasto {
    private String nombre;
    private double monto;
    private Bitmap icon;
    private Date date;

    public Gasto(String n, double cant, Date date, Bitmap icon){
        nombre=n;
        monto=cant;
        this.date = date;
        this.icon = icon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {return monto;}

    public void setMonto(double monto) {this.monto = monto;}

    public Bitmap getIcon() {return icon;}

    public void setIcon(Bitmap icon) {this.icon = icon;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
