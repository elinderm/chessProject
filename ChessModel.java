package Project3;
import java.util.ArrayList;

public class ChessModel implements IChessModel {
    private IChessPiece[][] board; // ask about, probs shouldn't be static
    private Player player;
    private ArrayList<String> deletedPiece;
    private ArrayList<Integer> previousRow;
    private ArrayList<Integer> previousColumn;
    private ArrayList<Player> capturedPlayer;
    private ArrayList<Integer> newRow;
    private ArrayList<Integer> newCol;
    private BoardState boardState;

    // declare other instance variables as needed

    public ChessModel() {
        board = new IChessPiece[8][8];
        player = Player.WHITE;


        boardState = new BoardState(false, false, false, false,
                false, false);

        deletedPiece = new ArrayList<>();
        previousRow = new ArrayList<>();
        previousColumn = new ArrayList<>();
        capturedPlayer = new ArrayList<>();
        newRow = new ArrayList<>();
        newCol = new ArrayList<>();

        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight (Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
        board[6][0] = new Pawn(Player.WHITE, true);
        board[6][1] = new Pawn(Player.WHITE, true);
        board[6][2] = new Pawn(Player.WHITE, true);
        board[6][3] = new Pawn(Player.WHITE, true);
        board[6][4] = new Pawn(Player.WHITE, true);
        board[6][5] = new Pawn(Player.WHITE, true);
        board[6][6] = new Pawn(Player.WHITE, true);
        board[6][7] = new Pawn(Player.WHITE, true);

        board[0][0] = new Rook(Player.BLACK);
        board[0][1] = new Knight(Player.BLACK);
        board[0][2] = new Bishop(Player.BLACK);
        board[0][3] = new Queen(Player.BLACK);
        board[0][4] = new King(Player.BLACK);
        board[0][5] = new Bishop(Player.BLACK);
        board[0][6] = new Knight (Player.BLACK);
        board[0][7] = new Rook(Player.BLACK);
        board[1][0] = new Pawn(Player.BLACK, true);
        board[1][1] = new Pawn(Player.BLACK, true);
        board[1][2] = new Pawn(Player.BLACK, true);
        board[1][3] = new Pawn(Player.BLACK, true);
        board[1][4] = new Pawn(Player.BLACK, true);
        board[1][5] = new Pawn(Player.BLACK, true);
        board[1][6] = new Pawn(Player.BLACK, true);
        board[1][7] = new Pawn(Player.BLACK, true);
    }

    public void newGame(){

        System.out.println("NEW GAME");
        player = Player.WHITE;
        for (int r = 0; r < 8; r++) {
            if(r == 1)
                for (int c = 0; c < 8; c++) {
                    setPiece(r, c, new Pawn(Player.BLACK, true));
                }
            if(r == 6)
                for (int c = 0; c < 8; c++) {
                    setPiece(r, c, new Pawn(Player.WHITE, true));
                }
            if(r < 6 && r >1)
                for (int c = 0; c < 8; c++) {
                    setPiece(r, c, null);
                }
        }
        setPiece(7, 0, new Rook(Player.WHITE));
        setPiece(7, 1, new Knight(Player.WHITE));
        setPiece(7, 2, new Bishop(Player.WHITE));
        setPiece(7, 3, new Queen(Player.WHITE));
        setPiece(7, 4, new King(Player.WHITE));
        setPiece(7, 5, new Bishop(Player.WHITE));
        setPiece(7, 6, new Knight (Player.WHITE));
        setPiece(7, 7, new Rook (Player.WHITE));

        setPiece(0, 0, new Rook(Player.BLACK));
        setPiece(0, 1, new Knight(Player.BLACK));
        setPiece(0, 2, new Bishop(Player.BLACK));
        setPiece(0, 3, new Queen(Player.BLACK));
        setPiece(0, 4, new King(Player.BLACK));
        setPiece(0, 5, new Bishop(Player.BLACK));
        setPiece(0, 6, new Knight (Player.BLACK));
        setPiece(0, 7, new Rook (Player.BLACK));

    }

    public void undo() {
        if (previousRow.isEmpty()) {
            player = Player.WHITE;
            return;
        }
        int index = 0;
        int fromRow = 0;
        int fromCol = 0;
        int toRow = 0;
        int toCol = 0;
        String deleted = "";

        while (index < previousRow.size()-1 && previousRow.get(index) != null)
            index++;

        fromRow = newRow.get(index);
        fromCol = newCol.get(index);
        toRow = previousRow.get(index);
        toCol = previousColumn.get(index);

        if (fromRow == 8) {
            if (fromCol == 4) {
                move(new Move(0,2,0,4));
                move(new Move(0,3,0,0));
                board[0][4].setHasMoved(false);
                board[0][0].setHasMoved(false);
            }
            else {
                move(new Move(0,6,0,4));
                move(new Move(0,5,0,7));
                board[0][4].setHasMoved(false);
                board[0][7].setHasMoved(false);
            }
            deleteMove(index);
        }
        if (fromRow == 14) {
            if (fromCol == 4) {
                move(new Move(7,2,7,4));
                move(new Move(7,3,7,0));
                board[7][4].setHasMoved(false);
                board[7][0].setHasMoved(false);
            }
            else {
                move(new Move(7,6,7,4));
                move (new Move(7,5,7,7));
                board[7][4].setHasMoved(false);
                board[7][7].setHasMoved(false);
            }
            deleteMove(index);
//            setNextPlayer();
            return;
        }


        move(new Move(fromRow, fromCol, toRow, toCol));
        board[fromRow][fromCol] = null;
        setPiece(fromRow, fromCol, null);

        if (board[toRow][toCol] != null && board[toRow][toCol].type().equals("Pawn")) {
            if (toRow == 1)
                setPiece(toRow, toCol, new Pawn(Player.BLACK, true));
            if (toRow == 6)
                setPiece(toRow, toCol, new Pawn(Player.WHITE, true));
        }
        if ((toRow == 7 && toCol == 7) || (toRow == 0 && toCol == 7)
                || (toRow == 0 && toCol == 0) || (toRow == 7 && toCol == 0))
            if (board[toRow][toCol].type().equals("Rook"))
                board[toRow][toCol].setHasMoved(false);

        if ((toRow == 0 && toCol == 4) || (toRow == 7 && toCol == 4))
            if (board[toRow][toCol].type().equals("King"))
                board[toRow][toCol].setHasMoved(false);

        if (deletedPiece.get(index) != null) {
            deleted = deletedPiece.get(index);
            if (deleted.equals("King"))
                setPiece(fromRow, fromCol, new King(capturedPlayer.get(index)));
            if (deleted.equals("Queen"))
                setPiece(fromRow, fromCol, new Queen(capturedPlayer.get(index)));
            if (deleted.equals("Knight"))
                setPiece(fromRow, fromCol, new Knight(capturedPlayer.get(index)));
            if (deleted.equals("Rook"))
                setPiece(fromRow, fromCol, new Rook(capturedPlayer.get(index)));
            if (deleted.equals("Pawn")) {
                if (fromCol == 6 || fromCol == 1)
                    setPiece(fromRow, fromCol, new Pawn(capturedPlayer.get(index), true));
                setPiece(fromRow, fromCol, new Pawn(capturedPlayer.get(index), false));
            }
            if (deleted.equals("Bishop"))
                setPiece(fromRow, fromCol, new Bishop(capturedPlayer.get(index)));
        }
        setNextPlayer();

        deleteMove(index);
    }

    public void saveMove(int row, int col, int nextRow, int nextCol) {
        previousRow.add(row);
        previousColumn.add(col);
        newRow.add(nextRow);
        newCol.add(nextCol);
        if (nextRow < 8 && nextCol < 8 && board[nextRow][nextCol] != null) {
            deletedPiece.add(board[nextRow][nextCol].type());
            capturedPlayer.add(board[nextRow][nextCol].player());
        }
        else {
            deletedPiece.add(null);
            capturedPlayer.add(null);
        }
    }

    private void deleteMove(int index) {
        previousRow.remove(index);
        previousColumn.remove(index);
        newRow.remove(index);
        newCol.remove(index);
        deletedPiece.remove(index);
        capturedPlayer.remove(index);
    }

    private void deleteLastMove() {
        int index = 0;
        while (index < previousRow.size()-1 && previousRow.get(index) != null)
            index++;

        previousRow.remove(index);
        previousColumn.remove(index);
        newRow.remove(index);
        newCol.remove(index);
        deletedPiece.remove(index);
        capturedPlayer.remove(index);
    }

    public boolean isComplete() {
        boolean valid = false;
        if (inCheck(currentPlayer())) {
            valid = true;
        }

        return valid;
    }

    public boolean isValidMove(Move move) {
        boolean valid = false;

     /*IChessPiece fromPiece = board[move.toRow][move.toColumn];
     IChessPiece toPiece = board[move.toRow][move.toColumn];*/

        Move moveBack = new Move(move.toRow, move.toColumn, move.fromRow, move.fromColumn);
        //saveMove(move.fromRow, move.fromColumn, move.toRow, move.toColumn);

        if (board[move.fromRow][move.fromColumn] != null)
            if ((board[move.fromRow][move.fromColumn].player().equals(currentPlayer()))
                    && board[move.fromRow][move.fromColumn].isValidMove(move, board)){

                if (!inCheck(player)) {
                    // move the piece
                    saveMove(move.fromRow, move.fromColumn, move.toRow, move.toColumn);
                    move(move);

                    if (inCheck(player)){
                        // the player cannot move into check
                        valid = false;
                        boardState.setMovingIntoCheck(true);

                        // move piece back
                        move(moveBack);
                        deleteLastMove();

                    }
                    else {
                        valid = true;
                        boardState.setMovingIntoCheck(false);
                    }
                }
                else {
                    // if the player is in check, they must move out of check
                    boardState.setInCheck(true); // FIXME should this go in the inCheck method instead?

                    saveMove(move.fromRow, move.fromColumn, move.toRow, move.toColumn);
                    move(move);

                    if (inCheck(player) == true){
                        // still in check; can't move there

                        // move piece back
                        move(moveBack);
                        deleteLastMove();

                        valid = false;
                    }
                    else {
                        valid = true;
                    }
                }
            }
/*    if (valid == true)
        saveMove(move.fromRow, move.fromColumn, move.toRow, move.toColumn);*/


        return valid;
    }


    public void move(Move move) {
        board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
        board[move.fromRow][move.fromColumn] = null;
    }

    public boolean inCheck(Player p) {
        boolean valid = false;
        int fromRow = 0;
        int fromColumn = 0;
        int toRow = 0;
        int toColumn = 0;

        for (int r = 0; r < numRows(); r++) {
            for (int c = 0; c < numColumns(); c++) {
                if (board[r][c] != null && board[r][c].type().equals("King")
                        && board[r][c].player().equals(p)) {
                    toRow = r;
                    toColumn = c;
                    break;
                }
            }
        }

        for (int r = 0; r < numRows(); r++)
            for (int c = 0; c < numColumns(); c++) {
                if (board[r][c] != null)
                    if (!board[r][c].player().equals(p)) {
                        fromRow = r;
                        fromColumn = c;
                        if (board[r][c].isValidMove(new Move (fromRow, fromColumn, toRow, toColumn), board)) {
                            valid = true;
                        }
                    }
            }

        return valid;
    }


    public void castleKingSide() {
        if (player.equals(Player.WHITE)) {
            if (board[7][7] != null && board[7][4] != null) {
                if (board[7][7].type().equals("Rook") && board[7][4].type().equals("King")) {
                    if (!board[7][7].hasMoved() && !board[7][4].hasMoved()) {
                        if (board[7][5] == null && board[7][6] == null) {
                            if (!canBeAttacked(player, 7, 5) && !canBeAttacked(player, 7, 6)) {
                                saveMove(7,4,14,11);
                                move(new Move(7,4,7,6));
//                                saveMove(7,7,7,5);
                                move(new Move(7,7,7,5));
                                board[7][6].setHasMoved(true);
                                board[7][5].setHasMoved(true);
                            }

                        }
                    }
                }
            }
        }

        if (player.equals(Player.BLACK)) {
            if (board[0][7] != null && board[0][4] != null) {
                if (board[0][7].type().equals("Rook") && board[0][4].type().equals("King")) {
                    if (!board[0][7].hasMoved() && !board[0][4].hasMoved()) {
                        if (board[0][5] == null && board[0][6] == null) {
                            if (!canBeAttacked(player, 7, 5) && !canBeAttacked(player, 7, 6)) {
                                saveMove(0,4,8,11);
                                move(new Move(0,4,0,6));
//                                saveMove(0,7,0,5);
                                move(new Move(0,7,0,5));
                                board[0][5].setHasMoved(true);
                                board[0][6].setHasMoved(true);
                            }

                        }
                    }
                }
            }
        }

    }

    public void castleQueenSide() {
        if (player.equals(Player.WHITE)) {
            if (board[7][0] != null && board[7][4] != null) {
                if (board[7][0].type().equals("Rook") && board[7][4].type().equals("King")) {
                    if (!board[7][0].hasMoved() && !board[7][4].hasMoved()) {
                        if (board[7][1] == null && board[7][2] == null && board[7][3] == null) {
                            if (!canBeAttacked(player, 7, 1) && !canBeAttacked(player, 7, 2)
                                    && !canBeAttacked(player, 7, 3)) {
                                saveMove(7,4, 14,4);
                                move (new Move(7,4,7,2));
//                                saveMove(7,0,7,3);
                                move(new Move(7,0,7,3));
                                board[7][3].setHasMoved(true);
                                board[7][2].setHasMoved(true);
                            }
                        }
                    }
                }
            }
        }

        if (player.equals(Player.BLACK)) {
            if (board[0][0] != null && board[0][4] != null) {
                if (board[0][0].type().equals("Rook") && board[0][4].type().equals("King")) {
                    if (!board[0][0].hasMoved() && !board[0][4].hasMoved()) {
                        if (board[0][1] == null && board[0][2] == null && board[0][3] == null) {
                            if (!canBeAttacked(player, 0, 1) && !canBeAttacked(player, 0, 2)
                                    && !canBeAttacked(player, 0, 3)) {
                                saveMove(0,4, 8,4);
                                move (new Move(0,4,0,2));
//                                saveMove(0,0,0,3);
                                move(new Move(0,0,0,3));
                                board[0][3].setHasMoved(true);
                                board[0][2].setHasMoved(true);
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean canBeAttacked(Player p, int row, int col) {
        int fromRow = 0;
        int fromColumn = 0;
        boolean dangerous = false;

        for (int r = 0; r < numRows(); r++)
            for (int c = 0; c < numColumns(); c++) {
                if (board[r][c] != null)
                    if (board[r][c].player().equals(player.next())) {
                        fromRow = r;
                        fromColumn = c;
                        if (board[r][c].isValidMove(new Move (fromRow, fromColumn, row, col), board))
                            dangerous = true;
                    }
            }

        return dangerous;
    }

    public Player currentPlayer() {
        return player;
    }

    public int numRows() {
        return 8;
    }

    public int numColumns() {
        return 8;
    }

    public IChessPiece pieceAt(int row, int column) {
        return board[row][column];
    }

    public boolean isOccupied(int r, int c) {
        if (board[r][c] != null)
            return true;
        return false;
    }

    public void setNextPlayer() {
        player = player.next();
    }

    public void setPiece(int row, int column, IChessPiece piece) {
        board[row][column] = piece;
    }

    public void AI() {
        /*
         * Write a simple AI set of rules in the following order.
         * a. Check to see if you are in check.
         * 		i. If so, get out of check by moving the king or placing a piece to block the check
         *
         * b. Attempt to put opponent into check (or checkmate).
         * 		i. Attempt to put opponent into check without losing your piece
         *		ii. Perhaps you have won the game.
         *
         *c. Determine if any of your pieces are in danger,
         *		i. Move them if you can.
         *		ii. Attempt to protect that piece.
         *
         *d. Move a piece (pawns first) forward toward opponent king
         *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
         */

    }
}
