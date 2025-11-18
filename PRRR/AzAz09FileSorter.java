import java.io.*;
import java.util.*;

/**
 * Clase que permite leer un archivo de texto, ordenar su contenido
 * siguiendo el criterio azAZ09 (primero minúsculas, luego mayúsculas,
 * luego dígitos y finalmente otros caracteres) y generar un archivo
 * de salida formateado en bloques de 5 columnas x 9 caracteres.
 */
public class AzAz09FileSorter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Solicitar al usuario la ruta del archivo a ordenar
        System.out.print("Ingrese la ruta del archivo a ordenar: ");
        String rutaEntrada = sc.nextLine();

        File archivoEntrada = new File(rutaEntrada);

        // Verificar si el archivo existe
        if (!archivoEntrada.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try {
            // Leer el contenido completo del archivo en un string
            String contenido = leerArchivo(archivoEntrada);

            // Paso 1–3: ordenar primero dentro de cada bloque y luego globalmente
            String resultadoOrdenado = ordenarHastaAzAZ09(contenido);

            // Paso 4: formatear en filas de 5 bloques de 9 caracteres
            String resultadoFormateado = formatearEnFilas(resultadoOrdenado);

            // Crear archivo de salida con prefijo "Ord_"
            String nombreOriginal = archivoEntrada.getName();
            File archivoSalida = new File("Ord_" + nombreOriginal);

            // Escribir el resultado en el archivo de salida
            escribirArchivo(archivoSalida, resultadoFormateado);

            System.out.println("Archivo ordenado generado en: " + archivoSalida.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo de texto y devuelve su contenido como un String.
     */
    private static String leerArchivo(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Escribe el contenido en un archivo de texto.
     */
    private static void escribirArchivo(File archivo, String contenido) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(contenido);
        }
    }

    /**
     * Ordena el contenido del archivo siguiendo el criterio azAZ09:
     * 1. Primero ordena dentro de cada bloque.
     * 2. Luego ordena todos los bloques.
     * 3. Finalmente hace un ordenamiento global.
     */
    public static String ordenarHastaAzAZ09(String entrada) {
        // Separar por espacios
        String[] bloques = entrada.trim().split("\\s+");

        // Ordenar cada bloque individualmente
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = ordenarBloque(bloques[i]);
        }

        // Ordenar los bloques entre sí (alfabéticamente)
        Arrays.sort(bloques);

        // Ordenamiento global de todo el texto concatenado
        return ordenarGlobalAzAZ09(String.join("", bloques));
    }

    /**
     * Ordena un bloque según el criterio:
     * primero minúsculas, luego mayúsculas, luego dígitos, y al final otros caracteres.
     */
    private static String ordenarBloque(String b) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>();

        // Clasificar caracteres del bloque
        for (char c : b.toCharArray()) {
            if (Character.isLowerCase(c)) lower.add(c);
            else if (Character.isUpperCase(c)) upper.add(c);
            else if (Character.isDigit(c)) digit.add(c);
            else otros.add(c);
        }

        // Ordenar cada lista por separado
        Collections.sort(lower);
        Collections.sort(upper);
        Collections.sort(digit);

        // Reconstruir el bloque en el orden definido
        StringBuilder sb = new StringBuilder(b.length());
        for (char c : lower) sb.append(c);
        for (char c : upper) sb.append(c);
        for (char c : digit) sb.append(c);
        for (char c : otros) sb.append(c);

        return sb.toString();
    }

    /**
     * Ordenamiento global de todos los caracteres siguiendo azAZ09.
     */
    private static String ordenarGlobalAzAZ09(String s) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>();

        // Clasificar caracteres de toda la cadena
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) lower.add(c);
            else if (Character.isUpperCase(c)) upper.add(c);
            else if (Character.isDigit(c)) digit.add(c);
            else otros.add(c);
        }

        // Ordenar cada grupo
        Collections.sort(lower);
        Collections.sort(upper);
        Collections.sort(digit);

        // Reconstruir la cadena en el orden establecido
        StringBuilder sb = new StringBuilder(s.length());
        for (char c : lower) sb.append(c);
        for (char c : upper) sb.append(c);
        for (char c : digit) sb.append(c);
        for (char c : otros) sb.append(c);

        return sb.toString();
    }

    /**
     * Formatea una cadena en filas de 5 bloques de 9 caracteres.
     * Ejemplo: XXXXXYYYYY ZZZZZAAAAA ......
     */
    private static String formatearEnFilas(String cadena) {
        StringBuilder sb = new StringBuilder();
        int index = 0;

        // Mientras queden caracteres por procesar
        while (index < cadena.length()) {
            // Cada fila tiene 5 bloques de 9 caracteres
            for (int bloque = 0; bloque < 5 && index < cadena.length(); bloque++) {
                int end = Math.min(index + 9, cadena.length());
                sb.append(cadena, index, end);
                index = end;

                // Espacio entre bloques, excepto el último
                if (bloque < 4) sb.append(" ");
            }
            // Nueva línea al final de cada fila
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}