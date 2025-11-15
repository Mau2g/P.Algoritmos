# PRRR — Planificador Recursivo de Rutas con Restricciones (Java)

Este pequeño proyecto muestra una implementación en Java de un planificador de rutas que utiliza recursividad y backtracking para encontrar una ruta válida que visite todos los nodos respetando ventanas de tiempo y restricciones de precedencia.

Archivos principales:
- `Node.java` — modelo de nodo con ventana de tiempo y tiempo de servicio.
- `RoutePlanner.java` — implementación del algoritmo recursivo con backtracking.
- `Main.java` — ejemplo de uso (pequeño caso de prueba) y salida por consola.

Cómo compilar y ejecutar (PowerShell en Windows):

```powershell
javac PRRR\*.java; if ($LASTEXITCODE -eq 0) { java -cp PRRR Main }
```

Qué hace el ejemplo:
- Define 4 nodos (depósito + 3 entregas).
- Define una matriz de tiempos de viaje.
- Define la restricción de precedencia: nodo 1 debe visitarse antes que nodo 3.
- Ejecuta el planificador y muestra la primera ruta válida encontrada y los tiempos de inicio de servicio por nodo.

Notas y mejoras posibles:
- Actualmente el algoritmo devuelve la primera solución válida encontrada. Se puede adaptar para buscar la ruta de coste mínimo (por ejemplo acumulando tiempo total y buscando la mínima) o enumerar todas las soluciones.
- Añadir poda por límites inferiores, heurísticas de ordenación de sucesores o memorización para mejorar rendimiento en instancias grandes.
