CREATE TABLE IF NOT EXISTS `author` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `firstname` varchar(255) DEFAULT NULL,
    `lastname` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `book` (
                                      `author_id` int DEFAULT NULL,
                                      `book_store_id` int DEFAULT NULL,
                                      `category_id` int DEFAULT NULL,
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `book_store` (
                                            `id` int NOT NULL AUTO_INCREMENT,
                                            `address` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `category` (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `rent_log` (
                                          `book_id` int DEFAULT NULL,
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `user_id` int DEFAULT NULL,
                                          `end_date` datetime(6) DEFAULT NULL,
    `start_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `email` varchar(255) DEFAULT NULL,
    `firstname` varchar(255) DEFAULT NULL,
    `lastname` varchar(255) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
