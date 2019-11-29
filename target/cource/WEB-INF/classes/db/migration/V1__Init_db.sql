create table Author(Id integer not null auto_increment,
                    place varchar(255),
                    name varchar(255), primary key (Id)) engine=InnoDB;
create table Book (Id integer not null auto_increment,
                   name varchar(255),
                   primary key (Id)) engine=InnoDB;

create table Book_Author (book integer not null,
                          author integer not null,
                          primary key (book, author)) engine=InnoDB;
create table Book_genre (Book_Id integer not null,
                         genre varchar(255)) engine=InnoDB;
create table book_NameOfCustommers (Book_Id  integer not null,
                                    name varchar(255)) engine=InnoDB;


alter table Book_Author
            add constraint Book_Author_Author_FK  foreign key (author) references Author (Id);
alter table Book_Author
            add constraint Book_Author_Book_FK foreign key (book) references Book (Id);
alter table Book_genre
            add constraint Book_genre_FK foreign key (Book_Id) references Book (Id);
alter table Book_NameOfCustommers
            add constraint NameOfCustommers_FK foreign key (Book_Id) references Book (Id);