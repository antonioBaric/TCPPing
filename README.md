# TCPPing

TCP ping application - sending data and measuring the time

Instructions for command line: 
 - open cmd and navigate to: TCPPing\target\classes 
 - execute next commands: 

  - Pitcher mode (Server) : java main.TCPPing -c -bind bindAddress -port portNumber
  - Catcher mode (Client) : java main.TCPPing -p -port portNumber -mps messagesPerSecond -size sizeOfMessages hostname

Note: First you need to start server (Cather) side, and then the client (Pitcher). In pitcher mode, fields "mps" and "size" can be left
empty since they have default properites.
