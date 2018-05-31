package varun.apcs.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import varun.apcs.entity.Direction;
import varun.apcs.entity.Snake;

public class Keyboard implements KeyListener {
	
	Snake snake;
	
	public Keyboard(Snake snake) {
		this.snake = snake;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != Direction.DOWN) {
			snake.turn(Direction.UP);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != Direction.UP) {
			snake.turn(Direction.DOWN);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != Direction.RIGHT) {
			snake.turn(Direction.LEFT);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != Direction.LEFT) {
			snake.turn(Direction.RIGHT);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
