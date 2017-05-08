package controllers;

import models.Ticket;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class DeleteTicketController {
    private CompanyController companyController;
    private Ticket ticket;
    private ArrayList<Ticket> ticketsList;
    @FXML AnchorPane deleteTicketAnchorPane;
    
    // constructor
    public DeleteTicketController(CompanyController companyController, Ticket ticket)
    {
        this.ticket = ticket;
        this.companyController = companyController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DeleteTicketGUI.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // action events
    @FXML
    public void setDeleteTicketAnchorPane(AnchorPane deleteTicketGUI)
    {
        deleteTicketAnchorPane = deleteTicketGUI;
    }
    
    @FXML
    public AnchorPane getDeleteTicketAnchorPane()
    {
        return deleteTicketAnchorPane;
    }
    
    @FXML
    public void cancelTicketDeletion(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    private void confirmTicketDeletion(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ticketsList = companyController.company.getTicketsList();
        Iterator it = ticketsList.iterator();
        while(it.hasNext())
        {
            Ticket t = (Ticket) it.next();
            if(t.getId() == ticket.getId())
            {
                it.remove();
            }
        }
        companyController.setTicketsList(ticketsList);
        companyController.company.saveListOfTickets(ticketsList);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
