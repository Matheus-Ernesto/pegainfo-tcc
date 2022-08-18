-- ESTE SCRIPT CRIA USUARIOS E DA PRIVILEGIOS

-- EXECUTE SOMENTE DE UMA VEZ, NUNCA SEPARADAMENTE

-- CRIA E GRANT O PERFIL DE CLIENTE
CREATE USER 'cliente'@'localhost' IDENTIFIED BY 'cliente';
GRANT INSERT, UPDATE, SELECT, DELETE, ALTER ON TABLE `mydb`.`u_setups` TO 'cliente';
GRANT INSERT, UPDATE, SELECT, DELETE, ALTER ON TABLE `mydb`.`u_usuarios` TO 'cliente';
GRANT INSERT, UPDATE, SELECT, DELETE, ALTER ON TABLE `mydb`.`u_favoritos` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`p_memoria_armaz` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`p_memoria_ram` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`p_placa_de_video` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`p_placa_mae` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`p_processador` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`p_gabinete` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`d_celulares` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`d_desktops` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`d_tablets` TO 'cliente';
GRANT SELECT, UPDATE ON TABLE `mydb`.`d_notebooks` TO 'cliente';
GRANT SELECT ON TABLE `mydb`.`l_lojas` TO 'cliente';
GRANT SELECT ON TABLE `mydb`.`l_descricao` TO 'cliente';

-- CRIA E GRANT O PERFIL DE DESENVOLVEDOR
CREATE USER 'desenvolvedor'@'localhost' IDENTIFIED BY 'desenvolvedor';
GRANT ALTER, DELETE, INSERT, SELECT, UPDATE ON TABLE `mydb`.`p_memoria_armaz` TO 'desenvolvedor';
GRANT ALTER, DELETE, INSERT, SELECT, UPDATE ON TABLE `mydb`.`p_memoria_ram` TO 'desenvolvedor';
GRANT DELETE, INSERT, SELECT, UPDATE, ALTER ON TABLE `mydb`.`p_placa_de_video` TO 'desenvolvedor';
GRANT INSERT, UPDATE, SELECT, DELETE, ALTER ON TABLE `mydb`.`p_placa_mae` TO 'desenvolvedor';
GRANT ALTER, DELETE, INSERT, SELECT, UPDATE ON TABLE `mydb`.`p_processador` TO 'desenvolvedor';
GRANT ALTER, DELETE, INSERT, SELECT, UPDATE ON TABLE `mydb`.`p_gabinete` TO 'desenvolvedor';
GRANT UPDATE, SELECT, INSERT, DELETE, ALTER ON TABLE `mydb`.`d_celulares` TO 'desenvolvedor';
GRANT UPDATE, SELECT, INSERT, DELETE, ALTER ON TABLE `mydb`.`d_desktops` TO 'desenvolvedor';
GRANT UPDATE, SELECT, INSERT, DELETE, ALTER ON TABLE `mydb`.`d_tablets` TO 'desenvolvedor';
GRANT UPDATE, SELECT, INSERT, DELETE, ALTER ON TABLE `mydb`.`d_notebooks` TO 'desenvolvedor';
GRANT UPDATE, SELECT, INSERT, DELETE, ALTER ON TABLE `mydb`.`l_lojas` TO 'cliente';