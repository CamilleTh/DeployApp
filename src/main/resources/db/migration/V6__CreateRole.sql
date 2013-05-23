CREATE  TABLE IF NOT EXISTS `40853_intech`.`Role` (
  `idRole` INT NOT NULL AUTO_INCREMENT ,
  `nom` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`idRole`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `40853_intech`.`Role_has_Personne` (
  `Role_idRole` INT(11) NOT NULL AUTO_INCREMENT,
  `Personne_idPersonne` INT(11) NOT NULL ,
  PRIMARY KEY (`Role_idRole`, `Personne_idPersonne`) ,
  INDEX `fk_Role_has_Personne_Personne1_idx` (`Personne_idPersonne` ASC) ,
  INDEX `fk_Role_has_Personne_Role1_idx` (`Role_idRole` ASC) ,
  CONSTRAINT `fk_Role_has_Personne_Role1`
    FOREIGN KEY (`Role_idRole` )
    REFERENCES `40853_intech`.`Role` (`idRole` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Role_has_Personne_Personne1`
    FOREIGN KEY (`Personne_idPersonne` )
    REFERENCES `40853_intech`.`Personne` (`idPersonne` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
