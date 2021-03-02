package by.bsuir.project.action;

import by.bsuir.project.entity.Role;

import java.util.Arrays;

public abstract class AuthorizedUserAction extends Action {
    protected AuthorizedUserAction(){
        getAllowRoles().addAll(Arrays.asList(Role.values()));
    }
}
