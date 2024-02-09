package github.erfan526z.tictactoeagent;

public interface TicTacToeProvider {
	
	public static final int EMPTY = 0;
	public static final int X = 1;
	public static final int O = 2;
	
	public static final int DRAW = 3;
	
	public static final int AGENT = X; // Agent uses X
	public static final int PLAYER = O; // Player uses O
	
	public static final int AGENT_EVALUATOR = 0;
	public static final int AGENT_UTILITY_BASED = 1;
	public static final int AGENT_ALPHA_BETA = 2;
	
	public void player_moves(int index);
	
	public int get_agent_move();
	
	public void reset(int agent_type);
	
	public int check_for_win();
}
