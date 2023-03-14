/**
 * @author Wache Nicolas - 2023
 * 
 */

package fr.fms.business;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import fr.fms.dao.CourseDao;
import fr.fms.dao.CategoryDao;
import fr.fms.dao.CustomerDao;
import fr.fms.dao.Dao;
import fr.fms.dao.DaoFactory;
import fr.fms.dao.OrderDao;
import fr.fms.dao.UserDao;
import fr.fms.entities.Course;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;
import fr.fms.entities.User;

public class IBusinessImpl implements IBusiness {	
	private HashMap<Integer,Course> cart;
	private Dao<Course> courseDao = DaoFactory.getCourseDao();
	private Dao<User> userDao = DaoFactory.getUserDao();
	private Dao<Category> categoryDao = DaoFactory.getCategoryDao();
	private Dao<Order> orderDao = DaoFactory.getOrderDao();
	private Dao<OrderItem> orderItemDao = DaoFactory.getOrderItemDao();
	private Dao<Customer> customerDao = DaoFactory.getCustomerDao();
	
	public IBusinessImpl() {
		this.cart = new HashMap<Integer,Course>();
	}

	@Override
	public void addToCart(Course course) {
		Course art = cart.get(course.getId());
		if(art != null) {
			art.setQuantity(art.getQuantity() + 1);
		}
		else cart.put(course.getId(), course);
	}

	@Override
	public void rmFromCart(int id) {
		Course course = cart.get(id);
		if(course != null) {
			if(course.getQuantity() == 1)	cart.remove(id);
			else course.setQuantity(course.getQuantity() - 1);
		}				
	}

	@Override
	public ArrayList<Course> getCart() {
		return new ArrayList<Course> (cart.values());
	}

	@Override
	public int order(int idCustomer) {	
		if(customerDao.read(idCustomer) != null) {
			double total = getTotal(); 
			Order order = new Order(total, new Date(), idCustomer);
			if(orderDao.create(order)) {	
				for(Course article : cart.values()) {	
					orderItemDao.create(new OrderItem(0, article.getId(), article.getQuantity(), article.getPrice(), order.getIdOrder()));
				}
				return order.getIdOrder();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Course> readCourses() {
		return courseDao.readAll();
	}
	
	@Override
	public ArrayList<Category> readCategories() {
		return categoryDao.readAll();
	}

	@Override
	public Course readOneCourse(int id) {
		return courseDao.read(id);
	}

	@Override
	public ArrayList<Course> readCoursesByCatId(int id) {
		return ((CourseDao) courseDao).readAllByCat(id);
	}

	/**
	 * renvoi le total de la commande en cours
	 * @return total
	 */
	public double getTotal() {
		double [] total = {0};
		cart.values().forEach((a) -> total[0] += a.getPrice() * a.getQuantity()); 	
		return total[0];
	}

	public boolean isCartEmpty() {
		return cart.isEmpty();
	}
	
	public void clearCart() {
		cart.clear();		
	}

	public Category readOneCategory(int id) {
		return categoryDao.read(id);
	}

	@Override
	public ArrayList<Course> readCoursesByMode(String mode) {
		return ((CourseDao) courseDao).readAllByMode(mode);
	}

	@Override
	public ArrayList<Course> searchCoursesByWord(String word) {
		return ((CourseDao) courseDao).searchByWord(word);
	}
	@Override
	public boolean isAdmin (int id) {
		return ((UserDao) userDao).isAdmin(id);
	}

	@Override
	public boolean addNewCourse(Course course) {
		return ((CourseDao) courseDao).create(course);	
	}

	@Override
	public boolean deleteCourse(Course course) {
		return ((CourseDao) courseDao).delete(course);
		
	}

	@Override
	public boolean updateCourse(Course course) {
		return ((CourseDao) courseDao).update(course);
		
	}

	@Override
	public boolean addNewCategory(Category category) {
		return ((CategoryDao) categoryDao).create(category);
	}

	@Override
	public boolean deleteCategory(Category category) {
		return ((CategoryDao) categoryDao).delete(category);
	}

	@Override
	public boolean updateCategory(Category category) {
		return ((CategoryDao) categoryDao).update(category);
	}

	@Override
	public Order consultOneOrder(int id) {
		return ((OrderDao) orderDao).read(id);
	}

	@Override
	public ArrayList<Customer> consultCustomer() {
		return customerDao.readAll();
	}
}
