Integrantes Miguel Angel Torres - Mauricio Hernandez Motta

Para hacer el deployment del proyecto respectivo, basta con ejecutar el script de deploy en cada
uno de los folders de los proyectos

el orden de deploy debe ser.

./netservices/deploynetservices
./operations/deploymatrix
./operations/deployoperations
./serializer/deployserializer
./server/deployserver
./client/deployclient

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
