
script:
{
	//PATH:"/app:company_home/cm:Aplicaciones/cm:Istac//*" AND @istace\:nm_identifier_universal:"urn:uuid:1478a039-c137-4739-a23f-91bf6a185bcb"
	var path = args.path;
	var urn_uuid = args.urn_uuid;
	var tmp_query = "PATH:\"/app:company_home/"+path+"//*\" AND @istace\\:nm_identifier_universal:\""+urn_uuid+"\"";
	
	var def =
	{
	query: tmp_query,
	store: "workspace://SpacesStore",
	language: "lucene",
	};
	// Ejecucion
	var results = search.query(def);

	model.resultado = results[0];
}