package org.example.salarymanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.salarymanager.adapters.AdapterTodo;
import org.example.salarymanager.Item;
import org.example.salarymanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoFragment extends Fragment {
    RecyclerView rv;
    AdapterTodo adapter;
    private ArrayList<Item> items;
    public TodoFragment(ArrayList<Item> items) {
        this.items = items;
    }

    public static TodoFragment newInstance(ArrayList<Item> items) {
        TodoFragment fragment = new TodoFragment(items);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        rv = view.findViewById(R.id.reciclerViewFragmentTodo);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterTodo(items);
        rv.setAdapter(adapter);
        return view;
    }


    public void addItem(Item item) {
        items.add(item);
        adapter.notifyItemInserted(items.size()-1);
    }
}