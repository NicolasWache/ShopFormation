/**
 * Application console de vente de formation permettant d'exploiter une couche métier/dao pour créer un panier en ajoutant ou retirant des formations
 * puis passer commande à tout instant, cela génère une commande en base avec tous les éléments associés
 * @author Wache Nicolas - 2023
 * 
 */
package fr.fms.myShop;

import java.util.Scanner;

import fr.fms.authentication.Authenticate;
import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Course;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;

public class ShopApp {
	private static Scanner scan = new Scanner(System.in); 
	private static IBusinessImpl business = new IBusinessImpl();
	private static Authenticate authenticate = new Authenticate();

	public static final String TEXT_BLUE = "\u001B[36m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_RESET = "\u001B[0m";	
	private static final String COLUMN_ID = "IDENTIFIANT";
	private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	private static final String COLUMN_NAME = "Nom";
	private static final String COLUMN_DURATION = "DUREE";
	private static final String COLUMN_MODE = "MODE";
	private static final String COLUMN_PRICE = "PRIX";
	private static final String COLUMN_ORDER_NUMBER = "NUMERO DE COMMANDE";
	private static final String COLUMN_ID_COURSE = "IDENTIFIANT DU COURS";
	private static final String COLUMN_QUANTITY = "QUANTITE";

	private static int idUser = 0;
	private static String login = null; 
	private static boolean isAdmin = false;
	private static boolean isConnected = false;

	public static void main(String[] args) {
		System.out.println(" ____   _                                                       _                     _____  _                    ______                             ");
		System.out.println("|  _ \\ (_)                                                     | |                   / ____|| |                  |  ____|                            ");
		System.out.println("| |_) | _   ___  _ __ __   __ ___  _ __   _   _   ___      ___ | |__    ___  ____   | (___  | |__    ___   _ __  | |__  ___   _ __  _ __ ___    __ _ ");
		System.out.println("|  _ < | | / _ \\| '_ \\\\ \\ / // _ \\| '_ \\ | | | | / _ \\    / __|| '_ \\  / _ \\|_  /    \\___ \\ | '_ \\  / _ \\ | '_ \\ |  __|/ _ \\ | '__|| '_ ` _ \\  / _` |");
		System.out.println("| |_) || ||  __/| | | |\\ V /|  __/| | | || |_| ||  __/   | (__ | | | ||  __/ / /     ____) || | | || (_) || |_) || |  | (_) || |   | | | | | || (_| |");
		System.out.println("|____/ |_| \\___||_| |_| \\_/  \\___||_| |_| \\__,_| \\___|    \\___||_| |_| \\___|/___|   |_____/ |_| |_| \\___/ | .__/ |_|   \\___/ |_|   |_| |_| |_| \\__,_|");
		System.out.println("                                                              						  | |");  
		System.out.println("                                                                                   			  |_|");
		  
		  
		  
		                                        
		System.out.println("******************************** Bonjour et bienvenue dans notre  boutique, voici la liste des formations disponibles ********************************\n ");
		userMenu();
	}

	/**
	 * Méthode qui affiche le menu principal
	 */
	public static void displayMenu() {	
		if(login != null)	System.out.print(TEXT_BLUE + "Bienvenue  " + login);
		System.out.println("\n" + "Pour réaliser une action, tapez le code correspondant");
		System.out.println("1 : Ajouter une formation au panier");
		System.out.println("2 : Retirer une formation du panier");
		System.out.println("3 : Afficher mon panier + total pour passer commande");
		System.out.println("4 : Afficher toutes les formations en stock");
		System.out.println("5 : Afficher toutes les catégories en base");
		System.out.println("6 : Afficher toutes les formations d'une catégorie");
		System.out.println("7 : Afficher toutes les formations par mode d'apprentissage");
		System.out.println("8 : Rechercher une formation par mot clés");
		if (isConnected)
		System.out.println("9 : Deconnexion de votre compte");
		if (!isConnected)
		System.out.println("9 : Connexion à votre compte");
		System.out.println("10 : sortir de l'application");
		if (isAdmin)
			System.out.println("11 : Acceder à l'interface d'administration");
	}

	/**
	 * Methode qui permet de naviguer dans l'application
	 */
	public static void userMenu() {
		displayCourses();
		int choice = 0;
		while(choice != 10) {
			displayMenu();
			choice = scanInt();
			switch(choice) {
			case 1 : addCourse();				
			break;					
			case 2 : removeCourse();
			break;					
			case 3 : displayCart(true);
			break;					
			case 4 : displayCourses();
			break;						
			case 5 : displayCategories();
			break;
			case 6 : displayCoursesByCategoryId();
			break;
			case 7 : displayCoursesByMode();
			break;
			case 8 : searchCourses();
			break;
			case 9 : connection();
			break;
			case 10 : System.out.println("à bientôt dans notre boutique :)");
			break;	
			case 11 :adminInterface();
			break;
			default : System.out.println("veuillez saisir une valeur entre 1 et 10");
			}
		}
	}

	/**
	 * Méthode qui affiche toutes les formation en base
	 */
	public static void displayCourses() { 		
		System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
		System.out.printf("------------------------------------------------------------%n");
		business.readCourses().forEach( a -> System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",a.getId(),a.getName(),a.getDescription(),a.getDuration(), a.getMode(), a.getPrice()));

	}

	/**
	 * Méthode qui affiche les formations par catégories
	 */
	private static void displayCoursesByCategoryId() {
		displayCategories();
		System.out.println("saisissez l'id de la catégorie concerné");
		int id = scanInt();
		Category category = business.readOneCategory(id);
		if(category != null) {
			System.out.printf("              AFFICHAGE PAR CATEGORIE    %n");
			System.out.printf("                     %-10s               %n",category.getName());
			System.out.printf("------------------------------------------------------------%n");
			System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
			System.out.printf("------------------------------------------------------------%n");
			business.readCoursesByCatId(id).forEach( a -> System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",a.getId(),a.getName(),a.getDescription(),a.getDuration(), a.getMode(), a.getPrice()));
		}
		else System.out.println("cette catégorie n'existe pas !");
	}

	/**
	 * Méthode qui affiche toutes les formation en fonction du mode d'apprentissage
	 */
	private static void displayCoursesByMode() {
		System.out.println("Quelles types de formations recherchez vous ? ");
		System.out.println("[1]- Formation en Présentiel [2]- Formation en Distanciel");
		int id = scanInt();
		switch (id) {
		case 1:
			System.out.println("Voici la liste des formations en Présentiel");
			System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
			System.out.printf("------------------------------------------------------------%n");
			business.readCoursesByMode("Presentiel").forEach( a -> System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",a.getId(),a.getName(),a.getDescription(),a.getDuration(), a.getMode(), a.getPrice()));

			break;
		case 2: 
			System.out.println("Voici la liste des formations en distanciel");
			System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
			System.out.printf("------------------------------------------------------------%n");
			business.readCoursesByMode("Distanciel").forEach( a -> System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",a.getId(),a.getName(),a.getDescription(),a.getDuration(), a.getMode(), a.getPrice()));
			break;
		default: System.out.println("veuillez saisir une valeur entre 1 et 2");
		}
	}

	/**
	 * Méthode qui permet de rechercher par mot clés
	 */
	private static void searchCourses() {
		System.out.println("Saisissez le nom d'une formation recherché");
		String searchWord = scan.next();
		System.out.println("Voici la liste des formations contenant le mot clé " + searchWord);
		System.out.printf("%-10s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
		System.out.printf("------------------------------------------------------------%n");
		business.searchCoursesByWord(searchWord).forEach( a -> System.out.printf("%-10s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",a.getId(),a.getName(),a.getDescription(),a.getDuration(), a.getMode(), a.getPrice()));	
	}

	/**
	 * Méthode qui affiche le menu Admin
	 */
	private static void adminMenu() {

		System.out.println("\n" + "Pour réaliser une action, tapez le code correspondant");
		System.out.println("1 : Ajouter une formation");
		System.out.println("2 : Modifier une formation");
		System.out.println("3 : Supprimer une formation");
		System.out.println("4 : Ajouter une catégorie");
		System.out.println("5 : Modifier une catégorie");
		System.out.println("6 : Supprimer une catégorie");
		System.out.println("7 : Afficher les commandes d'un client");
		System.out.println("8 : Gerer les Administrateurs");
		System.out.println("9 : Quitter l'espace d'administration");
	}

	/**
	 * Méthode qui permet d'acceder à l'interface Administrateur
	 */
	private static void adminInterface() {
		if(login == null)	{ 
			System.out.println("Vous devez être connecté pour continuer");
			connection();
		}
		if (business.isAdmin(idUser)) {
			isAdmin = true;
			System.out.print(TEXT_RED);
			System.out.println("Félicitation " + login +" Vous etes connecté en tant qu'administrateur");
			int choiceAdmin = 0;

			while(choiceAdmin != 9) {
				adminMenu();
				choiceAdmin = scanInt();
				switch(choiceAdmin) {
				case 1 : newCourse();				
				break;					
				case 2 : updateCourse();
				break;					
				case 3 : deleteCourse();
				break;					
				case 4 : createCategory();
				break;						
				case 5 : updateCategory();
				break;
				case 6 : deleteCategory();
				break;
				case 7 : displayOrderbyId();
				break;
				case 8 : System.out.println();
				break;	
				case 9 : userMenu();
				break;
				default : System.out.println("veuillez saisir une valeur entre 1 et 9");
				}
			}
		}
	}

	/**
	 * Méthode qui permet de creer une nouvelle formation en BDD
	 */
	private static void newCourse() {
		String name;
		String description;
		double duration;
		String mode;
		double price;
		int category;
		scan.nextLine();
		System.out.println("Quel est le nom de la formation que vous souhaitez créer - (Le nombre de caractères autorisés est de 30) ");
		name = scan.nextLine();
		isCorrespondToBddString(30, name);
		System.out.println("Redigez une breve description de la formation - (Le nombre de caractères autorisés est de 70)");
		description = scan.nextLine();
		isCorrespondToBddString(70, description);
		System.out.println("Quelle est la durée de cette formation? (Le nombre uniquement) ");
		duration = scan.nextDouble();
		System.out.println("Quel est le mode de formation ? Distanciel ou Presentiel? - (Seuls presentiel et distanciel sont valides) ");
		scan.nextLine();
		mode = scan.nextLine();
		verifyMode(mode);
		isCorrespondToBddString(10, mode);
		System.out.println("Quel est le prix de la formation? (Prix uniquement)");
		price = scan.nextDouble();
		System.out.println("Quelle est la catégorie de la formation ?");
		displayCategories();
		System.out.println("[0] Aucune catégorie");
		category = scanInt();
		Course course = new Course(name, description, duration, mode, price, category);
		if (business.addNewCourse(course))
			System.out.println("La formation a été ajoutée avec succés");
	}

	/**
	 * Méthode qui permet de supprimer une  formation en BDD
	 */
	private static void deleteCourse() {
		displayCourses();
		System.out.println("Selectionnez l'ID de la formation que vous souhaitez supprimer");
		int idToDelete = scanInt();
		Course course = business.readOneCourse(idToDelete);
		System.out.println("Etes vous sur de vouloir supprimer la formation suivante? ");
		System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
		System.out.printf("%-11s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",course.getId(),course.getName(),course.getDescription(),course.getDuration(), course.getMode(), course.getPrice());	
		System.out.println("Oui ou non ? ");
		String response = scan.next();
		if (response.equalsIgnoreCase("oui")) {
			if (business.deleteCourse(course));
			System.out.println("La formation a été supprimée avec succés");
		} else {
			adminMenu();
		}	
	}
	/**
	 * Méthode qui affiche le menu de modification d'une formation
	 */
	private static void menuUpdate() {
		System.out.println("Que souhaitez vous modifier ? ");
		System.out.println("[1] Le Nom de la Formation");
		System.out.println("[2] La Description de la Formation");
		System.out.println("[3] La durée de la formation");
		System.out.println("[4] Le mode d'apprentissage de la formation");
		System.out.println("[5] Le prix de la formation");
		System.out.println("[6] La category de la formation");
		System.out.println("[7] Rien à modifier");
	}


	/**
	 * Méthode qui permet de update une  formation en BDD
	 */
	private static void updateCourse() {
		displayCourses();
		System.out.println("Selectionnez l'ID de la formation que vous souhaitez modifier");
		int idToUpdate = scanInt();
		Course course = business.readOneCourse(idToUpdate);
		int choice = 0;
		while (choice != 7) {
			menuUpdate();
			choice = scanInt();
			scan.nextLine();
			switch (choice) {
			case 1: 
				System.out.println("Veuillez entrer le nouveau nom de la formation  - (Le nombre de caractères autorisés est de 30)" );
				String name = scan.nextLine();
				isCorrespondToBddString(30, name);
				Course updateCourseName = new Course(course.getId(),name, course.getDescription(), course.getDuration(), course.getMode(), course.getPrice(), course.getidCategory());
				if (business.updateCourse(updateCourseName))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 2 : 
				System.out.println("Veuillez entrer la nouvelle description de la formation");
				String description = scan.nextLine();
				isCorrespondToBddString(30, description);
				Course updateCourseDescription = new Course(course.getId(),course.getName(), description, course.getDuration(), course.getMode(), course.getPrice(), course.getidCategory());
				if (business.updateCourse(updateCourseDescription))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 3:
				System.out.println("Veuillez entrer la nouvelle durée de la formation");
				double duration = scan.nextDouble();
				Course updateCourseDuration = new Course(course.getId(),course.getName(), course.getDescription(), duration, course.getMode(), course.getPrice(), course.getidCategory());
				if (business.updateCourse(updateCourseDuration))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 4:
				System.out.println("Veuillez entrer le nouveau mode d'apprentissage de la formation");
				String mode = scan.next();
				isCorrespondToBddString(10, mode);
				Course updateCourseMode = new Course(course.getId(),course.getName(), course.getDescription(), course.getDuration(), mode , course.getPrice(), course.getidCategory());
				if (business.updateCourse(updateCourseMode))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 5:
				System.out.println("Veuillez entrer le nouveau prix de la formation");
				double price = scan.nextDouble();
				Course updateCoursePrice = new Course(course.getId(),course.getName(), course.getDescription(), course.getDuration(), course.getMode(), price, course.getidCategory());
				if (business.updateCourse(updateCoursePrice))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 6:
				System.out.println("Veuillez entrer la nouvelle categorie de la formation");
				int category = scanInt();
				Course updateCourseCategory = new Course(course.getId(),course.getName(), course.getDescription(), course.getDuration(), course.getMode(), course.getPrice(), category);
				if (business.updateCourse(updateCourseCategory))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 7: adminMenu();
			break;
			default: System.out.println("Veuillez saisir un chiffre entre 1 et 7");
			}
		}
	}

	/**
	 * Méthode qui crée une catégorie
	 */
	private static void createCategory() {
		String name;
		String description;
		scan.nextLine();
		System.out.println("Quel est le nom de la catégorie que vous souhaitez créer");
		name = scan.nextLine();
		System.out.println("Redigez une breve description de la catégorie");
		description = scan.nextLine();
		Category newCat = new Category(name, description);
		if (business.addNewCategory(newCat))
			System.out.println("La formation a été ajoutée avec succés");
	}

	/**
	 * Méthode qui permet de update une  catégorie en BDD
	 */
	private static void updateCategory() {
		displayCategories();
		System.out.println("Selectionnez l'ID de la catégorie que vous souhaitez modifier");
		int idToUpdate = scanInt();
		Category category = business.readOneCategory(idToUpdate);
		int choice = 0;
		while (choice != 3) {
			System.out.println("Que souhaitez vous modifier ? ");
			System.out.println("[1] Le Nom de la Formation");
			System.out.println("[2] La Description de la Formation");
			System.out.println("[3] Rien à modifier");
			choice = scanInt();
			scan.nextLine();
			switch (choice) {
			case 1: 
				System.out.println("Veuillez entrer le nouveau nom de la Catégorie");
				String name = scan.nextLine();
				Category updateCategoryName = new Category(category.getId(),name, category.getDescription());
				if (business.updateCategory(updateCategoryName))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;
			case 2 : 
				System.out.println("Veuillez entrer la nouvelle description de la formation");
				String description = scan.nextLine();
				Category updateCategoryDescription = new Category(category.getId(),category.getName(), description);
				if (business.updateCategory(updateCategoryDescription))
					System.out.println("La mise à jour à bien été prise en compte ");
				break;

			case 3: adminMenu();
			break;
			default: System.out.println("Veuillez saisir un chiffre entre 1 et 7");
			}
		}
	}

	/**
	 * Méthode qui permet de supprimer une catégorie en BDD
	 */
	private static void deleteCategory() {
		displayCategories();
		System.out.println("Selectionnez l'ID de la formation que vous souhaitez supprimer");
		int idToDelete = scanInt();
		Category category = business.readOneCategory(idToDelete);
		System.out.println("Etes vous sur de vouloir supprimer la catégorie suivante ? ");
		System.out.println(category);
		System.out.println("Oui ou non ? ");
		String response = scan.next();
		if (response.equalsIgnoreCase("oui")) {
			if (business.updateBeforeDeleteCategory(idToDelete)  & business.deleteCategory(category));
			System.out.println("La formation a été supprimée avec succés");
		} else {
			adminMenu();
		}	
	}

	/**
	 * Méthode qui affiche toutes les catégories
	 */
	private static void displayCategories() {
		System.out.println(Category.centerString(COLUMN_ID) + Category.centerString(COLUMN_NAME) + Category.centerString(COLUMN_DESCRIPTION));
		business.readCategories().forEach(System.out::println);		
	}

	/**
	 * Méthode qui les commandes d'un client
	 */
	private static void displayOrderbyId() {
		business.consultCustomer().forEach(System.out::println);
		System.out.println("De Quel client souhaitez vous consulter les commandes? Saisissez l'ID ");
		int clientChoice = scanInt();
		System.out.println("CONTENU DES COMMANDES :");
		System.out.printf("%-20s | %-22s | %-20s | %-15s %n",COLUMN_ORDER_NUMBER,COLUMN_ID_COURSE,COLUMN_QUANTITY,COLUMN_PRICE);
		business.consultOrderById(clientChoice).forEach( a-> business.readOrderItem(a.getIdOrder()).forEach(b -> System.out.printf("%-20s | %-22s | %-20s | %-15s %n",b.getIdOrder(),b.getIdCourse(),b.getQuantity(),b.getUnitaryPrice()  )));		
	}

	/**
	 * Méthode qui supprime une formation du panier
	 */
	public static void removeCourse() {
		displayCart(false);
		int id = scanInt();
		business.rmFromCart(id);
		displayCart(false);
	}

	/**
	 * Méthode qui ajoute une formation au panier
	 */
	public static void addCourse() {
		displayCourses();
		System.out.println("Selectionner l'id de la formation à ajouter au panier");
		int id = scanInt();
		Course course = business.readOneCourse(id);
		if(course != null) {
			business.addToCart(course);
			displayCart(false);
		}
		else System.out.println("la formation que vous souhaitez ajouter n'existe pas, pb de saisi id");
	} 

	/**
	 * Méthode qui affiche le contenu du panier + total de la commande + propose de passer commande
	 */
	private static void displayCart(boolean flag) {
		if(business.isCartEmpty()) 	System.out.println("PANIER VIDE");
		else {
			System.out.println("CONTENU DU PANIER :");
			System.out.printf("%-10s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_DURATION, COLUMN_MODE,COLUMN_PRICE);
			business.getCart().forEach( a -> System.out.printf("%-10s | %-22s | %-60s | %-10s | %-10s | %-10s  %n",a.getId(),a.getName(),a.getDescription(),a.getDuration(), a.getMode(), a.getPrice()));
			if(flag) {
				System.out.println("MONTANT TOTAL : " + business.getTotal());
				System.out.println("Souhaitez vous passer commande ? Oui/Non :");
				if(scan.next().equalsIgnoreCase("Oui")) {
					nextStep();
				}
			}
		}
	}

	/**
	 * Méthode qui passe la commande, l'utilisateur doit être connecté
	 * si c'est le cas, l'utilisateur sera invité à associé un client à sa commande
	 * si le client n'existe pas, il devra le créer
	 * puis une commande associée au client sera ajoutée en base
	 * De même, des commandes minifiées seront associées à la commande
	 * une fois toutes les opérations terminées correctement, le panier sera vidé et un numéro de commande attribué
	 */
	private static void nextStep() {
		if(login == null)	{ 
			System.out.println("Vous devez être connecté pour continuer");
			connection();
		}
		if(login != null) {
			int idCustomer = newCustomer(idUser);	
			if(idCustomer != 0) {
				int idOrder = business.order(idCustomer);	
				if(idOrder == 0)	System.out.println("pb lors du passage de commande");
				else {
					System.out.println("Votre commande a bien été validé, voici son numéro : " + idOrder);
					business.clearCart();
				}
			}
		}
	}

	/**
	 * Méthode qui ajoute un client en base s'il n'existe pas déjà 
	 * @return id du client afin de l'associer à la commande en cours
	 */
	private static int newCustomer(int idUser) {
		System.out.println("Avez vous déjà un compte client ? saisissez une adresse email valide pour vérifier :");
		String email = scan.next();		
		if(isValidEmail(email)) {	
			Customer customer = authenticate.existCustomerByEmail(email);
			if(customer == null) {
				scan.nextLine();	
				System.out.println("saisissez votre nom :");
				String name = scan.nextLine();
				System.out.println("saisissez votre prénom :");
				String fName = scan.next();					
				System.out.println("saisissez votre tel :");
				String tel = scan.next();		
				scan.nextLine(); 
				System.out.println("saisissez votre adresse :");
				String address = scan.nextLine();
				Customer cust = new Customer(name, fName, email, tel, address, idUser); 
				if(authenticate.addCustomer(cust))	
					return authenticate.existCustomerByEmail(email).getIdCustomer();
			}
			else {
				System.out.println("Nous allons associer la commande en cours avec le compte client : " + customer);
				return customer.getIdCustomer();
			}
		}
		else System.out.println("vous n'avez pas saisi un email valide");	
		return 0;
	}

	/**
	 * Méthode qui réalise la connexion/deconnexion d'un utilisateur
	 * si l'utilisateur n'existe pas, il lui est proposé d'en créer un
	 */
	private static void connection() {
		if(login != null) {
			System.out.println("Souhaitez vous vous déconnecter ? Oui/Non");
			String response = scan.next();
			if(response.equalsIgnoreCase("Oui")) {
				System.out.println("A bientôt " + login + TEXT_RESET);
				login = null;
				idUser = 0;
				isAdmin =false;
				isConnected = false;
			}				
		}
		else {
			System.out.println("saisissez votre login : ");
			String log = scan.next();
			System.out.println("saisissez votre password : ");
			String pwd = scan.next();

			int id = authenticate.existUser(log,pwd);
			if(id > 0)	{
				login = log;
				idUser = id;
				isConnected = true;
				if (business.isAdmin(idUser))
					isAdmin = true;
				System.out.print(TEXT_BLUE);
			}
			else {
				System.out.println("login ou password incorrect");
				System.out.println("Souhaitez vous [1] reessayer ou [2] creer un compte");
				int choice = scanInt();
				if (choice ==1 ) {
					connection();
				} else {
					System.out.println("Nouvel utilisateur, pour créer un compte, tapez ok");
					String ok = scan.next();
					if(ok.equalsIgnoreCase("ok")) {
						newUser();
					}
				}
			}
		}
	}

	/**
	 * Méthode qui ajoute un nouvel utilisateur en base
	 */
	public static void newUser() {
		System.out.println("saisissez un login :");
		String login = scan.next();			
		int id = authenticate.existUser(login);	
		if(id == 0) { 
			System.out.println("saisissez votre password :");
			String password = scan.next();
			authenticate.addUser(login,password);		
			System.out.println("Ne perdez pas ces infos de connexion...");
			stop(2);
			System.out.println("création de l'utilisateur terminé, merci de vous connecter");
		}
		else	System.out.println("Login déjà existant en base, veuillez vous connecter");
	}

	public static void stop(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int scanInt() {
		while(!scan.hasNextInt()) {
			System.out.println("saisissez une valeur entière svp");
			scan.next();
		}
		return scan.nextInt();
	}
	
	public static int isCorrespondToBddInt (int maxValue, int userValue) {
		while (userValue > maxValue || userValue< 0) {
			System.out.println("Votre saisie est erronée, veuillez réessayer ");
			userValue=scanInt();
		}
			
		return userValue;
	}
	
	public static String isCorrespondToBddString( int maxValue, String userValue) {
		while (userValue.length() > maxValue ) {
			System.out.println(userValue.length());
			System.out.println("Votre saisie est erronée, veuillez réessayer ");
			userValue =scan.nextLine();
			
		}
		return userValue;
	}
	
	public static String verifyMode (String userValue) {
		while (userValue.equalsIgnoreCase("presentiel") ||userValue.equalsIgnoreCase("distanciel")) {
			System.out.println("Votre saisie ne corresponds pas à Distanciel ou Présentiel ");
			userValue= scan.nextLine();
		}
		return userValue;
	}

	public static boolean isValidEmail(String email) {
		String regExp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$";	
		return email.matches(regExp);
	}
}
