CREATE CACHED TABLE "category" (
  "id"   BIGINT AUTO_INCREMENT PRIMARY KEY,
  "name" VARCHAR(255)
);

CREATE CACHED TABLE "supplier" (
  "id"   BIGINT AUTO_INCREMENT PRIMARY KEY,
  "name" VARCHAR(255)
);

CREATE CACHED TABLE "unit" (
  "id"   BIGINT AUTO_INCREMENT PRIMARY KEY,
  "name" VARCHAR(255)
);

CREATE CACHED TABLE "item" (
  "id"          BIGINT AUTO_INCREMENT PRIMARY KEY,
  "group"       VARCHAR(255),
  "name"        VARCHAR(255),
  "pack"        DECIMAL(19, 2),
  "price"       DECIMAL(19, 2),
  "category_id" BIGINT,
  "supplier_id" BIGINT,
  "unit_id"     BIGINT,
  FOREIGN KEY ("supplier_id") REFERENCES "supplier" ("id"),
  FOREIGN KEY ("unit_id") REFERENCES "unit" ("id"),
  FOREIGN KEY ("category_id") REFERENCES "category" ("id")
);
