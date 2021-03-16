package by.bsuir.project.service.factory;

import by.bsuir.project.dao.transaction.Transaction;
import by.bsuir.project.dao.transaction.TransactionFactory;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.*;
import by.bsuir.project.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final Map<Class<? extends Service>, Class<? extends ServiceImpl>> services = new ConcurrentHashMap<>();

    static {
        services.put(UserService.class, UserServiceImpl.class);
        services.put(UserInfoService.class, UserInfoServiceImpl.class);
        services.put(PublicationService.class, PublicationServiceImpl.class);
    }

    private final TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Type extends Service> Type getService(Class<Type> key) throws PersistentException {
        Class<? extends ServiceImpl> value = services.get(key);
        if (value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                Transaction transaction = factory.createTransaction();
                ServiceImpl service = value.newInstance();
                service.setTransaction(transaction);
                InvocationHandler handler = new ServiceInvocationHandlerImpl(service);
                return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to instance service class", e);
                throw new PersistentException(e);
            }
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}
