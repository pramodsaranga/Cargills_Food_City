
DROP DATABASE IF EXISTS Cargills;
CREATE DATABASE IF NOT EXISTS Cargills;
SHOW DATABASES ;
USE Cargills;
#-------------------
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    customerId VARCHAR(20),
    customerTitle VARCHAR (20),
    customerName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    address VARCHAR (30),
    city VARCHAR (20),
    province VARCHAR (20),
    postalCode VARCHAR (10),
    CONSTRAINT PRIMARY KEY (customerId)
    );
SHOW TABLES ;
DESCRIBE Customer;
#---------------------
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR (20),
    description VARCHAR (50),
    packSize VARCHAR (20),
    unitPrice DOUBLE ,
    qtyOnHand INT,
    CONSTRAINT PRIMARY KEY (itemCode)
);
SHOW TABLES ;
DESCRIBE item;
#---------------------
DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
    orderId VARCHAR(20),
    orderDate DATE,
    orderTime VARCHAR(15),
    customerId VARCHAR (20),
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);
#-----------------------
DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
    orderId VARCHAR(20),
    itemCode VARCHAR(20),
    orderQty INT NOT NULL DEFAULT 0,
    discount DOUBLE DEFAULT 0.00,
    orderDate DATE ,
    orderTime VARCHAR (15),
    customerId VARCHAR (20),
    total DOUBLE ,
    CONSTRAINT PRIMARY KEY (itemCode, orderId),
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(orderId) ON DELETE CASCADE ON UPDATE CASCADE
    );
