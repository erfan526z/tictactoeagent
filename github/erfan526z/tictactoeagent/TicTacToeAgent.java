package github.erfan526z.tictactoeagent;

public class TicTacToeAgent implements TicTacToeProvider {
	
	class ValueAndSelection {
		public int value;
		public int selection;
		
		public ValueAndSelection(int v, int s) {
			value = v;
			selection = s;
		}
	}
	
	private int[] internal_state;
	private int agent_type;
	
	public TicTacToeAgent() {
		internal_state = new int[9];
		for(int i = 0; i < 9; i++) {
			internal_state[i] = EMPTY;
		}
	}

	@Override
	public void player_moves(int index) {
		internal_state[index] = PLAYER;
	}

	@Override
	public int get_agent_move() {
		if(is_terminal(internal_state)) return -1;
		
		ValueAndSelection agent_pick = null;
		
		if(agent_type == AGENT_EVALUATOR)
			agent_pick = next_move_minimax_evaluator(internal_state, 3, true);
		else if(agent_type == AGENT_UTILITY_BASED)
			agent_pick = next_move_minimax_utility(internal_state, true);
		else
			agent_pick = next_move_minimax_alphabeta(internal_state, true, -999, 999);
		
		
		internal_state[agent_pick.selection] = AGENT;
		return agent_pick.selection;
	}

	@Override
	public void reset(int agent_type) {
		this.agent_type = agent_type;
		for(int i = 0; i < 9; i++) {
			internal_state[i] = EMPTY;
		}
	}

	@Override
	public int check_for_win() {
		// Horizontal
		if(internal_state[0] == PLAYER && internal_state[1] == PLAYER && internal_state[2] == PLAYER) return PLAYER;
		if(internal_state[3] == PLAYER && internal_state[4] == PLAYER && internal_state[5] == PLAYER) return PLAYER;
		if(internal_state[6] == PLAYER && internal_state[7] == PLAYER && internal_state[8] == PLAYER) return PLAYER;
		// Vertical
		if(internal_state[0] == PLAYER && internal_state[3] == PLAYER && internal_state[6] == PLAYER) return PLAYER;
		if(internal_state[1] == PLAYER && internal_state[4] == PLAYER && internal_state[7] == PLAYER) return PLAYER;
		if(internal_state[2] == PLAYER && internal_state[5] == PLAYER && internal_state[8] == PLAYER) return PLAYER;
		// Diagonal
		if(internal_state[0] == PLAYER && internal_state[4] == PLAYER && internal_state[8] == PLAYER) return PLAYER;
		if(internal_state[2] == PLAYER && internal_state[4] == PLAYER && internal_state[6] == PLAYER) return PLAYER;
				
		// Horizontal
		if(internal_state[0] == AGENT && internal_state[1] == AGENT && internal_state[2] == AGENT) return AGENT;
		if(internal_state[3] == AGENT && internal_state[4] == AGENT && internal_state[5] == AGENT) return AGENT;
		if(internal_state[6] == AGENT && internal_state[7] == AGENT && internal_state[8] == AGENT) return AGENT;
				// Vertical
		if(internal_state[0] == AGENT && internal_state[3] == AGENT && internal_state[6] == AGENT) return AGENT;
		if(internal_state[1] == AGENT && internal_state[4] == AGENT && internal_state[7] == AGENT) return AGENT;
		if(internal_state[2] == AGENT && internal_state[5] == AGENT && internal_state[8] == AGENT) return AGENT;
				// Diagonal
		if(internal_state[0] == AGENT && internal_state[4] == AGENT && internal_state[8] == AGENT) return AGENT;
		if(internal_state[2] == AGENT && internal_state[4] == AGENT && internal_state[6] == AGENT) return AGENT;
				
		for(int x : internal_state)
			if(x == EMPTY) return EMPTY;
				
		return DRAW;
	}
	
	private int utility(int[] state) {
		// Horizontal
		if(state[0] == PLAYER && state[1] == PLAYER && state[2] == PLAYER) return -1;
		if(state[3] == PLAYER && state[4] == PLAYER && state[5] == PLAYER) return -1;
		if(state[6] == PLAYER && state[7] == PLAYER && state[8] == PLAYER) return -1;
		// Vertical
		if(state[0] == PLAYER && state[3] == PLAYER && state[6] == PLAYER) return -1;
		if(state[1] == PLAYER && state[4] == PLAYER && state[7] == PLAYER) return -1;
		if(state[2] == PLAYER && state[5] == PLAYER && state[8] == PLAYER) return -1;
		// Diagonal
		if(state[0] == PLAYER && state[4] == PLAYER && state[8] == PLAYER) return -1;
		if(state[2] == PLAYER && state[4] == PLAYER && state[6] == PLAYER) return -1;
		
		// Horizontal
		if(state[0] == AGENT && state[1] == AGENT && state[2] == AGENT) return 1;
		if(state[3] == AGENT && state[4] == AGENT && state[5] == AGENT) return 1;
		if(state[6] == AGENT && state[7] == AGENT && state[8] == AGENT) return 1;
		// Vertical
		if(state[0] == AGENT && state[3] == AGENT && state[6] == AGENT) return 1;
		if(state[1] == AGENT && state[4] == AGENT && state[7] == AGENT) return 1;
		if(state[2] == AGENT && state[5] == AGENT && state[8] == AGENT) return 1;
		// Diagonal
		if(state[0] == AGENT && state[4] == AGENT && state[8] == AGENT) return 1;
		if(state[2] == AGENT && state[4] == AGENT && state[6] == AGENT) return 1;
		
		return 0;
	}
	
