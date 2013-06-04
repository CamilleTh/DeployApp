CREATE  TABLE IF NOT EXISTS `Login` (
  `idLogin` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NULL DEFAULT NULL ,
  `password` VARCHAR(45) NULL DEFAULT NULL ,
  `Personne_idPersonne` INT(11) NOT NULL ,
  PRIMARY KEY (`idLogin`, `Personne_idPersonne`) ,
  INDEX `fk_Login_Personne1_idx` (`Personne_idPersonne` ASC) ,
  CONSTRAINT `fk_Login_Personne1`
    FOREIGN KEY (`Personne_idPersonne` )
    REFERENCES `Personne` (`idPersonne` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

INSERT INTO `Login` (`username`, `password`, `Personne_idPersonne`) VALUES ('ctn', 'zfhizaegnana', '1');
