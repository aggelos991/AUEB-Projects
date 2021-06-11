------------------------------------------------------------------------------------------------
/* QUERY 1
Finds the best 10 apartments located in Plaka with review scores rating > 9 and review scores accuracy >8 in descending order
Output : 10 rows
*/
SELECT id,name,review_scores_rating,review_scores_accuracy FROM Listings
WHERE neighbourhood='Plaka' AND review_scores_rating>'9' AND review_scores_accuracy > '8'
ORDER BY review_scores_rating DESC
LIMIT 10;
------------------------------------------------------------------------------------------------
/* QUERY 2
Finds all reviews of Syntagma Heart of Athens free wifi apartment that have been submitted between 2015 and 2017
Output : 53 rows
*/
SELECT Listings.id,Listings.name,Reviews.date,Reviews.reviewer_name,Reviews.comments
FROM Listings
INNER JOIN Reviews
ON Reviews.listing_id=Listings.id
WHERE Listings.name='Syntagma Heart of Athens free wifi' AND Reviews.date BETWEEN '2015-01-01' AND '2017-12-31';

------------------------------------------------------------------------------------------------

/* QUERY 3
Finds all apartments that are located in Neos Kosmos and have a TV
Output: 776 rows
*/
SELECT Listings.id,Listings.name,Neighbourhoods.neighbourhood 
FROM Listings
INNER JOIN Neighbourhoods
ON Listings.neighbourhood_cleansed=Neighbourhoods.neighbourhood
WHERE amenities LIKE '%TV%' AND Neighbourhoods.neighbourhood = 'ΝΕΟΣ ΚΟΣΜΟΣ'

------------------------------------------------------------------------------------------------

/* QUERY 4
Checks whether there is an apartment without reviews
Output: 1 rows
*/
SELECT COUNT(*) AS apartments_with_no_reviews
FROM ( SELECT Listings.id, Reviews.listing_id 
	  FROM  Listings  LEFT OUTER JOIN Reviews ON Listings.id = Reviews.listing_id) AS new_table
WHERE new_table.id = null ;

------------------------------------------------------------------------------------------------

/* QUERY 5
Finds minimum and maximum nights someone can rent an apartment per neighbourhood and also the average number of reviews of all apartments per neighbourhood
Output: 45 rows
*/
SELECT Listings.neighbourhood_cleansed,MIN(minimum_nights) as minimum_nights_per_hood,MAX(maximum_nights) as maximum_nights_per_hood,to_char(AVG DISTINCT (number_of_reviews),'99999999999999999D99') AS average_number_of_reviews
FROM Listings
INNER JOIN Neighbourhoods
ON Listings.neighbourhood_cleansed=Neighbourhoods.neighbourhood
GROUP BY (Listings.neighbourhood_cleansed);


------------------------------------------------------------------------------------------------

/* QUERY 6
Checks whether there is a neighbourhood included in table Neighbourhoods but not being included in table Geolocation
Output: 1 rows
*/

SELECT COUNT(*) AS hood_no_included_in_geolocation
FROM ( SELECT Neighbourhoods.neighbourhood,Geolocation.properties_neighbourhood
	  FROM  Neighbourhoods  RIGHT OUTER JOIN Geolocation ON Neighbourhoods.neighbourhood = Geolocation.properties_neighbourhood) AS new_table
WHERE new_table.neighbourhood = NULL;

------------------------------------------------------------------------------------------------

/* QUERY 7
Finds all apartments which are available on a specific day, are private room and have exactly 3 beds
Output : 75 rows
*/

SELECT DISTINCT id,Listings.name,Calendar.date,Listings.beds,Listings.room_type FROM Listings
INNER JOIN Calendar
ON Listings.id=Calendar.listing_id
WHERE Calendar.date='2020-07-01' AND Listings.room_type='Private room' AND Listings.beds = 3;

------------------------------------------------------------------------------------------------

/* QUERY 8
Finds all apartments that offer breakfast and have flexible cancellation policy
Output = 16 rows
*/
SELECT DISTINCT Listings.id,Listings.name,Neighbourhoods.neighbourhood FROM Listings
INNER JOIN Neighbourhoods
ON Listings.neighbourhood_cleansed=Neighbourhoods.neighbourhood
WHERE description LIKE '%Breakfast%' AND cancellation_policy = 'flexible';

------------------------------------------------------------------------------------------------
/* QUERY 9
Find the first 10 houses that have been reviewed by a person called Susan,have at least 8.0 rating and over 200 reviews in total
Output:10 rows
*/

SELECT Listings.id,Reviews.date,Reviews.reviewer_id,Reviews.reviewer_name,Listings.review_scores_rating,Listings.number_of_reviews
FROM Listings
INNER JOIN Reviews
ON Listings.id=Reviews.listing_id
WHERE Reviews.reviewer_name='Susan' AND Listings.review_scores_rating >'8.00' AND Listings.number_of_reviews>200
LIMIT 10;

------------------------------------------------------------------------------------------------

/* QUERY 10
Finds all houses with the best review rating that are located either in neighbourhood of ΑΜΠΕΛΟΚΗΠΟΙ or ΓΚΥΖΗ,can be booked instantly and don't require guest profile picture
Output=347 rows
*/
SELECT Listings.name,Neighbourhoods.neighbourhood,Listings.review_scores_rating,Listings.require_guest_profile_picture
FROM Listings
INNER JOIN Neighbourhoods
ON Listings.neighbourhood_cleansed= Neighbourhoods.neighbourhood
WHERE Listings.neighbourhood_cleansed='ΑΜΠΕΛΟΚΗΠΟΙ' AND Listings.require_guest_profile_picture='false' AND Listings.instant_bookable='true' OR Listings.neighbourhood_cleansed='ΓΚΥΖΗ' AND Listings.require_guest_profile_picture='false' AND Listings.instant_bookable='true'
ORDER BY Listings.review_scores_rating ASC;

------------------------------------------------------------------------------------------------

/* QUERY 11
Finds all apartments are available on 2020-04-20 at ΚΥΨΕΛΗ neighbourhood
Output= 324 rows
*/
SELECT Listings.id, Listings.name, Listings.neighbourhood_cleansed,Calendar.date
FROM Listings
INNER JOIN Calendar
ON Listings.id = Calendar.listing_id
WHERE Calendar.date='2020-04-20' AND Listings.neighbourhood_cleansed='ΚΥΨΕΛΗ'; 


------------------------------------------------------------------------------------------------

/* QUERY 12
Finds all airbnbs that cost under 205$ USD and shows them in ascending order for price and in descending for review score rating
Output:471 rows
*/
SELECT Listings.id,Listings.name,Listings_Summary.price,Listings.review_scores_rating,Listings.listing_url
FROM Listings
INNER JOIN Listings_Summary
ON Listings.id=Listings_Summary.id
WHERE Listings_Summary.price <25 AND Listings.review_scores_rating>'90'
ORDER BY Listings_Summary.price ASC,Listings.review_scores_rating DESC;