import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

// Bishop class implements Piece interface and represents a Bishop piece in the game
public class Bishop implements Piece{
    
    String Posn, Color, defPosn; // Position, Color, and default position of the piece
    boolean moved; // Tracks if the piece has moved
    ImageIcon imageIcon; // Holds the image icon for the piece
    
    // Constructor to initialize the Bishop piece
    public Bishop(String posn, String Color, ImageIcon imageIcon){
        this.Posn = posn; // Set the current position
        defPosn = posn; // Set the default position
        moved = false; // Initially, the piece has not moved
        this.Color = Color; // Set the color of the piece
        // Scale the image to fit the piece size
        Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        this.imageIcon = new ImageIcon(image); // Assign the scaled image
    }
    
    @Override
    public String getColor() {
        return Color; // Return the color of the Bishop
    }
    
    @Override
    public String getPosn() {
        return Posn; // Return the current position of the Bishop
    }
    
    public ImageIcon getImageIcon() {
        return imageIcon; // Return the image icon for the Bishop
    }

    @Override
    public void setPosn(String string) {
        Posn = string; // Set the position of the Bishop
    }

    @Override
    public String getDefPosn() {
        return defPosn; // Return the default position of the Bishop
    }

    @Override
    public boolean getMoved() {
        return moved; // Return if the Bishop has moved
    }
    
    @Override
    public void setMoved() {
        moved = true; // Set the moved status to true
    }

    @Override
    public List<String> movesPossible(Piece[][] boardPieces, Piece king) {
        // Calculate the possible moves for the Bishop
        int[] x = {1, -1, 1, -1}; // Directions in x-axis (top-right, bottom-left, top-left, bottom-right)
        int[] y = {-1, 1, 1, -1}; // Directions in y-axis (top-right, bottom-left, top-left, bottom-right)
        List<String> moveStrings = new ArrayList<String>(); // List to store possible moves
        Piece[][] boardPieces2 = Arrays.stream(boardPieces)
                .map((Piece[] row) -> row.clone())
                .toArray((int length) -> new Piece[length][]);
        boardPieces2[getPosn().charAt(0) - '0'][getPosn().charAt(0) - '0'] = null;
        // Iterate through all four diagonal directions
        for (int i = 0; i < 4; i++) {
            int a = getPosn().charAt(0) - '0' + x[i]; // Get the new row index
            int b = getPosn().charAt(1) - '0' + y[i]; // Get the new column index
            
            // Check the direction until we reach the end of the board or an obstacle
            while (a >= 0 && a < 8 && b >= 0 && b < 8) {
                // If the space is empty, it's a valid move
                if (boardPieces[a][b] == null) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(king, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                }
                // If the space is occupied by an opponent's piece, it's a valid move
                else if (boardPieces[a][b].getColor() != getColor()) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(king, boardPieces2))
                		moveStrings.add("" + a + b);
                		boardPieces2[a][b]= null;
                    break; // Stop further movement in this direction
                }
                // If the space is occupied by a friendly piece, stop moving
                else {
                    break;
                }
                // Continue moving diagonally in the current direction
                a += x[i];
                b += y[i];
            }
        }
        return moveStrings; // Return the list of valid moves for the Bishop
    }
}
