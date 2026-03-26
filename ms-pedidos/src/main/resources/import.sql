-- Inserindo os Pedidos
INSERT INTO tb_pedido (nome, cpf, data, status, valor_total) 
VALUES ('Jon Snow', '12345678902', '2025-11-25', 'CRIADO', 790.00);

INSERT INTO tb_pedido (nome, cpf, data, status, valor_total) 
VALUES ('Ayra Stark', '12345678962', '2026-01-25', 'CRIADO', 3599.00);

-- Inserindo os Itens dos Pedidos
INSERT INTO tb_item_do_pedido (quantidade, descricao, preco_unitario, pedido_id) 
VALUES (2, 'Mouse sem fio Microsoft', 250.00, 1);

INSERT INTO tb_item_do_pedido (quantidade, descricao, preco_unitario, pedido_id) 
VALUES (1, 'Teclado sem fio Microsoft', 290.00, 1);

INSERT INTO tb_item_do_pedido (quantidade, descricao, preco_unitario, pedido_id) 
VALUES (1, 'SMART TV LG OLED', 3599.00, 2);