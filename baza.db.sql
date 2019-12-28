BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "drzava" (
	"id"	integer,
	"naziv"	text,
	"glavni_grad"	integer,
	CONSTRAINT "drzava_pk" PRIMARY KEY("id"),
	CONSTRAINT "drzava_grad_id_fk" FOREIGN KEY("glavni_grad") REFERENCES "grad"
);
CREATE TABLE IF NOT EXISTS "grad" (
	"id"	integer,
	"naziv"	text,
	"broj_stanovnika"	integer,
	"drzava"	integer,
	CONSTRAINT "grad_pk" PRIMARY KEY("id"),
	CONSTRAINT "grad_drzava_id_fk" FOREIGN KEY("drzava") REFERENCES "drzava"
);
INSERT INTO "drzava" VALUES (1,'Francuska',1);
INSERT INTO "drzava" VALUES (2,'Velika Britanija',2);
INSERT INTO "drzava" VALUES (3,'Austrija',3);
INSERT INTO "grad" VALUES (1,'Pariz',2206488,1);
INSERT INTO "grad" VALUES (2,'London',8825000,2);
INSERT INTO "grad" VALUES (3,'Beč',1899055,3);
INSERT INTO "grad" VALUES (4,'Manchester',545500,2);
INSERT INTO "grad" VALUES (5,'Graz',280200,3);
COMMIT;
