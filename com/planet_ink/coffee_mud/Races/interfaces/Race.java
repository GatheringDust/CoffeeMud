package com.planet_ink.coffee_mud.Races.interfaces;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;
import java.util.Vector;
/*
   Copyright 2000-2006 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
/**
 * A, well, Race
 * @author Bo Zimmerman
 */
public interface Race extends Tickable, StatsAffecting, MsgListener, CMObject
{
    /** Age constant for an infant */
	public final static int AGE_INFANT=0;
    /** Age constant for a toddler */
	public final static int AGE_TODDLER=1;
    /** Age constant for a child */
	public final static int AGE_CHILD=2;
    /** Age constant for a yound adultt */
	public final static int AGE_YOUNGADULT=3;
    /** Age constant for the mature adult */
	public final static int AGE_MATURE=4;
    /** Age constant for the middle aged adult*/
	public final static int AGE_MIDDLEAGED=5;
    /** Age constant for the old*/
	public final static int AGE_OLD=6;
    /** Age constant for the very old*/
	public final static int AGE_VENERABLE=7;
    /** Age constant for the very very old*/
	public final static int AGE_ANCIENT=8;
    /** Constant string list for the names of the age constants, in their order of value */
	public final static String[] AGE_DESCS={"Infant","Toddler","Child","Young adult","Adult", "Mature", "Old", "Venerable", "Ancient"};

