/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;

/**
 *
 * @author oskar
 */

public class Diagnostico extends OrdenServicio {

    public Diagnostico(String c, String cl, String d, double h, Zona z) {
        super(c, cl, d, h, z);
        if (h > 1.5) {
            throw new IllegalArgumentException("Diagnostico maximo 1.5 horas");
        }
    }

    public double calcularCosto() {
        return getZona() == Zona.METROPOLITANA ? 150 : 225;
    }

    public int calcularDiasAtencion() {
        return getZona() == Zona.METROPOLITANA ? 1 : 2;
    }

    public int obtenerMaxVisitas() {
        return 3;
    }

    public String obtenerCategoria() {
        return "Diagnostico";
    }
}
