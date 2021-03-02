package by.bsuir.project.action;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private final String url;
    private final String name;

    public MenuItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
