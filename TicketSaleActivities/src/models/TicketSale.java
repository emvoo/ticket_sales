package models;

import java.io.Serializable;

/**
 *
 * @author Marcin Wisniewski
 */
public class TicketSale implements Serializable {
    private int saleID, agentID, quantity;
    private String eventName, saleDate;
    
    // constructor
    public TicketSale(int saleID, int agentID, String eventName, String saleDate, int quantity)
    {
        this.saleID = saleID;
        this.agentID = agentID;
        this.eventName = eventName;
        this.saleDate = saleDate;
        this.quantity = quantity;
    }

    // getters and setters
    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
