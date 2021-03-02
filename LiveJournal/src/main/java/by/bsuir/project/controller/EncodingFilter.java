package by.bsuir.project.controller;

import javax.servlet.*;
import java.io.IOException;

/**
 * Encoding filter used to encoding (UTF-8) request and response
 *
 * @author Laikov Vlad
 * @version 1.0
 */
public class EncodingFilter implements Filter {

    @Override
    public void destroy() {
        //Default empty method
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) {
        //Default empty method
    }

}
