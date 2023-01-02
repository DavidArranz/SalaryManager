package org.example.salarymanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    double objetivo,salario,monto;
    AdapterGastos adapter;
    ArrayList<Gasto> gastos;
    RecyclerView rv;
    ActionBar ab;
    Button bAdd;
    ImageButton bEdit;
    TextView tvObjetivo,tvSalario,tvMonto;
    FileManager fm;

    boolean objFlag=true;

    //metodo que recive las respuestas de los intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Respuesta de la creracion de un gasto
        if (resultCode == RESULT_OK && requestCode == 1) {
            String nom,sdate;
            double monto_gasto;
            Bitmap icon;
            //recepcion de los datos del gasto
            nom=intent.getStringExtra("nombre");
            sdate=intent.getStringExtra("date");
            monto_gasto=intent.getDoubleExtra("monto",0.0);
            icon=intent.getParcelableExtra("icon");
            //creacion del gasto
            Gasto gasto = new Gasto(nom,monto_gasto,sdate,icon);
            gastos.add(gasto);
            //se notifica al adaptador el nuevo gasto
            adapter.notifyItemInserted(gastos.size()-1);
            //se vuelve a guardar la lista de gastos actualizada
            fm.saveData(gastos);
            //se actualiza el monto
            monto = monto-monto_gasto;
            //se guarda la nueva cantidad
            fm.saveData(String.valueOf(monto),"monto");
            //se establece la nueva cantidad en la interfaz
            tvMonto.setText(String.valueOf(monto)+"€");
            //si objFlag es true (todabia no se habia rebasado el objetivo)
            //y el monto es ahora menor que el ovjetivo
            //se crea el popup
            //y se pone el Flag a false para que no se vuelva a ejecutar
            if(objFlag && monto<objetivo){
                createPopUp();
                objFlag = false;
                fm.saveData(Boolean.toString(objFlag),"flag");
            }

        }
        //respuesta de la modificacion de parametros
        if (resultCode == RESULT_OK && requestCode == 2) {
            String obj,sal;
            obj = intent.getStringExtra("objetivo");
            sal =  intent.getStringExtra("salario");
            fm.saveData(obj,"objetivo");
            fm.saveData(sal,"salario");
            objetivo=Double.parseDouble(obj);
            salario=Double.parseDouble(sal);
            monto=salario;
            tvObjetivo.setText(objetivo+"€");
            tvSalario.setText(salario+"€");
            tvMonto.setText(salario+"€");


        }
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMonto = findViewById(R.id.textViewMonto);
        tvObjetivo = findViewById(R.id.textViewObjetivo);
        tvSalario = findViewById(R.id.textViewEditSalario);
        bEdit=findViewById(R.id.imageButtonEdit);
         fm = new FileManager(getApplicationContext());

        gastos = fm.getGastos();
        salario = fm.getSalario();
        objetivo = fm.getObjetivo();
        tvSalario.setText(String.valueOf(salario));
        tvObjetivo.setText(String.valueOf(objetivo));
        monto=fm.getMonto();
        tvMonto.setText(String.valueOf(monto));
        objFlag = fm.getFlag();

        adapter = new AdapterGastos(gastos);
        rv = findViewById(R.id.recyclerViewGastos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        ab = getSupportActionBar();
        if(ab!=null){
            ab.hide();
        }
        bAdd = findViewById(R.id.buttonAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivityForResult(intent,1);
            }
        });
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                startActivityForResult(intent,2);
            }
        });

    }


    private void createPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Queda poco dinero del salario de este mes.");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}