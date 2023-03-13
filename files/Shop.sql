-- ------------------------------------------------------------------------------
-- - Reconstruction de la base de données                                     ---
-- ------------------------------------------------------------------------------
DROP DATABASE IF EXISTS ShopForma;
CREATE DATABASE ShopForma;
USE ShopForma;

-- -----------------------------------------------------------------------------
-- - Construction de la table des utilisateurs                               ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Users (
	IdUser				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Login				varchar(20)	NOT NULL UNIQUE,
	Password			varchar(20)	NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 1, 'NicolasW' ,	'123456789' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 2, 'DylanD',	'123456789' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 3, 'BenoitT' ,	'123456789' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 4, 'MohamedEB'   ,	'123456789' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 5, 'CamilleG'     ,	'123456789' );

SELECT * FROM T_Users;

-- -----------------------------------------------------------------------------
-- - Construction de la table des clients	                                 ---
-- -----------------------------------------------------------------------------

CREATE TABLE T_Customers (
	IdCustomer				int(4)		PRIMARY KEY AUTO_INCREMENT,
	name					varchar(30)	NOT NULL,
	firstName				varchar(30)	NOT NULL,
	email 					varchar(30)	NOT NULL unique,
	phone 					varchar(20)	,
	address					varchar(50)	,
	idUser					int(4)		NOT NULL,
	FOREIGN KEY (idUser)	REFERENCES T_Users(idUser)
) ENGINE = InnoDB;

-- -----------------------------------------------------------------------------
-- - Construction de la table des Rôles	                                     ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Roles (	
	IdRole				int(4)		PRIMARY KEY AUTO_INCREMENT,
	RoleName			varchar(20) NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Roles (IdRole, RoleName) VALUES (1, 'Admin');

-- -----------------------------------------------------------------------------
-- - Construction de la table des User_Roles,                                ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_User_Roles (	
	IdRole				int(4)		PRIMARY KEY, FOREIGN KEY (idRole)	REFERENCES T_Roles(idRole),
	IdUser				int(4)      NOT NULL, 
	FOREIGN KEY (idUser)	REFERENCES T_Users(idUser)
) ENGINE = InnoDB;

INSERT INTO T_User_Roles (IdRole, IdUser) VALUES (1,1);
-- -----------------------------------------------------------------------------
-- - Construction de la table des catégories d'articles
-- -----------------------------------------------------------------------------

CREATE TABLE T_Categories (
	IdCategory 				INT(4) 		 PRIMARY KEY AUTO_INCREMENT,
	CatName 				VARCHAR(30)  NOT NULL,
	Description 			VARCHAR(100) NOT NULL
) ENGINE = InnoDB;

insert into T_Categories (IdCategory, CatName, Description) values (1 , 'DevWeb' , 'Formation sur des langages de Developpement Web');
insert into T_Categories (IdCategory, CatName, Description) values (2 , 'Windows' , 'Formation sur le systeme d exploitation Windows');
insert into T_Categories (IdCategory, CatName, Description) values (3 , 'Office' , 'Formation sur la suite Office');
insert into T_Categories (IdCategory, CatName, Description) values (4 , 'Adobe' , 'Formation sur la suite Adobe');

select * from t_categories;

-- -----------------------------------------------------------------------------
-- - Construction de la table des articles en vente                         ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Articles (
	IdArticle				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Name					varchar(30) NOT NULL,
	Description				varchar(70)	NOT NULL,
	Duration				float (8) 	NOT NULL,
	Mode					varchar(10) NOT NULL,
	UnitaryPrice			float(8)	NOT NULL DEFAULT 0,
	IdCategory				int(4),
	FOREIGN KEY (IdCategory)	REFERENCES T_Categories(IdCategory)
) ENGINE = InnoDB;

INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'JAVA','Java SE 8 : Syntaxe et Poo', 20, 'Presentiel', 2200, 1 );
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'JAVA Avance', 'Exception, Fichiers, Jdbc, tread', 20,'Presentiel', 2500, 1);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'SPRING',	'Spring Core/MVC/Security',	20 ,'Presentiel', 2200, 1);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'PHP Frameworks', 'Symphony', 15, 'Distanciel', 1800, 1);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'C#', 'DoNet Core', 20,'Presentiel', 2500, 1);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Windows Debutant', 'Formation Debutant Windows', 25,'Presentiel', 1400, 2);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Windows Intermediaire'    , 'Formation Intermediaire Windows', 20,'Distanciel', 1800, 2);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Windows Avance', 'Formation avance Windows', 15,'Distanciel', 1800, 2);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Office Debutant', 'Formation Debutant pour Suite Office', 25,'Presentiel', 1500, 3);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Office Intermediaire', 'Formation Intermediaire pour Suite Office', 20,'Distanciel', 1800, 3);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Office Avance', 'Formation Avance pour Suite Office', 18,'Distanciel', 1800, 3);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Adobe Debutant', 'Formation Debutant pour Adobe Photoshop et Illustrator', 30,'Presentiel', 3000, 4);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Adobe Intermediaire', 'Formation Intermediaire pour Adobe Photoshop et Illustrator', 20,'Presentiel', 2500, 4);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Adobe Avance', 'Formation Avance pour Adobe Photoshop et Illustrator', 20,'Presentiel', 3000, 4);
INSERT INTO T_Articles ( Name, Description, Duration, Mode, UnitaryPrice , IdCategory  ) VALUES ( 'Adobe Expert', 'Formation Expert pour Adobe Photoshop et Illustrator', 15,'Presentiel', 2200, 4);


SELECT * FROM T_Articles;

-- select IdArticle,T_Articles.Description,Brand,UnitaryPrice,T_Articles.IdCategory,CatName,T_Categories.Description 
-- from t_articles inner join t_categories where t_articles.IdCategory = t_categories.IdCategory and IdArticle=1;

-- SELECT IdArticle,t_articles.Description,brand,UnitaryPrice,CatName FROM t_articles 
-- INNER JOIN t_categories WHERE t_articles.IdCategory=t_categories.IdCategory AND IdArticle>10 ORDER BY UnitaryPrice;

CREATE TABLE T_Orders (
	IdOrder				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Amount				float(4)	NOT NULL DEFAULT 0,
	DateOrder 			DATE		NOT NULL DEFAULT NOW(),
	IdCustomer          INT(4)   	NOT NULL,
	FOREIGN KEY(IdCustomer) REFERENCES T_Customers(IdCustomer)
) ENGINE = InnoDB;



CREATE TABLE T_Order_Items (
	IdOrderItem			int(4)	PRIMARY KEY AUTO_INCREMENT,
	
	IdArticle           INT(4)   NOT NULL,
	FOREIGN KEY(IdArticle) REFERENCES T_Articles(IdArticle),
	
	Quantity			FLOAT(4) NOT NULL DEFAULT 1,
	UnitaryPrice		FLOAT(4)	NOT NULL DEFAULT 0,
	
	IdOrder             INT(4)   NOT NULL,
	FOREIGN KEY(IdOrder) REFERENCES T_Orders(IdOrder)
) ENGINE = InnoDB;