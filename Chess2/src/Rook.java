import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

public class Rook implements Piece {
    String Posn, Color, defPosn;  // Position, Color, and Default Position
    boolean moved;  // Flag to check if the rook has moved
    ImageIcon imageIcon;  // ImageIcon for the rook's visual representation

    // Constructor to initialize a Rook piece
    public Rook(String posn, String Color, ImageIcon imageIcon) {
        this.Posn = posn;  // Set the position of the Rook
        defPosn = posn;  // Set the default position of the Rook
        moved = false;  // Initially, the Rook hasn't moved
        this.Color = Color;  // Set the color of the Rook
        Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);  // Scale the image
        this.imageIcon = new ImageIcon(image);  // Set the image of the Rook
    }

    @Override
    public String getColor() {
        return Color;  // Return the color of the Rook
    }

    @Override
    public String getPosn() {
        return Posn;  // Return the position of the Rook
    }

    public ImageIcon getImageIcon() {
        return imageIcon;  // Return the ImageIcon of the Rook
    }

    @Override
    public void setPosn(String string) {
        Posn = string;  // Set the position of the Rook
    }

    @Override
    public String getDefPosn() {
        return defPosn;  // Return the default position of the Rook
    }

    @Override
    public boolean getMoved() {
        return moved;  // Return if the Rook has moved
    }

    @Override
    public void setMoved() {
        moved = true;  // Set the Rook's moved flag to true
    }

    @Override
    public List<String> movesPossible(Piece[][] boardPieces , Piece king) {
        // List to store all the possible moves of the Rook
        List<String> moveStrings = new ArrayList<String>();
        Piece[][] boardPieces2 = Arrays.stream(boardPieces)
                .map((Piece[] row) -> row.clone())
                .toArray((int length) -> new Piece[length][]);
        boardPieces2[getPosn().charAt(0) - '0'][getPosn().charAt(1) - '0'] = null;
        
        // Directions the Rook can move (up, down, left, right)
        int[] x = {0, 0, 1, -1};
        int[] y = {-1, 1, 0, 0};
        
        // Loop through all four directions
        for (int i = 0; i < 4; i++) {
            int a = getPosn().charAt(0) - '0' + x[i];  // Get the next row
            int b = getPosn().charAt(1) - '0' + y[i];  // Get the next column
            
            // Continue moving in the direction while inside bounds of the board
            while (a >= 0 && a < 8 && b >= 0 && b < 8) {
                if (boardPieces[a][b] == null) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(king, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                }
                else if (boardPieces[a][b].getColor() != getColor()) {
                	boardPieces2[a][b]= this;
                	if( !GameLoop.isCheck(king, boardPieces2))
                		moveStrings.add("" + a + b);
                	boardPieces2[a][b]= null;
                	break;  // Stop after capturing the opponent's piece
                } 
                else {
                    break;  // If the square contains a piece of the same color, stop
                }
                a += x[i];  // Move in the x-direction
                b += y[i];  // Move in the y-direction
            }
        }
        
        return moveStrings;  // Return the list of possible moves for the Rook
    }
}
