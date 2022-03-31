import java.util.ArrayList;
import java.text.DecimalFormat;

public class Hero extends Monster implements Attack {
    // extend Monster class as initialization is the same with bonus attributes
    protected double mana;
    protected int money;
    protected double strength;
    protected double dexterity;
    protected double agility;
    protected int exp;
    protected ArrayList<Weapon> weapons;
    protected Weapon currentWeapon;
    protected ArrayList<Armor> armors;
    protected Armor currentArmor;
    protected ArrayList<Potion> potions;
    protected Potion currentPotion;
    protected ArrayList<Spell> spells;
    protected Spell currentSpell;
    protected int target;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    // no-arg constructor
    public Hero(){
        super();
    }

    // constructor to be used for character creation
    public Hero(String name, int lvl, int hp, int mana, int money, int strength, int dexterity, int agility, int exp) {
        super(name, lvl, hp);
        this.mana = mana;
        this.money = money;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.exp = exp;
    }

    public void levelUp() {
        lvl+= 1;
        hp = lvl * 100;
        mana = mana * 1.1;
        System.out.println(name + " leveled up!");
    }

    public void inven() {
        System.out.println(name + "'s inventory: ");
        System.out.println("Weapons: ");
        if(weapons.isEmpty() == false) {
            for(Weapon weapon: weapons)
                System.out.println(weapon);
        }
        System.out.println("Armor: ");
        if(armors.isEmpty() == false) {
            for(Armor armor: armors)
                System.out.println(armors);
        }
        System.out.println("Spells: ");
        if(spells.isEmpty() == false) {
            for(Spell spell: spells)
                System.out.println(spell);
        }
        System.out.println("Potions: ");
        if(potions.isEmpty() == false) {
            for(Potion potion: potions)
                System.out.println(potions);
        }
    }
    public double spell_attack() {
        return currentSpell.dmg + (dexterity/10000)*currentSpell.dmg;
    }

    public double attack(){
        return (strength + currentWeapon.dmg) * 0.05;
    }

    public double dodge(){
        return agility * 0.002;
    }

    // method to increase attribute depending on potion that is drank
    public void drinkPotion(Potion potion) {
        String[] info = potion.att_name.split("\\s+");
        for(int x = 0; x < info.length; x++) {
            if(info[x].equals("Strength"))
                strength += potion.att_boost;
            if(info[x].equals("Dexterity"))
                dexterity += potion.att_boost;
            if(info[x].equals("Agility"))
                agility += potion.att_boost;
            if(info[x].equals("Health"))
                hp += potion.att_boost;
            if(info[x].equals("Mana"))
                mana += potion.att_boost;
            if(info[x].equals("Defense"))
                // boost dmg_reduc instead here for armor
                currentArmor.dmg_reduc += potion.att_boost;
        }
    }

    // call this method after every battle to see if hero is supposed to level up
    public void checkLvl(){
        if(exp >= lvl*10)
            levelUp();
    }

    // initialize the hero from the txt files given to us
    public void initialize(String line){
        lvl = 1;
        hp = lvl*100;
        // initialize inventory as well
        weapons = new ArrayList<Weapon>();
        currentWeapon = new Weapon();
        armors = new ArrayList<Armor>();
        currentArmor = new Armor();
        potions = new ArrayList<Potion>();
        currentPotion = new Potion();
        spells = new ArrayList<Spell>();
        String[] info = line.split("\\s+");
        name = info[0].replace("_", " ");
        mana = Integer.parseInt(info[1]);
        strength = Integer.parseInt(info[2]);
        agility = Integer.parseInt(info[3]);
        dexterity = Integer.parseInt(info[4]);
        money = Integer.parseInt(info[5]);
        exp = Integer.parseInt(info[6]);
    }

    //override check target to change input class to monster team
    public void check_target(MonsterTeam mobs) {
        if(mobs.team[target].hp < 0)
            change_target(mobs);
    }

    //override change target to change input class to monster team
    public void change_target(MonsterTeam mobs) {
        for(int m = 0; m < mobs.team.length; m++) {
            if(mobs.team[m].hp > 0) {
                target = m;
            }
        }
    }

    // method to run post-round for options 2-4 in battle
    public void soloHeal() {
        if(hp != 0) {
            if((hp *= 1.10) > lvl * 100)
                hp = lvl*100;
            else
                hp *= 1.10;
        }
    }

    //override toString method to print out party and stats
    @Override
    public String toString() {
        String ret = "";
        ret += this.name + " | ";
        if(this.hp <= 0) {
            ret += "DEAD";
            return ret;
        }
        ret += "Level: " + this.lvl + " ";
        ret += "Exp: " + this.exp + " ";
        ret += "HP: " + this.hp + " ";
        ret += "Mana: " + df.format(this.mana) + " ";
        ret += "Defense reduction: " + this.currentArmor.dmg_reduc + " ";
        ret += "Damage: " + this.attack() + " ";
        ret += "Wallet: " + this.money + " ";
        return ret;
    }
}
