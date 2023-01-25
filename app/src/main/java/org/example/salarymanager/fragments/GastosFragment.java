package org.example.salarymanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.salarymanager.adapters.AdapterGastos;
import org.example.salarymanager.Item;
import org.example.salarymanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GastosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GastosFragment extends Fragment {
    RecyclerView rv;
    private ArrayList<Item> items;
    private AdapterGastos adapter;
    public GastosFragment(ArrayList<Item> items) {
        this.items = filterItems(items);
    }

    private ArrayList<Item> filterItems(ArrayList<Item> items) {
        ArrayList<Item> filteredItems = new ArrayList<Item>();
        for(Item item : items){
            if(item.getMonto()<0){
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public static GastosFragment newInstance(ArrayList<Item> items) {
        GastosFragment fragment = new GastosFragment(items);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastos, container, false);
        rv = view.findViewById(R.id.reciclerViewFragmentGastos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterGastos(items);
        rv.setAdapter(adapter);
        return view;
    }


    public void addItem(Item item) {
        items.add(item);
            adapter.notifyItemInserted(items.size()-1);
    }
}