	private boolean is_terminal(int[] state) {
		return check_for_win() != 0;
	}
	
	private int evaluate(int[] state) {
		int score = 0;
		
		score += evaluate_line(new int[] {state[0], state[1], state[2]});
		score += evaluate_line(new int[] {state[3], state[4], state[5]});
		score += evaluate_line(new int[] {state[6], state[7], state[8]});
		
		score += evaluate_line(new int[] {state[0], state[3], state[6]});
		score += evaluate_line(new int[] {state[1], state[4], state[7]});
		score += evaluate_line(new int[] {state[2], state[5], state[8]});
		
		score += evaluate_line(new int[] {state[0], state[4], state[8]});
		score += evaluate_line(new int[] {state[2], state[4], state[6]});
		
		return score;
	}
	
	private int evaluate_line(int[] line) {
		int player = 0, empty = 0, agent = 0;
		for(int i = 0; i < 3; i++) {
			if(line[i] == PLAYER) player++;
			else if(line[i] == AGENT) agent++;
			else empty++;
		}
		
		if(player == 3) return -100;
		if(player == 2 && empty == 1) return -3;
		if(player == 1 && empty == 2) return -1;
		if(agent == 3) return 100;
		if(agent == 2 && empty == 1) return 3;
		if(agent == 1 && empty == 2) return 1;
		return 0;
	}
	
	private ValueAndSelection next_move_minimax_evaluator(int[] state, int depth, boolean max_playing) { // MAX is agent
		if(depth == 0 || is_terminal(state))
			return new ValueAndSelection(evaluate(state), -1);
		
		if(max_playing) {
			ValueAndSelection pick = new ValueAndSelection(-999, -1);
			for(int i = 0; i < 9; i++) {
				if(state[i] != EMPTY) continue;
				state[i] = AGENT;
				ValueAndSelection next = next_move_minimax_evaluator(state, depth - 1, false);
				if(pick.value < next.value) {
					pick.selection = i;
					pick.value = next.value;
				}
				state[i] = EMPTY;
			}
			return pick;
		} else {
			ValueAndSelection pick = new ValueAndSelection(999, -1);
			for(int i = 0; i < 9; i++) {
				if(state[i] != EMPTY) continue;
				state[i] = PLAYER;
				ValueAndSelection next = next_move_minimax_evaluator(state, depth - 1, true);
				if(pick.value > next.value) {
					pick.selection = i;
					pick.value = next.value;
				}
				state[i] = EMPTY;
			}
			return pick;
		}
	}
	
	private ValueAndSelection next_move_minimax_utility(int[] state, boolean max_playing) { // MAX is agent
		if(is_terminal(state))
			return new ValueAndSelection(utility(state), -1);
		
		if(max_playing) {
			ValueAndSelection pick = new ValueAndSelection(-999, -1);
			for(int i = 0; i < 9; i++) {
				if(state[i] != EMPTY) continue;
				state[i] = AGENT;
				ValueAndSelection next = next_move_minimax_utility(state, false);
				if(pick.value < next.value) {
					pick.selection = i;
					pick.value = next.value;
				}
				state[i] = EMPTY;
			}
			return pick;
		} else {
			ValueAndSelection pick = new ValueAndSelection(999, -1);
			for(int i = 0; i < 9; i++) {
				if(state[i] != EMPTY) continue;
				state[i] = PLAYER;
				ValueAndSelection next = next_move_minimax_utility(state, true);
				if(pick.value > next.value) {
					pick.selection = i;
					pick.value = next.value;
				}
				state[i] = EMPTY;
			}
			return pick;
		}
	}
	
	private ValueAndSelection next_move_minimax_alphabeta(int[] state, boolean max_playing, int alpha, int beta) { // MAX is agent
		if(is_terminal(state))
			return new ValueAndSelection(utility(state), -1);
		
		if(max_playing) {
			ValueAndSelection pick = new ValueAndSelection(-999, -1);
			for(int i = 0; i < 9; i++) {
				if(state[i] != EMPTY) continue;
				state[i] = AGENT;
				ValueAndSelection next = next_move_minimax_alphabeta(state, false, alpha, beta);
				if(pick.value < next.value) {
					pick.selection = i;
					pick.value = next.value;
					
					if(pick.value > alpha)
						alpha = pick.value;
					
					if(pick.value >= beta) {
						state[i] = EMPTY;
						break;
					}
				}
				state[i] = EMPTY;
			}
			return pick;
		} else {
			ValueAndSelection pick = new ValueAndSelection(999, -1);
			for(int i = 0; i < 9; i++) {
				if(state[i] != EMPTY) continue;
				state[i] = PLAYER;
				ValueAndSelection next = next_move_minimax_alphabeta(state, true, alpha, beta);
				if(pick.value > next.value) {
					pick.selection = i;
					pick.value = next.value;
					
					if(pick.value < beta)
						beta = pick.value;
					
					if(pick.value <= alpha) {
						state[i] = EMPTY;
						break;
					}
				}
				state[i] = EMPTY;
			}
			return pick;
		}
	}
}
