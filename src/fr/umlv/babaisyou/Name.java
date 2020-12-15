package fr.umlv.babaisyou;

/**
 * 
 * @author arthur pairaud & thomas pourchet
 *
 */
public enum Name {
	Baba(Type.Block), Wall(Type.Block), Rock(Type.Block), Flag(Type.Block), Lava(Type.Block), Water(Type.Block),
	BabaT(Type.Noun), WallT(Type.Noun), RockT(Type.Noun), FlagT(Type.Noun), LavaT(Type.Noun), WaterT(Type.Noun),
	Is(Type.Operator), You(Type.Property), Stop(Type.Property), Push(Type.Property);

	private Type type;

	/**
	 * Constructor for Name
	 * 
	 * @param type
	 */
	private Name(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if (type.equals(Type.Noun)) {
			return super.toString().substring(0, super.toString().length() - 1);
		}
		return super.toString();
	}

	/**
	 * 
	 * @return the type of the Name
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * 
	 * @return the folder of the image
	 */
	private String dir() {
		return switch (type) {
		case Block -> "./imagesName/";
		case Noun -> "./imagesName/";
		case Operator -> "./imagesOpe/";
		case Property -> "./imagesProp/";
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}

	/**
	 * 
	 * @return the image name
	 */
	private String imageName() {
		return switch (type) {
		case Block -> "/" + this.toString().toUpperCase() + "_0.gif";
		case Noun -> "/Text_" + this.toString().toUpperCase() + "_0.gif";
		case Operator -> "/Text_" + this.toString().toUpperCase() + ".gif";
		case Property -> "/Prop_" + this.toString().toUpperCase() + ".gif";
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}

	/**
	 * This function gives the file path for the image of the Name
	 * @return file path
	 */
	public String file() {
		return dir() + this.toString().toUpperCase() + imageName();
	}

	/**
	 * To switch between Block and Noun
	 * @return the new Name
	 */
	public Name switchBlockNoun() {
		return switch (this.getType()) {
		case Block -> Name.valueOf(this.toString() + "T");
		case Noun -> Name.valueOf(this.toString());
		default -> this;
		};
	}
}
