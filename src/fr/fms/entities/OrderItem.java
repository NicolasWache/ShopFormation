/**
 * @author Wache Nicolas - 2023
 * 
 */

package fr.fms.entities;

public class OrderItem {
	private int idOrderItem;

	private int idCourse;
	private int quantity;
	private double unitaryPrice;
	
	private int idOrder;

	public OrderItem(int idOrderItem, int idCourse, int quantity, double unitaryPrice, int idOrder) {
		this.idOrderItem = idOrderItem;
		this.idCourse = idCourse;
		this.quantity = quantity;
		this.unitaryPrice = unitaryPrice;
		this.idOrder = idOrder;
	}

	public int getIdOrderItem() {
		return idOrderItem;
	}

	public void setIdOrderItem(int idOrderItem) {
		this.idOrderItem = idOrderItem;
	}

	public int getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitaryPrice() {
		return unitaryPrice;
	}

	public void setUnitaryPrice(double unitaryPrice) {
		this.unitaryPrice = unitaryPrice;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
}
