package com.example.brisa.copy;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TableLayout;

public class RangosColorActivity extends AppCompatActivity {

    private  Conexion conexion;
    private Context context ;
    private Switch switchCarta;
    private Switch switchDobleCarta;
    private Switch switchOficio;
    private TableLayout tablaBN;
    private Button btnAcutalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangos_color);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAcutalizar= (Button) findViewById(R.id.btnActualizar);
        tablaBN = (TableLayout) findViewById(R.id.tablaBN);
        switchCarta = (Switch)findViewById(R.id.switchCarta);
        switchDobleCarta=(Switch)findViewById(R.id.switchDobleCarta);
        switchOficio=(Switch)findViewById(R.id.switchOficio);
        context = this;

        conexion = new Conexion(this);
        conexion.Abrr();

        btnAcutalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

}
