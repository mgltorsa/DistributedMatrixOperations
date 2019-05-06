Integrantes
Miguel Angel Torres Sanchez
Mauricio Hernandez Motta

Notas:
En la carpeta clientMatrix/src/main/resources/com/archisoft existe el archivo properties.properties
en este existen 2 propiedades: matrix1 y matrix2, cada una de ellas es una ruta absoluta que apunta
un archivo con matrices, es necesario cambiar y especificar estas rutas para que el programa funcione
correctamente.

Primero compilar el servidor en el folder serverMatrix. Una vez en la consola y dentro del folder
ejecute el comando:

    frascati compile src/main/java server

Luego, para correr el servidor, aun dentro del folder serverMatrix, ejecute:

    frascati run serverMatrix -libpath server.jar


Para compilar el cliente, abra el folder clientMatrix en la consola y ejecute el comando:

    frascati compile src/main/java client

Entonces, para correrlo ejecute, aun en el folder:

    frascati run clientMultiplication -libpath client.jar -s r -m run

