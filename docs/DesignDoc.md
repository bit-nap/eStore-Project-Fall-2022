---
geometry: margin=1in
---

# PROJECT Design Documentation

## Team Information

* Team name: Group 3C, The Code Monkeys
* Team members
	* Adrian Cliteur
	* Gino Coppola
	* Louan Flammanc
	* Norton Perez
	* Oscar Rojas

## Executive Summary

This is the website for a small theater, only one actual theater, which screens "classic" movies.

### Purpose

This website allows users to buy tickets and snacks (soda and popcorn) to the screening of a movie at this movie theater.
The admin (theater owner) can edit screenings and change the movie shown at a screening.
The admin can also create new screenings of the existing movies.

### Glossary and Acronyms

| Term      | Definition                                |
|-----------|-------------------------------------------|
| Movie     | A film                                    |
| Screening | The showing of a movie at a movie theater |

## Requirements

This section describes the features of the application.

### Definition of MVP

The website allows users to purchase tickets to the screening of a movie.

### MVP Features

- Users can manage their cart, which includes the ability to add tickets, soda and popcorn for a screening.
- Users can make accounts to make purchases and view their order history.
- Users can vote/suggest on new movies to be shown at a screening.

### Roadmap of Enhancements

- Users have the ability to select one of multiple movies to watch.
- Users can select the screening of a movie to attend.
- Users can select the number of tickets to purchase to a screening.
- Users can complete their purchase.
- Users can create/log in to/delete an account.
- Users can select the number of sodas they want to purchase for a screening.
- Users can select the number of popcorn they want to purchase for a screening.
- Users can select a specific seat in the theater for each ticket they will purchase.
- Users can view their purchase history, when logged in.


- Admin can view all screenings of all movies.
- Admin can modify existing screenings.
- Admin can view what seats are already reserved for each screening.

## Application Domain

This section describes the application domain.

![Domain Model](domain-model.jpg)

In our design, a Screening would display a Movie, which has various attributes specific to that movie.
A Screening has date and time fields for the user to choose based on.
Screening also has a counter of tickets remaining so the product owner can prevent overbooking a Screening.

The shopping cart contains information about the tickets, soda and popcorn which a customer is buying to attend a Screening.
A customer cannot purchase tickets to multiple Screenings at once.

There must be at least 1 ticket for the shopping cart to exist, and there is at most 20 seats in a theater so the number of tickets cannot exceed 20.
Soda and popcorn is optional and a user can complete a checkout without purchasing any.

The customer must either make an account, or sign in to an existing account to make a purchase. A customer can also delete theri account.

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern.

The Model stores the application data objects including any functionality to provide persistence.

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful
APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are
supplied below.

### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the e-store application.

The landing page displays the all the movies a user can view at the theater.
The user can enter text into the search bar to filter the movies they see based on their movie title.
There is a navigation header at the top of the webpage that has a button that allows a user to log in to, create, or delete their account.

When logged in, a user can select a movie from the homepage which brings up a list of screenings for that movie.
Upon selecting a screening, the user can select a seat (thereby selecting a ticket) to purchase for a screening.
In the same page, a user can select the number of sodas and/or popcorn to purchase for the same screening.
The user can then finalize their purchase and is displayed a page with information about the screening they have purchased tickets for.
The user, when logged in, can vote or suggest on a new movie. If they click on a button for a movie, it will increase the number of votes 
by one. They can click it as many times as they want. They can also suggest a new movie to be added that will have 1 vote added automatically. 
The admin, when logged in, can add a new movie or change the movie name of a previous one, but they cannot vote themselves.

### View Tier

> _Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities. This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _You must also provide sequence diagrams as is relevant to a particular aspects
> of the design that you are describing. For example, in e-store you might create a
> sequence diagram of a customer searching for an item and adding to their cart.
> Be sure to include an relevant HTTP reuqests from the client-side to the server-side
> to help illustrate the end-to-end flow._

There are several components needed to handle the user purchase of tickets to a screening.

![Sequence Diagram of User Purchase](complete-order-sequence-diagram.png)

