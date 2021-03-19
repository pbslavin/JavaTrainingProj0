Score Service

This REST api provides access to two related resources, Composers and 
Compositions. Composers may have multiple Compositions, which possess a foreign key.
The two resources are associated with `composers` and `compositions` endpoints,
respectively.

There is also a third table consisting of Users, with base endpoint `users`. Users 
are authenticated against the database upon login, as well as authorized 
or forbidden to access restricted endpoints or methods through the issuance of a header token. Users 
are subject to all CRUDL operations, if they are authorized.

Users with level-1 (or "general") authorization tokens may only send GET requests 
to any Composer or Composition endpoint, and may not access *any* User endpoint by *any* 
HTTP method. Level-2 ("admin") authorization tokens permit access to all method handlers,
including those of Users.

In addition to CRUDL operations, with standard RESTful routes, it is 
also possible to GET all Compositions belonging to a Composer by id, or to GET a single User by 
username (which must be unique). The form of these routes is as follows: `/composer/4/compositions` and 
`/users/username/PSlavin`).










