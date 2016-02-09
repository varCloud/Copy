package com.example.brisa.copy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by Brisa on 08/02/16.
 */
public class consumirWS extends AsyncTask<String, Void, String> {

     String SOAP_ACTION = "urn:servicio#registrarDetalleVenta";
     String METHOD = "registrarDetalleVenta";
     final String NAMESPACE = "urn:servicio";
     final String ENDPOINTWS = "http://taddibrisa.com/Copias/wsCopias/servicio.php";
    SoapObject userRequest ;
    String respuesta = null;
    Gson gson = new Gson();
    private Conexion conexion =null;
    private Context context;
    ProgressDialog dialog=null;
    private Activity actividad;
    public consumirWS(Context c,Activity a)
    {
        this.context =c;
        this.actividad =a;
        conexion = new Conexion(c);
        conexion.Abrr();
    }

    public int WebServiceVentaTotal(Cursor c)
    {
        SOAP_ACTION = "urn:servicio#registrarVentaTotal";
        METHOD = "registrarVentaTotal";
        userRequest = new SoapObject(NAMESPACE, METHOD);
        userRequest.addProperty("cantidadCopiasTotal",c.getInt(2));
        userRequest.addProperty("TotalVentas", c.getInt(3));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(userRequest);

        try{
            HttpTransportSE androidHttpTransport = new HttpTransportSE(ENDPOINTWS);
            androidHttpTransport.debug = true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            respuesta = envelope.getResponse().toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  Integer.parseInt(respuesta);
    }

    public int WebServiceregistrarDetalleVentaIndividual(Cursor c,int idVenta)
    {
        SOAP_ACTION = "urn:servicio#registrarDetalleVentaIndividual";
        METHOD = "registrarDetalleVentaIndividual";
        userRequest = new SoapObject(NAMESPACE, METHOD);

        userRequest.addProperty("idVenta", idVenta);
        userRequest.addProperty("idRangoXTipoCopiaBN", c.getString(2));
        userRequest.addProperty("cantidadCopiasBN",c.getString(3));
        userRequest.addProperty("costoCopiasBN", c.getString(4));
        userRequest.addProperty("idCatTipoCopia", c.getString(5));
        userRequest.addProperty("idPorcentajeCopiaColor", c.getString(6));
        userRequest.addProperty("cantidadCopiasColor", c.getString(7));
        userRequest.addProperty("costoCopiasColor", c.getString(8));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(userRequest);

        try{
            HttpTransportSE androidHttpTransport = new HttpTransportSE(ENDPOINTWS);
            androidHttpTransport.debug = true;
            androidHttpTransport.call(SOAP_ACTION, envelope);
            respuesta = envelope.getResponse().toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  Integer.parseInt(respuesta);
    }



    @Override
    protected String doInBackground(String... args) {

        try{
        List<ItemCopia> datosVenta = new ArrayList<ItemCopia>();
        String query = "SELECT * FROM ventas ORDER BY fechaVenta";

        Cursor c = conexion.db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
               int idVentaRemoto= WebServiceVentaTotal(c);

                String queryDetalleVenta="SELECT * FROM  detallesventa WHERE idVenta ="+c.getString(0);
                Cursor cDetalleVenta = conexion.db.rawQuery(queryDetalleVenta,null);
                if(cDetalleVenta.moveToFirst()){
                    do{
                        WebServiceregistrarDetalleVentaIndividual(cDetalleVenta,idVentaRemoto);


                    }while (cDetalleVenta.moveToNext());
                }
            }while (c.moveToNext());
            conexion.db.execSQL("DELETE FROM detallesventa");
            conexion.db.execSQL("DELETE FROM ventas");
        }

        }catch (Exception ex){

            Log.d("Error en",ex.getMessage());
        }
        return respuesta;
    }

    protected void onPostExecute(String result)
    {
        dialog.dismiss();
        Toast.makeText(this.context, "Datos actualizados exitosamente",
                Toast.LENGTH_SHORT).show();
        super.onPostExecute(String.valueOf(result));
    }

    @Override
    protected void onPreExecute() {
        try{
        dialog = new ProgressDialog(this.actividad);
        dialog.setMessage("Sincronizando la aplicaciòn con el sitio...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        }
        catch (Exception ex)
        {
            Log.d("error en ",ex.getMessage());
        }

    }
}
