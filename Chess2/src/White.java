import javax.swing.ImageIcon;

public class White implements Color {
    Piece king;  // Declare a variable to hold the White King

    @Override
    public Piece setPieces(Piece[][] board) {
        // Initialize pawns for White on row 6
        for (int i = 0; i < 8; i++) {
            // Place a pawn at position (6, i) for each column in row 6
            board[6][i] = new Pawn("" + 6 + i, "White", new ImageIcon(ClassLoader.getSystemResource("images//WhitePawn.png")));
        }

        // Initialize the back row pieces for White on row 7 (rank 1 in chess notation)
        // Set the Rooks
        board[7][0] = new Rook("70", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteRook.png")));
        board[7][7] = new Rook("77", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteRook.png")));
        
        // Set the Knights
        board[7][1] = new Knight("71", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteKnight.png")));
        board[7][6] = new Knight("76", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteKnight.png")));
        
        // Set the Bishops
        board[7][2] = new Bishop("72", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteBishop.png")));
        board[7][5] = new Bishop("75", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteBishop.png")));
        
        // Set the Queen
        board[7][3] = new Queen("73", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteQueen.png")));
        
        // Set the King (the king is also assigned to the 'king' variable)
        board[7][4] = king = new King("74", "White", new ImageIcon(ClassLoader.getSystemResource("images//WhiteKing.png")));
        
        // Return the White King piece
        return king;
    }
}
	