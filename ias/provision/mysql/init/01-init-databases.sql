-- Create databases
CREATE DATABASE IF NOT EXISTS payfirews;
CREATE DATABASE IF NOT EXISTS integration;

-- Create user
CREATE USER 'payfire'@'localhost' IDENTIFIED BY 'payfire';
CREATE USER 'payfire'@'%' IDENTIFIED BY 'payfire';

-- Grant privileges
GRANT ALL ON `payfirews`.* TO 'payfire'@'localhost';
GRANT ALL ON `payfirews`.* TO 'payfire'@'%';
GRANT ALL ON `integration`.* TO 'payfire'@'localhost';
GRANT ALL ON `integration`.* TO 'payfire'@'%';
FLUSH PRIVILEGES;