import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LHM {
    protected Board board;
    protected Team party;
    protected MonsterTeam monsterParty;
    protected boolean gameActive;
    // dx and dy account for wasd
    protected int dx;
    protected int dy;
    protected int num_party;

    //no-arg constructor
    public LHM() {
    }

    // method to get stats from txt file for corresponding character
    public String getStats(int line, String path) {
        String ret = "";
        File file = new File(path);
        Scanner scan = new Scanner(System.in);
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // user inputs number that corresponds with the line number, so loop until you get to that line
        for (int i = 0; i <= line; i++) {
            ret = scan.nextLine();
        }
        scan.close();
        return ret;
    }

    // method for initialization of character selection
    public void character_selection() {
        Scanner input = new Scanner(System.in);
        String stringInput;
        System.out.println("Welcome to Legends and Heroes!");
        System.out.println("|      WARRIORS      |      SORCERERS        |      PALADINS        |");
        System.out.println("|1.Gaerdal Ironhand  |1.Rillifane Rallathil  |1.Parzival            |");
        System.out.println("|2.Sheanine Monnbow  |2.Segojan Earthcaller  |2.Sekhanine Moonbow   |");
        System.out.println("|3.Muamman Duathall  |3.Reign Havoc          |3.Skoraeus Stonebones |");
        System.out.println("|4.Flandal Steelskin |4.Reverie Ashels       |4.Garl Glittergold    |");
        System.out.println("|5.Undefeated Yoj    |5.Kalabar              |5.Amaryllis Astra     |");
        System.out.println("|6.Eunoia Cyn        |6.Skye Soar            |6.Caliber Heist       |");
        System.out.println();
        System.out.println("How many heroes are in your party?");
        stringInput = input.next();
        num_party = Integer.parseInt(stringInput);
        party = new Team(num_party);
        for (int i = 0; i < num_party; i++) {
            System.out.println("Choose a class! (1 for Warrior, 2 for Sorcerer, 3 for Paladin)");
            stringInput = input.next();
            int loop_num = Integer.parseInt(stringInput);
            if (loop_num == 1) {
                System.out.println("Enter the corresponding number of the warrior you would like to select");
                stringInput = input.next();
                loop_num = Integer.parseInt(stringInput);
                String line = getStats(loop_num, "./Warriors.txt");
                Warrior hero = new Warrior();
                hero.init_warrior(line);
                System.out.println("You chose " + hero.name + "!");
                party.add(i, hero);
            } else if (loop_num == 2) {
                System.out.println("Enter the corresponding number of the sorcerer you would like to select");
                stringInput = input.next();
                loop_num = Integer.parseInt(stringInput);
                String line = getStats(loop_num, "./Sorcerers.txt");
                Sorcerer hero = new Sorcerer();
                hero.init_sorcerer(line);
                System.out.println("You chose " + hero.name + "!");
                party.add(i, hero);
            } else {
                System.out.println("Enter the corresponding number of the paladin you would like to select");
                stringInput = input.next();
                loop_num = Integer.parseInt(stringInput);
                String line = getStats(loop_num, "./Paladins.txt");
                Paladin hero = new Paladin();
                hero.init_paladin(line);
                System.out.println("You chose " + hero.name + "!");
                party.add(i, hero);
            }
        }
    }


    // method to check if monsters will be encountered when on a 'W' tile
    // returns true if monster(s) encountered, false if not
    public boolean wildCheck() {
        // set as 40 percent chance for encounters
        int random = ((int) (Math.random() * 10));
        if (random == 0 | random == 1 | random == 2 || random == 3) {
            // instantiate monsters here since we know there is going to be a fight
            monsterParty = new MonsterTeam(num_party);
            for (int x = 0; x < monsterParty.team.length; x++) {
                int random_m = ((int) (Math.random() * 9));
                // split probability of creating one of three monsters by 33%
                // exoskeleton
                if (random_m == 0 | random_m == 1 || random_m == 2) {
                    Monster monster = new Monster();
                    boolean foundMatch = false;
                    // start from 1 to account for header in .txt files
                    int count = 1;
                    while (!foundMatch) {
                        String line = getStats(count, "./Exoskeletons.txt");
                        monster.initialize(line);
                        count++;
                        if (monster.lvl == party.highestLvl()) {
                            monsterParty.add(x, monster);
                            foundMatch = true;
                        }
                    }
                }
                // dragon
                else if (random_m == 3 | random_m == 4 || random_m == 5) {
                    Monster monster = new Monster();
                    boolean foundMatch = false;
                    int count = 1;
                    while (!foundMatch) {
                        String line = getStats(count, "./Dragons.txt");
                        monster.initialize(line);
                        count++;
                        if (monster.lvl == party.highestLvl()) {
                            monsterParty.add(x, monster);
                            foundMatch = true;
                        }
                    }
                }
                // spirit
                else {
                    Monster monster = new Monster();
                    boolean foundMatch = false;
                    int count = 1;
                    while (!foundMatch) {
                        String line = getStats(count, "./Spirits.txt");
                        monster.initialize(line);
                        count++;
                        if (monster.lvl == party.highestLvl()) {
                            monsterParty.add(x, monster);
                            foundMatch = true;
                        }
                    }
                }
            }
            return true;
        } else
            return false;
    }


    public void startFight() {
        // indexes of all of the alive monsters
        System.out.println("You've encountered a fight!");
        // set the targets for battle
        for (int x = 0; x < party.team.length; x++) {
            party.team[x].target = x;
            monsterParty.team[x].target = x;
        }
        boolean active = true;
        while (active) {
            // indexes of all the alive heroes
            ArrayList<Integer> alive_h = party.alive();
            ArrayList<Integer> alive_m = monsterParty.alive();
            System.out.println(party);
            System.out.println(monsterParty);
            System.out.println("Choose your move:");
            System.out.println("1) Attack | 2) Spell | 3) Potion | 4) Change equipment");
            System.out.println("DISCRETION: Choosing Attack will cause all of your party members to attack at once.");
            System.out.println("DISCRETION: Choosing 'Spell' or 'Potion' will automatically use the currently equipped spell/potion");
            char input = getCharBattle();
            if (input != 'f') {
                if (input == '1') {
                    // re-calibrate before iteration
                    alive_h = party.alive();
                    // heroes attack first
                    for (int h = 0; h < alive_h.size(); h++) {
                        // confirm target is valid
                        party.team[alive_h.get(h)].check_target(monsterParty);
                        // if monster does not dodge, attack
                        if (!(Math.random() < monsterParty.team[party.team[alive_h.get(h)].target].dodge())) {
                            monsterParty.team[party.team[alive_h.get(h)].target].hp -= party.team[alive_h.get(alive_h.get(h))].attack() - monsterParty.team[party.team[alive_h.get(h)].target].defense;
                            System.out.println(party.team[alive_h.get(h)].name + " inflicted " + (party.team[alive_h.get(alive_h.get(h))].attack() - monsterParty.team[party.team[alive_h.get(h)].target].defense) + " damage to " + monsterParty.team[party.team[alive_h.get(h)].target].name + "\n");
                            if (monsterParty.team[party.team[alive_h.get(h)].target].hp <= 0) {
                                System.out.println(monsterParty.team[party.team[alive_h.get(h)].target].name + " died" + "\n");
                                party.team[alive_h.get(h)].change_target(monsterParty);
                                if (monsterParty.alive().size() == 0) {
                                    // adjust for xp increase and revive
                                    party.post_battle(monsterParty);
                                    active = false;
                                    break;
                                }
                            }
                        } else
                            System.out.println(monsterParty.team[party.team[alive_h.get(h)].target].name + " dodged the attack by " + party.team[alive_h.get(h)].name + "!" + "\n");
                    }
                    // re-calibrate before iteration
                    alive_m = monsterParty.alive();
                    for (int m = 0; m < alive_m.size(); m++) {
                        // confirm target is valid
                        monsterParty.team[alive_h.get(m)].check_target(party);
                        // monsters attack now
                        // if hero does not dodge, attack
                        if (!(Math.random() < party.team[monsterParty.team[alive_m.get(m)].target].dodge())) {
                            party.team[monsterParty.team[alive_m.get(m)].target].hp -= monsterParty.team[alive_m.get(m)].attack() - party.team[monsterParty.team[alive_m.get(m)].target].currentArmor.dmg_reduc;
                            System.out.println(monsterParty.team[alive_m.get(m)].name + " inflicted " + (monsterParty.team[alive_m.get(m)].attack() - party.team[monsterParty.team[alive_m.get(m)].target].currentArmor.dmg_reduc) + " damage to " + party.team[monsterParty.team[alive_m.get(m)].target].name + "\n");
                            if (party.team[monsterParty.team[alive_m.get(m)].target].hp <= 0) {
                                System.out.println(party.team[monsterParty.team[alive_m.get(m)].target].name + " died" + "\n");
                                monsterParty.team[alive_m.get(m)].change_target(party);
                                if (party.alive().size() == 0) {
                                    System.out.println("GAME OVER! All party members died.");
                                    System.exit(0);
                                }
                            }
                        } else
                            System.out.println(party.team[monsterParty.team[alive_m.get(m)].target].name + " dodged the attack by " + monsterParty.team[alive_m.get(m)].name + "!" + "\n");
                    }
                    // post round adjustments for HP/mana gain
                    party.teamHeal();
                } else if (input == '2') {
                    alive_h = party.alive();
                    System.out.println("Choose a hero to use a spell with:");
                    printHeroCatalog(alive_h);
                    input = getCharBattle();
                    int int_input = Integer.parseInt(String.valueOf(input)) - 1;
                    if (int_input < party.team.length) {
                        if (party.team[int_input].spells.isEmpty()) {
                            System.out.println("This hero has no spells!" + '\n');
                        } else {
                            Monster target = monsterParty.team[party.team[int_input].target];
                            Hero spellcaster = party.team[int_input];
                            // apply this debuff before monster is able to dodge
                            if (spellcaster.currentSpell.spellType == "L") {
                                target.dodge_chance *= 0.9;
                            }
                            if (!(Math.random() < target.dodge())) {
                                target.hp -= spellcaster.spell_attack() - target.defense;
                                // apply debuff
                                if (spellcaster.currentSpell.spellType == "F") {
                                    target.defense *= 0.9;
                                } else if (spellcaster.currentSpell.spellType == "I") {
                                    target.dmg *= 0.9;
                                }
                                System.out.println(spellcaster.name + " did " + (spellcaster.spell_attack() - target.defense) + " magic damage to " + target.name + "!" + '\n');
                                if (target.hp <= 0) {
                                    System.out.println(target.name + " died" + '\n');
                                    spellcaster.change_target(monsterParty);
                                    if (monsterParty.alive().size() == 0) {
                                        // adjust for xp increase and revive
                                        party.post_battle(monsterParty);
                                        break;
                                    }
                                }
                            } else
                                System.out.println(target.name + " dodged the magic attack!" + '\n');
                            if (!(Math.random() < spellcaster.dodge())) {
                                spellcaster.hp -= target.attack() - spellcaster.currentArmor.dmg_reduc;
                                System.out.println(target.name + " did " + (target.dmg - spellcaster.currentArmor.dmg_reduc) + " damage to " + spellcaster.name + "!" + '\n');
                                if (spellcaster.hp <= 0) {
                                    System.out.println(spellcaster.name + " died" + '\n');
                                    target.change_target(party);
                                    if (party.alive().size() == 0) {
                                        System.out.println("GAME OVER! All party members died.");
                                        System.exit(0);
                                    }
                                }
                                // post-round heal
                                spellcaster.soloHeal();
                            } else {
                                System.out.println(spellcaster.name + " dodged the magic attack!");
                                // post-round heal
                                spellcaster.soloHeal();
                            }
                        }

                    } else
                        System.out.println("Enter a valid input" + '\n');
                } else if (input == '3') {
                    alive_h = party.alive();
                    System.out.println("Choose a hero to consume a potion:");
                    printHeroCatalog(alive_h);
                    input = getCharBattle();
                    int int_input = Integer.parseInt(String.valueOf(input)) - 1;
                    if (int_input < party.team.length) {
                        if (party.team[int_input].potions.isEmpty()) {
                            System.out.println("This hero has no potions!" + '\n');
                        } else {
                            Monster target = monsterParty.team[party.team[int_input].target];
                            Hero potionConsumer = party.team[int_input];
                            potionConsumer.drinkPotion(potionConsumer.currentPotion);
                            System.out.println("Successfully drank " + potionConsumer.currentPotion.name);
                            potionConsumer.potions.remove(potionConsumer.currentPotion);
                            if (!(Math.random() < potionConsumer.dodge())) {
                                potionConsumer.hp -= target.attack() - potionConsumer.currentArmor.dmg_reduc;
                                System.out.println(target.name + " did " + (target.attack() - potionConsumer.currentArmor.dmg_reduc) + " damage to " + potionConsumer.name + "!" + '\n');
                                if (potionConsumer.hp <= 0) {
                                    System.out.println(potionConsumer.name + " died" + '\n');
                                    target.change_target(party);
                                    if (party.alive().size() == 0) {
                                        active = false;
                                        System.out.println("GAME OVER! All party members died.");
                                        System.exit(0);
                                    }
                                }
                                // post-round heal
                                potionConsumer.soloHeal();
                            } else {
                                System.out.println(potionConsumer.name + " dodged the attack by " + target.name + "!" + '\n');
                            }

                        }
                    }
                } else if (input == '4') {
                    alive_h = party.alive();
                    System.out.println("Choose a hero to change equipment: ");
                    printHeroCatalog(alive_h);
                    input = getCharBattle();
                    int int_input = Integer.parseInt(String.valueOf(input)) - 1;
                    Hero inven_hero = party.team[int_input];
                    inven_hero.inven();
                    System.out.println('\n' + "Choose an item to equip: (please type out the item you would like to sell (case-sensitive))");
                    Scanner scan = new Scanner(System.in);
                    String inp_inven = scan.nextLine();
                    boolean foundItem = false;
                    while (!foundItem) {
                        int count = 0;
                        for (Weapon weapon : inven_hero.weapons) {
                            // found the item, equip it. follow same process for other loops
                            if (weapon.name.equals(inp_inven)) {
                                foundItem = true;
                                inven_hero.currentWeapon = weapon;
                                System.out.println("Successfully equipped " + weapon.name);
                            }
                            count++;
                        }
                        for (Armor armor : inven_hero.armors) {
                            if (armor.name.equals(inp_inven)) {
                                foundItem = true;
                                inven_hero.currentArmor = armor;
                                System.out.println("Successfully equipped " + armor.name);
                            }
                            count++;
                        }
                        for (Potion potion : inven_hero.potions) {
                            if (potion.name.equals(inp_inven)) {
                                foundItem = true;
                                inven_hero.currentPotion = potion;
                                System.out.println("Successfully equipped " + potion.name);
                            }
                            count++;
                        }
                        for (Spell spell : inven_hero.spells) {
                            if (spell.name.equals(inp_inven)) {
                                foundItem = true;
                                inven_hero.currentSpell = spell;
                                System.out.println("Successfully equipped " + spell.name);
                            }
                            count++;
                        }
                    }
                    // commence battle
                    Monster target = monsterParty.team[inven_hero.target];
                    if (!(Math.random() < inven_hero.dodge())) {
                        inven_hero.hp -= target.attack() - inven_hero.currentArmor.dmg_reduc;
                        System.out.println(target.name + " did " + (target.attack() - inven_hero.currentArmor.dmg_reduc) + " damage to " + inven_hero.name + "!" + '\n');
                        if (inven_hero.hp <= 0) {
                            System.out.println(inven_hero.name + " died" + '\n');
                            target.change_target(party);
                            if (party.alive().size() == 0) {
                                System.out.println("GAME OVER! All party members died.");
                                System.exit(0);
                            }
                        }
                        // post-round heal
                        inven_hero.soloHeal();
                    } else {
                        System.out.println(inven_hero.name + " dodged the attack by " + target.name + "!" + '\n');
                    }
                } else
                    System.out.println("Enter a valid input");
            }
        }
    }

    // method for battling to show hero catalog for user to select
    public void printHeroCatalog(ArrayList<Integer> heroIndex) {
        for (int x = 0; x < heroIndex.size(); x++) {
            System.out.print(x + 1 + ") ");
            System.out.print(party.team[heroIndex.get(x)].name + " " + '\n');
        }
    }

    // method to check if move is valid and move, if so, for party
    public void move_input() {
        char input = getChar();
        if (input != 'f') {
            if (input == 'w') {
                dy = -1;
                if (board.valid(party.x_map, party.y_map, 0, dy)) {
                    party.y_map -= 1;
                    board.changeIcon(party.x_map, party.y_map, party.x_map, party.y_map + 1);
                    dy = 0;
                } else {
                    System.out.println("Enter a valid input");
                    move_input();
                }
            } else if (input == 'a') {
                dx = -1;
                if (board.valid(party.x_map, party.y_map, dx, 0)) {
                    party.x_map -= 1;
                    board.changeIcon(party.x_map, party.y_map, party.x_map + 1, party.y_map);
                    dx = 0;
                } else {
                    System.out.println("Enter a valid input");
                    move_input();
                }
            } else if (input == 's') {
                dy = 1;
                if (board.valid(party.x_map, party.y_map, 0, dy)) {
                    party.y_map += 1;
                    board.changeIcon(party.x_map, party.y_map, party.x_map, party.y_map - 1);
                    dy = 0;
                } else {
                    System.out.println("Enter a valid input");
                    move_input();
                }
            } else if (input == 'd') {
                dx = 1;
                if (board.valid(party.x_map, party.y_map, dx, 0)) {
                    party.x_map += 1;
                    board.changeIcon(party.x_map, party.y_map, party.x_map - 1, party.y_map);
                    dx = 0;
                } else {
                    System.out.println("Enter a valid input");
                    move_input();
                }
            } else if (input == 'i') {
                invenBrowse();
            }
        } else {
            System.out.println("Enter a valid input");
            move_input();
        }
    }

    // method to get the character input for wasd and i key bindings
    public char getChar() {
        System.out.println("Input:");
        Scanner sc = new Scanner(System.in);
        char input = sc.next().charAt(0);
        if (input == 'w' || input == 'a' || input == 's' || input == 'd' || input == 'i')
            return input;
        else
            return 'f';

    }

    // method to get the character input for battling
    public char getCharBattle() {
        System.out.println("Input:");
        Scanner sc = new Scanner(System.in);
        char input = sc.next().charAt(0);
        if (input == '1' || input == '2' || input == '3' || input == '4')
            return input;
        else
            return 'f';

    }

    // method used to browse the market
    public void marketBrowse() {
        System.out.println("Welcome to the Market!");
        System.out.println("Who is browsing the market?");
        // initializing this just to use helper method printHeroCatalog()
        ArrayList<Integer> alive_h = party.alive();
        printHeroCatalog(alive_h);
        // getCharBattle() still works here
        char input = getCharBattle();
        int intput = Integer.parseInt(String.valueOf(input)) - 1;
        Hero browsing_hero = party.team[intput];
        boolean browsing = true;
        while (browsing) {
            System.out.println("What would you like to browse?");
            System.out.println("1) Weapons | 2) Armor | 3) Potions | 4) Spells | 5) Sell | 6) Exit Market");
            System.out.println("Input: ");
            Scanner market_input_scanner = new Scanner(System.in);
            String market_input;
            market_input = market_input_scanner.next();
            if (market_input.equals("1")) {
                System.out.println("WEAPONS");
                System.out.println("Name/Cost/Level/Damage/Required Hands");
                System.out.println("1) Sword/500/1/800/1");
                System.out.println("2) Bow/300/2/500/2");
                System.out.println("3) Scythe/1000/6/1100/2");
                System.out.println("4) Axe/550/5/850/1");
                System.out.println("5) TSwords/1400/8/1600/2");
                System.out.println("6) Dagger/200/1/250/1");
                System.out.println("Which weapon would you like to purchase?");
                System.out.println("Input: ");
                market_input = market_input_scanner.next();
                int intput2 = Integer.parseInt(market_input);
                String line = getStats(intput2, "./Weaponry.txt");
                Weapon possible_purchase = new Weapon();
                possible_purchase.initialize(line);
                if (possible_purchase.lvl_requirement > browsing_hero.lvl) {
                    System.out.println("You do not meet the minimum level requirement for this weapon" + '\n');
                }
                // weapon is purchasable
                else {
                    if (browsing_hero.money >= possible_purchase.price) {
                        browsing_hero.weapons.add(possible_purchase);
                        browsing_hero.money -= possible_purchase.price;
                        System.out.println(possible_purchase.name + " purchased by " + browsing_hero.name);
                    } else {
                        System.out.println("You do not have enough money for this purchase" + '\n');
                    }
                }
            } else if (market_input.equals("2")) {
                System.out.println("ARMOR");
                System.out.println("Name/Cost/Level/Damage Reduction");
                System.out.println("1) Platinum Shield/150/1/200");
                System.out.println("2) Breastplate/350/3/600");
                System.out.println("3) Full_Body_Armor/1000/8/1500");
                System.out.println("4) Wizard Shield/1200/10/1500");
                System.out.println("5) Guardian Angel/1000/10/1000");
                System.out.println("What piece of armor would you like to purchase?");
                System.out.println("Input: ");
                market_input = market_input_scanner.next();
                int intput2 = Integer.parseInt(market_input);
                String line = getStats(intput2, "./Armory.txt");
                Armor possible_purchase = new Armor();
                possible_purchase.initialize(line);
                if (possible_purchase.lvl_requirement > browsing_hero.lvl) {
                    System.out.println("You do not meet the minimum level requirement for this weapon" + '\n');
                }
                // armor is purchasable
                else {
                    if (browsing_hero.money >= possible_purchase.price) {
                        browsing_hero.armors.add(possible_purchase);
                        browsing_hero.money -= possible_purchase.price;
                        System.out.println(possible_purchase.name + " purchased by " + browsing_hero.name);
                    } else {
                        System.out.println("You do not have enough money for this purchase" + '\n');
                    }
                }
            } else if (market_input.equals("3")) {
                System.out.println("POTIONS");
                System.out.println("Name/Cost/Level/Attribute increase/Attributes affected");
                System.out.println("1) Healing Potion/250/1/100");
                System.out.println("2) Strength Potion/200/1/75");
                System.out.println("3) Magic Potion/350/2/100");
                System.out.println("4) Luck Elixir/500/4/65");
                System.out.println("5) Mermaid Tears/850/5/100");
                System.out.println("6) Ambrosia/1000/8/150");
                System.out.println("What piece of armor would you like to purchase?");
                System.out.println("Input: ");
                market_input = market_input_scanner.next();
                int intput2 = Integer.parseInt(market_input);
                String line = getStats(intput2, "./Potions.txt");
                Potion possible_purchase = new Potion();
                possible_purchase.initialize(line);
                if (possible_purchase.lvl_requirement > browsing_hero.lvl) {
                    System.out.println("You do not meet the minimum level requirement for this weapon" + '\n');
                }
                // spell is purchasable
                else {
                    if (browsing_hero.money >= possible_purchase.price) {
                        browsing_hero.potions.add(possible_purchase);
                        browsing_hero.money -= possible_purchase.price;
                        System.out.println(possible_purchase.name + " purchased by " + browsing_hero.name);
                    } else {
                        System.out.println("You do not have enough money for this purchase" + '\n');
                    }
                }
            } else if (market_input.equals("4")) {
                System.out.println("What spells would you like to browse?");
                System.out.println("1) Lightning | 2) Fire | 3) Ice");
                System.out.println("Input: ");
                market_input = market_input_scanner.next();
                // lightning spells
                if (market_input.equals("1")) {
                    System.out.println("LIGHTNING SPELLS");
                    System.out.println("Name/Cost/Level/Damage/Mana cost");
                    System.out.println("1) Lightning Dagger/250/1/500/150");
                    System.out.println("2) Thunder Blast/750/4/950/400");
                    System.out.println("3) Electric Arrows/550/5/650/200");
                    System.out.println("4) Spark Needles/500/2/600/200");
                    System.out.println("Which lightning spell would you like to purchase?");
                    System.out.println("Input: ");
                    market_input = market_input_scanner.next();
                    int intput2 = Integer.parseInt(market_input);
                    String line = getStats(intput2, "./LightningSpells.txt");
                    Spell possible_purchase = new Spell();
                    possible_purchase.setSpellType("L");
                    possible_purchase.initialize(line);
                    if (possible_purchase.lvl_requirement > browsing_hero.lvl) {
                        System.out.println("You do not meet the minimum level requirement for this weapon" + '\n');
                    }
                    // spell is purchasable
                    else {
                        if (browsing_hero.money >= possible_purchase.price) {
                            browsing_hero.spells.add(possible_purchase);
                            browsing_hero.money -= possible_purchase.price;
                            System.out.println(possible_purchase.name + " purchased by " + browsing_hero.name);
                        } else {
                            System.out.println("You do not have enough money for this purchase" + '\n');
                        }
                    }
                }
                // fire spells
                else if (market_input.equals("2")) {
                    System.out.println("FIRE SPELLS");
                    System.out.println("Name/Cost/Level/Damage/Mana cost");
                    System.out.println("1) Flame Tornado/700/4/850/300");
                    System.out.println("2) Breath of Fire/350/1/450/100");
                    System.out.println("3) Heat Wave/450/2/600/150");
                    System.out.println("4) Lava Comet/800/7/1000/550");
                    System.out.println("5) Hell Storm/600/3/950/600");
                    System.out.println("Which fire spell would you like to purchase?");
                    System.out.println("Input: ");
                    market_input = market_input_scanner.next();
                    int intput2 = Integer.parseInt(market_input);
                    String line = getStats(intput2, "./FireSpells.txt");
                    Spell possible_purchase = new Spell();
                    possible_purchase.setSpellType("F");
                    possible_purchase.initialize(line);
                    if (possible_purchase.lvl_requirement > browsing_hero.lvl) {
                        System.out.println("You do not meet the minimum level requirement for this weapon" + '\n');
                    }
                    // spell is purchasable
                    else {
                        if (browsing_hero.money >= possible_purchase.price) {
                            browsing_hero.spells.add(possible_purchase);
                            System.out.println(possible_purchase.name + " purchased by " + browsing_hero.name);
                            browsing_hero.money -= possible_purchase.price;
                        } else {
                            System.out.println("You do not have enough money for this purchase" + '\n');
                        }
                    }
                } else if (market_input.equals("3")) {
                    // ice spells
                    System.out.println("ICE SPELLS");
                    System.out.println("Name/Cost/Level/Damage/Mana cost");
                    System.out.println("1) Snow Cannon/500/2/650/250");
                    System.out.println("2) Ice Blade/250/1/450/100");
                    System.out.println("3) Frost Blizzard/750/5/850/350");
                    System.out.println("4) Arctic Storm/700/6/800/300");
                    System.out.println("Which ice spell would you like to purchase?");
                    System.out.println("Input: ");
                    market_input = market_input_scanner.next();
                    int intput2 = Integer.parseInt(market_input);
                    String line = getStats(intput2, "./IceSpells.txt");
                    Spell possible_purchase = new Spell();
                    possible_purchase.setSpellType("I");
                    possible_purchase.initialize(line);
                    if (possible_purchase.lvl_requirement > browsing_hero.lvl) {
                        System.out.println("You do not meet the minimum level requirement for this weapon" + '\n');
                    }
                    // spell is purchasable
                    else {
                        if (browsing_hero.money >= possible_purchase.price) {
                            browsing_hero.spells.add(possible_purchase);
                            browsing_hero.money -= possible_purchase.price;
                            System.out.println(possible_purchase.name + " purchased by " + browsing_hero.name);
                        } else {
                            System.out.println("You do not have enough money for this purchase" + '\n');
                        }
                    }
                }
            } else if (market_input.equals("5")) {
                browsing_hero.inven();
                System.out.println('\n' + "Choose an item to sell: (please type out the item you would like to sell (case-sensitive))");
                market_input = market_input_scanner.next();
                boolean foundItem = false;
                while (!foundItem) {
                    int count = 0;
                    for (Weapon weapon : browsing_hero.weapons) {
                        // if weapon is found, sell for half the price and remove from arraylist, do the same for any other selling action
                        if (weapon.name.equals(market_input)) {
                            foundItem = true;
                            browsing_hero.money += weapon.price * 0.5;
                            browsing_hero.weapons.remove(count);
                            System.out.println("Sold " + weapon.name + " for " + weapon.price * 0.5 + " gold");
                            break;
                        }
                        count++;
                    }
                    for (Armor armor : browsing_hero.armors) {
                        // if weapon is found, sell for half the price and remove from arraylist, do the same for any other selling action
                        if (armor.name.equals(market_input)) {
                            foundItem = true;
                            browsing_hero.money += armor.price * 0.5;
                            browsing_hero.armors.remove(count);
                            System.out.println("Sold " + armor.name + " for " + armor.price * 0.5 + " gold");
                            break;
                        }
                        count++;
                    }
                    for (Potion potion : browsing_hero.potions) {
                        // if weapon is found, sell for half the price and remove from arraylist, do the same for any other selling action
                        if (potion.name.equals(market_input)) {
                            foundItem = true;
                            browsing_hero.money += potion.price * 0.5;
                            browsing_hero.potions.remove(count);
                            System.out.println("Sold " + potion.name + " for " + potion.price * 0.5 + " gold");
                            break;
                        }
                        count++;
                    }
                    for (Spell spell : browsing_hero.spells) {
                        // if weapon is found, sell for half the price and remove from arraylist, do the same for any other selling action
                        if (spell.name.equals(market_input)) {
                            foundItem = true;
                            browsing_hero.money += spell.price * 0.5;
                            browsing_hero.spells.remove(count);
                            System.out.println("Sold " + spell.name + " for " + spell.price * 0.5 + " gold");
                            break;
                        }
                        count++;
                    }
                }
            } else if (market_input.equals("6")) {
                System.out.println('\n' + "Thanks for stopping by!");
                browsing = false;
            }
        }
    }

    // method used to browse the inventory
    public void invenBrowse() {
        System.out.println("Choose a hero's inventory to access: ");
        ArrayList<Integer> alive_h = party.alive();
        printHeroCatalog(alive_h);
        System.out.println("Input: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        int intput = Integer.parseInt(input) - 1;
        System.out.println(party.team[intput]);
        party.team[intput].inven();
        System.out.println("Currently equipped weapon: " + party.team[intput].currentWeapon);
        System.out.println("Currently equipped weapon: " + party.team[intput].currentArmor);
    }

    public void play() {
        character_selection();
        // default value of 8x8
        board = new Board();
        board.populate();
        board.printMap();
        gameActive = true;
        while (gameActive) {
            move_input();
            if (board.prevIcon(party.x_map, party.y_map) == 'W') {
                boolean check = wildCheck();
                if (check) {
                    startFight();
                    board.printMap();
                } else
                    board.printMap();
            } else if (board.prevIcon(party.x_map, party.y_map) == 'M') {
                System.out.println("You landed on a Market tile! Enter? y or n");
                Scanner scan = new Scanner(System.in);
                String input = scan.next();
                if (input.equals("y")) {
                    marketBrowse();
                    board.printMap();
                } else if (input.equals("n")) {
                    System.out.println("Come by anytime!");
                    board.printMap();
                }
            } else
                board.printMap();
        }
    }

    public static void main(String[] args) {
        LHM game = new LHM();
        game.play();
    }
}
