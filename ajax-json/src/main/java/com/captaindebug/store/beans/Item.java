/*
 * http://www.javacodegeeks.com/2013/05/spring-mvc-ajax-and-json-part-1-setting-the-scene.html
 * http://www.javacodegeeks.com/2013/05/spring-mvc-ajax-and-json-part-2-the-server-side-code.html
 * http://www.javacodegeeks.com/2013/05/spring-mvc-ajax-and-json-part-3-the-client-side-code.html
 */

 package com.captaindebug.store.beans;

import java.math.BigDecimal;

/**
 * 
 * This models an item from the shop's catalogue...
 * 
 * @author Roger
 * 
 */
public class Item {

	private final int id;

	private final String description;

	private final String name;

	private final BigDecimal price;

	private Item(int id, String name, String description, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public final BigDecimal getPrice() {

		return price;
	}

	public final int getId() {

		return id;
	}

	public final String getDescription() {

		return description;
	}

	public final String getName() {

		return name;
	}

	public static Item getInstance(int id, String name, String description, BigDecimal price) {

		Item item = new Item(id, name, description, price);
		return item;
	}

}
