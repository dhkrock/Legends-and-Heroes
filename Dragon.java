public class Dragon extends Monster {
    public Dragon() {
        super();
    }

    public Dragon(String name, int lvl, int hp) {
        super(name, lvl, hp);
    }

    // initialization for dragon
    public void init_dragon(String info) {
        initialize(info);
    }

}

