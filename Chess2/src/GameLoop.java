import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameLoop extends JFrame implements ActionListener{
	
	static Piece[][] boardPieces = new Piece[8][8]; // 8x8 array to store all the pieces
	static boolean pieceSelected = false; // Tracks whether a piece is selected
	Piece blackKing; // To store black king piece
	Piece whiteKing; // To store white king piece
	static String turn; // Keeps track of whose turn it is ("White" or "Black")
	static Piece selectedPiece; // Stores the selected piece
	static List<String> posnOfSelectedPieceList = new ArrayList<String>(); // List to store possible moves of the selected piece
	static Black black = new Black(); // Object to manage black pieces
	static White white = new White(); // Object to manage white pieces
	JPanel[][] boardJPanels = new JPanel[8][8]; // JPanel for each board square
	
	GameLoop() {
		setBoard(); // Set up the initial board pieces
		if(blackKing == null || whiteKing == null) { // Check if both kings are present
			System.out.println("Both King not present");
			System.out.println("Exiting game");
			return;
		}
		this.setSize(620, 640); // Set window size
		// Initialize all board squares with JPanels
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				boardJPanels[i][j] = new JPanel();
				boardJPanels[i][j].setLayout(null);
				boardJPanels[i][j].setBounds(j * 75, i * 75, 75, 75); // Set bounds for each JPanel
				boardJPanels[i][j].setName("" + i + j); // Set name of each JPanel for easy identification
				if ((i + j) % 2 == 0) boardJPanels[i][j].setBackground(Color.WHITE); // Set white color for even squares
				else boardJPanels[i][j].setBackground(Color.GREEN); // Set green color for odd squares
				final int x = i;
				final int y = j;
				// Mouse listener to detect when a square is clicked
				boardJPanels[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(boardPieces[x][y] != null) {	
							// If a piece is already selected, check if the destination is valid
							if(pieceSelected) {
								if(posnOfSelectedPieceList.contains(""+x + y)) {
									movePiece(selectedPiece, "" + x + y); // Move piece if valid move
									posnOfSelectedPieceList.clear(); // Clear possible moves list
									loop(); // Refresh board
									return;
								}
							}
							// If the selected piece's color matches the turn's color
							if(boardPieces[x][y].getColor() != turn) return;
							pieceSelected = true; // Mark piece as selected
							selectedPiece = boardPieces[x][y]; // Set selected piece
							posnOfSelectedPieceList = selectedPiece.movesPossible(boardPieces, turn.equalsIgnoreCase("black") ? blackKing : whiteKing ); // Get possible moves for the selected piece
							loop(); // Refresh board
							System.out.println("Piece Selected " + selectedPiece + " " + posnOfSelectedPieceList.size());
						}
						else { // If no piece is selected, check if the destination is valid
							if(posnOfSelectedPieceList.contains(""+x + y)) movePiece(selectedPiece, "" + x + y);
							pieceSelected = false; // Deselect piece
							posnOfSelectedPieceList.clear(); // Clear possible moves list
							loop(); // Refresh board
							System.out.println("Piece not Selected " + posnOfSelectedPieceList.size());
						}
					}

					// Method to move the selected piece to a new position
					private void movePiece(Piece pieceSelected, String string) {
						String last = pieceSelected.getPosn(); // Store the current position
						if(pieceSelected.getClass() == King.class && !pieceSelected.getMoved()) { // Check if it's a King and has not moved
							// Castling logic
							if(string.charAt(1) == '2') { // Left castling
								System.out.println("Castel");
								int a = string.charAt(0) - '0';
								int b = 2;
								boardPieces[a][3] = boardPieces[a][0];
								boardPieces[a][0] = null;
								boardPieces[a][2] = pieceSelected;
								boardPieces[a][4] = null;
								pieceSelected.setPosn(""+a + 2);
								boardPieces[a][b + 1].setPosn(""+a + 3);
								boardPieces[a][b + 1].setMoved();
							}
							else if(string.charAt(1) == '6') { // Right castling
								System.out.println("Castel");
								int a = string.charAt(0) - '0';
								int b = 6;
								boardPieces[a][5] = boardPieces[a][7];
								boardPieces[a][7] = null;
								boardPieces[a][6] = pieceSelected;
								boardPieces[a][4] = null;
								pieceSelected.setPosn(""+a + 6);
								boardPieces[a][b - 1].setPosn(""+a + 5);
								boardPieces[a][b - 1].setMoved();
							}
							else {
								boardPieces[pieceSelected.getPosn().charAt(0) - '0'][pieceSelected.getPosn().charAt(1) - '0'] = null;
								boardPieces[string.charAt(0) - '0'][string.charAt(1) - '0'] = pieceSelected;
								pieceSelected.setPosn(string); // Set new position of the piece
							
							}
						}
						else { // Regular move
							boardPieces[pieceSelected.getPosn().charAt(0) - '0'][pieceSelected.getPosn().charAt(1) - '0'] = null;
							boardPieces[string.charAt(0) - '0'][string.charAt(1) - '0'] = pieceSelected;
							pieceSelected.setPosn(string); // Set new position of the piece
						}
						// Check if the move puts the current player's king in check
						if(isCheck(turn.equalsIgnoreCase("White") ? whiteKing : blackKing)) {
							System.out.println("Invalid move");
							boardPieces[last.charAt(0) - '0'][last.charAt(1) - '0'] = pieceSelected;
							boardPieces[string.charAt(0) - '0'][string.charAt(1) - '0'] = null;
							pieceSelected.setPosn(last); // Revert move
							return;
						}
						if(!selectedPiece.getMoved()) selectedPiece.setMoved(); // Mark piece as moved
						if(turn.equalsIgnoreCase("White")) {
							turn = "Black"; // Switch turn
						}
						else turn = "White";
						if(gameover(turn)) {
							endDialogue(turn);
							System.out.println("Game Over " + turn);
						}
						System.out.println(turn);
						
					}
				});
				this.add(boardJPanels[i][j]); // Add the JPanel for each square
			}
		}
		loop(); // Call loop to refresh the board
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(100, 100);
		this.setTitle("Chess");
		this.setVisible(true);
	}
	
	void endDialogue(String turn2) {
	    JFrame endFrame = new JFrame("Game Over");
	    endFrame.setBounds(500, 300, 500, 200);
	    endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    endFrame.setLayout(new BorderLayout());
	    
	    String winner = turn2.equalsIgnoreCase("black") ? "White" : "Black";
	    JLabel endLabel = new JLabel("Game Over - won by " + winner);
	    endLabel.setFont(new Font("Serif", Font.BOLD, 24));
	    endLabel.setHorizontalAlignment(JLabel.CENTER);
	    endFrame.add(endLabel, BorderLayout.CENTER);
	    
	   
	    endFrame.setLocationRelativeTo(null);  // Center on screen
	    endFrame.setVisible(true);
	}

	// Method to refresh the board after every move or change
	void loop() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				boardJPanels[i][j].removeAll(); // Clear the panel
				if((i + j) % 2 == 0) boardJPanels[i][j].setBackground(Color.WHITE); // Set background color based on square
				else boardJPanels[i][j].setBackground(Color.GREEN);
				if(boardPieces[i][j] == null) System.out.print("Nu ");
				else {
					if(boardPieces[i][j].getClass() == King.class) System.out.print("ki ");
					if(boardPieces[i][j].getClass() == Rook.class) System.out.print("ro ");
					if(boardPieces[i][j].getClass() == Queen.class) System.out.print("Qu ");
					if(boardPieces[i][j].getClass() == Bishop.class) System.out.print("Bi ");
					if(boardPieces[i][j].getClass() == Pawn.class) System.out.print("Pw ");
					if(boardPieces[i][j].getClass() == Knight.class) System.out.print("kn ");
				}
				if(boardPieces[i][j] != null) { // If there's a piece, add it to the square
					JLabel imageJLabel = new JLabel(boardPieces[i][j].getImageIcon());
					imageJLabel.setBounds(10, 10, 50, 50);
					boardJPanels[i][j].add(imageJLabel);
				}
				// Highlight possible moves for the selected piece
				if(posnOfSelectedPieceList.contains("" + i + j)) {
					ImageIcon framIcon = new ImageIcon(ClassLoader.getSystemResource("images//Frames.jpeg"));
					Image image = framIcon.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT);
					ImageIcon finalFrameIcon = new ImageIcon(image); 
					JLabel pointJLabel = new JLabel(finalFrameIcon);
					pointJLabel.setBounds(0, 0, 75, 75);
					pointJLabel.setBackground(Color.BLUE);
					boardJPanels[i][j].add(pointJLabel);
				}
				boardJPanels[i][j].revalidate();
				boardJPanels[i][j].repaint(); // Revalidate and repaint the panel
			}
			System.out.println();
		}
	}
	
	// Method to check if the king is in check
	static boolean isCheck(Piece king) {
		int[] x = {1, 1, -1, -1, -2, 2, -2, 2, 1, -1, 0, 0, 1, -1, 1, -1};
		int[] y = {-2, 2, -2, 2, 1, 1, -1, -1, 0, 0, 1, -1, 1, 1, -1, -1};
		// Check for Knight attacks
		for(int i = 0; i < 8; i++) {
			int a = king.getPosn().charAt(0) - '0' + x[i];
			int b = king.getPosn().charAt(1) - '0' + y[i];
			if(a >= 0 && a < 8 && b >= 0 && b < 8) {
				if(boardPieces[a][b] != null && 
						boardPieces[a][b].getClass() == Knight.class && 
						boardPieces[a][b].getColor() != king.getColor()) {
					return true;
				}
			}
		}
		// Check for Rook/Queen linear attacks
		for(int i = 8; i < 12; i++) {
			int a = king.getPosn().charAt(0) - '0' + x[i];
			int b = king.getPosn().charAt(1) - '0' + y[i];
			while(a >= 0 && a < 8 && b >= 0 && b < 8) {
				if(boardPieces[a][b] == null) {
					a = a + x[i];
					b = b + y[i];
				} else {
					if((boardPieces[a][b].getClass() == Queen.class || 
							boardPieces[a][b].getClass() == Rook.class) && 
							boardPieces[a][b].getColor() != king.getColor()) {
						return true;
					} else break;
				}
			}
		}
		
		// Check for Bishop/Queen diagonal attacks
		for(int i = 12; i < 16; i++) {
			int a = king.getPosn().charAt(0) - '0' + x[i];
			int b = king.getPosn().charAt(1) - '0' + y[i];
			while(a >= 0 && a < 8 && b >= 0 && b < 8) {
				if(boardPieces[a][b] == null) {
					a = a + x[i];
					b = b + y[i];
				} else {
					if((boardPieces[a][b].getClass() == Queen.class || 
							boardPieces[a][b].getClass() == Bishop.class) && 
							boardPieces[a][b].getColor() != king.getColor()) {
						return true;
					} else break;
				}
			}
		}
		// Check for Pawn attacks (specific to Black/White)
		int a = king.getPosn().charAt(0) - '0';
	    int b = king.getPosn().charAt(1) - '0';

	    if (king.getColor().equalsIgnoreCase("Black")) {
	        // Check for white pawn attacks on black king
	        if (a > 0) {
	            if (b > 0 && boardPieces[a - 1][b - 1] != null && 
	                boardPieces[a - 1][b - 1].getClass() == Pawn.class && 
	                boardPieces[a - 1][b - 1].getColor().equals("White")) {
	                return true;
	            }
	            if (b < 7 && boardPieces[a - 1][b + 1] != null && 
	                boardPieces[a - 1][b + 1].getClass() == Pawn.class && 
	                boardPieces[a - 1][b + 1].getColor().equals("White")) {
	                return true;
	            }
	        }
	    } else { // White king
	        // Check for black pawn attacks on white king
	        if (a < 7) {
	            if (b > 0 && boardPieces[a + 1][b - 1] != null && 
	                boardPieces[a + 1][b - 1].getClass() == Pawn.class && 
	                boardPieces[a + 1][b - 1].getColor().equals("Black")) {
	                return true;
	            }
	            if (b < 7 && boardPieces[a + 1][b + 1] != null && 
	                boardPieces[a + 1][b + 1].getClass() == Pawn.class && 
	                boardPieces[a + 1][b + 1].getColor().equals("Black")) {
	                return true;
	            }
	        }
	    }
		return false; // No check detected
	}
	static boolean isCheck(Piece king, Piece[][] boardPieces) {
		int[] x = {1, 1, -1, -1, -2, 2, -2, 2, 1, -1, 0, 0, 1, -1, 1, -1};
	    int[] y = {-2, 2, -2, 2, 1, 1, -1, -1, 0, 0, 1, -1, 1, 1, -1, -1};
	    // Check for Knight attacks
		for(int i = 0; i < 8; i++) {
			int a = king.getPosn().charAt(0) - '0' + x[i];
			int b = king.getPosn().charAt(1) - '0' + y[i];
			if(a >= 0 && a < 8 && b >= 0 && b < 8) {
				if(boardPieces[a][b] != null && 
						boardPieces[a][b].getClass() == Knight.class && 
						boardPieces[a][b].getColor() != king.getColor()) {
					return true;
				}
			}
		}
		// Check for Rook/Queen linear attacks
		for(int i = 8; i < 12; i++) {
			int a = king.getPosn().charAt(0) - '0' + x[i];
			int b = king.getPosn().charAt(1) - '0' + y[i];
			while(a >= 0 && a < 8 && b >= 0 && b < 8) {
				if(boardPieces[a][b] == null) {
					a = a + x[i];
					b = b + y[i];
				} else {
					if((boardPieces[a][b].getClass() == Queen.class || 
							boardPieces[a][b].getClass() == Rook.class) && 
							boardPieces[a][b].getColor() != king.getColor()) {
						return true;
					} else break;
				}
			}
		}
		
		// Check for Bishop/Queen diagonal attacks
		for(int i = 12; i < 16; i++) {
			int a = king.getPosn().charAt(0) - '0' + x[i];
			int b = king.getPosn().charAt(1) - '0' + y[i];
			while(a >= 0 && a < 8 && b >= 0 && b < 8) {
				if(boardPieces[a][b] == null) {
					a = a + x[i];
					b = b + y[i];
				} else {
					if((boardPieces[a][b].getClass() == Queen.class || 
							boardPieces[a][b].getClass() == Bishop.class) && 
							boardPieces[a][b].getColor() != king.getColor()) {
						return true;
					} else break;
				}
			}
		}
		// Check for Pawn attacks (specific to Black/White)
		int a = king.getPosn().charAt(0) - '0';
	    int b = king.getPosn().charAt(1) - '0';

	    if (king.getColor().equalsIgnoreCase("Black")) {
	        // Check for white pawn attacks on black king
	        if (a > 0) {
	            if (b > 0 && boardPieces[a - 1][b - 1] != null && 
	                boardPieces[a - 1][b - 1].getClass() == Pawn.class && 
	                boardPieces[a - 1][b - 1].getColor().equals("White")) {
	                return true;
	            }
	            if (b < 7 && boardPieces[a - 1][b + 1] != null && 
	                boardPieces[a - 1][b + 1].getClass() == Pawn.class && 
	                boardPieces[a - 1][b + 1].getColor().equals("White")) {
	                return true;
	            }
	        }
	    } else { // White king
	        // Check for black pawn attacks on white king
	        if (a < 7) {
	            if (b > 0 && boardPieces[a + 1][b - 1] != null && 
	                boardPieces[a + 1][b - 1].getClass() == Pawn.class && 
	                boardPieces[a + 1][b - 1].getColor().equals("Black")) {
	                return true;
	            }
	            if (b < 7 && boardPieces[a + 1][b + 1] != null && 
	                boardPieces[a + 1][b + 1].getClass() == Pawn.class && 
	                boardPieces[a + 1][b + 1].getColor().equals("Black")) {
	                return true;
	            }
	        }
	    }
		return false; // No check detected
	}
	
	boolean gameover(String color) {
		for(int i=0; i< 8; i++) {
			for(int j=0;j<8;j++ ) {
				if(boardPieces[i][j] ==null) continue;
				if(boardPieces[i][j].getColor().equalsIgnoreCase(color) && 
						boardPieces[i][j].movesPossible(boardPieces, color.equalsIgnoreCase("Black") ? blackKing: whiteKing).size() !=0) {
					return false;
				}
			}
					
		}
		return true;
	}
	// Method to set up the board
	void setBoard() {
		// Set pieces for black and white using their respective setPieces methods
		blackKing = black.setPieces(boardPieces);
		whiteKing = white.setPieces(boardPieces);
		turn = "White"; // White goes first
	}
	
	// Main method to start the game
	public static void main(String[] args) {
		new GameLoop(); // Create a new GameLoop object to start the game
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Empty method implementation as it's not being used
	}
}