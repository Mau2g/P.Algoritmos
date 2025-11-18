import java.util.Scanner;

public class BacktrackingDemo {
    
    static int llamadas = 0;
    
    static void buscar(int[] nums, int objetivo, int[] actual, 
                       int pos, int inicio, int suma) {
        llamadas++;
        
        // Caso base: suma coincide
        if (suma == objetivo) {
            System.out.print("[ENCONTRADO] ");
            for (int i = 0; i < pos; i++) {
                System.out.print(actual[i]);
                if (i < pos - 1) System.out.print(", ");
            }
            System.out.println();
            return;
        }
        
        // Caso base: suma excede objetivo
        if (suma > objetivo) {
            return;
        }
        
        // Probar cada numero disponible
        for (int i = inicio; i < nums.length; i++) {
            actual[pos] = nums[i];
            
            System.out.println("  Nivel " + (pos + 1) + ": Prueba " + nums[i] + 
                             " (suma: " + suma + " -> " + (suma + nums[i]) + ")");
            
            // Recursion
            buscar(nums, objetivo, actual, pos + 1, i, suma + nums[i]);
            
            // Backtracking
            System.out.println("  Retroceso desde " + nums[i]);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("===============================================");
        System.out.println("DEMO DE BACKTRACKING - COMBINACIONES");
        System.out.println("===============================================\n");
        
        System.out.print("Ingresa los numeros disponibles (separados por espacio): ");
        String entrada = sc.nextLine();
        String[] partes = entrada.split(" ");
        int[] nums = new int[partes.length];
        
        for (int i = 0; i < partes.length; i++) {
            nums[i] = Integer.parseInt(partes[i]);
        }
        
        System.out.print("Ingresa el numero objetivo: ");
        int objetivo = sc.nextInt();
        
        System.out.println("\nBuscando combinaciones que sumen " + objetivo + "...\n");
        
        buscar(nums, objetivo, new int[20], 0, 0, 0);
        
        System.out.println("\n===============================================");
        System.out.println("Total llamadas recursivas: " + llamadas);
        System.out.println("===============================================");
        
        sc.close();
    }
}