	/**
	 * Return a nice, displayable name for this race
	 * @return the races name
	 */
	public String name();
	/**
	 * Which racial category this race falls in.
	 * @return racial category
	 */
	public String racialCategory();
	/**
	 * Returns one or a combination of the Area.THEME_*
	 * constants from the Area interface.  This bitmap
	 * then describes the types of areas, skills, and
	 * classes which can interact.
	 * This bitmap is also used to to tell whether
	 * the race is available for selection by users
	 * at char creation time, whether they can
	 * change to this race via spells, or whether
	 * the race is utterly unavailable to them.
	 * @see com.planet_ink.coffee_mud.Areas.interfaces.Area
	 * @return the availability/theme of this race
	 */
	public int availabilityCode();
	/**
	 * After a mob is set or changed to a new race, this method
	 * should be called to finalize or initialize any settings
	 * from this race.
	 * The verify flag is almost always true, unless the mob
	 * is a new player being created, in which case false is sent.
	 * @param mob the mob or player being set to this race
	 * @param verifyOnly true flag unless this is a new player character
	 */
	public void startRacing(MOB mob, boolean verifyOnly);
	/**
	 * Will initialize a player or mobs height and weight based
	 * on this races parameters.
	 * @see com.planet_ink.coffee_mud.Common.interfaces.EnvStats
	 * @param stats the EnvStats object to change
	 * @param gender the mobs gender 'M' or 'F'
	 */
	public void setHeightWeight(EnvStats stats, char gender);
	/**
	 * The minimum height of males of this race.
	 * @return minimum height of males in inches
	 */
	public int shortestMale();
	/**
	 * The minimum height of females of this race.
	 * @return minimum height of females in inches
	 */
	public int shortestFemale();
	/**
	 * The amount from 0-this to add to the minimum height
	 * to achieve a random height.
	 * @return a range of inches to add to the mimiumum height
	 */
	public int heightVariance();
	/**
	 * The lightest weight for a member of this race
	 * @return the lightest weight for something of this race
	 */
	public int lightestWeight();
	/**
	 * The amount from 0-this to add to the minumum weight
	 * to achieve a random weight.
	 * @return a range of pounds to add to the minimum weight
	 */
	public int weightVariance();
	/**
	 * Returns an integer array equal in size and index to the
	 * Race.AGE_* constants in the Race interface.  Each value
	 * in the index represents the first mudyear age of that
	 * age category.
	 * @see Race
	 * @return an integer array mapping ages to age categories
	 */
	public int[] getAgingChart();
	/**
	 * A bitmap showing which on locations a member of this
	 * race can not wear clothing, even if the members have one
	 * or more of the required limbs.  The bitmap is made from
	 * Item.WORN_* constant values.
	 * @see com.planet_ink.coffee_mud.Items.interfaces.Item
	 * @return the illegal wear location bitmap
	 */
	public long forbiddenWornBits();
	/**
	 * Returns an array indexed by body part codes as defined by
	 * the BODY_* constants in the Race interface.  Each value is
	 * either -1 to show that the body part does not apply, 0 to
	 * show that the body part is not found on this race, and 1 or
	 * more to show how many of that part this race normally has.
	 * @return an array of body parts
	 */
	public int[] bodyMask();
	/**
	 * Whether this race, generally speaking, can procreate.
	 * @return whether this race is capable of procreating
	 */
	public boolean fertile();
	/**
	 * Returns a vector of Item objects representing the standard
	 * clothing, weapons, or other objects commonly given to players
	 * of this race just starting out.
	 * @param myChar one who will receive the objects
	 * @return a vector of Item objects
	 */
	public Vector outfit(MOB myChar);
	/**
	 * Returns a description of the given mobs description, by
	 * consulting the mobs curState().getHitPoints method.
	 * @see com.planet_ink.coffee_mud.MOBS.interfaces.MOB#curState()
	 * @see com.planet_ink.coffee_mud.Common.interfaces.CharState#getHitPoints()
	 * @param mob the mob whose health to check
	 * @return a string describing his health
	 */
	public String healthText(MOB viewer, MOB mob);
	/**
	 * Returns a Weapon object representing what a member of this
	 * race fights with when unarmed.  This method may change what it
	 * returns on every call to mix things up a bit.
	 * @see com.planet_ink.coffee_mud.Items.interfaces.Weapon
	 * @return a Weapon object representing claws or teeth, etc..
	 */
	public Weapon myNaturalWeapon();
	/**
	 * Returns a Vector of RawMaterial objects (usually GenFoodResource, GenLiquidResource,
	 * or GenResource items) representing what is left over of a member of this race
	 * after they've been butchered and cut up.
	 * @see com.planet_ink.coffee_mud.Items.interfaces.RawMaterial
	 * @return a vector of rawmaterial objects
	 */
	public Vector myResources();
	/**
	 * Returns the corpse of a member of this race, populates it with the equipment of
	 * the given mob, and places it in the given room.  If the destroyBodyAfterUse returns
	 * true, it will also populate the body with the contents of the myResources method.
	 * @see #myResources()
	 * @param mob the mob to use as a template for the body
	 * @param room the room to place the corpse in
	 * @return the corpse generated and placed in the room
	 */
	public DeadBody getCorpseContainer(MOB mob, Room room);
    /**
     * Whether this race object represents a Generic Race, or one which is modifiable by
     * builders at run-time.
     * @return whether this race is modifiable at run-time.
     */
	public boolean isGeneric();
	/**
	 * If this race is modifiable at run time, this method will return an xml document
	 * describing the several attributes of this race.
	 * @see #isGeneric()
	 * @see #setRacialParms(String)
	 * @return an xml document describing this race
	 */
	public String racialParms();
	/**
	 * If this race is modifiable at run time, this method will use the given xml document
	 * describing the several attributes of this race to populate this races fields and attributes.
	 * @see #isGeneric()
	 * @see #racialParms()
	 * @param parms an xml document describing this race
	 */
	public void setRacialParms(String parms);
	/**
	 * Returns the string describing what folks see when a member of this race enters a room.
	 * Should give an idea of the gate or walking style of this race.
	 * @return what people see what this race enters a room
	 */
	public String arriveStr();
	/**
	 * Returns the string describing what folks see when a member of this race leaves a room.
	 * Should give an idea of the gate or walking style of this race.
	 * @return what people see what this race leaves a room
	 */
	public String leaveStr();
	/**
	 * This method is called whenever a player gains a level while a member of this race.  If
	 * there are any special things which need to be done to a player who gains a level, they
	 * can be done in this method.  By default, it doesn't do anything.
	 * @param mob the mob to level up
	 * @param gainedAbilityIDs the set of abilities/skill IDs gained during this leveling process
	 */
	public void level(MOB mob, Vector gainedAbilityIDs);

	/**
	 * Whenever a player or mob of this race gains experience, this method gets a chance
	 * to modify the amount before the gain actually occurs.  
	 * @param mob the player or mob gaining experience
	 * @param victim if applicable, the mob or player who died to give the exp
	 * @param amount the amount of exp on track for gaining
	 * @return the adjusted amount of experience to gain
	 */
	public int adjustExperienceGain(MOB mob, MOB victim, int amount);
	
