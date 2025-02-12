DELIMITER $
DROP trigger IF exists TRG_D_NOTA_ENTRADA_ITENS;
CREATE trigger TRG_D_NOTA_ENTRADA_ITENS AFTER DELETE
ON NOTA_ENTRADA_ITENS
FOR each row
BEGIN
	UPDATE produto_estoque 
    SET quantidade = quantidade - OLD.quantidade 
	WHERE produto_id = OLD.produto_id;
end$