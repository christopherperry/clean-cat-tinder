#Clean Architecture Version of an example application (Cat Tinder)

See: http://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html
for more info on this type of architecture.

## Purpose
The purpose of a clean architecture is to create applications where concerns are separated
in such a way as to make the application highly testable, and extensible.

The application is split into three distinct layers:

1. Presentation Layer (Using MVP)
2. Domain Layer (POJOs)
3. Data Layer (Repository Pattern)

## The Presentation Layer
The Presentation Layer consists of Presenters which house the business rules around
how a particular View is presented. Each communicates with a Use Case Interactor to obtain
an appropriate View Model for a particular use case. It then passes it along to the View for presentation.

The View in this description is some Interface that is implemented by an Activity, Fragment, or Android View.
Note that the Presenter houses all the logic, and that the View implementations are "dumb".

## The Domain Layer
This layer houses the business objects of the application. These are POJOs and are not effected by
the Use Cases. Objects in this layer could be shared across the enterprise.

## The Data Layer
The Data Layer uses the Repository Pattern to abstract the source of data. Data could come from a disk/memory cache,
from the cloud, from a database, etc. The point is, the user of the repository need not know where the data comes
from. Where it comes from is an implementation detail of a Repository implementation.

## Directory Structure

>"Architecture is about intent, not frameworks" ~ Uncle Bob

The directory structure is such that the intent of the application is front and center, not the frameworks
involved in creating the application.