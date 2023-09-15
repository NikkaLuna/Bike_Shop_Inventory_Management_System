package com.example.demo3;

/**
 *
 * @author Andrea Hayes
 * This class implements the Outsourced Part objects and extends the Part class.
 */


public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructor
     //* @param companyName
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName ) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * * Setter
     //* @param companyName the companyName to set
     */

    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }

    /**
     * * Getter
     * //* @param companyName the companyName to get
     */

    public String getCompanyName() {

        return companyName;
    }
}