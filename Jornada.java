/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnihogar;

import java.util.*;
/**
 *
 * @author oskar
 */
public class Jornada {

    private final String codigo;
    private final Cuadrilla cuadrilla;
    private final ArrayList<OrdenServicio> ordenes = new ArrayList<>();
    private EstadoJornada estado = EstadoJornada.PREPARACION;

    public Jornada(String c, Cuadrilla q) {
        if (c == null || c.trim().isEmpty() || q == null) {
            throw new IllegalArgumentException("Codigo y cuadrilla requeridos");
        }
        codigo = c.trim();
        cuadrilla = q;
    }

    public void asignarOrden(OrdenServicio o) {
        if (estado != EstadoJornada.PREPARACION) {
            throw new IllegalStateException("Jornada ya iniciada");
        }
        if (o == null || o.getEstado() != EstadoOrden.PENDIENTE || o.getJornada() != null) {
            throw new IllegalArgumentException("Orden no disponible");
        }
        if (!o.compatible(cuadrilla)) {
            throw new IllegalArgumentException("Cuadrilla incompatible o sin certificado");
        }
        if (calcularHorasAsignadas() + o.getHorasEstimadas() > cuadrilla.getCapacidadHoras()) {
            throw new IllegalArgumentException("Se excede la capacidad");
        }
        o.asignar(this);
        ordenes.add(o);
    }

    public void retirarOrden(OrdenServicio o) {
        if (estado != EstadoJornada.PREPARACION || !ordenes.contains(o)) {
            throw new IllegalStateException("No se puede retirar");
        }
        ordenes.remove(o);
        o.retirar();
    }

    public int iniciar() {
        if (estado != EstadoJornada.PREPARACION || ordenes.isEmpty()) {
            throw new IllegalStateException("Jornada vacia o ya iniciada");
        }
        for (OrdenServicio o : ordenes) {
            o.iniciar();
        }
        estado = EstadoJornada.EN_CURSO;
        return ordenes.size();
    }

    public void registrarResultado(OrdenServicio o, boolean ok) {
        if (estado != EstadoJornada.EN_CURSO || !ordenes.contains(o) || o.getEstado() != EstadoOrden.EN_ATENCION) {
            throw new IllegalStateException("Orden no espera resultado");
        }
        o.registrarResultado(ok);
        boolean fin = true;
        for (OrdenServicio x : ordenes) {
            if (x.getEstado() == EstadoOrden.EN_ATENCION) {
                fin = false;
            }
        }
        if (fin) {
            estado = EstadoJornada.COMPLETO;
            cuadrilla.liberarJornada();
        }
    }

    public double calcularHorasAsignadas() {
        double n = 0;
        for (OrdenServicio o : ordenes) {
            n += o.getHorasEstimadas();
        }
        return n;
    }

    public double calcularCapacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - calcularHorasAsignadas();
    }

    public EstadoJornada getEstado() {
        return estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public List<OrdenServicio> getOrdenes() {
        return Collections.unmodifiableList(ordenes);
    }

    public String resumen() {
        return codigo + " | " + estado + " | " + cuadrilla.getCodigo() + " | horas " + String.format("%.2f", calcularHorasAsignadas()) + " | libres " + String.format("%.2f", calcularCapacidadDisponible());
    }
}
