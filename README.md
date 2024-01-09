An auction Rest API built with Spring Boot, Java 17 and PostgreSQL.

Based on my college (FCT - UNL) Cloud Computing Systems course (project 1 - 2022/23 edition). 

The API is based on the following entities: 
- Users
- Media
- Auctions
- Bids
- Questions/Replies



Besides the CRUD operations for all of the entities, the service provides endpoints for the following queries:
- List of bids of a given auction;
- List of bids of a given user;
- List of auctions about to close;
- List of auctions that a given user created;
- List of auctions that a given user bid on;
- List of questions of a given auction.



Other important features/implementation details (will) include:
- Use of the scheduling features of Spring Boot to make an auction close;
- Account validation through an email link;
- A simple web interface;
- Security with tokens;
- A recommendation system;
- Unit tests;
- Documentation.
