package com.stockcharts.interns.model;

public class Driver implements User {
    private int driverID;
    private String firstName;
    private String lastName;
    private String email;
    private String hash;
    private String phone;
    private String location;

    private Driver(Builder builder) {
        this.driverID = builder.driverID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.hash = builder.hash;
        this.phone = builder.phone;
        this.location = builder.location;
    }

    // Builder class
    public static class Builder {
        private int driverID = -1;
        private String firstName;
        private String lastName;
        private String email;
        private String hash;
        private String phone;
        private String location;

        public Builder withDriverID(int driverID) {
            this.driverID = driverID;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withHash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public Driver build() {
            Driver driver = new Driver(this);
            driver.driverID = this.driverID;
            driver.firstName = this.firstName;
            driver.lastName = this.lastName;
            driver.email = this.email;
            driver.hash = this.hash;
            driver.phone = this.phone;
            driver.location = this.location;
            return driver;
        }
    }

    public int getDriverID() {
        return driverID;
    }

    public int getUserID() {
        return this.driverID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

