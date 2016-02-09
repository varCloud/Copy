package com.example.brisa.copy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ChristianPR on 06/02/2016.
 */
public class VentaPageAdapter extends FragmentPagerAdapter {

    public VentaPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Creamos un fragme con el nombre como argumento
        Bundle args = new Bundle();
        Fragment fragment = new FragmentVenta();
        //args.putString("tituloVenta",getPageTitle(position).toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "Venta 1";
            case 1: return "Venta 2";
            case 2: return "Venta 3";
            case 3: return "Venta 4";
            case 4: return "Venta 5";
            default: return "";
        }
    }
}
