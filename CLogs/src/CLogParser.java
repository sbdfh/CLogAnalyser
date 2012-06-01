import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class CLogParser {

	protected String path;
	protected LinkedList<CLogEntry> log;

	public CLogParser(String path) {
		this.path = path;
	}

	public LinkedList<CLogEntry> getLog(){
		return log;
	}
	
	public void parse() {

		log = new LinkedList<CLogEntry>();

		try {

			FileInputStream fstream = new FileInputStream(this.path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;			
			while ((strLine = br.readLine()) != null) {
				int code = parseLine(strLine);
				if (code != 0)
					return;				
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected int parseLine(String line){

		CLogEntry entry = new CLogEntry();
		String[] tokens = line.split(",");

		String[] timeEvent = tokens[0].split(" ");		
		if (timeEvent.length != 4){
			System.err.println("TimeEvent error");
			return 1;		
		}
		String[] date = timeEvent[0].split("/");
		entry.month = Integer.parseInt(date[0]);
		entry.day = Integer.parseInt(date[1]);
		date = timeEvent[1].split(":");
		entry.hour = Integer.parseInt(date[0]);
		entry.minute = Integer.parseInt(date[1]);				
		entry.second = Integer.parseInt(date[2].substring(0, 2));
		entry.millis = Integer.parseInt(date[2].substring(3));	
		
		String[] event = timeEvent[3].split("_");		
		int suffixStart = parsePrefix(entry, event);	
		if (suffixStart == -1)
			return 1;
		int suffixCode = 0;
		if (suffixStart != -2)
			suffixCode = parseSuffix(entry, event, suffixStart);
		if (suffixCode != 0)
			return 1;
		
		if (tokens[2].equals("nil"))
			entry.sourceName = null;
		else
			entry.sourceName = tokens[2].substring(1, tokens[2].length()-1);
		if (tokens[6].equals("nil"))
			entry.targetName = null;
		else
			entry.targetName = tokens[6].substring(1, tokens[6].length()-1);
	
		if (entry.pre == CLogEntry.Prefix.RANGE ||
			entry.pre == CLogEntry.Prefix.SPELL ||
			entry.pre == CLogEntry.Prefix.SPELL_PERIODIC ||
			entry.pre == CLogEntry.Prefix.SPELL_BUILDING){
			parseSpellName(entry, tokens);
		}
		
		if (entry.pre == CLogEntry.Prefix.ENVIRONMENTAL){
			parseEnv(entry, tokens);
		}
		
		if (entry.pre != null){
			int sufInfStart = 0;
			switch (entry.pre){
			case SWING:
				sufInfStart = 9;
				break;
			case RANGE:
			case SPELL:
			case SPELL_PERIODIC:
			case SPELL_BUILDING:
				sufInfStart = 12;
				break;
			case ENVIRONMENTAL:
				sufInfStart = 10;
				break;
			}
			
			parseSuffixInformation(entry, tokens, sufInfStart);
		}			
		log.add(entry);
		return 0;
	}
	
	protected int parsePrefix(CLogEntry entry, String[] event){
		if (event[0].equals("SWING"))
			entry.pre = CLogEntry.Prefix.SWING;
		else if (event[0].equals("RANGE"))
			entry.pre = CLogEntry.Prefix.RANGE;
		else if (event[0].equals("SPELL")){
			if (event[1].equals("PERIODIC")){
				entry.pre = CLogEntry.Prefix.SPELL_PERIODIC;
				return 2;
			}
			else if (event[1].equals("BUILDING")){
				entry.pre = CLogEntry.Prefix.SPELL_BUILDING;
				return 2;
			}
			else
				entry.pre = CLogEntry.Prefix.SPELL;
		}		
		else if (event[0].equals("ENVIRONMENTAL"))
			entry.pre = CLogEntry.Prefix.ENVIRONMENTAL;
		else if (event[0].equals("UNIT")){
			entry.event = CLogEntry.Events.UNIT_DIED;
			return -2;
		}
		else if (event[0].equals("PARTY")){
			entry.event = CLogEntry.Events.PARTY_KILL;
			return -2;
		}		
		else if (event[0].equals("ENCHANT")){
			if (event[1].equals("APPLIED"))
				entry.event = CLogEntry.Events.ENCHANT_APPLIED;
			else if (event[1].equals("REMOVED"))
				entry.event = CLogEntry.Events.ENCHANT_REMOVED;
			else{
				System.err.println("Unknown Affix ENCHANT_"+event[1]);
				return -1;
			}
			return -2;
		}
		else if (event[0].equals("DAMAGE")){
			if (event[1].equals("SHIELD")){
				if (event.length == 3)
					entry.event = CLogEntry.Events.DAMAGE_SHIELD_MISSED;
				else 
					entry.event = CLogEntry.Events.DAMAGE_SHIELD;
			} else if (event[1].equals("SPLIT"))
				entry.event = CLogEntry.Events.DAMAGE_SPLIT;
			else{
				System.err.println("Unknown Affix DAMAGE_"+event[1]);
				return -1;
			}
			return -2;
		}
		else{
			System.err.println("Unknown Prefix "+event[0]);
			return -1;
		}
		return 1;
	}

	protected int parseSuffix (CLogEntry entry, String[] event, int start){
		
		if (start >= event.length){
			System.err.println("Error at Suffix (most probably Prefix error)");
			return 1;
		}
			
		
		if (event[start].equals("DAMAGE"))
			entry.suf = CLogEntry.Suffix.DAMAGE;
		else if (event[start].equals("MISSED"))
			entry.suf = CLogEntry.Suffix.MISSED;
		else if (event[start].equals("HEAL"))
			entry.suf = CLogEntry.Suffix.HEAL;
		else if (event[start].equals("ENERGIZE"))
			entry.suf = CLogEntry.Suffix.ENERGIZE;
		else if (event[start].equals("DRAIN"))
			entry.suf = CLogEntry.Suffix.DRAIN;
		else if (event[start].equals("LEECH"))
			entry.suf = CLogEntry.Suffix.LEECH;
		else if (event[start].equals("INTERRUPT"))
			entry.suf = CLogEntry.Suffix.INTERRUPT;
		else if (event[start].equals("DISPEL")){
			if (event.length == start+2)
				entry.suf = CLogEntry.Suffix.DISPEL_FAILED;
			else
				entry.suf = CLogEntry.Suffix.DISPEL;
		}		
		else if (event[start].equals("STOLEN"))
			entry.suf = CLogEntry.Suffix.STOLEN;
		else if (event[start].equals("EXTRA"))
			entry.suf = CLogEntry.Suffix.EXTRA_ATTACKS;
		else if (event[start].equals("AURA")){
			if (event[start+1].equals("APPLIED")){
				if (event.length == start+3)
					entry.suf = CLogEntry.Suffix.AURA_APPLIED_DOSE;
				else
					entry.suf = CLogEntry.Suffix.AURA_APPLIED;
			} else if (event[start+1].equals("REMOVED")){
				if (event.length == start+3)
					entry.suf = CLogEntry.Suffix.AURA_REMOVED_DOSE;
				else
					entry.suf = CLogEntry.Suffix.AURA_REMOVED;
			}else if (event[start+1].equals("REFRESH"))
				entry.suf = CLogEntry.Suffix.AURA_REFRESH;
			else if (event[start+1].equals("BROKEN")){
				if (event.length == start+3)
					entry.suf = CLogEntry.Suffix.AURA_BROKEN_SPELL;
				else
					entry.suf = CLogEntry.Suffix.AURA_BROKEN;
			} else{
				System.err.println("Unknown Suffix AURA_"+event[start+1]);
				return 1;
			}
		}			
		else if (event[start].equals("CAST")){
			if (event[start+1].equals("START"))
				entry.suf = CLogEntry.Suffix.CAST_START;
			else if (event[start+1].equals("SUCCESS"))
				entry.suf = CLogEntry.Suffix.CAST_SUCCESS;
			else if (event[start+1].equals("FAILED"))
				entry.suf = CLogEntry.Suffix.CAST_FAILED;
			else{
				System.err.println("Unknown Suffix CAST_"+event[start+1]);
				return 1;
			}
		}			
		else if (event[start].equals("INSTAKILL"))
			entry.suf = CLogEntry.Suffix.INSTAKILL;
		else if (event[start].equals("DURABILITY")){
			if (event.length == start+3)
				entry.suf = CLogEntry.Suffix.DURABILITY_DAMAGE_ALL;
			else
				entry.suf = CLogEntry.Suffix.DURABILITY_DAMAGE;
		}			
		else if (event[start].equals("CREATE"))
			entry.suf = CLogEntry.Suffix.CREATE;
		else if (event[start].equals("SUMMON"))
			entry.suf = CLogEntry.Suffix.SUMMON;
		else if (event[start].equals("RESURRECT"))
			entry.suf = CLogEntry.Suffix.RESURRECT;
		else {
			System.err.println("Unknown Suffix "+event[start]);
			return 1;
		}
		return 0;
	}
	
	protected void parseSpellName(CLogEntry entry, String[] tokens){
		entry.spellName = tokens[10].substring(1, tokens[10].length()-1);		
		if (entry.spellName.equals("Read")){
			entry.spellName = "Ready, Set, Aim...";
			for (int i = 11; i < tokens.length-2; ++i)
				tokens[i] = tokens[i+2];
		}
		if (entry.spellName.equals("Have Grou")){
			entry.spellName = "Have Group, Will Travel";
			for (int i = 11; i < tokens.length-1; ++i)
				tokens[i] = tokens[i+1];
		}		
		entry.school = parseSchool(Integer.parseInt(tokens[11].substring(2), 16));
	}
	
	protected void parseEnv(CLogEntry entry, String[] tokens){
		if (tokens[9].equals("FALLING"))
			entry.env = CLogEntry.EnvType.FALLING;
		else if (tokens[9].equals("DROWNING"))
			entry.env = CLogEntry.EnvType.DROWNING;
		else if (tokens[9].equals("FIRE"))
			entry.env = CLogEntry.EnvType.FIRE;
		else if (tokens[9].equals("LAVA"))
			entry.env = CLogEntry.EnvType.LAVA;
		else if (tokens[9].equals("SLIME"))
			entry.env = CLogEntry.EnvType.SLIME;
		else if (tokens[9].equals("FATIGUE"))
			entry.env = CLogEntry.EnvType.FATIGUE;
		else
			System.err.println("Unknown EnvType "+tokens[9]);
	}
	
	protected void parseSuffixInformation(CLogEntry entry, String[] tokens, int start){
		if (entry.suf == CLogEntry.Suffix.DAMAGE){
			entry.amount = Integer.parseInt(tokens[start]);
			entry.overkill = Integer.parseInt(tokens[start+1]);
			entry.resisted = Integer.parseInt(tokens[start+3]);
			entry.blocked = Integer.parseInt(tokens[start+4]);
			entry.absorbed = Integer.parseInt(tokens[start+5]);
			entry.crit = (tokens[start+6].equals("1"));
			entry.glancing = (tokens[start+7].equals("1"));
			entry.crushing = (tokens[start+8].equals("1"));
		}
		else if (entry.suf == CLogEntry.Suffix.MISSED){
			if (tokens[start].equals("DODGE"))
				entry.miss = CLogEntry.MissType.DODGE;
			else if (tokens[start].equals("PARRY"))
				entry.miss = CLogEntry.MissType.PARRY;
			else if (tokens[start].equals("BLOCK"))
				entry.miss = CLogEntry.MissType.BLOCK;
			else if (tokens[start].equals("DEFLECT"))
				entry.miss = CLogEntry.MissType.DEFLECT;
			else if (tokens[start].equals("ABSORB"))
				entry.miss = CLogEntry.MissType.ABSORB;
			else if (tokens[start].equals("EVADE"))
				entry.miss = CLogEntry.MissType.EVADE;
			else if (tokens[start].equals("IMMUNE"))
				entry.miss = CLogEntry.MissType.IMMUNE;
			else if (tokens[start].equals("MISS"))
				entry.miss = CLogEntry.MissType.MISS;
			else if (tokens[start].equals("REFLECT"))
				entry.miss = CLogEntry.MissType.REFLECT;
			else if (tokens[start].equals("RESIST"))
				entry.miss = CLogEntry.MissType.RESIST;
			else
				System.err.println("Unknown MissType "+tokens[start]);
			if (tokens.length > start+2)
				entry.amountMissed = Integer.parseInt(tokens[start+2]);
		}
		else if (entry.suf == CLogEntry.Suffix.HEAL){
			entry.amount = Integer.parseInt(tokens[start]);
			entry.overHeal = Integer.parseInt(tokens[start+1]);
			entry.absorbed = Integer.parseInt(tokens[start+2]);
			entry.crit = (tokens[start+3].equals("1"));
		}
		else if (entry.suf == CLogEntry.Suffix.ENERGIZE){
			entry.amount = Integer.parseInt(tokens[start]);
			entry.power = parsePower(Integer.parseInt(tokens[start+1]));
		}
		else if (entry.suf == CLogEntry.Suffix.DRAIN || 
				  entry.suf == CLogEntry.Suffix.LEECH){
			entry.amount = Integer.parseInt(tokens[start]);
			entry.power = parsePower(Integer.parseInt(tokens[start+1]));
			entry.extraAmount = Integer.parseInt(tokens[start+2]);
		}
		else if (entry.suf == CLogEntry.Suffix.	INTERRUPT ||
				  entry.suf == CLogEntry.Suffix.DISPEL_FAILED){
			entry.extraSpellName = tokens[start+1].substring(1, tokens[start+1].length()-1);
			entry.extraSchool = parseSchool(Integer.parseInt(tokens[start+2]));
			
		}
		else if (entry.suf == CLogEntry.Suffix.DISPEL ||
				  entry.suf == CLogEntry.Suffix.STOLEN){
			entry.extraSpellName = tokens[start+1].substring(1, tokens[start+1].length()-1);
			entry.extraSchool = parseSchool(Integer.parseInt(tokens[start+2]));
			if (tokens[start+3].equals("BUFF"))
				entry.aura = CLogEntry.AuraType.BUFF;
			else if (tokens[start+3].equals("DEBUFF"))
				entry.aura = CLogEntry.AuraType.DEBUFF;
			else
				System.err.println("Unknown Aura Type "+tokens[start+4]);
			
		}
		else if (entry.suf == CLogEntry.Suffix.EXTRA_ATTACKS){
			entry.amount = Integer.parseInt(tokens[start]);
		}
		else if (entry.suf == CLogEntry.Suffix.AURA_APPLIED ||
				  entry.suf == CLogEntry.Suffix.AURA_REMOVED ||
				  entry.suf == CLogEntry.Suffix.AURA_REFRESH ||
				  entry.suf == CLogEntry.Suffix.AURA_BROKEN){
			if (tokens[start].equals("BUFF"))
				entry.aura = CLogEntry.AuraType.BUFF;
			else if (tokens[start].equals("DEBUFF"))
				entry.aura = CLogEntry.AuraType.DEBUFF;
			else
				System.err.println("Unknwon Aura Type "+tokens[start]);
			
		}
		else if (entry.suf == CLogEntry.Suffix.AURA_APPLIED_DOSE ||
				  entry.suf == CLogEntry.Suffix.AURA_REMOVED_DOSE){
			if (tokens[start].equals("BUFF"))
				entry.aura = CLogEntry.AuraType.BUFF;
			else if (tokens[start].equals("DEBUFF"))
				entry.aura = CLogEntry.AuraType.DEBUFF;
			else
				System.err.println("Unknwon Aura Type "+tokens[start]);
			entry.amount = Integer.parseInt(tokens[start+1]);
			
		}
		else if (entry.suf == CLogEntry.Suffix.AURA_BROKEN_SPELL){
			entry.extraSpellName = tokens[start+1].substring(1, tokens[start+1].length()-1);
			entry.extraSchool = parseSchool(Integer.parseInt(tokens[start+2]));
			if (tokens[start+3].equals("BUFF"))
				entry.aura = CLogEntry.AuraType.BUFF;
			else if (tokens[start+3].equals("DEBUFF"))
				entry.aura = CLogEntry.AuraType.DEBUFF;
			else
				System.err.println("Unknwon Aura Type "+tokens[start+3]);
		}
		else if (entry.suf == CLogEntry.Suffix.CAST_FAILED){
			entry.fail = tokens[start];
		}
	}
	
	protected CLogEntry.SpellSchool parseSchool (int school){
		switch(school){
		case 1:
			return CLogEntry.SpellSchool.PHYSICAL;			
		case 2:
			return CLogEntry.SpellSchool.HOLY;			
		case 3:
			return CLogEntry.SpellSchool.HOLYSTRIKE;
		case 4:
			return CLogEntry.SpellSchool.FIRE;
		case 5:
			return CLogEntry.SpellSchool.FLAMESTRIKE;
		case 6:
			return CLogEntry.SpellSchool.HOLYFIRE;
		case 8: 
			return CLogEntry.SpellSchool.NATURE;
		case 9:
			return CLogEntry.SpellSchool.STORMSTRIKE;
		case 10:
			return CLogEntry.SpellSchool.HOLYSTORM;
		case 12:
			return CLogEntry.SpellSchool.FIRESTORM;
		case 16:
			return CLogEntry.SpellSchool.FROST;
		case 17:
			return CLogEntry.SpellSchool.FLAMESTRIKE;
		case 18:
			return CLogEntry.SpellSchool.HOLYFROST;
		case 20: 
			return CLogEntry.SpellSchool.FROSTFIRE;
		case 24:
			return CLogEntry.SpellSchool.FROSTSTORM;			
		case 28:
			return CLogEntry.SpellSchool.ELEMENTAL;
		case 32: 
			return CLogEntry.SpellSchool.SHADOW;
		case 33:
			return CLogEntry.SpellSchool.SHADOWSTRIKE;
		case 34:
			return CLogEntry.SpellSchool.TWILIGHT;
		case 36:
			return CLogEntry.SpellSchool.SHADOWFLAME;
		case 40:
			return CLogEntry.SpellSchool.SHADOWSTORM;
		case 48: 
			return CLogEntry.SpellSchool.SHADOWFROST;
		case 64:
			return CLogEntry.SpellSchool.ARCANE;
		case 65:
			return CLogEntry.SpellSchool.SPELLSTRIKE;
		case 66:
			return CLogEntry.SpellSchool.DIVINE;
		case 68:
			return CLogEntry.SpellSchool.SPELLFIRE;
		case 72:
			return CLogEntry.SpellSchool.SPELLSTORM;
		case 80:
			return CLogEntry.SpellSchool.SPELLFROST;			
		case 96:
			return CLogEntry.SpellSchool.SPELLSHADOW;
		case 124:
			return CLogEntry.SpellSchool.CHROMATIC;
		case 126:
			return CLogEntry.SpellSchool.MAGIC;
		case 127:
			return CLogEntry.SpellSchool.CHAOS;
		default:							
			return CLogEntry.SpellSchool.NOSCHOOL;
		}
	}
	
	protected CLogEntry.PowerType parsePower(int pow){
		switch (pow){
		case -2:
			return CLogEntry.PowerType.HEALTH;
		case 0:
			return CLogEntry.PowerType.MANA;
		case 1:
			return CLogEntry.PowerType.RAGE;
		case 2:
			return CLogEntry.PowerType.FOCUS;
		case 3:
			return CLogEntry.PowerType.ENERGY;
		case 4:
			return CLogEntry.PowerType.PET_HAPPINESS;
		case 5:
			return CLogEntry.PowerType.RUNES;
		case 6:
			return CLogEntry.PowerType.RUNIC_POWER;
		case 7:
			return CLogEntry.PowerType.SOUL_SHARD;
		case 8:
		case -8:
			return CLogEntry.PowerType.ECLIPSE_ENERGY;
		case 9:
			return CLogEntry.PowerType.HOLY_POWER;
		case 10:
			return CLogEntry.PowerType.SOUND;
		default:			
			return CLogEntry.PowerType.NOPOWER;
		}
	}
}
