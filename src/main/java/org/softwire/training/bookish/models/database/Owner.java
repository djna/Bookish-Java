package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Owner {

    private int id;

    private String name;

    private List<Cat> cats;

    public Owner(){
       this(0,"");
    }

    @JdbiConstructor
    public Owner (@ColumnName("oid") int oid, @ColumnName("oname")String oname){
        this.id = oid;
        this.name = oname;
        cats = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "o_id=" + id +
                ", o_name='" + name + '\'' +
                ", cats=" + cats +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return id == owner.id && name.equals(owner.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    public void addCat(Cat cat){
        // todo - don't add same cat twice
        cats.add(cat);
    }

    public List<Cat> getCats() {
        return cats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
