package Project3;

/*****************************************************************
 * A Knight piece in a chess game.
 *
 * @author Amela Aganovic
 * @version Winter 2019
 *****************************************************************/
public class Knight extends ChessPiece {

    /** Checks if knight has moved */
    public boolean moved;


    /*****************************************************************
     * Constructor for the knight piece
     *
     * @param player the current player
     *****************************************************************/
    public Knight(Player player) {
        super(player);
    }

    /*****************************************************************
     * Returns the type of chess piece the piece is
     *
     * @return knight
     *****************************************************************/
    public String type() {
        return "Knight";
    }

    public boolean hasMoved() {
        return false;
    }

    public void setHasMoved(boolean setMoved) {
        moved = setMoved;
    }


    /*****************************************************************
     * Determining valid moves for the selected knight piece
     *
     * @param move the move.
     * @param board the chest board.
     * @return true if move is valid, false if not.
     *****************************************************************/
    public boolean isValidMove(Move move, IChessPiece[][] board){

        boolean valid = false;
        if(super.isValidMove(move,board)) {

            // MOVING TWO VERTICALLY ONE HORIZONTALLY
            if (move.toColumn == move.fromColumn + 1 || move.toColumn == move.fromColumn - 1) {
                if (move.toRow == move.fromRow + 2 || move.toRow == move.fromRow - 2) {
                    valid = true;
                }
            }

            // MOVING ONE VERTICALLY TWO HORIZONTALLY
            if (move.toColumn == move.fromColumn + 2 || move.toColumn == move.fromColumn - 2) {
                if (move.toRow == move.fromRow + 1 || move.toRow == move.fromRow - 1) {
                    valid = true;
                }
            }
        }
        return valid;
    }
}
