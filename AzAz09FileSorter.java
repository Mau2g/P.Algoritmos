import java.io.*;
import java.util.*;

public class AzAz09FileSorter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Preguntar la ruta del archivo a procesar
        System.out.print("Ingrese la ruta del archivo a ordenar: ");
        String rutaEntrada = sc.nextLine();

        File archivoEntrada = new File(rutaEntrada);

        if (!archivoEntrada.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try {
            // Leer todo el contenido del archivo
            String contenido = leerArchivo(archivoEntrada);

            // Procesar el contenido con el algoritmo de ordenamiento
            String resultado = ordenarHastaAzAZ09(contenido);

            // Crear archivo de salida en la misma carpeta del programa
            String nombreOriginal = archivoEntrada.getName();
            File archivoSalida = new File("Ord_" + nombreOriginal);

            escribirArchivo(archivoSalida, resultado);

            System.out.println("Archivo ordenado generado en: " + archivoSalida.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    // Lee el archivo completo y devuelve su contenido en una sola cadena
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

    // Escribe el contenido procesado en un nuevo archivo
    private static void escribirArchivo(File archivo, String contenido) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(contenido);
        }
    }

    // Ordena hasta obtener una cadena final azAZ09
    public static String ordenarHastaAzAZ09(String entrada) {
        // Paso 1: ordenar internamente cada bloque
        String[] bloques = entrada.trim().split("\\s+");
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = ordenarBloque(bloques[i]);
        }

        // Paso 2: ordenar bloques entre sí
        Arrays.sort(bloques);

        // Paso 3: ordenar globalmente
        return ordenarGlobalAzAZ09(String.join("", bloques));
    }

    // Ordenar un bloque interno: minúsculas, mayúsculas, números
    private static String ordenarBloque(String b) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>();

        for (char c : b.toCharArray()) {
            if (Character.isLowerCase(c)) lower.add(c);
            else if (Character.isUpperCase(c)) upper.add(c);
            else if (Character.isDigit(c)) digit.add(c);
            else otros.add(c);
        }

        Collections.sort(lower);
        Collections.sort(upper);
        Collections.sort(digit);

        StringBuilder sb = new StringBuilder(b.length());
        for (char c : lower) sb.append(c);
        for (char c : upper) sb.append(c);
        for (char c : digit) sb.append(c);
        for (char c : otros) sb.append(c);

        return sb.toString();
    }

    // Ordenamiento global azAZ09
    private static String ordenarGlobalAzAZ09(String s) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>();

        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) lower.add(c);
            else if (Character.isUpperCase(c)) upper.add(c);
            else if (Character.isDigit(c)) digit.add(c);
            else otros.add(c);
        }

        Collections.sort(lower);
        Collections.sort(upper);
        Collections.sort(digit);

        StringBuilder sb = new StringBuilder(s.length());
        for (char c : lower) sb.append(c);
        for (char c : upper) sb.append(c);
        for (char c : digit) sb.append(c);
        for (char c : otros) sb.append(c);

        return sb.toString();
    }
}
