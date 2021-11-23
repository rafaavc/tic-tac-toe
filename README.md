# TicTacToe

- [User Guide](#user-guide)
- [Design Decisions](#design-decisions)
- [Machine Plays](#machine-plays)

[[click here to see a demonstration gif]](docs/demo.gif)

Project developed by Rafael Cristino ([GitHub](https://github.com/rafaavc))

## User guide

This project was developed using JDK 15.0.1. To run it (among other ways) you can import the `src` folder as a project in IntelliJ, and then run it by executing the `run` gradle task. This will open a server at [localhost:8080](http://localhost:8080).

By accessing the link you are presented by an immediately ready-to-play game against the computer, as specified in the project proposal.

[[click here to see the beginning screen]](docs/beginning.png)

Here you can play the game or pause / quit to main menu. If you go to the main menu this is what you'll see:

[[click here to see the main menu]](docs/mainMenu.png)

Here you can choose whether to play against a machine or another human. In both of these options you can customize the size of the game and the target length of the line of pieces to win, but if you choose to play against the machine you can also choose the piece you want (X starts first) and the amount of time that the machine has to find a move. Then, you can start a game by pressing the play button.

## Design decisions

As architectural pattern I decided to use MVC (model view controller), where the model is implemented in the `commonMain` module (so that it can be shared by server and client), the view is implemented in the `jsMain` module and the controller is split through the three modules (`commonMain`, `jsMain` and `jvmMain`).

To obtain the machine's play, the client makes a request to the server and, for simplification, the client always sends the serialized game model in each of these requests, which means that we have a stateless server.

Throughout the development design patterns were used where needed. For example:

- **Strategy** pattern: for how to make a play, for example, in HvH or HvM modes - [code](project/src/jsMain/kotlin/controller/move)
- **State** pattern: for coordinating the states of the game (playing, pause, welcome screen, error, game over) - [code](project/src/jsMain/kotlin/controller/states)
- **Abstract factory** pattern: for generically instantiating a move strategy without knowing which of them it is - [code](project/src/jsMain/kotlin/controller/move/factory)
- **Factory method** pattern: for centralizing the instantiation of game states - [code](project/src/jsMain/kotlin/controller/GameStateFactory.kt)

## Machine plays

The machine plays are obtained by using a combination of Monte Carlo Tree Search (MCTS) and some manual checking (by searching for play patterns on the game board). The machine manually checks (only) for defensive plays and the output of this overrules the output of MCTS only if it proves more beneficial. Other approaches using MiniMax were tried but didn't achieve comparable results because of the high branching factor of this version of the game.

Implementation: [MCTSRobot](project/src/jvmMain/kotlin/ai/MCTSRobot.kt)
