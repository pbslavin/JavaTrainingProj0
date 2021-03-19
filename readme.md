Score Service

This REST api consists of two related database tables: Composers and 
Compositions; Composers have a one-to-many relationship to Compositions.
The main endpoints to access these resources are simply `/composers` and 
`/compositions`, with another slash followed by the id to access individual 
items in the database.

There is also a third table consisting of Users, who are authenticated 
by being checked against the database on login, and either authorized 
or forbidden to access restricted resources through the issuance of a 
header token. Users are also provided with all CRUDL methods.

Possessors of level-1 authorization tokens may only send GET requests to Composer 
and Composition endpoints, and may not access User endpoints by any HTTP method.

Those having level-2 ("admin") authorization may use any HTTP verb with any endpoint. 

In addition to the standard CRUDL operations with associated RESTful routes, it is 
also possible to GET all Compositions by Composer, and to GET a single User by 
username. These methods employ an additional route segment: `composer` and 
`username`, respectively, which are followed by the composer id or case-sensitive 
username (`.../compositions/composer/4` or `.../users/username/PSlavin`).










