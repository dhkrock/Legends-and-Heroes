import java.util.ArrayList;

public class Team {
    // use a list instead here, so we can associate via index
    protected Hero[] team;
    // current location of party
    protected int x_map;
    protected int y_map;

    public Team() {
    }

    public Team(int num_heroes) {
        team = new Hero[num_heroes];
        x_map = 0;
        y_map = 0;
    }

    // method to match monster with the highest level member of party
    public int highestLvl() {
        int ret = team[0].lvl;
        int prev = team[0].lvl;
        for(int x = 1; x < team.length; x++) {
            if(prev < team[x].lvl) {
                ret = team[x].lvl;
            }
            prev = team[x].lvl;
        }
        return ret;
    }

    // method to heal/replenish hp and mana for alive heroes after a round
    public void teamHeal(){
        for(int x = 0; x < team.length; x++) {
            if(team[x].hp != 0) {
                if((team[x].hp *= 1.10) > team[x].lvl*100)
                    team[x].hp = team[x].lvl*100;
                else
                    team[x].hp *= 1.10;
                // no limit imposed on mana for max in hw pdf, so leaving it uncapped
                team[x].mana *= 1.10;
            }
        }
    }

    // method to keep track of who is alive during battle
    public ArrayList<Integer> alive() {
        ArrayList<Integer> ret = new ArrayList<>();
        for(int x = 0; x < team.length; x++) {
            if(team[x].hp > 0)
                ret.add(x);
        }
        return ret;
    }

    // method to add hero to list of team
    public void add(int index, Hero hero) {
        team[index] = hero;
    }

    //override toString method to print out party and stats
    @Override
    public String toString() {
        String ret = "";
        ret += "Party: \n";
        for(int x = 0; x < team.length; x++) {
            ret += team[x].toString() + '\n';
        }
        return ret;
    }

    // method to run post-battle to progress
    public void post_battle(MonsterTeam mobs) {
        for(int i = 0; i < team.length; i++) {
            // update exp and currency
            if (team[i].hp > 0) {
                team[i].exp += 2;
                team[i].money += 100 * highestLvl();
                team[i].checkLvl();
            }
            else if(team[i].hp <= 0) {
                team[i].hp = team[i].lvl * 100 / 2;
                System.out.println(team[i].name + " got revived with 50% of their HP.");
            }
            System.out.println(team[i].name + " earned 2 exp and " + 100*highestLvl() + " gold.");
        }
    }
}
