package com.example.demo3;

/**
 *
 * @author Andrea Hayes
 * This class implements the Inhouse Part objects.
 */

public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor
     * @param machineId
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * * Setter
     * @param machineId the machineID to set
     */

    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }

    /**
     * * Getter
     * @param machineId the machineID to get
     */

    public int getMachineId() {

        return machineId;
    }
}


