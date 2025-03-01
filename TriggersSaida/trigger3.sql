DELIMITER $

# repõe o estoque ao excluir a saída de itens doados.
DROP TRIGGER IF EXISTS TRG_D_DOACAO_SAIDA_ITENS;
CREATE TRIGGER TRG_D_DOACAO_SAIDA_ITENS AFTER DELETE
ON DOACAO_SAIDA_ITENS
FOR EACH ROW
BEGIN
    UPDATE produto_estoque 
    SET quantidade = quantidade + OLD.quantidade 
    WHERE produto_id = OLD.produto_id;
END$

DELIMITER ;