# Rest API de productos

Para utilizar la aplicación se deben seguir los siguientes pasos:

1. Clonar este repositorio en su computadora.
2. Abrir una terminal dentro de la carpeta descargada (puede ser CMD, Git Bash o cualquier otra).
3. Ejecutar el comando ./mvnw spring-boot:run. Luego de que aparezca un mensaje del estilo "Started Application in 4.685 seconds" la aplicación está lista para usarse.

La app nos permite administrar productos, donde cada producto tiene nombre, descripción, precio y cantidad. Ya viene cargada con algunos datos, y se puede utilizar desde aplicaciones como Postman.
Los endpoints disponibles son:

1. Listar todos los productos: http://localhost:8080/api/products (GET)
2. Obtener un producto por su id: http://localhost:8080/api/products/{id} (GET)
3. Crear un nuevo producto: http://localhost:8080/api/products (POST)
4. Editar un producto: http://localhost:8080/api/products/{id} (PUT)
5. Eliminar un producto: http://localhost:8080/api/products (DELETE)

Para los endpoints 3 y 5 se requiere enviar un cuerpo en el HTTP request. El formato de este cuerpo es el siguiente:

    {
        "name": "Heladera",
        "description": "Contiene freezer y modo descongelación.",
        "price": "200000",
        "amount": "1"
    }

Si modificamos el endpoint 1 de modo que nos quede http://localhost:8080/api/products?order=price obtenemos el listado de productos ordenados de menor a mayor por precio.