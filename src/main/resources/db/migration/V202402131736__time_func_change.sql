ALTER TABLE users ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE items ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE shops ALTER COLUMN created_at SET DEFAULT now();