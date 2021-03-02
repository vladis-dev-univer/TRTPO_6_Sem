package by.bsuir.project.controller;

import by.bsuir.project.action.Action;
import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Security filter used to provide security in the event of
 * unexpected interference in other roles
 *
 * @author Laikov Vlad
 * @version 1.0
 */
public class SecurityFilter implements Filter {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void destroy() {
        //Default empty method
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Action action = (Action) httpRequest.getAttribute("action");
            Set<Role> allowRoles = action.getAllowRoles();
            String userName = "unauthorized user";
            HttpSession session = httpRequest.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("authorizedUser");
                action.setAuthorizedUser(user);
                securityFilterMessageAttribute(httpRequest, session);
            }
            boolean canExecute = allowRoles.equals(Collections.emptySet());
            if (user != null) {
                userName = "\"" + user.getLogin() + "\" user";
                canExecute = canExecute || allowRoles.contains(user.getRole());
            }
            if (canExecute) {
                chain.doFilter(request, response);
            } else {
                String strInfo = "Trying of " + userName + " access to forbidden resource " + action.getName();
                logger.info(strInfo);
//                if (session != null && action.getClass() != MainAction.class) {
//                    session.setAttribute("SecurityFilterMessage", "accessIsDenied");
//                }
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    private void securityFilterMessageAttribute(HttpServletRequest httpRequest, HttpSession session){
        String errorMessage = (String) session.getAttribute("SecurityFilterMessage");
        if (errorMessage != null) {
            httpRequest.setAttribute("securityMessage", errorMessage);
            session.removeAttribute("SecurityFilterMessage");
        }
    }

    @Override
    public void init(FilterConfig config) {
        //Default empty method
    }

}
