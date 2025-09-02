import java.io.*;
import java.util.*;

public class AzAz09FileSorter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese la ruta del archivo a ordenar: ");
        String rutaEntrada = sc.nextLine();

        File archivoEntrada = new File(rutaEntrada);

        if (!archivoEntrada.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try {
            String contenido = leerArchivo(archivoEntrada);

            // Paso 1–3: ordenar hasta azAZ09
            String resultadoOrdenado = ordenarHastaAzAZ09(contenido);

            // Paso 4: formatear en bloques de 9*5 por fila
            String resultadoFormateado = formatearEnFilas(resultadoOrdenado);

            // Crear archivo de salida
            String nombreOriginal = archivoEntrada.getName();
            File archivoSalida = new File("Ord_" + nombreOriginal);

            escribirArchivo(archivoSalida, resultadoFormateado);

            System.out.println("Archivo ordenado generado en: " + archivoSalida.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

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

    private static void escribirArchivo(File archivo, String contenido) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(contenido);
        }
    }

    public static String ordenarHastaAzAZ09(String entrada) {
        String[] bloques = entrada.trim().split("\\s+");
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = ordenarBloque(bloques[i]);
        }
        Arrays.sort(bloques);
        return ordenarGlobalAzAZ09(String.join("", bloques));
    }

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

    // NUEVA FUNCIÓN: agrupar en filas de 5 bloques de 9 caracteres
    private static String formatearEnFilas(String cadena) {
        StringBuilder sb = new StringBuilder();
        int index = 0;

        while (index < cadena.length()) {
            for (int bloque = 0; bloque < 5 && index < cadena.length(); bloque++) {
                int end = Math.min(index + 9, cadena.length());
                sb.append(cadena, index, end);
                index = end;

                if (bloque < 4) sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
