# reduz o estoque ao excluir itens de doação.

DELIMITER $
DROP trigger IF exists TRG_D_DOACAO_ENTRADA_ITENS;
CREATE trigger TRG_D_DOACAO_ENTRADA_ITENS AFTER DELETE
ON DOACAO_ENTRADA_ITENS
FOR each row
BEGIN
	UPDATE produto_estoque 
    SET quantidade = quantidade - OLD.quantidade 
	WHERE produto_id = OLD.produto_id;
end$