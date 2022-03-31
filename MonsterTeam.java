import java.sql.Array;
import java.util.ArrayList;

public class MonsterTeam extends Team {
    // use a list instead here, so we can associate via index
    protected Monster[] team;

    public MonsterTeam() {
    }

    public MonsterTeam(int num_monsters) {
        this.team = new Monster[num_monsters];
    }

    // method to add monster to list of team
    public void add(int index, Monster monster) {
        this.team[index] = monster;
    }

    public ArrayList<Integer> alive() {
        ArrayList<Integer> ret = new ArrayList<>();
        for(int x = 0; x < team.length; x++) {
            if(team[x].hp > 0)
                ret.add(x);
        }
        return ret;
    }

    //override toString method to print out party and stats
    @Override
    public String toString() {
        String ret = "";
        ret += "Monster Party: \n";
        for(int x = 0; x < team.length; x++) {
            ret += team[x].toString() + '\n';
        }
        return ret;
    }
}
