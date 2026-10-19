/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;
/**
 *
 * @author oskar
 */
public abstract class OrdenServicio {

    private final String codigo, cliente, direccion;
    private final double horas;
    private final Zona zona;
    private EstadoOrden estado = EstadoOrden.PENDIENTE;
    private int visitas;
    private Jornada jornada;
    private double ingresoExtra;

    protected OrdenServicio(String c, String cl, String d, double h, Zona z) {
        if (c == null || c.trim().isEmpty() || cl == null || cl.trim().isEmpty() || d == null || d.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo cliente y direccion son obligatorios");
        }
        if (!Double.isFinite(h) || h <= 0) {
            throw new IllegalArgumentException("Las horas deben ser positivas");
        }
        if (z == null) {
            throw new IllegalArgumentException("Zona invalida");
        }
        codigo = c.trim();
        cliente = cl.trim();
        direccion = d.trim();
        horas = h;
        zona = z;
    }

    public abstract double calcularCosto();

    public abstract int calcularDiasAtencion();

    public abstract int obtenerMaxVisitas();

    public abstract String obtenerCategoria();

    public boolean compatible(Cuadrilla c) {
        return c != null;
    }

    public void asignar(Jornada j) {
        if (estado != EstadoOrden.PENDIENTE || jornada != null) {
            throw new IllegalStateException("Orden no disponible");
        }
        jornada = j;
        estado = EstadoOrden.ASIGNADO;
    }

    public void retirar() {
        if (estado != EstadoOrden.ASIGNADO) {
            throw new IllegalStateException("Orden no asignada");
        }
        jornada = null;
        estado = EstadoOrden.PENDIENTE;
    }

    public boolean iniciar() {
        if (estado != EstadoOrden.ASIGNADO) {
            return false;
        }
        if (visitas > 0) {
            ingresoExtra += 40;
        }
        estado = EstadoOrden.EN_ATENCION;
        return true;
    }

    public void registrarResultado(boolean ok) {
        if (estado != EstadoOrden.EN_ATENCION) {
            throw new IllegalStateException("Orden no esta en atencion");
        }
        visitas++;
        estado = ok ? EstadoOrden.COMPLETADO : (visitas >= obtenerMaxVisitas() ? EstadoOrden.CANCELADO : EstadoOrden.PENDIENTE);
        jornada = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getHorasEstimadas() {
        return horas;
    }

    public Zona getZona() {
        return zona;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public int getVisitasRealizadas() {
        return visitas;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public double getIngresoVisitas() {
        return ingresoExtra;
    }

    public String resumen() {
        return codigo + " | " + obtenerCategoria() + " | " + cliente + " | " + zona + " | " + horas + " h | " + estado + " | visitas " + visitas + " | Q" + String.format("%.2f", calcularCosto());
    }
}
