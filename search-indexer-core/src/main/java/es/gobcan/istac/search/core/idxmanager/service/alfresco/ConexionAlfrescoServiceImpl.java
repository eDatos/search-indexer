package es.gobcan.istac.search.core.idxmanager.service.alfresco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import es.gobcan.istac.idxmanager.domain.alfresco.ContenidoOperacion;
import es.gobcan.istac.idxmanager.domain.alfresco.NucleoMetadatosPublicado;
import es.gobcan.istac.idxmanager.domain.util.ISO8601DateFormat;
import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.util.ServiceUtils;

@Service
public class ConexionAlfrescoServiceImpl implements ConexionAlfrescoService {

    protected Log      log    = LogFactory.getLog(ConexionAlfrescoServiceImpl.class);

    private String     ticket = null;

    @Autowired
    private HttpParams httpParams;

    private String obtenerTicket() throws es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion {
        if (!isValidTicket()) {
            // Hay que pedir nuevo ticket
            ticket = obtenerNuevoTicket();
        }
        return ticket;
    }

    private boolean isValidTicket() {
        try {
            if (ticket == null) {
                return false;
            }

            StringBuilder urlAlfresco = new StringBuilder(getConfigurationService().retrieveAlfrescoUrl());
            if (urlAlfresco.charAt(urlAlfresco.length() - 1) != '/') {
                urlAlfresco.append("/");
            }

            // Para validar el ticket hay que llamar con el propio ticket
            URL url = new URL(urlAlfresco + "service/api/login/ticket/" + ticket + "?alf_ticket=" + ticket);
            URLConnection urlCon = url.openConnection();

            if (urlCon instanceof HttpURLConnection) {
                HttpURLConnection httpCon = (HttpURLConnection) urlCon;
                if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Validado
                    return true;
                }
            }

        } catch (Exception e) {
            log.warn("Warning: ConexionAlfrescoServiceImpl:isValidTicket no ha podido comprobar la validez del ticket, se da como no valido " + e);
        }
        return false;
    }

    private synchronized String obtenerNuevoTicket() throws ServiceExcepcion {
        // Ticket Alfresco
        try {

            StringBuilder urlTicketSB = new StringBuilder(getConfigurationService().retrieveAlfrescoUrl());
            if (urlTicketSB.charAt(urlTicketSB.length() - 1) != '/') {
                urlTicketSB.append("/");
            }
            urlTicketSB.append("service/api/login?");
            urlTicketSB.append("u=").append(getConfigurationService().retrieveAlfrescoUsername());
            urlTicketSB.append("&pw=").append(getConfigurationService().retrieveAlfrescoPassword());

            // URL urlTicket = new URL(urlTicketSB.toString());
            // BufferedReader in = new BufferedReader(new InputStreamReader(urlTicket.openStream(), "UTF-8"));
            File contentFile = fetchContentFromUrl(urlTicketSB.toString());
            List<String> readLines = IOUtils.readLines(new FileInputStream(contentFile));

            // En el grupo 1, se queda el ticket
            Pattern patronTicket = Pattern.compile("<ticket>(.*)</ticket>");

            // String inputLine;
            // String dev = StringUtils.EMPTY;
            // while ((inputLine = in.readLine()) != null) {
            // if (inputLine.startsWith("<ticket>")) {
            // Matcher matching = patronTicket.matcher(inputLine);
            // if (matching.matches()) {
            // dev = matching.group(1);
            // }
            // }
            // }
            // in.close();
            String dev = StringUtils.EMPTY;
            for (String inputLine : readLines) {
                if (inputLine.startsWith("<ticket>")) {
                    Matcher matching = patronTicket.matcher(inputLine);
                    if (matching.matches()) {
                        dev = matching.group(1);
                        break;
                    }
                }
            }

            if (StringUtils.isEmpty(dev)) {
                throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_TICKET_ALFRESCO);
            }

            return dev;
        } catch (Exception e) {
            log.error("Error: ConexionAlfresco:obtenerTicket " + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_TICKET_ALFRESCO);
        }
    }

    /*
     * Le añade como prefio a la url la url de alfresco y le añade un ticket valido
     */
    private String buildStringURL(String uri) throws MalformedURLException, ServiceExcepcion, MetamacException {
        StringBuilder urlAlfresco = new StringBuilder(getConfigurationService().retrieveAlfrescoUrl());
        if (urlAlfresco.charAt(urlAlfresco.length() - 1) != '/') {
            urlAlfresco.append("/");
        }

        obtenerTicket();
        return urlAlfresco + uri + (uri.lastIndexOf('?') == -1 ? ("?alf_ticket=" + ticket) : ("&alf_ticket=" + ticket));
    }

    private SearchConfigurationService getConfigurationService() {
        return (SearchConfigurationService) ApplicationContextProvider.getApplicationContext().getBean("configurationService");
    }

    @Override
    public String obtenerContenidoNodo(String identifierUniv) throws ServiceExcepcion {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Url dle servicio que devuelve un nodeId a partir del universal identifier del istac y Le ponemos los parametros
            StringBuilder urlNodeIdServiceSB = new StringBuilder("service/istac/nodeUuid_fromUrn");
            urlNodeIdServiceSB.append("?path=").append(getConfigurationService().retrieveAlfrescoPath());
            urlNodeIdServiceSB.append("&urn_uuid=").append(identifierUniv);

            String urlNodeIdServiceStr = buildStringURL(urlNodeIdServiceSB.toString());
            log.info("Uri get nodeId :: " + urlNodeIdServiceStr);

            File nodeIdFile = fetchContentFromUrl(urlNodeIdServiceStr);
            Map<String, String> mapValues = mapper.readValue(nodeIdFile, Map.class);

            String nodeUuid = mapValues.get("id");

            // 1. Url del Servicio de contenido
            StringBuilder urlContenidoSB = new StringBuilder("service/api/node/content/workspace/SpacesStore/").append(nodeUuid);

            // 2. Construir URL
            String urlContenidoStr = buildStringURL(urlContenidoSB.toString());
            log.info("Uri node content:: " + urlContenidoStr);
            File contenidoFile = fetchContentFromUrl(urlContenidoStr);

            ContentHandler contentHandler = new BodyContentHandler(ServiceUtils.MAX_TIKA_LIMIT);
            Parser parser = new AutoDetectParser();
            parser.parse(new FileInputStream(contenidoFile), contentHandler, new Metadata(), new ParseContext());
            return contentHandler.toString();
        } catch (TikaException | SAXException tikaException) {
            log.warn("Ha ocurrido un problema extrayendo contenido con TIKA, el nodo: " + identifierUniv + " sólo se indexará por el contenido de su núcleo de metadatos. Detalles: ", tikaException);
            return null;
        } catch (Exception e) {
            log.error("Error ConexionAlfresco:getNodeTextContent " + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_NODE_CONTENT_ALFRESCO);
        }
    }

    /**
     * @param nodeId
     * @return
     * @throws ServiceExcepcion
     */
    @Override
    public NucleoMetadatosPublicado obtenerNodoNucleoMetadatos(String nodeId) throws ServiceExcepcion {
        // URL urlObtencionRecurso = null;
        try {
            ObjectMapper objMapper = new ObjectMapper();
            String urlObtencionRecursoStr = "service/istac/nucleo_istac/" + nodeId;
            urlObtencionRecursoStr = buildStringURL(urlObtencionRecursoStr);
            log.info("Uri nodo Alfresco:: " + urlObtencionRecursoStr);
            File fetchContentFile = fetchContentFromUrl(urlObtencionRecursoStr);

            // 3. Obtencion del rercurso
            NucleoMetadatosPublicado recurso = objMapper.readValue(fetchContentFile, NucleoMetadatosPublicado.class);

            return recurso;

        } catch (Exception e) {
            log.error("Error: ConexionAlfrescoService:obtenerNodoNucleoMetadatos " + e);

            // try {
            // String contenido = urlObtencionRecurso != null ? IOUtils.toString(urlObtencionRecurso.openStream()) : StringUtils.EMPTY;
            // log.error("Contenido: " + contenido);
            // } catch (IOException e1) {
            // log.error("Error alfresco content: ", e1);
            // throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_CONTENIDO_NUCLEO_ALFRESCO);
            // }
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_NODO_NUCLEO_METADATOS_ALFRESCO);
        }
    }

    @Override
    public NucleoMetadatosPublicado obtenerNodoNucleoMetadatosUltimaVersionVisible(String nodeId) {
        try {
            NucleoMetadatosPublicado nucleoMetadatosPublicado = obtenerNodoNucleoMetadatos(nodeId);

            Date fechaVisible = ISO8601DateFormat.parse(nucleoMetadatosPublicado.getRangeDatesAvailable());

            if (fechaVisible == null) {
                return null;
            }

            // Si es visible lo indexamos
            if (fechaVisible.before(Calendar.getInstance().getTime())) {
                return nucleoMetadatosPublicado;
            }

            // Si no es visible, Vemos si la version anterior esta publicada
            if (!nucleoMetadatosPublicado.getReplaces().isEmpty()) {
                String version = getVersionFromIdentifier(nucleoMetadatosPublicado.getReplaces().get(0));
                return obtenerNodoNucleoMetadatosVersion(nodeId, version);
            }

        } catch (Exception e) {
            log.error("Error obtenerNodoNucleoMetadatosUltimaVersionVisible: ", e);
        }
        return null;
    }

    private NucleoMetadatosPublicado obtenerNodoNucleoMetadatosVersion(String nodeId, String version) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            String urlObtencionRecursoStr = "service/istac/nucleo_istac_version/" + nodeId + "/" + version;
            urlObtencionRecursoStr = buildStringURL(urlObtencionRecursoStr);
            log.info("Uri nodo Alfresco:: " + urlObtencionRecursoStr);
            File fetchContentFromUrl = fetchContentFromUrl(urlObtencionRecursoStr);
            return objMapper.readValue(fetchContentFromUrl, NucleoMetadatosPublicado.class);
        } catch (Exception e) {
            log.error("Error obtenerNodoNucleoMetadatosVersion: ", e);
            return null;
        }
    }

    @Override
    public List<ContenidoOperacion> obtenerRecursosPublicados() throws ServiceExcepcion {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            StringBuilder urlRecursosPublicadosSB = new StringBuilder("service/istac/publicaciones_istac/");
            urlRecursosPublicadosSB.append(getConfigurationService().retrieveAlfrescoPath().replaceAll("cm:", ""));

            String urlRecursosPublicados = buildStringURL(urlRecursosPublicadosSB.toString());
            log.info("Uri recursos publicados:: " + urlRecursosPublicados);
            File fetchContentFromUrl = fetchContentFromUrl(urlRecursosPublicados);
            return objMapper.readValue(fetchContentFromUrl, new TypeReference<List<ContenidoOperacion>>() {});
        } catch (Exception e) {
            log.error("Error ConexionAlfrescoServiceImpl:obtenerRecursosPublicados " + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_RECURSOS_PUBLICADOS_ALFRESCO);
        }
    }

    private String getVersionFromIdentifier(String identifier) {
        String[] splitIdent = identifier.split("_");
        String lastSplit = splitIdent[splitIdent.length - 1];
        String version = null;

        if (lastSplit.startsWith("V")) {
            Pattern pat = Pattern.compile("V(\\d\\d)(\\d\\d\\d)");
            Matcher matcher = pat.matcher(lastSplit);
            if (matcher.find() && matcher.groupCount() == 3) {
                version = matcher.group(1) + "." + matcher.group(2);
            }
        }
        return version;
    }

    private File fetchContentFromUrl(String url) throws ServiceExcepcion {
        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

        HttpGet httpGet = new HttpGet(url);
        File result = null;
        HttpResponse response;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                result = File.createTempFile("idx_", ".tmp");
                IOUtils.copy(entity.getContent(), new FileOutputStream(result));
            } else {
                log.error("Error: Imposible conectar con URL: " + url + ", Status: " + response.getStatusLine());
                throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
            }
            consume(entity); // Ensure it is fully consumed
        } catch (IOException e) {
            log.error("Error: fetchContentFromUrl " + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }

        httpclient.getConnectionManager().shutdown();
        return result;
    }

    private static void consume(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            final InputStream instream = entity.getContent();
            if (instream != null) {
                instream.close();
            }
        }
    }
}
