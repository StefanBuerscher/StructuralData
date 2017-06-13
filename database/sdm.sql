-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 13. Jun 2017 um 13:26
-- Server-Version: 10.1.21-MariaDB
-- PHP-Version: 5.6.30

-- SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `sdm`
--
CREATE DATABASE IF NOT EXISTS `sdm` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sdm`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bribery`
--

CREATE TABLE `bribery` (
  `FK_Politician` int(11) NOT NULL,
  `FK_Lobbyist` int(11) NOT NULL,
  `Value` int(11) NOT NULL,
  `Reason` text NOT NULL,
  `Confirmed` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lobbyist`
--

CREATE TABLE `lobbyist` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Forename` text NOT NULL,
  `Surname` text NOT NULL,
  `DOB` date NOT NULL,
  `Company` text NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `politician`
--

CREATE TABLE `politician` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Forename` text NOT NULL,
  `Surname` text NOT NULL,
  `DOB` date NOT NULL,
  `Party` text NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `politician`
--

INSERT INTO `politician` (`Forename`, `Surname`, `DOB`, `Party`) VALUES
('Siegfried', 'Nagl', '1963-04-18', 'ÖVP'),
('Mario', 'Eustacchio', '1938-03-13', 'FPÖ'),
('Alexander', 'Van da Bellen', '1877-12-12', 'Die Grünen'),
('Wolfgang', 'Schüssel', '1950-02-12', 'ÖVP'),
('Jesus', 'Christus', '0000-01-01', 'KPÖ'),
('Alfred', 'Gusenbauer', '1957-12-04', 'SPÖ'),
('Michael', 'Ehmann', '1960-04-04', 'SPÖ'),
('Eva', 'Glawischnig', '1945-12-12', 'Die Grünen');

--
-- Daten für Tabelle `lobbyist`
--

INSERT INTO `lobbyist` (`Forename`, `Surname`, `DOB`, `Company`) VALUES
('Fritz', 'Phantom', '2017-06-11', 'Shell'),
('Tom', 'Turbo', '2016-05-11', 'BP'),
('Hans', 'Hermann', '2015-05-11', 'Estag'),
('Otto', 'Normalverbraucher', '2013-05-11', 'Monsanto'),
('Hans', 'Huber', '2012-02-10', 'Springer');


--
-- Daten für Tabelle `bribery`
--

INSERT INTO `bribery` (`FK_Politician`, `FK_Lobbyist`, `Value`, `Reason`, `Confirmed`) VALUES
(1, 1, 10000, 'Murkraftwerk', 1),
(1, 2, 12000, 'Gesetz1', 1),
(1, 3, 15000, 'Gesetz2', 1),
(2, 2, 8000, 'Gesetz3', 1),
(3, 2, 4000, 'Gesetz4', 0),
(4, 1, 9000, 'Gesetz1', 1),
(5, 4, 6000, 'Gesetz13', 0),
(6, 4, 12000, 'Gesetz8', 1);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `bribery`
--
ALTER TABLE `bribery`
  ADD PRIMARY KEY (`FK_Politician`,`FK_Lobbyist`),
  ADD KEY `FK_Lobbyist` (`FK_Lobbyist`);


--
-- Constraints der Tabelle `bribery`
--
ALTER TABLE `bribery`
  ADD CONSTRAINT `bribery_ibfk_1` FOREIGN KEY (`FK_Politician`) REFERENCES `politician` (`Id`),
  ADD CONSTRAINT `bribery_ibfk_2` FOREIGN KEY (`FK_Lobbyist`) REFERENCES `lobbyist` (`Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
