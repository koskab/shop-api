CREATE TABLE shops(id BIGSERIAL PRIMARY KEY,
                   created_at TIMESTAMP,
                   updated_at TIMESTAMP,
                   is_deleted SMALLINT,
                   name VARCHAR(30),
                   address VARCHAR(50),
                   phone_number VARCHAR(15));
CREATE TABLE items(id BIGSERIAL PRIMARY KEY,
                   created_at TIMESTAMP,
                   updated_at TIMESTAMP,
                   is_deleted SMALLINT,
                   name VARCHAR(30),
                   deadline_date DATE,
                   count INT);
CREATE TABLE users(id BIGSERIAL PRIMARY KEY,
                   created_at TIMESTAMP,
                   updated_at TIMESTAMP,
                   is_deleted SMALLINT,
                   fullname VARCHAR(50),
                   email VARCHAR(30) UNIQUE,
                   password VARCHAR(50),
                   birthdate DATE);
CREATE TABLE shop_items(shop_id BIGSERIAL references shops(id),
                        item_id BIGSERIAL references items(id));
CREATE TABLE items_users(item_id BIGSERIAL references items(id),
                         user_id BIGSERIAL references users(id));
