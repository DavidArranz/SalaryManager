package org.example.salarymanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    File file_gastos,file_objetivo,file_salario,file_monto;

    //metodo que recive las respuestas de los intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Respuesta de la creracion de un gasto
        if (resultCode == RESULT_OK && requestCode == 1) {
            String nom,sdate;
            double monto;
            Bitmap icon;
            //recepcion de los datos del gasto
            nom=intent.getStringExtra("nombre");
            sdate=intent.getStringExtra("date");
            monto=intent.getDoubleExtra("monto",0.0);
            icon=intent.getParcelableExtra("icon");
            //creacion del gasto
            Gasto gasto = new Gasto(nom,monto,sdate,icon);
            gastos.add(gasto);
            //se notifica al adaptador el nuevo gasto
            adapter.notifyItemInserted(gastos.size()-1);
            //se vuelve a guardar la lista de gastos actualizada
            saveData(gastos,file_gastos);

        }
        //respuesta de la modificacion de parametros
        if (resultCode == RESULT_OK && requestCode == 2) {
            String obj,sal;
            obj = intent.getStringExtra("objetivo");
            sal =  intent.getStringExtra("salario");
            saveData(obj,file_objetivo);
            saveData(sal,file_salario);
            objetivo=Double.parseDouble(obj);
            salario=Double.parseDouble(sal);
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
        file_gastos = new File(getFilesDir(), "rvGastos.bin");
        file_objetivo = new File(getFilesDir(), "objetivo.bin");
        file_salario = new File(getFilesDir(), "salario.bin");
        file_monto = new File(getFilesDir(),"monto.bin");
        if(checkIfFileExists(file_gastos) && file_gastos.length()!=0){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file_gastos));
                gastos = (ArrayList<Gasto>) inputStream.readObject();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            gastos = new ArrayList<>();
        }
        if(checkIfFileExists(file_salario) && file_salario.length()!=0){
            salario = readDouble(file_salario);
            objetivo = readDouble(file_objetivo);
            tvSalario.setText(String.valueOf(salario));
            tvObjetivo.setText(String.valueOf(objetivo));
        }
        if(checkIfFileExists(file_monto) && file_monto.length()!=0){
                monto = readDouble(file_monto);
                tvMonto.setText(String.valueOf(monto));
        }

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
    public void saveData(ArrayList data,File file){


        try {
            FileOutputStream outputStream = outputStream = new FileOutputStream(file,false);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.close();
        outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveData(String data,File file){
        try {
            FileOutputStream outputStream = outputStream = new FileOutputStream(file,false);


            outputStream.write(data.getBytes());
            outputStream.close();

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Double readDouble(File file){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileInputStream fis = new FileInputStream(file);
            int i;
            i = fis.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = fis.read();
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String valueString = byteArrayOutputStream.toString();
        double value = Double.parseDouble(valueString);
        return value;

    }
    public boolean checkIfFileExists(File file){
        if (!file.exists()) {
            try {
                file.createNewFile();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            return true;
        }
        return false;
    }
}