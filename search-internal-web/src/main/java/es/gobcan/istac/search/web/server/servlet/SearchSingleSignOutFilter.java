package es.gobcan.istac.search.web.server.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.web.common.server.session.SingleSignOutFilter;

public class SearchSingleSignOutFilter extends SingleSignOutFilter {

    private final String PATHTOBEIGNORED = ".idx";

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        String path = request.getRequestURI();

        // Ignore SSO out filter
        if (!StringUtils.isEmpty(PATHTOBEIGNORED) && path.endsWith(PATHTOBEIGNORED)) {
            filterChain.doFilter(servletRequest, servletResponse); // Just continue chain.
            return; // Do not continue up filter chain
        }

        super.doFilter(servletRequest, servletResponse, filterChain);
    }

}
