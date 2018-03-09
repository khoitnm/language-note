TODO
Need to separate the different <a href="https://www.sitepoint.com/javascript-testing-unit-functional-integration/">types of test</a>:
- Unit Test: asynchronous operations such as network, DB, and file I/O should be avoided. if you want to test that when a specific URL is posted to, a corresponding record gets added to the database, that would be an integration test, not a unit test.
- Integration Test: ensure that component collaborations work as expected. Assertions may test component API, UI, or side-effects (such as database I/O, logging, etc…)
- Functional Test: ensure that the app works as expected from the user’s perspective. Assertions primarily test the user interface.
