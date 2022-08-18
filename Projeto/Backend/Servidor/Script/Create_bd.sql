SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`d_celulares`  --  OK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`d_celulares` (
  `idCelulares` INT NOT NULL AUTO_INCREMENT,
  `modelo` VARCHAR(50) NOT NULL,
  `data_lancamento` DATE,
  `marca` VARCHAR(45) NULL,
  `qnt_ram` INT(2) NULL,
  `processador` VARCHAR(2000) NULL,
  `tela_tipo` VARCHAR(30) NULL,
  `polegadas_tela` FLOAT(4,2) NULL,
  `cameras` VARCHAR(200) NULL,
  `sistema_op` VARCHAR(50) NULL,
  `qnt_rom` INT(3) NULL,
  `bateria` INT(5) NULL,
  `resolu_tela` VARCHAR(45) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idCelulares`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`d_desktops`  --  SEM
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`d_desktops` (
  `idDesktop` INT NOT NULL AUTO_INCREMENT,
  `modelo` VARCHAR(50) NULL,
  `gabinete` VARCHAR(100) NULL,
  `processador` VARCHAR(100) NULL,
  `mem_armaz` VARCHAR(100) NULL,
  `mem_ram` VARCHAR(100) NULL,
  `fonte` VARCHAR(100) NULL,
  `placa_de_video` VARCHAR(100) NULL,
  `placa_mae` VARCHAR(100) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idDesktop`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`d_notebooks`  --  SEM
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`d_notebooks` (
  `idNotebooks` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(30) NULL,
  `modelo` VARCHAR(50) NULL,
  `mem_armaz` VARCHAR(30),
  `tam_mem_armaz` INT(3) NULL,
  `vel_rotacao` INT(4) NULL,
  `tipo_ram` VARCHAR(10) NULL,
  `qnt_ram` INT(2) NULL,
  `processador` VARCHAR(40) NULL,
  `cache` INT(2) NULL,
  `tipo_tela` VARCHAR(40) NULL,
  `proporcao` VARCHAR(6) NULL,
  `resolucao` VARCHAR(30) NULL,
  `entradas` VARCHAR(100) NULL,
  `qnt_entradas` INT(2) NULL,
  `dimensao` VARCHAR(50) NULL,
  `placa_video` VARCHAR(80) NULL,
  `peso` INT(5) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idNotebooks`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`d_tablets`  --  OK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`d_tablets` (
  `idTablets` INT NOT NULL AUTO_INCREMENT,
  `modelo` VARCHAR(50) NOT NULL,
  `data_lancamento` DATE,
  `marca` VARCHAR(45) NULL,
  `qnt_ram` INT(2) NULL,
  `processador` VARCHAR(200) NULL,
  `tela_tipo` VARCHAR(30) NULL,
  `polegadas_tela` FLOAT(4,2) NULL,
  `cameras` VARCHAR(100) NULL,
  `sistema_op` VARCHAR(40) NULL,
  `qnt_rom` INT(3) NULL,
  `bateria` INT(5) NULL,
  `resolu_tela` VARCHAR(45) NULL,
  `caneta` VARCHAR(3) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idTablets`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`p_gabinete`  --  OK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`p_gabinete` (
  `idGabinete` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(30) NULL,
  `modelo` VARCHAR(50) NULL,
  `fator_forma` VARCHAR(50) NULL,
  `slot_expans` INT(2) NULL,
  `dimensao` VARCHAR(30) NULL,
  `entradas` VARCHAR(100) NULL,
  `qnt_entradas` INT(1),
  `qnt_baias` INT(1) NULL,
  `tam_baias` VARCHAR(40),
  `qnt_cooler` INT(1) NULL,
  `cooler_tam` INT(3) NULL,
  `compatibilidade` VARCHAR(40) NULL,
  `peso` INT(5) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idGabinete`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`p_memoria_armaz`  --  OK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`p_memoria_armaz` (
  `idMemoria_armaz` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(30) NULL,
  `modelo` VARCHAR(50) NULL,
  `tipo_armaz` VARCHAR(5) NULL,
  `capacidade` INT(6) NULL,
  `vel_rotacao` INT(4) NULL,
  `tamanho` FLOAT(2,1) NULL,
  `interface` VARCHAR(20) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idMemoria_armaz`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`p_memoria_ram`  --  OK CORRIGIDO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`p_memoria_ram` (
  `idMemoria_ram` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(30) NULL,
  `modelo` VARCHAR(50) NULL,
  `tipo` VARCHAR(4) NULL,
  `tamanho` INT(2) NULL,
  `canal` VARCHAR(20) NULL,
  `frequencia` INT NULL,
  `latencia_cas` INT NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idMemoria_ram`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`p_placa_de_video`  --  OK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`p_placa_de_video` (
  `idPlaca_de_video` INT NOT NULL AUTO_INCREMENT,
  `fabricante` VARCHAR(30) NULL,
  `marca` VARCHAR(30) NULL,
  `modelo` VARCHAR(50) NULL,
  `serie` VARCHAR(100) NULL,
  `memoria` VARCHAR(30) NULL,
  `mem_clock` INT(4) NULL,
  `gpu_clock` INT(4) NULL,
  `nucleo_cuda` INT(3) NULL,
  `resolucao` VARCHAR(10) NULL,
  `tipo_saida` VARCHAR(30) NULL,
  `qnt_saida` INT(1) NULL,
  `dimensao` VARCHAR(30) NULL,
  `peso` INT(4) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idPlaca_de_video`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`p_placa_mae`  --  OK CORRIGIDO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`p_placa_mae` (
  `idPlaca_mae` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(30) NULL,
  `modelo` VARCHAR(50) NULL,
  `formato` VARCHAR(20),
  `chipset` VARCHAR(100),
  `slots_dimm` INT(1) NULL,
  `tipo_dimm` VARCHAR(10),
  `slots_pci` VARCHAR(100),
  `soquete` VARCHAR(100),
  `usb` VARCHAR(200),
  `visualizacao` int(5),
  PRIMARY KEY (`idPlaca_mae`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

  -- -----------------------------------------------------
-- Table `mydb`.`p_processador`  --  OK CORRIGIDO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`p_processador` (
  `idProcessadores` INT NOT NULL AUTO_INCREMENT,
  `fabricante` VARCHAR(90) NULL,
  `modelo` VARCHAR(50) NULL,
  `data_lancamento` DATE,
  `litografia` INT(2) NULL,
  `qnt_nucleo` INT(3) NULL,
  `qnt_thread` INT(3) NULL,
  `freq_ghz` FLOAT(3,2) NULL,
  `tdp` INT(3) NULL,
  `cache` VARCHAR(50) NULL,
  `chip_graf` VARCHAR(45) NULL,
  `soquete` VARCHAR(20) NULL,
  `visualizacao` int(5),
  PRIMARY KEY (`idProcessadores`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`l_lojas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`l_lojas` (
  `idLojas` INT NOT NULL AUTO_INCREMENT,
  `modelo` VARCHAR(100) NULL,
  `loja1` VARCHAR(50) NULL,
  `link1` VARCHAR(2000) NULL,
  `loja2` VARCHAR(50) NULL,
  `link2` VARCHAR(2000) NULL,
  `loja3` VARCHAR(50) NULL,
  `link3` VARCHAR(2000) NULL,
  PRIMARY KEY (`idLojas`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`l_descricao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`l_descricao` (
  `idDescricao` INT NOT NULL AUTO_INCREMENT,
  `modelo` VARCHAR(100) NOT NULL,
  `descricao` VARCHAR(1000) NULL,
  PRIMARY KEY (`idDescricao`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`u_favoritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`u_favoritos` (
  `idFavoritos` INT NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(100) NOT NULL,
  `modelo` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idFavoritos`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`u_setups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`u_setups` (
  `idSetups` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NULL,
  `nome` VARCHAR(100) NULL,
  `gabinete` VARCHAR(100) NULL,
  `processador` VARCHAR(100) NULL,
  `mem_armaz` VARCHAR(100) NULL,
  `mem_ram` VARCHAR(100) NULL,
  `placa_de_video` VARCHAR(100) NULL,
  `placa_mae` VARCHAR(100) NULL,
  PRIMARY KEY (`idSetups`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`u_usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`u_usuarios` (
  `idUsuarios` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `nome` VARCHAR(45) NULL,
  `senha` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idUsuarios`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;