import javax.swing.ImageIcon;

// Black class implements Color interface to set up the black pieces on the chessboard
public class Black implements Color{
    Piece king; // Reference to the black king piece

    @Override
    public Piece setPieces(Piece[][] board) {
        // Set up all the black pieces on the chessboard
        for(int i = 0; i < 8; i++) {
            // Place black pawns on the second row (index 1)
            board[1][i] = new Pawn(""+1+i, "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackPawn.png")));
        }

        // Set up other black pieces on the first row (index 0)
        board[0][0] = new Rook("00", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackRook.png")));
        board[0][1] = new Knight("01", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackKnight.png")));
        board[0][2] = new Bishop("02", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackBishop.png")));
        board[0][3] = new Queen("03", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackQueen.png")));
        board[0][4] = king = new King("04", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackKing.png"))); // Set black king and store reference
        board[0][5] = new Bishop("05", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackBishop.png")));
        board[0][6] = new Knight("06", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackKnight.png")));
        board[0][7] = new Rook("07", "Black", new ImageIcon(ClassLoader.getSystemResource("images//BlackRook.png")));

        return king; // Return the reference to the black king
    }
}
