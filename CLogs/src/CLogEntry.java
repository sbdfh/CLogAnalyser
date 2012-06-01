
public class CLogEntry {
	
	public int day, month, hour, minute, second, millis;
	public Prefix pre;
	public Suffix suf;
	public Events event;
	public EnvType env;
	public MissType miss;
	public String sourceName, targetName, spellName, extraSpellName;
	public SpellSchool school, extraSchool;
	public int amount, extraAmount, amountMissed, overkill, resisted, blocked, absorbed, overHeal;
	public PowerType power;
	public AuraType aura;
	public String fail;
	public boolean crit, glancing, crushing;
	
	public enum Prefix {SWING, RANGE, SPELL, 
		SPELL_PERIODIC, SPELL_BUILDING, ENVIRONMENTAL};
		
	public enum Suffix {DAMAGE, MISSED, HEAL, ENERGIZE,
		DRAIN, LEECH, INTERRUPT, DISPEL, DISPEL_FAILED, STOLEN,
		EXTRA_ATTACKS, AURA_APPLIED, AURA_REMOVED, AURA_APPLIED_DOSE,
		AURA_REMOVED_DOSE, AURA_REFRESH, AURA_BROKEN, AURA_BROKEN_SPELL,
		CAST_START, CAST_SUCCESS, CAST_FAILED, INSTAKILL, 
		DURABILITY_DAMAGE, DURABILITY_DAMAGE_ALL, CREATE, SUMMON, 
		RESURRECT};
		
	public enum Events {DAMAGE_SHIELD, DAMAGE_SPLIT, 
		DAMAGE_SHIELD_MISSED, ENCHANT_APPLIED, ENCHANT_REMOVED,
		PARTY_KILL, UNIT_DIED, UNIT_DESTROYED};
		
	public enum MissType {ABSORB, BLOCK, DEFLECT, DODGE, EVADE,
		IMMUNE, MISS, PARRY, REFLECT, RESIST};
		
	public enum AuraType {BUFF, DEBUFF};
	
	public enum EnvType {DROWNING, FALLING, FATIGUE, FIRE, LAVA, SLIME};
	
	public enum SpellSchool {PHYSICAL, HOLY, FIRE, NATURE, FROST, 
		SHADOW, ARCANE, HOLYSTRIKE, FLAMESTRIKE, HOLYFIRE, STORMSTRIKE,
		HOLYSTORM, FIRESTORM, FROSTSTRIKE, HOLYFROST, FROSTFIRE, 
		FROSTSTORM, SHADOWSTRIKE, TWILIGHT, SHADOWFLAME, SHADOWSTORM,
		SHADOWFROST, SPELLSTRIKE, DIVINE, SPELLFIRE, SPELLSTORM, 
		SPELLFROST, SPELLSHADOW, ELEMENTAL, CHROMATIC, MAGIC, CHAOS,
		NOSCHOOL};
		
	public enum PowerType {HEALTH, MANA, RAGE, FOCUS, ENERGY, 
		PET_HAPPINESS, RUNES, RUNIC_POWER, SOUL_SHARD, ECLIPSE_ENERGY,
		HOLY_POWER, SOUND, NOPOWER};

	
}
