CREATE  TABLE IF NOT EXISTS `Personne` (
  `idPersonne` INT NOT NULL AUTO_INCREMENT ,
  `prenom` VARCHAR(45) NULL ,
  `nom` VARCHAR(45) NULL ,
  `age` VARCHAR(45) NULL ,
  `role` VARCHAR(45) NULL ,
  `adresse` VARCHAR(512) NULL ,
  PRIMARY KEY (`idPersonne`) );
  
CREATE TABLE IF NOT EXISTS `Flipping` (
  `flip` BOOLEAN
);

INSERT INTO `Flipping` (`flip`) VALUES ('1');

INSERT INTO `40853_intech`.`Personne` (`idPersonne`, `prenom`, `nom`, `age`, `role`, `adresse`) VALUES (NULL, 'Camille', 'THOMASSIN', '23', 'Etudiant', '7 - rue de la liberte - 57130 - VERNEVILLE - FRANCE');
INSERT INTO `40853_intech`.`Personne` (`idPersonne`, `prenom`, `nom`, `age`, `role`, `adresse`) VALUES (NULL, 'Camille', 'THOMASSIN', '23', 'Stagiaire', '23 - rue de la tour - 57000 - THIONVILLE - FRANCE');
INSERT INTO `40853_intech`.`Personne` (`idPersonne`, `prenom`, `nom`, `age`, `role`, `adresse`) VALUES (NULL, 'Jean', 'BONBEUR', '25', 'Stagiaire', '42 -  rue de platafin - 57000 - THIONVILLE - FRANCE');