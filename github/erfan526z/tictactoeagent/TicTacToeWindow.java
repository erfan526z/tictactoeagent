package github.erfan526z.tictactoeagent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeWindow {
	
	public static final int WIDTH = 450;
	public static final int HEIGHT = 450;
	
	private final String[] agent_types = {
			"Evaluator",
			"Utility Based",
			"Alpha Beta"
	};
	
	private JFrame frame;
	private JPanel content_pane;
	private JLabel game_title;
	private JLabel game_state_txt;
	private JButton[] game_tiles;
	private JButton game_reset;
	private JComboBox<String> game_agent_selector;
	
	private TicTacToeProvider provider;
	private int[] game_state;
	
	public TicTacToeWindow(TicTacToeProvider provider) {
		this.provider = provider;
	}
	
	public void initialize() {
		initializeGame();
		initializeGUI();
	}
	
	private void initializeGame() {
		game_state = new int[9];
		for(int i = 0; i < 9; i++)
			game_state[0] = TicTacToeProvider.EMPTY;
	}
	
	private void initializeGUI() {
		frame = new JFrame("TicTacToe Agent");
		frame.setBounds(150, 150, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		content_pane = new JPanel();
		content_pane.setLayout(null);
		content_pane.setBackground(Color.DARK_GRAY);
		frame.setContentPane(content_pane);
		
		game_title = new JLabel("TicTacToe Agent");
		game_title.setBounds(0, 10, WIDTH, 50);
		game_title.setFont(new Font("SansSerif", Font.ITALIC, 32));
		game_title.setForeground(Color.WHITE);
		game_title.setHorizontalAlignment(JLabel.CENTER);
		content_pane.add(game_title);
		
		game_state_txt = new JLabel("Pick a tile");
		game_state_txt.setBounds(0, 60, WIDTH, 50);
		game_state_txt.setFont(new Font("SansSerif", Font.PLAIN, 20));
		game_state_txt.setForeground(Color.WHITE);
		game_state_txt.setHorizontalAlignment(JLabel.CENTER);
		content_pane.add(game_state_txt);
		
		game_agent_selector = new JComboBox<String>(agent_types);
		game_agent_selector.setBounds(16, HEIGHT - 110, 128, 40);
		game_agent_selector.setFont(new Font("SansSerif", Font.PLAIN, 16));
		content_pane.add(game_agent_selector);
		
		game_reset = new JButton("Reset game");
		game_reset.setBounds(160, HEIGHT - 110, 256, 40);
		game_reset.setFont(new Font("SansSerif", Font.PLAIN, 20));
		game_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = game_agent_selector.getSelectedIndex();
				if(idx < 0 || idx > 2) return;
				
				provider.reset(idx);
				for(int i = 0; i < 9; i++) {
					game_state[i] = TicTacToeProvider.EMPTY;
					game_tiles[i].setText(displayText(TicTacToeProvider.EMPTY));
					game_tiles[i].setBackground(new Color(16, 48, 72));
					game_tiles[i].setEnabled(true);
				}
				
				game_state_txt.setText("Pick a tile.");
			}
		});
		content_pane.add(game_reset);
		
		game_tiles = new JButton[9];
		for(int i = 0; i < 9; i++) {
			int row = i / 3;
			int col = i % 3;
			
			final int fi = i;
			
			game_tiles[i] = new JButton(displayText(TicTacToeProvider.EMPTY));
			game_tiles[i].setBounds(WIDTH / 2 - 96 + 64 * row, HEIGHT / 2 - 96 + 64 * col, 64, 64);
			game_tiles[i].setBackground(new Color(16, 48, 72));
			game_tiles[i].setForeground(Color.WHITE);
			game_tiles[i].setFont(new Font("SansSerif", Font.BOLD, 32));
			game_tiles[i].addActionListener(new ActionListener() {
				private final int my_index = fi;
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(game_state[my_index] == TicTacToeProvider.EMPTY) {
						game_tiles[my_index].setText(displayText(TicTacToeProvider.PLAYER));
						game_state[my_index] = TicTacToeProvider.PLAYER;
						
						provider.player_moves(my_index);
						
						int agent_move_idx = provider.get_agent_move();
						
						if(agent_move_idx != -1) {
							game_tiles[agent_move_idx].setText(displayText(TicTacToeProvider.AGENT));
							game_state[agent_move_idx] = TicTacToeProvider.AGENT;
						}
						
						int win_state = provider.check_for_win();
						if(win_state == TicTacToeProvider.EMPTY) {
							game_state_txt.setText("Pick a tile.");
						} else if(win_state == TicTacToeProvider.PLAYER) {
							game_state_txt.setText("You won the game.");
							for(int i = 0; i < 9; i++) {
								game_tiles[i].setBackground(new Color(16, 128, 16));
								game_tiles[i].setEnabled(false);
							}
						} else if(win_state == TicTacToeProvider.AGENT) {
							game_state_txt.setText("Agent won the game.");
							for(int i = 0; i < 9; i++) {
								game_tiles[i].setBackground(new Color(128, 16, 16));
								game_tiles[i].setEnabled(false);
							}
						} else if(win_state == TicTacToeProvider.DRAW) {
							game_state_txt.setText("Draw!");
							for(int i = 0; i < 9; i++) {
								game_tiles[i].setBackground(new Color(72, 72, 16));
								game_tiles[i].setEnabled(false);
							}
						}
					}
					else {
						game_state_txt.setText("That location is occupied.");
					}
				}
			});
			content_pane.add(game_tiles[i]);
		}
		
		frame.setVisible(true);
	}
	
	private String displayText(int state) {
		if(state == TicTacToeProvider.EMPTY)
			return "";
		else if(state == TicTacToeProvider.X)
			return "X";
		else if(state == TicTacToeProvider.O)
			return "O";
		return "?";
	}
	
}
