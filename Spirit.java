public class Spirit extends Monster {
    public Spirit() {
        super();
    }

    public Spirit(String name, int lvl, int hp) {
        super(name, lvl, hp);
    }

    // initialization for spirit
    public void init_spirit(String info) {
        initialize(info);
    }

}
