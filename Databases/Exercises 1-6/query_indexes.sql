 /* 
 QUERY 1*
 
 SELECT Host.id, COUNT(*) FROM Listing, Host WHERE
Host.id=Listing.host_id GROUP BY Host.id;

 */
 
 create index index1 on Listing(host_id);

---------------------------------------------------------------------------
 
 /* QUERY 2
 
SELECT id, price FROM Listing, Price WHERE guests_included > 5
AND price > 40;
 
 */
 create index index2 on Price(price,guests_included)

 
 ---------------------------------------------------------------------------
/* QUERY 3

SELECT COUNT(Location.listing_id) AS total_amount_of_listings FROM Location
JOIN Geolocation ON properties_neighbourhood=Location.neighbourhood_cleansed
JOIN Listing ON Listing.id=Location.listing_id
GROUP BY(properties_neighbourhood,property_type)
HAVING properties_neighbourhood= 'ΑΜΠΕΛΟΚΗΠΟΙ' AND property_type='Apartment'

*/

create index index3 on Listing(property_type)
create index index4 on Location(listing_id)



---------------------------------------------------------------------------
/* QUERY 4

SELECT Listing.id,Listing.name,Price.Price,Location.neighbourhood,beds FROM Room
JOIN Price ON Price.listing_id=Room.listing_id
JOIN Location ON Location.listing_id=Room.listing_id
JOIN Listing ON Listing.id=Room.listing_id
WHERE Price.price<20 AND beds>5

*/

create index index5 on Room(beds)
create index index6 on Price(price)


---------------------------------------------------------------------------

/* QUERY 5

SELECT listing.id AS listing_id, host.id AS host_id, Listing.name, Price.weekly_price,Room.accommodates from Listing
JOIN host ON Host.id = Listing.host_id
JOIN price ON Price.listing_id=Listing.id
JOIN room ON Room.listing_id=Listing.id
GROUP BY Listing.id, Host.id ,Listing.name,Price.weekly_price,Room.accommodates
HAVING Price.weekly_price >=500 AND Price.weekly_price <=1000 AND Room.accommodates >=4
ORDER BY Price.weekly_price

*/

create index index7 on price(weekly_price)
create index index8 on room(listing_id,accommodates)

---------------------------------------------------------------------------
/* QUERY 6

SELECT COUNT(*) AS hood_no_included_in_geolocation
FROM ( SELECT Neighbourhood.neighbourhood,Geolocation.properties_neighbourhood
	  FROM  Neighbourhood  RIGHT OUTER JOIN Geolocation ON Neighbourhood.neighbourhood = Geolocation.properties_neighbourhood) AS new_table
WHERE new_table.neighbourhood = NULL;

*/

create index index9 on Neighbourhood(neighbourhood)
create index index10 on Geolocation(properties_neighbourhood)

---------------------------------------------------------------------------

/* QUERY 7

SELECT count(listing.id) as Number_of_Listings, Host.name,Host.id as host_id FROM Host
FULL OUTER JOIN Listing ON Listing.host_id=Host.id
GROUP BY(Host.name,Host.id)
HAVING Host.location LIKE '%Athens%'

*/

create index index11 on Host(location,id,name)

