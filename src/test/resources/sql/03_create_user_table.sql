create table Users
(
    u_Id int primary key,
    email varchar(255),
    encryptedpassword varchar(255),
    firstname varchar(255),
    lastname varchar(255),
    salt varchar(255),
    token varchar(255),
    userid varchar(255)
);
commit;