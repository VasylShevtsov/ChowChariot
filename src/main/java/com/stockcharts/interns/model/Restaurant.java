package com.stockcharts.interns.model;

public class Restaurant implements User {
    private int restID;
    private String name;
    private final int menuID;
    private String hash;
    private String email;
    private String cuisineType;
    private String location;
    private String phone;
    private String hours;

    private Restaurant(Builder builder) {
        this.restID = builder.restID;
        this.name = builder.name;
        this.menuID = builder.menuID;
        this.email = builder.email;
        this.cuisineType = builder.cuisineType;
        this.location = builder.location;
        this.phone = builder.phone;
        this.hours = builder.hours;
        this.hash = builder.hash;
    }


    // Getters and setters for the fields

    public int getRestID() {
        return restID;
    }

    public int getUserID() {
        return this.restID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenuID() {
        return menuID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHours() {
        return hours;
    }

    public String getCuisineType() {
        return this.cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
    
    public String getHash() {
        return hash;
    }

    public void setHash(String newhash) {
        hash = newhash;
    }

    public void setRestID(int id) {
        this.restID = id;
    }

    // Builder class

    public static class Builder {
        private int restID = -1;
        private String name;
        private int menuID;
        private String hash;
        private String cuisineType;
        private String email;
        private String location;
        private String phone;
        private String hours;

        public Builder restID(int restID) {
            this.restID = restID;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cuisineType(String cuisineType) {
            this.cuisineType = cuisineType;
            return this;
        }

        public Builder menuID(int menuID) {
            this.menuID = menuID;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder hours(String hours) {
            this.hours = hours;
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

        public Restaurant build() {
            return new Restaurant(this);
        }
    }

    
}
