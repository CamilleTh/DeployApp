CREATE  TABLE IF NOT EXISTS `Personne` (
  `idPersonne` INT NOT NULL AUTO_INCREMENT ,
  `prenom` VARCHAR(45) NULL ,
  `nom` VARCHAR(45) NULL ,
  `age` VARCHAR(45) NULL ,
  `adresse` VARCHAR(512) NULL ,
  PRIMARY KEY (`idPersonne`) );
  
CREATE TABLE IF NOT EXISTS `Flipping` (
  `flip` BOOLEAN
);

INSERT INTO `Flipping` (`flip`) VALUES ('1');

INSERT INTO `40853_intech`.`Personne` (`idPersonne`, `prenom`, `nom`, `age`, `adresse`) VALUES (NULL, 'Camille', 'THOMASSIN', '23', '7 - rue de la liberté - 57130 - VERNEVILLE - FRANCE');