package github.erfan526z.tictactoeagent;

public class TicTacToe {

	public static void main(String[] args) {
		
		TicTacToeAgent agent = new TicTacToeAgent();
		TicTacToeWindow window = new TicTacToeWindow(agent);
		window.initialize();
		
	}
	
}
