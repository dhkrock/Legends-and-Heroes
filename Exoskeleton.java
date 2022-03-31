public class Exoskeleton extends Monster {
    // no-arg constructor
    public Exoskeleton() {
        super();
    }

    public Exoskeleton(String name, int lvl, int hp) {
        super(name, lvl, hp);
    }

    // initialization for exoskeleton
    public void init_exoskeleton(String info) {
        initialize(info);
    }

}
