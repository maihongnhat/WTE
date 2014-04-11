package com.fas.what2eat;

import java.io.Serializable;

public class Dish implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;

	public Dish(String dish) {
		this.name = dish;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
