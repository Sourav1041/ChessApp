import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

public class Knight implements Piece {

    String Posn, Color, defPosn;  // Position, color, and default position of the knight
    boolean moved;                // A flag to track if the knight has moved
    ImageIcon imageIcon;          // Image icon representing the knight

    // Constructor to initialize the Knight object with position, color, and image icon
    public Knight(String posn, String Color, ImageIcon imageIcon) {
        this.Posn = posn;
        defPosn = posn;           // Set the default position
        moved = false;            // Initially, the knight has not moved
        this.Color = Color;       // Set the color of the knight (White or Black)
        Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        this.imageIcon = new ImageIcon(image);  // Resize the image to fit the board
    }

    // Returns true if the knight has moved, false otherwise
    @Override
    public boolean getMoved() {
        return moved;
    }

    // Marks the knight as having moved
    @Override
    public void setMoved() {
        moved = true;
    }

    // Returns the color of the knight (White or Black)
    @Override
    public String getColor() {
        return Color;
    }

    // Returns the current position of the knight (e.g., "01", "34")
    @Override
    public String getPosn() {
        return Posn;
    }

    // Returns the image icon of the knight
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    // Sets a new position for the knight
    @Override
    public void setPosn(String string) {
        Posn = string;  // Update the knight's position
    }

    // Returns the default position of the knight
    @Override
    public String getDefPosn() {
        return defPosn;
    }

    // Calculates and returns a list of possible moves for the knight
    @Override
    public List<String> movesPossible(Piece[][] boardPieces, Piece king) {
        List<String> moveStrings = new ArrayList<String>();
        Piece[][] boardPieces2 = Arrays.stream(boardPieces)
                .map((Piece[] row) -> row.clone())
                .toArray((int length) -> new Piece[length][]);
        boardPieces2[getPosn().charAt(0) - '0'][getPosn().charAt(1) - '0'] = null;
        
        // Possible moves for a knight: 8 directions in an "L" shape
        int[] x = {1, 1, -1, -1, 2, 2, -2, -2};
        int[] y = {2, -2, 2, -2, 1, -1, 1, -1};

        // Loop through all 8 possible directions
        for (int i = 0; i < 8; i++) {
            int a = getPosn().charAt(0) - '0' + x[i];  // Calculate the new row (x-coordinate)
            int b = getPosn().charAt(1) - '0' + y[i];  // Calculate the new column (y-coordinate)

            // Check if the new position is within bounds
            if (a >= 0 && a < 8 && b >= 0 && b < 8) {
                // If the target position is empty, add it to the list of possible moves
                if (boardPieces[a][b] == null) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(king, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                }
                // If the target position contains a piece of the opposite color, capture it
                else if (boardPieces[a][b].getColor() != getColor()) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(king, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                	break;
                }
            }
        }
        return moveStrings;  // Return the list of all possible valid moves for the knight
    }
}
