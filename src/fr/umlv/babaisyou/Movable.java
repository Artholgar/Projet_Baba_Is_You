package fr.umlv.babaisyou;

import fr.umlv.zen5.KeyboardKey;

public sealed interface Movable permits Block {
	/**
	 * To make a block move in a direction
	 * @param direction
	 * @param win
	 * @return false if the block can't move
	 */
	public boolean move(KeyboardKey direction, Window win);
}
