use estoque_solidario;

# insere um registro em produto_estoque com quantidade 0 ao adicionar um produto.
DELIMITER $

CREATE trigger TGR_I_PRODUTOS AFTER INSERT
ON produtos
FOR each row
BEGIN
	insert into produto_estoque (quantidade, produto_id) values (0, new.id);
end$