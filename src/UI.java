
/**
 * @author Manthan
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UI extends Application {

	private double wFactor;
	private double hFactor;
	private Rectangle rec[];
	private Logic logic;
	private String previous;
	private int selected = 0;
	private Piece current;
	private boolean start = false;

	public void run(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, InterruptedException {

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		double width = primScreenBounds.getWidth();
		double height = primScreenBounds.getHeight();
		wFactor = width / 1920;
		hFactor = height / 1040;

		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.setFullScreen(true);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Chess");

		// Pane
		Pane menu = new Pane();
		Pane root = new Pane();

		RadioButton w = new RadioButton();
		w.setText("Play as White");
		w.setLayoutX(900 * wFactor);
		w.setLayoutY(320 * hFactor);
		w.setSelected(true);
		menu.getChildren().add(w);

		RadioButton b = new RadioButton();
		b.setText("Play as Black");
		b.setLayoutX(900 * wFactor);
		b.setLayoutY(360 * hFactor);
		menu.getChildren().add(b);

		RadioButton bw = new RadioButton();
		bw.setText("Play as both Black and White (2 Players)");
		bw.setLayoutX(900 * wFactor);
		bw.setLayoutY(400 * hFactor);
		menu.getChildren().add(bw);

		RadioButton s = new RadioButton();
		s.setText("Watch CPU Simulation");
		s.setLayoutX(900 * wFactor);
		s.setLayoutY(440 * hFactor);
		menu.getChildren().add(s);

		RadioButton f = new RadioButton();
		f.setText("Free Play, No waiting for turns");
		f.setLayoutX(900 * wFactor);
		f.setLayoutY(480 * hFactor);
		menu.getChildren().add(f);

		w.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				w.setSelected(false);
				b.setSelected(false);
				bw.setSelected(false);
				s.setSelected(false);
				f.setSelected(false);
				logic.Mode = 1;
			}
		});

		b.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				w.setSelected(false);
				b.setSelected(false);
				bw.setSelected(false);
				s.setSelected(false);
				f.setSelected(false);
				logic.Mode = 2;
			}
		});

		bw.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				w.setSelected(false);
				b.setSelected(false);
				bw.setSelected(false);
				s.setSelected(false);
				f.setSelected(false);
				logic.Mode = 3;
			}
		});

		s.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				w.setSelected(false);
				b.setSelected(false);
				bw.setSelected(false);
				s.setSelected(false);
				f.setSelected(false);
				logic.Mode = 4;
			}
		});

		f.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				w.setSelected(false);
				b.setSelected(false);
				bw.setSelected(false);
				s.setSelected(false);
				f.setSelected(false);
				logic.Mode = 5;

			}
		});

		// Text
		Text author = new Text();
		author.setText("- Made By Manthan");
		author.setX(width - 130);
		author.setY(1040 * hFactor);
		menu.getChildren().add(author);

		Text win = new Text();
		win.setText("");
		win.setScaleX(2);
		win.setScaleY(2);
		win.setX(940 * wFactor);
		win.setY(80 * hFactor);
		root.getChildren().add(win);

		// Buttons
		Button exit = new Button();
		exit.setText("X");
		exit.setPrefWidth(50);
		exit.setLayoutX(width - 60);
		exit.setLayoutY(15 * hFactor);
		exit.setStyle("-fx-background-color: red; -fx-text-fill: white;");

		exit.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				exit.setStyle("-fx-background-color: black; -fx-text-fill: white;");
			}
		});

		Button play = new Button();
		play.setText("Play");
		play.setPrefWidth(130);
		play.setLayoutX(940 * wFactor);
		play.setLayoutY(540 * hFactor);
		play.setScaleX(2);
		play.setScaleY(2);
		play.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
		menu.getChildren().add(play);
		menu.getChildren().add(exit);

		// Initialize Board
		rec = new Rectangle[72];
		double X = 400 * wFactor;
		double Y = 100 * hFactor;
		for (int i = 0; i < 64; i++) {
			X = X + 110 * wFactor;
			rec[i] = new Rectangle(X, Y, 110 * wFactor, 110 * hFactor);

			if ((i + 1) % 8 == 0) {
				X = 400 * wFactor;
				Y = Y + 110 * hFactor;
			}

			if (((i) / 8) % 2 == 0) {
				if (i % 2 != 0) {
					rec[i].setFill(Color.WHITE);
				} else {
					rec[i].setFill(Color.BROWN);
				}
			} else {
				if (i % 2 == 0) {
					rec[i].setFill(Color.WHITE);
				} else {
					rec[i].setFill(Color.BROWN);
				}
			}
			root.getChildren().add(rec[i]);
		}
		rec[64] = new Rectangle(15000, 100, 110 * wFactor, 110 * hFactor);
		rec[64].setFill(Color.GRAY);
		rec[65] = new Rectangle(15000, 215, 110 * wFactor, 110 * hFactor);
		rec[65].setFill(Color.GRAY);
		rec[66] = new Rectangle(15000, 330, 110 * wFactor, 110 * hFactor);
		rec[66].setFill(Color.GRAY);
		rec[67] = new Rectangle(15000, 445, 110 * wFactor, 110 * hFactor);
		rec[67].setFill(Color.GRAY);
		root.getChildren().add(rec[64]);
		root.getChildren().add(rec[65]);
		root.getChildren().add(rec[66]);
		root.getChildren().add(rec[67]);

		rec[68] = new Rectangle((15000), (100), 110 * wFactor, 110 * hFactor);
		root.getChildren().add(rec[68]);

		rec[69] = new Rectangle((15000), (215), 110 * wFactor, 110 * hFactor);
		root.getChildren().add(rec[69]);

		rec[70] = new Rectangle((15000), (330), 110 * wFactor, 110 * hFactor);
		root.getChildren().add(rec[70]);

		rec[71] = new Rectangle((15000), (445), 110 * wFactor, 110 * hFactor);
		root.getChildren().add(rec[71]);

		rec[68].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (logic.promoteEvent == 1) {
					logic.promote(logic.promotePawn, "Queen");
					logic.promoteEvent = 0;
					logic.promotePawn = null;
					rec[64].setX(15000);
					rec[65].setX(15000);
					rec[66].setX(15000);
					rec[67].setX(15000);
					rec[68].setX(15000);
					rec[69].setX(15000);
					rec[70].setX(15000);
					rec[71].setX(15000);
					Refresh();
					logic.refreshPieces();
				}
			}

		});

		rec[69].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (logic.promoteEvent == 1) {
					logic.promote(logic.promotePawn, "Rook");
					logic.promoteEvent = 0;
					logic.promotePawn = null;
					rec[64].setX(15000);
					rec[65].setX(15000);
					rec[66].setX(15000);
					rec[67].setX(15000);
					rec[68].setX(15000);
					rec[69].setX(15000);
					rec[70].setX(15000);
					rec[71].setX(15000);
					Refresh();
					logic.refreshPieces();
				}
			}

		});

		rec[70].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (logic.promoteEvent == 1) {
					logic.promote(logic.promotePawn, "Bishop");
					logic.promoteEvent = 0;
					logic.promotePawn = null;
					rec[64].setX(15000);
					rec[65].setX(15000);
					rec[66].setX(15000);
					rec[67].setX(15000);
					rec[68].setX(15000);
					rec[69].setX(15000);
					rec[70].setX(15000);
					rec[71].setX(15000);
					Refresh();
					logic.refreshPieces();

				}
			}

		});

		rec[71].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (logic.promoteEvent == 1) {
					logic.promote(logic.promotePawn, "Knight");
					logic.promoteEvent = 0;
					logic.promotePawn = null;
					rec[64].setX(15000);
					rec[65].setX(15000);
					rec[66].setX(15000);
					rec[67].setX(15000);
					rec[68].setX(15000);
					rec[69].setX(15000);
					rec[70].setX(15000);
					rec[71].setX(15000);
					Refresh();
					logic.refreshPieces();
				}
			}

		});

		// Initialize Pieces
		logic = new Logic();
		initializePieces(root);

		exit.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});

		// Controls
		for (int i = 0; i < 16; i++) {
			initializeControls(i);
		}
		for (int i = 0; i < 64; i++) {
			tileControl(i);
		}

		Scene menuScene = new Scene(menu, width, height);
		Scene game = new Scene(root, width, height);
		menuScene.getStylesheets().add("menu.css");

		game.getStylesheets().add("layout.css");

		// Key Press Events
		game.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.A) {
				// logic.player1Move();
				// Refresh();
			}
			if (e.getCode() == KeyCode.S) {
				// logic.player2Move();
				// Refresh();
			}
		});

		play.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setScene(game);
				primaryStage.setFullScreen(true);
				root.getChildren().add(exit);
				author.setFill(Color.WHITE);
				root.getChildren().add(author);
				start = true;
				if (logic.Mode == 2) {
					logic.player1Move();
				}
			}
		});

		play.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				play.setStyle("-fx-background-color: black; -fx-text-fill: white;");
			}
		});

		primaryStage.setScene(menuScene);
		primaryStage.show();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					Refresh();
					if (logic.Winner == 1) {
						win.setText("White Wins");
					} else if (logic.Winner == 2) {
						win.setText("Black Wins");
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}, 0, 500);

		Timer timer2 = new Timer();
		timer2.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					if (logic.Mode == 4 && start) {
						if (logic.Winner == 0) {
							logic.player1Move();
							logic.player2Move();
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}, 0, 1500);

	}

	public double getX(Piece piece) {
		int x = (int) piece.getAlpha() - 96;
		return (400 + x * 110) * wFactor;
	}

	public double getY(Piece piece) {
		int y = piece.getNum() - 1;
		return (100 + y * 110) * hFactor;
	}

	public char getAlpha(Rectangle rec) {
		return (char) ((((rec.getX() / wFactor) - 400) / 110) + 96);
	}

	public int getNum(Rectangle rec) {
		return (int) ((((rec.getY() - 100) / 110) + 1) / hFactor);
	}

	// Gets the rectangle on which the piece is on
	public int getEnclosingRec(Piece piece) {
		int x = piece.getAlpha() - 96;
		int y = piece.getNum() - 1;

		for (int i = 0; i < 64; i++) {
			if (rec[i].getX() >= (400 + x * 110) * wFactor - 10 && rec[i].getX() <= (400 + (x + 1) * 110) * wFactor + 10
					&& rec[i].getY() >= (100 + y * 110) * hFactor - 10
					&& rec[i].getY() <= (100 + (y + 1) * 110) * hFactor + 10) {
				return i;
			}
		}
		return -1;
	}

	// Initializing click events for the pieces
	public void initializeControls(int i) {
		if (logic.player1[i] != null) {
			logic.player1[i].getRectangle().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent t) {
					if (logic.Mode == 1 || logic.Mode == 5 || (current != null && logic.Mode != 3)) {
						if (logic.Winner == 0) {
							if (logic.promoteEvent != 1) {
								if (current != null && !logic.player1[i].getState().equals(current.getState())) {
									if (logic.moveList(current, null).contains(logic.getPosition(logic.player1[i]))) {
										if (previous.equals("Brown")) {
											rec[getEnclosingRec(current)].setFill(Color.BROWN);
										} else {
											rec[getEnclosingRec(current)].setFill(Color.WHITE);
										}
										logic.Register(current, logic.getPosition(current),
												getAlpha(logic.player1[i].getRectangle()) + ""
														+ getNum(logic.player1[i].getRectangle()));
										logic.Replace(current, logic.player1[i]);
										Refresh();
										current = null;
										selected = 0;
										if (logic.Mode == 2 && logic.Winner == 0) {
											logic.player1Move();
										}
									}
								} else {
									if (rec[getEnclosingRec(logic.player1[i])].getFill() == Color.GRAY) {
										if (selected == 1) {

											if (previous.equals("Brown")) {
												rec[getEnclosingRec(logic.player1[i])].setFill(Color.BROWN);
											} else {
												rec[getEnclosingRec(logic.player1[i])].setFill(Color.WHITE);
											}
											selected = 0;
											current = null;
										}
									} else {
										if (selected == 0) {
											if (rec[getEnclosingRec(logic.player1[i])].getFill() == Color.BROWN) {
												previous = "Brown";
											} else {
												previous = "White";
											}
											selected = 1;
											current = logic.player1[i];
											rec[getEnclosingRec(logic.player1[i])].setFill(Color.GRAY);
										}
									}
								}
							}
							logic.refreshPieces();
							if (logic.promoteEvent == 1) {
								rec[64].setX(1500);
								rec[65].setX(1500);
								rec[66].setX(1500);
								rec[67].setX(1500);
								rec[68].setX(1500);
								rec[69].setX(1500);
								rec[70].setX(1500);
								rec[71].setX(1500);
								if (logic.promotePawn.getState().equals("White")) {
									Image img = new Image("/images/wq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/wr.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/wb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/wh.png");
									rec[71].setFill(new ImagePattern(img));
								} else {
									Image img = new Image("/images/bq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/br.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/bb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/bh.png");
									rec[71].setFill(new ImagePattern(img));
								}
								Refresh();
							}
						}
					}
					if (logic.Mode == 3 && (logic.Turn == 0
							|| (logic.Turn == 1 && current != null && current.getState().equals("Black")))) {
						if (logic.Winner == 0) {
							if (logic.promoteEvent != 1) {
								if (current != null && !logic.player1[i].getState().equals(current.getState())) {
									if (logic.moveList(current, null).contains(logic.getPosition(logic.player1[i]))) {
										if (previous.equals("Brown")) {
											rec[getEnclosingRec(current)].setFill(Color.BROWN);
										} else {
											rec[getEnclosingRec(current)].setFill(Color.WHITE);
										}
										logic.Register(current, logic.getPosition(current),
												getAlpha(logic.player1[i].getRectangle()) + ""
														+ getNum(logic.player1[i].getRectangle()));
										logic.Replace(current, logic.player1[i]);
										Refresh();
										current = null;
										selected = 0;
										if (logic.Mode == 2 && logic.Winner == 0) {
											logic.player1Move();
										}
										logic.Turn = 0;
									}
								} else {
									if (rec[getEnclosingRec(logic.player1[i])].getFill() == Color.GRAY) {
										if (selected == 1) {
											if (previous.equals("Brown")) {
												rec[getEnclosingRec(logic.player1[i])].setFill(Color.BROWN);
											} else {
												rec[getEnclosingRec(logic.player1[i])].setFill(Color.WHITE);
											}
											selected = 0;
											current = null;
										}
									} else {
										if (selected == 0) {
											if (rec[getEnclosingRec(logic.player1[i])].getFill() == Color.BROWN) {
												previous = "Brown";
											} else {
												previous = "White";
											}
											selected = 1;
											current = logic.player1[i];
											rec[getEnclosingRec(logic.player1[i])].setFill(Color.GRAY);
										}
									}
								}
							}
							logic.refreshPieces();
							if (logic.promoteEvent == 1) {
								rec[64].setX(1500);
								rec[65].setX(1500);
								rec[66].setX(1500);
								rec[67].setX(1500);
								rec[68].setX(1500);
								rec[69].setX(1500);
								rec[70].setX(1500);
								rec[71].setX(1500);
								if (logic.promotePawn.getState().equals("White")) {
									Image img = new Image("/images/wq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/wr.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/wb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/wh.png");
									rec[71].setFill(new ImagePattern(img));
								} else {
									Image img = new Image("/images/bq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/br.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/bb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/bh.png");
									rec[71].setFill(new ImagePattern(img));
								}
								Refresh();
							}
						}
					}
				}

			});
		}
		if (logic.player2[i] != null) {
			logic.player2[i].getRectangle().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent t) {
					if (logic.Mode == 2 || logic.Mode == 5 || (current != null && logic.Mode != 3)) {
						if (logic.Winner == 0) {
							if (logic.promoteEvent != 1) {
								if (current != null && !logic.player2[i].getState().equals(current.getState())) {
									if (logic.moveList(current, null).contains(logic.getPosition(logic.player2[i]))) {
										if (previous.equals("Brown")) {
											rec[getEnclosingRec(current)].setFill(Color.BROWN);
										} else {
											rec[getEnclosingRec(current)].setFill(Color.WHITE);
										}
										logic.Register(current, logic.getPosition(current),
												getAlpha(logic.player2[i].getRectangle()) + ""
														+ getNum(logic.player2[i].getRectangle()));
										logic.Replace(current, logic.player2[i]);
										Refresh();
										current = null;
										selected = 0;
										if (logic.Mode == 1 && logic.Winner == 0) {
											logic.player2Move();
										}
									}
								} else {
									if (rec[getEnclosingRec(logic.player2[i])].getFill() == Color.GRAY) {
										if (selected == 1) {
											if (previous.equals("Brown")) {
												rec[getEnclosingRec(logic.player2[i])].setFill(Color.BROWN);
											} else {
												rec[getEnclosingRec(logic.player2[i])].setFill(Color.WHITE);
											}
											selected = 0;
											current = null;
										}
									} else {
										if (selected == 0) {
											if (rec[getEnclosingRec(logic.player2[i])].getFill() == Color.BROWN) {
												previous = "Brown";
											} else {
												previous = "White";
											}
											selected = 1;
											current = logic.player2[i];
											rec[getEnclosingRec(logic.player2[i])].setFill(Color.GRAY);
										}
									}
								}
							}
							logic.refreshPieces();
							if (logic.promoteEvent == 1) {
								rec[64].setX(1500);
								rec[65].setX(1500);
								rec[66].setX(1500);
								rec[67].setX(1500);
								rec[68].setX(1500);
								rec[69].setX(1500);
								rec[70].setX(1500);
								rec[71].setX(1500);
								if (logic.promotePawn.getState().equals("White")) {
									Image img = new Image("/images/wq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/wr.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/wb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/wh.png");
									rec[71].setFill(new ImagePattern(img));
								} else {
									Image img = new Image("/images/bq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/br.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/bb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/bh.png");
									rec[71].setFill(new ImagePattern(img));
								}
								Refresh();
							}
						}
					}
					if (logic.Mode == 3 && (logic.Turn == 1
							|| (logic.Turn == 0 && current != null && current.getState().equals("White")))) {
						if (logic.Winner == 0) {
							if (logic.promoteEvent != 1) {
								if (current != null && !logic.player2[i].getState().equals(current.getState())
										&& !isSame(logic.player2[i], current)) {
									if (logic.moveList(current, null).contains(logic.getPosition(logic.player2[i]))) {
										if (previous.equals("Brown")) {
											rec[getEnclosingRec(current)].setFill(Color.BROWN);
										} else {
											rec[getEnclosingRec(current)].setFill(Color.WHITE);
										}
										logic.Register(current, logic.getPosition(current),
												getAlpha(logic.player2[i].getRectangle()) + ""
														+ getNum(logic.player2[i].getRectangle()));
										logic.Replace(current, logic.player2[i]);
										Refresh();
										current = null;
										selected = 0;
										if (logic.Mode == 1 && logic.Winner == 0) {
											logic.player2Move();
										}
										logic.Turn = 1;
									}
								} else {
									if (rec[getEnclosingRec(logic.player2[i])].getFill() == Color.GRAY) {
										if (selected == 1) {
											if (previous.equals("Brown")) {
												rec[getEnclosingRec(logic.player2[i])].setFill(Color.BROWN);
											} else {
												rec[getEnclosingRec(logic.player2[i])].setFill(Color.WHITE);
											}
											selected = 0;
											current = null;
										}
									} else {
										if (selected == 0) {
											if (rec[getEnclosingRec(logic.player2[i])].getFill() == Color.BROWN) {
												previous = "Brown";
											} else {
												previous = "White";
											}
											selected = 1;
											current = logic.player2[i];
											rec[getEnclosingRec(logic.player2[i])].setFill(Color.GRAY);
										}
									}
								}
							}
							logic.refreshPieces();
							if (logic.promoteEvent == 1) {
								rec[64].setX(1500);
								rec[65].setX(1500);
								rec[66].setX(1500);
								rec[67].setX(1500);
								rec[68].setX(1500);
								rec[69].setX(1500);
								rec[70].setX(1500);
								rec[71].setX(1500);
								if (logic.promotePawn.getState().equals("White")) {
									Image img = new Image("/images/wq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/wr.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/wb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/wh.png");
									rec[71].setFill(new ImagePattern(img));
								} else {
									Image img = new Image("/images/bq.png");
									rec[68].setFill(new ImagePattern(img));

									img = new Image("/images/br.png");
									rec[69].setFill(new ImagePattern(img));

									img = new Image("/images/bb.png");
									rec[70].setFill(new ImagePattern(img));

									img = new Image("/images/bh.png");
									rec[71].setFill(new ImagePattern(img));
								}
								Refresh();
							}
						}
					}
				}
			});
		}
	}

	// Initialize pieces with correct image on board
	public void initializePieces(Pane root) {
		for (int i = 0; i < logic.player1.length; i++) {
			logic.player1[i].setRectangle(
					new Rectangle(getX(logic.player1[i]), getY(logic.player1[i]), 110 * wFactor, 110 * hFactor));
			if (logic.player1[i].getValue().equals("Pawn")) {
				Image img = new Image("/images/wp.png");
				logic.player1[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player1[i].getValue().equals("Rook1") || logic.player1[i].getValue().equals("Rook2")) {
				Image img = new Image("/images/wr.png");
				logic.player1[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player1[i].getValue().equals("Knight1") || logic.player1[i].getValue().equals("Knight2")) {
				Image img = new Image("/images/wh.png");
				logic.player1[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player1[i].getValue().equals("Bishop1") || logic.player1[i].getValue().equals("Bishop2")) {
				Image img = new Image("/images/wb.png");
				logic.player1[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player1[i].getValue().equals("Queen")) {
				Image img = new Image("/images/wq.png");
				logic.player1[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player1[i].getValue().equals("King")) {
				Image img = new Image("/images/wk.png");
				logic.player1[i].getRectangle().setFill(new ImagePattern(img));
			}
			root.getChildren().add(logic.player1[i].getRectangle());

			logic.player2[i].setRectangle(
					new Rectangle(getX(logic.player2[i]), getY(logic.player2[i]), 110 * wFactor, 110 * hFactor));
			if (logic.player2[i].getValue().equals("Pawn")) {
				Image img = new Image("/images/bp.png");
				logic.player2[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player2[i].getValue().equals("Rook1") || logic.player2[i].getValue().equals("Rook2")) {
				Image img = new Image("/images/br.png");
				logic.player2[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player2[i].getValue().equals("Knight1") || logic.player2[i].getValue().equals("Knight2")) {
				Image img = new Image("/images/bh.png");
				logic.player2[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player2[i].getValue().equals("Bishop1") || logic.player2[i].getValue().equals("Bishop2")) {
				Image img = new Image("/images/bb.png");
				logic.player2[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player2[i].getValue().equals("Queen")) {
				Image img = new Image("/images/bq.png");
				logic.player2[i].getRectangle().setFill(new ImagePattern(img));
			} else if (logic.player2[i].getValue().equals("King")) {
				Image img = new Image("/images/bk.png");
				logic.player2[i].getRectangle().setFill(new ImagePattern(img));
			}
			root.getChildren().add(logic.player2[i].getRectangle());

		}
	}

	// Click event of a board tile
	public void tileControl(int i) {
		rec[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (logic.Winner == 0) {
					if (current != null) {
						if (logic.moveList(current, null).contains(getAlpha(rec[i]) + "" + getNum(rec[i]))) {
							if (selected == 1) {
								if (previous.equals("Brown")) {
									rec[getEnclosingRec(current)].setFill(Color.BROWN);
								} else {
									rec[getEnclosingRec(current)].setFill(Color.WHITE);
								}
								selected = 0;
								logic.Register(current, logic.getPosition(current),
										getAlpha(rec[i]) + "" + getNum(rec[i]));

								// Casting
								logic.CastRook(current, getAlpha(rec[i]));
								if (!logic.enPassant.equals("")) {
									if (current.getValue().equals("Pawn") && logic.enPassant.substring(1, 3)
											.equals(getAlpha(rec[i]) + "" + getNum(rec[i]))) {
										if (logic.enPassant.charAt(0) == 'W') {
											logic.clearTile(getAlpha(rec[i]) + "" + (getNum(rec[i]) + 1));
										}
										if (logic.enPassant.charAt(0) == 'B') {
											logic.clearTile(getAlpha(rec[i]) + "" + (getNum(rec[i]) - 1));
										}
									}
									logic.enPassant = "";
								}
								if (current.getValue().equals("Pawn")) {
									if (current.getState().equals("Black")
											&& (current.getNum() - getNum(rec[i])) == 2) {
										logic.enPassant = "B" + current.getAlpha() + "" + (current.getNum() - 1);
									}
									if (current.getState().equals("White")
											&& (current.getNum() - getNum(rec[i])) == -2) {
										logic.enPassant = "W" + current.getAlpha() + "" + (current.getNum() + 1);
									}
								}

								current.setAplha(getAlpha(rec[i]));
								current.setNum(getNum(rec[i]));
								current = null;
								Refresh();
							}
							if (logic.Mode == 2 && logic.Winner == 0) {
								logic.player1Move();
							} else if (logic.Mode == 1 && logic.Winner == 0) {
								logic.player2Move();
							}

							if (logic.Mode == 3) {
								if (logic.Turn == 0) {
									logic.Turn = 1;
								} else if (logic.Turn == 1) {
									logic.Turn = 0;
								}
							}
						}
					}
					logic.refreshPieces();

					if (logic.promoteEvent == 1) {
						rec[64].setX(1500);
						rec[65].setX(1500);
						rec[66].setX(1500);
						rec[67].setX(1500);
						rec[68].setX(1500);
						rec[69].setX(1500);
						rec[70].setX(1500);
						rec[71].setX(1500);
						if (logic.promotePawn.getState().equals("White")) {
							Image img = new Image("/images/wq.png");
							rec[68].setFill(new ImagePattern(img));

							img = new Image("/images/wr.png");
							rec[69].setFill(new ImagePattern(img));

							img = new Image("/images/wb.png");
							rec[70].setFill(new ImagePattern(img));

							img = new Image("/images/wh.png");
							rec[71].setFill(new ImagePattern(img));
						} else {
							Image img = new Image("/images/bq.png");
							rec[68].setFill(new ImagePattern(img));

							img = new Image("/images/br.png");
							rec[69].setFill(new ImagePattern(img));

							img = new Image("/images/bb.png");
							rec[70].setFill(new ImagePattern(img));

							img = new Image("/images/bh.png");
							rec[71].setFill(new ImagePattern(img));
						}
						Refresh();
					}
				}
			}
		});
	}

	public void Refresh() {
		for (int i = 0; i < 16; i++) {
			if (logic.player1[i].getState().equals("Null")) {
				logic.player1[i].getRectangle().setX(12000);
				logic.player1[i].getRectangle().setY(12000);
			}
			logic.player1[i].getRectangle().setX(getX(logic.player1[i]));
			logic.player1[i].getRectangle().setY(getY(logic.player1[i]));
		}

		for (int i = 0; i < 16; i++) {
			if (logic.player2[i].getState().equals("Null")) {
				logic.player2[i].getRectangle().setX(12000);
				logic.player2[i].getRectangle().setY(12000);
			}
			logic.player2[i].getRectangle().setX(getX(logic.player2[i]));
			logic.player2[i].getRectangle().setY(getY(logic.player2[i]));
		}
	}

	public boolean isSame(Piece p1, Piece p2) {
		if (p1.getAlpha() == p2.getAlpha() && p1.getNum() == p2.getNum() && p1.getState().equals(p2.getState())
				&& p1.getValue().equals(p2.getValue())) {
			return true;
		}
		return false;
	}

	public Logic getLogic() {
		return this.logic;
	}
}