package org.softwire.training.bookish;

import com.google.common.collect.Multimap;
import com.mysql.cj.result.Row;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.generic.GenericType;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.guava.GuavaPlugin;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.softwire.training.bookish.models.database.*;


import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws SQLException {
        String hostname = "localhost";
        String database = "cat_shelter";
        String user = "booker";
        String password = "booker";
        String connectionString = "jdbc:mysql://" + hostname + "/" + database;

        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);
        connectionProps.setProperty("useSSL", "false");
        connectionProps.setProperty("useJDBCCompliantTimezoneShift", "true");
        connectionProps.setProperty("useLegacyDatetimeCode", "false");
        connectionProps.setProperty("serverTimezone", "GMT");

        Main mainInstance = new Main();

        mainInstance.jdbcMethod(connectionString, connectionProps);
        mainInstance.jdbiMethod(connectionString, connectionProps);
    }

    private  void jdbcMethod(String connectionString, Properties properties) throws SQLException {
        System.out.println("JDBC method...");

        // TODO: print out the details of all the books (using JDBC)
        // See this page for details: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html

        Connection connection
                = DriverManager.getConnection(connectionString, properties);

        String query = "select * from cats";


        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while ( resultSet.next() ) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.printf("%d %s%n", id, name);
                // … etc, for every column we want to use
            }

        } // catch errors


    }

    private  void jdbiMethod(String url, Properties poperties) {
        System.out.println("\nJDBI method...");

        // TODO: print out the details of all the books (using JDBI)
        // See this page for details: http://jdbi.org
        // Use the "Book" class that we've created for you (in the models.database folder)

        Jdbi jdbi = Jdbi.create(url, poperties);
        jdbi.installPlugin(new GuavaPlugin());

        Object result = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM cats")
                        .mapToBean(Cat.class)
                        .list()
        );

        System.out.println("All cats:" + result);

        Object ownerList = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM owners")
                        .mapToBean(Owner.class)
                        .list()
        );

        System.out.println("All owners:" + ownerList);

        String ownerQuery = "SELECT o.id oid, o.name oname, " +
                "c.id, c.name, c.age, c.owner_id " +
                "FROM cats c, owners o where c.owner_id = o.id";

        getGenericOwnerMap(jdbi, ownerQuery);

        getOwnerCatMap(jdbi, ownerQuery);

    }

    private void getGenericOwnerMap(Jdbi jdbi, String ownerQuery) {
        /** example using Guava, but result is not easy to use
        Multimap<Owner, Cat> owners = jdbi.withHandle(handle -> {
            Multimap<Owner, Cat> map = handle.createQuery(ownerQuery)
                    .registerRowMapper(BeanMapper.factory(Owner.class, "o"))
                    .registerRowMapper(BeanMapper.factory(Cat.class))
                    .collectInto(new GenericType<Multimap<Owner, Cat>>() {});
            return map;
        });
         System.out.println("collected:" + owners);
         */

        Map<Integer, Owner> knownOwners = new HashMap<>();
        Object ownerMap = jdbi.withHandle(handle -> {
            handle.createQuery(ownerQuery)
                    .map( (rs, ctx) -> {
                        RowMapper<Owner> ownerMapper = BeanMapper.of(Owner.class, "o");
                        Owner ownerFromRow = ownerMapper.map(rs,ctx);

                        RowMapper<Cat> catMapper = BeanMapper.of(Cat.class);
                        Cat cat = catMapper.map(rs,ctx);

                        Owner currentOwner = knownOwners.computeIfAbsent(
                                ownerFromRow.getId(),
                                ( id -> ownerFromRow )
                        );

                        currentOwner.addCat(cat);

                        return currentOwner;

                     } );

            return knownOwners.entrySet().stream().collect(Collectors.toList());
        });
        System.out.println("listed :" + ownerMap);
    }

    private void getOwnerCatMap(Jdbi jdbi, String ownerQuery) {
        List<Owner> ownerList = jdbi.withHandle(handle -> {

            List<Owner> owners = handle.createQuery(ownerQuery)
                    .registerRowMapper(BeanMapper.factory(Owner.class, "o"))
                    .registerRowMapper(BeanMapper.factory(Cat.class))
                    .reduceRows(new LinkedHashMap<Integer, Owner>(),
                            (map, rowView) -> {
                                Owner ownerFromRow = rowView.getRow(Owner.class);

                                Owner ownerInResultMap = map.computeIfAbsent(
                                        ownerFromRow.getId(),
                                        id -> ownerFromRow);

                                ownerInResultMap.addCat(rowView.getRow(Cat.class));

                                return map;
                            })
                    .values()
                    .stream()
                    .collect(Collectors.toList());


            return owners;

        });
        System.out.println("Owners " + ownerList);
    }



/*
"o.oid oid, o.oname oname, " +
                "c.id id, c.name name, c.age age, c.owner_id owner_id " +

--add-opens java.base/java.lang=ALL-UNNAMED
     DBI dbi = new DBI(url, poperties);

        OwnerDao dao = dbi.onDemand(OwnerDao.class);
        Owner owner = dao.getOwner(1);

        System.out.println("Owner " + owner);
 */
}

/*
    jdbi.withHandle(handle -> {

            String joinQuery = "SELECT * FROM cats c, owners o where c.owner_id = o.oid";
            List<Owner> contacts = handle.createQuery(joinQuery)
        .registerRowMapper(BeanMapper.factory(Owner.class, "o"))
        .registerRowMapper(BeanMapper.factory(Cat.class, "c"))
        .reduceRows(new LinkedHashMap<Integer, Owner>(),
        (map, rowView) -> {
        Owner owner = map.computeIfAbsent(
        rowView.getColumn("o.oid", Integer.class),
        id -> rowView.getRow(Owner.class));

        if (rowView.getColumn("id", Integer.class) != null) {
        owner.addCat(rowView.getRow(Cat.class));
        }

        return map;
        })
        .values()
        .stream()
        .collect(Collectors.toList());

        System.out.println("Owner " + result);
        return result;
        });
*/