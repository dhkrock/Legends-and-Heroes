public class Board {
    public Tile[][] board;
    public int dim;
    public int dim2;

    public Board() {
        this.board = new Tile[8][8];
        this.dim = 8;
        this.dim2 = 8;
    }
    public Board(int dim) {
        this.board = new Tile[dim][dim];
        this.dim = dim;
        this.dim2 = dim;
    }

    public Board(int dim1, int dim2) {
        this.board = new Tile[dim1][dim2];
        this.dim = dim1;
        this.dim2 = dim2;
    }

    // method to change icon of tile for when party is on the move
    public void changeIcon(int x, int y, int prev_x, int prev_y) {
        board[y][x].setTile('P');
        board[prev_y][prev_x].setTile(board[prev_y][prev_x].prev_tile);
    }

    // method for when checking what the tile was before being replaced by 'P'
    public char prevIcon(int x, int y) {
        return board[y][x].prev_tile;
    }

    public char getIcon(int i, int j) {
        if(board[i][j].getTile() == 'P')
            return 'P';
        else if(board[i][j].getTile() == '^')
            return '^';
        else if(board[i][j].getTile() == 'W')
            return 'W';
        else
            return 'M';
    }

    // method to check if move is valid
    public boolean valid(int x, int y, int dx, int dy) {
        if(dx == 0 && (y+dy <= board[y].length) && y+dy >= 0 && board[dy+y][x].getTile() != '^') {
            return true;
        }
        else if(dy == 0 && (x+dx <= board.length) && x+dx >= 0 && board[y][dx+x].getTile() != '^') {
            return true;
        }
        else
            return false;
    }

    // method to populate the board with randomly assigned tiles
    public void populate() {
        int random = ((int) (Math.random() * 10));
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                // starting tile for party
                if(i == 0 && j == 0)
                    board[i][j] = new Tile('P');
                // non-accessible tiles
                else if(random == 0 || random == 1) {
                    board[i][j] = new Tile('^');
                }
                // market tiles
                else if(random == 2 || random == 3 | random == 4) {
                    board[i][j] = new Tile('M');
                }
                // common/wild tiles
                else {
                    board[i][j] = new Tile('W');
                }
                random = ((int) (Math.random() * 10));
            }
        }
    }

    // method to display board
    public void printMap() {
        for(int i = 0; i < dim2; i++) {
            System.out.printf("==========");
        }
        System.out.println();
        for(int i = 0; i < dim; i++) {
            System.out.println();
            System.out.printf("   ||   ");
            for(int j = 0; j < dim2; j++) {
                System.out.print(getIcon(i,j));
                System.out.print("   ||   ");
            }
            System.out.println();
            System.out.printf(" ");
            System.out.println();
            for(int j = 0; j < dim2; j++) {
                System.out.printf("==========");
            }
            System.out.println();
        }
        System.out.println("INSTRUCTIONS:");
        System.out.println("w: move up | a: move left | s: move down | d: move right | i: information");

    }
}
