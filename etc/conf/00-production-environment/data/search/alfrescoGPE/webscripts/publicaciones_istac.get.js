
/**
 * 
 * @param nodeChild
 * @return
 */
function nodePxAsObject(nodeChild) {
	var objetoInPx = new Object();
	objetoInPx.id = nodeChild.id;
	objetoInPx.nombre = (nodeChild.properties["istace:nombre"] == null ||  nodeChild.properties["istace:nombre"].length == 0) ? "" : jsonUtils.encodeJSONString(nodeChild.properties["istace:nombre"]);
	objetoInPx.tipoRecurso = nodeChild.properties["istace:tipoRecurso"];

	return objetoInPx;
}

/**
 * 
 * @param nodeChild
 * @return
 */
function nodePublicacionAsObject(nodeChild) {
	var objetoPublicacion = new Object();
	objetoPublicacion.id = nodeChild.id;
	objetoPublicacion.nombre = (nodeChild.properties["istace:nm_title"] == null ||  nodeChild.properties["istace:nm_title"].length == 0) ? "" : jsonUtils.encodeJSONString(nodeChild.properties["istace:nm_title"]);
	objetoPublicacion.tipoRecurso = nodeChild.properties["istace:tipoRecurso"];

	return objetoPublicacion;
}

/**
 * 
 * @param nodeChild
 * @return
 */
function nodeOperacionAsObject(nodeChild) {
	var objetoOperacion = new Object();
	objetoOperacion.id = nodeChild.id;
	objetoOperacion.operacion = jsonUtils.encodeJSONString(nodeChild.properties["istace:nombre"]);
	if (objetoOperacion.operacion == null || objetoOperacion.operacion.length == 0) {
		objetoOperacion.operacion = jsonUtils.encodeJSONString(nodeChild.properties["cm:name"]);
	}
	
	var recursos = new Array();
	var sizeChildren = nodeChild.children.length;
	var node;
	for (var i = 0; i < sizeChildren; i++) {
		if (nodeChild.children[i].properties["cm:name"] == "PUBLICACIONES") {
			var sizeChildrenPub = nodeChild.children[i].children.length;
			for (var j = 0; j < sizeChildrenPub; j++) {
				node = nodeChild.children[i].children[j];
				if (node.type == "{http://www.gobiernodecanarias.org/istac/estadisticas/1.0}publicacion") {
					recursos[recursos.length] = nodePublicacionAsObject(node)	
				}
			}
		}
		else if (nodeChild.children[i].properties["cm:name"] == "PXS") {
			var sizeChildrenPx = nodeChild.children[i].children.length;
			for (var j = 0; j < sizeChildrenPx; j++) {
				node = nodeChild.children[i].children[j];
				if (node.type == "{http://www.gobiernodecanarias.org/istac/estadisticas/1.0}archivoPX") { //No es necesariamente un px, puede ser un pdf de info comp
					recursos[recursos.length] = nodePxAsObject(node);
				}
			}
		}
	}
	
	// Recursos
	objetoOperacion.recursos = recursos;
	
	return objetoOperacion;
}

script:
{
	// Se coge el path REST, este debe ser el home de los archivos del GPE
	var folder;
	if ((url.extension) && (url.extension != "")){
		var folder = companyhome.childByNamePath(url.extension + "/OPERACIONES");
	}
	
	if (folder == undefined || !folder.isContainer) {
		status.code = 404;
	    status.message = "Folder " + url.extension + " not found.";
	    status.redirect = true;
	    break script;
	}
	
	// Recorremos las operaciones
	var nodeChild;
	var nodesFolderOperaciones = new Array();
	var sizeChildren = folder.children.length;
	for (var i = 0; i < sizeChildren; i++) {
		nodeChild = folder.children[i];
		if (nodeChild.type == "{http://www.gobiernodecanarias.org/istac/estadisticas/1.0}operacion") {
			//Nodo nodesFolderOperaciones
			nodesFolderOperaciones[nodesFolderOperaciones.length] = nodeOperacionAsObject(nodeChild);
		}
    }
	
	//**************************************************
	model.resultado = jsonUtils.toJSONString(nodesFolderOperaciones);
}