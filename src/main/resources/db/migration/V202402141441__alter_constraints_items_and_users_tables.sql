ALTER TABLE items ALTER COLUMN name DROP DEFAULT,
                  ALTER COLUMN price SET DEFAULT 0,
                  ALTER COLUMN count SET DEFAULT 0,
                  ALTER COLUMN count SET NOT NULL;
ALTER TABLE users ADD role VARCHAR(20) NOT NULL,
                  ALTER COLUMN role SET DEFAULT 'ROLE_USER';
