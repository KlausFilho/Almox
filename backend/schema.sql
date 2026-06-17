-- Script de criação do banco de dados ToolTrack (PostgreSQL)
-- Execute este script uma vez no seu banco online (Neon, Render, Supabase, etc.)

CREATE TABLE IF NOT EXISTS pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS funcionario (
    id INTEGER PRIMARY KEY REFERENCES pessoa(id) ON DELETE CASCADE,
    funcao VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS ferramenta (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(255),
    quantidade INTEGER NOT NULL DEFAULT 0,
    disponivel BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS registro (
    id SERIAL PRIMARY KEY,
    ferramenta_id INTEGER NOT NULL REFERENCES ferramenta(id) ON DELETE CASCADE,
    funcionario_id INTEGER NOT NULL REFERENCES pessoa(id) ON DELETE CASCADE,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('RETIRADA', 'DEVOLUCAO')),
    data_hora TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Dados de exemplo (opcional — pode remover se preferir começar vazio)
INSERT INTO pessoa (nome, matricula) VALUES ('João Silva', 'M001') ON CONFLICT DO NOTHING;
INSERT INTO funcionario (id, funcao) VALUES (1, 'Almoxarife') ON CONFLICT DO NOTHING;

INSERT INTO ferramenta (nome, descricao, quantidade, disponivel) VALUES
  ('Furadeira', 'Furadeira elétrica 500W', 3, TRUE),
  ('Chave de fenda', 'Conjunto com 5 pontas', 10, TRUE)
ON CONFLICT DO NOTHING;
