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
    private static final int __VERSION = 5;
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
        Log.d("el script actual es ",bd);
        db.execSQL(bd);
        db.execSQL(tablaCatTipoCopias);
        db.execSQL(tablaVentas);
        db.execSQL(tablaRegistroVentas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS "+CatTipoCopias);
            db.execSQL( "DROP TABLE IF EXISTS "+ Ventas);
            db.execSQL( "DROP TABLE IF EXISTS "+TotalVentas);
            //y luego creamos la nueva tabla
            db.execSQL(tablaCatTipoCopias);
            db.execSQL(tablaVentas);
            db.execSQL(tablaRegistroVentas);

            db.execSQL("INSERT INTO " + conexion.CatTipoCopias + " values (1,'color',3.2)");
            db.execSQL("INSERT INTO " + conexion.CatTipoCopias + " values (2,'Blanco / Negro',2.5)");
        }
    }

    public  void Abrr()
    {
        this.db = this.getWritableDatabase();

    }

    public final String bd = "  CREATE TABLE catporcentaje (\n" +
            "  idPorcentaje int(11) NOT NULL AUTOINCREMENT,\n" +
            "  descripcion varchar(100) NOT NULL\n" +
            ")\n -- ----------------------------\n" +
            "-- Records of catporcentaje\n" +
            "-- ----------------------------\n" +
            "INSERT INTO catporcentaje VALUES ('1', '20%');\n" +
            "INSERT INTO catporcentaje VALUES ('2', '50%');\n" +
            "INSERT INTO catporcentaje VALUES ('3', '100%');\n" +
            "\n -- ----------------------------\n" +
            "-- Table structure for cattipocopia\n" +
            "-- ----------------------------\n"+
            "CREATE TABLE cattipocopia (\n" +
            "  idCatTipoCopia int(11) INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
            "  Descripcion varchar(100) NOT NULL )\n"+
            "-- ----------------------------\n" +
            "-- Records of cattipocopia\n" +
            "-- ----------------------------\n" +
            "INSERT INTO cattipocopia VALUES ('1', 'Carta');\n" +
            "INSERT INTO cattipocopia VALUES ('2', 'Oficio');\n" +
            "INSERT INTO cattipocopia VALUES ('3', 'Doble Carta');\n" +
            "\n" +"-- ----------------------------\n" +
            "-- Table structure for detallesventa\n" +
            "-- ----------------------------\n" +
            "CREATE TABLE detallesventa (\n" +
            "  idDetalleVenta int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
            "  idVenta int(11) NOT NULL,\n" +
            "  idRangoXTipoCopiaBN int(11) DEFAULT NULL,\n" +
            "  cantidadCopiasBN int(11) DEFAULT NULL,\n" +
            "  costoCopiasBN decimal(10,2) DEFAULT NULL,\n" +
            "  idCatTipoCopia int(11) DEFAULT NULL,\n" +
            "  idPorcentajeCopiaColor int(11) DEFAULT NULL,\n" +
            "  cantidadCopiasColor int(11) DEFAULT NULL,\n" +
            "  costoCopiasColor decimal(10,2) DEFAULT NULL,\n" +
            "-- ----------------------------\n" +
            "-- Records of detallesventa\n" +
            "-- ----------------------------\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for porcentajexcopiacolor\n" +
            "-- ----------------------------\n" +
            "CREATE TABLE porcentajexcopiacolor (\n" +
            "  idPorcentajeCopiaColor int(11) NOT NULL AUTOINCREMENT,\n" +
            "  idRangoXTipoCopiaColor int(11) NOT NULL,\n" +
            "  IdPorcentaje int(11) NOT NULL,\n" +
            "  costo decimal(10,2) NOT NULL,\n" +
            "  PRIMARY KEY (idPorcentajeCopiaColor),\n" +
            "  KEY porcentajexcopiacolor_ibfk_1 (idRangoXTipoCopiaColor),\n" +
            "  KEY porcentajexcopiacolor_ibfk_2 (IdPorcentaje),\n" +
            "  CONSTRAINT porcentajexcopiacolor_ibfk_1 FOREIGN KEY (idRangoXTipoCopiaColor) REFERENCES rangosxtipocopiacolor (idRangoXTipoCopiaColor) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT porcentajexcopiacolor_ibfk_2 FOREIGN KEY (IdPorcentaje) REFERENCES catporcentaje (idPorcentaje) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") \n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Records of porcentajexcopiacolor\n" +
            "-- ----------------------------\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('1', '1', '1', '2.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('2', '1', '2', '3.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('3', '1', '3', '5.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('4', '2', '1', '1.80');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('5', '2', '2', '2.70');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('6', '2', '3', '4.50');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('7', '3', '1', '1.50');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('8', '3', '2', '2.40');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('9', '3', '3', '4.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('10', '4', '1', '3.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('11', '4', '2', '4.50');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('12', '4', '3', '7.50');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('13', '5', '1', '2.70');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('14', '5', '2', '4.05');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('15', '5', '3', '6.75');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('16', '6', '1', '2.40');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('17', '6', '2', '3.60');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('18', '6', '3', '6.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('19', '7', '1', '4.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('20', '7', '2', '6.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('21', '7', '3', '10.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('22', '8', '1', '3.60');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('23', '8', '2', '5.40');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('24', '8', '3', '9.00');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('25', '9', '1', '3.20');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('26', '9', '2', '4.80');\n" +
            "INSERT INTO porcentajexcopiacolor VALUES ('27', '9', '3', '8.00');\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for rangosxtipocopiabn\n" +
            "-- ----------------------------\n" +
            "DROP TABLE IF EXISTS rangosxtipocopiabn;\n" +
            "CREATE TABLE rangosxtipocopiabn (\n" +
            "  idRangoXTipoCopiaBN int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  min int(11) DEFAULT NULL,\n" +
            "  max int(11) DEFAULT NULL,\n" +
            "  costo decimal(10,2) DEFAULT NULL,\n" +
            "  idCatTipoCopia int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (idRangoXTipoCopiaBN),\n" +
            "  KEY idCatTipoCopia (idCatTipoCopia),\n" +
            "  CONSTRAINT rangosxtipocopiabn_ibfk_1 FOREIGN KEY (idCatTipoCopia) REFERENCES cattipocopia (idCatTipoCopia) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") \n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Records of rangosxtipocopiabn\n" +
            "-- ----------------------------\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('1', '1', '5', '1.00', '1');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('2', '6', '99', '0.50', '1');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('3', '100', '999', '0.35', '1');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('4', '1000', '99999', '0.30', '1');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('5', '1', '5', '1.50', '2');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('6', '6', '99', '0.75', '2');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('7', '100', '999', '0.53', '2');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('8', '1000', '99999', '0.45', '2');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('9', '1', '5', '2.00', '3');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('10', '6', '99', '1.00', '3');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('11', '100', '999', '0.70', '3');\n" +
            "INSERT INTO rangosxtipocopiabn VALUES ('12', '1000', '99999', '0.60', '3');\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for rangosxtipocopiacolor\n" +
            "-- ----------------------------\n" +
            "DROP TABLE IF EXISTS rangosxtipocopiacolor;\n" +
            "CREATE TABLE rangosxtipocopiacolor (\n" +
            "  idRangoXTipoCopiaColor int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  min int(11) DEFAULT NULL,\n" +
            "  max int(11) DEFAULT NULL,\n" +
            "  idCatTipoCopia int(11) NOT NULL,\n" +
            "  PRIMARY KEY (idRangoXTipoCopiaColor),\n" +
            "  KEY rangosxtipocopiacolor_ibfk_1 (idCatTipoCopia),\n" +
            "  CONSTRAINT rangosxtipocopiacolor_ibfk_1 FOREIGN KEY (idCatTipoCopia) REFERENCES cattipocopia (idCatTipoCopia) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") \n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Records of rangosxtipocopiacolor\n" +
            "-- ----------------------------\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('1', '1', '24', '1');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('2', '10', '49', '1');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('3', '50', '99999', '1');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('4', '1', '24', '2');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('5', '10', '49', '2');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('6', '50', '99999', '2');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('7', '1', '24', '3');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('8', '10', '49', '3');\n" +
            "INSERT INTO rangosxtipocopiacolor VALUES ('9', '50', '99999', '3');\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for ventas\n" +
            "-- ----------------------------\n" +
            "DROP TABLE IF EXISTS ventas;\n" +
            "CREATE TABLE ventas (\n" +
            "  idVenta int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  fechaVenta datetime NOT NULL,\n" +
            "  cantidadCopiasTotal int(11) NOT NULL,\n" +
            "  Total decimal(10,2) NOT NULL,\n" +
            "  PRIMARY KEY (idVenta)\n" +
            ") \n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Records of ventas\n" +
            "-- ----------------------------\n";

}

