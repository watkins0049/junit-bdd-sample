# JUnit 5 Behavior Driven Development (BDD) Sample Application
This repo provides a sample of how to use JUnit's `Nested` and `DisplayName`
annotations to create comprehensive BDD tests that are easier to read,
understand, and maintain.

## Why should I read this?
Each project I've been on has had flat tests that are disorganized and hard to
read and maintain. Some of them had a BDD-style to them, but they were in the
comments and not the living, breathing code. Pushing the given/when/then setup
into the classes forces the developer to update the scenario, creating living,
breathing documentation that is useful for incoming developers.

In addition to creating living, breathing documentation, this setup creates
natively parameterized tests. Each scenario feeds into the next and each layer
of `BeforeEach` functions runs before the next layer. This allows for multiple
scenarios with similar setups to be tested easily.

## Where do I start?
I would recommend starting in the `PokemonServiceTests` class and read through
the comments. Start at the parent-level class and work your way down through
each nested child class. Once you're done there, take a look in the
`PokemonControllerTests` class and maybe try your hand at adding a test in that
class for invalid input!

## What are the benefits?
- Natively parameterized tests. Each layer of `Nested` class feeds to the next
- Living, breathing documentation. The tests make it readily apparent what the
    code is supposed to do.
- Forces you to think through the requirements. This allows you to easily
    create tests that match the business requirements. Better yet! It allows
    you to write the tests based on the requirements first, and _then_ write
    the code (hey! that sounds a lot like Test-Driven Development :)).

## What are the downsides?
- Boilerplate hell! Just take a look at `PokemonServiceTests` and you'll get an
    idea about how much fun this is to setup.
- Steep learning curve. This is _extremely_ different from what most developers
    are used to and will require significant learning and time investment.
