public class Warrior extends Hero {
    // no-arg constructor
    public Warrior(){
        super();
    }

    public Warrior(String name, int lvl, int hp, int mana, int money, int strength, int dexterity, int agility, int exp) {
        super(name, lvl, hp, mana, money, strength, dexterity, agility, exp);
    }

    // initialization for warrior
    public void init_warrior(String info) {
        initialize(info);
    }

    //override parent's method to cater for extra strength and agility
    public void levelUp(){
        lvl+= 1;
        hp = lvl * 100;
        mana = mana * 1.1;
        strength *= 1.1;
        dexterity *= 1.05;
        agility *= 1.1;
    }
}
