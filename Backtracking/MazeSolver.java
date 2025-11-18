package backtracking;

import java.util.*;

/**
 * Solucionador de Laberintos usando Backtracking
 * 
 * Demuestra cómo usar backtracking para encontrar caminos válidos
 * a través de un laberinto. El algoritmo explora recursivamente
 * cada posible dirección, retrocediendo cuando llega a un callejón sin salida.
 */
public class MazeSolver {
    
    // Representación del laberinto:
    // 0 = camino disponible
    // 1 = pared
    // 2 = solución
    private int[][] maze;
    private boolean[][] visited;
    private List<Coordinate> solutionPath;
    private int rows;
    private int cols;
    
    // Direcciones: arriba, derecha, abajo, izquierda
    private static final int[][] DIRECTIONS = {
        {-1, 0},  // arriba
        {0, 1},   // derecha
        {1, 0},   // abajo
        {0, -1}   // izquierda
    };
    
    private static final String[] DIRECTION_NAMES = {"ARRIBA", "DERECHA", "ABAJO", "IZQUIERDA"};
    
    /**
     * Constructor del solucionador
     * @param maze matriz del laberinto (0=camino, 1=pared)
     */
    public MazeSolver(int[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.visited = new boolean[rows][cols];
        this.solutionPath = new ArrayList<>();
    }
    
    /**
     * Resuelve el laberinto desde la posición inicial hasta la final
     * @param startRow fila de inicio
     * @param startCol columna de inicio
     * @param endRow fila de fin
     * @param endCol columna de fin
     * @return true si se encontró solución, false en caso contrario
     */
    public boolean solve(int startRow, int startCol, int endRow, int endCol) {
        System.out.println("=== INICIANDO BÚSQUEDA DE SOLUCIÓN ===");
        System.out.println("Inicio: (" + startRow + ", " + startCol + ")");
        System.out.println("Destino: (" + endRow + ", " + endCol + ")\n");
        
        // Validar posiciones iniciales
        if (!esValido(startRow, startCol) || !esValido(endRow, endCol)) {
            System.out.println("Posiciones inválidas");
            return false;
        }
        
        solutionPath.clear();
        resetVisited();
        
        // Iniciar el backtracking
        if (backtrack(startRow, startCol, endRow, endCol)) {
            System.out.println("\n✓ ¡SOLUCIÓN ENCONTRADA!\n");
            return true;
        } else {
            System.out.println("\n✗ No hay solución disponible\n");
            return false;
        }
    }
    
    /**
     * Algoritmo recursivo de backtracking
     * Explora todas las direcciones posibles desde la posición actual
     */
    private boolean backtrack(int row, int col, int endRow, int endCol) {
        // CASO BASE: llegamos al destino
        if (row == endRow && col == endCol) {
            solutionPath.add(new Coordinate(row, col));
            System.out.println("→ Destino alcanzado en: (" + row + ", " + col + ")");
            return true;
        }
        
        // Validar si la posición actual es válida
        if (!esValido(row, col) || visited[row][col]) {
            return false;
        }
        
        // Marcar como visitado
        visited[row][col] = true;
        solutionPath.add(new Coordinate(row, col));
        
        System.out.println("  Visitando: (" + row + ", " + col + ") | Camino: " + solutionPath.size());
        
        // EXPLORACIÓN: intentar todas las direcciones (backtracking)
        for (int i = 0; i < DIRECTIONS.length; i++) {
            int newRow = row + DIRECTIONS[i][0];
            int newCol = col + DIRECTIONS[i][1];
            
            System.out.println("    └─ Intentando " + DIRECTION_NAMES[i] + 
                             " hacia (" + newRow + ", " + newCol + ")");
            
            // Si encontramos solución en esta dirección, retornamos true
            if (backtrack(newRow, newCol, endRow, endCol)) {
                return true;
            }
            
            // BACKTRACKING: si no hay solución en esta dirección, continuamos
            System.out.println("    └─ " + DIRECTION_NAMES[i] + " no lleva a solución");
        }
        
        // RETROCESO: desmarcar como visitado y remover del camino
        visited[row][col] = false;
        solutionPath.remove(solutionPath.size() - 1);
        System.out.println("  Retrocediendo desde: (" + row + ", " + col + ")");
        
        return false;
    }
    
    /**
     * Verifica si una posición es válida para continuar
     */
    private boolean esValido(int row, int col) {
        return row >= 0 && row < rows && 
               col >= 0 && col < cols && 
               maze[row][col] == 0;
    }
    
    /**
     * Reinicia el array de visitados
     */
    private void resetVisited() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visited[i][j] = false;
            }
        }
    }
    
    /**
     * Imprime el laberinto
     */
    public void printMaze() {
        System.out.println("=== LABERINTO ===");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j] == 1 ? "█ " : "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Imprime el laberinto con la solución marcada
     */
    public void printMazeWithSolution() {
        if (solutionPath.isEmpty()) {
            System.out.println("No hay solución para mostrar");
            return;
        }
        
        // Crear copia del laberinto
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = maze[i][j];
            }
        }
        
        // Marcar solución
        for (Coordinate coord : solutionPath) {
            result[coord.row][coord.col] = 2;
        }
        
        System.out.println("=== LABERINTO CON SOLUCIÓN ===");
        System.out.println("█ = Pared | • = Solución | (vacío) = Camino no utilizado\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (result[i][j] == 1) {
                    System.out.print("█ ");
                } else if (result[i][j] == 2) {
                    System.out.print("• ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Retorna el camino de la solución
     */
    public List<Coordinate> getSolutionPath() {
        return solutionPath;
    }
    
    /**
     * Imprime estadísticas de la solución
     */
    public void printStatistics() {
        System.out.println("=== ESTADÍSTICAS ===");
        System.out.println("Longitud del camino: " + solutionPath.size() + " celdas");
        if (!solutionPath.isEmpty()) {
            System.out.print("Ruta completa: ");
            for (int i = 0; i < solutionPath.size(); i++) {
                Coordinate c = solutionPath.get(i);
                System.out.print("(" + c.row + "," + c.col + ")");
                if (i < solutionPath.size() - 1) System.out.print(" → ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Clase interna para representar coordenadas
     */
    public static class Coordinate {
        public int row;
        public int col;
        
        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }
}
