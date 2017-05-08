package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcin Wisniewski
 * 
 *      Company class holds all details about:
 *          - Agents
 *          - Tickets
 *          - Ticket Sales
 */
public class Company implements Serializable {
    private ArrayList<Agent> agentsList;
    private ArrayList<Ticket> ticketsList;
    private ArrayList<TicketSale> ticketsSalesList;
    private String fileNameAgents;
    private String fileNameTickets;
    private String fileNameTicketSales;
    private Error error;
    
    // constructor
    public Company()
    {
        fileNameAgents = "agentsList.txt";
        fileNameTickets = "ticketsList.txt";
        fileNameTicketSales = "ticketSalesList.txt";
        agentsList = new ArrayList<>();
        ticketsList = new ArrayList<>();
        ticketsSalesList = new ArrayList<>();
        // load Agents
        initAgents();
        // load Tickets
        initTickets();
        // load Ticket sales
        initTicketSales();
    }
    
    // AGENTS BLOCK
    
    // method to initialize agents list on the first run 
    private void initAgents()
    {
        // instantiate file storing Agents
        File file = new File(fileNameAgents);
        boolean exists = file.exists();
        // if file exists load its contents into system
        if(exists)
        {
            try {
                getAgentsList();
            } catch (IOException | ClassNotFoundException ex) {
                error = new Error("lol");
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // if file doesnt exist create it and save predefined list of Agents
        else
        {
            try {
                initialAgents();
            } catch (IOException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // method to initiate list of Agents on the first run
    // if file of agents list does not exist
    public void initialAgents() throws IOException
    {
        agentsList.add(new Agent(1, "Ticketmaster", "London", "0207654321", true));
        agentsList.add(new Agent(2, "TicketWeb", "Manchester", "0176543222", true));
        agentsList.add(new Agent(3, "TicketsOnline", "Birmingham", "0197654323", true));
        saveListOfAgents(agentsList);
    }

    
    // assigns agents list from a file to agentsList 
    public ArrayList<Agent> getAgentsList() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        agentsList = readListOfAgents();
        return agentsList;
    }
    
    // gets last inserted agent id
    public int getAgentLastID() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int lastID = 0;
        ArrayList<Agent> tmp = this.readListOfAgents();
        Iterator it = tmp.iterator();
        while(it.hasNext())
        {
            Agent a = (Agent)it.next();
            lastID = a.getId();
        }
        return lastID;
    }
    
    // assigns id to newly created agent
    public int assignAgentID() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int id = getAgentLastID();
        
        if(id==0)
        {
            return 0;
        }
        else
        {
            return id+1;
        }
    }
    
    public int getTicketSaleLastID() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int lastID = 0;
        ArrayList<TicketSale> tmp = this.readListOfTicketsSales();
        Iterator it = tmp.iterator();
        while(it.hasNext())
        {
            TicketSale ts = (TicketSale)it.next();
            lastID = ts.getSaleID();
        }
        return lastID;
    }
    
    // allocateID to ticketSale
    public int assignTicketSaleID() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int id = getTicketSaleLastID();
        
        if(id==0)
        {
            return 0;
        }
        else
        {
            return id+1;
        }
    }
    
