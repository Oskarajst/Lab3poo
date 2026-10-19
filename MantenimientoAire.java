/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;
/**
 *
 * @author oskar
 */
public class MantenimientoAire extends OrdenServicio {

    private final int btu;

    public MantenimientoAire(String c, String cl, String d, double h, Zona z, int b) {
        super(c, cl, d, h, z);
        if (z != Zona.METROPOLITANA) {
            throw new IllegalArgumentException("Aire solo en zona metropolitana");
        }
        if (b < 9000 || b > 60000) {
            throw new IllegalArgumentException("BTU debe estar entre 9000 y 60000");
        }
        btu = b;
    }

    public double calcularCosto() {
        return 180 + 110 * getHorasEstimadas();
    }

    public int calcularDiasAtencion() {
        return 2;
    }

    public int obtenerMaxVisitas() {
        return 1;
    }

    public String obtenerCategoria() {
        return "Mantenimiento aire";
    }

    @Override
    public boolean compatible(Cuadrilla c) {
        return c != null && c.tieneCertificacion();
    }
}
