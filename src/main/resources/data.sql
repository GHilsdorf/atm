DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS unit;

CREATE TABLE `user` (
  id INT PRIMARY KEY,
  pin INT NOT NULL,
  balance INT NOT NULL,
  overdraft INT NOT NULL
);
COMMENT ON COLUMN `user`.id 
   IS 'used as account number';

CREATE TABLE unit (
  id INT PRIMARY KEY AUTO_INCREMENT,
  fifty_notes INT NOT NULL,
  twenty_notes INT NOT NULL,
  ten_notes INT NOT NULL,
  five_notes INT NOT NULL
);

INSERT INTO `user` (id, pin, balance, overdraft) VALUES 
(123456789,1234,800,200),
(987654321,4321,1230,150);

INSERT INTO unit (fifty_notes, twenty_notes, ten_notes, five_notes) VALUES 
(10,30,30,20);