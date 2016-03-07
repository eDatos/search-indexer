{
	"identifierUniversal": "${node.properties["istace:nm_identifier_universal"]?string}",
	"tipoRecurso": "${jsonUtils.encodeJSONString(node.properties["istace:tipoRecurso"]!""?string)}",
	"type": [
			<#if node.properties["istace:nm_type"]??>
			<#list node.properties["istace:nm_type"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	"title": "${jsonUtils.encodeJSONString(node.properties["istace:nm_title"]!""?string)}",
	"subtitle": [
			<#if node.properties["istace:nm_subtitle"]??>
			<#list node.properties["istace:nm_subtitle"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	"titleAlternative": [
			<#if node.properties["istace:nm_title_alternative"]??>
			<#list node.properties["istace:nm_title_alternative"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		     </#if>
	    ],
	 "surveyCode": "${jsonUtils.encodeJSONString(node.properties["istace:nm_survey_code"]!""?string)}",   
	 "surveyTitle": "${jsonUtils.encodeJSONString(node.properties["istace:nm_survey_title"]!""?string)}",   
	 "surveyAlternative": "${jsonUtils.encodeJSONString(node.properties["istace:nm_survey_alternative"]!""?string)}",   
	 "subjectAreas": [
			<#if node.properties["istace:nm_subject_areas"]??>
			<#list node.properties["istace:nm_subject_areas"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	  "subjectCodes": [
	  		<#if node.properties["istace:nm_subject_codes"]??>
			<#list node.properties["istace:nm_subject_codes"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	  "description": [
	  		<#if node.properties["istace:nm_description"]??>
			<#list node.properties["istace:nm_description"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	  "abstract": [
	 		<#if node.properties["istace:nm_abstract"]??>
			<#list node.properties["istace:nm_abstract"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	   "keywords": [
	   		<#if node.properties["istace:nm_keywords"]??>
			<#list node.properties["istace:nm_keywords"] as d>
				"${jsonUtils.encodeJSONString(d!""?string)}"
				<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	   "coverageSpatial": [
	   		<#if node.properties["istace:nm_coverage_spatial"]??>
			<#list node.properties["istace:nm_coverage_spatial"] as d>
					"${jsonUtils.encodeJSONString(d!""?string)}"
					<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	    "coverageSpatialCodes": [
	    	<#if node.properties["istace:nm_coverage_spatial_codes"]??>
			<#list node.properties["istace:nm_coverage_spatial_codes"] as d>
					"${jsonUtils.encodeJSONString(d!""?string)}"
					<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	    "coverageTemporal": [
	   		<#if node.properties["istace:nm_coverage_temporal"]??>
			<#list node.properties["istace:nm_coverage_temporal"] as d>
					"${jsonUtils.encodeJSONString(d!""?string)}"
					<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	    "coverageTemporalCodes": [
	   		<#if node.properties["istace:nm_coverage_temporal_codes"]??>
			<#list node.properties["istace:nm_coverage_temporal_codes"] as d>
					"${jsonUtils.encodeJSONString(d!""?string)}"	
					<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	    "replaces": [
	   		<#if node.properties["istace:nm_replaces"]??>
			<#list node.properties["istace:nm_replaces"] as d>
					"${jsonUtils.encodeJSONString(d!""?string)}"	
					<#if d_has_next>,</#if>
		    </#list>
		    </#if>
	    ],
	    <#if node.properties["istace:nm_last_update"]??>
	    "lastUpdate": "${node.properties["istace:nm_last_update"]?string("yyyy-MM-dd'T'HH:mm:ss.'000'Z")}",
	    <#else>
	    "lastUpdate": "",
		</#if>
		<#if node.properties["istace:nm_range_dates_available"]??>
	    "rangeDatesAvailable": "${node.properties["istace:nm_range_dates_available"]?string}"
	    <#else>
	    "rangeDatesAvailable": ""
		</#if>
}