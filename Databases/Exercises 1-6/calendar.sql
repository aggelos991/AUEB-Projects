UPDATE Calendar
SET 
price= REPLACE(price,'$',''),
adjusted_price= REPLACE(price,'$','');

UPDATE Calendar
SET 
price= REPLACE(price,',',''),
adjusted_price= REPLACE(price,',','');

ALTER TABLE Calendar 
alter column price TYPE numeric(10,0) using price::numeric,
alter column adjusted_price TYPE numeric(10,0) using price::numeric;

ALTER TABLE Calendar
ALTER COLUMN available TYPE boolean;