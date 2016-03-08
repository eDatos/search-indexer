A la hora de entregar el entregable del proyecto, entregar sólo la carpeta "solr/istac" excluyendo el contenido que pudiera haber en el subpath "solr/istac/data*".
Los archivos a nivel superior "solr.xml" y "zoo.cfg" son propios de configuraciones del servidor y no de la distribución.
Existen en esta carpeta puesto que los test del core utilizan la configuración de "solr/istac" para crear un core dummy de indexación para test
y por tanto tener así siempre sincronizados la configuración solr de test con la que se entrega.