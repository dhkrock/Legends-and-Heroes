public class Monster implements Attack {
    // class for initializing creatures
    protected String name;
    protected String enemy;
    protected int lvl;
    protected double hp;
    protected int dmg;
    protected int dodge_chance;
    protected int defense;
    protected int target;

    // no-arg constructor
    public Monster() {
        name = "";
        lvl = 1;
        hp = 100;
    }

    // constructor to initialize stats
    public Monster(String name, int lvl, int hp) {
        this.name = name;
        this.lvl = lvl;
        this.hp = hp;
    }


    public void levelUp() {
        lvl += 1;
        hp = lvl * 100;
    }

    public double attack() {
        return dmg;
    }
    public void heal(int hp_inc) {
        hp += hp_inc;
    }

    public void damage(int dmg) {
        hp -= dmg;
    }

    public void setDodge(int dodge_chance) {
        this.dodge_chance = dodge_chance;
    }

    public double dodge(){
        return dodge_chance * 0.01;
    }

    // method to check before attacking to see if they are dead
    public void check_target(Team heroes) {
        if(heroes.team[target].hp < 0)
            change_target(heroes);
    }

    // for when original target dies to switch targets to the next enemy
    public void change_target(Team heroes) {
        for(int h = 0; h < heroes.team.length; h++) {
            if(heroes.team[h].hp > 0)
                target = h;
        }
    }

    // method for setting defense so constructor can still apply to hero
    public void setDefense(int def) {
        this.defense = def;
    }


    // initialize the monster from the txt files given to us
    public void initialize(String line){
        String[] info = line.split("\\s+");
        name = info[0].replace("_", " ");
        lvl = Integer.parseInt(info[1]);
        hp = lvl*100;
        dmg = Integer.parseInt(info[2]);
        defense = Integer.parseInt(info[3]);
        dodge_chance = Integer.parseInt(info[4]);
    }

    //override toString method to print out monsters and stats
    @Override
    public String toString() {
        String ret = "";
        if (this.hp <= 0) {
            ret += "DEAD";
            return ret;
        }
        ret += this.name + " | ";
        ret += "Level: " + this.lvl + " ";
        ret += "HP: " + this.hp + " ";
        ret += "Defense: " + this.defense + " ";
        ret += "Damage: " + this.dmg + " ";
        ret += "Dodge: " + this.dodge_chance + "% ";
        return ret;
    }
}
