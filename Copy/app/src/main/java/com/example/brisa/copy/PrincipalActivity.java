package com.example.brisa.copy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Principal;

public class PrincipalActivity extends AppCompatActivity {


    ViewPager viewPagerVentas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //obtenemos  el elemento de las tabs
        viewPagerVentas = (ViewPager) findViewById(R.id.viewPagerVentas);
        viewPagerVentas.setAdapter(new VentaPageAdapter(getSupportFragmentManager()));

        Conexion conexion= new Conexion(this);
        conexion.Abrr();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        Drawable icon = getResources().getDrawable(R.drawable.color);
        menu.getItem(0).setIcon(icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent=null;
        try{
            switch (item.getItemId()) {
                case R.id.action_actualizar_rbn:
                    intent= new Intent(this, RangosBNActivity.class);
                    intent.putExtra("tipoHoja", "BLANCO");
                    this.startActivity(intent);
                    return true;
                case R.id.action_actualizar_rCorlor:
                    intent = new Intent(this, RangosBNActivity.class);
                    intent.putExtra("tipoHoja", "COLOR");
                    this.startActivity(intent);
                    return true;
                case R.id.action_sync:
                    if(conectadoWifi()) {
                        consumirWS c = new consumirWS(getBaseContext(), PrincipalActivity.this);
                        c.execute("");
                    }else
                    {
                        Toast.makeText(getBaseContext(), "Para sincronizar la informacion debes estas conectador a un red inalambrica",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch(Exception e){
            Log.d("error en ", e.getMessage());
        }
        return true;
    }

    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

}
