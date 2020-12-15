package fr.umlv.babaisyou;

import java.awt.Graphics2D;

public sealed interface Drawable permits Block {
	/**
	 * To draw a block on the window
	 * @param graphics
	 * @param win
	 */
	public void draw(Graphics2D graphics, Window win);
}
