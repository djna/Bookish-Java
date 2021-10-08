package org.softwire.training.bookish.models.database;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface OwnerDao {
/*    @RegisterBeanMapper(value = Owner.class, prefix = "o")
    @RegisterBeanMapper(value = Cat.class, prefix = "c")
    @SqlQuery("SELECT o.oid o_id, o.oname o_name, c.id id, c.name name FROM " +
            "OWNER o LEFT JOIN CAT c ON o.oid = c.owner_id ")
    @UseRowReducer(AccountUserReducer.class)
    List<Owner> getOwners();

    @SqlQuery("SELECT o.id o_id, o.name o_name, c.id id, c.name name FROM " +
            "OWNER o LEFT JOIN CAT c ON o.oid = c.owner_id WHERE o.oid = :ownerid") */


    @SqlQuery("SELECT o.id o_id, o.name o_name FROM " +
            "OWNER o WHERE o.oid = :ownerid")
    @Mapper(OwnerMapper.class)
    public Owner getOwner( @Bind("ownerid") int id );
}
