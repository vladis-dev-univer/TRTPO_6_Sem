package by.bsuir.project.action.user;


import by.bsuir.project.action.Action;
import by.bsuir.project.entity.Role;

public abstract class UserAction extends Action {
    protected UserAction() {
        getAllowRoles().add(Role.USER);
    }
}
