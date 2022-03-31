How to run:
In order to run Legends and Heroes, you must unzil all of the files in src and run 
javac LHM.java
java LHM

Legends and Heroes: an RPG played through the terminal, similar to Pokemon

Classes used:
Armor.java - class used to initialize Armor from Armory.txt
Attack.java - interface implemented by Monster and Hero to implement the attack method
Board.java - class used to initialize and populate the board/map being played on 
Dragon.java - extends Monster, class to set up monsters from Dragons.txt
Exoskeleton.java - extends Monster, class to set up mosnters from Exoskeletons.txt
Hero.java - extends Monster and implements Attack, class used to set up heroes that embark on the journey
LHM.java - launcher, class used to play the game 
Monster.java - implements Attack, used to set up monsters encounterd in wild 
MonsterTeam.java - extends Team, class to set up the party of monsters encountered in wild
Paladin.java -  extends Hero, class for paladins from Paladins.txt
Potion.java - class used to initialize potions from Potions.txt
Sorcerer.java - extends Hero, class for sorcerers from Sorcerers.txt
Spell.java - subclass of Weapon for spells used by heroes
Spirit.java - extends Monster, class to set up monsters from Spirits.txt
Team.java - class used to set up the party/team of heroes
Tile.java - class used by Board to create tiles of spaces for the heroes to traverse across
Warrior.java - extends Hero, class for warriors from Warriors.txt
Weapon.java - class used to set up weapons from Weaponry.txt 

-----------------------------------------------------------------------------------------------------------

Navigate via w,a,s,d, and i for information regarding inventory 
To enter market, stand on "M" tile and press y when asked "y or n"

Rules of Battle:
- If the player decides to choose the "Attack" option during battle, all heroes will attack and then all monsters will follow up with an attack
- If the player decides to choose the "Spell" option during battle, the selected hero will cast the spell and the target monster will follow up with an attack
	- After this round is over, only the player that used a spell will be healed for 10%
- If the player decides to choose the "Potion" option during battle, the player will be able to go again without the monster(s) attacking the party
	- Same logic applies as the subbullet in the rule for "Spell," only the hero who drank the potion will receive post-round heal
- The same rules for the "Potion" option applies for the "Change equipment" option
	- Same rule as above subbullets
- The turn will not be wasted if user selects a hero with no potions, spells, or extra equipment as they will be able to choose an option again

Market:
- Only one hero can buy with each visit to the market

Inventory:
- To make the game riskier, heroes can only equip items from the inventory during battle
- Accessing inventory counts as a turn, so you may encounter a monster if in the wild
	- same is applicable for the market, can use again