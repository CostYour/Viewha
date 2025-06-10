# Viewha
Web-application for monitoring and managing Project Zomboid server depending on the Avrix core. It consists of a server part (API for the PZ server) and a client part (in the form of a website). Actual for `Project Zomboid 41.78.16 Stable Build`

## Disclaimer
This is working, but test version. At this moment it can does't work, some critical parts of the application may be missing (because I lost serverside code :\ ) 
So this repository at this moment is exist just for presentation and saved code from "unexpected" deleting. The code may cause you to have fits of anger, despair, or horror. That's fine, I sketched it out as a test to see if I could recreate something like this, and in the future I plan to fix both the code itself and the approaches to writing the code itself and its architecture. 

I'm just learning how to write good code, and I'll be glad for comments and advice.


## Feauters
- RCON - just send command from web-application and get result of executing
- API - you can see on index page settings of server and sandbox, list and count of players in `current/max` format
- Map view - see players and safehouses on the original Project Zomboid map
- Map control - сenter map on any player or safehouse on the server, rotate map!
- Updating the map - simply refresh the page to update the coordinates of the players and safehouses on the map (this is the only way it works at the moment and will be updated to "in realtime")

## Technologies used, frameworks, etc.
- Java
  - Maven
  - Spring Boot
  - Thymeleaf
- JavaScript
  - OpenSeaDragon
- HTML, CSS (in low priority yet)

## TODOLIST
- Do normal versioning
- Clean up the code: rewrite it using IoC, DI and based on some design pattern
- CSS and HTML code should be rewrited! It terrible
   
    
