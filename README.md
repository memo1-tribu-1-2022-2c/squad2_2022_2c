# Modulo de Proyectos

## Ejecución

Para compilar el proyecto se debe ejecutar el siguiente comando:

```bash
mvn clean install
```

Esto descargará las dependencias de ser necesario y generará un archivo `.jar` ejecutable en la carpeta target. Para levantar el proyecto se debe ejecutar el siguiente comando:

```bash
java -jar .\target\projects-<VERSION>.jar --spring.config.location="path\al\perfil\que\usamos\application.properties"
```

El perfil en la carpeta de resources de tests posee la configuración para levantar la base de datos en memoria. En cambio el perfil en la carpeta de resources de main posee la configuración para levantar la base de datos mysql en producción.

El swagger se encuentra en el siguiente [link](http://localhost:8080/swagger-ui/index.html).

## Base de Datos

Para levantar la base de datos hay que ejecutar con Docker:

```docker
docker run --name memodb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pass123 -d mysql
```

Y para acomodar y crear las tablas se debe ejecutar el script sql en resources. Cada vez que se ejecute el servicio, se generará en la carpeta root del proyecto un archivo `create.sql` el cual contiene las queries para crear las tablas de la base de datos (se debe borrar la metadata innecesaria y agregar los `;` al final de las líneas, se recomienda editar el archivo en masa)