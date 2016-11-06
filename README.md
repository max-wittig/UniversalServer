# UniversalServer
An http remote control server, that is easily extensible and universal

# How to add commands
Create a new class in the `Commands` package, that extends `Command` and implement the abstract method.  
The class name is the command name  
Additionally you can change the response, that is returned by changing the protected `response` attribute
