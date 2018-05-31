package varun.apcs.entity;

import java.util.Random;

import varun.apcs.main.Board;

public class Dot {

	Random random = new Random();

	int x = (random.nextInt(Board.WIDTH) / 20) * 20;
	int y = (random.nextInt(Board.HEIGHT) / 20) * 20;

	public void changeCoords() {
		x = (random.nextInt(Board.WIDTH) / 20) * 20;
		y = (random.nextInt(Board.HEIGHT) / 20) * 20;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
