package models;

import java.io.Serializable;

/**
 *
 * @author Marcin Wisniewski
 */
public class Ticket implements Serializable {
    private int id, quantity;
    private String eventName, price, startDate, expiryDate;
    
    // constructor
    public Ticket(int id, String eventName, String price, String startDate, String expiryDate, int quantity)
    {
        this.id = id;
        this.eventName = eventName;
        this.price = price;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }
    
    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    
    // reduces quantity
    public void reduceQuantity(int qty)
    {
        if(qty > 0)
        {
            if(this.quantity > 0)
            {
                if((this.quantity-qty) > 0)
                {
                    this.quantity -= qty;
                }
            }
        }
    }
    
    // increases qyantity
    public void increaseQuantity(int qty)
    {
        if(qty > 0)
        {
            this.quantity += qty;
        }
    }
}
