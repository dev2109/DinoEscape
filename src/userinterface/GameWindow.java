package userinterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;
	private GameScreen gameScreen;

	public GameWindow() {

		super("Dino Escape The Dino Game");
		setSize(SCREEN_WIDTH, 250);
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
//		setVisible(true);
		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);

	}
	
	public void startGame() {
		setVisible(true);
		gameScreen.startGame();
	}
	
	public static void main(String args[]) {
		GameWindow g = new GameWindow();
		g.startGame();
	}
}
