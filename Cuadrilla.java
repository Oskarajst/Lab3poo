/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;

/**
 *
 * @author oskar
 */

public class Cuadrilla {

    private final String codigo;
    private final double capacidad;
    private final boolean certificada;
    private Jornada activa;

    public Cuadrilla(String c, double h, boolean cert) {
        if (c == null || c.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo obligatorio");
        }
        if (!Double.isFinite(h) || h <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser positiva");
        }
        codigo = c.trim();
        capacidad = h;
        certificada = cert;
    }

    public boolean estaDisponible() {
        return activa == null || activa.getEstado() == EstadoJornada.COMPLETO;
    }

    public boolean tieneCertificacion() {
        return certificada;
    }

    public double getCapacidadHoras() {
        return capacidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void asignarJornada(Jornada j) {
        if (!estaDisponible()) {
            throw new IllegalStateException("Cuadrilla ocupada");
        }
        activa = j;
    }

    public void liberarJornada() {
        activa = null;
    }

    public String resumen() {
        return codigo + " | " + capacidad + " h | refrigeracion " + (certificada ? "si" : "no") + " | disponible " + estaDisponible();
    }
}
