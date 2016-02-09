package com.example.brisa.copy;

/**
 * Created by ChristianPR on 06/02/2016.
 */
public class ItemCopia {

    private int imagen;
    private String tipoCopia;
    private String costo;
    private String cantidadCopias;
    private int idRangoXTipoCopiaBN;
    private int cantidadCopiasBN;
    private double costoCopiasBN;
    private int idCatTipoCopia;
    private int idPorcentajeCopiaColor;
    private int cantidadCopiasColor;
    private double costoCopiasColor;

    public ItemCopia() {

    }

    public ItemCopia(int image, String tipoCopia, String costo,String cantidadCopias) {
        super();
        this.imagen = image;
        this.tipoCopia = tipoCopia;
        this.costo = costo;
        this.cantidadCopias = cantidadCopias;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getTipoCopia() {
        return tipoCopia;
    }

    public void setTipoCopia(String tipoCopia) {
        this.tipoCopia = tipoCopia;
    }

    public int getIdRangoXTipoCopiaBN() {
        return idRangoXTipoCopiaBN;
    }

    public void setIdRangoXTipoCopiaBN(int idRangoXTipoCopiaBN) {
        this.idRangoXTipoCopiaBN = idRangoXTipoCopiaBN;
    }

    public int getCantidadCopiasBN() {
        return cantidadCopiasBN;
    }

    public void setCantidadCopiasBN(int cantidadCopiasBN) {
        this.cantidadCopiasBN = cantidadCopiasBN;
    }

    public double getCostoCopiasBN() {
        return costoCopiasBN;
    }

    public void setCostoCopiasBN(double costoCopiasBN) {
        this.costoCopiasBN = costoCopiasBN;
    }

    public int getIdCatTipoCopia() {
        return idCatTipoCopia;
    }

    public void setIdCatTipoCopia(int idCatTipoCopia) {
        this.idCatTipoCopia = idCatTipoCopia;
    }

    public int getIdPorcentajeCopiaColor() {
        return idPorcentajeCopiaColor;
    }

    public void setIdPorcentajeCopiaColor(int idPorcentajeCopiaColor) {
        this.idPorcentajeCopiaColor = idPorcentajeCopiaColor;
    }

    public int getCantidadCopiasColor() {
        return cantidadCopiasColor;
    }

    public void setCantidadCopiasColor(int cantidadCopiasColor) {
        this.cantidadCopiasColor = cantidadCopiasColor;
    }

    public double getCostoCopiasColor() {
        return costoCopiasColor;
    }

    public void setCostoCopiasColor(double costoCopiasColor) {
        this.costoCopiasColor = costoCopiasColor;
    }

    public String getCantidadCopias() {
        return cantidadCopias;
    }

    public void setCantidadCopias(String cantidadCopias) {
        this.cantidadCopias = cantidadCopias;
    }
}
