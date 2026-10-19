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
public class SistemaTecniHogar {

    private final ArrayList<OrdenServicio> ordenes = new ArrayList<>();
    private final ArrayList<Cuadrilla> cuadrillas = new ArrayList<>();
    private final ArrayList<Jornada> jornadas = new ArrayList<>();
    private double ingresosRegistro, ingresosVisitas;
    private final Map<String, Double> ingresosCategoria = new LinkedHashMap<>();

    public SistemaTecniHogar() {
        ordenes.add(new Diagnostico("D01", "Ana Lopez", "Zona 10", 1, Zona.METROPOLITANA));
        ordenes.add(new Diagnostico("D02", "Luis Perez", "Mixco", 1.5, Zona.PERIFERICA));
        ordenes.add(new Instalacion("I01", "Marta Ruiz", "Zona 4", 2.5, Zona.METROPOLITANA, 5));
        ordenes.add(new Instalacion("I02", "Pablo Diaz", "Villa Nueva", 3, Zona.PERIFERICA, 2));
        ordenes.add(new InstalacionCertificada("C01", "Rosa Gil", "Zona 1", 2.5, Zona.METROPOLITANA, 5, 2000));
        ordenes.add(new InstalacionCertificada("C02", "Juan Solis", "Zona 15", 1, Zona.METROPOLITANA, 2, 500));
        ordenes.add(new MantenimientoAire("A01", "Eva Cruz", "Zona 9", 1.5, Zona.METROPOLITANA, 24000));
        ordenes.add(new MantenimientoAire("A02", "Mario Leon", "Zona 14", 2, Zona.METROPOLITANA, 36000));
        cuadrillas.add(new Cuadrilla("Q01", 8, true));
        cuadrillas.add(new Cuadrilla("Q02", 6, false));
        cuadrillas.add(new Cuadrilla("Q03", 10, true));
    }

    public double cotizar(OrdenServicio o) {
        return o.calcularCosto();
    }

    public void registrarOrden(OrdenServicio o) {
        for (OrdenServicio x : ordenes) {
            if (x.getCodigo().equalsIgnoreCase(o.getCodigo())) {
                throw new IllegalArgumentException("Codigo repetido");
            }
        }
        ordenes.add(o);
        ingresosRegistro += o.calcularCosto();
        ingresosCategoria.put(o.obtenerCategoria(), ingresosCategoria.getOrDefault(o.obtenerCategoria(), 0.0) + o.calcularCosto());
    }

    public void registrarCuadrilla(Cuadrilla q) {
        for (Cuadrilla x : cuadrillas) {
            if (x.getCodigo().equalsIgnoreCase(q.getCodigo())) {
                throw new IllegalArgumentException("Codigo repetido");
            }
        }
        cuadrillas.add(q);
    }

    public Jornada crearJornada(String c, Cuadrilla q) {
        if (buscarJornada(c) != null) {
            throw new IllegalArgumentException("Codigo de jornada repetido");
        }
        if (!cuadrillas.contains(q) || !q.estaDisponible()) {
            throw new IllegalStateException("Cuadrilla no disponible");
        }
        Jornada j = new Jornada(c, q);
        jornadas.add(j);
        q.asignarJornada(j);
        return j;
    }

    public void iniciarJornada(Jornada j) {
        double extra = 0;
        for (OrdenServicio o : j.getOrdenes()) {
            if (o.getVisitasRealizadas() > 0) {
                extra += 40;
                ingresosCategoria.put(o.obtenerCategoria(), ingresosCategoria.getOrDefault(o.obtenerCategoria(), 0.0) + 40);
            }
        }
        j.iniciar();
        ingresosVisitas += extra;
    }

    public OrdenServicio buscarOrden(String c) {
        for (OrdenServicio o : ordenes) {
            if (o.getCodigo().equalsIgnoreCase(c)) {
                return o;
            }
        }
        return null;
    }

    public Cuadrilla buscarCuadrilla(String c) {
        for (Cuadrilla q : cuadrillas) {
            if (q.getCodigo().equalsIgnoreCase(c)) {
                return q;
            }
        }
        return null;
    }

    public Jornada buscarJornada(String c) {
        for (Jornada j : jornadas) {
            if (j.getCodigo().equalsIgnoreCase(c)) {
                return j;
            }
        }
        return null;
    }

    public List<OrdenServicio> getOrdenes() {
        return new ArrayList<>(ordenes);
    }

    public List<Cuadrilla> getCuadrillas() {
        return new ArrayList<>(cuadrillas);
    }

    public double getIngresosRegistro() {
        return ingresosRegistro;
    }

    public double getIngresosVisitas() {
        return ingresosVisitas;
    }

    public void generarReporteOrdenes() {
        Map<String, Integer> m = new LinkedHashMap<>();
        for (OrdenServicio o : ordenes) {
            String k = o.obtenerCategoria() + " / " + o.getEstado();
            m.put(k, m.getOrDefault(k, 0) + 1);
        }
        System.out.println("Ordenes por categoria y estado");
        m.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public void generarReporteIngresos() {
        System.out.printf("Registro Q%.2f | visitas Q%.2f | total Q%.2f%n", ingresosRegistro, ingresosVisitas, ingresosRegistro + ingresosVisitas);
        ingresosCategoria.forEach((k, v) -> System.out.printf("%s Q%.2f%n", k, v));
    }

    public void generarReporteJornadas() {
        boolean hay = false;
        for (Jornada j : jornadas) {
            if (j.getEstado() != EstadoJornada.COMPLETO) {
                hay = true;
                System.out.println(j.resumen());
                for (OrdenServicio o : j.getOrdenes()) {
                    System.out.println("  " + o.resumen());
                }
            }
        }
        if (!hay) {
            System.out.println("No hay jornadas abiertas");
        }
    }
}
