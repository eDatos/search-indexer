<%@page import="java.io.OutputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="org.opencms.jsp.CmsJspActionElement"%>
<%@page import="org.opencms.util.CmsRequestUtil"%>

<%@ page pageEncoding="UTF-8" %><%@page buffer="none" session="false" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="<%= request.getLocale() %>" />
<cms:formatter var="content" val="value">
<div>

<%--  Important: the argument file="search" in cms:property is IMPORTANT. With this argument, if the property is not set for the resource, its parent folders are searched. --%>
<c:set var="ApiUrl">
    <cms:property name="istac.buscador.endpoint" file="search" default="FILL_ME" />
</c:set>

<%--  ************************************************************************* --%>
<%--  Declaration: variables, functions, static data...                         --%>
<%--  ************************************************************************* --%>
<%!
    public final static String PROXY_REFERER = "http://localhost/proxy.jsp";
    public final static String START_FETCH_TAG = "<!-- OPENCMS -->";
    public final static String END_FETCH_TAG = "<!-- /OPENCMS -->";
    public final static String START_HEADER_FETCH_TAG = "<!-- OPENCMS-HEADER -->";
    public final static String END_HEADER_FETCH_TAG = "<!-- /OPENCMS-HEADER -->";
    public final static String START_FOOTER_FETCH_TAG = "<!-- OPENCMS-FOOTER -->";
    public final static String END_FOOTER_FETCH_TAG = "<!-- /OPENCMS-FOOTER -->";

    public final static String EMPTY_STRING = "";
    public final static String HTTP_POST = "POST";
    
    String buscadorEndpoint = null;
    
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static String filter(String source, String startTag, String endTag) {
        String result = EMPTY_STRING;
        int idxStart = source.indexOf(startTag);
        int idxEnd = source.indexOf(endTag);
        // No filter
        if (idxStart != -1 && idxEnd != -1) {
            result = source.substring(idxStart + startTag.length(), idxEnd);
        }
        
        if (result == null || result == EMPTY_STRING) {
            result = "<!-- NO DATA -->" + startTag + endTag;
        }
        return result;
    }

    public static String addFinalSlashIfNecessary(String source) {
        if (source == null) {
            return source;
        }

        if (source.charAt(source.length() - 1) != '/') {
            source += "/";
        }

        return source.toLowerCase();
    }

    public byte[] readRequestPostBody(HttpServletRequest request) throws IOException {
        int clength = request.getContentLength();
        if (clength > 0) {
            byte[] bytes = new byte[clength];
            DataInputStream dataIs = new DataInputStream(request.getInputStream());

            dataIs.readFully(bytes);
            dataIs.close();
            return bytes;
        }

        return new byte[0];
    }

    public HttpURLConnection forwardToServer(HttpServletRequest request, String uri, byte[] postBody) throws IOException {
        return postBody.length > 0 ? doHTTPRequest(uri, postBody, HTTP_POST, request.getHeader("Referer"), request.getContentType()) : doHTTPRequest(uri, request.getMethod());
    }

    private HttpURLConnection doHTTPRequest(String uri, String method) throws IOException {

        byte[] bytes = null;
        String contentType = null;
        if (method.equals(HTTP_POST)) {
            String[] uriArray = uri.split("\\?");

            if (uriArray.length > 1) {
                contentType = "application/x-www-form-urlencoded";
                String queryString = uriArray[1];

                bytes = URLEncoder.encode(queryString, "UTF-8").getBytes();
            }
        }
        return doHTTPRequest(uri, bytes, method, PROXY_REFERER, contentType);
    }

    private HttpURLConnection doHTTPRequest(String uri, byte[] bytes, String method, String referer, String contentType) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(30000);
        con.setReadTimeout(120000);

        con.setRequestProperty("Referer", referer);
        con.setRequestMethod(method);

        if (bytes != null && bytes.length > 0 || method.equals(HTTP_POST)) {

            if (bytes == null) {
                bytes = new byte[0];
            }

            con.setRequestMethod(HTTP_POST);
            con.setDoOutput(true);
            if (contentType == null || contentType.isEmpty()) {
                contentType = "application/x-www-form-urlencoded";
            }

            con.setRequestProperty("Content-Type", contentType);

            OutputStream os = con.getOutputStream();
            os.write(bytes);
        }

        return con;
    }

    public String fetchAndPassBackToClient(HttpURLConnection con, HttpServletResponse clientResponse, boolean ignoreAuthenticationErrors) throws IOException {
        if (con != null) {
            if (con.getResponseCode() >= 400 && con.getErrorStream() != null) {
                return EMPTY_STRING;
            }
            return getStringFromInputStream(con.getInputStream());

        }
        return EMPTY_STRING;
    }%>


<%--  ************************************************************************* --%>
<%--  Body Code                                                                 --%>
<%--  ************************************************************************* --%>
<%
    String uri = EMPTY_STRING;

    // HTTP redirect to ROOT context (index.html)
    if (request.getPathInfo().endsWith("buscar")) {
        CmsJspActionElement jsp = new CmsJspActionElement(pageContext, request, response);
        CmsRequestUtil.redirectRequestSecure(jsp,"./");
    }

    // Enpoint normalize
    buscadorEndpoint = (String)pageContext.getAttribute("ApiUrl");
    if ("FILL_ME".equals(buscadorEndpoint)) {
        System.out.println("ERROR: Endpoint no encontrado. ¿Ha olvidado configurar el valor de la propiedad ApiUrl en OpenCMS?");
        return;
    }

    buscadorEndpoint = addFinalSlashIfNecessary(buscadorEndpoint);

    // Request path normalize
    String normalizePath = addFinalSlashIfNecessary(request.getPathInfo());
    if (normalizePath.endsWith("facets/")) {
        uri = buscadorEndpoint + "facets";
    }
    else if (normalizePath.endsWith("busca/")) {
        uri = buscadorEndpoint + "busca";
    }
    else {
        uri = buscadorEndpoint;
    }
    
    // Add query parameters
    if (request.getQueryString() != null) {
        uri += "?" + request.getQueryString();
    }
    
    // Readying body (if any) of POST request
    byte[] postBody = readRequestPostBody(request);
    String post = new String(postBody);
    
    // Execute proxy request
    HttpURLConnection con = forwardToServer(request, uri, postBody);
    String fetched = fetchAndPassBackToClient(con, response, true);
    
    // Print Output

    out.println(filter(fetched, START_HEADER_FETCH_TAG, END_HEADER_FETCH_TAG));
    
    out.println(filter(fetched, START_FETCH_TAG, END_FETCH_TAG));
    
    out.println(filter(fetched, START_FOOTER_FETCH_TAG, END_FOOTER_FETCH_TAG));

%>

</div>
</cms:formatter>