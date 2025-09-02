import java.util.*;

public class AzAz09Sorter {

    public static void main(String[] args) {
        String entrada = "Qv5HZBLWL B8Q59GXVL hrdcacpXm twQjKviU0 V9xiZ7eVh";
        String resultado = ordenarHastaAzAZ09(entrada);
        System.out.println(resultado); // abbcdprtyABCXX122346
    }

    // Orquesta los 3 pasos y devuelve la cadena final azAZ09
    public static String ordenarHastaAzAZ09(String entrada) {
        // Paso 1: ordenar internamente cada bloque
        String[] bloques = entrada.trim().split("\\s+");
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = ordenarBloque(bloques[i]);
        }

        // Paso 2: ordenar bloques entre sí
        Arrays.sort(bloques);

        // Paso 3: ordenar globalmente a-z, A-Z, 0-9
        return ordenarGlobalAzAZ09(String.join("", bloques));
    }

    // Ordena un bloque: minúsculas (a-z), luego mayúsculas (A-Z), luego dígitos (0-9)
    private static String ordenarBloque(String b) {
        List<Character> lower = new ArrayList<>();
        List<Character> upper = new ArrayList<>();
        List<Character> digit = new ArrayList<>();
        List<Character> otros = new ArrayList<>(); // por si aparecen símbolos

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
        // Si quieres ignorar símbolos, elimina la siguiente línea:
        for (char c : otros) sb.append(c);

        return sb.toString();
    }

    // Ordena una cadena completa con patrón a-z, A-Z, 0-9
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
        // Si quieres excluir símbolos del resultado final, no los agregues:
        for (char c : otros) sb.append(c);

        return sb.toString();
    }
}
