/**
 * Nodo de entrega con ventana de tiempo y tiempo de servicio.
 */
public class Node {
    public final int id;
    // ventana de tiempo [earliest, latest]
    public final int earliest;
    public final int latest;
    // tiempo de servicio en el nodo (duración de la entrega)
    public final int serviceTime;

    public Node(int id, int earliest, int latest, int serviceTime) {
        this.id = id;
        this.earliest = earliest;
        this.latest = latest;
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "Node{" + id + ": [" + earliest + "," + latest + "] s=" + serviceTime + '}';
    }
}
