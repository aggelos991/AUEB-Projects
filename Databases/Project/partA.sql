------------- Create Tables -------------

create table Movies_Metadata(
   adult varchar(10),
   belongs_to_collection varchar(190),
   budget int,
   genres varchar(270),
   homepage varchar(250),
   id int,
   imdb_id varchar(10),
   original_language varchar(10),
   original_title varchar(110),
   overview varchar(1000),
   popularity varchar(10),
   poster_path varchar(40),
   production_companies varchar(1260),
   production_countries varchar(1040),
   release_date date,
   revenue bigint,
   runtime varchar(10),
   spoken_languages varchar(770),
   status varchar(20),
   tagline varchar(300),
   title varchar(110),
   video varchar(10),
   vote_average varchar(10),
   vote_count int
);

create table Credits(
   cast_info text,
   crew text,
   id int
);

create table Keywords(
   id int,
   keywords text
);

create table Links(
   movieId int,
   imdbId int,
   tmdbId int
);

create table Ratings(
   userId int,
   movieId int,
   rating varchar(10),
   timestamp int
);

------------- Create Primary/Foreign Keys -------------

ALTER TABLE Movies_Metadata
ADD PRIMARY KEY(id);

ALTER TABLE Credits
ADD PRIMARY KEY(id);

ALTER TABLE Keywords
ADD PRIMARY KEY(id);

ALTER TABLE ratings
ADD PRIMARY KEY(movieId,userId);

ALTER TABLE Links
ADD PRIMARY KEY(movieId);

ALTER TABLE Links
ADD FOREIGN KEY(movieId) REFERENCES Movies_Metadata(id);

ALTER TABLE Ratings
ADD FOREIGN KEY(movieId) REFERENCES Movies_Metadata(id);

ALTER TABLE Credits
ADD FOREIGN KEY(id) REFERENCES Movies_Metadata(id);

ALTER TABLE Keywords
ADD FOREIGN KEY(id) REFERENCES Movies_Metadata(id);

 


------------- Data Processing -------------


DELETE FROM Movies_Metadata WHERE id IN (
WITH cte AS (
    SELECT
        id,
        ROW_NUMBER() over(PARTITION BY id ORDER BY id DESC) AS rn
    FROM Movies_Metadata
)
SELECT id FROM cte WHERE rn > 1);

DELETE FROM Credits WHERE id IN (
WITH cte AS (
    SELECT
        id,
        ROW_NUMBER() over(PARTITION BY id ORDER BY id DESC) AS rn
    FROM Credits
)
SELECT id FROM cte WHERE rn > 1);

DELETE FROM Keywords WHERE id IN (
WITH cte AS (
    SELECT
        id,
        ROW_NUMBER() over(PARTITION BY id ORDER BY id DESC) AS rn
    FROM Keywords
)
SELECT id FROM cte WHERE rn > 1);


DELETE FROM Links
WHERE movieId NOT IN (SELECT m.id FROM Movies_Metadata m);
 
DELETE FROM Ratings
WHERE movieId NOT IN (SELECT m.id FROM Movies_Metadata m);



 

 
 