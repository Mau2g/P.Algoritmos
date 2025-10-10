import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Clase principal CountFinder - Herramienta para análisis de frecuencia de palabras en archivos de texto 
 * 
 * <p>Esta aplicación permite buscar y contar coincidencias exactas y parciales de una palabra
 * específica dentro de un archivo de texto, proporcionando estadísticas detalladas y porcentajes.</p>
 * 
 * <p><b>Características principales:</b>
 * <ul>
 * <li> O = (n * (L + M)) 
 *   <li>Lectura y procesamiento de archivos de texto</li>
 *   <li>Búsqueda de coincidencias exactas de palabras</li>
 *   <li>Búsqueda de coincidencias parciales usando algoritmo KMP</li>
 *   <li>Cálculo de estadísticas y porcentajes</li>
 *   <li>Manejo de errores robusto</li>
 * </ul>
 * 
 * @author Desarrollador
 * @version 1.0
 */
public class CountFinder {

    /**
     * Método principal - Punto de entrada de la aplicación
     * 
     * <p>Flujo de ejecución:
     * <ol>
     *   <li>Solicita al usuario la ruta del archivo y la palabra a buscar</li>
     *   <li>Lee y procesa el archivo de texto</li>
     *   <li>Realiza conteos de palabras y coincidencias</li>
     *   <li>Calcula porcentajes y muestra resultados</li>
     * </ol>
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Entrada de datos del usuario
            System.out.print("Ingrese la ruta del archivo: ");
            String ruta = scanner.nextLine();

            System.out.print("Ingrese la palabra a buscar: ");
            String palabra = scanner.nextLine().trim();

            // Lectura y validación del archivo
            String texto = leerArchivoComoTexto(ruta);
            if (texto.isEmpty()) {
                System.out.println("El archivo está vacío o no se pudo leer correctamente.");
                return;
            }

            // Normalización a minúsculas para búsqueda case-insensitive
            texto = texto.toLowerCase();
            palabra = palabra.toLowerCase();

            // Conteos y estadísticas
            int totalPalabras = contarPalabras(texto);
            int coincidenciasExactas = contarCoincidenciasExactas(texto, palabra);
            int coincidenciasParciales = contarCoincidenciasParciales(texto, palabra);

            // Cálculo de porcentajes con validación de división por cero
            double porcentajeExactas = (totalPalabras > 0)
                    ? ((double) coincidenciasExactas / totalPalabras) * 100
                    : 0;
            double porcentajeParciales = (totalPalabras > 0)
                    ? ((double) coincidenciasParciales / totalPalabras) * 100
                    : 0;

            // Presentación de resultados
            System.out.println("\n📄 Resultados:");
            System.out.println("Palabra buscada: " + palabra);
            System.out.println("Total de palabras en el documento: " + totalPalabras);
            System.out.println("Coincidencias exactas: " + coincidenciasExactas);
            System.out.printf("Porcentaje (exactas): %.2f%%\n", porcentajeExactas);
            System.out.println("Coincidencias parciales (palabras que contienen \"" + palabra + "\"): " + coincidenciasParciales);
            System.out.printf("Porcentaje (parciales): %.2f%%\n", porcentajeParciales);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            // ✅ IMPORTANTE: Cerrar el Scanner para evitar resource leaks
            scanner.close();
            System.out.println("\nPrograma finalizado.");
        }
    }

    // ----------- MÉTODOS DE LECTURA Y PROCESAMIENTO -----------

    /**
     * Lee un archivo de texto y lo convierte en una cadena única
     * 
     * <p>Este método utiliza BufferedReader para leer el archivo línea por línea
     * y concatenar todo el contenido en un solo String, separando cada línea
     * con un espacio.</p>
     * 
     * @param ruta Ruta absoluta o relativa del archivo a leer
     * @return String con todo el contenido del archivo
     * @throws IOException Si ocurre algún error durante la lectura del archivo
     * @throws SecurityException Si no se tienen permisos de lectura
     * @throws InvalidPathException Si la ruta proporcionada es inválida
     */
    private static String leerArchivoComoTexto(String ruta) throws IOException {
        Path path = Paths.get(ruta);
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * Cuenta el número total de palabras en el texto
     * 
     * <p>Utiliza una expresión regular para dividir el texto por espacios
     * en blanco (\\s+) y cuenta los tokens resultantes.</p>
     * 
     * @param texto Texto completo a analizar
     * @return Número total de palabras en el texto
     */
    private static int contarPalabras(String texto) {
        String[] palabras = texto.trim().split("\\s+");
        return palabras.length;
    }

    // ----------- MÉTODOS DE BÚSQUEDA Y COINCIDENCIAS -----------

    /**
     * Cuenta coincidencias exactas de la palabra buscada
     * 
     * <p>Busca palabras que sean idénticas a la palabra objetivo,
     * considerando límites de palabras completas.</p>
     * 
     * @param texto Texto completo donde buscar
     * @param palabra Palabra objetivo a buscar
     * @return Número de coincidencias exactas encontradas
     */
    private static int contarCoincidenciasExactas(String texto, String palabra) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;
        for (String p : palabras) {
            if (p.equals(palabra)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Cuenta coincidencias parciales de la palabra buscada
     * 
     * <p>Busca palabras que contengan la palabra objetivo como subcadena,
     * excluyendo las coincidencias exactas ya contabilizadas.</p>
     * 
     * @param texto Texto completo donde buscar
     * @param palabra Palabra objetivo a buscar como subcadena
     * @return Número de coincidencias parciales encontradas
     */
    private static int contarCoincidenciasParciales(String texto, String palabra) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;
        for (String p : palabras) {
            if (!p.equals(palabra) && contieneKMP(p, palabra)) {
                contador++;
            }
        }
        return contador;
    }

    // ----------- IMPLEMENTACIÓN DEL ALGORITMO KMP -----------

    /**
     * Implementa el algoritmo Knuth-Morris-Pratt (KMP) para búsqueda de subcadenas
     * 
     * <p>El algoritmo KMP es más eficiente que la búsqueda por fuerza bruta
     * para patrones repetitivos, con complejidad O(n + m) donde n es la longitud
     * del texto y m la longitud del patrón.</p>
     * 
     * @param texto Cadena de texto donde buscar el patrón
     * @param patron Patrón a buscar dentro del texto
     * @return true si el patrón está contenido en el texto, false en caso contrario
     * 
     * @see #construirLPS(String)
     */
    private static boolean contieneKMP(String texto, String patron) {
        // Validaciones iniciales para casos triviales
        if (patron.length() == 0 || texto.length() < patron.length()) {
            return false;
        }

        int[] lps = construirLPS(patron);
        int i = 0; // índice para recorrer el texto
        int j = 0; // índice para recorrer el patrón

        while (i < texto.length()) {
            if (texto.charAt(i) == patron.charAt(j)) {
                i++;
                j++;
                if (j == patron.length()) {
                    return true; // Coincidencia completa encontrada
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1]; // Retroceso usando la tabla LPS
                } else {
                    i++; // Avance en el texto
                }
            }
        }
        return false;
    }

    /**
     * Construye la tabla LPS (Longest Prefix Suffix) para el algoritmo KMP
     * 
     * <p>La tabla LPS almacena para cada posición del patrón, la longitud
     * del sufijo propio más largo que también es prefijo.</p>
     * 
     * @param patron Patrón para el cual construir la tabla LPS
     * @return Arreglo de enteros con los valores LPS para cada posición del patrón
     */
    private static int[] construirLPS(String patron) {
        int[] lps = new int[patron.length()];
        int len = 0; // Longitud del prefijo-sufijo anterior más largo
        int i = 1;   // Índice para recorrer el patrón

        while (i < patron.length()) {
            if (patron.charAt(i) == patron.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1]; // Retroceso en el patrón
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}