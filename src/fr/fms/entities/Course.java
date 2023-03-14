/**
 * @author Wache Nicolas - 2023
 * 
 */

package fr.fms.entities;

public class Course {
	private int id;
	private String name;
	private String description;
	private double duration;
	private String mode;
	private double price;
	private int idCategory;
	private int quantity=1;
	
	public static final int MAX_STRING_LENGTH = 20;
	
	public Course(int id, String name, String description, double duration, String mode, double price, int idCategory) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.mode = mode;
		this.price = price;
		this.idCategory = idCategory;
	}
	
	public Course(int id, String name, String description, double duration, String mode, double price) {
		this.id = id;
		this.name=name;
		this.description = description;
		this.duration = duration;
		this.mode = mode;
		this.price = price;
	}
	
	public Course(String name, String description, double duration, String mode, double price, int idCategory) {
		this.id = 0;
		this.name=name;
		this.description = description;
		this.duration = duration;
		this.mode = mode;
		this.price = price;
		this.idCategory = idCategory;
	}	

	@Override
	public String toString() {
		return centerString(String.valueOf(id)) + centerString(name) + centerString(description) + centerString(String.valueOf(duration)) + centerString(mode) + centerString(String.valueOf(price) + centerString(String.valueOf(idCategory)));
	}
	
	public static String centerString(String str) {
		if(str.length() >= MAX_STRING_LENGTH) return str;
		String dest = "                    ";
		int deb = (MAX_STRING_LENGTH - str.length())/2 ;
		String data = new StringBuilder(dest).replace( deb, deb + str.length(), str ).toString();
		return data;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getidCategory() {
		return idCategory;
	}

	public void setCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
