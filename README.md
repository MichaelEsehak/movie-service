
## Movie Service

A simple movie service that is built around a movie entity with exposed CURD rest endpoints.

### Built With

* Spring boot
* Spring reactor
* Mongo Reactive Repositories
* Spock Testing framework


### Getting Started
The service is a stand alone no external dependencies except MongoDb, it only requires a connection to MongoDb, the connection configuration could be updated in the application.properties file.

### Available end-points

#### Create new movie
POST: http://{server-ip}/api/v1.0/movies/create

The endpoint will return an error if a movie with same ID exists in the db

#### Update movie
POST: http://{server-ip}/api/v1.0/movies/update

The endpoint will return an error if the movie is not in the db, also the update will consider the concurrent modification, so the requester should load the entity and preserve the version while make a new update request

#### Load movie by id
GET:http://{server-ip}/api/v1.0/movies/id/{MovieID}

Will return not found error if the movie is not in the DB

#### Delete movie by id
DELETE: http://{server-ip}/api/v1.0/movies/id/{MovieID}

Will always return N0_CONTENT unless there is an error occered

#### Search movies by name, releaseYear and duration with pagination
GET: http://{server-ip}/api/v1.0/movies/search?releaseYear=2000&name=movie&duration=1234

#### Load all movies with pagination 
GET:http://{server-ip}/api/v1.0/movies/?offset=0&limit=2

#### Example Body Json
```
{
  "id": "3",
  "name": "movie",
  "releaseYear": 2000,
  "duration": 1234,
  "version": 1,
  "actors": [
    {
      "id": "1",
      "name": "Mike"
    },
    {
      "id": 2,
      "name": "test"
    }
  ],
  "thumbnailUrl": "https://abc.com",
  "language": "English",
  "country": "Germany",
  "rate": "VERY_GOOD",
  "genres": [
    "COMEDY",
    "ACTION"
  ]
}
```





