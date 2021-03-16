package by.bsuir.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Locale filter used to language localization
 *
 * @author Laikov Vlad
 * @version 1.0
 */
public class LocaleFilter implements Filter {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void destroy() {
        //Default empty method
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String locale = null;
        if (session.getAttribute("locale") == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("locale")) {
                    locale = cookie.getValue();
                    logger.debug("received locale from cookies : {}", locale);
                    break;
                }
            }
            session.setAttribute("locale", locale);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) {
        //Default empty method
    }

}
