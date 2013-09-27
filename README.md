TestingBehaviorThroughMocks
===========================

Second steps for TDD learners

These project is part of the PM-72 course by Caelum.

Can you see any logical problem? The tests are covering all possible scenarios? FORK IT! AND SOLVE THE PROBLEMS :)

# Problems to solve

Below are all tags, and what is being expected to be done.

### V1.0 - Simulating behaviors with Mock Objects

##### Sometimes test interdependent systems can become an arduous and costly task. Imagine scenario in our auctions system, when we've to deal with all transactions to created, changed, and removed, any of our domain entities(auctions, auctioneers, throws and etc..). Thinking about concepts and theories that come forward in the use of doubles objects, as the case of Mocks, that will helps us simulating expected behaviors and help us document the steps in a process expected.

1. Create AuctionCloserTest class and write a test that ensures that the class closes auctions that began more than a week ago. Start simulating behaviors like the DAO involved (AuctionDao daoMock = mock (AuctionDao.class). It's a good practice to introduce some dependencies passing than as a parameter in their constructor.