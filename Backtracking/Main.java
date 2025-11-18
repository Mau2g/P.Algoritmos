package backtracking;

/**
 * Programa de demostración de Backtracking
 * 
 * Resuelve varios laberintos usando el algoritmo de backtracking.
 * Cada laberinto presenta diferentes niveles de complejidad.
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║  DEMOSTRACIÓN DE BACKTRACKING - SOLUCIONADOR DE LABERINTOS  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        // Ejemplo 1: Laberinto Simple
        System.out.println("┌─ EJEMPLO 1: Laberinto Simple (5x5) ─────────────────┐\n");
        solucionarEjemplo1();
        
        // Ejemplo 2: Laberinto Mediano
        System.out.println("\n┌─ EJEMPLO 2: Laberinto Mediano (8x10) ────────────────┐\n");
        solucionarEjemplo2();
        
        // Ejemplo 3: Laberinto Complejo
        System.out.println("\n┌─ EJEMPLO 3: Laberinto Complejo (10x12) ──────────────┐\n");
        solucionarEjemplo3();
    }
    
    /**
     * EJEMPLO 1: Laberinto simple 5x5
     * 0 = camino, 1 = pared
     */
    private static void solucionarEjemplo1() {
        int[][] maze = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0}
        };
        
        MazeSolver solver = new MazeSolver(maze);
        solver.printMaze();
        
        if (solver.solve(0, 0, 4, 4)) {
            solver.printMazeWithSolution();
            solver.printStatistics();
        }
    }
    
    /**
     * EJEMPLO 2: Laberinto mediano 8x10
     */
    private static void solucionarEjemplo2() {
        int[][] maze = {
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 1, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            {1, 1, 1, 0, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 1}
        };
        
        MazeSolver solver = new MazeSolver(maze);
        solver.printMaze();
        
        if (solver.solve(0, 0, 7, 9)) {
            solver.printMazeWithSolution();
            solver.printStatistics();
        }
    }
    
    /**
     * EJEMPLO 3: Laberinto complejo 10x12
     */
    private static void solucionarEjemplo3() {
        int[][] maze = {
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };
        
        MazeSolver solver = new MazeSolver(maze);
        solver.printMaze();
        
        if (solver.solve(0, 0, 9, 11)) {
            solver.printMazeWithSolution();
            solver.printStatistics();
        }
    }
}
