# ajusta o estoque ao atualizar a quantidade de itens doados.

DELIMITER $
DROP trigger IF exists TRG_U_DOACAO_ENTRADA_ITENS;
CREATE trigger TRG_U_DOACAO_ENTRADA_ITENS AFTER UPDATE
ON DOACAO_ENTRADA_ITENS
FOR each row
BEGIN
	UPDATE produto_estoque 
    SET quantidade = quantidade - (OLD.quantidade  - NEW.quantidade)
	WHERE produto_id = NEW.produto_id;
end$