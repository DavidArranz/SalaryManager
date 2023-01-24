package org.example.salarymanager.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.example.salarymanager.Gasto;
import org.example.salarymanager.fragments.GastosFragment;
import org.example.salarymanager.fragments.IngresosFragment;
import org.example.salarymanager.fragments.TodoFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class AdapterViewPager extends FragmentStateAdapter {
    ArrayList <Gasto> items;
    TodoFragment tfInstance;

    GastosFragment gfInstance;
    IngresosFragment ifInstance;
    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity, ArrayList<Gasto> items) {
        super(fragmentActivity);
        this.items = items;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                tfInstance = TodoFragment.newInstance((ArrayList<Gasto>) items.clone());
                return tfInstance;
            case 1:
                gfInstance = GastosFragment.newInstance((ArrayList<Gasto>) items.clone());
                return gfInstance;
            case 2:
                ifInstance = IngresosFragment.newInstance((ArrayList<Gasto>) items.clone());
                return ifInstance;
            default:
                return null;
        }
    }
    public void addItem(Gasto item){
        if(tfInstance != null) {
            tfInstance.addItem(item);
        }
        if(ifInstance != null && item.getMonto()>0) {

            ifInstance.addItem(item);
        }
        if(gfInstance != null && item.getMonto()<0) {
            gfInstance.addItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