	/**
	 * Whether this race can be associated with a character class.
	 * @see com.planet_ink.coffee_mud.CharClasses.interfaces.CharClass
	 * @return whether this race can have a class
	 */
	public boolean classless();
	/**
	 * Whether players of this race can be associated with an experience level.
	 * @return whether players of this race can have a level
	 */
	public boolean leveless();
	/**
	 * Whether players of this race can gain or lose experience points.
	 * @return whether players of this race can gain or lose experience points
	 */
	public boolean expless();

	/**
	 * Return a vector of skills, spells, and other abilities granted to the given
	 * mob of the given mobs level.
	 * @see com.planet_ink.coffee_mud.Abilities.interfaces.Ability
	 * @param mob the mob to grant the abilities to
	 * @return a vector of the Ability objects
	 */
	public Vector racialAbilities(MOB mob);
	/**
	 * Return a vector of skills, spells, and other abilities granted to the given
	 * mob of the given mobs level.  This method is not functionally used because
	 * it doesn't quite work correctly yet.
	 * @see com.planet_ink.coffee_mud.Abilities.interfaces.Ability
	 * @param mob the mob to grant the abilities to
	 * @return a vector of the Ability objects
	 */
	public Vector racialEffects(MOB mob);
	/**
	 * Apply any affects of the given mob at the given age to the given base and/or
	 * current char stats.
	 * @see com.planet_ink.coffee_mud.Common.interfaces.CharState
	 * @param mob the mob to apply changes to
	 * @param baseStats permanent charstats changes
	 * @param charStats temporary charstats changes
	 */
	public void agingAffects(MOB mob, CharStats baseStats, CharStats charStats);

	/**
     * Returns an array of the string names of those fields which are modifiable on this object at run-time by
     * builders.
     * @see Race#getStat(String)
     * @see Race#setStat(String, String)
     * @return list of the fields which may be set.
     */
	public String[] getStatCodes();
    /**
     * An alternative means of retreiving the values of those fields on this object which are modifiable at
     * run-time by builders.  See getStatCodes() for possible values for the code passed to this method.
     * Values returned are always strings, even if the field itself is numeric or a list.
     * @see Race#getStatCodes()
     * @param code the name of the field to read.
     * @return the value of the field read
     */
	public String getStat(String code);
    /**
     * An alternative means of setting the values of those fields on this object which are modifiable at
     * run-time by builders.  See getStatCodes() for possible values for the code passed to this method.
     * The value passed in is always a string, even if the field itself is numeric or a list.
     * @see Race#getStatCodes()
     * @param code the name of the field to set
     * @param val the value to set the field to
     */
	public void setStat(String code, String val);
    /**
     * Whether this object instance is functionally identical to the object passed in.  Works by repeatedly
     * calling getStat on both objects and comparing the values.
     * @see Race#getStatCodes()
     * @see Race#getStat(String)
     * @param E the race to compare this one to
     * @return whether this object is the same as the one passed in
     */
	public boolean sameAs(Race E);

	/** body part constant representing antenea*/
	public final static int BODY_ANTENEA=0;
	/** body part constant representing eyes */
	public final static int BODY_EYE=1;
	/** body part constant representing ears*/
	public final static int BODY_EAR=2;
	/** body part constant representing head*/
	public final static int BODY_HEAD=3;
	/** body part constant representing neck*/
	public final static int BODY_NECK=4;
	/** body part constant representing arm*/
	public final static int BODY_ARM=5;
	/** body part constant representing hand*/
	public final static int BODY_HAND=6;
	/** body part constant representing torso*/
	public final static int BODY_TORSO=7;
	/** body part constant representing legs*/
	public final static int BODY_LEG=8;
	/** body part constant representing feet*/
	public final static int BODY_FOOT=9;
	/** body part constant representing noses*/
	public final static int BODY_NOSE=10;
	/** body part constant representing gills*/
	public final static int BODY_GILL=11;
	/** body part constant representing mouth*/
	public final static int BODY_MOUTH=12;
	/** body part constant representing waists*/
	public final static int BODY_WAIST=13;
	/** body part constant representing tails*/
	public final static int BODY_TAIL=14;
	/** body part constant representing wings*/
	public final static int BODY_WING=15;
	/** the number of body part constants*/
	public final static int BODY_PARTS=16;
	/** constant string list naming each of the BODY_* constants in the order of their value*/
	public final static String[] BODYPARTSTR={
		"ANTENEA","EYE","EAR","HEAD","NECK","ARM","HAND","TORSO","LEG","FOOT",
		"NOSE","GILL","MOUTH","WAIST","TAIL","WING"};
	/** constant used to set and check the classless flag on generic races */
	public final static int GENFLAG_NOCLASS=1;
	/** constant used to set and check the levelless flag on generic races */
	public final static int GENFLAG_NOLEVELS=2;
	/** constant used to set and check the expless flag on generic races */
	public final static int GENFLAG_NOEXP=4;

