package org.example.task3;

import org.example.task3.enams.LockStatus;

public class Door {
    private boolean isOpen;
    private LockStatus lockStatus;

    public Door(boolean isOpen, LockStatus lockStatus) {
        this.isOpen = isOpen;
        this.lockStatus = lockStatus;
    }

    public void open() { isOpen = true; }
    public void close() { isOpen = false; }
}
