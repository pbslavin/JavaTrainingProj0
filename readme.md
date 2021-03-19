Score Service

This REST api consists of two related database tables: Composers and 
Compositions; Composers have a one-to-many relationship to Compositions.
The resources are associated with `composers` and `composition` endpoints,
respectively.

There is also a third table consisting of Users (with base endpoint `users`), who 
are authenticated against the database upon login, as well as either authorized 
or forbidden to access restricted resources, by issuing a header token. Users 
are also provided with all CRUDL methods.

Users issued level-1 (or "general") authorization tokens may only send GET requests 
to Composer and Composition endpoints, and may not access *any* User endpoint by *any* 
HTTP method. Level-2 ("admin") authorization tokens permit access to all methods,
including full access to user records.

In addition to CRUDL operations, using standard RESTful routes, it is 
also possible to GET all Compositions by Composer, or a single User by 
username. Each of these methods interpolates an additional route segment 
(`composer` or `username`) between the primary resource's path segment and 
the composer id or case-sensitive username (`/compositions/composer/4` or 
`/users/username/PSlavin`).










