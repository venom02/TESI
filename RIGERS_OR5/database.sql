SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `RIGERS` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `RIGERS` ;

-- -----------------------------------------------------
-- Table `RIGERS`.`Compartimento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Compartimento` (
  `idCompartimento` INT NOT NULL,
  `NomeCompartimento` VARCHAR(45) NULL,
  PRIMARY KEY (`idCompartimento`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Edificio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Edificio` (
  `idEdificio` INT NOT NULL,
  `idCompartimento` INT NOT NULL,
  `Indirizzo` VARCHAR(45) NULL,
  PRIMARY KEY (`idEdificio`),
  INDEX `fk_Edificio_Compartimento1_idx` (`idCompartimento` ASC),
  CONSTRAINT `fk_Edificio_Compartimento1`
    FOREIGN KEY (`idCompartimento`)
    REFERENCES `RIGERS`.`Compartimento` (`idCompartimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Dispositivo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Dispositivo` (
  `idDispositivo` INT NOT NULL,
  `idEdificio` INT NOT NULL,
  `NomeDispositivo` VARCHAR(45) NULL,
  PRIMARY KEY (`idDispositivo`, `idEdificio`),
  INDEX `fk_Dispositivo_Edificio1_idx` (`idEdificio` ASC),
  CONSTRAINT `fk_Dispositivo_Edificio1`
    FOREIGN KEY (`idEdificio`)
    REFERENCES `RIGERS`.`Edificio` (`idEdificio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Lettura_Dispositivo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Lettura_Dispositivo` (
  `idLettura` INT NOT NULL AUTO_INCREMENT,
  `Data_Lettura` DATETIME NOT NULL,
  `idDispositivo` INT NOT NULL,
  `idEdificio` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  INDEX `fk_Lettura_Dispositivo_Dispositivo1_idx` (`idDispositivo` ASC, `idEdificio` ASC),
  UNIQUE INDEX `idEdificio_UNIQUE` (`idEdificio` ASC, `idDispositivo` ASC, `Data_Lettura` ASC),
  CONSTRAINT `fk_Lettura_Dispositivo_Dispositivo1`
    FOREIGN KEY (`idDispositivo` , `idEdificio`)
    REFERENCES `RIGERS`.`Dispositivo` (`idDispositivo` , `idEdificio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Meter_Acqua`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Meter_Acqua` (
  `current_readout_value` INT NULL,
  `periodic_readout_value` INT NULL,
  `periodic_reading_date` DATETIME NULL,
  `idLettura` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  CONSTRAINT `fk_Meter_Acqua_Lettura_Dispositivo1`
    FOREIGN KEY (`idLettura`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo` (`idLettura`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Dispositivo_Pub`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Dispositivo_Pub` (
  `idDispositivo_Pub` INT NOT NULL,
  `idCompartimento` INT NOT NULL,
  `NomeDispositivo_Pub` VARCHAR(45) NULL,
  PRIMARY KEY (`idDispositivo_Pub`, `idCompartimento`),
  INDEX `fk_Dispositivo_Pub_Compartimento1_idx` (`idCompartimento` ASC),
  CONSTRAINT `fk_Dispositivo_Pub_Compartimento1`
    FOREIGN KEY (`idCompartimento`)
    REFERENCES `RIGERS`.`Compartimento` (`idCompartimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Lettura_Dispositivo_Pub`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Lettura_Dispositivo_Pub` (
  `idLettura_Pub` INT NOT NULL,
  `Data_Lettura_Pub` DATETIME NOT NULL,
  `idDispositivo_Pub` INT NOT NULL,
  `idCompartimento` INT NOT NULL,
  INDEX `fk_Lettura_Dispositivo_Pub_Dispositivo_Pub1_idx` (`idDispositivo_Pub` ASC, `idCompartimento` ASC),
  UNIQUE INDEX `Data_Lettura_Pub_UNIQUE` (`Data_Lettura_Pub` ASC, `idDispositivo_Pub` ASC, `idCompartimento` ASC),
  PRIMARY KEY (`idLettura_Pub`),
  CONSTRAINT `fk_Lettura_Dispositivo_Pub_Dispositivo_Pub1`
    FOREIGN KEY (`idDispositivo_Pub` , `idCompartimento`)
    REFERENCES `RIGERS`.`Dispositivo_Pub` (`idDispositivo_Pub` , `idCompartimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Meter_Termie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Meter_Termie` (
  `current_date_time` DATETIME NULL,
  `current_energy` INT NULL,
  `error_code` INT NULL,
  `current_total_volume` INT NULL,
  `current_flow` INT NULL,
  `current_performance` INT NULL,
  `return_flow_temperature` INT NULL,
  `temperature_difference` INT NULL,
  `current_impulse_count1` INT NULL,
  `current_impulse_count2` INT NULL,
  `idLettura` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  CONSTRAINT `fk_Meter_Termie_Lettura_Dispositivo1`
    FOREIGN KEY (`idLettura`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo` (`idLettura`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Meter_Gas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Meter_Gas` (
  `pm1_network_status` TINYINT NULL,
  `stato_valvola` TINYINT NULL,
  `comando_valvola` TINYINT NULL,
  `unix_time_eogd` DATETIME NULL,
  `daily_diagnostic` VARCHAR(45) NULL,
  `tot_vb_eogd_f1` INT NULL,
  `tot_vb_eogd_f2` INT NULL,
  `tot_vb_eogd_f3` INT NULL,
  `tot_va_eogd` INT NULL,
  `qbc_max` INT NULL,
  `qbc_max_timestamp` DATETIME NULL,
  `idLettura` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  CONSTRAINT `fk_Meter_Gas_Lettura_Dispositivo1`
    FOREIGN KEY (`idLettura`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo` (`idLettura`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Meter_Ripartitore_Calore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Meter_Ripartitore_Calore` (
  `unita_consumo` INT NULL,
  `idLettura` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  CONSTRAINT `fk_Meter_Ripartitore_Calore_Lettura_Dispositivo1`
    FOREIGN KEY (`idLettura`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo` (`idLettura`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Meter_Elettrico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Meter_Elettrico` (
  `tot_E_attiva_F1` INT NULL,
  `tot_E_attiva_F2` INT NULL,
  `tot_E_attiva_F3` INT NULL,
  `tot_E_reattiva` INT NULL,
  `energia_attiva_giornaliera` INT NULL,
  `energia_reattiva_giornaliera` INT NULL,
  `corrente_fase_1` INT NULL,
  `tensione_fase_1` INT NULL,
  `cosphi_fase_1` INT NULL,
  `corrente_fase_2` INT NULL,
  `tensione_fase_2` INT NULL,
  `cosphi_fase_2` INT NULL,
  `corrente_fase_3` INT NULL,
  `tensione_fase_3` INT NULL,
  `cosphi_fase_3` INT NULL,
  `THD_fatt_distorsione` INT NULL,
  `pot_attiva_fase1` INT NULL,
  `pot_reattiva_fase1` INT NULL,
  `pot_apparente_fase1` INT NULL,
  `pot_attiva_fase2` INT NULL,
  `pot_reattiva_fase2` INT NULL,
  `pot_apparente_fase2` INT NULL,
  `pot_attiva_fase3` INT NULL,
  `pot_reattiva_fase3` INT NULL,
  `pot_apparente_fase3` INT NULL,
  `idLettura` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  CONSTRAINT `fk_Meter_Elettrico_Lettura_Dispositivo1`
    FOREIGN KEY (`idLettura`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo` (`idLettura`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Meter_Sonde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Meter_Sonde` (
  `temp_locali` INT NULL,
  `temp_esterna` INT NULL,
  `temp_giorno` INT NULL,
  `luminosita` INT NULL,
  `solarimetro` INT NULL,
  `sismografo` INT NULL,
  `idLettura` INT NOT NULL,
  PRIMARY KEY (`idLettura`),
  CONSTRAINT `fk_Meter_Sonde_Lettura_Dispositivo1`
    FOREIGN KEY (`idLettura`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo` (`idLettura`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Parametri_elettrici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Parametri_elettrici` (
  `tot_E_attiva` INT NULL,
  `tot_E_reattiva` INT NULL,
  `energia_attiva_giornaliera` INT NULL,
  `energia_reattiva_giornaliera` INT NULL,
  `corrente_fase_1` INT NULL,
  `tensione_fase_1` INT NULL,
  `cosphi_fase_1` INT NULL,
  `corrente_fase_2` INT NULL,
  `tensione_fase_2` INT NULL,
  `cosphi_fase_2` INT NULL,
  `corrente_fase_3` INT NULL,
  `tensione_fase_3` INT NULL,
  `cosphi_fase_3` INT NULL,
  `THD_fatt_distorsione` INT NULL,
  `pot_attiva_fase1` INT NULL,
  `pot_reattiva_fase1` INT NULL,
  `pot_apparente_fase1` INT NULL,
  `pot_attiva_fase2` INT NULL,
  `pot_reattiva_fase2` INT NULL,
  `pot_apparente_fase2` INT NULL,
  `pot_attiva_fase3` INT NULL,
  `pot_reattiva_fase3` INT NULL,
  `pot_apparente_fase3` INT NULL,
  `idLettura_Pub` INT NOT NULL,
  PRIMARY KEY (`idLettura_Pub`),
  CONSTRAINT `fk_Parametri_elettrici_Lettura_Dispositivo_Pub1`
    FOREIGN KEY (`idLettura_Pub`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo_Pub` (`idLettura_Pub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Allarmi_stato_impianto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Allarmi_stato_impianto` (
  `stato_interr_magnetotermico` TINYINT NULL,
  `stato_differenziale_apparato` TINYINT NULL,
  `stato_on_off` TINYINT NULL,
  `assenza_rete_elettrica` TINYINT NULL,
  `mancato_on_tramonto` TINYINT NULL,
  `mancato_off_alba` TINYINT NULL,
  `assorbimento_sopra_soglia` INT NULL,
  `assorbimento_sotto_soglia` INT NULL,
  `apertura_quadro_elettrico` TINYINT NULL,
  `allarme_guasto_singolo_punto` TINYINT NULL,
  `idLettura_Pub` INT NOT NULL,
  PRIMARY KEY (`idLettura_Pub`),
  CONSTRAINT `fk_Allarmi_stato_impianto_Lettura_Dispositivo_Pub1`
    FOREIGN KEY (`idLettura_Pub`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo_Pub` (`idLettura_Pub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Telecontrollo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Telecontrollo` (
  `programm_orario_funzionamento` INT NULL,
  `comando_interruttore_magnetotermico` TINYINT NULL,
  `comando_apertura_differenziale` TINYINT NULL,
  `comando_on_off_manuale` TINYINT NULL,
  `dimmerizzazione_on_off` TINYINT NULL,
  `set_point_dimmer` INT NULL,
  `idLettura_Pub` INT NOT NULL,
  PRIMARY KEY (`idLettura_Pub`),
  CONSTRAINT `fk_Telecontrollo_Lettura_Dispositivo_Pub1`
    FOREIGN KEY (`idLettura_Pub`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo_Pub` (`idLettura_Pub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RIGERS`.`Videosorveglianza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RIGERS`.`Videosorveglianza` (
  `idVideosorveglianza` INT NOT NULL,
  `idLettura_Pub` INT NOT NULL,
  PRIMARY KEY (`idVideosorveglianza`, `idLettura_Pub`),
  INDEX `fk_Videosorveglianza_Lettura_Dispositivo_Pub1_idx` (`idLettura_Pub` ASC),
  CONSTRAINT `fk_Videosorveglianza_Lettura_Dispositivo_Pub1`
    FOREIGN KEY (`idLettura_Pub`)
    REFERENCES `RIGERS`.`Lettura_Dispositivo_Pub` (`idLettura_Pub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
