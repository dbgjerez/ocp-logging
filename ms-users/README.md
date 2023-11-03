# s-users-fr

## Frameworks and librearies

* Quarkus
* MariaDB

## Lifecycle

### Local development

The application needs a ```MariaDB``` database running to run correctly. So, we'll start a container with MariaDB using ```podman```.

```bash
podman run \
    -d  \
    --name mariadb-users  \
    --env MARIADB_USER=user1234 \
    --env MARIADB_PASSWORD=user1234 \
    --env MARIADB_ROOT_PASSWORD=user1234 \
    --env MARIADB_DATABASE=users \
    -p 3306:3306 \
    mariadb:latest
```

Now, we can start the application with the ```Maven``` command:

```bash
mvn quarkus:dev
```

### Production build

We will compile a native image, using ```Maven```:

```bash
mvn package quarkus:image-build
```

After some minutes, we can build the final image:

```bash
podman image tag \                                  
    localhost/b0rr3g0/ms-users:0.1 \
    quay.io/dborrego/logging-ms-users:0.1.1
```

### API

#### Create an user

```bash
curl -X POST \
    --data '{ 
                "firstName":"David", 
                "lastName":"Borrego", 
                "dni":"00000000C", 
                "phone":"+34 123 456 789", 
                "gender": "H" 
            }' \
    -H 'Content-Type: application/json' \
    localhost:8080/users
```
