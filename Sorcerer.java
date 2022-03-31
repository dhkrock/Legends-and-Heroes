public class Sorcerer extends Hero {
    // no-arg constructor
    public Sorcerer(){
        super();
    }

    public Sorcerer(String name, int lvl, int hp, int mana, int money, int strength, int dexterity, int agility, int exp) {
        super(name, lvl, hp, mana, money, strength, dexterity, agility, exp);
    }

    public void init_sorcerer(String info) {
        initialize(info);
    }

    //override parent's method to cater for extra dexterity and agility
    public void levelUp(){
        lvl+= 1;
        hp = lvl * 100;
        mana = mana * 1.1;
        strength *= 1.05;
        dexterity *= 1.1;
        agility *= 1.1;
    }
}
