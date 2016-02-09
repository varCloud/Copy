package com.example.brisa.copy;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by ChristianPR on 06/02/2016.
 */
public class FragmentVenta extends Fragment {

    private View vista;
    private Switch swColor;
    private Switch swBN;
    private EditText txtCantidadCopias;
    private RadioGroup rgbTipoCarta;
    private LinearLayout contentPorcentaje;
    private TextView txtPorcentaje;
    private SeekBar seekbPorcentaje;
    private int minPorcentaje;
    private Button btnAddCopias;
    private Button btnGuardarVenta;
    private Button btnCancelarVenta;
    Context contexto;
    private Toast toast;

    private Conexion conexion;

    //VAriables detalle de venta
    private int copiasBN;
    private int idtTipoCopia;
    private String detalleTipoCopia;
    private int cantidadCopias;
    private int idPorcentajeColor;
    private int copiasColor;
    private TextView lblTotalVenta;

    //variables venta
    private double ventaTotal;
    private int cantidadCopiasTotal;

    private ListView ltvDetalleVenta;
    private List<ItemCopia> items= new ArrayList<>();
    private ListAdapter adapterItemCopia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.vista = inflater.inflate(R.layout.fragment_venta, container, false);
        this.contexto = this.vista.getContext();
        Bundle args = getArguments();
        InitComponent();
        //Eventos
        InitEvent();
        return vista;
    }

    public void agregarCopias(final View v){

        ObtenerDatosCopia();
        if (this.cantidadCopias==0)return;
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Confirmación");
        builder.setMessage("Esta seguro de añadir las copias a la venta?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (swColor.isChecked()) {
                        addItemDetalleVentaColor();
                    }
                    if (swBN.isChecked()) {
                        //copiasBN = Integer.parseInt(txtBN.getText().toString());
                        addItemDetalleVentaBN();
                        //devemos obtener el precio de acuerdo al rango
                    }
                    dialog.dismiss();
                } catch (Exception ex) {
                    Alert("Ocurrio un error: " + ex.getMessage());
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
        });
        builder.show();
    }


    public void addItemDetalleVentaColor(){
        try {
            ItemCopia itemCopia=null;
            String detalleItem = getString(R.string.detalleVenta)+" "+getString(R.string.detalleColor)+" "+this.detalleTipoCopia+" "+this.txtPorcentaje.getText();

            /*String sql = "SELECT r.idRangoXTipoCopiaColor, p.costo FROM rangosxtipocopiacolor As r "
                    +"INNER JOIN porcentajexcopiacolor As p ON r.idRangoXTipoCopiaColor = p.idRangoXTipoCopiaColor "
                    +"WHERE r.min <=" +this.cantidadCopias+" AND  r.max >= "+this.cantidadCopias+" AND r.idCatTipoCopia ="+this.idtTipoCopia+" "
                    +"AND p.idRangoXTipoCopiaColor =  r.idRangoXTipoCopiaColor "
                    +"AND p.IdPorcentaje = "+this.idPorcentajeColor;
            */
            String sql = "SELECT r.idPorcentajeCopiaColor, r.costo FROM porcentajexcopiacolor As r "
                    +"WHERE r.min <=" +this.cantidadCopias+" AND  r.max >= "+this.cantidadCopias+" AND r.idCatTipoCopia ="+this.idtTipoCopia+" "
                    +"AND r.IdPorcentaje = "+this.idPorcentajeColor;
            double costoTotal=0,costoUnitario=0;
            int idPorcentajeCopiaColor =0;
            Cursor c =this.conexion.db.rawQuery(sql,null);
            while (c.moveToNext()){
                idPorcentajeCopiaColor = c.getInt(0);
                costoUnitario = c.getFloat(1);
                costoTotal= roundTwoDecimals(costoUnitario * this.cantidadCopias);
                itemCopia = new ItemCopia(R.drawable.imp_colorr,detalleItem,"$"+costoTotal,this.cantidadCopias+"");
            }
            if(itemCopia!=null){
                itemCopia.setCantidadCopiasColor(this.cantidadCopias);
                itemCopia.setCostoCopiasColor(costoTotal);
                itemCopia.setIdCatTipoCopia(this.idtTipoCopia);
                itemCopia.setIdPorcentajeCopiaColor(this.idPorcentajeColor);
                this.items.add(itemCopia);
                this.adapterItemCopia.notifyDataSetChanged();

                this.cantidadCopiasTotal = this.cantidadCopiasTotal + itemCopia.getCantidadCopiasColor();
                this.ventaTotal = roundTwoDecimals(this.ventaTotal + itemCopia.getCostoCopiasColor());
                this.lblTotalVenta.setText(this.ventaTotal + "");
                this.txtCantidadCopias.setText("");
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    public void addItemDetalleVentaBN(){
        try {
            ItemCopia itemCopia=null;
            String detalleItem = getString(R.string.detalleVenta)+" "+getString(R.string.detalleBN)+" "+this.detalleTipoCopia;
            String sql = "SELECT idRangoXTipoCopiaBN, costo FROM rangosxtipocopiabn WHERE min <="
                    +this.cantidadCopias+" AND  max >= "+this.cantidadCopias+" AND idCatTipoCopia ="+this.idtTipoCopia;
            double costoTotal=0,costoUnitario=0;
            int rangoCopia =0;
            Cursor c =this.conexion.db.rawQuery(sql,null);
            while (c.moveToNext()){
                rangoCopia = c.getInt(0);
                costoUnitario = c.getDouble(1);
                costoTotal= roundTwoDecimals(costoUnitario * this.cantidadCopias);
                itemCopia = new ItemCopia(R.drawable.imp_negroo,detalleItem,"$"+costoTotal,this.cantidadCopias+"");
            }
            if(itemCopia!=null){
                itemCopia.setCantidadCopiasBN(this.cantidadCopias);
                itemCopia.setCostoCopiasBN(costoTotal);
                itemCopia.setIdCatTipoCopia(this.idtTipoCopia);
                itemCopia.setIdRangoXTipoCopiaBN(rangoCopia);
                this.items.add(itemCopia);
                this.adapterItemCopia.notifyDataSetChanged();

                this.cantidadCopiasTotal = this.cantidadCopiasTotal + itemCopia.getCantidadCopiasBN();
                this.ventaTotal = roundTwoDecimals(this.ventaTotal + itemCopia.getCostoCopiasBN());
                this.lblTotalVenta.setText(this.ventaTotal + "");
                this.txtCantidadCopias.setText("");
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    public void deleteItemDetalleVenta(int position){
        try {
            ItemCopia item = (ItemCopia) ltvDetalleVenta.getAdapter().getItem(position);
            this.cantidadCopiasTotal = this.cantidadCopiasTotal - item.getCantidadCopiasColor()-item.getCantidadCopiasBN();
            this.ventaTotal = roundTwoDecimals(this.ventaTotal - item.getCostoCopiasColor() - item.getCostoCopiasBN());
            this.lblTotalVenta.setText(this.ventaTotal + "");
            items.remove(item);
            this.adapterItemCopia.notifyDataSetChanged();
        }
        catch (Exception ex){
            throw  ex;
        }
    }

    public void ObtenerDatosCopia(){
        try {
            switch (this.rgbTipoCarta.getCheckedRadioButtonId()){
                case R.id.rbCarta:
                    this.idtTipoCopia = 1;
                    this.detalleTipoCopia = getString(R.string.detalleCarta);
                    break;
                case R.id.rbOficio:
                    this.idtTipoCopia = 2;
                    this.detalleTipoCopia = getString(R.string.detalleOficio);
                    break;
                case R.id.rbDobleCarta:
                    this.idtTipoCopia = 3;
                    this.detalleTipoCopia = getString(R.string.detalleDobleCarta);
                    break;
            }
            switch (seekbPorcentaje.getProgress()){
                case 20:
                    this.idPorcentajeColor = 1;
                    break;
                case 50 :
                    this.idPorcentajeColor =2;
                    break;
                case 100:
                    this.idPorcentajeColor =3;
                    break;
                default:
                    this.idPorcentajeColor =1;
                    break;
            }
            this.cantidadCopias = Integer.parseInt(this.txtCantidadCopias.getText().toString());
        }
        catch (NumberFormatException ex){
            Alert("Ingrese la cantidad de copias por favor");
            this.cantidadCopias =0 ;
        }
        catch (Exception ex){
            throw  ex;
        }

    }

    public void InsertarVenta(){
        try{
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fechaVenta", "'datetime()'");
            nuevoRegistro.put("cantidadCopiasTotal",this.cantidadCopiasTotal);
            nuevoRegistro.put("Total",this.ventaTotal);
            int idVenta = (int)this.conexion.db.insert("ventas",null,nuevoRegistro);
            //insertamos el detalle de venta
            int detalle = 0;
            for(ItemCopia item : items){
                ContentValues nuevoDetalleVenta = new ContentValues();
                nuevoDetalleVenta.put("idVenta", idVenta);
                nuevoDetalleVenta.put("idRangoXTipoCopiaBN",item.getIdRangoXTipoCopiaBN());
                nuevoDetalleVenta.put("cantidadCopiasBN",item.getCantidadCopiasBN());
                nuevoDetalleVenta.put("costoCopiasBN",item.getCostoCopiasBN());
                nuevoDetalleVenta.put("idCatTipoCopia",item.getIdCatTipoCopia());
                nuevoDetalleVenta.put("idPorcentajeCopiaColor",item.getIdPorcentajeCopiaColor());
                nuevoDetalleVenta.put("cantidadCopiasColor", item.getCantidadCopiasColor());
                nuevoDetalleVenta.put("costoCopiasColor", item.getCostoCopiasColor());
                detalle = (int)this.conexion.db.insert("detallesventa",null,nuevoDetalleVenta);
            }
            if(detalle>0){
                Alert("Se guardo correctamente la venta");
               limpiarVenta();
            }
        }
        catch (Exception ex){
            throw  ex;
        }
    }

    public void Alert(String mensaje){
        try{
            this.toast = Toast.makeText(contexto,mensaje, Toast.LENGTH_SHORT);
            this.toast.show();
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public void limpiarVenta(){
        this.cantidadCopiasTotal = 0;
        this.ventaTotal = 0;
        this.lblTotalVenta.setText(""+this.ventaTotal);
        this.items.clear();
        this.adapterItemCopia.notifyDataSetChanged();
        this.txtCantidadCopias.setText("");
    }

    public void SetPorcentajeColor_(int porcentaje){
        if(porcentaje <= this.minPorcentaje){
            this.seekbPorcentaje.setProgress(this.minPorcentaje);
            this.txtPorcentaje.setText(this.seekbPorcentaje.getProgress() + "%");
        }
        if(porcentaje > this.minPorcentaje && porcentaje <51){
            this.seekbPorcentaje.setProgress(50);
            this.txtPorcentaje.setText(this.seekbPorcentaje.getProgress() + "%");
        }
        if(porcentaje > 50 && porcentaje<=100){
            this.seekbPorcentaje.setProgress(100);
            this.txtPorcentaje.setText(this.seekbPorcentaje.getProgress() + "%");
        }


    }

    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#####.##");
        return Double.valueOf(twoDForm.format(d).toString().replace(",",""));
    }
    //region MetodosPrivados
    private void InitComponent(){
        this.swColor = (Switch) vista.findViewById(R.id.swColor);
        this.swBN = (Switch) vista.findViewById(R.id.swBN);
        this.txtCantidadCopias = (EditText) vista.findViewById(R.id.txtCantidadCopias);
        this.rgbTipoCarta= (RadioGroup)vista.findViewById(R.id.rgbTipoCarta);
        this.txtPorcentaje = (TextView) vista.findViewById(R.id.txtPorcentaje);
        this.seekbPorcentaje = (SeekBar) vista.findViewById(R.id.seekbPorcentaje);
        this.minPorcentaje = 20;
        this.seekbPorcentaje.setMax(100);
        this.seekbPorcentaje.setProgress(this.minPorcentaje);
        this.contentPorcentaje = (LinearLayout) vista.findViewById(R.id.content_porcentaje);
        this.contentPorcentaje.setVisibility(View.INVISIBLE);
        this.lblTotalVenta = (TextView) vista.findViewById(R.id.lblTotalVenta);
        this.lblTotalVenta.setText(this.ventaTotal + "");

        this.ltvDetalleVenta = (ListView) vista.findViewById(R.id.ltvDetalleVenta);
        this.adapterItemCopia = new ListAdapter(contexto, items);
        this.ltvDetalleVenta.setAdapter(adapterItemCopia);

        this.btnAddCopias = (Button) vista.findViewById(R.id.btnAddCopias);
        this.btnGuardarVenta = (Button) vista.findViewById(R.id.btnGuardarVenta);
        this.btnCancelarVenta = (Button)vista.findViewById(R.id.btnCancelarVenta);

        this.conexion = new Conexion(contexto);
        this.conexion.Abrr();
    }

    private void InitEvent() {
        this.swColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swBN.setChecked(false);
                    contentPorcentaje.setVisibility(View.VISIBLE);
                } else {
                    swColor.setChecked(false);
                    contentPorcentaje.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.swBN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    swColor.setChecked(false);
                else
                    swBN.setChecked(false);
            }
        });
        this.btnAddCopias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    agregarCopias(v);
                }
                catch (Exception ex){
                    Alert("Error: "+ex.getMessage());
                }
            }
        });
        this.btnGuardarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (items.size() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                        builder.setTitle("Confirmación");
                        builder.setMessage("Esta seguro de concluir la venta?");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                InsertarVenta();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } else
                        Alert("Sin datos que guardar");
                } catch (Exception ex) {
                    Alert("Error: " + ex.getMessage());
                }
            }
        });

        this.btnCancelarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(items.size()>0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                        builder.setTitle("Confirmación");
                        builder.setMessage("Esta seguro de cancelar la venta?");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                limpiarVenta();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    else
                    {
                        Alert("La venta esta en blanco");
                    }
                } catch (Exception e) {
                    Alert("Ocurrio un error: " + e.getMessage());
                }
            }
        });

        this.seekbPorcentaje.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SetPorcentajeColor_(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.ltvDetalleVenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    final int position_ = position;
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setTitle("Confirmación");
                    builder.setMessage("Esta seguro de quitar este  registro de la venta?");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteItemDetalleVenta(position_);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } catch (Exception ex) {
                    Alert("Error: " + ex.getMessage());
                }
            }
        });
    }
    //endregion MetodosPrivados
}
