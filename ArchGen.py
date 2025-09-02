import random
import string
import sys

def crear_archivo(tamaño_mb):
    if tamaño_mb <= 0:
        print("El tamaño debe ser mayor que cero.")
        sys.exit(1)
    n = f"ArchGen_{tamaño_mb}MB.txt"
    chars = string.ascii_letters + string.digits
    size = tamaño_mb * 1024 * 1024  # MB a bytes
    
    # Configuración del formato 9x5
    caracteres_por_bloque = 9
    bloques_por_linea = 5
    caracteres_por_linea = caracteres_por_bloque * bloques_por_linea
    
    with open(n, "w", encoding="utf-8") as f:
        num_lineas = size // caracteres_por_linea
        for _ in range(num_lineas):
            # Generar 5 bloques de 9 caracteres cada uno
            bloques = []
            for _ in range(bloques_por_linea):
                bloque = ''.join(random.choices(chars, k=caracteres_por_bloque))
                bloques.append(bloque)
            # Unir los bloques con espacio y escribir la línea
            linea = ' '.join(bloques)
            f.write(linea + "\n")
    print(f"Archivo '{n}' creado con {tamaño_mb} MB.")

if __name__ == "__main__":
    try:
        tamaño = int(input("Ingrese el tamaño del archivo en MB: "))
        crear_archivo(tamaño)
    except ValueError:
        print("Por favor, ingrese un número válido.")