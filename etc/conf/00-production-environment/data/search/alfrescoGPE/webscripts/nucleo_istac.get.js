script:
{

	if ((url.templateArgs["nodeId"]) && (url.templateArgs["nodeId"] != "")){
	   model.node = search.findNode("workspace://SpacesStore/" + url.templateArgs["nodeId"]);
	}

	if (model.node == null || model.node == null) {
	   status.code = 404;
	   status.message = "El nodo no ha sido encontrado.";
	   status.redirect = true;
	   break script;
	}
}
