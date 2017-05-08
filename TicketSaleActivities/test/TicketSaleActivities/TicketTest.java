/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicketSaleActivities;

import models.Ticket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class TicketTest {
    
    public TicketTest() {
    }

    /**
     * Test of increaseQuantity method, of class Ticket.
     */
    @Test
    public void testIncreaseQuantity() {
        int qty = 2;
        Ticket ticket = new Ticket(1, "", "", "", "", 10);
        ticket.increaseQuantity(qty);
        assertEquals(12, ticket.getQuantity());
    }
    
    /**
     * Test of increaseQuantity method, of class Ticket.
     */
    @Test
    public void testIncreaseQuantity2() {
        int qty = -2;
        Ticket ticket = new Ticket(1, "", "", "", "", 10);
        ticket.increaseQuantity(qty);
        assertEquals(10, ticket.getQuantity());
    }
    
    /**
     * Test of reduceQuantity method, of class Ticket
     */
    @Test
    public void testReduceQuantity()
    {
        int qty = 2;
        Ticket ticket = new Ticket(2, "", "", "", "", 10);
        ticket.reduceQuantity(qty);
        assertEquals(8, ticket.getQuantity());
    }
    
    /**
     * Test of reduceQuantity method, of class Ticket
     */
    @Test
    public void testReduceQuantity2()
    {
        int qty = -2;
        Ticket ticket = new Ticket(2, "", "", "", "", 10);
        ticket.reduceQuantity(qty);
        assertEquals(10, ticket.getQuantity());
    }
    
    /**
     * Test of reduceQuantity method, of class Ticket
     */
    @Test
    public void testReduceQuantity3()
    {
        int qty = 2;
        Ticket ticket = new Ticket(2, "", "", "", "", 0);
        ticket.reduceQuantity(qty);
        assertEquals(0, ticket.getQuantity());
    }
}
