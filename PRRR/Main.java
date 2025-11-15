/**
 * Ejemplo de uso del Planificador Recursivo de Rutas con Restricciones (PRRR).
 */
public class Main {
    public static void main(String[] args) {
        // Definir nodos. Nodo 0 será el depósito (depot).
        Node depot = new Node(0, 0, 99999, 0);
        Node n1 = new Node(1, 10, 50, 5);
        Node n2 = new Node(2, 0, 100, 10);
        Node n3 = new Node(3, 30, 80, 5);

        Node[] nodes = new Node[]{depot, n1, n2, n3};

        // Matriz de tiempos de viaje (en las mismas unidades que las ventanas)
        int[][] travel = new int[][]{
            {0, 5, 10, 15},
            {5, 0, 6, 9},
            {10,6, 0, 4},
            {15,9, 4, 0}
        };

        // Restricciones de precedencia: precedence[a][b] = true => a antes que b
        boolean[][] precedence = new boolean[nodes.length][nodes.length];
        // ejemplo: visitar n1 antes que n3
        precedence[1][3] = true;

        RoutePlanner planner = new RoutePlanner(nodes, travel, precedence);
        boolean ok = planner.findRouteFromDepot(0);
        if (ok) {
            planner.printSolution();
        } else {
            System.out.println("No existe ruta que satisfaga todas las restricciones.");
        }
    }
}
