package fr.umlv.babaisyou;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;

/**
 * 
 * @author arthur pairaud & thomas pourchet
 *
 */
public class Area {
	private final Window win;
	private final ArrayList<Block> blocks;
	private final HashMap<Name, HashSet<Name>> properties;

	/**
	 * Constructor for Area
	 * 
	 * @param win
	 */
	public Area(Window win) {
		this.win = Objects.requireNonNull(win);
		this.blocks = new ArrayList<>();
		this.properties = new HashMap<>();
		for (var block : Name.values()) {
			properties.put(block, new HashSet<>());
		}
	}

	/**
	 * Draw the area on the window
	 * 
	 * @param context
	 */
	public void draw(ApplicationContext context) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.DARK_GRAY);
			graphics.fill(new Rectangle2D.Float(0, 0, win.CaseX() * win.nbCaseW(), win.CaseY() * win.nbCaseH()));
			for (var block : blocks) {
				block.draw(graphics, win);
			}
		});
	}

	/**
	 * Add a block to the area
	 * 
	 * @param block
	 */
	public void addBlock(Block block) {
		Objects.requireNonNull(block);

		blocks.add(block);
	}

	/**
	 * Give a property to a block
	 * 
	 * @param name
	 * @param prop
	 */
	public void addProp(Name name, Name prop) {
		if (!prop.getType().equals(Type.Property)) {
			throw new IllegalArgumentException("prop should be a Property");
		}
		properties.get(name).add(prop);
	}

	/**
	 * Get a List of all blocks who have the name specified
	 * @param block
	 * @return the list of blocks
	 */
	public List<Block> getAll(Name block) {
		return blocks.stream().filter(b -> b.getName().equals(block)).collect(Collectors.toList());
	}

	/**
	 * Check if there is not a block with Stop property on the way of a block
	 * @param direction
	 * @param blockToMove
	 * @return true if the block can move
	 */
	private boolean stop(KeyboardKey direction, Block blockToMove) {
		for (var block : blocks) {
			if (hasProp(block, Name.Stop) && blockToMove.collision(block, direction)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Push a block (if there is some) who can be push
	 * @param direction
	 * @param block
	 * @return true if the block have successfully moved
	 */
	private boolean push(KeyboardKey direction, Block block) {
		if (stop(direction, block)) {
			return false;
		}
		for (var blockToPush : blocks) {
			if (hasProp(blockToPush, Name.Push) && block.collision(blockToPush, direction)) {
				if (this.push(direction, blockToPush)) {
					return blockToPush.move(direction, win);
				}
				return false;
			}
		}
		return true;

	}

	/**
	 * Check if a block have a property
	 * @param block
	 * @param prop
	 * @return true if the block have the property
	 */
	public boolean hasProp(Block block, Name prop) {
		Objects.requireNonNull(block);
		if (!prop.getType().equals(Type.Property)) {
			throw new IllegalArgumentException("prop should be a Property");
		}
		return properties.get(block.getName()).contains(prop);
	}

	/**
	 * Apply all properties to the blocks
	 * @param direction
	 */
	public void applyProps(KeyboardKey direction) {
		for (var block : blocks) {
			if (hasProp(block, Name.You)) {
				if (!stop(direction, block)) {
					if (push(direction, block)) {
						block.move(direction, win);
					}
				}
			}
		}
	}
	
	/**
	 * Discard all properties of the area
	 */
	public void discard() {
		for (var block : properties.keySet()) {
			properties.replace(block, new HashSet<Name>());
		}
	}
	
	/**
	 * Remade all the properties of the area.
	 */
	public void rulesOfGames() {
		var nouns = blocks.stream().filter(b -> b.getName().getType().equals(Type.Noun)).collect(Collectors.toList());
		var ops = blocks.stream().filter(b -> b.getName().getType().equals(Type.Operator)).collect(Collectors.toList());
		var props = blocks.stream().filter(b -> b.getName().getType().equals(Type.Property))
				.collect(Collectors.toList());
		var texts = blocks.stream().filter(b -> !b.getName().getType().equals(Type.Block)).collect(Collectors.toList());
		this.discard();
		for (var noun : nouns) {
//			We take a Noun
			for (var op : ops) {
//				We take an operation
//				If the noun and the operation are adjacent
				if (noun.collision(op, KeyboardKey.DOWN)) {
					for (var prop : props) {
//						We check for the last member of the rule
						if (op.collision(prop, KeyboardKey.DOWN)) {
							this.addProp(noun.getName().switchBlockNoun(), prop.getName());
						}
					}
					for (var noun2 : nouns) {
						if (op.collision(noun2, KeyboardKey.DOWN)) {
							System.out.println("lalal");
							blocks.forEach(b -> {
								if (b.getName().equals(noun.getName().switchBlockNoun())) {
									b.changeBlock(noun2.getName().switchBlockNoun());
								}
							});
						}
					}
				}
				if (noun.collision(op, KeyboardKey.RIGHT)) {
					for (var prop : props) {
						if (op.collision(prop, KeyboardKey.RIGHT)) {
							this.addProp(noun.getName().switchBlockNoun(), prop.getName());
						}
					}
					for (var noun2 : nouns) {
						if (op.collision(noun2, KeyboardKey.RIGHT)) {
							System.out.println("lalal");
							blocks.forEach(b -> {
								if (b.getName().equals(noun.getName().switchBlockNoun())) {
									b.changeBlock(noun2.getName().switchBlockNoun());
								}
							});
						}
					}
				}
			}
		}
		//Texts are always pushable
		for (var text : texts) {
			this.addProp(text.getName(), Name.Push);
		}
	}
}
