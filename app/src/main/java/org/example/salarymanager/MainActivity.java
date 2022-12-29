package org.example.salarymanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AdapterGastos adapter;
    ArrayList<Gasto> gastos;
    RecyclerView rv;
    ActionBar ab;
    Button bAdd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gastos = new ArrayList<>();
        adapter = new AdapterGastos(gastos);
        rv = findViewById(R.id.recyclerViewGastos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ab = getSupportActionBar();
        if(ab!=null){
            ab.hide();
        }
        bAdd = findViewById(R.id.buttonAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

    }
}