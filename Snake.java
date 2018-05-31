package varun.apcs.entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import varun.apcs.main.Board;

public class Snake extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Direction direction = null;
	private Timer timer = new Timer(75, this);

	int speed = 20;
	int length = 1;
	boolean alive = true;

	public int[] xPos = new int[(Board.WIDTH * Board.HEIGHT)
			/ (Board.size * Board.size)];
	public int[] yPos = new int[(Board.WIDTH * Board.HEIGHT)
			/ (Board.size * Board.size)];

	public Snake(int x, int y) {
		xPos[0] = x;
		yPos[0] = y;
		timer.start();
	}

	public void actionPerformed(ActionEvent arg0) {
		if (xPos[0] >= Board.WIDTH || xPos[0] < 0 || yPos[0] >= Board.HEIGHT
				|| yPos[0] < 0) {
			alive = false;
		}
		for (int i = 2; i < length; i++) {
			if (checkForCollision(xPos[i], yPos[i])) {
				alive = false;
			}
		}
		if (direction == Direction.UP) {
			move(-speed, yPos);
		}
		if (direction == Direction.DOWN) {
			move(speed, yPos);
		}
		if (direction == Direction.LEFT) {
			move(-speed, xPos);
		}
		if (direction == Direction.RIGHT) {
			move(speed, xPos);
		}
		repaint();
	}

	public void move(int speed, int arr[]) {
		for (int i = length; i > 0; i--) {
			xPos[i] = xPos[(i - 1)];
			yPos[i] = yPos[(i - 1)];
		}
		arr[0] += speed;
	}

	public boolean checkIfEating(int xDot, int yDot) {
		if (xPos[0] == xDot && yPos[0] == yDot) {
			return true;
		}
		return false;
	}

	public boolean checkForCollision(int x, int y) {
			if (xPos[0] == x && yPos[0] == y) {
				return true;
			}
		return false;
	}

	public void turn(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getX(int index) {
		return xPos[index];
	}

	public int getY(int index) {
		return yPos[index];
	}

	public int getLength() {
		return length;
	}

	public void add(int length) {
		for (int i = 0; i <= length; i++) {
			if (direction == Direction.UP) {
				xPos[this.length + i] = xPos[this.length + i - 1];
				yPos[this.length + i] = yPos[this.length + i - 1] + 20;
			} else if (direction == Direction.DOWN) {
				xPos[this.length + i] = xPos[this.length + i - 1];
				yPos[this.length + i] = yPos[this.length + i - 1] - 20;
			} else if (direction == Direction.LEFT) {
				xPos[this.length + i] = xPos[this.length + i - 1] + 20;
				yPos[this.length + i] = yPos[this.length + i - 1];
			} else if (direction == Direction.RIGHT) {
				xPos[this.length + i] = xPos[this.length + i - 1] - 20;
				yPos[this.length + i] = yPos[this.length + i - 1];
			}
		}
		this.length += length;
	}

	public boolean isAlive() {
		return alive;
	}

}
