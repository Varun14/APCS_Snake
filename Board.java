package varun.apcs.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import varun.apcs.entity.Dot;
import varun.apcs.entity.Snake;

/**
 * Main class that handles rendering and overall game managment
 * 
 * @author Varun Hegde
 * @version 1.0
 *
 */
public class Board extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private Thread thread;
	private Snake snake;
	private Dot dot;
	private Keyboard key;

	public static int WIDTH = 1000;
	public static int HEIGHT = 700;
	public static int size = 20;
	private static String title = new String("Snake  |  Varun Hegde");

	private boolean running = false;
	private Font font = new Font("Verdana", Font.PLAIN, 20);
	private Font gameOverFont = new Font("Arial", Font.BOLD, 100);

	public Board() {
		frame = new JFrame();
		snake = new Snake(0, 0);
		dot = new Dot();
		key = new Keyboard(snake);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		addKeyListener(key);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Main");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		repaint();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	public void update() {

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		super.paint(g);
		{
			// Snake operations
			if (snake.isAlive()) {
				for (int i = 0; i < snake.getLength(); i++) {
					g.setColor(Color.WHITE);
					g.fillRect(snake.getX(i), snake.getY(i), size, size);
					g.setColor(Color.BLACK);
					g.drawRect(snake.getX(i), snake.getY(i), size, size);
				}
				// Dot operations
				g.setColor(Color.RED);
				if (snake.checkIfEating(dot.getX(), dot.getY())) {
					dot.changeCoords();
					for (int i = 0; i < snake.getLength(); i++) {
						while (dot.getX() == snake.getX()
								&& dot.getY() == snake.getY()
								&& dot.getX() > 875 && dot.getY() < 30) {
							dot.changeCoords();
						}
					}
					g.fillRect(dot.getX(), dot.getY(), size, size);
					snake.add(3);
				} else {
					g.fillRect(dot.getX(), dot.getY(), size, size);
				}
				// Score string
				g.setColor(Color.WHITE);
				g.setFont(font);
				g.drawString("Length: " + snake.getLength(), 875, 30);
				g.dispose();
				bs.show();
			}
			else {
				g.setColor(Color.WHITE);
				g.setFont(gameOverFont);
				g.drawString("GAME OVER", 190, 200);
				g.setFont(font);
				g.drawString("Final Length: " + snake.getLength(), 435, 300);
				g.dispose();
				bs.show();
			}
		}
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.frame.setResizable(false);
		board.frame.getContentPane().setBackground(Color.BLACK);
		board.frame.add(board);
		board.frame.requestFocus();
		board.frame.setFocusable(true);
		board.frame.pack();
		board.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.frame.setLocationRelativeTo(null);
		board.frame.setTitle(title);
		board.frame.setVisible(true);

		board.start();
	}
}
