import java.util.Scanner;
public class BacktrackingDemo {
    
    // Variable para contar cuántas veces se ejecuta la función recursiva
    static int llamadas = 0;
    
    /**
     * FUNCIÓN RECURSIVA DE BACKTRACKING
     * ──────────────────────────────────
     * 
     * Esta función es el corazón del algoritmo de backtracking. Explora recursivamente
     * todas las combinaciones posibles de números que pueden sumar el objetivo.
     * 
     * @param nums      Array de números disponibles (ej: [1, 2, 3, 5])
     * @param objetivo  La suma que queremos alcanzar (ej: 7)
     * @param actual    Array que almacena la combinación siendo construida en este momento
     * @param pos       Posición actual en el array 'actual' (dónde insertar el próximo número)
     * @param inicio    Índice desde el cual buscar en 'nums' (evita combinaciones duplicadas)
     * @param suma      Suma acumulada hasta el momento
     * 
     * FLUJO DE EJECUCIÓN:
     * 1. Se incrementa contador de llamadas
     * 2. Se verifican CASOS BASE (condiciones de parada)
     * 3. Se EXPLORA cada opción posible (bucle FOR)
     * 4. Se RECURSA para cada opción
     * 5. Se RETROCEDE automáticamente al volver de la recursión
     */
    static void buscar(int[] nums, int objetivo, int[] actual, 
                       int pos, int inicio, int suma) {
        // Contador para visualizar recursión
        llamadas++;
        
        // CONDICIÓN DE PARADA 1: ✓ SOLUCIÓN ENCONTRADA
        // Si la suma actual es EXACTAMENTE igual al objetivo, tenemos una solución
        if (suma == objetivo) {
            System.out.print("[ENCONTRADO] ");
            // Imprimir los números que forman esta solución
            for (int i = 0; i < pos; i++) {
                System.out.print(actual[i]);
                if (i < pos - 1) System.out.print(", ");
            }
            System.out.println();
            // Retornamos para que el algoritmo siga buscando otras soluciones posibles
            return;
        }

        // CONDICIÓN DE PARADA 2: ✗ PODA - RAMA INVÁLIDA
        if (suma > objetivo) {
            System.out.println("    └─ PODA: suma " + suma + " > objetivo " + objetivo + 
                             " (retroceso automático)");
            return;
        }
        
        // FASE DE EXPLORACIÓN: INTENTAR CADA NÚMERO DISPONIBLE
        for (int i = inicio; i < nums.length; i++) {
            // PASO 1: ELEGIR - Agregar nums[i] a la combinación actual
            actual[pos] = nums[i];
            
            // Mostrar el estado: qué número estamos intentando en qué nivel
            System.out.println("  Nivel " + (pos + 1) + ": Prueba " + nums[i] + 
                             " (suma: " + suma + " -> " + (suma + nums[i]) + ")");
            
            // PASO 2: EXPLORAR - Llamada recursiva (descender un nivel del árbol)
            buscar(nums, objetivo, actual, pos + 1, i, suma + nums[i]);
            
            // PASO 3: RETROCESO (BACKTRACK)
            // NO NECESITAMOS CÓDIGO EXPLÍCITO PARA RETROCESO porque:

            System.out.println("  └─ Retroceso desde " + nums[i] + 
                             " (explorando otra rama)");
        }
        // FIN DE LA RECURSIÓN
    }
    
