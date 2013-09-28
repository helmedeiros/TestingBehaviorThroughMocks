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

2. Now is time to cover the other point of this scenario. Review the AuctionCloserTest, write a test that ensures that the class doesn't closes auctions that began less than a week ago.

3. What happens when there's no auction open to be closed. Let's use the mocks to test this?

4. Most of mock frameworks only mock based on interfaces. Mockito also works with class, are we missing something mocking our DAO class directly? And if this implementation had changes, what happens with our tests? Could we fix that? Extract a common interface for both AuctionDao an AuctionDaoFake, and changed it all over our tests to use this interface instead.