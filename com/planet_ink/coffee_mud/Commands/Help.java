package com.planet_ink.coffee_mud.Commands;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;
import java.io.*;

/* 
   Copyright 2000-2004 Bo Zimmerman

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
public class Help extends StdCommand
{
	public Help(){}

	private String[] access={"HELP"};
	public String[] getAccessWords(){return access;}

	public boolean execute(MOB mob, Vector commands)
		throws java.io.IOException
	{
		String helpStr=Util.combine(commands,1);
		if(MUDHelp.getHelpFile().size()==0)
		{
			mob.tell("No help is available.");
			return false;
		}
		StringBuffer thisTag=null;
		if(helpStr.length()==0)
			thisTag=Resources.getFileResource("help"+File.separatorChar+"help.txt");
		else
			thisTag=MUDHelp.getHelpText(helpStr,MUDHelp.getHelpFile(),mob);
		if((thisTag==null)&&(CMSecurity.isAllowed(mob,mob.location(),"AHELP")))
			thisTag=MUDHelp.getHelpText(helpStr,MUDHelp.getArcHelpFile(),mob);
		if(thisTag==null)
		{
			mob.tell("No help is available on '"+helpStr+"'.\nEnter 'COMMANDS' for a command list, or 'TOPICS' for a complete list, or 'HELPLIST' to search.");
			Log.errOut("Help",mob.Name()+" wanted help on "+helpStr);
		}
		else
		if(!mob.isMonster())
			mob.session().wraplessPrintln(thisTag.toString());
		return false;
	}
	public int ticksToExecute(){return 0;}
	public boolean canBeOrdered(){return true;}

	public int compareTo(Object o){ return CMClass.classID(this).compareToIgnoreCase(CMClass.classID(o));}
}
