public class Armor {
    protected String name;
    protected int price;
    protected int lvl_requirement;
    protected int dmg_reduc;

    // no-arg constructor
    public Armor() {
        this.name = "";
        this.price = 0;
        this.lvl_requirement = 1;
        this.dmg_reduc = 0;
    }

    public Armor(String name, int price, int lvl_requirement, int dmg_reduc){
        this.name = name;
        this.price = price;
        this.lvl_requirement = lvl_requirement;
        this.dmg_reduc = dmg_reduc;
    }

    // method to retrieve name of weapon
    public String name() {
        return this.name;
    }

    public void initialize(String file){
        String[] info = file.split("\\s+");
        name = info[0].replace("_", " ");
        price = Integer.parseInt(info[1]);
        lvl_requirement = Integer.parseInt(info[2]);
        dmg_reduc = Integer.parseInt(info[3]);
    }

    //override toString method to print out armor and stats
    @Override
    public String toString() {
        String ret = "";
        ret += this.name + " | ";
        ret += "Required level: " + this.lvl_requirement + " ";
        ret += "Damage reduction: " + this.dmg_reduc + " ";
        return ret;
    }
}
