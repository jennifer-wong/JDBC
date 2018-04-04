/* 1. List all city information for cities with a population greater than 1 million. */
SELECT * 
FROM City 
WHERE Population > 1000000
ORDER BY Population DESC;

/* 2. List the country codes of countries that have cities with a population greater than 1 million. */
SELECT DISTINCT Country 
FROM City 
WHERE Population > 1000000
ORDER BY Country;

/* 3. For each country, list the city (or cities) with the largest population. */
SELECT c1.Country, c1.Province, c1.Name
FROM City c1
WHERE c1.Population = 
	(SELECT MAX(population)
     FROM City c2
     WHERE c1.Country = c2.Country)
ORDER BY Country;

/* 4. For each country, list its population and the total population of all neighboring countries. */
SELECT C.code, C.population, sum(neighborList.population) AS NeighborPopulation
FROM Country AS C, ((SELECT country1 AS countryA, country2 AS countryB 
	                    FROM Borders 
	                    UNION 
	                    SELECT country2 AS countryA, country1 AS countryB 
	                    FROM Borders) AS neighbor
INNER JOIN Country ON Country.code = countryB) AS neighborList
WHERE C.code = neighborList.countryA
GROUP BY C.code, C.population;

/* 5. List countries having a border with another country. */
SELECT Name 
FROM Country
WHERE Code IN
	(SELECT DISTINCT country1
	 FROM borders 
	 UNION 
	 SELECT DISTINCT country2
	 FROM borders)
ORDER BY Code;

/* 6. List countries having at least two borders with another country. */
SELECT Code
FROM Country
WHERE Code IN
	((SELECT DISTINCT country1
	 FROM borders
	 GROUP BY country1
	 HAVING COUNT(*) > 1 
	 UNION 
	 SELECT DISTINCT country2
	 FROM borders
	 GROUP BY country2
	 HAVING COUNT(*) > 1)
	 UNION
	 (SELECT DISTINCT country1
	 FROM borders
	 GROUP BY country1
	 HAVING COUNT(*) = 1 
	 INTERSECT 
	 SELECT DISTINCT country2
	 FROM borders
	 GROUP BY country2
	 HAVING COUNT(*) = 1))
ORDER BY Code;

/* 7. List countries having no border with another country. */
SELECT Name
FROM Country
WHERE (Code NOT IN (SELECT Country1 FROM borders) AND 
	Code NOT IN (SELECT Country2 FROM borders))
ORDER BY Name;

/* 8. List all countries in Europe or Asia. */
SELECT Name
FROM encompasses, Country
WHERE encompasses.Country IN
	(SELECT Country
	 FROM encompasses 
	 GROUP BY Country 
	 HAVING COUNT(*) < 2)
	AND Continent IN ('Asia','Europe')
	AND Country.Code = encompasses.Country
ORDER BY Name;

/* 9. List all countries that are located in both Europe and Asia. */
SELECT Name
FROM Country
WHERE Code IN
	(SELECT e1.Country
	 FROM encompasses e1
	 INNER JOIN encompasses e2 ON e1.country = e2.country 
	 	AND e1.continent = 'Europe'
	 	AND e2.continent = 'Asia')
ORDER BY Name;

/* 10. Find the city (or cities) with the largest population. */
SELECT Name
FROM City
WHERE Population =
	(SELECT MAX(Population)
	 FROM City);
