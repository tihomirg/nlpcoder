package test;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Skeleton implementation of City interface. Usable as-is or suitable for
 * extension.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */

interface City<T>{}

class State{
	
	public Country getCountry(){return null;}
}

class Country<B>{}

abstract class ACity{}

abstract class ACity2<K,V>{}

public class CityImpl<A extends Country<A>> extends ACity2<String,Boolean> implements City<A> {

	protected String id = "ovde";

	protected String name = id;

	protected State state;

	protected final Country<A> country = null;

	/**
	 * If state is set, will return state.getCountry(), otherwise will return
	 * local country field.
	 * 
	 * @see State#getCountry()
	 * @return Country object as set via setCountry or from state, may be null.
	 */
	public Country<? extends A> getCountry() {
		if (this.state == null) {
			return this.country;
		}
		return this.state.getCountry();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */

	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	public State getState() {
		return this.state;
	}

	/**
	 * Sets the ID.
	 * 
	 * @param id
	 */
	public void setId(final String id) {
		this.id = id; //Righthand-side comment under
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 */
	public void setName(final String name) {
		//Comment under
		this.name = name;
		//System.out.print("A"+this.name+"B"+"\n");
		
		for(int i= 0; i <10;i++){
			System.out.print("A");
		}
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 */
	public void setState(final State state) {
		this.state = state;
		
		// Comment below
	}

	public boolean is1(List<String> list){
		return false;
	}
	
	public <T> boolean is3(List<T> list){
		return false;
	}
	
	synchronized final public <K, V extends City> boolean is4(Map<K, V> list){
		return false;
	}
	
	public boolean is2(List<String> list, boolean... b){
		return false;
	}
	
}
