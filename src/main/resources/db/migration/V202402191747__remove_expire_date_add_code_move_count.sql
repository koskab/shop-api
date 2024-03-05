ALTER TABLE items ADD code VARCHAR(255) NOT NULL UNIQUE,
          DROP COLUMN deadline_date,
          DROP COLUMN count;
ALTER TABLE shop_items ADD count INT DEFAULT 1;