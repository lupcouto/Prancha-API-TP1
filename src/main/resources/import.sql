INSERT INTO tipoquilha (nome) VALUES
('Tri-Fin'),
('Quad'),
('Single');

INSERT INTO marca (nome, paisOrigem) VALUES
('Rusty', 'EUA'),
('Mormaii', 'Brasil');

INSERT INTO modelo (nome, id_marca) VALUES
('Fish Classic', (SELECT id FROM marca WHERE nome = 'Rusty')),
('Short Pro',  (SELECT id FROM marca WHERE nome = 'Mormaii'));

INSERT INTO telefone (ddd, numero) VALUES
('63', '999999999'),
('63', '988888888'),
('63', '977777777');

INSERT INTO pessoa (nome, id_telefone)
VALUES ('João Silva', (SELECT id FROM telefone WHERE numero = '999999999'));

INSERT INTO cliente (id, cpf)
VALUES (
    (SELECT id FROM pessoa WHERE nome = 'João Silva'),
    '11111111111'
);

INSERT INTO pessoa (nome, id_telefone)
VALUES ('Maria Admin', (SELECT id FROM telefone WHERE numero = '988888888'));

INSERT INTO administrador (id, cargo, statusAdm)
VALUES (
    (SELECT id FROM pessoa WHERE nome = 'Maria Admin'),
    'Gerente',
    'ATIVO'
);

INSERT INTO pessoa (nome, id_telefone)
VALUES ('Surf Supply Ltda', (SELECT id FROM telefone WHERE numero = '977777777'));

INSERT INTO fornecedor (id, cnpj)
VALUES (
    (SELECT id FROM pessoa WHERE nome = 'Surf Supply Ltda'),
    '22222222222222'
);

INSERT INTO prancha (tamanho, valor, estoque, id_modelo, tipoPrancha, habilidade)
VALUES
(
    5.8, 1200.00, 10,
    (SELECT id FROM modelo WHERE nome = 'Fish Classic'),
    'FISH', 'INICIANTE'
),
(
    6.2, 1800.00, 5,
    (SELECT id FROM modelo WHERE nome = 'Short Pro'),
    'SHORTBOARD', 'INTERMEDIARIO'
);

INSERT INTO prancha_fornecedor (id_prancha, id_fornecedor)
SELECT p.id, f.id
FROM prancha p, fornecedor f
WHERE f.cnpj = '22222222222222';

INSERT INTO quilha (descricaoQuilha, id_tipo_quilha)
VALUES
(
    'Quilha Tri-Fin padrão',
    (SELECT id FROM tipoquilha WHERE nome = 'Tri-Fin')
),
(
    'Quilha Quad performance',
    (SELECT id FROM tipoquilha WHERE nome = 'Quad')
);

INSERT INTO prancha_quilha (id_prancha, id_quilha)
VALUES 
(1, 1),
(2, 2);

INSERT INTO endereco (cidade, estado, cep)
VALUES ('Palmas', 'TO', '77000000');

INSERT INTO pagamento (dataPagamento, statusPagamento)
VALUES
(CURRENT_TIMESTAMP, 'PAGO'),
(CURRENT_TIMESTAMP, 'PENDENTE'),
(CURRENT_TIMESTAMP, 'PENDENTE');

INSERT INTO pix (id, chave)
VALUES (
    (SELECT id FROM pagamento WHERE statusPagamento = 'PAGO'),
    'joao@pix.com'
);

INSERT INTO boleto (id, dataVencimento, codigoBarras)
VALUES (
    (SELECT id FROM pagamento WHERE statusPagamento = 'PENDENTE' LIMIT 1),
    CURRENT_DATE + 5,
    '123456789000111'
);

INSERT INTO cartao (id, dataVencimento, nomeTitular, numeroCartao)
VALUES (
    (SELECT id FROM pagamento WHERE statusPagamento = 'PENDENTE' ORDER BY id DESC LIMIT 1),
    CURRENT_DATE + 365,
    'João Silva',
    '5555444433332222'
);

INSERT INTO pedido (dataPedido, valorTotal, id_endereco, id_cliente, id_pagamento)
VALUES (
    CURRENT_TIMESTAMP,
    2400.00,
    (SELECT id FROM endereco WHERE cep = '77000000'),
    (SELECT id FROM cliente LIMIT 1),
    (SELECT id FROM pix LIMIT 1)
);

INSERT INTO itempedido (id_pedido, id_prancha, quantidade, precoUnit, subtotal)
VALUES 
(1, 1, 2, 150.00, 300.00),
(1, 2, 1, 120.00, 120.00);

INSERT INTO usuario (login, senha, perfil)
VALUES ('admin', '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==', 1);

INSERT INTO usuario (login, senha, perfil)
VALUES ('user', '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==', 2);