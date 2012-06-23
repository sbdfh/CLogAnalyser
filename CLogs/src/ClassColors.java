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
	
	ccolor.put("Attack", 	new Color(0f,0.44f,0.87f));
	
	ccolor.put("Wrath", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Starfire", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Insect Swarm", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Starsurge", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Starfall", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Moonfire", 	new Color(1f,0.96f,0.41f));
	ccolor.put("Sunfire", 		new Color(0.77f,0.12f,0.23f));
	
	ccolor.put("Auto Shot", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Chimera Shot", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Steady Shot", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Wild Quiver", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Aimed Shot", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Arcane Shot", 	new Color(1f,0.96f,0.41f));
	
	ccolor.put("Fireball", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Ignite", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Pyroblast", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Combustion", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Living Bomb", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Flame Orb", 	new Color(1f,0.96f,0.41f));

	ccolor.put("Templar's Verdict", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Hand of Light", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Crusader Strike", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Censure", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Seal of Truth", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Judgement of Truth", 	new Color(1f,0.96f,0.41f));
	
	ccolor.put("Mind Flay", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Mind Blast", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Vampiric Touch", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Shadow Word: Pain", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Devouring Plague", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Shadow Word: Plague", 	new Color(1f,0.96f,0.41f));

	ccolor.put("Sinister Strike", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Instant Poison", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Main Gauche", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Deadly Poison", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Eviscerate", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Rupture", 	new Color(1f,0.96f,0.41f));
	
	ccolor.put("Death Coil", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Scourge Strike", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Festering Strike", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Blood Plague", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Frost Fever", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Death and Decay", 	new Color(1f,0.96f,0.41f));
	
	ccolor.put("Unstable Affliction", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Shadow Bolt", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Corruption", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Drain Soul", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Bane of Doom", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Haunt", 	new Color(1f,0.96f,0.41f));
	
	ccolor.put("Lava Lash", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Flametongue Attack", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Windfury Attack", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Stormstrike", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Lightning Bolt", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Flame Shock", 	new Color(1f,0.96f,0.41f));

	ccolor.put("Heroic Strike", 	new Color(1f,0.49f,0.04f));
	ccolor.put("Shield Slam", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Cleave", 		new Color(0.78f,0.61f,0.43f));
	ccolor.put("Starsurge", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Revenge", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Deep Wounds", 	new Color(1f,0.96f,0.41f));
	ccolor.put("Thunderclap", 		new Color(0.77f,0.12f,0.23f));

	ccolor.put("Bloodthirst", 	new Color(0.67f,0.83f,0.45f));
	ccolor.put("Devastate", 		new Color(0.41f,0.8f,0.94f));
	ccolor.put("Whirlwind Maw", 		new Color(0.96f,0.55f,0.73f));
	ccolor.put("Raging Blow", 	new Color(1f,0.96f,0.41f));
	ccolor.put("Whirlwind", 		new Color(0.77f,0.12f,0.23f));
	
	}
	
}
