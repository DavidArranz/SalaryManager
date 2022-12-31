package org.example.salarymanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    AdapterGastos adapter;
    ArrayList<Gasto> gastos;
    RecyclerView rv;
    ActionBar ab;
    Button bAdd;
    File file = new File(getFilesDir(), "appData.bin");
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == 1) {
            String nom,sdate;
            Date date = null;
            double monto;
            Bitmap icon;
            nom=intent.getStringExtra("nombre");
            sdate=intent.getStringExtra("date");
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date=format.parse(sdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            monto=intent.getDoubleExtra("monto",0.0);
            icon=intent.getParcelableExtra("icon");
            Gasto gasto = new Gasto(nom,monto,date,icon);
            gastos.add(gasto);
            adapter.notifyItemInserted(gastos.size()-1);
            saveData(gastos,file);

        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkIfFileExists(file)){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                gastos = (ArrayList<Gasto>) inputStream.readObject();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            gastos = new ArrayList<>();
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

    }
    public void saveData(ArrayList data,File file){


        try {
            FileOutputStream outputStream = outputStream = new FileOutputStream(file);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.close();
        outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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