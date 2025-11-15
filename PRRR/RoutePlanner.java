import java.util.Arrays;

/**
 * Planificador recursivo de rutas con restricciones (ventanas de tiempo y precedencias).
 * Implementa backtracking para encontrar una ruta que visite todos los nodos.
 */
public class RoutePlanner {
    private final Node[] nodes;
    private final int[][] travelTime;
    private final boolean[][] precedence; // precedence[a][b] == true => a debe visitarse antes que b
    private final int n;

    private boolean[] visited;
    private int[] currentPath;
    private int[] bestPath;
    private int[] arrivalTimes;      // tiempos de inicio de servicio actuales
    private int[] bestArrivalTimes;  // tiempos de inicio de servicio para la mejor solución
    private boolean found = false;

    public RoutePlanner(Node[] nodes, int[][] travelTime, boolean[][] precedence) {
        this.nodes = nodes;
        this.travelTime = travelTime;
        this.precedence = precedence;
        this.n = nodes.length;
    }

    /**
     * Encuentra una ruta válida que visite todos los nodos empezando desde depotIndex.
     * Devuelve true si encuentra una solución (la primera encontrada).
     */
    public boolean findRouteFromDepot(int depotIndex) {
        visited = new boolean[n];
        currentPath = new int[n];
        bestPath = new int[n];
        arrivalTimes = new int[n];
        bestArrivalTimes = new int[n];

        visited[depotIndex] = true;
        currentPath[0] = depotIndex;
        arrivalTimes[depotIndex] = Math.max(0, nodes[depotIndex].earliest);

        backtrack(depotIndex, 1, arrivalTimes[depotIndex] + nodes[depotIndex].serviceTime);
        return found;
    }

    private void backtrack(int currentNode, int depth, int currentTime) {
        if (found) return; // stop after first solution

        if (depth == n) {
            // solución completa
            System.arraycopy(currentPath, 0, bestPath, 0, n);
            System.arraycopy(arrivalTimes, 0, bestArrivalTimes, 0, n);
            found = true;
            return;
        }

        for (int next = 0; next < n; next++) {
            if (visited[next]) continue;

            // comprueba restricciones de precedencia: todos los predecesores deben estar visitados
            boolean ok = true;
            for (int p = 0; p < n; p++) {
                if (precedence[p][next] && !visited[p]) { ok = false; break; }
            }
            if (!ok) continue;

            int arrival = currentTime + travelTime[currentNode][next];
            int startService = Math.max(arrival, nodes[next].earliest);
            if (startService > nodes[next].latest) continue; // no cabe en la ventana

            // tomar la decisión
            visited[next] = true;
            currentPath[depth] = next;
            arrivalTimes[next] = startService;

            backtrack(next, depth + 1, startService + nodes[next].serviceTime);

            // deshacer (backtrack)
            visited[next] = false;
            // arrivalTimes[next] no hace falta restaurarlo explícitamente
            if (found) return;
        }
    }

    public void printSolution() {
        if (!found) {
            System.out.println("No se encontró ninguna ruta válida.");
            return;
        }
        System.out.println("Ruta válida (orden de nodos):");
        for (int i = 0; i < n; i++) {
            int id = bestPath[i];
            System.out.print(id + (i + 1 < n ? " -> " : "\n"));
        }
        System.out.println("Tiempos de llegada / inicio de servicio:");
        for (int i = 0; i < n; i++) {
            int id = bestPath[i];
            int t = bestArrivalTimes[id];
            System.out.println(" nodo " + id + ": t_start=" + t + " (ventana [" + nodes[id].earliest + "," + nodes[id].latest + "]) s=" + nodes[id].serviceTime);
        }
    }

    // métodos de inspección para pruebas u otras necesidades
    public int[] getBestPath() { return Arrays.copyOf(bestPath, bestPath.length); }
    public int[] getBestArrivalTimes() { return Arrays.copyOf(bestArrivalTimes, bestArrivalTimes.length); }
}
