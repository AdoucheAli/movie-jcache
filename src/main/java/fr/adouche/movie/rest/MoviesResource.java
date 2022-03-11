/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fr.adouche.movie.rest;

import fr.adouche.movie.entity.Movie;
import fr.adouche.movie.service.MovieService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Path("movies")
@Produces({"application/json"})
@Stateless
public class MoviesResource {

    @Inject
    MovieService service;

    Random random = new Random();

    @GET
    @Path("{id}")
    public Movie find(@PathParam("id") final Long id) {
        return service.find(id);
    }

    @GET
    public List<Movie> getMovies(@Context final UriInfo context) {
        MultivaluedMap<String, String> queryParameters = context.getQueryParameters();
        Integer first = null;
        Integer max = null;
        String field = queryParameters.getFirst("field");
        String searchTerm = queryParameters.getFirst("searchTerm");

        String tmp = queryParameters.getFirst("first");
        if (tmp != null) {
            first = Integer.valueOf(tmp);
        }

        tmp = queryParameters.getFirst("max");
        if (tmp != null) {
            max = Integer.valueOf(tmp);
        }

        return service.getMovies(first, max, field, searchTerm);
    }

    @GET
    @Path("genres")
    public Collection<String> getGenres() {
        return service.getGenres();
    }

    @POST
    @Consumes("application/json")
    public Movie addMovie(final Movie movie) {
        service.addMovie(movie);
        return movie;
    }

    //don't work with wildfly
/*    @PATCH
    @Consumes("application/json")
    public Movie patchMovie(final Movie movie) {
        System.err.println(movie);
        service.editMovie(movie);
        return movie;
    }*/

    @PUT
    @Consumes("application/json")
    public Movie editMovie(final Movie movie) {
        System.err.println(movie);
        service.editMovie(movie);
        return movie;
    }

    @DELETE
    @Path("{id}")
    public void deleteMovie(@PathParam("id") final long id) {
        service.deleteMovie(id);
    }

    @GET
    @Path("count")
    public int count(@QueryParam("field") final String field, @QueryParam("searchTerm") final String searchTerm) {
        return service.count(field, searchTerm);
    }

    @GET
    @Path("count2")
    public int count(@Context final UriInfo context) {
        MultivaluedMap<String, String> queryParameters = context.getQueryParameters();
        String field = queryParameters.getFirst("field");
        String searchTerm = queryParameters.getFirst("searchTerm");
        return service.count(field, searchTerm);
    }

    @GET
    @Path("load")
    public Movie loadNew() {
        int thisYear = LocalDate.now().getYear();
        Movie newMovie = new Movie("Wedding Crashers", "David Dobkin", "Comedy", randomInRange(1, 10),
                randomInRange(thisYear - 20, thisYear));
        service.addMovie(newMovie);

        return newMovie;
    }

    public int randomInRange(int start, int end) {
        return random.nextInt(end - start) + start;
    }
}
