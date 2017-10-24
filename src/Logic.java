
/**
 * @author Manthan
 */
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Logic {
	char board[][] = new char[8][8];

	Piece player1[] = new Piece[16];
	Piece player2[] = new Piece[16];

	final char pawn = 'p';
	final char rook = 'r';
	final char knight = 'n';
	final char bishop = 'b';
	final char queen = 'q';
	final char king = 'k';

	int promoteEvent = 0;
	Piece promotePawn;

	String p1Moves;
	String p2Moves;
	String Moves = "";
	String enPassant = "";
	int Winner = 0;
	int Turn = 0;
	int selected = 0;

	int Mode = 1;

	// 0=player1 1=player2
	private int turn = 0;

	public Logic() throws InterruptedException {
		// Initialize Board
		initialize();
	}

	public void initialize() {

		// Initialize Player 1
		player1[0] = new Piece("Pawn", "White", 'a', 2);
		player1[1] = new Piece("Pawn", "White", 'b', 2);
		player1[2] = new Piece("Pawn", "White", 'c', 2);
		player1[3] = new Piece("Pawn", "White", 'd', 2);
		player1[4] = new Piece("Pawn", "White", 'e', 2);
		player1[5] = new Piece("Pawn", "White", 'f', 2);
		player1[6] = new Piece("Pawn", "White", 'g', 2);
		player1[7] = new Piece("Pawn", "White", 'h', 2);
		player1[8] = new Piece("Rook1", "White", 'a', 1);
		player1[9] = new Piece("Knight1", "White", 'b', 1);
		player1[10] = new Piece("Bishop1", "White", 'c', 1);
		player1[11] = new Piece("Queen", "White", 'd', 1);
		player1[12] = new Piece("King", "White", 'e', 1);
		player1[13] = new Piece("Bishop2", "White", 'f', 1);
		player1[14] = new Piece("Knight2", "White", 'g', 1);
		player1[15] = new Piece("Rook2", "White", 'h', 1);

		// Initialize Player2
		player2[0] = new Piece("Pawn", "Black", 'a', 7);
		player2[1] = new Piece("Pawn", "Black", 'b', 7);
		player2[2] = new Piece("Pawn", "Black", 'c', 7);
		player2[3] = new Piece("Pawn", "Black", 'd', 7);
		player2[4] = new Piece("Pawn", "Black", 'e', 7);
		player2[5] = new Piece("Pawn", "Black", 'f', 7);
		player2[6] = new Piece("Pawn", "Black", 'g', 7);
		player2[7] = new Piece("Pawn", "Black", 'h', 7);
		player2[8] = new Piece("Rook1", "Black", 'a', 8);
		player2[9] = new Piece("Knight1", "Black", 'b', 8);
		player2[10] = new Piece("Bishop1", "Black", 'c', 8);
		player2[11] = new Piece("Queen", "Black", 'd', 8);
		player2[12] = new Piece("King", "Black", 'e', 8);
		player2[13] = new Piece("Bishop2", "Black", 'f', 8);
		player2[14] = new Piece("Knight2", "Black", 'g', 8);
		player2[15] = new Piece("Rook2", "Black", 'h', 8);

	}

	public void refreshPieces() {
		if (player1[12].getState().equals("Null") || !movesPossible("White")) {
			Winner = 2;
		} else if (player2[12].getState().equals("Null") || !movesPossible("Black")) {
			Winner = 1;
		}
		p1Moves = "";
		p2Moves = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = '\0';
			}
		}
		for (int i = 0; i < 16; i++) {
			if (player1[i].getValue().equals("Pawn") && player1[i].getNum() == 8) {
				promoteEvent = 1;
				promotePawn = player1[i];
			}

			if (player2[i].getValue().equals("Pawn") && player2[i].getNum() == 1) {
				promoteEvent = 1;
				promotePawn = player2[i];
			}

			p1Moves += moveList(player1[i], "Attack").toString();
			p2Moves += moveList(player2[i], "Attack").toString();

			if (!player1[i].getState().equals("Null")) {
				if (player1[i].getValue().equals("Pawn")) {
					board[player1[i].getNum() - 1][player1[i].getAlpha() - 97] = pawn;
				} else if (player1[i].getValue().contains("Rook")) {
					board[player1[i].getNum() - 1][player1[i].getAlpha() - 97] = rook;
				} else if (player1[i].getValue().contains("Knight")) {
					board[player1[i].getNum() - 1][player1[i].getAlpha() - 97] = knight;
				} else if (player1[i].getValue().contains("Bishop")) {
					board[player1[i].getNum() - 1][player1[i].getAlpha() - 97] = bishop;
				} else if (player1[i].getValue().equals("King")) {
					board[player1[i].getNum() - 1][player1[i].getAlpha() - 97] = king;
				} else if (player1[i].getValue().equals("Queen")) {
					board[player1[i].getNum() - 1][player1[i].getAlpha() - 97] = queen;
				}
			}

			if (!player2[i].getState().equals("Null")) {
				if (player2[i].getValue().equals("Pawn")) {
					board[player2[i].getNum() - 1][player2[i].getAlpha() - 97] = pawn;
				} else if (player2[i].getValue().contains("Rook")) {
					board[player2[i].getNum() - 1][player2[i].getAlpha() - 97] = rook;
				} else if (player2[i].getValue().contains("Knight")) {
					board[player2[i].getNum() - 1][player2[i].getAlpha() - 97] = knight;
				} else if (player2[i].getValue().contains("Bishop")) {
					board[player2[i].getNum() - 1][player2[i].getAlpha() - 97] = bishop;
				} else if (player2[i].getValue().equals("King")) {
					board[player2[i].getNum() - 1][player2[i].getAlpha() - 97] = king;
				} else if (player2[i].getValue().equals("Queen")) {
					board[player2[i].getNum() - 1][player2[i].getAlpha() - 97] = queen;
				}
			}

		}
	}

	public void printBoard() {
		refreshPieces();
		System.out.println("################\n");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void player1Move() {
		if (Winner == 0) {
			if (movesPossible("White")) {
				Random rand = new Random();
				int i = rand.nextInt(16);
				while (moveList(player1[i], null).size() == 0 || player1[i].getState().equals("Null")) {
					i = rand.nextInt(16);
				}
				int j = moveList(player1[i], null).size();
				int z = rand.nextInt(j);
				makeMove(player1[i], moveList(player1[i], null).get(z));
				refreshPieces();
			}
		}
	}

	public void player2Move() {
		if (Winner == 0) {
			if (movesPossible("Black")) {
				Random rand = new Random();
				int i = rand.nextInt(16);
				while (moveList(player2[i], null).size() == 0 || player2[i].getState().equals("Null")) {
					i = rand.nextInt(16);
				}
				int j = moveList(player2[i], null).size();
				int z = rand.nextInt(j);
				makeMove(player2[i], moveList(player2[i], null).get(z));
				refreshPieces();
			}
		}
	}

	public void makeMove(Piece piece, String pos) {
		if (!exists(pos).equals("Null")) {
			clearTile(pos);
		}

		if (!enPassant.equals("")) {
			if (piece.getValue().equals("Pawn") && enPassant.substring(1, 3).equals(pos)) {
				if (enPassant.charAt(0) == 'W') {
					clearTile(pos.charAt(0) + "" + (Integer.parseInt(pos.substring(1) + 1)));
				}
				if (enPassant.charAt(0) == 'B') {
					clearTile(pos.charAt(0) + "" + (Integer.parseInt(pos.substring(1)) - 1));
				}
			}
			enPassant = "";
		}
		if (piece.getValue().equals("Pawn")) {
			if (piece.getState().equals("Black") && (piece.getNum() - Integer.parseInt(pos.substring(1))) == 2) {
				enPassant = "B" + piece.getAlpha() + "" + (piece.getNum() - 1);
			}
			if (piece.getState().equals("White") && (piece.getNum() - Integer.parseInt(pos.substring(1))) == -2) {
				enPassant = "W" + piece.getAlpha() + "" + (piece.getNum() + 1);
			}
		}

		if (piece.getValue().equals("King")) {
			if (piece.getState().equals("Black")) {
				if (piece.getAlpha() - pos.charAt(0) == 2) {
					player2[8].setAplha((char) (piece.getAlpha() + 1));
				} else if (piece.getAlpha() - Integer.parseInt(pos.substring(1)) == -2) {
					player2[15].setAplha((char) (piece.getAlpha() + 1));
				}
			}

			if (piece.getState().equals("White")) {
				if (piece.getAlpha() - pos.charAt(0) == 2) {
					player1[8].setAplha((char) (piece.getAlpha() + 1));
				} else if (piece.getAlpha() - pos.charAt(0) == -2) {
					player1[15].setAplha((char) (piece.getAlpha() + 1));
				}
			}
			refreshPieces();
		}

		Register(piece, getPosition(piece), pos);
		piece.setAplha(pos.charAt(0));
		piece.setNum(Integer.parseInt(pos.substring(1)));
		if (piece.getValue().equals("Pawn")) {
			if (piece.getState().equals("White") && piece.getNum() == 8) {
				promoteEvent = 1;
			}
			if (piece.getState().equals("Black") && piece.getNum() == 1) {
				promoteEvent = 1;
			}
		}

		if (promoteEvent == 1) {
			Random rand = new Random();
			int x = rand.nextInt(4);
			String value = "";
			if (x == 0) {
				value = "Queen";
			} else if (x == 1) {
				value = "Rook";
			} else if (x == 2) {
				value = "Bishop";
			} else if (x == 3) {
				value = "Knight";
			}
			promote(piece, value);
			promoteEvent = 0;
		}
	}

	public ArrayList<String> moveList(Piece piece, String attack) {
		ArrayList<String> list = new ArrayList();
		if (piece.getValue().equals("Pawn")) {
			if (attack != null) {
				if (piece.getState().equals("White")) {
					if ((piece.getAlpha() - 1) > 96 && (piece.getNum() + 1) < 9) {
						list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1));
					}
					if ((piece.getAlpha() + 1) < 105 && (piece.getNum() + 1) < 9) {
						list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1));
					}
				} else if (piece.getState().equals("Black")) {
					if ((piece.getAlpha() - 1) > 96 && (piece.getNum() - 1) > 0) {
						list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1));
					}
					if ((piece.getAlpha() + 1) < 105 && (piece.getNum() - 1) > 0) {
						list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1));
					}
				}
			} else {
				if (piece.getState().equals("White")) {
					if (piece.getNum() == 2
							&& exists((char) (piece.getAlpha()) + "" + (piece.getNum() + 2)).equals("Null")
							&& exists((char) (piece.getAlpha()) + "" + (piece.getNum() + 1)).equals("Null")) {
						list.add((char) (piece.getAlpha()) + "" + (piece.getNum() + 2));
					}
					if (exists((char) (piece.getAlpha()) + "" + (piece.getNum() + 1)).equals("Null")) {
						list.add((char) (piece.getAlpha()) + "" + (piece.getNum() + 1));
					}
					if (exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1)).equals("Black")) {
						list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1));
					}
					if (exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1)).equals("Black")) {
						list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1));
					}
					if (!enPassant.equals("")) {
						if ((enPassant.substring(1, 3).equals((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1))
								&& enPassant.charAt(0) == 'B')) {
							list.add(enPassant.substring(1, 3));
						}
						if ((enPassant.substring(1, 3).equals((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1))
								&& enPassant.charAt(0) == 'B')) {
							list.add(enPassant.substring(1, 3));
						}
					}
				} else if (piece.getState().equals("Black")) {
					if (piece.getNum() == 7
							&& exists((char) (piece.getAlpha()) + "" + (piece.getNum() - 2)).equals("Null")
							&& exists((char) (piece.getAlpha()) + "" + (piece.getNum() - 1)).equals("Null")) {
						list.add((char) (piece.getAlpha()) + "" + (piece.getNum() - 2));
					}
					if (exists((char) (piece.getAlpha()) + "" + (piece.getNum() - 1)).equals("Null")) {
						list.add((char) (piece.getAlpha()) + "" + (piece.getNum() - 1));
					}
					if (exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 1)).equals("White")) {
						list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 1));
					}
					if (exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 1)).equals("White")) {
						list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 1));
					}

					if (!enPassant.equals("")) {
						if ((enPassant.substring(1, 3).equals((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 1))
								&& enPassant.charAt(0) == 'W')) {
							list.add(enPassant.substring(1, 3));
						}
						if ((enPassant.substring(1, 3).equals((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 1))
								&& enPassant.charAt(0) == 'W')) {
							list.add(enPassant.substring(1, 3));
						}
					}

				}

			}

		} else if (piece.getValue().contains("Rook")) {
			char c = piece.getAlpha();
			int n = piece.getNum();
			while (true) {
				c++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();

			while (true) {
				c--;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();

			while (true) {
				n++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			n = piece.getNum();

			while (true) {
				n--;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

		} else if (piece.getValue().contains("Knight")) {
			if (piece.getState().equals("Black")) {
				if (!exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() + 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() + 1)).equals("Black")) {
					list.add((char) (piece.getAlpha() + 2) + "" + (piece.getNum() + 1));
				}
				if (!exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() - 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() - 1)).equals("Black")) {
					list.add((char) (piece.getAlpha() - 2) + "" + (piece.getNum() - 1));
				}
				if (!exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 2)).equals("Black")) {
					list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 2));
				}
				if (!exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 2)).equals("Black")) {
					list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 2));
				}
				if (!exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() - 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() - 1)).equals("Black")) {
					list.add((char) (piece.getAlpha() + 2) + "" + (piece.getNum() - 1));
				}
				if (!exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() + 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() + 1)).equals("Black")) {
					list.add((char) (piece.getAlpha() - 2) + "" + (piece.getNum() + 1));
				}
				if (!exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 2)).equals("Black")) {
					list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 2));
				}
				if (!exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 2)).equals("Black")) {
					list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 2));
				}
			} else if (piece.getState().equals("White")) {
				if (!exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() + 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() + 1)).equals("White")) {
					list.add((char) (piece.getAlpha() + 2) + "" + (piece.getNum() + 1));
				}
				if (!exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() - 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() - 1)).equals("White")) {
					list.add((char) (piece.getAlpha() - 2) + "" + (piece.getNum() - 1));
				}
				if (!exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 2)).equals("White")) {
					list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 2));
				}
				if (!exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 2)).equals("White")) {
					list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 2));
				}
				if (!exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() - 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 2) + "" + (piece.getNum() - 1)).equals("White")) {
					list.add((char) (piece.getAlpha() + 2) + "" + (piece.getNum() - 1));
				}
				if (!exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() + 1)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 2) + "" + (piece.getNum() + 1)).equals("White")) {
					list.add((char) (piece.getAlpha() - 2) + "" + (piece.getNum() + 1));
				}
				if (!exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 2)).equals("White")) {
					list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 2));
				}
				if (!exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 2)).equals("Error")
						&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 2)).equals("White")) {
					list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 2));
				}
			}
		} else if (piece.getValue().contains("Bishop")) {
			char c = piece.getAlpha();
			int n = piece.getNum();
			while (true) {
				c++;
				n++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();
			n = piece.getNum();
			while (true) {
				c--;
				n--;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();
			n = piece.getNum();
			while (true) {
				c--;
				n++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			n = piece.getNum();
			c = piece.getAlpha();
			while (true) {
				n--;
				c++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}
		} else if (piece.getValue().equals("Queen")) {
			char c = piece.getAlpha();
			int n = piece.getNum();
			while (true) {
				c++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();

			while (true) {
				c--;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();

			while (true) {
				n++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			n = piece.getNum();

			while (true) {
				n--;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			n = piece.getNum();

			while (true) {
				c++;
				n++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();
			n = piece.getNum();
			while (true) {
				c--;
				n--;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			c = piece.getAlpha();
			n = piece.getNum();
			while (true) {
				c--;
				n++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}

			n = piece.getNum();
			c = piece.getAlpha();
			while (true) {
				n--;
				c++;
				if (exists((char) c + "" + n).equals("Null")) {
					list.add((char) c + "" + n);
				} else {
					if (piece.getState().equals("Black")) {
						if (exists((char) c + "" + n).equals("White")) {
							list.add((char) c + "" + n);
						}
					} else if (piece.getState().equals("White")) {
						if (exists((char) c + "" + n).equals("Black")) {
							list.add((char) c + "" + n);
						}
					}
					break;
				}
			}
		} else if (piece.getValue().equals("King")) {
			if (!exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1)).equals("Error")
					&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1)).equals(piece.getState())) {
				list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() + 1));
			}
			if (!exists((char) (piece.getAlpha()) + "" + (piece.getNum() + 1)).equals("Error")
					&& !exists((char) (piece.getAlpha()) + "" + (piece.getNum() + 1)).equals(piece.getState())) {
				list.add((char) (piece.getAlpha()) + "" + (piece.getNum() + 1));
			}
			if (!exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1)).equals("Error")
					&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1)).equals(piece.getState())) {
				list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() + 1));
			}
			if (!exists((char) (piece.getAlpha() + 1) + "" + piece.getNum()).equals("Error")
					&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum())).equals(piece.getState())) {
				list.add((char) (piece.getAlpha() + 1) + "" + piece.getNum());
			}
			if (!exists((char) (piece.getAlpha()) + "" + (piece.getNum() - 1)).equals("Error")
					&& !exists((char) (piece.getAlpha()) + "" + (piece.getNum() - 1)).equals(piece.getState())) {
				list.add((char) (piece.getAlpha()) + "" + (piece.getNum() - 1));
			}
			if (!exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 1)).equals("Error")
					&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 1)).equals(piece.getState())) {
				list.add((char) (piece.getAlpha() - 1) + "" + (piece.getNum() - 1));
			}
			if (!exists((char) (piece.getAlpha() - 1) + "" + piece.getNum()).equals("Error")
					&& !exists((char) (piece.getAlpha() - 1) + "" + (piece.getNum())).equals(piece.getState())) {
				list.add((char) (piece.getAlpha() - 1) + "" + piece.getNum());
			}
			if (!exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 1)).equals("Error")
					&& !exists((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 1)).equals(piece.getState())) {
				list.add((char) (piece.getAlpha() + 1) + "" + (piece.getNum() - 1));
			}
			if (piece.getState().equals("Black") && piece.getNum() == 8) {
				if (player2[8].getAlpha() == 'a' && player2[8].getNum() == 8
						&& Math.abs(piece.getAlpha() - player2[8].getAlpha()) > 2 && !Moves.contains("BlackRook1")) {
					boolean empty = true;
					for (int i = player2[8].getAlpha() + 1; i < piece.getAlpha(); i++) {
						if (!isEmpty((char) i + "" + 8)) {
							empty = false;
							break;
						}
					}
					if (empty && (piece.getAlpha() - 2) > 97) {
						list.add((char) (piece.getAlpha() - 2) + "" + 8);
					}
				}

				if (player2[15].getAlpha() == 'h' && player2[15].getNum() == 8
						&& Math.abs(piece.getAlpha() - player2[15].getAlpha()) > 2 && !Moves.contains("BlackRook2")) {
					boolean empty = true;
					for (int i = piece.getAlpha() + 1; i < player2[15].getAlpha(); i++) {
						if (!isEmpty((char) i + "" + 8)) {
							empty = false;
							break;
						}
					}
					if (empty && (piece.getAlpha() + 2) < 104) {
						list.add((char) (piece.getAlpha() + 2) + "" + 8);
					}
				}
			}

			if (piece.getState().equals("White") && piece.getNum() == 1) {
				if (player1[8].getAlpha() == 'a' && player1[8].getNum() == 1
						&& Math.abs(piece.getAlpha() - player1[8].getAlpha()) > 2 && !Moves.contains("WhiteRook1")) {
					boolean empty = true;
					for (int i = player1[8].getAlpha() + 1; i < piece.getAlpha(); i++) {
						if (!isEmpty((char) i + "" + 1)) {
							empty = false;
							break;
						}
					}
					if (empty && (piece.getAlpha() - 2) > 97) {
						list.add((char) (piece.getAlpha() - 2) + "" + 1);
					}
				}

				if (player1[15].getAlpha() == 'h' && player1[15].getNum() == 1
						&& Math.abs(piece.getAlpha() - player1[15].getAlpha()) > 2 && !Moves.contains("WhiteRook2")) {
					boolean empty = true;
					for (int i = piece.getAlpha() + 1; i < player1[15].getAlpha(); i++) {
						if (!isEmpty((char) i + "" + 1)) {
							empty = false;
							break;
						}
					}
					if (empty && (piece.getAlpha() + 2) < 104) {
						list.add((char) (piece.getAlpha() + 2) + "" + 1);
					}
				}
			}

			ArrayList<String> toRemove = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				if (Check(piece, list.get(i))) {
					toRemove.add(list.get(i));
				}
			}
			list.removeAll(toRemove);
		}
		return list;
	}

	public String exists(String pos) {
		char c = pos.charAt(0);
		int n = Integer.parseInt(pos.substring(1));
		if (c > 104 || n > 8 || c < 97 || n < 1) {
			return "Error";
		}

		for (int i = 0; i < 16; i++) {
			if (player1[i].getAlpha() == c && player1[i].getNum() == n) {
				return "White";
			}

			if (player2[i].getAlpha() == c && player2[i].getNum() == n) {
				return "Black";
			}
		}

		return "Null";
	}

	public String getPosition(Piece piece) {
		return piece.getAlpha() + "" + piece.getNum();
	}

	public boolean Check(Piece piece, String pos) {
		if (piece.getState().equals("White")) {
			if (p2Moves.contains(pos)) {
				return true;
			}
		} else if (piece.getState().equals("Black")) {
			if (p1Moves.contains(pos)) {
				return true;
			}
		}

		return false;
	}

	public void promote(Piece piece, String value) {
		Moves += "Promotion: " + getPosition(piece) + "-> " + piece.getState() + piece.getValue() + " to "
				+ piece.getState() + value + "\n";
		piece.setValue(value);
		promoteEvent = 0;
		try {
			if (piece.getState().equals("Black")) {
				if (value.equals("Queen")) {
					Image img = new Image("/images/bq.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				} else if (value.equals("Rook")) {
					Image img = new Image("/images/br.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				} else if (value.equals("Bishop")) {
					Image img = new Image("/images/bb.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				} else if (value.equals("Knight")) {
					Image img = new Image("/images/bh.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				}
			} else if (piece.getState().equals("White")) {
				if (value.equals("Queen")) {
					Image img = new Image("/images/wq.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				} else if (value.equals("Rook")) {
					Image img = new Image("/images/wr.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				} else if (value.equals("Bishop")) {
					Image img = new Image("/images/wb.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				} else if (value.equals("Knight")) {
					Image img = new Image("/images/wh.png");
					piece.getRectangle().setFill(new ImagePattern(img));
				}
			}
		} catch (Exception e) {
			// Do Nothing, just for testing
		}
	}

	public void Register(Piece piece, String from, String to) {
		Moves += piece.getState() + piece.getValue() + ": " + from + "->" + to + "\n";
	}

	// Returns true when tile is empty
	public boolean isEmpty(String pos) {
		for (int i = 0; i < 16; i++) {
			if (player1[i].getAlpha() == pos.charAt(0) && player1[i].getNum() == Integer.parseInt(pos.substring(1))) {
				return false;
			}
			if (player2[i].getAlpha() == pos.charAt(0) && player2[i].getNum() == Integer.parseInt(pos.substring(1))) {
				return false;
			}
		}
		return true;
	}

	// Casting
	public void CastRook(Piece current, char c) {
		if (current.getValue().equals("King") && current.getState().equals("Black")) {
			if (current.getAlpha() - c == 2) {
				player2[8].setAplha((char) (current.getAlpha() - 1));
			} else if (current.getAlpha() - c == -2) {
				player2[15].setAplha((char) (current.getAlpha() + 1));
			}
		} else if (current.getValue().equals("King") && current.getState().equals("White")) {
			if (current.getAlpha() - c == 2) {
				player1[8].setAplha((char) (current.getAlpha() - 1));
			} else if (current.getAlpha() - c == -2) {
				player1[15].setAplha((char) (current.getAlpha() + 1));
			}
		}
	}

	public void clearTile(String pos) {
		for (int i = 0; i < 16; i++) {
			if (player1[i].getAlpha() == pos.charAt(0) && Integer.parseInt(pos.substring(1)) == player1[i].getNum()) {
				player1[i].setAplha('\0');
				player1[i].setNum(0);
				player1[i].setState("Null");
			}

			if (player2[i].getAlpha() == pos.charAt(0) && Integer.parseInt(pos.substring(1)) == player2[i].getNum()) {
				player2[i].setAplha('\0');
				player2[i].setNum(0);
				player2[i].setState("Null");
			}
		}
	}

	public void Replace(Piece p1, Piece p2) {
		if (p2.getValue().equals("King") && p2.getState().equals("Black")) {
			Winner = 1;
		} else if (p2.getValue().equals("King") && p2.getState().equals("White")) {
			Winner = 2;
		}
		p1.setAplha(p2.getAlpha());
		p1.setNum(p2.getNum());
		p2.setState("Null");
		p2.setAplha('\0');
		p2.setNum(0);
	}

	public Piece getPiece(String pos) {
		for (int i = 0; i < 16; i++) {
			if (player1[i].getAlpha() == pos.charAt(0) && Integer.parseInt(pos.substring(1)) == player1[i].getNum()) {
				return player1[i];
			}

			if (player2[i].getAlpha() == pos.charAt(0) && Integer.parseInt(pos.substring(1)) == player2[i].getNum()) {
				return player2[i];
			}
		}
		return null;
	}

	public boolean movesPossible(String state) {
		for (int i = 0; i < 16; i++) {
			if (state.equals("White")) {
				if (moveList(player1[i], null).size() != 0) {
					return true;
				}
			}

			if (state.equals("Black")) {
				if (moveList(player2[i], null).size() != 0) {
					return true;
				}
			}
		}
		return false;
	}
}
