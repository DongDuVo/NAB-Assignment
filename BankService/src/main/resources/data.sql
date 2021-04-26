DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher (
  id INT AUTO_INCREMENT PRIMARY KEY,
  phone_number VARCHAR(250) NOT NULL,
  voucher_code VARCHAR(250) NOT NULL,
  created_date datetime NOT NULL,
  processed boolean default false
);