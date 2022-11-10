# Modulo de Proyectos

## Ejecución

Para compilar el proyecto se debe ejecutar el siguiente comando:

```bash
mvn clean install
```

Esto descargará las dependencias de ser necesario y generará un archivo `.jar` ejecutable en la carpeta target. Para levantar el proyecto se debe ejecutar el siguiente comando:

```bash
java -jar .\target\projects-<VERSION>.jar
```

El swagger se encuentra en el siguiente [link](http://localhost:8080/swagger-ui/index.html).

## Base de Datos

Para levantar la base de datos hay que ejecutar con Docker:

```docker
docker run --name memodb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pass123 -d mysql
```

Y para acomodar y crear las tablas se debe ejecutar el script sql en resources.