create table Usr(Id integer not null auto_increment,
				 email varchar(255),
                 name varchar(255),
                 password varchar(255),
                 primary key (Id)
) engine=InnoDB;

create table user_role(user_id  integer not null,
                 role varchar(255),
                 primary key (user_id,role)
) engine=InnoDB;

alter table user_role
            add constraint User_Role_FK  foreign key (user_id) references Usr (Id);