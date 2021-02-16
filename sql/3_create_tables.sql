-- Table `users`
CREATE TABLE IF NOT EXISTS `users`
(
    `id`          INT                 NOT NULL AUTO_INCREMENT,
    `login`       VARCHAR(255)        NOT NULL,
    `password`    CHAR(32)            NOT NULL,
    `email`       VARCHAR(255) UNIQUE NOT NULL,
    `date_of_reg` DATE                NOT NULL,
    #
    # 0 - администратор
    # 1 - пользователь
    # 2 - гость
    #
    `role`        TINYINT             NOT NULL CHECK (`role` IN (0, 1, 2)),
    `is_active`   TINYINT             NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- Table `user_info`
CREATE TABLE IF NOT EXISTS `user_info`
(
    `id`            INT          NOT NULL AUTO_INCREMENT,
    `surname`       VARCHAR(255) NOT NULL,
    `name`          VARCHAR(255) NOT NULL,
    `pseudonym`     VARCHAR(255) NOT NULL,
    #
    # 0 - читатель
    # 1 - новичок
    # 2 - любитель
    # 3 - профи
    #
    `level`         TINYINT      NOT NULL CHECK (`level` IN (0, 1, 2, 3)),
    `date_of_birth` DATE         NOT NULL,
    `users_id`      INT          NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`users_id`)
        REFERENCES `users` (`id`)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- Table `genre`
CREATE TABLE IF NOT EXISTS `genre`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- Table `publications`
CREATE TABLE IF NOT EXISTS `publications`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255) NULL DEFAULT NULL,
    `content`     TEXT         NULL DEFAULT NULL,
    `public_date` DATE         NOT NULL,
    `user_id`     INT          NOT NULL,
    `genre_id`    INT          NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (`genre_id`)
        REFERENCES `genre` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- Table `publication_comment`
CREATE TABLE IF NOT EXISTS `publication_comment`
(
    `id`              INT  NOT NULL AUTO_INCREMENT,
    `user_id`         INT  NULL DEFAULT NULL,
    `text`            TEXT NULL DEFAULT NULL,
    `comment_data`    DATE NOT NULL,
    `publications_id` INT  NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (`publications_id`)
        REFERENCES `publications` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;