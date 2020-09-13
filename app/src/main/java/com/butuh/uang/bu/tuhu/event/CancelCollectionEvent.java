package com.butuh.uang.bu.tuhu.event;

public class CancelCollectionEvent {
    private String serialNumber;

    public CancelCollectionEvent(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
