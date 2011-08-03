package com.planet_ink.coffee_mud.Abilities.Misc;
import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
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

import java.util.*;

/*
   Copyright 2000-2011 Bo Zimmerman

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

@SuppressWarnings("unchecked")
public class Dueler extends StdAbility
{
    public String ID() { return "Dueler"; }
    public String name(){ return "Dueler";}
    protected Dueler otherDueler = null;
    protected long lastTimeISawYou=System.currentTimeMillis();
    protected boolean oldPVPStatus = false;
    protected CharState oldCurState = null;
    protected List<Ability> oldEffects = new LinkedList<Ability>();
    protected Hashtable<Item,Item> oldEq = new Hashtable<Item,Item>();
    
    public String displayText()
    { 
    	if(otherDueler != null)
	    	return "(Dueling "+otherDueler.affecting().name()+")";
    	return "";
    }
    protected int canAffectCode(){return Ability.CAN_MOBS;}
    protected int canTargetCode(){return 0;}

    public void unInvoke()
    {
        if(affected instanceof MOB)
        {
	        MOB mob=(MOB)affected;
	        Dueler oDA=otherDueler;
	        if(oDA!=null)
	        {
	        	otherDueler=null;
	        	oDA.otherDueler=null;
	        	oDA.unInvoke();
	        }
	        if((canBeUninvoked())
	        &&(!((MOB)affected).amDead())
	        &&(CMLib.flags().isInTheGame(affected,true)))
	        	((MOB)affected).tell("Your duel has ended.");
	        if(!oldPVPStatus) 
	        	mob.setBitmap(CMath.unsetb(mob.getBitmap(), MOB.ATT_PLAYERKILL));
	        oldCurState.copyInto(mob.curState());
	        LinkedList<Ability> cleanOut=new LinkedList<Ability>();
	    	for(Enumeration<Ability> a=mob.effects();a.hasMoreElements();)
	    	{
	    		final Ability A=a.nextElement();
	    		if(!oldEffects.contains(A))
	    			cleanOut.add(A);
	    	}
	    	for(Ability A : cleanOut)
    		{
    			A.unInvoke();
    			mob.delEffect(A);
    			A.destroy();
    		}
	    	for(Item I : oldEq.keySet())
	    	{
	    		Item copyI=oldEq.get(I);
	    		if(I.amDestroyed())
	    			mob.addItem(copyI);
	    		else
	    		if(I.usesRemaining() < copyI.usesRemaining())
	    			I.setUsesRemaining(copyI.usesRemaining());
	    	}
	        mob.recoverCharStats();
	        mob.recoverMaxState();
	        mob.recoverPhyStats();
			mob.makePeace();
        }
    	oldEffects.clear();
    	oldEq.clear();
    	oldCurState=null;
        super.unInvoke();
    }

	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;

		if((msg.sourceMinor()==CMMsg.TYP_DEATH)
		&&(msg.source()==affecting()))
		{
			MOB source=null;
			if((msg.tool()!=null)&&(msg.tool() instanceof MOB))
				source=(MOB)msg.tool();
			MOB target=msg.source();
			Room deathRoom=target.location();
			deathRoom.show(source,source,CMMsg.MSG_OK_VISUAL,msg.sourceMessage());
			target.makePeace();
			source.makePeace();
			unInvoke();
			return false;
		}
		return true;
	}
	
    public boolean tick(Tickable ticking, int tickID)
    {
        if(!super.tick(ticking,tickID))
        	return false;
        if((ticking instanceof MOB)&&(tickID==Tickable.TICKID_MOB))
        {
        	MOB mob=(MOB)ticking;
        	final Dueler tDuel=otherDueler;
        	if((tDuel==null)
        	||(tDuel.amDestroyed)
        	||(tDuel.unInvoked)
            ||(!(tDuel.affecting() instanceof MOB))
            ||(mob.amDead())
            ||(((MOB)tDuel.affecting()).amDead())
            ||(!(CMLib.flags().isInTheGame(mob, false)))
            ||(!(CMLib.flags().isInTheGame((MOB)tDuel.affecting(), false)))
            ||(mob.getVictim()==null)
            ||((mob.getVictim()!=tDuel.affecting())&&(mob.getVictim().amUltimatelyFollowing()!=tDuel.affecting()))
            )
        		unInvoke();
        	else
        	{
        		MOB tMOB=(MOB)tDuel.affecting();
            	if(mob.location()==tMOB.location())
            		lastTimeISawYou=System.currentTimeMillis();
            	if((System.currentTimeMillis()-lastTimeISawYou)>30000)
            		unInvoke();
        	}
        }
        return true;
    }
    
    public void init(MOB mob)
    {
    	oldPVPStatus=CMath.bset(mob.getBitmap(), MOB.ATT_PLAYERKILL);
    	oldCurState=(CharState)mob.curState().copyOf();
    	oldEffects.clear();
    	for(Enumeration<Ability> a=mob.personalEffects();a.hasMoreElements();)
    		oldEffects.add(a.nextElement());
    	for(Enumeration<Item> i=mob.items();i.hasMoreElements();)
    	{
    		Item I=i.nextElement();
    		if(((I instanceof Weapon)||(I instanceof Armor))
    		&&(!I.amWearingAt(Item.IN_INVENTORY)))
    			oldEq.put(I,(Item)I.copyOf());
    	}
    }

    public boolean invoke(MOB mob, Vector commands, Physical target, boolean auto, int asLevel)
    {
    	if(target==null) target=mob;
    	if(!(target instanceof MOB)) return false;
    	if(((MOB)target).location()==null) return false;
    	if(((MOB)target).location().show(mob,(MOB)target,this,CMMsg.MSG_OK_VISUAL,"^R<S-NAME> and <T-NAME> start(s) dueling <T-NAME>!^?"))
    	{
    		MOB tmob = (MOB)target;
    		Dueler A;
    		Dueler tA;
    		A=(Dueler)mob.fetchEffect(ID());
    		if(A!=null){ A.unInvoke(); mob.delEffect(A); }
    		A=(Dueler)tmob.fetchEffect(ID());
    		if(A!=null){ A.unInvoke(); tmob.delEffect(A); }
    		A=(Dueler)newInstance();
    		tA=(Dueler)newInstance();
    		A.otherDueler=tA;
    		tA.otherDueler=A;
    		A.init(mob);
    		tA.init(tmob);
    		mob.setVictim(tmob);
    		tmob.setVictim(mob);
    		A.startTickDown(mob, mob, Integer.MAX_VALUE-2);
    		tA.startTickDown(tmob, tmob, Integer.MAX_VALUE-2);
    	}
    	return true;
    }
}