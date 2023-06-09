package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Category;
import fr.fms.entities.Order;

public class OrderDao implements Dao<Order> {

	@Override
	public boolean create(Order obj) {
		String str = "INSERT INTO T_Orders (Amount , IdCustomer) VALUES (?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str,Statement.RETURN_GENERATED_KEYS)){	
			ps.setDouble(1, obj.getAmount());
			ps.setInt(2, obj.getIdCustomer());
			ps.executeUpdate();
			try(ResultSet generatedKeySet = ps.getGeneratedKeys()){
				if(generatedKeySet.next()) {
					obj.setIdOrder(generatedKeySet.getInt(1));
					return true;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Order read(int id) {
		try (Statement statement = connection.createStatement()){
			String str = "SELECT * FROM T_Orders where IdCustomer=" + id + ";";									
			ResultSet rs = statement.executeQuery(str);
			if(rs.next()) return new Order(rs.getInt(1) , rs.getDouble(2) , rs.getDate(3));
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return null;
	}

	@Override
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ArrayList<Order> readOrderById(int id) {
		ArrayList<Order> orders = new ArrayList<Order>();
		String sql = "select * from T_Orders WHERE IdCustomer="+ id + ";";
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(sql)){
				while(resultSet.next()) {
					orders.add(new Order(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getDate(3)));
				}
				return orders;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}
}
