package org.example.salarymanager;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;
//objeto de un Gasto
public class Item implements Serializable {
    private String nombre;
    private double monto;
    private ProxyBitmap icon;
    private String date;

    public Item(String n, double cant, String date, Bitmap icon){
        nombre=n;
        monto=cant;
        this.date = date;
        this.icon = new ProxyBitmap(icon);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {return monto;}

    public void setMonto(double monto) {this.monto = monto;}

    public Bitmap getIcon() {return icon.getBitmap();}

    public void setIcon(Bitmap icon) {this.icon = new ProxyBitmap(icon);}

    public String getDate() {return date;}

    public void setDate(Date String) {this.date = date;}
}
