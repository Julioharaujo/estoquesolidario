use estoque_solidario;

DELIMITER $

CREATE TRIGGER TRG_I_DOACAO_SAIDA_ITENS AFTER INSERT
ON DOACAO_SAIDA_ITENS
FOR EACH ROW
BEGIN
    UPDATE produto_estoque
    SET quantidade = quantidade - NEW.quantidade
    WHERE produto_id = NEW.produto_id;
END$

DELIMITER ;