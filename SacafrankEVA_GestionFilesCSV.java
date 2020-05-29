import java.util.*;
import java.io.*;
public class SacafrankEVA_GestionFilesCSV {
    public static void main(String[] args) {
        try {
            Locale ingles = new Locale("en", "EN");
            // lista de estudiantes
            String[] students = {"TENE S. MARIA", "FAGGIONI L. GIOVANNI", "LOPEZ Q. JAIME", "AVILA D. GREYS",
                    "LUZURIAGA C. DIANA", "MENDEZ C. JANETH", "CAMPOS D. FRANCISCA", "BORJA A. ANDREA",
                    "MENDOZA J. FATIMA", "SANCHEZ B. CARLA", "CASTILLO H. NESTOR", "SOLORZANO T. MARIBEL"};
            // creacion del archivo principal
            Formatter outArchivo = new Formatter
                    ("/home/franjo18/Documentos/studentCalif.csv", "US-ASCII", ingles);
            // asignacion de notas aleatorias a cada uno de los estudiantes
            for (String student : students) {
                outArchivo.format("%s;%.1f;%.1f;%.1f;%.2f;%.2f;%.1f;%.1f;%.1f;%.2f;%.2f\n", student,
                        Math.random(), Math.random(), Math.random(), Math.random() * (0 - 6 + 1) + 6, Math.random() * (0 - 14 + 1) + 14
                        , Math.random(), Math.random(), Math.random(), Math.random() * (0 - 6 + 1) + 6, Math.random() * (0 - 14 + 1) + 14);
            }
            // cierre del archivo
            outArchivo.close();
            // lectura del archivo anterior
            Scanner inArchivo = new Scanner(new File("/home/franjo18/Documentos/studentCalif.csv"));
            // variables globales
            String contenido, alerta = " ";
            double fin1, fin2;
            // Variables para contar los estudiantes
            double nroRendir1 = 0, nroRendir2 = 0, nroRendir12 = 0, nroTrabajos = 0;
            // creacion del nuevo archivo que contendra el los totales
            Formatter newFile = new Formatter
                    ("/home/franjo18/Documentos/caliFinally.csv", "US-ASCII", ingles);
            while (inArchivo.hasNext()) {
                // lectura de cada linea del archivo
                contenido = inArchivo.nextLine();
                String[] tokens = contenido.split(";");
                alerta = " ";
                double total;
                fin1 = 0;
                fin2 = 0;
                // suma de un estudiante en caso tenga todas as calif completas
                total = Double.valueOf(tokens[1]) + Double.valueOf(tokens[2]) + Double.valueOf(tokens[3]) +
                        Double.valueOf(tokens[4]) + Double.valueOf(tokens[5]) + Double.valueOf(tokens[6]) +
                        Double.valueOf(tokens[7]) + Double.valueOf(tokens[8]) + Double.valueOf(tokens[9]) +
                        Double.valueOf(tokens[10]);
                // Calculo del total en caso de que el estudiante tenga menos de 28
                if(total <= 27.50){
                    fin2 = Math.random() * (0 - 20 + 1) + 20;
                    total = Double.valueOf(tokens[1]) + Double.valueOf(tokens[2]) + Double.valueOf(tokens[3]) +
                            Double.valueOf(tokens[4]) + Double.valueOf(tokens[5]) + fin2;
                }
                // En caso el alumno renga menos de 8 en le pre1
                if (Double.valueOf(tokens[5]) < 8) {
                    alerta = "Rendir final 1";
                    // asignacion de una calificacion al final1
                    fin1 = Math.random() * (0 - 20 + 1) + 20;
                    total = Double.valueOf(tokens[4]) + Double.valueOf(tokens[6]) + Double.valueOf(tokens[7]) +
                            Double.valueOf(tokens[8]) + Double.valueOf(tokens[9]) + Double.valueOf(tokens[10]) + fin1 ;
                }
                // En caso el alumno tenga menos de 8 en le pre2
                if (Double.valueOf(tokens[10]) < 8) {
                    alerta = "Rendir final 2";
                    // asignacion de una calificacion al final2
                    fin2 = Math.random() * (0 - 20 + 1) + 20;
                    total = Double.valueOf(tokens[1]) + Double.valueOf(tokens[2]) + Double.valueOf(tokens[3]) +
                            Double.valueOf(tokens[4]) + Double.valueOf(tokens[5]) + Double.valueOf(tokens[9]) + fin2;
                }
                // En caso el alumno renga menos de 8 en los dos presenciales
                if (Double.valueOf(tokens[5]) < 8 && Double.valueOf(tokens[10]) < 8) {
                    alerta = "Rendir final 1 y 2";
                    // asignacion de una calificacion al final1 y final2
                    fin1 = Math.random() * (0 - 20 + 1) + 20;
                    fin2 = Math.random() * (0 - 20 + 1) + 20;
                    // suma de las columnas de trabajos
                    total = fin1 + fin2;
                }
                // calculo en caso este reprobado por fata de trabajo
                if (Double.valueOf(tokens[4]) <= 0 || Double.valueOf(tokens[9]) <= 0) {
                    alerta = "Reprobado Falta Trabajo";fin1 = 0;fin2 = 0;
                    total = Double.valueOf(tokens[1]) + Double.valueOf(tokens[2]) + Double.valueOf(tokens[3]) +
                            Double.valueOf(tokens[4]) + Double.valueOf(tokens[5]) + Double.valueOf(tokens[6]) +
                            Double.valueOf(tokens[7]) + Double.valueOf(tokens[8]) + Double.valueOf(tokens[9]) +
                            Double.valueOf(tokens[10]);
                }
                nroRendir1 += (alerta.equals("Rendir final 1"))?1:0;
                nroRendir2 += (alerta.equals("Rendir final 2"))?1:0;
                nroRendir12 += (alerta.equals("Rendir final 1 y 2"))?1:0;
                nroTrabajos += (alerta.equals("Reprobado Falta Trabajo"))?1:0;
                // al dar un resultado superior a 40 se cambia ese 40
                total = (total > 40)?40:total;
                // calculo de la promocion
                String promocion = (total >= 27.50) ? "Aprobado" : "Reprobado";
                // Asignacion de los valores al archivo por cada estudiante
                newFile.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%.2f;%.0f;%s;%s\n",
                        tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7],
                        tokens[8], tokens[9], tokens[10], fin1, fin2, total, alerta, promocion);
            }
            // Cierre de archivos
            inArchivo.close();
            newFile.close();
            // lectura del archivo para generar los CSV ordenados
            Scanner lista = new Scanner(new File("/home/franjo18/Documentos/caliFinally.csv"));
            // lista usada para tener los valores de cada fila del archivo
            LinkedList<String> values1 = new LinkedList<>();
            // array usado para separar las notas finales de los estudiantes
            int notas[] = new int[students.length], c = 0;
            while (lista.hasNext()) {
                contenido = lista.nextLine();
                String[] tokens = contenido.split(";");
                notas[c++] = Integer.valueOf(tokens[13]);
                values1.add(contenido);
            }
            // creacion del archivo ordenado por nombres
            Formatter orderByName = new Formatter
                    ("/home/franjo18/Documentos/SacaFrankBdEst_OrdenNomb.csv", "US-ASCII", ingles);
            // Metodo usado para ordenar una lista
            Collections.sort(values1);
            // iterador para escribir sobre el archivo
            Iterator iterador = values1.iterator();
            // while para escribir cada fila
            while (iterador.hasNext()) {
                contenido = (String) iterador.next();
                orderByName.format("%s\n",contenido);
            }
            // cierre del archivo
            orderByName.close();
            lista = new Scanner(new File("/home/franjo18/Documentos/caliFinally.csv"));
            LinkedList<String> values2 = new LinkedList<String>();
            // Creacion del archivo ordenado por el total
            Formatter orderByTotal = new Formatter
                    ("/home/franjo18/Documentos/SacaFrankBdEst_OrdenTOTAL.csv", "US-ASCII", ingles);
            // Metodo burbuja para ordenar las notas de mayor a menor
            for(int i = 0; i < notas.length - 1; i++) {
                for(int j = 0; j < notas.length - 1; j++) {
                    if (notas[j] < notas[j + 1]) {
                        int tmp = notas[j+1];
                        notas[j+1] = notas[j];
                        notas[j] = tmp;
                    }
                }
            }
            int ini, fin = 0;
            // ciclo para recorrer todos los estudiantes varias veces
            for (int i = 0; i < students.length; i++) {
                ini = 0;
                // lectura del archivo
                lista = new Scanner(new File("/home/franjo18/Documentos/caliFinally.csv"));
                // Recorrido de estudiantes
                while (lista.hasNext()) {
                    contenido = lista.nextLine();
                    String[] tokens = contenido.split(";");
                    ini++;
                    // comparacion si es estudiante tiene esa nota y ya no ah sido agregao antes
                    if (Integer.valueOf(tokens[13]) == notas[i] && ini != fin) {
                        values2.add(contenido);
                        // si ya lo encontro que salte del ciclo y no pierda tiempo
                        break;
                    }
                }
                // variable fin que se usa como auxiliar para saber que estudiante ya paso
                fin = ini;
            }
            // paso a un iterador la lista de valores
            iterador = values2.iterator();
            // asiganacion de cada estudiante al archivo
            while (iterador.hasNext()) {
                contenido = (String) iterador.next();
                orderByTotal.format("%s\n",contenido);
            }
            // cierre del archivo
            orderByTotal.close();
            // lectura del archivo
            lista = new Scanner(new File("/home/franjo18/Documentos/caliFinally.csv"));
            LinkedList<String> values3 = new LinkedList<String>();
            // creacion del archivo que se ordenara por Promocion
            Formatter orderByPromo = new Formatter
                    ("/home/franjo18/Documentos/SacaFrankBdEst_OrdenPromo.csv", "US-ASCII", ingles);
            // Recorrido de cada fila del archivo
            double nroReprobado = 0, nroAprobado = 0;
            while (lista.hasNext()){
                contenido = lista.nextLine();
                String[] tokens = contenido.split(";");
                // en caso sea aprobado agregelo al inicio si no agregelo despues
                if(tokens[15].equals("Aprobado")){
                    values3.addFirst(contenido);
                    // contador de Aprobados
                    nroAprobado++;
                }else {
                    values3.addLast(contenido);
                    // Contador de reprobados
                    nroReprobado++;
                }
            }
            // paso a un iterador la lista
            iterador = values3.iterator();
            // Asignacion de datos al archivo
            while (iterador.hasNext()) {
                contenido = (String) iterador.next();
                orderByPromo.format("%s\n",contenido);
            }
            // cierre del archivo
            orderByPromo.close();
            System.out.printf("Estudiantes aprobados: %.0f%%\nEstudiantes reprobados: %.0f%%\nEstudiantes Final 1: %.0f%%" +
                    "\nEstudiantes Final 2: %.0f%%\nEstudiantes Final 1 y 2: %.0f%%\nEstudiantes reprobados por trabajo: %.0f%%",
                    (nroAprobado*100)/students.length,(nroReprobado*100)/students.length,(nroRendir1*100)/students.length
                    ,(nroRendir2*100)/students.length,(nroRendir12*100)/students.length , (nroTrabajos*100)/students.length);
        } catch (Exception e) {
            System.out.println("Fatal error: " + e);
        }
    }
}