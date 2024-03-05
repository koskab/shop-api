ALTER TABLE carts ADD is_paid BOOLEAN NOT NULL DEFAULT false,
                  ADD paid_at TIMESTAMP;
DROP INDEX carts_user_id_shop_id_item_id_idx;
CREATE UNIQUE INDEX UX_carts_user_id_shop_id_item_id_is_paid_paid_at ON carts(user_id, shop_id, item_id, is_paid, paid_at)