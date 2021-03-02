package by.bsuir.project.service.impl;

import by.bsuir.project.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceInvocationHandlerImpl implements InvocationHandler {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final ServiceImpl service;

    public ServiceInvocationHandlerImpl(ServiceImpl service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Object result = method.invoke(service, args);
            service.transaction.commit();
            return result;
        } catch(PersistentException e) {
            rollback();
            throw e;
        } catch(InvocationTargetException e) {
            rollback();
            throw e.getCause();
        }
    }

    private void rollback() {
        try {
            service.transaction.rollback();
        } catch(PersistentException e) {
            logger.warn("It is impossible to rollback transaction", e);
        }
    }
}
