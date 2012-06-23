import java.util.HashMap;


public class ClassColors {

	HashMap<String,Color> ccolor = new HashMap<String,Color> ();
	
	public ClassColors () {
	// Klassenfarben	
	ccolor.put("Miracelwip", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Spatzenhirn", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Illiash", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Cursia", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Kr�mml", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Troublemaker", 	new Color(1f,0.96f,0.41f));
	ccolor.put("Kangee", 		new Color(0.77f,0.12f,0.23f));
	ccolor.put("Delraius", 		new Color(0.58f,0.51f,0.79f));
	ccolor.put("Fellmuh", 		new Color(0f,0.44f,0.87f));
	ccolor.put("Madhorn", 		new Color(0f,0.44f,0.87f));
	ccolor.put("Recall", 		new Color(1f,0.9f,0.8f));
	
	
	//Spellfarben
	ccolor.put("Attack", 	new Color(0f,0f,0f));
	//Bollan+Miracelwip
	
	float x = 1f;
	float fac = 0.05f;
	ccolor.put("Shred", 			new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Wrath", 			new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Ferocious Bite", 	new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Starfire", 			new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Rake", 				new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Insect Swarm",		new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Rip", 				new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Starsurge", 		new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Ravage!", 			new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Starfall", 			new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Fury Swipes", 		new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Moonfire", 			new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Swipe", 			new Color(1f*x,0.49f*x,0.04f*x));
	ccolor.put("Sunfire", 			new Color(1f*x,0.49f*x,0.04f*x));
	x += fac;
	ccolor.put("Mangle", 			new Color(1f*x,0.49f*x,0.04f*x));

	//Illiash
	x = 1f;
	ccolor.put("Heroic Strike", 	new Color(0.78f*x,0.61f*x,0.43f*x));
	x += fac;
	ccolor.put("Shield Slam", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	ccolor.put("Bloodthirst", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	x += fac;
	ccolor.put("Devastate", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	ccolor.put("Cleave",	 		new Color(0.78f*x,0.61f*x,0.43f*x));
	x += fac;
	ccolor.put("Revenge", 			new Color(0.78f*x,0.61f*x,0.43f*x));
	ccolor.put("Whirlwind Maw", 	new Color(0.78f*x,0.61f*x,0.43f*x));
	x += fac;
	ccolor.put("Deep Wounds", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	ccolor.put("Raging Blow", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	x += fac;
	ccolor.put("Thunderclap", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	ccolor.put("Whirlwind", 		new Color(0.78f*x,0.61f*x,0.43f*x));
	
	//Spatzenhirn
	x = 1f;
	ccolor.put("Auto Shot", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Chimera Shot", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Steady Shot", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Wild Quiver", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Aimed Shot", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Arcane Shot", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Piercing Shots", 	new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Serpent Sting", 	new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Kill Shot", 		new Color(0.67f*x,0.83f*x,0.45f*x));
	x += fac;
	ccolor.put("Speaking of Rage", 	new Color(0.67f*x,0.83f*x,0.45f*x));

	//Cursia
	x = 1f;
	ccolor.put("Fireball", 			new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Ignite", 			new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Pyroblast", 		new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Combustion",		new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Living Bomb", 		new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Wrath of Tarecgosa",new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Flame Orb", 		new Color(0.41f*x,0.8f*x,0.49f*x));
	x += fac;
	ccolor.put("Fire Power", 		new Color(0.41f*x,0.8f*x,0.49f*x));

	//Kr�mml
	x = 1f;
	ccolor.put("Templar's Verdict", new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Hand of Light", 	new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Crusader Strike", 	new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Censure", 			new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Seal of Truth", 	new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Judgement of Truth",new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Exorcism", 			new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Seal of Command", 	new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Holy Wrath", 		new Color(0.96f*x,0.55f*x,0.73f*x));
	x += fac;
	ccolor.put("Ancient Fury", 		new Color(0.96f*x,0.55f*x,0.73f*x));

	//Recall
	x = 1f;
	ccolor.put("Mind Flay", 		new Color(1f*x,0.9f*x,0.8f*x));
	x += fac;
	ccolor.put("Mind Blast", 		new Color(1f*x,0.9f*x,0.8f*x));
	x += fac;
	ccolor.put("Vampiric Touch", 	new Color(1f*x,0.9f*x,0.8f*x));
	x += fac;
	ccolor.put("Shadow Word: Pain",	new Color(1f*x,0.9f*x,0.8f*x));
	x += fac;
	ccolor.put("Devouring Plague", 	new Color(1f*x,0.9f*x,0.8f*x));
	x += fac;
	ccolor.put("Shadow Word: Death",new Color(1f*x,0.9f*x,0.8f*x));

	//Troublemaker
	x = 1f;
	ccolor.put("Sinister Strike", 	new Color(1f*x,0.96f*x,0.41f*x));
	x += fac;
	ccolor.put("Instant Poison", 	new Color(1f*x,0.96f*x,0.41f*x));
	x += fac;
	ccolor.put("Main Gauche", 		new Color(1f*x,0.96f*x,0.41f*x));
	x += fac;
	ccolor.put("Deadly Poison", 	new Color(1f*x,0.96f*x,0.41f*x));
	x += fac;;
	ccolor.put("Eviscerate", 		new Color(1f*x,0.96f*x,0.41f*x));
	x += fac;
	ccolor.put("Rupture", 			new Color(1f*x,0.96f*x,0.41f*x));

	//Kangee
	x = 1f;
	ccolor.put("Death Coil", 		new Color(0.77f*x,0.12f*x,0.23f*x));
	x += fac;
	ccolor.put("Scourge Strike", 	new Color(0.77f*x,0.12f*x,0.23f*x));
	x += fac;
	ccolor.put("Festering Strike", 	new Color(0.77f*x,0.12f*x,0.23f*x));
	x += fac;
	ccolor.put("Blood Plague", 		new Color(0.77f*x,0.12f*x,0.23f*x));
	x += fac;
	ccolor.put("Frost Fever", 		new Color(0.77f*x,0.12f*x,0.23f*x));
	x += fac;
	ccolor.put("Death and Decay", 	new Color(0.77f*x,0.12f*x,0.23f*x));
	x += fac;
	ccolor.put("Blood Boil", 		new Color(0.77f*x,0.12f*x,0.23f*x));

	//Delraius
	x = 1f;
	ccolor.put("Unstable Affliction", 		new Color(0.58f*x,0.51f*x,0.79f*x));
	x += fac;
	ccolor.put("Shadow Bolt", 		new Color(0.58f*x,0.51f*x,0.79f*x));
	x += fac;
	ccolor.put("Corruption", 		new Color(0.58f*x,0.51f*x,0.79f*x));
	x += fac;
	ccolor.put("Drain Soul", 		new Color(0.58f*x,0.51f*x,0.79f*x));
	x += fac;
	ccolor.put("Bane of Doo,", 		new Color(0.58f*x,0.51f*x,0.79f*x));
	x += fac;
	ccolor.put("Haunt", 			new Color(0.58f*x,0.51f*x,0.79f*x));
	x += fac;
	ccolor.put("Soul Link", 		new Color(0.58f*x,0.51f*x,0.79f*x));

	//Fellmuh+Madhorn
	x = 1f;
	ccolor.put("Lava Lash", 		new Color(0f*x,0.44f*x,0.87f*x));
	x += fac;
	ccolor.put("Flametongue Attack", new Color(0f*x,0.44f*x,0.87f*x));
	x += fac;
	ccolor.put("Windfury Attack", 	new Color(0f*x,0.44f*x,0.87f*x));
	x += fac;
	ccolor.put("Stormsrike", 		new Color(0f*x,0.44f*x,0.87f*x));
	x += fac;
	ccolor.put("Lightning Bolt", 	new Color(0f*x,0.44f*x,0.87f*x));
	x += fac;
	ccolor.put("Flame Shock", 		new Color(0f*x,0.44f*x,0.87f*x));
	x += fac;
	ccolor.put("Lightning Shield", 	new Color(0f*x,0.44f*x,0.87f*x));	
	
	
	
	
	
	}
	
}
