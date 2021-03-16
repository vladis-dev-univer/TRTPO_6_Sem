package by.bsuir.project.action.menu;

import by.bsuir.project.action.Action;
import by.bsuir.project.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;

public class HomeAction extends Action {

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}

