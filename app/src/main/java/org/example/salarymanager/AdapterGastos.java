package org.example.salarymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterGastos extends RecyclerView.Adapter<AdapterGastos.GastoViewHolder> {

    private List<Gasto> expenses;

    public AdapterGastos(List<Gasto> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gasto_item, parent, false);
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
        private TextView tvCant;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.textViewNombre);
            tvCant = itemView.findViewById(R.id.textViewCantidad);
        }

        public void bind(Gasto expense) {
            tvNom.setText(expense.getNombre());
            tvCant.setText(String.valueOf(expense.getCantidad()));
        }
    }
}
