package es.gobcan.istac.search.web.server.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
import org.siemac.metamac.web.common.server.servlet.FileDownloadServletBase;

import es.gobcan.istac.search.web.shared.utils.SearchSharedTokens;

public class FileDownloadServlet extends FileDownloadServletBase {

    private static final long serialVersionUID = 6267996624034326676L;

    @Override
    protected File getFileToDownload(HttpServletRequest request) throws Exception {
        if (!StringUtils.isEmpty(request.getParameter(SearchSharedTokens.PARAM_FILE_NAME))) {
            return getFileFromTempDir(request.getParameter(SearchSharedTokens.PARAM_FILE_NAME));
        } else {
            throw new InvalidArgumentException("You must specify some action");
        }

    }

}
