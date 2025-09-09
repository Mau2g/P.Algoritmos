import java.util.*;

/**
 * Programa que ordena una cadena de texto según el patrón:
 * primero letras minúsculas (a-z), luego mayúsculas (A-Z),
 * después dígitos (0-9), y finalmente otros símbolos.
 */
public class AzAz09Sorter {

    public static void main(String[] args) {
        // Cadena de entrada con bloques separados por espacios
        String entrada = "Qv5HZBLWL B8Q59GXVL hrdcacpXm twQjKviU0 V9xiZ7eVh";

        // Aplicar el algoritmo de ordenamiento azAZ09
        String resultado = ordenarHastaAzAZ09(entrada);

        // Imprimir el resultado esperado
        System.out.println(resultado); // abbcdprtyABCXX122346
    }

    /**
     * Orquesta el proceso completo en 3 pasos:
     * 1. Ordena internamente cada bloque.
     * 2. Ordena los bloques entre sí.
     * 3. Hace un ordenamiento global de todos los caracteres.
     */
    public static String ordenarHastaAzAZ09(String entrada) {
        // Dividir en bloques usando espacios
        String[] bloques = entrada.trim().split("\\s+");

        // Paso 1: ordenar cada bloque individualmente
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = ordenarBloque(bloques[i]);
        }

        // Paso 2: ordenar los bloques entre sí alfabéticamente
        Arrays.sort(bloques);

        // Paso 3: juntar todo y ordenar globalmente
        return ordenarGlobalAzAZ09(String.join("", bloques));
    }

    /**
     * Ordena un bloque individual:
     * primero minúsculas, luego mayúsculas, luego dígitos, y finalmente símbolos.
     */
    private static String ordenarBloque(String b) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>(); // por si hay caracteres especiales

        // Clasificar cada carácter del bloque
        for (char c : b.toCharArray()) {
            if (Character.isLowerCase(c)) lower.add(c);     // minúscula
            else if (Character.isUpperCase(c)) upper.add(c); // mayúscula
            else if (Character.isDigit(c)) digit.add(c);     // número
            else otros.add(c);                               // otros símbolos
        }

        // Ordenar cada grupo por separado
        Collections.sort(lower);
        Collections.sort(upper);
        Collections.sort(digit);

        // Reconstruir el bloque ordenado
        StringBuilder sb = new StringBuilder(b.length());
        for (char c : lower) sb.append(c);
        for (char c : upper) sb.append(c);
        for (char c : digit) sb.append(c);
        for (char c : otros) sb.append(c); // opcional: si quieres ignorarlos, elimínalo

        return sb.toString();
    }

    /**
     * Ordena todos los caracteres de la cadena completa con el patrón:
     * a-z, luego A-Z, luego dígitos, y finalmente símbolos.
     */
    private static String ordenarGlobalAzAZ09(String s) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>();

        // Clasificar todos los caracteres
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) lower.add(c);
            else if (Character.isUpperCase(c)) upper.add(c);
            else if (Character.isDigit(c)) digit.add(c);
            else otros.add(c);
        }

        // Ordenar cada lista
        Collections.sort(lower);
        Collections.sort(upper);
        Collections.sort(digit);

        // Reconstruir cadena final en orden azAZ09
        StringBuilder sb = new StringBuilder(s.length());
        for (char c : lower) sb.append(c);
        for (char c : upper) sb.append(c);
        for (char c : digit) sb.append(c);
        for (char c : otros) sb.append(c); // opcional

        return sb.toString();
    }
}