# Buscador Istac

## Componentes

### Buscador App (buscador-istac-web)

Aplicación cliente que implementa la interfaz de búsqueda de usuario del Istac. Es una aplicación pública


### Indexador App (search-internal-web)

Aplicación (privada) que realiza el mantenimiento de los recursos indexados por el buscador Istac.


### Módulo OpenCMS (buscador-opencms)

Módulo de OpenCMS que actua de proxy con la aplicación "buscador-istac-web" para incrustar el buscador en el gestor de contenidos OpenCMS.


### Configuración de core de SOLR (buscador-opencms)

Contiene la configuración necesaria para crear un core de indexación en un servidor Solr para el buscador del Istac


## Otra información relevante

Ver confluence

