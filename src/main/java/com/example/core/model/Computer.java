package com.example.core.model;

public abstract class Computer {

    private String computerNumber;
    private boolean unlocked;
    protected String computerType;

    protected Computer(String computerNumber, String computerType) {
        this.computerNumber = computerNumber;
        this.computerType = computerType;
    }

    public String getComputerNumber() {
        return computerNumber;
    }

    public void setComputerNumber(String computerNumber) {
        this.computerNumber = computerNumber;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public String getComputerType() {
        return computerType;
    }
}