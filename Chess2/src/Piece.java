import java.util.List;
import javax.swing.ImageIcon;

public interface Piece {
	
    // Returns the color of the piece (White or Black)
    String getColor();

    // Returns the position of the piece on the board (e.g., "01", "34")
    String getPosn();

    // Sets the position of the piece on the board
    void setPosn(String posn);

    // Returns the default starting position of the piece
    String getDefPosn();

    // Returns true if the piece has moved, false otherwise
    boolean getMoved();

    // Marks the piece as having moved
    void setMoved();

    // Returns a list of all possible valid moves for the piece
    List<String> movesPossible(Piece[][] boardPieces, Piece king);

    // Returns the ImageIcon for the piece to be used in GUI representation
    ImageIcon getImageIcon();
}
