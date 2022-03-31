public class Tile {
    protected char prev_tile;
    protected char tile_c;

    // no-arg constructor, blank as we won't be using it
    public Tile() throws InstantiationException {
        throw new InstantiationException();
    }

    public Tile(char tile_c) {
        this.tile_c = tile_c;
    }

    public char getTile() {
        return tile_c;
    }

    public void setTile(char c) {
        prev_tile = tile_c;
        tile_c = c;
    }
}
