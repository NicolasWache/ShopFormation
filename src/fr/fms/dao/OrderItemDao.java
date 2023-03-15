package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Category;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;

public class OrderItemDao implements Dao<OrderItem> {

	@Override
	public boolean create(OrderItem obj) {
		String str = "INSERT INTO T_Order_Items (IdCourse, Quantity, UnitaryPrice, IdOrder) VALUES (?,?,?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str)){	
			ps.setInt(1, obj.getIdCourse());
			ps.setInt(2, obj.getQuantity());
			ps.setDouble(3, obj.getUnitaryPrice());
			ps.setInt(4, obj.getIdOrder());
			
			ps.executeUpdate();			
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'un orderItem : " + e.getMessage());
		}
		return false;
	}

	
	public ArrayList<OrderItem> readAllbyId(int id) {
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		String sql = "SELECT * FROM T_Order_Items where IdOrder=" + id + ";";
		try(Statement statement = connection.createStatement()){
			try(ResultSet rs = statement.executeQuery(sql)){
				while(rs.next()) {
					orderItems.add(new OrderItem(rs.getInt(1) , rs.getInt(2) , rs.getInt(3),rs.getDouble(4),rs.getInt(5)));
				}
				return orderItems;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public boolean update(OrderItem obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(OrderItem obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<OrderItem> readAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public OrderItem read(int id) {
		// TODO Auto-generated method stub
		return null;
	}



}
