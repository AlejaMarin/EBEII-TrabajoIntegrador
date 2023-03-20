create table bill
(
    id_bill       varchar(255) not null,
    customer_bill varchar(255),
    product_bill  varchar(255),
    total_price   double,
    billing_date   date,
    primary key (id_bill)
);

INSERT INTO bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE,BILLING_DATE) VALUES (MD5(UUID()), 'Jhon', 'courses/Java', 500,'2022-09-25');
INSERT INTO bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE,BILLING_DATE) VALUES (MD5(UUID()), 'Michael', 'courses/Java', 500,'2022-09-28');
INSERT INTO bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE,BILLING_DATE) VALUES (MD5(UUID()), 'Peter', 'courses/Java', 500,'2022-10-05');
INSERT INTO bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE,BILLING_DATE) VALUES (MD5(UUID()), 'Sebastian', 'courses/Java', 500,'2022-10-15');
INSERT INTO bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE,BILLING_DATE) VALUES (MD5(UUID()), 'Aaron', 'courses/Java', 500,'2022-10-25');
INSERT INTO bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE,BILLING_DATE) VALUES (MD5(UUID()), 'William', 'courses/Java', 500,'2022-10-26');