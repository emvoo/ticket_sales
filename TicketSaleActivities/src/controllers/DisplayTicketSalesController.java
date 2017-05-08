package controllers;

import models.Ticket;
import models.TicketSale;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class DisplayTicketSalesController implements Initializable {
    private CompanyController companyController;
    private Ticket ticket;
    
    @FXML private AnchorPane displayTicketSalesAnchorPane;
    @FXML private Label noOfTickets;
    
    private static final String LOW_SALES= "-fx-text-fill: #fa0a0a;";
    private static final String AVERAGE_SALES= "-fx-text-fill: #ffa81f;";
    private static final String HIGH_SALES= "-fx-text-fill: #27ff1f;";
    
    // constructor
    public DisplayTicketSalesController(CompanyController companyController, Ticket ticket)
    {
        this.companyController = companyController;
        this.ticket = ticket;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DisplayTicketSalesGUI.fxml"));
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "DisplayTicketSalesGUI.fxml file not loaded properly.");
        }
    }
    
    // action events
    @FXML
    public void setDisplayTicketSalesAnchorPane(AnchorPane displayTicketSalesAnchorPane)
    {
        this.displayTicketSalesAnchorPane = displayTicketSalesAnchorPane;
    }
    
    @FXML
    public AnchorPane getDisplayTicketSalesAnchorPane()
    {
        return this.displayTicketSalesAnchorPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setSales();
        }
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DisplayTicketSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // sets sales values per selected ticket
    private void setSales() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<TicketSale> ticketSales = companyController.company.getTicketsSalesList();
            int noOfTicketsSold = 0;
            for(TicketSale ts:ticketSales)
            {
                if(ts.getEventName().equals(ticket.getEventName()))
                {
                    noOfTicketsSold += ts.getQuantity();
                }
            }
            if(noOfTicketsSold < 10)
            {
                noOfTickets.setStyle(LOW_SALES);
            }
            else if(noOfTicketsSold > 10 && noOfTicketsSold < 40)
            {
                noOfTickets.setStyle(AVERAGE_SALES);
            }
            else
            {
                noOfTickets.setStyle(HIGH_SALES);
            }
            noOfTickets.setText(Integer.toString(noOfTicketsSold));
    }
}
