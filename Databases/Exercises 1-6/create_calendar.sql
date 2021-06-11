create table "Calendar"(
   listing_id int,
   date date,
   available boolean,
   price varchar(20),
   adjusted_price varchar(20),
   minimum_nights int,
   maximum_nights int,
	PRIMARY KEY(listing_id,date)
);