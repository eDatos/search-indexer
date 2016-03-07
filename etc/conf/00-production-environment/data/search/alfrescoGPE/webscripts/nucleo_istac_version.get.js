script:
{
	nodeId = null;
	nm_version = null;
	if ((url.templateArgs["nodeId"]) && (url.templateArgs["nodeId"] != "")){
		nodeId = url.templateArgs["nodeId"];
	}
	if ((url.templateArgs["version"]) && (url.templateArgs["version"] != "")){
		nm_version = url.templateArgs["version"];
		nm_version = nm_version.replace("_",".");
	}
			
	if (nodeId != null && nm_version != null) {
	   node = search.findNode("workspace://SpacesStore/" + nodeId);
	   vHistory =  node.getVersionHistory();
	   for (index in vHistory) {
		   vNode = vHistory[index].getNode();
		   node_version = vNode.properties['{http://www.gobiernodecanarias.org/istac/estadisticas/1.0}nm_version'];
		   if (node_version == nm_version) {
			   model.node = vNode;
			   break;
		   }
	   }
	}

	if (model.node == null || model.node == null) {
	   status.code = 404;
	   status.message = "El nodo no ha sido encontrado.";
	   status.redirect = true;
	   break script;
	}
}
