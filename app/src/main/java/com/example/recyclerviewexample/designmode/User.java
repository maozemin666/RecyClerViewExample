package com.example.recyclerviewexample.designmode;

public class User {

    private final int id;
    private final String name;
    private int age;
    private String address;
    private String phone;

    public User(UserBuilder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.age = builder.age;
        this.address = builder.address;
        this.phone = builder.phone;
    }

    public static class UserBuilder {

        private final int id;
        private final String name;
        private int age;
        private String address;
        private String phone;

        public User build() {
            User user = new User(this);
            return user;
        }

        public UserBuilder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public UserBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
    }
}
