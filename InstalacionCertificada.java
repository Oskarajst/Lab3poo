/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;

/**
 *
 * @author oskar
 */

public class InstalacionCertificada extends Instalacion {

    private final double materiales;

    public InstalacionCertificada(String c, String cl, String d, double h, Zona z, int p, double m) {
        super(c, cl, d, h, z, p);
        if (!Double.isFinite(m) || m < 0) {
            throw new IllegalArgumentException("Materiales no pueden ser negativos");
        }
        materiales = m;
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + 120 + materiales * .05;
    }

    @Override
    public int calcularDiasAtencion() {
        return super.calcularDiasAtencion() + 2;
    }

    @Override
    public int obtenerMaxVisitas() {
        return 2;
    }

    @Override
    public String obtenerCategoria() {
        return "Instalacion certificada";
    }
}
