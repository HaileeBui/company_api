CREATE TABLE IF NOT EXISTS company(
    id NUMBER(3) PRIMARY KEY AUTO_INCREMENT
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    url VARCHAR(1000),
    businessId VARCHAR(20)
    address VARCHAR(1000),
    phoneNumber VARCHAR(40)
)