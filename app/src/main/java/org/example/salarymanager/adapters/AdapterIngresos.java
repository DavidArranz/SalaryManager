package org.example.salarymanager.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.example.salarymanager.Gasto;
import org.example.salarymanager.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

//adaptador para el RecivlerView del objeto Gasto
public class AdapterIngresos extends RecyclerView.Adapter<AdapterIngresos.GastoViewHolder> {

    private ArrayList<Gasto> ingresos;

    public AdapterIngresos(ArrayList<Gasto> ingresos) {
        this.ingresos = ingresos;
    }



    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        Gasto ingreso = ingresos.get(position);
        holder.bind(ingreso);
    }

    @Override
    public int getItemCount() {
            return ingresos.size();
    }

    static class GastoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNom;
        private TextView tvMont;
        private TextView tvFecha;
        private ImageView ivIcon;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.textViewNombre);
            tvMont = itemView.findViewById(R.id.textViewMontoGasto);
            tvFecha = itemView.findViewById(R.id.textViewFecha);
            ivIcon = itemView.findViewById(R.id.imageViewIcon);
        }

        public void bind(Gasto ingreso) {
            tvNom.setText(ingreso.getNombre());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00€",new DecimalFormatSymbols(Locale.ITALIAN));
            double monto = ingreso.getMonto();
            if(monto>0) {
                tvMont.setText("+"+String.valueOf(decimalFormat.format(ingreso.getMonto())));
                tvMont.setTextColor(Color.parseColor("#01E7C0"));
            }else{
                tvMont.setText(String.valueOf(decimalFormat.format(ingreso.getMonto())));
            }
            tvFecha.setText(ingreso.getDate().toString());
            ivIcon.setImageBitmap(ingreso.getIcon());
        }


    }
}
