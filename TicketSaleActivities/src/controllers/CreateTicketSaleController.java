package controllers;

import models.Agent;
import models.Ticket;
import models.TicketSale;
import models.Validate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class CreateTicketSaleController implements Initializable {
    private CompanyController companyController;
    private Agent agent;
    private ArrayList<Ticket> availableTickets;
    private Ticket selectedTicket;
    @FXML private AnchorPane createTicketSaleAnchorPane;
    @FXML private Label agentNameTicketSale;
    @FXML private ChoiceBox<String> ticketsDropdown;
    @FXML private Label availableQuantity;
    @FXML private TextField noOfTickets;
    @FXML private Label choiceBoxLabelError;
    @FXML private Label quantityLabelError;
    
    // constructor
    public CreateTicketSaleController(CompanyController companyController)
    {
        this.companyController = companyController;
        setAgent();
        availableTickets = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateTicketSaleGUI.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CreateTicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setAgentName();
            getAllValidTickets();
            populateChoiceBox();
            choiceBoxChangeValueAction();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(CreateTicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // gets quantity of selected ticket
    // and sets quantity to value
    public void choiceBoxChangeValueAction()
    {
        ticketsDropdown.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> 
        {
            setSelectedTicket(newValue);
            availableQuantity.setText(Integer.toString(getSelectedTicket().getQuantity()));
        });
    }
    
    // set AnchorPane
    @FXML
    public void setCreateTicketSaleAnchorPane(AnchorPane createTicketSaleGUI)
    {
        createTicketSaleAnchorPane = createTicketSaleGUI;
    }
    
    // gets AnchorPane
    @FXML
    public AnchorPane getTicketSaleAnchorPane()
    {
        return createTicketSaleAnchorPane;
    }
    
    // action events
    @FXML
    public void cancelTicketSale(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void createTicketSale(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(validation())
        {
            int id = companyController.company.assignTicketSaleID();
            int agentID = this.agent.getId();
            String eventName = getSelectedEvent();
            String saleDate = LocalDate.now().toString();
            int quantity = Integer.parseInt(noOfTickets.getText());
            TicketSale ts = new TicketSale(id, agentID, eventName, saleDate, quantity);
            selectedTicket.reduceQuantity(quantity);
            companyController.addToTicketsSalesList(ts);
            companyController.updateTicketsList(selectedTicket);
            companyController.setTicketsList(companyController.company.getTicketsList());
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // validation function
    public boolean validation()
    {
        boolean validate = true;
        Validate v = new Validate();
        if(!v.isEventSelected(ticketsDropdown, choiceBoxLabelError))
        {
            validate = false;
        }
        if(!v.fieldNotEmpty(noOfTickets, quantityLabelError) || !v.isNumber(noOfTickets, quantityLabelError) || !v.isPositive(noOfTickets, quantityLabelError) || !v.ticketsAvailable(noOfTickets, quantityLabelError, availableQuantity, selectedTicket))
        {
            validate = false;
        }
        return validate;
    }
    
    // sets agent
    public void setAgent()
    {
        agent = companyController.getSelectedAgent();
    }
    
    // sets label to agent name
    public void setAgentName()
    {
        this.agentNameTicketSale.setText(agent.getName());
    }
    
    // get available for sale valid tickets
    public void getAllValidTickets() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<Ticket> allTickets = companyController.company.getTicketsList();
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // exlude tickets if its expiry date is before today
        for(Ticket t:allTickets)
        {
            String ticketExpiryDate = t.getExpiryDate();
            LocalDate localDate = LocalDate.parse(ticketExpiryDate, DATE_FORMAT);
            if(localDate.isAfter(LocalDate.now()))
            {
                availableTickets.add(t);
            }
        }
    }
    
    // populate ChoiceBox
    public void populateChoiceBox()
    {
        ArrayList<String> eventNames = new ArrayList<>();
        for(Ticket t:availableTickets)
        {
            eventNames.add(t.getEventName());
        }
        Collections.sort(eventNames);
        ticketsDropdown.getItems().addAll(eventNames);
    }
    
    // gets selected valueof ChoiceBox
    public String getSelectedEvent()
    {
        return ticketsDropdown.getSelectionModel().getSelectedItem();
    }
    
    // sets selectedTicket
    public void setSelectedTicket(String eventName)
    {
        for(Ticket t:availableTickets)
        {
            if(t.getEventName().equals(eventName))
            {
                this.selectedTicket = t;
            }
        }
    }
    
    // gets selectedTicket
    public Ticket getSelectedTicket()
    {
        return selectedTicket;
    }
    
}
