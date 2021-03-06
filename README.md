Testing Behavior Through Mocks
===========================

Second steps for TDD learners

These project is part of the [PM-72](http://www.alura.com.br/course/PM-72) course by [Caelum](http://www.caelum.com.br/).

Can you see any logical problem? The tests are covering all possible scenarios? FORK IT! AND SOLVE THE PROBLEMS :)

# Problems to solve

Below are all tags, and what is being expected to be done.

### V1.0 - Simulating behaviors with Mock Objects

##### Sometimes test interdependent systems can become an arduous and costly task. Imagine scenario in our auctions system, when we've to deal with all transactions to created, changed, and removed, any of our domain entities(auctions, auctioneers, throws and etc..). Thinking about concepts and theories that come forward in the use of doubles objects, as the case of Mocks, that will helps us simulating expected behaviors and help us document the steps in a process expected.

1. Create AuctionCloserTest class and write a test that ensures that the class closes auctions that began more than a week ago. Start simulating behaviors like the DAO involved (AuctionDao daoMock = mock (AuctionDao.class). It's a good practice to introduce some dependencies passing than as a parameter in their constructor.

2. Now is time to cover the other point of this scenario. Review the AuctionCloserTest, write a test that ensures that the class doesn't closes auctions that began less than a week ago.

3. What happens when there's no auction open to be closed. Let's use the mocks to test this?

4. Most of mock frameworks only mock based on interfaces. Mockito also works with class, are we missing something mocking our DAO class directly? And if this implementation had changes, what happens with our tests? Could we fix that? Extract a common interface for both AuctionDao an AuctionDaoFake, and changed it all over our tests to use this interface instead.

### v2.0 - Ensuring that the methods were invoked

#### The Mockito framework also allows us to check whether and how many times a particular method is called. This could be done using the pair verify() and times(). Let's see how to work more this feature. It's very important to remember that our tests should focus only on the expected behavior, not the current implementation. Use the verify only im parts of your could that are actually part of the business rules. Take care to not break the encapsulation and end up with really brittle tests that have to be changed all the time.

1. Until then, we did not know or had been a major part of our business rule, internalized within the AuctionCloser. Where an auction is closed that was open we should store in the database, so that this information does not suffer the same auction a new bid closing. Let's write a test that ensures that the method update is invoked by the class AuctionCloser. To do this, use the verify ().

2. For some cases, only check if the method was called could be insufficient, for those cases we'll use of another mockito's auxiliary method, times(). Change the previous made test to check with two different auctions if each of them was updated only once.

3. Another variation that could be used with Mockito's times method is never(). Let's improve our old test that verify that no auction until seven days ago should be updated, using the pair times and never.

4. A new functional requirement should be implemented. After an Auction had been closed we need to send an email about that change. We don't have any clue about how we'll implement that but we've already know that a change on AuctionCloser will happened. Implement an interface called MailSender, that will be injected through the constructor and will have the method send, that will be called after update the closed Auction. Change all AuctionCloser's tests to deal with the new constructor change.

5. Once we've created and changed our old tests, let's cover the new logic piece. Add a new test that ensure that a MailSender will deal to send the auction after it has been closed and updated in database. Check out the InOrder class, existing inside the Mockito, it could be helpful in our test scenario.

### v3.0 - Mocks that throw exceptions

#### Among our functional and cross-functional requirements, there is the recovering from failures, that as well as several others that base their logic in triggers fired for errors or failures of our systems. Thinking about test-driven development, we should always write tests before develop the solution. For this case, we'll need to simulate errors, to develop and test the new code. The Mockito framework provide some tools to do that, let's see below.

1. The AuctionCloser should not stop running when an exception is thrown by our DAO, he should try to process the next auction anyway. To do so, make the necessary changes in the method close() and write a test to ensure this behavior. Take a look into Mockito's method doThrow to simulate the exception.

2. Now ensures that if the exception was thrown by a MailSender our AuctionCloser still works the same way.

3. Now, take any of the previous tests and make the mock throw an Exception instead of IllegalStateException. Now run the test. What happens? Does it make sense?

4. Should never send any email, if all Auctions had throwable to be updated. Create a test to verify this.

5. Some of our test were duplicated with parts of codes that could be generalized. Let's try some of Mockito's matchers to turn our when() request more generics.

### v4.0 - Capturing arguments received by Mock

#### Know to much about what was being tested could be not cool and break the encapsulation, but sometimes it's really necessary according to your testing process and granularity. For those cases, the Mockito framework provide us a captor called ArgumentCaptor that enable us to know not only if a method was called, but mainly what it's returning so we could test. Many authors defend that only public class should be test, but sometimes it isn't not enough.

1. We have a new requirement, after we'd closed an Auction we have to receive its payment. Create a new class Payment, that receives the amount and date of it and keep it. Let's also create a new payment repository with a save(Payment payment) method. After that we'll need to create a PaymentManager class, with a method manage() that get all closed Auctions, and creates a payment for each one of them based on the higher bid (we can use an Auctioneer to evaluate and return the higher bid amount).

2. Let's test our PaymentManager. Write a test to ensure that the payment amount generated is equal to the highest bidder evaluated by the Auctioneer. Note that the generated charge is not returned to the test. To be able to test, we need to use the Mockito's ArgumentCaptor.

3. In the previous test you could mock or not the Auctioneer. Why should you mock it? Think about and chose the best way to test it.

### v5.0 - Isolating for testing

#### Sometimes our code over testing had dependencies with static classes or static methods, and we weren't able to mock then. In this session we'll check how to isolate something using abstractions and interfaces to test it.

1. We have a new requirement, the payment date should be postponed to monday if the closing happens over the weekend. Implement that.

2. Now imagine that you're trying to made this tests pass, but actually today wasn't a weekend's day. Our production code has a dependency with the static method getInstance from PaymentManager business rule, and we couldn't mock that to test specific days. Now create a Clock abstraction, and a concrete class SystemClock, and make the class PaymentManager make use of it. Check if all test still passing. Use mocks to test Saturday and Sunday cases.
