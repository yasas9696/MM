-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2019 at 08:48 AM
-- Server version: 10.1.39-MariaDB
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `me`
--

-- --------------------------------------------------------

--
-- Table structure for table `accessories`
--

CREATE TABLE `accessories` (
  `aID` int(11) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `wholesalePrice` float DEFAULT NULL,
  `wp` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `itemID` int(11) NOT NULL,
  `type` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `monthlyreport`
--

CREATE TABLE `monthlyreport` (
  `rID` int(11) NOT NULL,
  `itemID` int(11) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `sale` float DEFAULT NULL,
  `purchase` float DEFAULT NULL,
  `type` varchar(256) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `phones`
--

CREATE TABLE `phones` (
  `pID` int(11) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `wholesalePrice` float DEFAULT NULL,
  `wp` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `photocopy`
--

CREATE TABLE `photocopy` (
  `cID` int(11) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `wholesalePrice` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `Name` varchar(256) DEFAULT NULL,
  `Quantity` decimal(32,0) DEFAULT NULL,
  `Price` float DEFAULT NULL,
  `Amount` double DEFAULT NULL,
  `Type` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`Name`, `Quantity`, `Price`, `Amount`, `Type`) VALUES
('Tempered Glass - Nokia Lumia 925', '21', 1200, 25200, 'Sale'),
('A4', '15', 15, 225, 'Sale'),
('htc - 626', '10', 26000, 260000, 'Purchase'),
('htc - 626', '13', 27500, 357500, 'Sale'),
('apple - iphone', '15', 70000, 1050000, 'Purchase'),
('apple - iphone', '13', 75000, 975000, 'Sale'),
('charger - nokia', '15', 200, 3000, 'Purchase'),
('charger - nokia', '16', 250, 4000, 'Sale'),
('current bill', '2', 2500, 5000, 'Sale');

-- --------------------------------------------------------

--
-- Table structure for table `report2`
--

CREATE TABLE `report2` (
  `Name` varchar(256) DEFAULT NULL,
  `Quantity` decimal(32,0) DEFAULT NULL,
  `Total_Sales` double DEFAULT NULL,
  `Total_Purchases` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report2`
--

INSERT INTO `report2` (`Name`, `Quantity`, `Total_Sales`, `Total_Purchases`) VALUES
('apple - iphone', '20', 0, 1400000),
('apple - iphone', '10', 750000, 0),
('hand free - samsung', '10', 5000, 0),
('nokiaa - lumiA 925', '10', 54510, 0),
('temperd glass - oppo', '20', 0, 15000),
('Tempered Glass - Nokia Lumia 925', '20', 0, 22000);

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `sID` int(11) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `path` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`sID`, `name`, `path`) VALUES
(1, 'server', 'C:\\xampp\\mysql\\bin'),
(2, 'report', 'D:/Documents/NetBeansProjects/ME/src/Transaction_Management');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `saleID` int(11) NOT NULL,
  `itemID` int(11) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accessories`
--
ALTER TABLE `accessories`
  ADD PRIMARY KEY (`aID`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`itemID`);

--
-- Indexes for table `monthlyreport`
--
ALTER TABLE `monthlyreport`
  ADD PRIMARY KEY (`rID`);

--
-- Indexes for table `phones`
--
ALTER TABLE `phones`
  ADD PRIMARY KEY (`pID`);

--
-- Indexes for table `photocopy`
--
ALTER TABLE `photocopy`
  ADD PRIMARY KEY (`cID`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`sID`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`saleID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `itemID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `monthlyreport`
--
ALTER TABLE `monthlyreport`
  MODIFY `rID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `sID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `saleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=164;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accessories`
--
ALTER TABLE `accessories`
  ADD CONSTRAINT `fk_accessories_items` FOREIGN KEY (`aID`) REFERENCES `items` (`itemID`),
  ADD CONSTRAINT `fk_accessory_items` FOREIGN KEY (`aID`) REFERENCES `items` (`itemID`);

--
-- Constraints for table `phones`
--
ALTER TABLE `phones`
  ADD CONSTRAINT `fk_items_phones` FOREIGN KEY (`pID`) REFERENCES `items` (`itemID`),
  ADD CONSTRAINT `fk_phones_items` FOREIGN KEY (`pID`) REFERENCES `items` (`itemID`);

--
-- Constraints for table `photocopy`
--
ALTER TABLE `photocopy`
  ADD CONSTRAINT `fk_photocopy_items` FOREIGN KEY (`cID`) REFERENCES `items` (`itemID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
