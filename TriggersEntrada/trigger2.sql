DELIMITER $

CREATE trigger TRG_I_NOTA_ENTRADA_ITENS AFTER INSERT
ON NOTA_ENTRADA_ITENS
FOR each row
BEGIN
	UPDATE produto_estoque 
    SET quantidade = quantidade + NEW.quantidade 
	WHERE produto_id = new.produto_id;
end$

select * from NOTA_ENTRADA_ITENS;