import java.util.ArrayList;

public class Weapon {
    protected String name;
    protected int dmg;
    protected int price;
    protected int lvl_requirement;
    protected int hands;

    // no-arg constructor
    public Weapon() {
        name = "";
        dmg = 0;
        price = 0;
        lvl_requirement = 1;
    }

    public Weapon(String name, int dmg, int price, int lvl_requirement, int hands) {
        this.name = name;
        this.dmg = dmg;
        this.price = price;
        this.lvl_requirement = lvl_requirement;
        this.hands = hands;
    }


    public void initialize(String file){
        String[] info = file.split("\\s+");
        name = info[0].replace("_", " ");
        price = Integer.parseInt(info[1]);
        lvl_requirement = Integer.parseInt(info[2]);
        dmg = Integer.parseInt(info[3]);
        hands = Integer.parseInt(info[4]);
    }

    //override toString method to print out weapon and stats
    @Override
    public String toString() {
        String ret = "";
        ret += this.name + " | ";
        ret += "Required level: " + this.lvl_requirement + " ";
        ret += "Damage: " + this.dmg + " ";
        ret += "Required hands: " + this.hands + " ";
        return ret;
    }
}
