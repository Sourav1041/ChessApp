import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

public class Pawn implements Piece {
	String Posn, Color, defPosn; // Position, color, and default position of the pawn
	ImageIcon imageIcon; // Image icon representing the pawn
	boolean moved; // A flag to track if the pawn has moved

	// Constructor to initialize the Pawn object with position, color, and image
	// icon
	public Pawn(String posn, String Color, ImageIcon imageIcon) {
		this.Posn = posn;
		defPosn = posn; // Set the default position
		moved = false; // Initially, the pawn has not moved
		this.Color = Color; // Set the color of the pawn (White or Black)
		Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		this.imageIcon = new ImageIcon(image); // Resize the image to fit the board
	}

	// Returns true if the pawn has moved, false otherwise
	@Override
	public boolean getMoved() {
		return moved;
	}

	// Marks the pawn as having moved
	@Override
	public void setMoved() {
		moved = true;
	}

	// Returns the color of the pawn (White or Black)
	@Override
	public String getColor() {
		return Color;
	}

	// Returns the current position of the pawn (e.g., "01", "34")
	@Override
	public String getPosn() {
		return Posn;
	}

	// Returns the image icon of the pawn
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	// Sets a new position for the pawn
	@Override
	public void setPosn(String string) {
		Posn = string; // Update the pawn's position
	}

	// Returns the default position of the pawn
	@Override
    public String getDefPosn() {
        return defPosn;
    }

	// Calculates and returns a list of possible moves for the pawn
	@Override
    public List<String> movesPossible(Piece[][] boardPieces, Piece king) {
        List<String> moveString = new ArrayList<String>();
        int a = getPosn().charAt(0) - '0';  // Row (x-coordinate)
        int b = getPosn().charAt(1) - '0';  // Column (y-coordinate)
        Piece[][] boardPieces2 = Arrays.stream(boardPieces)
                .map((Piece[] row) -> row.clone())
                .toArray((int length) -> new Piece[length][]);
        boardPieces2[getPosn().charAt(0) - '0'][getPosn().charAt(1) - '0'] = null;
        
        // Logic for White pawn's movement
        if (getColor().equals("White")) {
            // Basic pawn move (one step forward)
            if (getPosn().charAt(0) != '0' && boardPieces[a - 1][b] == null) {
            	boardPieces2[a-1][b] = this;
            	if(!GameLoop.isCheck(king, boardPieces2))
            		moveString.add("" + (a - 1) + getPosn().charAt(1));
            	boardPieces2[a-1][b] = null;

            }
                
            // Double move for first turn (move two steps forward if both squares are empty)
            if (getDefPosn().equals(getPosn()) && boardPieces[a - 1][b] == null && boardPieces[a - 2][b] == null)
            {
            	boardPieces2[a-2][b] = this;
            	if(!GameLoop.isCheck(king, boardPieces2))
            		moveString.add("" + (a - 2) + getPosn().charAt(1));
            	boardPieces2[a-2][b] = null;
            }
            // Capture logic (diagonal movement)
            if (getPosn().charAt(0) != '0') {
                if (getPosn().charAt(1) != '0') {
                    if (b != 0 && boardPieces[a - 1][b - 1] != null && boardPieces[a - 1][b - 1].getColor().equals("Black")) {
                    	boardPieces2[a-1][b-1] = this;
                    	if(!GameLoop.isCheck(king, boardPieces2))
                    		moveString.add("" + (a - 1) + (b-1));
                    	boardPieces2[a-1][b-1] = null;
                    }
                    if (b != 7 && boardPieces[a - 1][b + 1] != null && boardPieces[a - 1][b + 1].getColor().equals("Black")) {
                    	boardPieces2[a-1][b+1] = this;
                    	if(!GameLoop.isCheck(king, boardPieces2))
                    		moveString.add("" + (a - 1) + (b+1));
                    	boardPieces2[a-1][b+1] = null;
                    }
                }
            }
        } 
        else {  // Logic for Black pawn's movement
            // Basic pawn move (one step forward)
            if (a != 7 && boardPieces[a + 1][b] == null){
            	boardPieces2[a + 1][b] = this;
        		if(!GameLoop.isCheck(king, boardPieces2)) 
        			moveString.add("" + (a+ 1) + getPosn().charAt(1));
        		boardPieces2[a + 1][b] = null;
            }
            // Double move for first turn (move two steps forward if both squares are empty)
            if (getDefPosn().equals(getPosn()) && boardPieces[a + 1][b] == null && boardPieces[a + 2][b] == null) {
            	boardPieces2[a + 2][b] = this;
        		if(!GameLoop.isCheck(king, boardPieces2)) 
        			moveString.add("" + (a+ 2) + b);
        		boardPieces2[a + 2][b] = null;
        	
            }
                //moveString.add("" + (getPosn().charAt(0) - '0' + 2) + getPosn().charAt(1));

            // Capture logic (diagonal movement)
            if (getPosn().charAt(0) != '7') {
                if (getPosn().charAt(1) != '0') {
                    if (b != 0 && boardPieces[a + 1][b - 1] != null && boardPieces[a + 1][b - 1].getColor().equals("White")) {
                    	boardPieces2[a + 1][b-1] = this;
                		if(!GameLoop.isCheck(king, boardPieces2)) 
                			moveString.add("" + (a+ 1) + (b-1));
                		boardPieces2[a + 1][b-1] = null;
                	
                    	
                    	//moveString.add("" + (a + 1) + (b - 1));
                    }
                    if (b != 7 && boardPieces[a + 1][b + 1] != null && boardPieces[a + 1][b + 1].getColor().equals("White")) {
                    	boardPieces2[a + 1][b+1] = this;
                		if(!GameLoop.isCheck(king, boardPieces2)) 
                			moveString.add("" + (a+ 1) + (b+1));
                		boardPieces2[a + 1][b+1] = null;
                	
                    	
                    	//moveString.add("" + (a + 1) + (b + 1));
                    }
                }
            }
        }
                
        return moveString;
    }
	
}
