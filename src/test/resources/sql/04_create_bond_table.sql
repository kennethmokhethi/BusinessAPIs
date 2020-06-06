create table bond(
id int primary key,
noRooms varchar(255),
address varchar(255),
u_Id int ,
constraint fk_id foreign key (u_Id) references Users(u_Id));
commit;