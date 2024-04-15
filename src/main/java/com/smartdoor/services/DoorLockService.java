package com.smartdoor.services;

import com.smartdoor.Actuator.DeadBolt;

public class DoorLockService {
    private boolean doorSwitch = false;
    private boolean lockedState;

    DeadBolt deadBoltSensor = new DeadBolt();

    public DoorLockService(){

    }
    public DoorLockService(boolean lockedState){
        this.lockedState = lockedState;
    }

    public void setLockedState(boolean accessState) {

        this.lockedState = !accessState;



       // deadBoltSensor.setDeadboltSensorLockedState(this.lockedState);

    }

    public boolean getLockedState() {
        return lockedState;
    }




}


