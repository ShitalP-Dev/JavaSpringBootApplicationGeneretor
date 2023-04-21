# <%= appName %>

<%_ if (buildTool === 'maven') { _%>
### Run tests
`$ ./mvnw clean verify`

### Run locally
```
$ docker-compose -f docker/docker-compose.yml up -d
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```
<%_ } _%>

<%_ if (buildTool === 'gradle') { _%>
### Run tests
`$ ./gradlew clean build`

### Run locally
```
$ docker-compose -f docker/docker-compose.yml up -d
$ ./gradlew bootRun -Plocal
```
<%_ } _%>

### Useful Links
* Swagger UI: http://localhost:8080/swagger-ui.html
* Actuator Endpoint: http://localhost:8080/actuator
<%_ if (cloudFeatures.includes('elk')) { _%>
* Prometheus: http://localhost:9090/
* Grafana: http://localhost:3000/ (admin/admin)
* Kibana: http://localhost:5601/
<%_ } _%>

### About this project
1.Domain-Driven Hexagon
The generated project is based on Domain-Driven Hexagon pattern.This readme includes a quick brief about the terminologies that are beign used in this pattern along with some recommendations.

2.Modules
This project's code use separation by modules (also called components). Each module's name should reflect an important concept from the Domain and have its own folder with a dedicated codebase. Each business use case inside that module gets its own folder to store most of the things it needs (this is also called Vertical Slicing). It's easier to work on things that change together if those things are gathered relatively close to each other. Think of a module as a "box" that groups together related business logic.

Using modules is a great way to encapsulate parts of highly cohesive business domain rules.

3.Application Layer

a.Commands and Queries
This principle is called Command–Query Separation(CQS). When possible, methods should be separated into Commands (state-changing operations) and Queries (data-retrieval operations). To make a clear distinction between those two types of operations, input objects can be represented as Commands and Queries. Before DTO reaches the domain, it's converted into a Command/Query object.

# Commands
Command is an object that signals user intent, for example CreateUserCommand. It describes a single action (but does not perform it).

Commands are used for state-changing actions, like creating new user and saving it to the database. Create, Update and Delete operations are considered as state-changing.

Data retrieval is responsibility of Queries, so Command methods should not return business data.

Some CQS purists may say that a Command shouldn't return anything at all. But you will need at least an ID of a created item to access it later. To achieve that you can let clients generate a UUID (more info here: CQS versus server generated IDs).

Though, violating this rule and returning some metadata, like ID of a created item, redirect link, confirmation message, status, or other metadata is a more practical approach than following dogmas.

# Queries
Query is similar to a Command. It signals user intent to find something and describes how to do it.

Query is used for retrieving data and should not make any state changes (like writes to the database, files etc.).

Queries are usually just a data retrieval operation and have no business logic involved; so, if needed, application and domain layers can be bypassed completely. Though, if some additional non-state changing logic has to be applied before returning a query response (like calculating something), it can be done in an application/domain layer.

By enforcing Command and Query separation, the code becomes simpler to understand. One changes something, another just retrieves data.

b.Application Service
Are also called "Workflow Services", "Use Cases", "Interactors" etc. These services orchestrate the steps required to fulfill the commands imposed by the client.

* Typically used to orchestrate how the outside world interacts with your application and performs tasks required by the end users.
* Contain no domain-specific business logic;
* Operate on scalar types, transforming them into Domain types. A scalar type can be considered any type that's unknown to the Domain Model.
  This includes primitive types and types that don't belong to the Domain.
* Declare dependencies on infrastructural services required to execute domain logic (by using Ports).
* Are used in order to fetch domain Entities (or anything else) from database/outside world through Ports;
* Execute other out-of-process communications through Ports (like event emits, sending emails etc.);
* In case of interacting with one Entity/Aggregate, execute its methods directly;
* In case of working with multiple Entities/Aggregates, use a Domain Service to orchestrate them;
* Are basically Command/Query handlers;
* Should not depend on other application services since it may cause problems (like cyclic dependencies);
* One service per use case is considered a good practice.

c.Ports
Ports are interfaces that define contracts that should be implemented by adapters. For example, a port can abstract technology details (like what type of database is used to retrieve some data), and infrastructure layer can implement an adapter in order to execute some action more related to technology details rather than business logic. Ports act like abstractions for technology details that business logic does not care about. Name "port" most actively is used in Hexagonal Architecture.

