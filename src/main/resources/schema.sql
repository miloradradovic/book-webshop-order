drop table if exists ordered_items;
drop table if exists orders;

create table ordered_items (
    id integer not null auto_increment,
    amount integer not null,
    name varchar(255) not null,
    price double precision not null,
    order_id integer,
    primary key (id)
);

create table orders (
    id integer not null auto_increment,
    address varchar(255) not null,
    final_price double precision not null,
    order_status varchar(255),
    phone_number varchar(255) not null,
    primary key (id)
);

alter table ordered_items
    add constraint FKha3q3tsqr7gri0mbp14hc5mkq
    foreign key (order_id)
    references orders (id);