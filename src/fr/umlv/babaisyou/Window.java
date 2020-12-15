package fr.umlv.babaisyou;

public class Window {
	private final float sizeCaseX;
	private final float sizeCaseY;
	private final int nbCaseWidth;
	private final int nbCaseHeight;
	
	public Window(float width, float height, int nbCaseWidth, int nbCaseHeight){
		this.sizeCaseX =  width / nbCaseWidth;
		this.sizeCaseY =  height / nbCaseHeight;
		this.nbCaseWidth = nbCaseWidth;
		this.nbCaseHeight = nbCaseHeight;
	}
	
	public float CaseX(){
		return sizeCaseX;
	}
	
	public float CaseY() {
		return sizeCaseY;
	}
	
	public int nbCaseW() {
		return nbCaseWidth;
	}
	
	public int nbCaseH() {
		return nbCaseHeight;
	}
}
