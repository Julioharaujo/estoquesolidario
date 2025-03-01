
DELIMITER $

# ajusta o estoque ao atualizar a saída de itens doados.

DROP TRIGGER IF EXISTS TRG_U_DOACAO_SAIDA_ITENS;
CREATE TRIGGER TRG_U_DOACAO_SAIDA_ITENS AFTER UPDATE
ON DOACAO_SAIDA_ITENS
FOR EACH ROW
BEGIN
    UPDATE produto_estoque 
    SET quantidade = quantidade + (OLD.quantidade + NEW.quantidade)
    WHERE produto_id = NEW.produto_id;
END$

DELIMITER ;