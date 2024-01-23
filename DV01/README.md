# DV01 backend challenge

## Running the app

Run this using [sbt](http://www.scala-sbt.org/).

```bash
sbt -mem 4096 run
```

And then go to <http://localhost:9000> to see the running web application.

## Controllers

- `HomeController.scala`:

  Shows that the application is started.

- `DataController.scala`:

  Has API endpoints consisting of GetById, GetByState, and GetByStatePaginated

  Test URLs are as follows:
  > http://localhost:9000/getById/126378396
  > http://localhost:9000/getById/badId

  > http://localhost:9000/getByState/CA
  > http://localhost:9000/getByState/badState

  > http://localhost:9000/getByStatePaginated/CA?page=1&pageSize=5
  > http://localhost:9000/getByStatePaginated/badState