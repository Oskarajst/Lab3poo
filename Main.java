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
public class Main {

    private static final Scanner s = new Scanner(System.in);
    private static final SistemaTecniHogar sys = new SistemaTecniHogar();

    public static void main(String[] a) {
        boolean salir = false;
        while (!salir) {
            try {
                System.out.println("\n1 cotizar/registrar 2 cuadrilla 3 jornada 4 asignar 5 retirar 6 iniciar 7 resultado 8 ordenes 9 cuadrillas 10 reporte ordenes 11 ingresos 12 jornadas 0 salir");
                int op = entero("Opcion: ");
                switch (op) {
                    case 1:
                        orden();
                        break;
                    case 2:
                        cuadrilla();
                        break;
                    case 3:
                        jornada();
                        break;
                    case 4:
                        asignar();
                        break;
                    case 5:
                        retirar();
                        break;
                    case 6:
                        iniciar();
                        break;
                    case 7:
                        resultado();
                        break;
                    case 8:
                        for (OrdenServicio o : sys.getOrdenes()) {
                            System.out.println(o.resumen() + " | dias " + o.calcularDiasAtencion());
                        }
                        break;
                    case 9:
                        for (Cuadrilla q : sys.getCuadrillas()) {
                            System.out.println(q.resumen());
                        }
                        break;
                    case 10:
                        sys.generarReporteOrdenes();
                        break;
                    case 11:
                        sys.generarReporteIngresos();
                        break;
                    case 12:
                        sys.generarReporteJornadas();
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Operacion rechazada: " + e.getMessage());
            }
        }
    }

    private static String texto(String x) {
        System.out.print(x);
        return s.nextLine().trim();
    }

    private static int entero(String x) {
        while (true)try {
            return Integer.parseInt(texto(x));
        } catch (Exception e) {
            System.out.println("Ingrese un entero valido");
        }
    }

    private static double decimal(String x) {
        while (true)try {
            return Double.parseDouble(texto(x));
        } catch (Exception e) {
            System.out.println("Ingrese un numero valido");
        }
    }

    private static Zona zona() {
        while (true) {
            String z = texto("Zona M/P: ").toUpperCase();
            if (z.equals("M")) {
                return Zona.METROPOLITANA;
            }
            if (z.equals("P")) {
                return Zona.PERIFERICA;
            }
            System.out.println("Use M o P");
        }
    }

    private static void orden() {
        int t = entero("1 diagnostico 2 instalacion 3 certificada 4 aire: ");
        String c = texto("Codigo: "), n = texto("Cliente: "), d = texto("Direccion: ");
        double h = decimal("Horas: ");
        Zona z = zona();
        OrdenServicio o;
        switch (t) {
            case 1:
                o = new Diagnostico(c, n, d, h, z);
                break;
            case 2:
                o = new Instalacion(c, n, d, h, z, entero("Puntos: "));
                break;
            case 3:
                o = new InstalacionCertificada(c, n, d, h, z, entero("Puntos: "), decimal("Materiales: "));
                break;
            case 4:
                o = new MantenimientoAire(c, n, d, h, z, entero("BTU: "));
                break;
            default:
                throw new IllegalArgumentException("Categoria invalida");
        }
        System.out.printf("Costo Q%.2f | dias %d%n", sys.cotizar(o), o.calcularDiasAtencion());
        if (texto("Registrar S/N: ").equalsIgnoreCase("S")) {
            sys.registrarOrden(o);
            System.out.println("Registrada");
        }
    }

    private static void cuadrilla() {
        sys.registrarCuadrilla(new Cuadrilla(texto("Codigo: "), decimal("Horas maximas: "), texto("Certificada S/N: ").equalsIgnoreCase("S")));
    }

    private static void jornada() {
        String c = texto("Codigo jornada: ");
        Cuadrilla q = sys.buscarCuadrilla(texto("Codigo cuadrilla: "));
        if (q == null) {
            throw new IllegalArgumentException("Cuadrilla no existe");
        }
        sys.crearJornada(c, q);
    }

    private static void asignar() {
        Jornada j = sys.buscarJornada(texto("Jornada: "));
        OrdenServicio o = sys.buscarOrden(texto("Orden: "));
        if (j == null || o == null) {
            throw new IllegalArgumentException("No se encontro jornada u orden");
        }
        j.asignarOrden(o);
    }

    private static void retirar() {
        Jornada j = sys.buscarJornada(texto("Jornada: "));
        OrdenServicio o = sys.buscarOrden(texto("Orden: "));
        if (j == null || o == null) {
            throw new IllegalArgumentException("No se encontro jornada u orden");
        }
        j.retirarOrden(o);
    }

    private static void iniciar() {
        Jornada j = sys.buscarJornada(texto("Jornada: "));
        if (j == null) {
            throw new IllegalArgumentException("Jornada no existe");
        }
        sys.iniciarJornada(j);
    }

    private static void resultado() {
        Jornada j = sys.buscarJornada(texto("Jornada: "));
        OrdenServicio o = sys.buscarOrden(texto("Orden: "));
        if (j == null || o == null) {
            throw new IllegalArgumentException("No se encontro jornada u orden");
        }
        j.registrarResultado(o, texto("Completada S/N: ").equalsIgnoreCase("S"));
    }
}
