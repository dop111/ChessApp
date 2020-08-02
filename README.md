A chess application for playing chess and analysing positions.

The application interfaces with a so-called chess engine 
which does all the analysis and plays against the player.

The application is effectively a graphical user interface for chess engines.
The communication between the engine and the GUI is done using the so-called UCI protocol.
This means that any UCI compatible chess engine can be used in the application.

The application keeps track of moves (in the so called long algebraic chess notation)
and provides many other useful features.
For example, the user can set up a custom position on the board from a
so-called FEN (Forsyth–Edwards Notation) string or manually
by selecting pieces and clicking on the chess tiles where they want to place them.