    /**
     * MÉTODO PRINCIPAL
     * ────────────────
     * Controlador del programa. Obtiene entrada del usuario y ejecuta el algoritmo
     * de backtracking para encontrar combinaciones.
     * 
     * FLUJO:
     * 1. Recibe números de entrada del usuario
     * 2. Recibe el objetivo (suma deseada)
     * 3. Ejecuta el backtracking
     * 4. Muestra estadísticas
     */
    public static void main(String[] args) {
        // Crear scanner para entrada de usuario
        Scanner sc = new Scanner(System.in);
        
        // ENCABEZADO DEL PROGRAMA
        System.out.println("===============================================");
        System.out.println("     DEMO DE BACKTRACKING - COMBINACIONES");
        System.out.println("===============================================\n");
        
        // ENTRADA 1: OBTENER NÚMEROS DISPONIBLES
        System.out.print("Ingresa los numeros disponibles (separados por espacio): ");
            // Lee una línea completa (ej: "1 2 3 5")
        String entrada = sc.nextLine();
            // Divide la línea en partes usando espacio como delimitador
        String[] partes = entrada.split(" ");
            // Convierte los strings a integers
        int[] nums = new int[partes.length];
        
        for (int i = 0; i < partes.length; i++) {
            nums[i] = Integer.parseInt(partes[i]);
        }
        
        System.out.println("Números válidos: " + java.util.Arrays.toString(nums));
        
        // ENTRADA 2: OBTENER EL OBJETIVO (SUMA DESEADA)
        System.out.print("Ingresa el numero objetivo (suma a encontrar): ");
        int objetivo = sc.nextInt();
        
        // MENSAJE INFORMATIVO
        System.out.println("\n=== INICIANDO BÚSQUEDA DE BACKTRACKING ===");
        System.out.println("Objetivo: " + objetivo);
        System.out.println("Buscando todas las combinaciones que sumen " + objetivo + "...\n");
        
        // EJECUCIÓN PRINCIPAL: LLAMAR AL ALGORITMO DE BACKTRACKING

        // nums: array de números disponibles
        // objetivo: la suma que queremos alcanzar
        // new int[objetivo+1]: array para almacenar la combinación actual
        // 0: posición inicial (empezamos en índice 0)
        // 0: índice inicial de búsqueda
        // 0: suma inicial es 0 (aún no hemos elegido números)

        buscar(nums, objetivo, new int[objetivo+1], 0, 0, 0);
        
        // RESUMEN Y ESTADÍSTICAS
        System.out.println("BÚSQUEDA COMPLETADA");
        System.out.println("Total de llamadas recursivas: " + llamadas);
        
        // Cerrar scanner
        sc.close();
    }
}

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * ANÁLISIS DETALLADO: CÓMO FUNCIONA EL BACKTRACKING EN ESTE CÓDIGO
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * EJEMPLO DE EJECUCIÓN:
 * ─────────────────────
 * Input: números = [1, 2, 3], objetivo = 4
 * 
 * 
 * ÁRBOL DE RECURSIÓN VISUAL:
 * ──────────────────────────
 * 
 *                          buscar([], suma=0)
 *                              /    |    \
 *                     [1]/    [2]|    \[3]
 *                     /        |        \
 *            buscar([1], suma=1)  buscar([2], suma=2)  buscar([3], suma=3)
 *                /    |    \          /    \            /    \
 *           [1] [2]  [3] buscar([1,1])...buscar([2,2])...buscar([3,1])...
 *             / | \      ...
 *          suma=2
 *             ...
 *          
 * 
 * EJECUCIÓN PASO A PASO:
 * ──────────────────────
 * 
 * 1. buscar([1,2,3], 4, [], 0, 0, 0)  <- INICIO
 *    • Intenta 1: suma = 1
 *    
 *    2. buscar([1,2,3], 4, [1], 1, 0, 1)
 *       • Intenta 1: suma = 2
 *       
 *       3. buscar([1,2,3], 4, [1,1], 2, 0, 2)
 *          • Intenta 1: suma = 3
 *          
 *          4. buscar([1,2,3], 4, [1,1,1], 3, 0, 3)
 *             • Intenta 1: suma = 4 ✓ ENCONTRADO [1,1,1,1]
 *             • Retrocede por poda (suma > 4)
 *             • Intenta 2: suma = 5 ✗ PODA (5 > 4) RETROCESO
 *             • Intenta 3: suma = 6 ✗ PODA (6 > 4) RETROCESO
 *          
 *          • RETROCESO: vuelve a nivel anterior, intenta siguiente
 *          • Intenta 2: suma = 4 ✓ ENCONTRADO [1,1,2]
 *          • Intenta 3: suma = 5 ✗ PODA RETROCESO
 *       
 *       • RETROCESO a nivel 2
 *       • Intenta 2: suma = 3
 *       ...
 * 
 * 
 * CONCEPTOS CLAVE:
 * ───────────────
 * 
 * 1. PROFUNDIDAD (depth): Nivel en el árbol de recursión
 *    • Nivel 1: Primera opción (pos=0)
 *    • Nivel 2: Segunda opción (pos=1)
 *    • Etc.
 * 
 * 2. ESTADO DE LA BÚSQUEDA:
 *    • actual[]: Almacena la combinación siendo construida
 *    • suma: Va aumentando conforme agregamos números
 *    • pos: Indica cuántos números hemos elegido
 * 
 * 3. PODA (Pruning):
 *    • Si suma > objetivo → RETROCEDE inmediatamente
 *    • Esto evita explorar ramas inútiles
 *    • Sin poda, exploraría infinitamente (suma siempre positiva)
 * 
 * 4. EVITAR DUPLICADOS:
 *    • Usamos 'inicio' como parámetro
 *    • Así [1,2] es diferente de [2,1]
 *    • Si no hiciéramos esto, obtendríamos duplicados
 * 
 * 5. RETROCESO IMPLÍCITO:
 *    • No hay código explícito para "deshacer"
 *    • Ocurre automáticamente cuando retorna la recursión
 *    • Los parámetros se resetean por la naturaleza de la recursión
 * 
 * 
 * COMPLEJIDAD:
 * ────────────
 * • Mejor caso (con poda): O(n) - cuando hay pocas soluciones
 * • Peor caso: O(2^n) - cuando explora todas las combinaciones
 * • La poda es lo que hace este algoritmo práctico
 * 
 * 
 * APLICACIONES REALES:
 * ────────────────────
 * • Solución de laberintos (MazeSolver.java)
 * • N-Reinas (puzzle)
 * • Combinaciones de monedas (coin change)
 * • Sudoku
 * • Seleccionar ítems con restricciones (knapsack)
 * • Parseo de gramáticas
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 */
