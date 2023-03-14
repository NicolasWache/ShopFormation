/**
 * @author Wache Nicolas - 2023
 * 
 */

package fr.fms.business;

import java.util.ArrayList;
import fr.fms.entities.Course;
import fr.fms.entities.Customer;
import fr.fms.entities.Order;
import fr.fms.entities.Category;

public interface IBusiness {	
	/**
	 * méthode qui ajoute un article au panier
	 * @param article à ajouter
	 */
	public void addToCart(Course Course);		
	
	/**
	 * méthode qui retire un article au panier s'il est dedans
	 * @param id de l'article à retirer
	 */
	public void rmFromCart(int id);		
	
	/**
	 * méthode qui renvoi sous la forme d'une liste tous les éléments du panier (gestion en mémoire)
	 * @return Liste d'articles du panier
	 */
	public ArrayList<Course> getCart();	
	
	/**
	 * méthode qui réalise la commande en base avec l'idUser + total de la commande en cours + date du jour + contenu du panier :
	 * - la méthode va céer une commande en base -> idOrder + montant + date + idUser
	 * - puis va ajouter autant de commandes minifiées associées : orderItem -> idOrderItem + idArticle + Quantity + Price + idOrder
	 * @param idUser est l'identifiant du client qui est passé commande
	 * @return 1 si tout est ok 0 si pb 
	 */
	public int order(int idUser);		
	
	/**
	 * méthode qui renvoi tous les articles de la table t_articles en bdd
	 * @return Liste d'articles en base
	 */
	public ArrayList<Course> readCourses();	
	
	/**
	 * méthode renvoie l'article correspondant à l'id
	 * @param id de l'article à renvoyer
	 * @return Course correspondant si trouvé, null sinon
	 */
	public Course readOneCourse(int id);	
	
	/**
	 * méthode qui renvoi toutes les catégories de la table t_catégories en bdd
	 * @return Liste de catégories en base
	 */
	public ArrayList<Category> readCategories();
	
	/**
	 * méthode qui renvoi une seule categorie
	 * @return Liste de catégories en base
	 * @return Category correspondant si trouvé, null sinon
	 */
	public Category readOneCategory(int id);
	
	/**
	 * méthode qui renvoi tous les articles d'une catégorie
	 * @param id de la catégorie
	 * @return Liste d'articles
	 */
	public ArrayList<Course> readCoursesByCatId(int idCat);
	
	/**
	 * méthode qui renvoi tous les articles d'un mode d'apprentissage
	 * @param type de mode
	 * @return Liste d'articles
	 */
	public ArrayList<Course> readCoursesByMode(String mode);
	
	/**
	 * méthode qui renvoi tous les articles d'un mode d'apprentissage
	 * @param type de mode
	 * @return Liste d'articles
	 */
	public ArrayList<Course> searchCoursesByWord(String word);
	/**
	 * méthode qui renvoi un booléen pour savoir si l'utilisateur est Admin
	 * @param id du user
	 * @return True = IsAdmin / False = IsNotAdmin
	 */
	public boolean isAdmin (int id);
	
	/**
	 * méthode qui ajoute une formation
	 * @param Object Course Nouvelle formation
	 * @return True si l'opération s'est bien deroulée. False si erreur
	 */
	public boolean addNewCourse (Course course);
	
	/**
	 * méthode qui supprime une formation
	 * @param Object Course
	 * @return True si l'opération s'est bien deroulée. False si erreur
	 */
	public boolean deleteCourse (Course course);
	
	/**
	 * méthode qui update une formation
	 * @param Object Course
	 * @return True si l'opération s'est bien deroulée. False si erreur
	 */
	public boolean updateCourse (Course course);
	
	/**
	 * méthode qui ajoute une Categorie
	 * @param Object Category 
	 * @return True si l'opération s'est bien deroulée. False si erreur
	 */
	public boolean addNewCategory (Category category);
	
	/**
	 * méthode qui supprime une Catégorie
	 * @param Object Category
	 * @return True si l'opération s'est bien deroulée. False si erreur
	 */
	public boolean deleteCategory (Category category);
	
	/**
	 * méthode qui update une catégorie
	 * @param Object Category
	 * @return True si l'opération s'est bien deroulée. False si erreur
	 */
	public boolean updateCategory (Category category);
	
	/**
	 * méthode qui permet de consulter les commandes d'un seul client
	 * @param id du client
	 * @return objet Order
	 */
	public Order consultOneOrder(int id);
	
	public ArrayList<Customer> consultCustomer ();
}
