package org.example.salarymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterGastos extends RecyclerView.Adapter<AdapterGastos.GastoViewHolder> {

    private ArrayList<Gasto> expenses;

    public AdapterGastos(ArrayList<Gasto> expenses) {
        this.expenses = expenses;
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
        Gasto expense = expenses.get(position);
        holder.bind(expense);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
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

        public void bind(Gasto expense) {
            tvNom.setText(expense.getNombre());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00€",new DecimalFormatSymbols(Locale.ITALIAN));
            tvMont.setText(String.valueOf(decimalFormat.format(expense.getMonto())));
            tvFecha.setText(expense.getDate().toString());
            ivIcon.setImageBitmap(expense.getIcon());
        }
    }
}
