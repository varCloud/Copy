package com.example.brisa.copy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PrincipalActivity extends AppCompatActivity {

    TextView txtPorcentaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Conexion conexion= new Conexion(this);
        conexion.Abrr();
        Button btn = (Button) findViewById(R.id.btnAceptar);
        txtPorcentaje = (TextView)findViewById(R.id.txtPorcentaje);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        SeekBar seekPorcentajeColor  = (SeekBar) findViewById(R.id.pocentColor);

        seekPorcentajeColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress;
                //txtPorcentaje.setText(progress + "/" + seekBar.getMax());
                txtPorcentaje.setText(progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch(Exception e){
            Log.d("error en ", e.getMessage());
        }
        return true;
    }

}
