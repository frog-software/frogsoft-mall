/*
 Source Server         : openGauss
 Source Server Type    : PostgreSQL
 Source Server Version : 90204
 Source Catalog        : postgres
 Source Schema         : gaussdb

 Target Server Type    : PostgreSQL
 Target Server Version : 90204
 File Encoding         : 65001

 Date: 26/05/2022 12:30:50
*/

DROP SCHEMA IF EXISTS "gaussdb";
CREATE SCHEMA "gaussdb";

-- ----------------------------
-- Sequence structure for addresses_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."addresses_id_seq";
CREATE SEQUENCE "gaussdb"."addresses_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."addresses_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for bankcards_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."bankcards_id_seq";
CREATE SEQUENCE "gaussdb"."bankcards_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."bankcards_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for cart_items_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."cart_items_id_seq";
CREATE SEQUENCE "gaussdb"."cart_items_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."cart_items_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for comments_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."comments_id_seq";
CREATE SEQUENCE "gaussdb"."comments_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."comments_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for customers_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."customers_id_seq";
CREATE SEQUENCE "gaussdb"."customers_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."customers_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for order_items_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."order_items_id_seq";
CREATE SEQUENCE "gaussdb"."order_items_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."order_items_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for orders_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."orders_id_seq";
CREATE SEQUENCE "gaussdb"."orders_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."orders_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for products_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."products_id_seq";
CREATE SEQUENCE "gaussdb"."products_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."products_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for shops_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."shops_id_seq";
CREATE SEQUENCE "gaussdb"."shops_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."shops_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Sequence structure for users_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "gaussdb"."users_id_seq";
CREATE SEQUENCE "gaussdb"."users_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "gaussdb"."users_id_seq" OWNER TO "gaussdb";

-- ----------------------------
-- Table structure for addresses
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."addresses";
CREATE TABLE "gaussdb"."addresses" (
  "id" int8 NOT NULL DEFAULT nextval('addresses_id_seq'::regclass),
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "country" varchar(255) COLLATE "pg_catalog"."default",
  "detail_address" varchar(255) COLLATE "pg_catalog"."default",
  "province" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_name" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_phone" varchar(255) COLLATE "pg_catalog"."default",
  "customer_id" int8
)
;
ALTER TABLE "gaussdb"."addresses" OWNER TO "gaussdb";

-- ----------------------------
-- Records of addresses
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."addresses" ("id", "city", "country", "detail_address", "province", "receiver_name", "receiver_phone", "customer_id") VALUES (1, 'beijing', 'haidian', 'bupt', 'beijing', 'wht', '15212123234', 2);
COMMIT;

-- ----------------------------
-- Table structure for bankcards
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."bankcards";
CREATE TABLE "gaussdb"."bankcards" (
  "id" int8 NOT NULL DEFAULT nextval('bankcards_id_seq'::regclass),
  "bankcard_inc" varchar(255) COLLATE "pg_catalog"."default",
  "bankcard_num" varchar(255) COLLATE "pg_catalog"."default",
  "customer_id" int8
)
;
ALTER TABLE "gaussdb"."bankcards" OWNER TO "gaussdb";