The landing page logic is located in the `Movies` component. This component retrieves all `Movie` objects using an HTTP GET request
and displays them to the user. The search functionality on the landing page also uses an HTTP GET request (with a query string)
to retrieve all the movies with the given phrase in their title. When the user selects a movie, that `Movie` object is stored with the `MovieSelectorService`.
The user is then routed to the `/screenings` page.

Similarly, the `Screenings` component retrieves all screenings for the selected movie using the `MovieSelectorService`
and an HTTP GET request with a query string. When the user selects a screening, that `Screening` object is saved in the `ScreeningSelectorService`.
The user is then routed to the `/tickets`page.

The `Tickets` component presents allows the user to select the seats for a screening, the number of sodas and popcorn to order for the screening.
When the user finalizes their purchase using the button at the bottom of the page, the `Tickets` component sends
an HTTP POST request to the orders endpoint, adding that order to the storage. The user is then routed to the `/thank` page.

The `CompletedPurchase` component then presents the user with a summary of their order. Specifically, it displays information about the
selected movie and date and time of the screening using the `MovieSelectorService` and `ScreeningSelectorService`.

The `VoteSuggestion` component presents the user with a list of movies that they can vote on, or add a new movie. It will use an HTTP GET request to get the movie,
and then use a PUT to add a vote to the movie.

The `VoteSuggestionAdmin` component presents the admin with a similar pge to the user. But, they will be able to use an HTTP PUT method to change the movie name.

The `AdminComponent` component presents the admin the ability to change inventory and change the vote/suggestions. For the inventory, they will use POST methods to 
create a new screening, and a PUT method to change the screening, and DELETE method to delete the screening.

### ViewModel Tier

> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._


![ScreeningController UML Diagram](uml-diagrams/screening-controller.png)

The `ScreeningController` is used to respond to HTTP requests for `Screening` objects. There are methods that handle simple GET, POST, PUT, DELETE requests.
The POST and PUT requests require a `Screening` object as an argument, to insert into the storage of `Screening` objects.
The method `getScreeningsByMovieId` is used to find all `Screenings` for a `Movie`.
The `getScreeningsByMovieId` takes an argument representing a movie id to search for, and returns an array of all `Screenings` for that movie.
This method is used in the View Tier to display all screenings of a movie when a user is placing an order.

![MovieController UML Diagram](uml-diagrams/movie-controller.png)

The `MovieController` is used to respond to HTTP requests for `Movie` objects. There are methods that handle simple GET, POST, PUT, DELETE requests.
The POST and PUT requests require a `Movie` object as an argument, to insert into the storage of `Movie` objects.
The `searchMovies` method takes an argument of a String to find all movies with a title that contains that string.
This method is used in the View Tier, providing the functionality of the homepage search bar.

![SuggestionController UML Diagram](uml-diagram/suggestion-controller.png)

The `SuggestionController` is used to respond to HTTP requests for `Suggestion` objects. There are methods that handle simple GET, POST, PUT, and DELETE requests.
The POST and PUT requests require a `Suggestion` object as an argument, to insert into the storage of a `Suggestion` objects.


### Model Tier

> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._

![Movie UML Diagram](uml-diagrams/movie-model.png)

The `Movie` model has fields relating to a movie. The `poster` field is the relative path to the poster of the movie
in the estore-ui folder. The `mpaRating` field is the Motion Pictures Association's rating for the movie, such as G, PG, PG-13 or R.

![Screening UML Diagram](uml-diagrams/screening-model.png)

The `Screening` model has fields relating to the screening of a movie (indicated with `movieId` field), such as the date and time of the screening.
The `ticketsRemaining` field can never be higher than the `TOTAL_TICKETS` field in the `Screening` class. This number will decrement everytime
a user purchases tickets to a screening. The `seats` field is a 2D Array of booleans representing the 20 seats in the theater.
If a seat is already reserved by a user, it will have a value of `True` in the `seats` field. If the seat is empty, it will be `False`.

![Suggestion UML Diagram](uml-diagram/suggestion-model.png)

The `Suggestion` model has fields relating to the number of votes for the movie and the movie name. 
The `votes` field will be incremented by one everytime the user clicks on the button on the front end. 
The `movieTitle` field can be changed by the admin, and added by the user.

### Static Code Analysis/Design Improvements

> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements._

> _With the results from the Static Code Analysis exercise,
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing

> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing

> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage

> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
