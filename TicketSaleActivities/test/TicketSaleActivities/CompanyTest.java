/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicketSaleActivities;


import models.TicketSale;
import models.Company;
import models.Ticket;
import models.Agent;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class CompanyTest {
    public CompanyTest() {
    }

    /**
     * Test of getAgentLastID method, of class Company.
     */
    @Test
    public void testGetAgentLastID() throws Exception {
        Company instance = new Company();
        ArrayList<Agent> agentsList = instance.getAgentsList();
        Agent agent = agentsList.get(agentsList.size()-1);
        int expResult = agent.getId();
        int result = instance.getAgentLastID();
        assertEquals(expResult, result);
    }

    /**
     * Test of assignAgentID method, of class Company.
     */
    @Test
    public void testAssignAgentID() throws Exception {
        Company instance = new Company();
        ArrayList<Agent> agentsList = instance.getAgentsList();
        Agent agent = agentsList.get(agentsList.size()-1);
        int expResult = agent.getId()+1;
        int result = instance.assignAgentID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTicketSaleLastID method, of class Company.
     */
    @Test
    public void testGetTicketSaleLastID() throws Exception {
        Company instance = new Company();
        ArrayList<TicketSale> ticketSaleList = instance.getTicketsSalesList();
        TicketSale ts = ticketSaleList.get(ticketSaleList.size()-1);
        int expResult = ts.getSaleID();
        int result = instance.getTicketSaleLastID();
        assertEquals(expResult, result);
    }

    /**
     * Test of assignTicketSaleID method, of class Company.
     */
    @Test
    public void testAssignTicketSaleID() throws Exception {
        Company instance = new Company();
        ArrayList<TicketSale> ticketSaleList = instance.getTicketsSalesList();
        TicketSale ts = ticketSaleList.get(ticketSaleList.size()-1);
        int expResult = ts.getSaleID()+1;
        int result = instance.assignTicketSaleID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTicketLastID method, of class Company.
     */
    @Test
    public void testGetTicketLastID() throws Exception {
        Company instance = new Company();
        ArrayList<Ticket> ticketsList = instance.getTicketsList();
        Ticket ticket = ticketsList.get(ticketsList.size()-1);
        int expResult = ticket.getId();
        int result = instance.getTicketLastID();
        assertEquals(expResult, result);
    }

    /**
     * Test of assignTicketID method, of class Company.
     */
    @Test
    public void testAssignTicketID() throws Exception {
        Company instance = new Company();
        ArrayList<Ticket> ticketsList = instance.getTicketsList();
        Ticket ticket = ticketsList.get(ticketsList.size()-1);
        int expResult = ticket.getId()+1;
        int result = instance.assignTicketID();
        assertEquals(expResult, result);
    }
}
