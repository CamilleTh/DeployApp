ALTER TABLE `Personne` DROP `role`;

INSERT IGNORE INTO `Role_has_Personne` (`Role_idRole`, `Personne_idPersonne`) VALUES ('2', '1');
