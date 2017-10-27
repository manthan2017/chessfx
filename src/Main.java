
/**
 * @author Manthan
 */
public class Main {
	public static void main(String args[]) throws InterruptedException {
		UI ui = new UI();
		ui.run();
		//test(1000);
	}

	public static void test(int n) throws InterruptedException {
		Logic logic = null;
		int white = 0;
		int black = 0;
		for (int i = 0; i < n; i++) {
			logic = new Logic();
			while (logic.Winner == 0) {
				logic.player1Move();
				logic.player2Move();
			}
			if (logic.Winner == 1) {
				white++;
			} else if (logic.Winner == 2) {
				black++;
			}
		}

		System.out.println("White: " + white);
		System.out.println("Black: " + black);
	}
}
