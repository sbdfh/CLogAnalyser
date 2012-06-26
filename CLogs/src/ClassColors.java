import java.util.HashMap;


public class ClassColors {

	HashMap<String,Color> ccolor = new HashMap<String,Color> ();
	
	public ClassColors () {
	// Klassenfarben													//H,S,V
	ccolor.put("Miracelwip", 			new Color(1f,0.49f,0.04f)); 	//28,96,100
	ccolor.put("Spatzenhirn", 			new Color(0.67f,0.83f,0.45f));  //85,46,83
	ccolor.put("Illiash", 				new Color(0.78f,0.61f,0.43f));	//30,45,78
	ccolor.put("Cursia", 				new Color(0.41f,0.8f,0.94f));	//200,59,100
	ccolor.put("Kr�mml", 				new Color(0.96f,0.55f,0.73f)); 	//334,43,96
	ccolor.put("Troublemaker", 			new Color(1f,0.96f,0.41f));		//56,59,100
	ccolor.put("Kangee", 				new Color(0.77f,0.12f,0.23f));	//333,84,77
	ccolor.put("Delraius", 				new Color(0.58f,0.51f,0.79f));	//255,35,79
	ccolor.put("Fellmuh", 				new Color(0f,0.44f,0.87f));		//210,100,87
	ccolor.put("Madhorn", 				new Color(0f,0.44f,0.87f));		//210,100,87
	ccolor.put("Recall", 				new Color(1f,0.9f,0.8f));		//31,20,100
	
	Color c0 = new Color(0f,0f,0f);
	Color c1 = new Color(0.75f,0f,0f);
	Color c2 = new Color(0f,0.75f,0f);
	Color c3 = new Color(0f,0f,0.75f);
	Color c4 = new Color(0.75f,0.75f,0f);
	Color c5 = new Color(0f,0.75f,0.75f);
	Color c6 = new Color(0.75f,0f,0.75f);

	ccolor.put("Attack", 				c0);
	ccolor.put("Auto Shot", 			c0);
	
	ccolor.put("Wrath", 				c1); 
	ccolor.put("Starfire", 				c2); 
	ccolor.put("Insect Swarm", 			c3); 
	ccolor.put("Starsurge", 			c4); 
	ccolor.put("Starfall", 				c5); 
	ccolor.put("Moonfire", 				c6);
	
	ccolor.put("Chimera Shot", 			c1);
	ccolor.put("Steady Shot", 			c2);
	ccolor.put("Wild Quiver", 			c3);
	ccolor.put("Aimed Shot", 			c4);
	ccolor.put("Arcane Shot", 			c5);
	
	ccolor.put("Fireball", 				c1);
	ccolor.put("Ignite", 				c2);
	ccolor.put("Pyroblast", 			c3);
	ccolor.put("Combustion", 			c4);
	ccolor.put("Living Bomb", 			c5);
	ccolor.put("Flame Orb", 			c6);

	ccolor.put("Templar's Verdict",		c1);
	ccolor.put("Hand of Light", 		c2);
	ccolor.put("Crusader Strike", 		c3);
	ccolor.put("Censure", 				c4);
	ccolor.put("Seal of Truth", 		c5);
	ccolor.put("Judgement of Truth",	c6);
	
	ccolor.put("Mind Flay", 			c1);
	ccolor.put("Mind Blast", 			c2);
	ccolor.put("Vampiric Touch", 		c3);
	ccolor.put("Shadow Word: Pain", 	c4);
	ccolor.put("Devouring Plague", 		c5);
	ccolor.put("Shadow Word: Plague",	c6);

	ccolor.put("Sinister Strike", 		c1);
	ccolor.put("Instant Poison", 		c2);
	ccolor.put("Main Gauche", 			c3);
	ccolor.put("Deadly Poison", 		c4);
	ccolor.put("Eviscerate", 			c5);
	ccolor.put("Rupture", 				c6);
	
	ccolor.put("Death Coil", 			c1);
	ccolor.put("Scourge Strike", 		c2);
	ccolor.put("Festering Strike", 		c3);
	ccolor.put("Blood Plague", 			c4);
	ccolor.put("Frost Fever", 			c5);
	ccolor.put("Death and Decay", 		c6);
	
	ccolor.put("Unstable Affliction",	c1);
	ccolor.put("Shadow Bolt", 			c2);
	ccolor.put("Corruption", 			c3);
	ccolor.put("Drain Soul", 			c4);
	ccolor.put("Bane of Doom", 			c5);
	ccolor.put("Haunt", 				c6);
	
	ccolor.put("Lava Lash", 			c1);
	ccolor.put("Flametongue Attack", 	c2);
	ccolor.put("Windfury Attack", 		c3);
	ccolor.put("Stormstrike", 			c4);
	ccolor.put("Lightning Bolt", 		c5);
	ccolor.put("Flame Shock", 			c6);

	ccolor.put("Heroic Strike", 		c1);
	ccolor.put("Shield Slam", 			c2);
	ccolor.put("Cleave", 				c3);
	ccolor.put("Revenge", 				c4);
	ccolor.put("Deep Wounds", 			c5);
	ccolor.put("Thunderclap", 			c6);

	ccolor.put("Bloodthirst", 			c2);
	ccolor.put("Devastate", 			c3);
	ccolor.put("Whirlwind Maw", 		c4);
	ccolor.put("Raging Blow", 			c5);
	ccolor.put("Whirlwind", 			c6);
	
	}
	
}
