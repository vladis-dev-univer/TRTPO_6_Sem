package by.bsuir.project.action.admin;


import by.bsuir.project.action.Action;
import by.bsuir.project.entity.Role;

public abstract class AdminAction extends Action {
    protected AdminAction() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}
