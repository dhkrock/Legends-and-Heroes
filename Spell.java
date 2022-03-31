public class Spell extends Weapon {
    protected String name;
    protected int dmg;
    protected int price;
    protected int required_lvl;
    protected int mana_usage;
    protected String spellType;

    // no-arg constructor
    public Spell() {
        super();
        this.mana_usage = 0;
    }

    public Spell(String name, int dmg, int price, int required_lvl, int mana_usage) {
        super(name, dmg, price, required_lvl, 0);
        this.mana_usage = mana_usage;
    }

    public void setSpellType(String type) {
        this.spellType = type;
    }

    public void initialize(String file){
        String[] info = file.split("\\s+");
        name = info[0].replace("_", " ");
        price = Integer.parseInt(info[1]);
        required_lvl = Integer.parseInt(info[2]);
        dmg = Integer.parseInt(info[3]);
        mana_usage = Integer.parseInt(info[4]);
    }

    //override toString method to print out spell and stats
    @Override
    public String toString() {
        String ret = "";
        ret += this.name + " | ";
        ret += "Required level: " + this.required_lvl + " ";
        ret += "Damage: " + this.dmg + " ";
        ret += "Mana cost: " + this.mana_usage + " ";
        return ret;
    }
}
