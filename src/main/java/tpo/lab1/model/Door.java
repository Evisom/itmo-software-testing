package tpo.lab1.model;

import tpo.lab1.model.enums.LockStatus;

public class Door {
    private boolean isOpen;
    private LockStatus lockStatus;

    public Door(boolean isOpen, LockStatus lockStatus) {
        this.isOpen = isOpen;
        this.lockStatus = lockStatus;
    }

    public void open() { isOpen = true; }
    public void close() { isOpen = false; }

    public boolean isOpen() {
        return isOpen;
    }

    public LockStatus getLockStatus() {
        return lockStatus;
    }
}
