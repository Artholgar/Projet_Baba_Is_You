package fr.umlv.babaisyou;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.umlv.zen5.KeyboardKey;

/**
 * 
 * @author arthur pairaud & thomas pourchet
 *
 */
public final class Block implements Movable, Drawable {
	private int x;
	private int y;
	private Image image;
	private Name name;
	
	/**
	 * Constructor for Block
	 * @param x
	 * @param y
	 * @param name
	 * @param area
	 */
	public Block(int x, int y, Name name, Area area) {
		this.x = x;
		this.y = y;
		this.name = name;
		try {
			this.image = ImageIO.read(new File(name.file()));
		} catch (IOException e) {
			System.out.println("no image found for " + name);
			e.printStackTrace();
			System.exit(1);
		}
		area.addBlock(this);
	}
	
	/**
	 * Getter for the name field
	 * @return the name field
	 */
	public Name getName() {
		return name;
	}
	
	/**
	 * To change the type of a Block, for example a Wall can became a Rock
	 * @param name
	 */
	public void changeBlock(Name name) {
		//We have to change the name, but we also have to change the image
		this.name = name;
		try {
			this.image = ImageIO.read(new File(name.file()));
		} catch (IOException e) {
			System.out.println("no image found for " + name);
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * To check if there is a collision batween two blocks
	 * @param other
	 * @param direction
	 * @return true if there is a collision else false
	 */
	public boolean collision(Block other, KeyboardKey direction) {
		Objects.requireNonNull(other);
		Objects.requireNonNull(direction);
		return switch (direction) {
		case UP -> this.x == other.x && this.y - 1 == other.y;
		case DOWN -> this.x == other.x && this.y + 1 == other.y;
		case LEFT -> this.x - 1 == other.x && this.y == other.y;
		case RIGHT -> this.x + 1 == other.x && this.y == other.y;
		default -> false;
		};
	}

	@Override
	public boolean move(KeyboardKey direction, Window win) {
		switch (direction) {
		case UP:
			if (y > 0) {
				y--;
				return true;
			}
			break;
		case DOWN:
			if (y < win.nbCaseH() - 1) {
				y++;
				return true;
			}
			break;
		case LEFT:
			if (x > 0) {
				x--;
				return true;
			}
			break;
		case RIGHT:
			if (x < win.nbCaseW() - 1) {
				x++;
				return true;
			}
			break;
		default:
			return false;
		}
		return false;
	}

	@Override
	public void draw(Graphics2D graphics, Window win) {
		graphics.drawImage(image, (int) win.CaseX() * x, (int) win.CaseY() * y, (int) win.CaseX(),
				(int) win.CaseY(), null);
	}
}
