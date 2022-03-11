---
#Movie-jcache
<br/>

---

original : https://github.com/tomitribe/moviefun-jcache

You can use ***cache.http*** and ***movie.http*** files to try rest endpoint in intellij ultimate idea

## 1.Movie rest endpoint

### Movie structure
```json
{
"id" : 1,
"director" : "director",
"title" : "title",
"productionYear" : 2022,
"genre" : "genre",
"rating" : 1
}
```

### All movies
GET http://localhost:9097/moviejcache/rest/movies

### Find a movie by id
GET http://localhost:9097/moviejcache/rest/movies/{{movie_id}}

### Count movies
GET http://localhost:9097/moviejcache/rest/movies/count

### Count movies with search term
GET http://localhost:9097/moviejcache/rest/movies/count?field={{movie_field}}&searchTerm={{movie_searchTerm}}

### All genres
GET http://localhost:9097/moviejcache/rest/movies/genres

### Post a new movie
POST http://localhost:9097/moviejcache/rest/movies

Content-Type: application/json
```json
{
"director" : "director",
"title" : "title",
"productionYear" : "2022",
"genre" : "genre",
"rating" : "5"
}
```

### Edit an existing movie
PUT http://localhost:9097/moviejcache/rest/movies

Content-Type: application/json
```json
{
"id" : 1,
"director" : "director",
"title" : "title",
"productionYear" : 2022,
"genre" : "genre",
"rating" : 1
}
```

### Delete an existing movie
DELETE http://localhost:9097/moviejcache/rest/movies/{{movie_id}}

<br/>

---
## 2.Cache rest endpoint

### All caches
GET http://localhost:9097/moviejcache/rest/caches

### Find a cache by name
GET http://localhost:9097/moviejcache/rest/caches/{{cacheName}}

### Clear all caches
DELETE http://localhost:9097/moviejcache/rest/caches/clear

### Clear a specific cache
DELETE http://localhost:9097/moviejcache/rest/caches/clear/{{cacheName}}

