<%@ page pageEncoding="UTF-8"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@include file="../util/taglibs.jsp"%>
	<head>
		<title><fmt:message key="welcome.title" /></title>
		<meta http-equiv="Content-Type"	content="text/html; charset=UTF-8" />
		<meta name="robots" content="all" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta name="keywords" content="gobierno canarias" />
		<meta name="description" content="Instituto Canario de Estadística" />
		<meta http-equiv="imagetoolbar" content="no" />
  		
        
        <!-- Gobierno de Canarias -->
        <link rel="shortcut icon" href="http://www.gobiernodecanarias.org/gcc/img/favicon.ico"/>
                
        <!-- Los siguientes estilos del Gobcan-Istac se incluyen en la plantilla de OpenCMS por tanto están fuera del bloque OPENCMS-HEADER -->        
        <link href="http://www.gobiernodecanarias.org/gcc/css/estilos.css" rel="stylesheet" type="text/css" media="screen"/>
        <link href="http://www.gobiernodecanarias.org/gcc/css/imprime.css" rel="stylesheet" type="text/css" media="print"/>
        <link href="http://www.gobiernodecanarias.org/gcc/css/voz.css" rel="stylesheet" type="text/css" media="aural"/>
        <link href="http://www.gobiernodecanarias.org/opencms8/export/system/modules/es.gobcan.istac.web/resources/css/gobcanoverwrite.css" rel="stylesheet" type="text/css">
        
        <!-- {{{ --><!-- OPENCMS-HEADER --><!-- }}} -->	

	 	<!-- Hojas de estilo -->
        <link rel="stylesheet" href="http://www.gobiernodecanarias.org/gc/css/buscador.css"/>
        <link rel="stylesheet" href="http://www.gobiernodecanarias.org/gc/css/google.css"/>

        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/gobcanoverwrite.css"/>
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/paginadorsimple.css"/>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/istac-bs/css/istac-bs.css"/>
        
        <!-- Optional theme -->
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/istac-bs/css/istac-bstheme.css"/>
        
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/jquery.qtip-2.2.1.min.css"/>
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/select2-4.0.0.min.css" />
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/select2-bootstrap.min.css" />

        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/main.css"/>
        <!-- {{{ --><!-- /OPENCMS-HEADER --><!-- }}} -->  
        
	</head>