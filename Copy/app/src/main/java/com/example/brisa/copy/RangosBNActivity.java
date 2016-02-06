package com.example.brisa.copy;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RangosBNActivity extends AppCompatActivity {


    private  Conexion conexion;
    private Context context ;
    private Switch switchCarta;
    private Switch switchDobleCarta;
    private Switch switchOficio;
    private TableLayout tablaBN;
    private   Button btnAcutalizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangos_bn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAcutalizar= (Button) findViewById(R.id.btnActualizar);
        tablaBN = (TableLayout) findViewById(R.id.tablaBN);
        switchCarta = (Switch)findViewById(R.id.switchCarta);
        context = this;

        conexion = new Conexion(this);
        conexion.Abrr();
        btnAcutalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LimpiarTabla();
                int i = 1;
                if (switchCarta.isChecked()) {
                    Cursor c = conexion.db.rawQuery("select * from rangosxtipocopiabn where idCatTipoCopia = 1", null);
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            String min = c.getString(1);
                            String max = c.getString(2);
                            String costo = c.getString(3);
                            String idCatTipoCopia = c.getString(4);
                            Log.d("resultado de la table ", min + " " + max + " " + costo + " " + idCatTipoCopia);

                            TableRow row = new TableRow(context);
                            row.setId(100 + i);
                            row.setTag(c.getString(0));

                            TextView tv = new TextView(context);
                            tv.setId(600 + i);
                            tv.setText("A");


                            EditText txtmin = new EditText(context);
                            txtmin.setId(300 + i);
                            txtmin.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            txtmin.setText(min);

                            EditText txtmax = new EditText(context);
                            txtmax.setId(400 + i);
                            txtmax.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            txtmax.setText(max);

                            EditText txtcosto = new EditText(context);
                            txtcosto.setId(500 + i);
                            txtcosto.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            txtcosto.setText(costo);

                            row.addView(txtmin);
                            row.addView(tv);
                            row.addView(txtmax);
                            row.addView(txtcosto);
                            tablaBN.addView(row);

                            i++;
                        } while (c.moveToNext());
                    }
                }
            }
        });

        Button btnGuardaBN =(Button) findViewById(R.id.btnGuardarBN);
        btnGuardaBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _min,_max,_costo;
                _costo=_min=_max="0";
                try {

                    for (int i = 2; i < tablaBN.getChildCount(); i++) {
                        View child = tablaBN.getChildAt(i);

                        if (child instanceof TableRow) {
                            TableRow row = (TableRow) child;

                            for (int x = 0; x < row.getChildCount(); x++) {
                                View view = row.getChildAt(x);
                                if(view instanceof  EditText) {
                                    switch (x) {
                                        case 0:
                                            EditText min = ((EditText) row.getChildAt(x));
                                            _min = min.getText().toString();
                                            break;
                                        case 2:
                                            EditText max = (EditText) row.getChildAt(x);
                                            _max = max.getText().toString();
                                            break;
                                        case 3:
                                            EditText costo = (EditText) row.getChildAt(x);
                                            _costo = costo.getText().toString();
                                            break;
                                    }
                                }
                            }
                            conexion.db.execSQL("UPDATE rangosxtipocopiabn set min ="+_min+"," +
                                    " max ="+_max+ " , costo="+_costo+" where idCatTipoCopia= 1 and idRangoXTipoCopiaBN="+row.getTag().toString());
                        }
                    }

                   DesabilitaItems();


                }catch (Exception ex)
                {
                    Log.d("error en ", ex.getMessage());
                }
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

    public void LimpiarTabla()
    {
        //for (int i = 0; i < tablaBN.getChildCount(); i++) {
          //  View child = tablaBN.getChildAt(i);
        int childCount = tablaBN.getChildCount();
        if(childCount > 1)
            tablaBN.removeViews(2, childCount - 2);
       // }
    }

    public void  DesabilitaItems()
    {
        for (int i = 0; i < tablaBN.getChildCount(); i++) {
            TableRow child = (TableRow) tablaBN.getChildAt(i);
            child.setEnabled(false);
        }

    }

}
