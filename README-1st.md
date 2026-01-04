### Teleporters

See README.txt for Teleporters problem definition.

The solution shall adhere to the following Object-Oriented Design Principles.

#### Adherence to SOLID

Designs must conform to the SOLID principles, with particular emphasis on single responsibility, explicit dependencies, and substitutability.

#### No Temporal Coupling

Objects shall not rely on a specific order of method invocation to function correctly.
Any required dependencies or configuration must be provided at construction time or enforced by the type system.

Lines of code shall not rely on a specific order of execution to function correctly. i.e. if two lines of code in the same scope are switched the program should either function correctly or not compile.

#### Encapsulation of State

Objects shall not expose internal state or implementation details, including via getters.

Interaction with an object must occur exclusively through behaviour-centric methods.

#### Behaviour-Centric Design

* Objects shall be defined by what they do, not by the data they contain.
* State is an internal implementation detail, never part of an object’s public contract.

#### Ubiquitous, Noun-Based Class Naming

* Classes shall be named for the domain concept they represent, not the actions they perform.
* Class names must be nouns aligned with the ubiquitous language of the domain.

#### Command–Query Separation with Explicit Intent

Methods must fall into one of two categories:

##### Builders (Queries)

* Return a value
* Must not produce observable side effects
* Named as a noun describing the value returned

##### Manipulators (Commands)

* Produce a side effect (e.g. mutate state, emit events, interact with external systems)
* Must return void
* Named as a verb describing the action performed

#### Immutability by Default

Objects should be immutable unless mutation is essential to their role.

#### Tell, Don’t Ask

Objects should be instructed to perform behaviour rather than queried for state and acted upon externally.

#### Constructor Completeness

An object must be fully valid and usable immediately after construction.