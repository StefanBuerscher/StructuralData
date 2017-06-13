-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 13. Jun 2017 um 12:21
-- Server-Version: 10.1.21-MariaDB
-- PHP-Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `db_struct`
--

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

--
-- Daten für Tabelle `bribery`
--

INSERT INTO `bribery` (`FK_Politician`, `FK_Lobbyist`, `Value`, `Reason`, `Confirmed`) VALUES
(0, 1, 10000, 'Murkraftwerk', 1),
(0, 2, 12000, 'Gesetz1', 1),
(0, 3, 15000, 'Gesetz2', 1),
(1, 2, 8000, 'Gesetz3', 1),
(2, 2, 4000, 'Gesetz4', 0),
(3, 1, 9000, 'Gesetz1', 1),
(3, 4, 6000, 'Gesetz13', 0),
(5, 4, 12000, 'Gesetz8', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lobbyist`
--

CREATE TABLE `lobbyist` (
  `Id` int(11) NOT NULL,
  `Forename` text NOT NULL,
  `Surname` text NOT NULL,
  `DOB` date NOT NULL,
  `Company` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `lobbyist`
--

INSERT INTO `lobbyist` (`Id`, `Forename`, `Surname`, `DOB`, `Company`) VALUES
(1, 'Fritz', 'Phantom', '2017-06-11', 'Shell'),
(2, 'Tom', 'Turbo', '2016-05-11', 'BP'),
(3, 'Hans', 'Hermann', '2015-05-11', 'Estag'),
(4, 'Otto', 'Normalverbraucher', '2013-05-11', 'Monsanto'),
(5, 'Hans', 'Huber', '2012-02-10', 'Springer');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `politician`
--

CREATE TABLE `politician` (
  `Id` int(11) NOT NULL,
  `Forename` text NOT NULL,
  `Surname` text NOT NULL,
  `DOB` date NOT NULL,
  `Party` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `politician`
--

INSERT INTO `politician` (`Id`, `Forename`, `Surname`, `DOB`, `Party`) VALUES
(0, 'Siegfried', 'Nagl', '1963-04-18', 'ÖVP'),
(1, 'Mario', 'Eustacchio', '1938-03-13', 'FPÖ'),
(2, 'Alexander', 'Van da Bellen', '1877-12-12', 'Die Grünen'),
(3, 'Wolfgang', 'Schüssel', '1950-02-12', 'ÖVP'),
(4, 'Jesus', 'Christus', '0000-01-01', 'KPÖ'),
(5, 'Alfred', 'Gusenbauer', '1957-12-04', 'SPÖ'),
(6, 'Michael', 'Ehmann', '1960-04-04', 'SPÖ'),
(7, 'Eva', 'Glawischnig', '1945-12-12', 'Die Grünen');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `bribery`
--
ALTER TABLE `bribery`
  ADD PRIMARY KEY (`FK_Politician`,`FK_Lobbyist`);

--
-- Indizes für die Tabelle `politician`
--
ALTER TABLE `politician`
  ADD PRIMARY KEY (`Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
