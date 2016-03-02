package es.gobcan.istac.idxmanager.web.buscador.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XSSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        aplicaCabeceraXss(response);
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }

    private void aplicaCabeceraXss(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        // Protection against Type 1 Reflected XSS attacks
        res.addHeader("X-XSS-Protection", "1; mode=block");
        // Disabling browsers to perform risky mime sniffing
        res.addHeader("X-Content-Type-Options", "nosniff");
        // Clickjack attack
        res.addHeader("X-FRAME-OPTIONS", "DENY");

        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

}