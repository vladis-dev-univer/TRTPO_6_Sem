package by.bsuir.project.action;


import by.bsuir.project.service.factory.ServiceFactory;

public class ActionManagerFactory {
    private ActionManagerFactory() {
        throw new IllegalStateException("ActionManagerFactory class");
    }

    public static ActionManager getManager(ServiceFactory factory) {
        return new ActionManagerImpl(factory);
    }
}
