package by.bsuir.project.validator;


import by.bsuir.project.entity.Entity;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {

    /**
     * private default constructor (help SonarLint)
     */
    private ValidatorFactory() {
        throw new IllegalStateException("ValidatorFactory class");
    }

    private static final Map<Class<? extends Entity>, Class<? extends Validator<?>>> validators = new HashMap<>();

    static {
        validators.put(UserInfo.class, UserInfoValidator.class);
        validators.put(Publication.class, PublicationValidator.class);
    }

    @SuppressWarnings("unchecked")
    public static <Type extends Entity> Validator<Type> createValidator(Class<Type> entity) {
        try {
            return (Validator<Type>)validators.get(entity).newInstance();
        } catch(InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
