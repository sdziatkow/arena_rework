package control;

import java.util.Objects;

public abstract class ArenaObject implements Comparable<ArenaObject> {

    /** The ID that is unique to this object's instance. */
    private Integer id;

    /** The ID that is unique to this object's base data (the data it is generated from). */
    private String objID;

    public ArenaObject() {
        id = null;
    }

    public void setID(Integer id) {this.id = id;}
    public void setObjID(String id) {this.objID = id;}

    public Integer getID() {return id;}
    public String getObjID() {return objID;}

    /** @return True if the given ArenaObject was generated from the same object data. */
    public boolean isSameAs(ArenaObject a) {return (compareTo(a) == 0);}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArenaObject a)) return false;
        return (this.id.equals(a.id));
    }

    @Override
    public int hashCode() { return Objects.hash(String.valueOf(id)); }

    @Override
    public int compareTo(ArenaObject a) {return objID.compareTo(a.objID);}
}
