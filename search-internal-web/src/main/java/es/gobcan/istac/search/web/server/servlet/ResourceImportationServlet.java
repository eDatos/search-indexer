package es.gobcan.istac.search.web.server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringEscapeUtils;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.io.FileUtils;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.siemac.metamac.web.common.shared.exception.MetamacWebExceptionItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.facade.serviceapi.RecommendedLinksServiceFacade;
import es.gobcan.istac.search.core.message.ServiceMessageType;
import es.gobcan.istac.search.core.utils.SearchLocaleUtil;
import es.gobcan.istac.search.web.shared.ImportableResourceTypeEnum;
import es.gobcan.istac.search.web.shared.utils.SearchSharedTokens;

@Singleton
@SuppressWarnings("serial")
public class ResourceImportationServlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(ResourceImportationServlet.class);

    private File                tmpDir;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("FileUpload Servlet");
        tmpDir = new File(((File) getServletContext().getAttribute("javax.servlet.context.tempdir")).toString());
        logger.info("tmpDir: " + tmpDir.toString());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check that we have a file upload request
        if (ServletFileUpload.isMultipartContent(request)) {
            processFiles(request, response);
        } else {
            processQuery(request, response);
        }
    }

    @SuppressWarnings("rawtypes")
    private void processFiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HashMap<String, String> args = new HashMap<String, String>();

        String fileName = new String();
        InputStream inputStream = null;

        try {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Get the temporary directory (this is where files that exceed the threshold will be stored)
            factory.setRepository(tmpDir);

            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List items = upload.parseRequest(request);

            // Process the uploaded items
            Iterator itr = items.iterator();

            while (itr.hasNext()) {
                DiskFileItem item = (DiskFileItem) itr.next();
                if (item.isFormField()) {
                    args.put(item.getFieldName(), item.getString());
                } else {
                    fileName = item.getName();
                    inputStream = item.getInputStream();
                }
            }

            if (ImportableResourceTypeEnum.RECOMMENDED_LINKS.name().equals(args.get(SearchSharedTokens.UPLOAD_PARAM_FILE_TYPE))) {

                if (args.containsKey(SearchSharedTokens.UPLOAD_PARAM_MODE)) {
                    if (args.get(SearchSharedTokens.UPLOAD_PARAM_MODE).equals(SearchSharedTokens.UPLOAD_PARAM_MODE_ADDITIVE)) {
                        importByAddingRecommendedLinks(inputStream, fileName);
                    } else if (args.get(SearchSharedTokens.UPLOAD_PARAM_MODE).equals(SearchSharedTokens.UPLOAD_PARAM_MODE_SUSTITUTIVE)) {
                        importByReplacingRecommendedLinks(inputStream, fileName);
                    } else {
                        throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.IMPORTATION_MODE).build();
                    }
                } else {
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.IMPORTATION_MODE).build();
                }
            }

            sendSuccessImportationResponse(response, getSuccessMessage());

        } catch (Exception e) {
            String errorMessage = null;
            if (e instanceof MetamacException) {
                errorMessage = WebExceptionUtils.serializeToJson((MetamacException) e);
            } else if (e instanceof MetamacWebException) {
                errorMessage = getMessageFromMetamacWebException((MetamacWebException) e);
                errorMessage = StringEscapeUtils.escapeJavaScript(errorMessage);
            } else {
                errorMessage = e.getMessage();
                errorMessage = StringEscapeUtils.escapeJavaScript(errorMessage);
            }
            logger.error("Error importing file = " + fileName + ". " + e.getMessage(), e);
            sendFailedImportationResponse(response, errorMessage);
        }
    }

    private String getSuccessMessage() throws MetamacException {
        return SearchLocaleUtil.getTranslatedMessage(ServiceMessageType.IMPORTATION_TSV_SUCCESFUL);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    //
    // IMPORTATION METHODS
    //

    private void importByAddingRecommendedLinks(InputStream inputStream, String fileName) throws MetamacException {
        File file = createTempFile(inputStream, fileName);
        getRecommendedLinksServiceFacade().importByAddingRecommendedLinks(ServiceContextHolder.getCurrentServiceContext(), file, fileName);
    }

    private void importByReplacingRecommendedLinks(InputStream inputStream, String fileName) throws MetamacException {
        File file = createTempFile(inputStream, fileName);
        getRecommendedLinksServiceFacade().importByReplacingRecommendedLinks(ServiceContextHolder.getCurrentServiceContext(), file, fileName);
    }

    //
    // UTILITY METHODS
    //

    private File createTempFile(InputStream stream, String fileName) throws MetamacException {
        try {
            File file = FileUtils.createTempFile(stream, fileName);
            file.deleteOnExit();
            return file;
        } catch (Exception e) {
            logger.error("Error creating temporal file", e);
            //@formatter:off
            throw MetamacExceptionBuilder.builder()
                .withExceptionItems(ServiceExceptionType.IMPORTATION_TSV_ERROR)
                .withMessageParameters(e.getMessage())
                .withCause(e)
                .withLoggedLevel(ExceptionLevelEnum.ERROR)
                .build();
            //@formatter:on
        }
    }

    private void sendSuccessImportationResponse(HttpServletResponse response, String message) throws IOException {
        String action = "if (parent.uploadComplete) parent.uploadComplete('" + message + "');";
        sendResponse(response, action);
    }

    private void sendFailedImportationResponse(HttpServletResponse response, String errorMessage) throws IOException {
        String action = "if (parent.uploadFailed) parent.uploadFailed('" + errorMessage + "');";
        sendResponse(response, action);
    }

    private void sendResponse(HttpServletResponse response, String action) throws IOException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<script type=\"text/javascript\">");
        out.println(action);
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    private String getMessageFromMetamacWebException(MetamacWebException e) {
        List<MetamacWebExceptionItem> items = e.getWebExceptionItems();
        if (items != null && !items.isEmpty()) {
            return items.get(0).getMessage(); // only return the first message error
        }
        return null;
    }

    private RecommendedLinksServiceFacade getRecommendedLinksServiceFacade() {
        return (RecommendedLinksServiceFacade) ApplicationContextProvider.getApplicationContext().getBean(RecommendedLinksServiceFacade.BEAN_ID);
    }

}
