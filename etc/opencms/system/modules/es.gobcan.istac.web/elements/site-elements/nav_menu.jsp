<%@page buffer="none" session="false" taglibs="c,cms,fn" %>
<cms:navigation type="forFolder" startLevel="1" var="nav"/>
	<div id="menu_contextual">
		<c:if test="${!empty nav.items}">
			<ul class="menu">
				<c:set var="oldLevel" value="" />
				<c:forEach items="${nav.items}" var="elem" varStatus="navCounter">
					<c:set var="currentLevel" value="${elem.navTreeLevel}" />
					
					<c:choose>
						<c:when test="${empty oldLevel}"></c:when>
						<c:when test="${currentLevel > oldLevel}"><ul></c:when>
						<c:when test="${currentLevel == oldLevel}"></li></c:when>
						<c:when test="${oldLevel > currentLevel}">
							<c:forEach begin="${currentLevel+1}" end="${oldLevel}"></li></ul></c:forEach>
						</c:when>
					</c:choose>
					
					<li>
					<a href="<cms:link>${elem.resourceName}</cms:link>" <c:choose><c:when test="${fn:startsWith(cms.requestContext.uri, elem.resourceName)}">class="select"</c:when><c:otherwise>class="inactive"</c:otherwise></c:choose> accesskey="${navCounter.count}" title="${elem.navText} (tecla de acceso: ${navCounter.count})">${elem.navText}</a>
					
					<c:set var="oldLevel" value="${currentLevel}" />
				</c:forEach>
				
				<c:forEach begin="1" end="${oldLevel}"></li></ul></c:forEach>
			</ul>
		</c:if>
        <!-- Formulario de cabecera para integración con el buscador -->
		<form name="gs" method="GET" action="<cms:property name="istac.buscador.cabecera.endpoint" file="search" default="FILL_ME"/>">
			<div id="formulario_google" style="padding-top: 1px;">
				<input type="text" id="userQuery" name="userQuery" placeholder="texto de búsqueda" class="buscar" value="">
				<input type="submit" value="Buscar" class="boton">
			</div>
		</form>
	</div>