package by.bsuir.project.service.factory;


import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.Service;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key) throws PersistentException;

    void close();
}
