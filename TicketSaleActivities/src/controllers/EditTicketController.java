package controllers;

import models.Ticket;
import models.Validate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class EditTicketController implements Initializable {
    private CompanyController companyController;
    private Ticket ticket;
    private ArrayList<Ticket> ticketsList;
    @FXML AnchorPane editTicketAnchorPane;
    // taxt fields
    @FXML private TextField ticketID;
    @FXML private TextField eventNameEdit;
    @FXML private TextField eventPriceEdit;
    @FXML private TextField eventQuantityEdit;
    // date fields
    @FXML private DatePicker eventStartDateEdit;
    @FXML private DatePicker eventExpiryDateEdit;
    // labels
    @FXML private Label eventNameLabelErrorEdit;
    @FXML private Label eventPriceLabelErrorEdit;
    @FXML private Label eventStartDateLabelErrorEdit;
    @FXML private Label eventExpiryDateLabelErrorEdit;
    @FXML private Label eventQuantityLabelErrorEdit;
    
    // constructor
    public EditTicketController(CompanyController companyController, Ticket ticket)
    {
        this.companyController = companyController;
        this.ticket = ticket;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditTicketGUI.fxml"));
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "EditTicketGUI.fxml file not loaded properly");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ticketID.setText(Integer.toString(ticket.getId()));
        eventNameEdit.setText(ticket.getEventName());
        eventPriceEdit.setText(ticket.getPrice());
        eventStartDateEdit.setValue(LOCAL_DATE(ticket.getStartDate()));
        eventExpiryDateEdit.setValue(LOCAL_DATE(ticket.getExpiryDate()));
        eventQuantityEdit.setText(Integer.toString(ticket.getQuantity()));
    }
    
    // converts String to date
    private static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    
    // action events
    @FXML
    public void setEditTicketAnchorPane(AnchorPane EditTicketGUI)
    {
        editTicketAnchorPane = EditTicketGUI;
    }
    
    @FXML
    public AnchorPane getEditTicketAnchorPane()
    {
        return editTicketAnchorPane;
    }
    
    @FXML
    public void cancelEditingTicket(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void updateTicketAction(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(validation())
        {
            updateDetails();
            saveNewDetails();
            companyController.company.saveListOfTickets(ticketsList);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // validation function
    private boolean validation() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        Validate v = new Validate();
        v.reset(eventNameEdit, eventNameLabelErrorEdit);
        v.reset(eventPriceEdit, eventPriceLabelErrorEdit);
        v.resetDatefield(eventStartDateEdit, eventStartDateLabelErrorEdit);
        v.resetDatefield(eventExpiryDateEdit, eventExpiryDateLabelErrorEdit);
        v.reset(eventQuantityEdit, eventQuantityLabelErrorEdit);
        boolean validate = true;
        // check event name field
        if(!eventNameEdit.getText().equals(ticket.getEventName()))
        {
            if(!v.fieldNotEmpty(eventNameEdit, eventNameLabelErrorEdit) || !v.isString(eventNameEdit, eventNameLabelErrorEdit) || v.ticketsExist(eventNameEdit, eventNameLabelErrorEdit))
            {
                validate = false;
            }
        }
        if(!eventPriceEdit.getText().equals(ticket.getPrice()))
        {
             
            if(!v.fieldNotEmpty(eventPriceEdit, eventPriceLabelErrorEdit) || !v.hasOneComma(eventPriceEdit, eventPriceLabelErrorEdit)|| !v.isDouble(eventPriceEdit, eventPriceLabelErrorEdit) || !v.isPositivePrice(eventPriceEdit, eventPriceLabelErrorEdit) || !v.hasTwoDecimals(eventPriceEdit, eventPriceLabelErrorEdit))
            {
                validate = false;
            }
        }
        if(!eventStartDateEdit.getValue().toString().equals(ticket.getStartDate()))
        {
            if(!v.isDateFieldEmpty(eventStartDateEdit, eventStartDateLabelErrorEdit) || !v.isBeforeToday(eventStartDateEdit, eventStartDateLabelErrorEdit))
            {
                validate = false;
            }
        }
        if(!eventExpiryDateEdit.getValue().toString().equals(ticket.getExpiryDate()))
        {
            if(!v.isDateFieldEmpty(eventExpiryDateEdit, eventExpiryDateLabelErrorEdit) || !v.isBeforeToday(eventExpiryDateEdit, eventExpiryDateLabelErrorEdit))
            {
                validate = false;
            }
        }
        if(!eventQuantityEdit.getText().equals(Integer.toString(ticket.getQuantity())))
        {
            if(!v.fieldNotEmpty(eventQuantityEdit, eventQuantityLabelErrorEdit) || !v.isNumber(eventQuantityEdit, eventQuantityLabelErrorEdit) || !v.isPositive(eventQuantityEdit, eventQuantityLabelErrorEdit))
            {
                validate = false;
            }
        }
        return validate;
    }
    
    // update details
    private void updateDetails() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ticketsList = companyController.company.getTicketsList();
        int id = ticket.getId();
        for(Ticket t:ticketsList)
        {
            if(t.getId() == id)
            {
                if(!eventNameEdit.getText().equals(t.getEventName()))
                {
                    t.setEventName(eventNameEdit.getText());
                }
                if(!eventPriceEdit.getText().equals(t.getPrice()))
                {
                    t.setPrice(priceFormat(eventPriceEdit.getText()));
                }
                if(!eventStartDateEdit.getValue().toString().equals(t.getStartDate()))
                {
                    t.setStartDate(eventStartDateEdit.getValue().toString());
                }
                if(!eventExpiryDateEdit.getValue().toString().equals(t.getExpiryDate()))
                {
                    t.setExpiryDate(eventExpiryDateEdit.getValue().toString());
                }
                if(!eventQuantityEdit.getText().equals(t.getQuantity()))
                {
                    t.setQuantity(Integer.parseInt(eventQuantityEdit.getText()));
                }
                ticket = t;
                break;
            }
        }
    }
    
    // save details
    private void saveNewDetails()
    {
        companyController.setTicketsList(ticketsList);
    }
    
    // makes sure comma is replaced by dot
    private String priceFormat(String price)
    {
        String newPrice = "";
        if(price.contains(","))
        {
            newPrice = price.replace(",", ".");
        }
        return newPrice;
    }  
}
