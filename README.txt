Integrantes Miguel Angel Torres - Mauricio Hernandez Motta

Para hacer el deployment del proyecto respectivo, basta con ejecutar el script de deploy en cada
uno de los folders de los proyectos (NOTA: ejecute el script una vez este sobre el folder del proyecto correspondiente).

el orden de deploy debe ser.

./netservices/deploynetservices
./operations/deploymatrix
./operations/deployoperations
./serializer/deployserializer
./server/deployserver
./client/deployclient



Las anteriores son instrucciones para hacer el deployment monolitico del sistema.

Para desplegar en diferentes maquinas, puede correr los scripts anteriores pero debe asegurarse que los references de rmi en los respectivos archivos composites (ubicados en src/main/resources de cada proyect) esten correctamente configurados, es decir, tengan las ip correspodientes en donde estan montados los diferentes servicios.


En caso de que los scripts anteriores no funcionen correctamente debera dirigirse al folder correspondiente para cada proyecto y visualizar las instrucciones en el respectivo archivo README.txt. Recuerde que, el orden de inicializacion debe ser:

netservices
operations
serializer
server
client


//Informacion RMI
Servicios y puertos.

BROKER

port: 5555
rmiServiceName: redirecting

TiffProcessor

port: 5099
rmiServiceName: tiff


Serializer
port: 5010
rmiServiceName: serializer
