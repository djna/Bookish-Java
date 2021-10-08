package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Owner {
    @ColumnName("oid")
    private int oid;
    @ColumnName("oname")
    private String oname;

    private List<Cat> cats;

    public Owner(){
       this(0,"");
    }

    @JdbiConstructor
    public Owner (@ColumnName("oid") int oid, @ColumnName("oname")String oname){
        System.out.println("build: " + oname);
        this.oid = oid;
        this.oname = oname;
        cats = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "o_id=" + oid +
                ", o_name='" + oname + '\'' +
                ", cats=" + cats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return oid == owner.oid && oname.equals(owner.oname) && cats.equals(owner.cats);
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid, oname, cats);
    }

    public void addCat(Cat cat){
        // todo - don't add same cat twice
        cats.add(cat);
    }

    public List<Cat> getCats() {
        return cats;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
}
