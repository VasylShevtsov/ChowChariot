package com.stockcharts.interns.model;

public class Menu {
    private int menuID = -1;
    private int restID;

    public Menu(int menuID, int restID) {
        setMenuID(menuID);
        setRestID(restID);
    }

    // =============================== getters ==================================== \\

    public int getMenuID() {
        return menuID;
    }

    public int getRestID() {
        return restID;
    }

    // =============================== setters ==================================== \\

    public void setMenuID(int newMenuID) {
        menuID = newMenuID;
    }

    private void setRestID(int newRestID) {
        restID = newRestID;
    }

}

