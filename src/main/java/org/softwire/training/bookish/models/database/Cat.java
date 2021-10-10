package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.util.ArrayList;
import java.util.Objects;

public class Cat {
    int id;
    String name;
    int age;
    int owner_id;

    public Cat (){
        this(0,"",0,0);
    }

    @JdbiConstructor
    public Cat ( int id,
                String name,
                int age,
                int owner_id){
        this.id = id;
        this.name = name;
        this.age = age;
        this.owner_id = owner_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return id == cat.id && owner_id == cat.owner_id && name.equals(cat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner_id);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", owner_id=" + owner_id +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
}
