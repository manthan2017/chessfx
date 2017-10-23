
/**
 * @author Manthan
 */
import javafx.scene.shape.Rectangle;

public class Piece {
	private Rectangle rec;
	private String value;
	private String state;
	private char alpha;
	private int num;

	public Piece(String value, String state, char alpha, int num) {
		this.value = value;
		this.state = state;
		this.alpha = alpha;
		this.num = num;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

	public void setAplha(char alpha) {
		this.alpha = alpha;
	}

	public char getAlpha() {
		return this.alpha;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNum() {
		return this.num;
	}

	public void setRectangle(Rectangle rec) {
		this.rec = rec;
	}

	public Rectangle getRectangle() {
		return this.rec;
	}

}