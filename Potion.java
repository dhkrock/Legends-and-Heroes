public class Potion {
    protected String name;
    protected int price;
    protected int lvl_requirement;
    protected String att_name;
    protected int att_boost;

    // no-arg constructor
    public Potion() {
        this.name = "";
        this.price = 0;
        this.lvl_requirement = 1;
        this.att_name = "";
        this.att_boost = 0;
    }

    public Potion(String name, int price, int lvl_requirement, String att_name, int att_boost) {
        this.name = name;
        this.price = price;
        this.lvl_requirement = lvl_requirement;
        this.att_name = att_name;
        this.att_boost = att_boost;
    }

    public void initialize(String file){
        String[] info = file.split("\\s+");
        name = info[0].replace("_", " ");
        price = Integer.parseInt(info[1]);
        lvl_requirement = Integer.parseInt(info[2]);
        att_boost = Integer.parseInt(info[3]);
        att_name = info[4];
    }

    //override toString method to print out potion and stats
    @Override
    public String toString() {
        String ret = "";
        ret += this.name + " | ";
        ret += "Required level: " + this.lvl_requirement + " ";
        ret += "Attribute increase: " + this.att_boost + " ";
        ret += "Attributes affected: " + this.att_name + " ";
        return ret;
    }
}
