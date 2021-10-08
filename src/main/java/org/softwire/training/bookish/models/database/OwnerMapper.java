package org.softwire.training.bookish.models.database;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerMapper implements ResultSetMapper<Owner> {

    @Override
    public Owner map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Owner(rs.getInt("oid"), rs.getString("oname"));
    }
}
