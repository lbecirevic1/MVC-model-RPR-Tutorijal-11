
CREATE table grad (
id integer ,
naziv TEXT,
broj_stanovnika integer,
primary key (id),
foreign key (drzava) references drzava (id));

create table drzava (
id integer,
naziv TEXT,
foreign key (glavni_grad) references grad(id),
primary key (id)
);
