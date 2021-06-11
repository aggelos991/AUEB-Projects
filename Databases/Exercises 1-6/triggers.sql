/*---------------------------------------------------------------*/

CREATE FUNCTION insert_host_listings_count() RETURNS trigger AS
'BEGIN
UPDATE Host
SET host_listings_count = host_listings_count + 1
WHERE id = NEW.Host.id;
RETURN NEW;
END;
'
LANGUAGE plpgsql;

CREATE TRIGGER insertTrigger AFTER INSERT ON Listing
FOR EACH ROW
EXECUTE PROCEDURE insert_host_listings_count();


CREATE FUNCTION delete_host_listings_count() RETURNS trigger AS
'BEGIN
UPDATE Host
SET host_listings_count = host_listings_count -1 
WHERE id = OLD.Host.id;
RETURN OLD;
END;
'
LANGUAGE plpgsql;

CREATE TRIGGER deleteTrigger AFTER INSERT ON Listing
FOR EACH ROW
EXECUTE PROCEDURE delete_host_listings_count();

/*---------------------------------------------------------------
 Το trigger αυτό ενημερώνει το πεδίο number_of_reviews στην εγγραφή του πίνακα Listing.*/

CREATE FUNCTION insert_number_of_reviews() RETURNS trigger AS
'BEGIN
UPDATE Listing
SET number_of_reviews = number_of_reviews + 1
WHERE id = NEW.Listing.number_of_reviews;
RETURN NEW;
END;
'
LANGUAGE plpgsql;

CREATE TRIGGER insertTrigger1 AFTER INSERT ON Listing
FOR EACH ROW
EXECUTE PROCEDURE insert_number_of_reviews();


CREATE FUNCTION delete_number_of_reviews() RETURNS trigger AS
'BEGIN
UPDATE Listing
SET number_of_reviews = number_of_reviews -1 
WHERE id = OLD.Listing.id;
RETURN OLD;
END;
'
LANGUAGE plpgsql;

CREATE TRIGGER deleteTrigger1 AFTER INSERT ON Listing
FOR EACH ROW
EXECUTE PROCEDURE delete_number_of_reviews();