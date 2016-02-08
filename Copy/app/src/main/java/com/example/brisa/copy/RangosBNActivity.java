package com.example.brisa.copy;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RangosBNActivity extends AppCompatActivity {


    private  Conexion conexion;
    private Context context ;
    private Switch switchCarta;
    private Switch switchDobleCarta;
    private Switch switchOficio;
    private TableLayout tablaBN;
    private  Button btnAcutalizar;
    private  String idCatTipoCopias = "0";
    private boolean esColor=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        esColor = (getIntent().getExtras().getString("tipoHoja").equals("COLOR") ? true : false);
        setTitle(getResources().getString(R.string.title_activity_rangos_bn)+(esColor==true ?" A Color" : " Blanco y Negro" ));
        setContentView(R.layout.activity_rangos_bn);
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
                String min = "";
                String max = "";
                String costo = "";
                String idRango="";
                String idPorcentaje="";
                String descPorcentaje="";
                LimpiarTabla();
                int i = 1;
                int contadorHojaSeleccionada=0;
                String hojaSeleccionada ="";
                if (switchCarta.isChecked()) {
                    idCatTipoCopias = switchCarta.getTag().toString();
                    hojaSeleccionada="CARTA";
                    contadorHojaSeleccionada++;

                }
                if (switchDobleCarta.isChecked()){
                    idCatTipoCopias = switchDobleCarta.getTag().toString();
                    hojaSeleccionada="DOBLE CARTA";
                    contadorHojaSeleccionada++;
                }
                if (switchOficio.isChecked()) {
                    idCatTipoCopias = switchOficio.getTag().toString();
                    hojaSeleccionada="Oficio";
                    contadorHojaSeleccionada++;
                }


                TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                if (!idCatTipoCopias.toString().equals("0") && contadorHojaSeleccionada ==1) {
                    TextView hojaSelecciona = (TextView)findViewById(R.id.tvHojaSeleccion);
                    hojaSelecciona.setText(getResources().getString(R.string.hojaSeleccionada)+" "+ hojaSeleccionada);

                      String query = (esColor ==false? "select * from rangosxtipocopiabn where idCatTipoCopia = "+idCatTipoCopias :
                              "SELECT A.*,B.descripcion  FROM porcentajexcopiacolor A INNER JOIN " +
                                      "catporcentaje B ON A.idPorcentaje=B.idPorcentaje " +
                                      " where idCatTipoCopia = "+ idCatTipoCopias);

                    Log.d("query =>",query);
                    Cursor c = conexion.db.rawQuery(query, null);
                    if (c.moveToFirst()) {
                        do {
                            idRango=c.getString(0);
                            TableRow row = new TableRow(context);
                            row.setId(100 + i);
                            row.setTag(idRango);
                            row.setLayoutParams(layoutFila);

                            TextView tvContador = new TextView(context);
                            EstiloView(tvContador,Integer.toString(i),300);
                            row.addView(tvContador);

                            TextView tvA = new TextView(context);
                            EstiloView(tvA," A ",301);
                            if(esColor) {
                                idPorcentaje = c.getString(2);
                                min = c.getString(4);
                                max = c.getString(5);
                                costo = c.getString(3);
                                descPorcentaje=c.getString(6);

                                TextView tvDescPorcenaje = new TextView(context);
                                EstiloView(tvDescPorcenaje,descPorcentaje,303);
                                tvDescPorcenaje.setTag(idPorcentaje);
                                row.addView(tvDescPorcenaje);

                            }
                            else
                            {
                                min = c.getString(1);
                                max = c.getString(2);
                                costo = c.getString(3);
                                idCatTipoCopias = c.getString(4);
                            }
                            Log.d("resultado de la table ", min + " " + max + " " + costo + " " + idCatTipoCopias);

                            EditText txtmin = new EditText(context);
                            EstiloView(txtmin,min,304);

                            EditText txtmax = new EditText(context);
                            EstiloView(txtmax,max,305);

                            EditText txtcosto = new EditText(context);
                            EstiloView(txtcosto,costo,306);

                            row.addView(txtmin);
                            row.addView(tvA);
                            row.addView(txtmax);
                            row.addView(txtcosto);
                            tablaBN.addView(row);

                            i++;
                        } while (c.moveToNext());
                    }
                }else
                {
                    Toast.makeText(context,"Debes de seleccionar al menos un tipo de hoja",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnGuardaBN =(Button) findViewById(R.id.btnGuardarBN);
        btnGuardaBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _min,_max,_costo,idPorcentaje;
                idPorcentaje=_costo=_min=_max="0";
                try {
                    if(esColor) {

                        for (int i = 0; i < tablaBN.getChildCount(); i++) {
                            View child = tablaBN.getChildAt(i);

                            if (child instanceof TableRow) {
                                TableRow row = (TableRow) child;

                                for (int x = 0; x < row.getChildCount(); x++) {
                                    View view = row.getChildAt(x);
                                    if(view instanceof  EditText) {
                                        switch (x) {
                                            case 2:
                                                EditText min = ((EditText) row.getChildAt(x));
                                                _min = min.getText().toString();
                                                break;
                                            case 4:
                                                EditText max = (EditText) row.getChildAt(x);
                                                _max = max.getText().toString();
                                                break;
                                            case 5:
                                                EditText costo = (EditText) row.getChildAt(x);
                                                _costo = costo.getText().toString();
                                                break;
                                        }
                                    }else
                                    {
                                        if(view instanceof TextView && x==1)
                                        {
                                         TextView tvP  = (TextView) view;
                                            idPorcentaje = tvP.getTag().toString();
                                        }
                                    }
                                }
                                String query="UPDATE porcentajexcopiacolor SET  min ="+_min+",max ="+_max+"," +
                                        "costo ="+_costo+" where IdPorcentaje="+idPorcentaje+" " +
                                        "and idPorcentajeCopiaColor="+row.getTag().toString();
                                conexion.db.execSQL(query);
                            }
                        }

                    }
                    else{
                        for (int i = 0; i < tablaBN.getChildCount(); i++) {
                            View child = tablaBN.getChildAt(i);

                            if (child instanceof TableRow) {
                                TableRow row = (TableRow) child;

                                for (int x = 0; x < row.getChildCount(); x++) {
                                    View view = row.getChildAt(x);
                                    if(view instanceof  EditText) {
                                        switch (x) {
                                            case 1:
                                                EditText min = ((EditText) row.getChildAt(x));
                                                _min = min.getText().toString();
                                                break;
                                            case 3:
                                                EditText max = (EditText) row.getChildAt(x);
                                                _max = max.getText().toString();
                                                break;
                                            case 4:
                                                EditText costo = (EditText) row.getChildAt(x);
                                                _costo = costo.getText().toString();
                                                break;
                                        }
                                    }
                                }
                                conexion.db.execSQL("UPDATE rangosxtipocopiabn set min ="+_min+"," +
                                        " max ="+_max+ " , costo="+_costo+" where idCatTipoCopia="+idCatTipoCopias+" and idRangoXTipoCopiaBN="+row.getTag().toString());
                            }
                        }
                    }
                    Toast.makeText(context,"Rangos Actualizados", Toast.LENGTH_SHORT).show();
                   DesabilitaItems();
                }catch (Exception ex)
                {
                    Log.d("error en ", ex.getMessage());
                }
            }
        });
    }

    public void LimpiarTabla()
    {
        //for (int i = 0; i < tablaBN.getChildCount(); i++) {
          //  View child = tablaBN.getChildAt(i);
        int childCount = tablaBN.getChildCount();
        if(childCount > 1)
            tablaBN.removeViews(0, childCount );
       // }
    }

    public void  DesabilitaItems()
    {
        for (int i = 0; i < tablaBN.getChildCount(); i++) {
            TableRow child = (TableRow) tablaBN.getChildAt(i);
            child.setEnabled(true);
        }

    }

    private int obtenerAnchoPixelesTexto(String texto)
    {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(120);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }

    public void EstiloView(View v,String text,int id)
    {
        TableRow.LayoutParams layoutCelda;
        if(v instanceof  EditText ) {
            EditText et = (EditText)v;
            et.setId(id);
            et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et.setText(text);
            et.setGravity(Gravity.CENTER_HORIZONTAL);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(text), TableRow.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(layoutCelda);
        }else{
            TextView tv = (TextView)v;
            tv.setId(id);
            tv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(text), TableRow.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutCelda);
        }
    }

}
