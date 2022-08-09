package com.rest.project.repositories;

import com.rest.project.models.Person;
import io.agroal.api.AgroalDataSource;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Future;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PersonRepository {

    @Inject
    protected AgroalDataSource dataSource;


    public Uni<List<Person>> getByName(String name) {
        final Future<List<Person>> future = Future.future(promise -> {
            try (final Connection connection = dataSource.getConnection()) {
                try (final PreparedStatement ps = connection.prepareStatement("select * from person where name = ?")) {
                    ps.setString(1, name);

                    try (final ResultSet rs = ps.executeQuery()) {
                        List<Person> personList = new ArrayList<>();

                        while (rs.next()) {
                            final Person person = new Person();
                            person.setName(rs.getString("NAME"));
                            person.setBooks(rs.getString("BOOK"));
                            personList.add(person);
                        }

                        promise.complete(personList);
                    }
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        return Uni.createFrom().future(future.toCompletionStage().toCompletableFuture());
    }



    public Uni<Response> save(Person person) {
        final Future<Response> future = Future.future(promise -> {
            try (final Connection connection = dataSource.getConnection()) {
                try (final PreparedStatement query = connection.prepareStatement("insert into person values(?, ?)")) {
                    query.setString(1, person.getName());
                    query.setString(2, person.getBooks());
                    query.executeUpdate();
                    promise.complete(Response.ok().entity(person).build());
                }

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
        return Uni.createFrom().future(future.toCompletionStage().toCompletableFuture());
    }


    public Uni<Response> deleteByName(String name) {

        final Future<Response> future = Future.future(promise -> {
            try (final Connection connection = dataSource.getConnection()) {
                try (final PreparedStatement query = connection.prepareStatement("delete from person where name = ?")) {
                    query.setString(1, name);
                    query.executeUpdate();
                    promise.complete(Response.ok().build());
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
        return Uni.createFrom().future(future.toCompletionStage().toCompletableFuture());
    }



    public Uni<Response> deleteByNameAndBook(String name, String book) {
        final Future<Response> future = Future.future(promise -> {
            try (final Connection connection = dataSource.getConnection()) {
                try (final PreparedStatement query = connection.prepareStatement("delete from person where name = ? and book = ?")) {
                    query.setString(1, name);
                    query.setString(2, book);
                    query.executeUpdate();
                    promise.complete(Response.ok().build());
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
        return Uni.createFrom().future(future.toCompletionStage().toCompletableFuture());
    }


}



