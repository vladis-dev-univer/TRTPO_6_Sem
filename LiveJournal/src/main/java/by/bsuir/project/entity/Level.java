package by.bsuir.project.entity;

public enum Level {
    READER("Reader"),
    BEGINNER("Beginner"),
    AMATEUR("Amateur"),
    PROFESSIONAL("Professional");

    private final String name;

    Level(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getLevelName(){
        return ordinal();
    }

    public static Level getById(Integer id){
        return Level.values()[id];
    }

    public static Level fromString(String level) {
        for (Level b : Level.values()) {
            if (b.name.equalsIgnoreCase(level)) {
                return b;
            }
        }
        return null;
    }
}
