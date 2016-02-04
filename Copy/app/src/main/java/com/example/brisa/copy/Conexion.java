package com.example.brisa.copy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Brisa on 02/02/16.
 */
public class Conexion extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    private  Conexion conexion;
    private static final String __DATABASE = "dbCopy";
    //versión de la base de datos
    private static final int __VERSION = 17;
    //nombre tabla y campos de tabla
    public final String __tabla__ = "persona";
    public final String CatTipoCopias = "CatTipoCopias";
    public final String Ventas = "Ventas";
    public final String TotalVentas = "TotalVentas";
    public final String __campo_id = "id";
    public final String __campo_nombre = "nombre";
    public final String __campo_apellido = "apellido";
    public final String __campo_sexo = "sexo";
    //Instrucción SQL para crear las tablas
    private final String sql = "CREATE TABLE " + __tabla__ +
            " ( " + __campo_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + __campo_nombre + " TEXT, "
            + __campo_apellido + " TEXT, "
            + __campo_sexo + " TEXT )";

    private final String tablaCatTipoCopias = "CREATE TABLE "+CatTipoCopias+
            " ( idCatTipoCopias INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "   descripcion varchar not null ," +
            "   valor float not null)";

    private final String tablaVentas = "CREATE TABLE "+Ventas+
            " ( idVenta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "   idUsuario int NOT NULL)";

    private final String tablaRegistroVentas = "CREATE TABLE "+TotalVentas+
            " ( idTotalVentas INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "idVenta int not null,"+
            "fechaVenta datetime not null," +
            "idUsuario int NOT NULL," +
            "idCatTipoCopias int not null," +
            "TotalVenta float not null )";

    public Conexion(Context context)
    {
        super( context, __DATABASE, null, __VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreaBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS catporcentaje");
            db.execSQL("DROP TABLE IF EXISTS cattipocopia");
            db.execSQL( "DROP TABLE IF EXISTS detallesventa");
            db.execSQL( "DROP TABLE IF EXISTS rangosxtipocopiabn");
            db.execSQL( "DROP TABLE IF EXISTS porcentajexcopiacolor");
            db.execSQL( "DROP TABLE IF EXISTS detallesventa");
            db.execSQL( "DROP TABLE IF EXISTS rangosxtipocopiacolor");
            db.execSQL( "DROP TABLE IF EXISTS ventas");
            CreaBase(db);

            //y luego creamos la nueva tabla
            /*db.execSQL(tablaCatTipoCopias);
            db.execSQL(tablaVentas);
            db.execSQL(tablaRegistroVentas);

            db.execSQL("INSERT INTO " + conexion.CatTipoCopias + " values (1,color,3.2)");
            db.execSQL("INSERT INTO " + conexion.CatTipoCopias + " values (2,Blanco / Negro,2.5)");*/
        }
    }

    public  void Abrr()
    {
        this.db = this.getWritableDatabase();

    }

    public void CreaBase(SQLiteDatabase db)
    {
 try {
     db.execSQL("CREATE TABLE catporcentaje (" +
             "  idPorcentaje INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  descripcion varchar(100) NOT NULL)");

     db.execSQL("INSERT INTO catporcentaje VALUES (1, '20%')");
     db.execSQL("INSERT INTO catporcentaje VALUES (2, '50%')");
     db.execSQL("INSERT INTO catporcentaje VALUES (3, '100%')");


     db.execSQL("CREATE TABLE cattipocopia (\n" +
             "  idCatTipoCopia INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  Descripcion TEXT NOT NULL )");

     db.execSQL("INSERT INTO cattipocopia VALUES (1, 'Carta')");
     db.execSQL("INSERT INTO cattipocopia VALUES (2, 'Oficio')");
     db.execSQL("INSERT INTO cattipocopia VALUES (3, 'Doble Carta')");

     db.execSQL("CREATE TABLE detallesventa (\n" +
             "  idDetalleVenta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  idVenta INTEGER NOT NULL,\n" +
             "  idRangoXTipoCopiaBN INTEGER ,\n" +
             "  cantidadCopiasBN INTEGER ,\n" +
             "  costoCopiasBN NUMERIC ,\n" +
             "  idCatTipoCopia INTEGER ,\n" +
             "  idPorcentajeCopiaColor INTEGER ,\n" +
             "  cantidadCopiasColor INTEGER ,\n" +
             "  costoCopiasColor NUMERIC )");

     db.execSQL("CREATE TABLE porcentajexcopiacolor (\n" +
             "  idPorcentajeCopiaColor INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  idRangoXTipoCopiaColor INTEGER NOT NULL,\n" +
             "  IdPorcentaje INTEGER NOT NULL,\n" +
             "  costo NUMERIC NOT NULL)");

     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (1, 1, 1, 2.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (2, 1, 2, 3.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (3, 1, 3, 5.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (4, 2, 1, 1.80)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (5, 2, 2, 2.70)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (6, 2, 3, 4.50)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (7, 3, 1, 1.50)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (8, 3, 2, 2.40)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (9, 3, 3, 4.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (10, 4, 1, 3.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (11, 4, 2, 4.50)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (12, 4, 3, 7.50)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (13, 5, 1, 2.70)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (14, 5, 2, 4.05)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (15, 5, 3, 6.75)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (16, 6, 1, 2.40)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (17, 6, 2, 3.60)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (18, 6, 3, 6.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (19, 7, 1, 4.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (20, 7, 2, 6.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (21, 7, 3, 10.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (22, 8, 1, 3.60)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (23, 8, 2, 5.40)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (24, 8, 3, 9.00)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (25, 9, 1, 3.20)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (26, 9, 2, 4.80)");
     db.execSQL(" INSERT INTO porcentajexcopiacolor VALUES (27, 9, 3, 8.00)");

     db.execSQL("CREATE TABLE rangosxtipocopiabn (\n" +
             "  idRangoXTipoCopiaBN INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  min INTEGER ,\n" +
             "  max INTEGER ,\n" +
             "  costo NUMERIC ,\n" +
             "  idCatTipoCopia INTEGER )");

     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (1, 1, 5, 1.00, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (2, 6, 99, 0.50, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (3, 100, 999, 0.35, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (4, 1000, 99999, 0.30, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (5, 1, 5, 1.50, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (6, 6, 99, 0.75, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (7, 100, 999, 0.53, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (8, 1000, 99999, 0.45, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (9, 1, 5, 2.00, 3)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (10, 6, 99, 1.00, 3)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (11, 100, 999, 0.70, 3)");
     db.execSQL(" INSERT INTO rangosxtipocopiabn VALUES (12, 1000, 99999, 0.60, 3)");

     db.execSQL("CREATE TABLE rangosxtipocopiacolor (\n" +
             "  idRangoXTipoCopiaColor INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  min INTEGER ,\n" +
             "  max INTEGER ,\n" +
             "  idCatTipoCopia INTEGER NOT NULL)");

     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (1, 1, 24, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (2, 10, 49, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (3, 50, 99999, 1)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (4, 1, 24, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (5, 10, 49, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (6, 50, 99999, 2)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (7, 1, 24, 3)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (8, 10, 49, 3)");
     db.execSQL(" INSERT INTO rangosxtipocopiacolor VALUES (9, 50, 99999, 3)");

     db.execSQL("CREATE TABLE ventas (\n" +
             "  idVenta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
             "  fechaVenta datetime NOT NULL,\n" +
             "  cantidadCopiasTotal INTEGER NOT NULL,\n" +
             "  Total NUMERIC NOT NULL)");

 }catch (Exception ex)
 {
     Log.d("Error en " ,ex.getMessage());
 }
    }

}