4.Interface Adapters
Interface adapters (also called driving/primary adapters) are user-facing interfaces that take input data from the user and repackage it in a form that is convenient for the use cases(services/command handlers) and entities. Then they take the output from those use cases and entities and repackage it in a form that is convenient for displaying it back for the user. User can be either a person using an application or another server.Contains Controllers and Request/Response DTOs (can also contain Views, like backend-generated HTML templates, if required).

a.Controllers
Controller is a user-facing API that is used for parsing requests, triggering business logic and presenting the result back to the client.
One controller per use case is considered a good practice.

b.Resolvers
If you are using GraphQL instead of controllers, you will use Resolvers.One of the main benefits of a layered architecture is separation of concerns. As you can see, it doesn't matter if you use REST or GraphQL, the only thing that changes is user-facing API layer (interface-adapters). All the application Core stays the same since it doesn't depend on technology you are using.

c.DTOs
Data that comes from external applications should be represented by a special type of classes - Data Transfer Objects (DTO for short). Data Transfer Object is an object that carries data between processes. It defines a contract between your API and clients.
Using DTOs protects your clients from internal data structure changes that may happen in your API. When internal data models change (like renaming variables or splitting tables), they can still be mapped to match a corresponding DTO to maintain compatibility for anyone using your API.

d.Request DTOs
Input data sent by a user.Using Request DTOs gives a contract that a client of your API has to follow to make a correct request.

e.Response DTOs
Output data returned to a user.Using Response DTOs ensures clients only receive data described in DTOs contract, not everything that your model/entity owns (which may result in data leaks).

5.Infrastructure Layer
The Infrastructure layer is responsible for encapsulating technology. You can find there the implementations of database repositories for storing/retrieving business entities, message brokers to emit messages/events, I/O services to access external resources, framework related code and any other code that represents a replaceable detail for the architecture.

It's the most volatile layer. Since the things in this layer are so likely to change, they are kept as far away as possible from the more stable domain layers. Because they are kept separate, it's relatively easy to make changes or swap one component for another.

Infrastructure layer can contain Adapters, database related files like Repositories, ORM entities/Schemas, framework related files etc.

a.Adapters
Adapters are essentially an implementation of ports. They are not supposed to be called directly in any point in code, only through ports(interfaces).Infrastructure adapters (also called driven/secondary adapters) enable a software system to interact with external systems by receiving, storing and providing data when requested (like persistence, message brokers, sending emails or messages, requesting 3rd party APIs etc).
Adapters also can be used to interact with different domains inside single process to avoid coupling between those domains.

b.Repositories
Repositories are abstractions over collections of entities that are living in a database. They centralize common data access functionality and encapsulate the logic required to access that data. Entities/aggregates can be put into a repository and then retrieved at a later time without domain even knowing where data is saved: in a database, in a file, or some other source.

The data flow here looks something like this: repository receives a domain Entity from application service, maps it to database schema/ORM format, does required operations (saving/updating/retrieving etc), then maps it back to domain Entity format and returns it back to service.

Application's core usually is not allowed to depend on repositories directly, instead it depends on abstractions (ports/interfaces). This makes data retrieval technology-agnostic.

We use repositories to decouple the infrastructure or technology used to access databases from the domain model layer.

c.Persistance Models
Using a single entity for domain logic and database concerns leads to a database-centric architecture. In DDD world domain model and persistance model should be separated.

Since domain Entities have their data modeled so that it best accommodates domain logic, it may be not in the best shape to save in a database. For that purpose Persistence models can be created that have a shape that is better represented in a particular database that is used. Domain layer should not know anything about persistance models, and it should not care.

There can be multiple models optimized for different purposes, for example:

Domain with its own models - Entities, Aggregates and Value Objects.
Persistence layer with its own models - ORM (Object–relational mapping), schemas, read/write models if databases are separated into a read and write db (CQRS) etc.
Over time, when the amount of data grows, there may be a need to make some changes in the database like improving performance or data integrity by re-designing some tables or even changing the database entirely. Without an explicit separation between Domain and Persistance models any change to the database will lead to change in your domain Entities or Aggregates. For example, when performing a database normalization data can spread across multiple tables rather than being in one table, or vice-versa for denormalization. This may force a team to do a complete refactoring of a domain layer which may cause unexpected bugs and challenges. Separating Domain and Persistance models prevents that.

Note: separating domain and persistance models may be overkill for smaller applications. It requires a lot of effort creating and maintaining boilerplate code like mappers and abstractions. Consider all pros and cons before making this decision.

####
NOTE : For further details please visit: https://github.com/Sairyss/domain-driven-hexagon.
####

