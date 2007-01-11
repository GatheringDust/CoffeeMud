package com.planet_ink.coffee_mud.core.interfaces;

import com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior;

/*
Copyright 2000-2007 Bo Zimmerman

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
* 
* Something that can behave -- means almost everything!
* @author Bo Zimmerman
*
*/
public interface Behavable {

    /**
     * Add a new behavior to this object.  After calling this method,
     * recoverEnvStats() should be called next in case this behavior object modifies the stats.
     * A Behavior with a given ID() can only be added once per object.
     * @see com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior
     * @see com.planet_ink.coffee_mud.core.interfaces.Environmental#recoverEnvStats()
     * @param to The behavior object to add.
     */
	public void addBehavior(Behavior to);
    /**
     * Delete a behavior from this object.  After calling this method,
     * recoverEnvStats() should be called next in case this behavior object modified the stats.
     * @see com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior
     * @see com.planet_ink.coffee_mud.core.interfaces.Environmental#recoverEnvStats()
     * @param to The behavior object to remove.
     */
	public void delBehavior(Behavior to);
    /**
     * The number of behaviors this object has.
     * @see com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior
     * @return the number of behaviors
     */
	public int numBehaviors();
    /**
     * Returns a behavior object on this object. May return null even if the index
     * is correct to mark a race condition.
     * @see com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior
     * @see com.planet_ink.coffee_mud.core.interfaces.Environmental#numBehaviors()
     * @param index which object to return
     * @return the behavior object
     */
	public Behavior fetchBehavior(int index);
    /**
     * Returns a behavior object listed on this object. The object will
     * be the one with the same ID() string as passed in.
     * @see com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior
     * @see CMObject#ID()
     * @return the behavior object
     */
	public Behavior fetchBehavior(String ID);

}
