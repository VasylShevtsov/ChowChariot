package com.stockcharts.interns.model;

public class Customer implements User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String hash;
    private String phone;
    private String location;

    private Customer(Builder builder) {
        this.userID = builder.userID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.hash = builder.hash;
        this.phone = builder.phone;
        this.location = builder.location;
    }

    // Getters and setters for the fields

    public void setUserID(int id) {
        this.userID = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getHash() {
        return hash;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Builder class

    public static class Builder {
        private int userID = -1;
        private String firstName;
        private String lastName;
        private String email;
        private String hash;
        private String phone;
        private String location;

        public Builder userID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder hash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}

