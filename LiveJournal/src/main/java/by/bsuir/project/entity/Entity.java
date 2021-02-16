package by.bsuir.project.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if(object != null) {
            if(object != this) {
                if(object.getClass() == getClass() && id != null) {
                    return id.equals(((Entity)object).id);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

