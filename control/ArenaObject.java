package control;

import java.util.Objects;

public abstract class ArenaObject {
    private Integer id;

    public ArenaObject() {
        id = null;
    }

    public void setID(Integer id) {this.id = id;}

    public Integer getID() {return id;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArenaObject a)) return false;
        return (this.id.equals(a.id));
    }

    @Override
    public int hashCode() { return Objects.hash(String.valueOf(id)); }
}
