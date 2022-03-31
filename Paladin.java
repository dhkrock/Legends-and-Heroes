public class Paladin extends Hero {
    // no-arg constructor
    public Paladin(){
        super();
    }

    public Paladin(String name, int lvl, int hp, int mana, int money, int strength, int dexterity, int agility, int exp) {
        super(name, lvl, hp, mana, money, strength, dexterity, agility, exp);
    }

    public void init_paladin(String info) {
        initialize(info);
    }

    //override parent's method to cater for extra strength and dexterity
    public void levelUp(){
        lvl+= 1;
        hp = lvl * 100;
        mana = mana * 1.1;
        strength *= 1.1;
        dexterity *= 1.1;
        agility *= 1.05;
    }
}
