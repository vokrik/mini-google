-- Adminer 3.7.1 MySQL dump

SET NAMES utf8;

DROP TABLE IF EXISTS `iterations`;
CREATE TABLE `iterations` (
  `url` varchar(256) COLLATE utf8_czech_ci NOT NULL,
  `pagerank` double NOT NULL,
  `iteration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;


DROP TABLE IF EXISTS `pagerank`;
CREATE TABLE `pagerank` (
  `url` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `pagerank` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `urls_urls`;
CREATE TABLE `urls_urls` (
  `url1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `url2` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `urls_words`;
CREATE TABLE `urls_words` (
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `word` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- 2014-04-26 19:35:44
