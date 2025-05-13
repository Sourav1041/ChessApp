import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

public class King implements Piece {
    String Posn, Color, defPosn;
    ImageIcon imageIcon;
    boolean moved;

    // Constructor to initialize the King piece with its position, color, and image
    public King(String posn, String Color, ImageIcon imageIcon) {
        this.Posn = posn;  // Set the current position
        defPosn = posn;    // Set the default position
        moved = false;     // Initially, the King has not moved
        this.Color = Color; // Set the color of the King (White or Black)
        Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT); // Resize the image to 50x50 pixels
        this.imageIcon = new ImageIcon(image); // Set the image for the King piece
    }

    // Returns whether the King has moved or not
    @Override
    public boolean getMoved() {
        return moved;
    }

    // Marks the King as having moved
    @Override
    public void setMoved() {
        moved = true;
    }

    // Returns the color of the King (White or Black)
    @Override
    public String getColor() {
        return Color;
    }

    // Returns the current position of the King
    @Override
    public String getPosn() {
        return Posn;
    }

    // Returns the image icon of the King to display in the GUI
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    // Sets the current position of the King
    @Override
    public void setPosn(String string) {
        Posn = string;
    }

    // Returns the default position of the King
    @Override
    public String getDefPosn() {
        return defPosn;
    }

    // Calculates all possible valid moves for the King
    @Override
    public List<String> movesPossible(Piece[][] boardPieces, Piece king) {
        List<String> moveStrings = new ArrayList<String>();  // List to store possible moves
        Piece[][] boardPieces2 = Arrays.stream(boardPieces)
                .map((Piece[] row) -> row.clone())
                .toArray((int length) -> new Piece[length][]);
        boardPieces2[getPosn().charAt(0) - '0'][getPosn().charAt(1) - '0'] = null;
        String posnx = getPosn();
        // Possible directions the King can move (8 possible directions)
        int x[] = {1, 1, 1, 0, 0, -1, -1, -1};
        int y[] = {-1, 1, 0, -1, 1, 1, -1, 0};

        // Check all 8 possible moves for the King
        for (int i = 0; i < 8; i++) {
            int a = posnx.charAt(0) - '0' + x[i];  // Calculate the new row
            int b = posnx.charAt(1) - '0' + y[i];  // Calculate the new column
            setPosn("" +a+b);
            // Check if the new position is within bounds
            if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                // If the new position is empty or occupied by an opponent's piece
                if (boardPieces[a][b] == null) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(this, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                } 
                else if (boardPieces[a][b].getColor() != getColor()) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(this, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                	
                }
            }
        }

        // Special move for castling if the King hasn't moved
        if (!getMoved()) {
            int a = posnx.charAt(0) - '0';

            // Check for left-side castling (Rook and empty squares check)
            if (boardPieces[a][0] != null && !boardPieces[a][0].getMoved() && boardPieces[a][2] == null 
            		&& boardPieces[a][3] == null && boardPieces[a][1] == null) {
                moveStrings.add("" + a + 2);  // Add the castling move
            }

            // Check for right-side castling (Rook and empty squares check)
            if (boardPieces[a][7] != null && !boardPieces[a][7].getMoved() && boardPieces[a][5] == null && boardPieces[a][6] == null) {
                moveStrings.add("" + a + 6);  // Add the castling move
            }
        }
        setPosn(posnx);
        return moveStrings;  // Return the list of all possible moves for the King
    }
}
