package org.example.salarymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
    //esta clase recoje los nuevo datos del Salario y el monto objetivo
public class EditActivity extends AppCompatActivity {
    EditText etObjetivo,etSalario;
    Button bSave;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etObjetivo=findViewById(R.id.editTextObjetivo);
        etSalario = findViewById(R.id.editTextSalario);
        bSave = findViewById(R.id.buttonSave2);
        //al pulsar el boton de guardar se recogen los datos y se devuelven como respuesta al MainActivity
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj,sal;
                obj=etObjetivo.getText().toString();
                sal=etSalario.getText().toString();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("objetivo",obj);
                intent.putExtra("salario",sal);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
