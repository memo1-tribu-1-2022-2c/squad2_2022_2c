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