	/** array mapping worn locations to body parts, indexed by body parts. */
	public final static long[] BODY_WEARVECTOR={
		Item.WORN_HEAD, // ANTENEA, having any of these removes that pos
		Item.WORN_EYES, // EYES, having any of these adds this position
		Item.WORN_EARS, // EARS, gains a wear position here for every 2
		Item.WORN_HEAD, // HEAD, gains a wear position here for every 1
		Item.WORN_NECK, // NECK, gains a wear position here for every 1
		Item.WORN_ARMS, // ARMS, gains a wear position here for every 2
		Item.WORN_HANDS, // HANDS, gains a wear position here for every 1
		Item.WORN_TORSO, // TORSO, gains a wear position here for every 1
		Item.WORN_LEGS, // LEGS, gains a wear position here for every 2
		Item.WORN_FEET, // FEET, gains a wear position here for every 2
		Item.WORN_HEAD, // NOSE, No applicable wear position for this body part
		Item.WORN_HEAD, // GILLS, No applicable wear position for this body part
		Item.WORN_MOUTH, // MOUTH, gains a wear position here for every 1
		Item.WORN_WAIST, // WAIST, gains a wear position here for every 1
		Item.WORN_BACK, // TAIL, having any of these removes that pos
		Item.WORN_BACK, // WINGS, having any of these removes that pos
	};
	/** 2 dimentional array, indexed first by body_ part constant, with each row
	 * having two values: the first being the Item.WORN_ location which is affected
	 * by having or losing this body part, and then the number of such body parts
	 * necessary to gain or lose one such wear location.  A value of -1 means N/A
	 */
	public final static long[][] BODY_WEARGRID={
		{Item.WORN_HEAD,-1}, // ANTENEA, having any of these removes that pos
		{Item.WORN_EYES,2}, // EYES, having any of these adds this position
		{Item.WORN_EARS,2}, // EARS, gains a wear position here for every 2
		{Item.WORN_HEAD,1}, // HEAD, gains a wear position here for every 1
		{Item.WORN_NECK,1}, // NECK, gains a wear position here for every 1
		{Item.WORN_ARMS,2}, // ARMS, gains a wear position here for every 2
		{Item.WORN_WIELD|Item.WORN_HELD|Item.WORN_HANDS
	     |Item.WORN_LEFT_FINGER|Item.WORN_LEFT_WRIST
		 |Item.WORN_RIGHT_FINGER|Item.WORN_RIGHT_WRIST,1}, // HANDS, gains a wear position here for every 1
			// lots of exceptions apply to the above
		{Item.WORN_TORSO|Item.WORN_BACK,1}, // TORSO, gains a wear position here for every 1
		{Item.WORN_LEGS,2}, // LEGS, gains a wear position here for every 2
		{Item.WORN_FEET,2}, // FEET, gains a wear position here for every 2
		{-1,-1}, // NOSE, No applicable wear position for this body part
		{-1,-1}, // GILLS, No applicable wear position for this body part
		{Item.WORN_MOUTH,1}, // MOUTH, gains a wear position here for every 1
		{Item.WORN_WAIST,1}, // WAIST, gains a wear position here for every 1
		{-1,-1}, // TAIL, having any of these removes that pos
		{Item.WORN_BACK,-1}, // WINGS, having any of these removes that pos
	};
}
