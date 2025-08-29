import random, string, argparse  # Random/texto y manejo de argumentos

def crear_archivo(tamaño_mb):
    n=f"ArchGen_{tamaño_mb}MB.txt"  # Nombre auto
    chars=string.ascii_letters+string.digits  # Letras+números
    size=tamaño_mb*1024*1024  # MB→bytes
    with open(n,"w",encoding="utf-8") as f: # Abre un archivo o lo crea y lo determina como f
        for _ in range(size//1024):  # Bloques de 1KB (más rápido, menos RAM)
            f.write("".join(random.choice(chars) for _ in range(1024)))
        f.write("".join(random.choice(chars) for _ in range(size%1024)))  # Sobrante
    print(f"Archivo '{n}' creado con {tamaño_mb} MB.")  # Confirmación

if __name__=="__main__":
    p=argparse.ArgumentParser(description="Generador de archivos aleatorios (letras/números)")
    p.add_argument("tamaño",type=int,help="Tamaño en MB del archivo")  # Parámetro en consola
    args=p.parse_args()
    crear_archivo(args.tamaño)