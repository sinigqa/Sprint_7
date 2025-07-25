package org.example.config;

public class Endpoints {

    public static class Courier {
        public static final String CREATE = "/api/v1/courier";
        public static final String LOGIN = "/api/v1/courier/login";
        public static final String DELETE = "/api/v1/courier/{id}";
    }

    public static class Order {
        public static final String CREATE = "/api/v1/orders";
        public static final String CANCEL = "/api/v1/orders/cancel";
        public static final String LIST = "/api/v1/orders";
    }
}
