package com.ajah.geo;

import java.io.*;

/**
 * Skeleton implementation of City interface. Usable as-is or suitable for
 * extension.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class CityImpl implements City {

	protected String id = "ovde";

	protected String name = id;

	protected State state;

	protected Country country = null;

	/**
	 * If state is set, will return state.getCountry(), otherwise will return
	 * local country field.
	 * 
	 * @see State#getCountry()
	 * @return Country object as set via setCountry or from state, may be null.
	 */
	@Override
	public Country getCountry() {
		if (this.state == null) {
			return this.country;
		}
		return this.state.getCountry();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
		
		List list = new LinkedList<Boolean>();
		
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

}
