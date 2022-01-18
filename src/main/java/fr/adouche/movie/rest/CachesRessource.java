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

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import fr.adouche.movie.cache.utils.CacheUtils;

@Path("caches")
@Produces({"application/json"})
@Stateless
public class CachesRessource {

    @Inject
    CacheUtils cacheUtils;

    @GET
    @Path("{cacheName}")
    public Response find(@PathParam("cacheName") final String cacheName) {
        JsonObjectBuilder response = jsonCache(cacheName);

        return Response.ok(response.build()).build();

    }

    @GET
    public Response allCaches() {
        String cachesName = joinningcachesName();
        return Response.ok(cachesName).build();
    }

    private String joinningcachesName() {
        return cacheUtils.cacheNames().stream().collect(Collectors.joining(", ", "{", "}"));
    }

    @GET
    @Path("clear")
    public String clearCache() {
        cacheUtils.clearCaches();
        return "clear caches " + joinningcachesName() + " : OK";
    }

    @GET
    @Path("clear/{cacheName}")
    public String clearCache(@PathParam("cacheName") final String cacheName) {
        cacheUtils.clearCache(cacheName);
        return "clear cache " + cacheName + " : OK";
    }

    private JsonObjectBuilder jsonCache(final String cacheName) {
        JsonObjectBuilder response = Json.createObjectBuilder();
        cacheUtils.getCache(cacheName)
                .ifPresent(cache -> response.add("name", cache.getName()));
        return response;
    }
}
