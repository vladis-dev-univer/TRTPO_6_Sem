package by.bsuir.project.action;

import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Action {
    private final Set<Role> allowRoles = new HashSet<>();
    private String name;
    private User authorizedUser;
    protected ServiceFactory factory;

    public Set<Role> getAllowRoles() {
        return allowRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFactory(ServiceFactory factory) {
        this.factory = factory;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
    }


    public abstract Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException;
//TODO
//    protected String getStringFromResourceBundle(HttpSession session, String nameLoc) {
//        Object localParameter = session.getAttribute("locale");
//       Locale currentLang;
//        if (localParameter != null) {
//            String string = String.valueOf(localParameter);
//            String[] langParameters = string.split("_");
//            currentLang = new Locale(langParameters[0], langParameters[1]);
//        } else {
//            currentLang = new Locale("en", "US");
//        }
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("property.locale", currentLang);
//        return resourceBundle.getString(nameLoc);
//    }

    public static class Forward {
        private final String forth;
        private final boolean redirect;
        private final Map<String, Object> attributes = new HashMap<>();

        public Forward(String forth, boolean redirect) {
            this.forth = forth;
            this.redirect = redirect;
        }

        public Forward(String forth) {
            this(forth, true);
        }

        public String getForth() {
            return forth;
        }

//        public void setForth(String forth) {
//            this.forth = forth;
//        }

        public boolean isRedirect() {
            return redirect;
        }

//        public void setRedirect(boolean redirect) {
//            this.redirect = redirect;
//        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }
    }
}
