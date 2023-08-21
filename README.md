## INSTRUCCIONES

1. Para generar una nueva imagen correr:
``` bash
docker build -t clojure-datascience --build-arg USER_ID=$(id -u) --build-arg GROUP_ID=$(id -g) .
```
O correr el script ./docker_build.sh
 
Si se desean agregar dependencias de Python o R, hay que ir al Dockerfile y agregar el comando

RUN pip install <nombre del paquete>

De manera análoga se procede con los paquetes de R.

Luego hay que reconstruir la imagen con el comando indicado en (1)

2. Para correr el REPL en modo headless correr el siguiente script:

```bash
./docker_run_xxx.sh
```

