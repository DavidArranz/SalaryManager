package org.example.salarymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        double objetivo,salario;
        Bundle extras = getIntent().getExtras();
        objetivo = extras.getDouble("objetivo");
        salario = extras.getDouble("salario");
        etObjetivo.setText(String.valueOf(objetivo));
        etSalario.setText(String.valueOf(salario));
        //al pulsar el boton de guardar se recogen los datos y se devuelven como respuesta al MainActivity
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj,sal;
                obj=etObjetivo.getText().toString();
                sal=etSalario.getText().toString();
                double inobj,insal;
                inobj = Double.parseDouble(obj);
                insal = Double.parseDouble(sal);
                if(obj.equals("") || sal.equals("")){
                    Toast.makeText(getApplicationContext(),"No se admiten campos vacios",Toast.LENGTH_SHORT).show();
                }else if(inobj>insal){
                    Toast.makeText(getApplicationContext(),"El objetivo debe ser inferior al salario",Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("objetivo", obj);
                    intent.putExtra("salario", sal);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
