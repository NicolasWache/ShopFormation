/**
 * Composant d'accès aux données de la table T_Courses dans la base de données Shop
 * @author Wache Nicolas - 2023
 * 
 */

package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Course;

public class CourseDao implements Dao<Course> {

	public CourseDao() {
		
	}

	@Override
	public boolean create(Course obj) {
		String str = "INSERT INTO T_Courses (Name, Description, Duration, Mode, UnitaryPrice, IdCategory) VALUES (?,?,?,?,?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str)){
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setDouble(3, obj.getDuration());
			ps.setString(4, obj.getMode());
			ps.setDouble(5, obj.getPrice());
			if (obj.getidCategory() == 0) {
				ps.setNull(6, 0);
			} else {
				ps.setInt(6, obj.getidCategory());
			}
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'une formation " + e.getMessage());
		} 	
		return false;
	}

	@Override
	public Course read(int id) {
		try (Statement statement = connection.createStatement()){
			String str = "SELECT * FROM T_Courses where IdCourse=" + id + ";";									
			ResultSet rs = statement.executeQuery(str);
			if(rs.next()) return new Course(rs.getInt(1) , rs.getString(2) , rs.getString(3) , rs.getDouble(4), rs.getString(5), rs.getDouble(6), rs.getInt(7));
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'une formation" + e.getMessage());
		} 	
		return null;
	}

	@Override
	public boolean update(Course obj) {
		String str = "UPDATE T_Courses set Name=? , Description=? , Duration =? , Mode= ?, UnitaryPrice = ?, IdCategory = ? where idCourse=?;";	
		try (PreparedStatement ps = connection.prepareStatement(str)){				
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setDouble(3, obj.getDuration());
			ps.setString(4, obj.getMode());
			ps.setDouble(5, obj.getPrice());	
			if (obj.getidCategory() == 0) {
				ps.setNull(6, 0);
			} else {
				ps.setInt(6, obj.getidCategory());
			}	
			ps.setInt(7, obj.getId());
			if( ps.executeUpdate() == 1)	return true;
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mise à jour d'une formation " + e.getMessage());
		} 	
		return false;
	}

	@Override
	public boolean delete(Course obj) {
		try (Statement statement = connection.createStatement()){
			String str = "DELETE FROM T_Courses where IdCourse=" + obj.getId() + ";";									
			statement.executeUpdate(str);		
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'une formation " + e.getMessage());
		} 	
		return false;
	}

	@Override
	public ArrayList<Course> readAll() {
		ArrayList<Course> courses = new ArrayList<Course>();
		String strSql = "SELECT * FROM T_Courses";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					double rsDuration = resultSet.getDouble(4);	
					String rsMode = resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);	
					courses.add((new Course(rsId,rsName,rsDescription, rsDuration, rsMode,rsPrice)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des formations " + e.getMessage());
		}	
		catch (Exception e) {
			logger.severe("pb : " + e.getMessage());
		}
		return courses;
	}
	
	public ArrayList<Course> readAllByCat(int id) {
		ArrayList<Course> courses = new ArrayList<Course>();
		String strSql = "SELECT * FROM T_Courses where idCategory=" + id;		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					double rsDuration = resultSet.getDouble(4);	
					String rsMode = resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);	
					courses.add((new Course(rsId,rsName,rsDescription, rsDuration, rsMode,rsPrice)));					
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des formations par catégories " + e.getMessage());
		}			
		return courses;
	}
	public ArrayList<Course> readAllByMode(String mode) {
		ArrayList<Course> courses = new ArrayList<Course>();
		String strSql = "SELECT * FROM T_Courses where mode=" + "'" + mode + "'";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					double rsDuration = resultSet.getDouble(4);	
					String rsMode = resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);	
					courses.add((new Course(rsId,rsName,rsDescription, rsDuration, rsMode,rsPrice)));					
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des formations par catégories " + e.getMessage());
		}			
		return courses;
	}
	
	public ArrayList<Course> searchByWord(String word) {
		ArrayList<Course> courses = new ArrayList<Course>();
		String strSql = "SELECT * FROM T_Courses where name like '%" + word + "%'";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					double rsDuration = resultSet.getDouble(4);	
					String rsMode = resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);	
					courses.add((new Course(rsId,rsName,rsDescription, rsDuration, rsMode,rsPrice)));					
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur l'affichage des formations par catégories " + e.getMessage());
		}			
		return courses;
	}
	
	public boolean updateBeforeDeleteCategory(int id) {
		try (Statement statement = connection.createStatement()){
			String str = "UPDATE T_Courses set IdCategory =NULL  where idCategory=" + id + ";";									
			statement.executeUpdate(str);		
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'une formation " + e.getMessage());
		} 	
		return false;
	}
}
