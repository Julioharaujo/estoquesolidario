USE controle_estoque;

DELIMITER $

DROP TRIGGER IF EXISTS TRG_U_NOTA_SAIDA_ITENS;
CREATE TRIGGER TRG_U_NOTA_SAIDA_ITENS AFTER UPDATE
ON NOTA_SAIDA_ITENS
FOR EACH ROW
BEGIN
    UPDATE produto_estoque 
    SET quantidade = quantidade + (OLD.quantidade + NEW.quantidade)
    WHERE produto_id = NEW.produto_id;
END$

DELIMITER ;