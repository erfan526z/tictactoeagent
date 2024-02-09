# TicTacToe Agent

This is a simple computer opponent for the classic TicTacToe game. It's written in Java and uses swing library to implement the GUI.

## Features

- Interactive GUI for playing TicTacToe.
- Three types of algorithms to act as opponent:
  - Evaluator: Uses evaluation function to decide on the next move.
  - Utility Based: It expands the entire game tree and uses minimax algorithm to find next move.
  - Alpha Beta: It's just like Utility Based, but uses alpha-beta pruning to lower the response time.
- Game can be restarted :D

## Getting Started

### Requirements

- Java Development Kit (JDK) installed on your machine.
- It also requiers make to use Makefile provided (Or if you want, you can compile the files manually if you know how)

### Get It Running

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/erfan526z/tictactoeagent.git
   ```
2. Inside the project folder run
   ```bash
   make
   ```
3. The output file is located on "./release/TicTacToeAgent.jar". You can type make run it using command below
   ```bash
   make run
   ```
   There you go!

- To clean up the output files use "make clean"
- To clean up only the temporary .class files use "make cleantemp"

### Playing

The game is simple, You can click on each tile to fill it and the computer will response back with a move that is calculated using the selected algorithm. The winner is who could make 3 marks in a row or column or diagonally before the opponent.
You can choose alogrythms using the menu at bottom of page and the computer uses new algorithm after a game restart which is done using the restart game button.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

