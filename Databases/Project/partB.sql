------------- Statistics -------------


select count(*) as number_of_movies_per_year,date_part('year',release_date) from movies_metadata
group by date_part('year',release_date)
order by date_part('year',release_date);

select count(*) as number_of_movies_per_genre,genres from movies_metadata
group by genres;

select count(*) as number_of_movies,genres,date_part('year',release_date) from movies_metadata
group by genres,date_part('year',release_date);

select count(*) as number_of_movies,to_char(avg(cast(rating as float)),'99999999999999999D99') as average_rating,genres from movies_metadata
inner join Ratings on Ratings.movieId = Movies_Metadata.id
group by genres;

select count(*) as number_of_ratings,userId from Ratings
group by userId;

select to_char(avg(cast(rating as float)),'99999999999999999D99') as average_rating_per_user,userId from Ratings
group by userId;



------------- Create View Table -------------

create view user_view as
select userId,count(*) as number_of_ratings_of_user,
to_char(avg(cast(rating as float)),'99999999999999999D99') as average_rating
from Ratings
group by userId;
							