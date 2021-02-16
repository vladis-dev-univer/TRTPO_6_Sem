package by.bsuir.project.entity;

public enum Role {
    ADMINISTRATOR("Administrator"),  // 0 - admin
    USER("User");                    // 1 - user

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getRoleName(){
        return ordinal();
    }

    public static Role getById(Integer id){
        return Role.values()[id];
    }

    public static Role fromString(String role) {
        for (Role b : Role.values()) {
            if (b.name.equalsIgnoreCase(role)) {
                return b;
            }
        }
        return null;
    }

}
