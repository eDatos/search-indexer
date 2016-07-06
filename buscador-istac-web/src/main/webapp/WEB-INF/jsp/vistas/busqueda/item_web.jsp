<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page import="es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain" %>
<%@page import="org.apache.solr.common.SolrDocument" %>


<div class="resultado_busqueda">
	<h3 class="resultado_titulo">
	<c:choose>
		<c:when test="${empty item['nm_title']}"><a href="${item['id']}">${item['id']}</a></c:when>
		<c:when test="${!empty item['nm_title']}"><a href="${item['id']}">${item['nm_title'][0]}</a></c:when>
	</c:choose>
	</h3>
	
 	<c:if test="${!empty item['nm_last_update']}">
 	 	<div class="resultado_item resultado_item_nm_type">
			<fmt:formatDate value="${item['nm_last_update']}" pattern="dd/MM/yyyy" />
		</div>
	</c:if>
	
	<c:if test="${!empty highlightingList}">
 	<div class="resultado_item resultado_item_highlight">	
 		<c:choose>
	 		<c:when test="${!empty highlightingList[item['id']]['nm_description']}">
	 			<c:forEach var="item" items="${highlightingList[item['id']]['nm_description']}" varStatus="status"><c:if test="${status.first}"><b>...</b></c:if><c:out escapeXml="false" value="${item}"/><b> ...</b></c:forEach>
	 		</c:when>
	 		<c:when test="${!empty highlightingList[item['id']]['tk_contenido']}">
	 			<c:forEach var="item" items="${highlightingList[item['id']]['tk_contenido']}" varStatus="status"><c:if test="${status.first}"><b>...</b></c:if><c:out escapeXml="false" value="${item}"/><b> ...</b></c:forEach>
	 		</c:when>
 		</c:choose>			
 	</div>
	</c:if>
</div>