-- ----------------------------
-- Records of bankcards
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for cart_items
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."cart_items";
CREATE TABLE "gaussdb"."cart_items" (
  "id" int8 NOT NULL DEFAULT nextval('cart_items_id_seq'::regclass),
  "add_time" timestamp(6),
  "amount" int4 NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "product_id" int8,
  "customer_id" int8,
  "username" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "gaussdb"."cart_items" OWNER TO "gaussdb";

-- ----------------------------
-- Records of cart_items
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."cart_items" ("id", "add_time", "amount", "remarks", "product_id", "customer_id", "username") VALUES (1, '2022-05-12 16:13:14.120903', 10, '我要买十个！', 2, 2, NULL);
COMMIT;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."comments";
CREATE TABLE "gaussdb"."comments" (
  "id" int8 NOT NULL DEFAULT nextval('comments_id_seq'::regclass),
  "comment_time" timestamp(6),
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "type" int4 NOT NULL,
  "customer_id" int8,
  "product_id" int8
)
;
ALTER TABLE "gaussdb"."comments" OWNER TO "gaussdb";

-- ----------------------------
-- Records of comments
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."customers";
CREATE TABLE "gaussdb"."customers" (
  "id" int8 NOT NULL DEFAULT nextval('customers_id_seq'::regclass),
  "balance" numeric(19,2),
  "gender" int4 NOT NULL,
  "user_id" int8
)
;
ALTER TABLE "gaussdb"."customers" OWNER TO "gaussdb";

-- ----------------------------
-- Records of customers
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."customers" ("id", "balance", "gender", "user_id") VALUES (1, 0.00, 1, 1);
INSERT INTO "gaussdb"."customers" ("id", "balance", "gender", "user_id") VALUES (2, 0.00, 1, 2);
INSERT INTO "gaussdb"."customers" ("id", "balance", "gender", "user_id") VALUES (5, 0.00, 0, 6);
INSERT INTO "gaussdb"."customers" ("id", "balance", "gender", "user_id") VALUES (6, 0.00, 0, 7);
COMMIT;

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."order_items";
CREATE TABLE "gaussdb"."order_items" (
  "id" int8 NOT NULL DEFAULT nextval('order_items_id_seq'::regclass),
  "amount" int4 NOT NULL,
  "order_price" numeric(19,2),
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "product_id" int8,
  "order_id" int8
)
;
ALTER TABLE "gaussdb"."order_items" OWNER TO "gaussdb";

-- ----------------------------
-- Records of order_items
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (9, 78, 1.00, 'laborum Duis amet', 1, 3);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (7, 14, 43.00, 'veniam aliquip Excepteur Ut ut', 7, 3);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (8, 31, 36.00, 'anim cupidatat sit', 2, 3);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (10, 14, 43.00, 'veniam aliquip Excepteur Ut ut', 7, 4);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (11, 31, 36.00, 'anim cupidatat sit', 2, 4);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (12, 78, 1.00, 'laborum Duis amet', 1, 4);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (18, 31, 36.00, '商品备注2', 2, 6);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (17, 14, 43.00, '商品备注7', 7, 6);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (16, 78, 1.00, '商品备注1', 1, 6);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (20, 14, 43.00, '商品备注7', 7, 7);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (21, 31, 36.00, '商品备注2', 2, 7);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (19, 78, 1.00, '商品备注1', 1, 7);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (26, 31, 36.00, '商品备注2', 2, 8);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (25, 14, 43.00, '商品备注7', 7, 8);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (24, 78, 1.00, '商品备注1', 1, 8);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (27, 78, 1.00, '商品备注1', 1, 9);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (28, 14, 43.00, '商品备注7', 7, 9);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (29, 31, 36.00, '商品备注2', 2, 9);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (30, 1, 12.00, NULL, 1, 9);
INSERT INTO "gaussdb"."order_items" ("id", "amount", "order_price", "remarks", "product_id", "order_id") VALUES (31, 1, 9.99, NULL, 2, 9);
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."orders";
CREATE TABLE "gaussdb"."orders" (
  "id" int8 NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "logistics_number" varchar(255) COLLATE "pg_catalog"."default",
  "order_time" timestamp(6),
  "payment" varchar(255) COLLATE "pg_catalog"."default",
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4 NOT NULL,
  "total_price" numeric(19,2),
  "buyer_id" int8,
  "address_id" int8,
  "seller_id" int8
)
;
ALTER TABLE "gaussdb"."orders" OWNER TO "gaussdb";

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."orders" ("id", "account", "logistics_number", "order_time", "payment", "remarks", "status", "total_price", "buyer_id", "address_id", "seller_id") VALUES (3, NULL, NULL, '2022-05-16 17:05:13.631871', NULL, 'Excepteur sint', 1, 1796.00, 2, 1, 1);
INSERT INTO "gaussdb"."orders" ("id", "account", "logistics_number", "order_time", "payment", "remarks", "status", "total_price", "buyer_id", "address_id", "seller_id") VALUES (4, NULL, NULL, '2022-05-16 17:09:07.125286', NULL, 'Excepteur sint', 1, 1796.00, 2, 1, 1);
INSERT INTO "gaussdb"."orders" ("id", "account", "logistics_number", "order_time", "payment", "remarks", "status", "total_price", "buyer_id", "address_id", "seller_id") VALUES (6, NULL, NULL, '2022-05-17 11:20:35.787444', NULL, '这是我下订单的备注', 1, 1796.00, 2, 1, 1);
INSERT INTO "gaussdb"."orders" ("id", "account", "logistics_number", "order_time", "payment", "remarks", "status", "total_price", "buyer_id", "address_id", "seller_id") VALUES (7, NULL, NULL, '2022-05-17 11:39:48.267339', NULL, '这是我下订单的备注', 1, 1796.00, 2, 1, 1);
INSERT INTO "gaussdb"."orders" ("id", "account", "logistics_number", "order_time", "payment", "remarks", "status", "total_price", "buyer_id", "address_id", "seller_id") VALUES (8, NULL, NULL, '2022-05-17 11:51:53.022675', NULL, '这是我下订单的备注', 1, 1796.00, 2, 1, 1);
INSERT INTO "gaussdb"."orders" ("id", "account", "logistics_number", "order_time", "payment", "remarks", "status", "total_price", "buyer_id", "address_id", "seller_id") VALUES (9, NULL, NULL, '2022-05-17 11:53:17.683782', NULL, '商家修改了订单的备注', 1, 1817.99, 2, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."products";
CREATE TABLE "gaussdb"."products" (
  "id" int8 NOT NULL DEFAULT nextval('products_id_seq'::regclass),
  "brand" varchar(255) COLLATE "pg_catalog"."default",
  "category" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "image_list" varchar(255) COLLATE "pg_catalog"."default",
  "price" numeric(19,2),
  "product_name" varchar(255) COLLATE "pg_catalog"."default",
  "thumb" varchar(255) COLLATE "pg_catalog"."default",
  "shop_id" int8
)
;
ALTER TABLE "gaussdb"."products" OWNER TO "gaussdb";

-- ----------------------------
-- Records of products
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (7, 'Excepteur in consequat et veniam', 'ut incididunt est', 'qui', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 43.00, '测试商品thumb', 'http://dummyimage.com/400x400', 1);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (8, 'FrogSoftware', 'Other', '这是一个测试商品', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 36.00, '测试商品001', 'http://dummyimage.com/400x400', 4);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (6, 'sint consequat', 'sed', 'dolor', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 92.00, '定学又社', 'http://dummyimage.com/400x400', 4);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (2, 'FrogSoftware', 'Other', '这是一个测试商品', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 36.00, '测试商品', 'http://dummyimage.com/400x400', 1);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (5, 'elit velit ipsum', 'in', 'Excepteur sed', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 100.00, '于管起交强', 'http://dummyimage.com/400x400', 1);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (4, 'dolor Duis enim', 'amet', 'sunt ad enim', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 90.00, '所这机争', 'http://dummyimage.com/400x400', 5);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (1, 'Frogsoft', 'Food', '你是一个一个一个商品啊', 'null', 1.00, 'test_product', 'http://dummyimage.com/400x400', 1);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (3, 'in ut', 'esse est', 'mollit id fugiat', '["http://dummyimage.com/400x400"]', 81.00, '总们装般', 'http://dummyimage.com/400x400', 4);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (9, 'FrogSoftware', 'Other', '这是一个测试商品', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 36.00, '测试商品001', 'http://dummyimage.com/400x400', 4);
INSERT INTO "gaussdb"."products" ("id", "brand", "category", "description", "image_list", "price", "product_name", "thumb", "shop_id") VALUES (10, 'FrogSoftware', 'Other', '这是一个测试商品', '["http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400","http://dummyimage.com/400x400"]', 36.00, '测试商品001', 'http://dummyimage.com/400x400', 4);
COMMIT;

-- ----------------------------
-- Table structure for shops
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."shops";
CREATE TABLE "gaussdb"."shops" (
  "id" int8 NOT NULL DEFAULT nextval('shops_id_seq'::regclass),
  "rate" float4 NOT NULL,
  "shop_image" varchar(255) COLLATE "pg_catalog"."default",
  "shop_name" varchar(255) COLLATE "pg_catalog"."default",
  "trade_quantity" int4 NOT NULL,
  "owner_id" int8
)
;
ALTER TABLE "gaussdb"."shops" OWNER TO "gaussdb";

-- ----------------------------
-- Records of shops
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (3, 0, 'http://dummyimage.com/400x400', 'WHT抢夺QY的小卖铺', 0, 2);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (5, 0, 'http://dummyimage.com/400x400', '商店3', 0, NULL);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (6, 0, 'http://dummyimage.com/400x400', '我的测试商店', 0, 2);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (7, 0, 'http://dummyimage.com/400x400', '我的测试商店', 0, 2);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (4, 0, 'http://dummyimage.com/400x400', '小卖铺Real', 0, 2);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (8, 0, 'http://dummyimage.com/400x400', '我的测试商店', 0, 2);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (1, 5, 'http://dummyimage.com/400x400', 'test_shop', 0, 1);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (9, 0, 'http://dummyimage.com/400x400', '我的测试商店', 0, 2);
INSERT INTO "gaussdb"."shops" ("id", "rate", "shop_image", "shop_name", "trade_quantity", "owner_id") VALUES (10, 0, 'http://dummyimage.com/400x400', '我的测试商店', 0, 2);
COMMIT;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."user_roles";
CREATE TABLE "gaussdb"."user_roles" (
  "user_id" int8 NOT NULL,
  "roles" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "gaussdb"."user_roles" OWNER TO "gaussdb";

-- ----------------------------
-- Records of user_roles
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."user_roles" ("user_id", "roles") VALUES (1, 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "gaussdb"."users";
CREATE TABLE "gaussdb"."users" (
  "id" int8 NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "real_name" varchar(255) COLLATE "pg_catalog"."default",
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "nickname" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "gaussdb"."users" OWNER TO "gaussdb";

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO "gaussdb"."users" ("id", "avatar", "password", "phone", "real_name", "username", "nickname") VALUES (1, '', '{bcrypt}$2a$10$X4XjVoBwfD7C1GNanzlAt.Pn2zb5nAv2HwKUeAuhJBgdgu1WBdDK6', '18888888888', '管理员', 'admin', NULL);
INSERT INTO "gaussdb"."users" ("id", "avatar", "password", "phone", "real_name", "username", "nickname") VALUES (2, 'http://dummyimage.com/100x100', '{bcrypt}$2a$10$rU5ZTyS9w2Z5RBAg/xdq1.pdCiXX6L8H3c4QqwjD/xcU4BQyk8e/.', '15212412168', NULL, 'wht', 'nickbit');
INSERT INTO "gaussdb"."users" ("id", "avatar", "password", "phone", "real_name", "username", "nickname") VALUES (3, 'http://dummyimage.com/100x100', '{bcrypt}$2a$10$Ylegzo8fvHJE0kioktd8N.zJSxkCw1e2GuB6TsGbI1S8KXNQd.Kd6', '18632644225', NULL, '李超', '毛秀兰');
INSERT INTO "gaussdb"."users" ("id", "avatar", "password", "phone", "real_name", "username", "nickname") VALUES (6, 'http://dummyimage.com/100x100', '{bcrypt}$2a$10$hADMpos8G0.ElXxO4RPmG.OP1BCZXy6hpfLV/8ZdEXSyvQLtUYpQ6', '18679017740', NULL, 'test_user1', 'TestName');
INSERT INTO "gaussdb"."users" ("id", "avatar", "password", "phone", "real_name", "username", "nickname") VALUES (7, 'http://dummyimage.com/100x100', '{bcrypt}$2a$10$1Alhn8liiO1orgVnl/cBtu./wFWY.qkd7S9eTK.1E2PmCbYwEjj1O', '18679017740', NULL, 'test_user1', 'TestName');
COMMIT;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."addresses_id_seq"
OWNED BY "gaussdb"."addresses"."id";
SELECT setval('"gaussdb"."addresses_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."bankcards_id_seq"
OWNED BY "gaussdb"."bankcards"."id";
SELECT setval('"gaussdb"."bankcards_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."cart_items_id_seq"
OWNED BY "gaussdb"."cart_items"."id";
SELECT setval('"gaussdb"."cart_items_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."comments_id_seq"
OWNED BY "gaussdb"."comments"."id";
SELECT setval('"gaussdb"."comments_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."customers_id_seq"
OWNED BY "gaussdb"."customers"."id";
SELECT setval('"gaussdb"."customers_id_seq"', 6, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."order_items_id_seq"
OWNED BY "gaussdb"."order_items"."id";
SELECT setval('"gaussdb"."order_items_id_seq"', 31, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."orders_id_seq"
OWNED BY "gaussdb"."orders"."id";
SELECT setval('"gaussdb"."orders_id_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."products_id_seq"
OWNED BY "gaussdb"."products"."id";
SELECT setval('"gaussdb"."products_id_seq"', 10, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."shops_id_seq"
OWNED BY "gaussdb"."shops"."id";
SELECT setval('"gaussdb"."shops_id_seq"', 10, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "gaussdb"."users_id_seq"
OWNED BY "gaussdb"."users"."id";
SELECT setval('"gaussdb"."users_id_seq"', 7, true);

-- ----------------------------
-- Primary Key structure for table addresses
-- ----------------------------
ALTER TABLE "gaussdb"."addresses" ADD CONSTRAINT "addresses_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bankcards
-- ----------------------------
ALTER TABLE "gaussdb"."bankcards" ADD CONSTRAINT "bankcards_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cart_items
-- ----------------------------
ALTER TABLE "gaussdb"."cart_items" ADD CONSTRAINT "cart_items_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table comments
-- ----------------------------
ALTER TABLE "gaussdb"."comments" ADD CONSTRAINT "comments_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table customers
-- ----------------------------
ALTER TABLE "gaussdb"."customers" ADD CONSTRAINT "customers_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table order_items
-- ----------------------------
ALTER TABLE "gaussdb"."order_items" ADD CONSTRAINT "order_items_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table orders
-- ----------------------------
ALTER TABLE "gaussdb"."orders" ADD CONSTRAINT "orders_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table products
-- ----------------------------
ALTER TABLE "gaussdb"."products" ADD CONSTRAINT "products_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table shops
-- ----------------------------
ALTER TABLE "gaussdb"."shops" ADD CONSTRAINT "shops_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "gaussdb"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table addresses
-- ----------------------------
ALTER TABLE "gaussdb"."addresses" ADD CONSTRAINT "fkhrpf5e8dwasvdc5cticysrt2k" FOREIGN KEY ("customer_id") REFERENCES "gaussdb"."customers" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table bankcards
-- ----------------------------
ALTER TABLE "gaussdb"."bankcards" ADD CONSTRAINT "fk30gw4i2lowm4jg770evw4d026" FOREIGN KEY ("customer_id") REFERENCES "gaussdb"."customers" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table cart_items
-- ----------------------------
ALTER TABLE "gaussdb"."cart_items" ADD CONSTRAINT "fk1re40cjegsfvw58xrkdp6bac6" FOREIGN KEY ("product_id") REFERENCES "gaussdb"."products" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "gaussdb"."cart_items" ADD CONSTRAINT "fkdagcsk6v6x4n1kxw3rkp57921" FOREIGN KEY ("customer_id") REFERENCES "gaussdb"."customers" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table comments
-- ----------------------------
ALTER TABLE "gaussdb"."comments" ADD CONSTRAINT "fk2b031jfm517sto7j4a58tkhy" FOREIGN KEY ("customer_id") REFERENCES "gaussdb"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "gaussdb"."comments" ADD CONSTRAINT "fk6uv0qku8gsu6x1r2jkrtqwjtn" FOREIGN KEY ("product_id") REFERENCES "gaussdb"."products" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table customers
-- ----------------------------
ALTER TABLE "gaussdb"."customers" ADD CONSTRAINT "fkrh1g1a20omjmn6kurd35o3eit" FOREIGN KEY ("user_id") REFERENCES "gaussdb"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table order_items
-- ----------------------------
ALTER TABLE "gaussdb"."order_items" ADD CONSTRAINT "fkbioxgbv59vetrxe0ejfubep1w" FOREIGN KEY ("order_id") REFERENCES "gaussdb"."orders" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "gaussdb"."order_items" ADD CONSTRAINT "fkocimc7dtr037rh4ls4l95nlfi" FOREIGN KEY ("product_id") REFERENCES "gaussdb"."products" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table orders
-- ----------------------------
ALTER TABLE "gaussdb"."orders" ADD CONSTRAINT "fkgirny93ovukuqskmghj2jicx7" FOREIGN KEY ("seller_id") REFERENCES "gaussdb"."shops" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "gaussdb"."orders" ADD CONSTRAINT "fkhlglkvf5i60dv6dn397ethgpt" FOREIGN KEY ("address_id") REFERENCES "gaussdb"."addresses" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "gaussdb"."orders" ADD CONSTRAINT "fknvxxua7miwcasisaltk0abpem" FOREIGN KEY ("buyer_id") REFERENCES "gaussdb"."customers" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table products
-- ----------------------------
ALTER TABLE "gaussdb"."products" ADD CONSTRAINT "fk7kp8sbhxboponhx3lxqtmkcoj" FOREIGN KEY ("shop_id") REFERENCES "gaussdb"."shops" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table shops
-- ----------------------------
ALTER TABLE "gaussdb"."shops" ADD CONSTRAINT "fkrduswa89ayj0poad3l70nag19" FOREIGN KEY ("owner_id") REFERENCES "gaussdb"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table user_roles
-- ----------------------------
ALTER TABLE "gaussdb"."user_roles" ADD CONSTRAINT "fkhfh9dx7w3ubf1co1vdev94g3f" FOREIGN KEY ("user_id") REFERENCES "gaussdb"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
