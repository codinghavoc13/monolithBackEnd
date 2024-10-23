# Monolith Backend and [Angular Frontend](https://github.com/codinghavoc13/angularFrontEnd)

This is Spring Boot backend portion of one part of my portfolio page. The two fully working sections are a Cipher application and a drastically reduced version of IMDB that only has information about movies that I own (and nowhere near the level of detail found on IMDB).

## Cipher

In addition to the dreaded "Hello World" application that is usually the first program new software developers write, I used to also write a basic Caesar Cipher program (details are on the page), so when I wrote this first Spring Boot application (and the accompanying [Angular page](https://github.com/codinghavoc13/angularFrontEnd)), I thought it appropriate to do the same. After writing the Caesar Cipher version, I also chose to build out another cipher using a pass phrase instead of one or two numerical keys. 

### Future plans

I plan to come back to this application at some point to clean up the code to use ASCII code values instead of a fixed alphabet.

## MMDB

MMDB, or My Movie Database, is, as I said, a drastically reduced version of IMDB. I keep track of movies I own, and some of the actors starring in those movies. The listings for each movie provide a brief summary and a few other details, along with a list of actors. Clicking on an actor takes to you another page showing some information about that actor and which movies they've been in (again, movies that I have). Click on one of those movies takes you back to the movie summary page for that movie. Round and round.

This page is meant to demonstrate getting information from a database and presenting to the screen.

### Future plans

Add a couple forms that will allow me to enter more information to the database. These forms will either be password protected so that I can update on the go, or will be local only so that I don't have to worry about someone hacking in and claiming that I have movies that I don't or are questionable