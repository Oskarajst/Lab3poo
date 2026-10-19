/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;

/**
 *
 * @author oskar
 */

public class Instalacion extends OrdenServicio {

    private final int puntos;

    public Instalacion(String c, String cl, String d, double h, Zona z, int p) {
        super(c, cl, d, h, z);
        if (p <= 0) {
            throw new IllegalArgumentException("Puntos debe ser entero positivo");
        }
        puntos = p;
    }

    protected int horasFacturables() {
        return (int) Math.ceil(Math.max(getHorasEstimadas(), puntos * .75));
    }

    public double calcularCosto() {
        return (getZona() == Zona.METROPOLITANA ? 100 : 175) + 90.0 * horasFacturables();
    }

    public int calcularDiasAtencion() {
        return getZona() == Zona.METROPOLITANA ? 3 : 5;
    }

    public int obtenerMaxVisitas() {
        return 3;
    }

    public String obtenerCategoria() {
        return "Instalacion";
    }
}
