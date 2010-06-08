package com.planet_ink.coffee_mud.core.collections;
import java.util.*;
/*
Copyright 2000-2010 Bo Zimmerman

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
public final class Triad<T,K,L> 
{
	public T first;
	public K second;
	public L third;
	public Triad(T frst, K scnd, L thrd)
	{
		first=frst;
		second=scnd;
		third=thrd;
	}
	public static final class FirstConverter<T,K,L> implements Converter<Triad<T,K,L>,T> 
	{
		public T convert(Triad<T, K,L> obj) { return obj.first;}
	}
	public static final class SecondConverter<T,K,L> implements Converter<Triad<T,K,L>,K> 
	{
		public K convert(Triad<T, K, L> obj) { return obj.second;}
	}
	public static final class ThirdConverter<T,K,L> implements Converter<Triad<T,K,L>,L>
	{
		public L convert(Triad<T, K, L> obj) { return obj.third;}
	}
}