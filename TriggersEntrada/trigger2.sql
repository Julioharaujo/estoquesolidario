DELIMITER $

CREATE trigger TRG_I_DOACAO_ENTRADA_ITENS AFTER INSERT
ON DOACAO_ENTRADA_ITENS
FOR each row
BEGIN
	UPDATE produto_estoque 
    SET quantidade = quantidade + NEW.quantidade 
	WHERE produto_id = new.produto_id;
end$

select * from DOACAO_ENTRADA_ITENS;