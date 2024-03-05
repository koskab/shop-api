ALTER TABLE shop_items RENAME TO shops_storage;
ALTER TABLE shops_storage ADD CONSTRAINT shop_item_unique UNIQUE (shop_id, item_id),
                          ADD id BIGSERIAL PRIMARY KEY,
                          ADD created_at TIMESTAMP DEFAULT now() NOT NULL,
                          ADD updated_at TIMESTAMP,
                          ADD is_deleted SMALLINT DEFAULT 0,
                          ADD deleted_at TIMESTAMP;
ALTER TABLE carts ADD created_at TIMESTAMP DEFAULT now() NOT NULL,
                  ADD updated_at TIMESTAMP,
                  ADD is_deleted SMALLINT DEFAULT 0,
                  ADD deleted_at TIMESTAMP;
ALTER TABLE shops ADD CONSTRAINT name_address_unique UNIQUE (name, address);