    // file handling block
    public void saveListOfAgents(ArrayList<Agent> agentsList) throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream(fileNameAgents);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(agentsList);
        oos.close();
    }
    
    private ArrayList<Agent> readListOfAgents() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(fileNameAgents);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Agent> listOfAgents = (ArrayList<Agent>) ois.readObject();
        ois.close();
        return listOfAgents;
    }
    
    // END AGENT BLOCK
    
    
    // TICKETS BLOCK
    
    private void initTickets()
    {
        // instantiate file storing tickets
        File file = new File(fileNameTickets);
        boolean exists = file.exists();
        // if file exists load its contents into 
        if(exists)
        {
            try {
                getTicketsList();
            } catch (IOException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // if file doesnt exist create it and save predefined list of Agents
        else
        {
            try {
                initialTickets();
            } catch (IOException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // if file exists read it's contents and assign them to ticketsList
    public ArrayList<Ticket> getTicketsList() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ticketsList = readListOfTickets();
        return ticketsList;
    }
    
    // read contents of the file
    private ArrayList<Ticket> readListOfTickets() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(fileNameTickets);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Ticket> listOfTickets = (ArrayList<Ticket>) ois.readObject();
        ois.close();
        return listOfTickets;
    }
    
    // save tickets to file
    public void saveListOfTickets(ArrayList<Ticket> ticketsList) throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream(fileNameTickets);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ticketsList);
        oos.close();
    }
    
    // if file doesnt exist set these below as default Tickets
    private void initialTickets() throws IOException
    {
        ticketsList.add(new Ticket(1, "Metallica", "40.00", "2016-10-10", "2016-12-12", 100));
        ticketsList.add(new Ticket(2, "Nigel Kennedy", "60.00", "2016-10-08", "2017-10-30", 10));
        ticketsList.add(new Ticket(3, "Rihanna", "50.00", "2016-12-12", "2017-11-18", 300));
        saveListOfTickets(ticketsList);
    }
    
    // gets last inserted agent id
    public int getTicketLastID() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int lastID = 0;
        ArrayList<Ticket> test = this.readListOfTickets();
        Iterator it = test.iterator();
        while(it.hasNext())
        {
            Ticket t = (Ticket)it.next();
            lastID = t.getId();
        }
        return lastID;
    }
    
    // assigns id to newly created agent
    public int assignTicketID() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int id = getTicketLastID();
        
        if(id==0)
        {
            return 0;
        }
        else
        {
            return id+1;
        }
    }
    
    // TICKET SALES BLOCK
    
    private void initTicketSales()
    {
        // instantiate file storing ticket sales
        File file = new File(fileNameTicketSales);
        boolean exists = file.exists();
        if(exists)
        {
            try {
                getTicketsSalesList();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try {
                initialTicketsSales();
            } catch (IOException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // return ticket sales 
    public ArrayList<TicketSale> getTicketsSalesList() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ticketsSalesList = readListOfTicketsSales();
        return ticketsSalesList;
    }
    
    // read file containing ticket sales
    private ArrayList<TicketSale> readListOfTicketsSales() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(fileNameTicketSales);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<TicketSale> listOfTicketsSales = (ArrayList<TicketSale>)ois.readObject();
        ois.close();
        return listOfTicketsSales;
    }
    
    // save ticket sales to file
    public void saveListOfTicketsSales(ArrayList<TicketSale> ticketsSalesList) throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream(fileNameTicketSales);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ticketsSalesList);
        oos.close();
    }
    
    // if no file containing ticket sales is found set these values as default
    private void initialTicketsSales() throws IOException
    {
        ticketsSalesList.add(new TicketSale(1, 1, "Metallica", "2016-10-12", 1));
        ticketsSalesList.add(new TicketSale(2, 1, "Metallica", "2016-10-14", 4));
        ticketsSalesList.add(new TicketSale(3, 1, "Metallica", "2016-10-23", 6));
        ticketsSalesList.add(new TicketSale(4, 2, "Nigel Kennedy", "2016-10-23", 2));
        ticketsSalesList.add(new TicketSale(5, 2, "Nigel Kennedy", "2016-10-23", 3));
        ticketsSalesList.add(new TicketSale(6, 2, "Rihanna", "2016-10-23", 1));
        ticketsSalesList.add(new TicketSale(7, 3, "Metallica", "2016-10-23", 8));
        ticketsSalesList.add(new TicketSale(8, 3, "Rihanna", "2016-10-23", 2));
        ticketsSalesList.add(new TicketSale(9, 3, "Rihanna", "2016-10-23", 3));
        saveListOfTicketsSales(ticketsSalesList);
    }
}
