package com.elif.bookstoreapi.containers;

import org.testcontainers.containers.MySQLContainer;

public class BookStoreApiMysqlContainer extends MySQLContainer<BookStoreApiMysqlContainer> {

    private static final String IMAGE_VERSION = "mysql:5.7";
    private static BookStoreApiMysqlContainer container;

    public BookStoreApiMysqlContainer() {
        super(IMAGE_VERSION);
    }

    public static BookStoreApiMysqlContainer getInstance() {
        if (container == null) {
            container = new BookStoreApiMysqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}
