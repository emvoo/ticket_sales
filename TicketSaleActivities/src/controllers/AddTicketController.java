package controllers;

import models.Ticket;
import models.Validate;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class AddTicketController {
    private CompanyController companyController;
    @FXML
    private AnchorPane addTicketAnchorPane;
    
    // taxt fields
    @FXML private TextField eventName;
    @FXML private TextField eventPrice;
    @FXML private TextField eventQuantity;
    // date fields
    @FXML private DatePicker eventStartDate;
    @FXML private DatePicker eventExpiryDate;
    // labels
    @FXML private Label eventNameLabelError;
    @FXML private Label eventPriceLabelError;
    @FXML private Label eventStartDateLabelError;
    @FXML private Label eventExpiryDateLabelError;
    @FXML private Label eventQuantityLabelError;
    
    public AddTicketController(CompanyController companyController)
    {
        this.companyController = companyController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddTicketGUI.fxml"));
        loader.setController(this);
        try
        {
            loader.load();
        } catch (Exception e)
        {
            System.out.println(e.getMessage() + "AddTicketGUI.fxml file not loaded properly");
        }
    }
    
    // set AnchorPane
    @FXML
    public void setAddTicketAnchorPane(AnchorPane addTicketGUI)
    {
        addTicketAnchorPane = addTicketGUI;
    }
    
    // get Anchor Pane
    @FXML
    public AnchorPane getAddTicketAnchorPane()
    {
        return addTicketAnchorPane;
    }
    
    // cancel action and return to main screen
    @FXML
    public void cancelCreatingTicket(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    // confirm action, create ticket and return to main screen
    @FXML
    public void addTicketAction(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        // validate entered data
        if(validation())
        {
            // allocate ID
            int id = companyController.company.assignTicketID();
            // set data for ticket creatin
            String name = eventName.getText();
            String price = eventPrice.getText();
            price = priceFormat(price);
            String startDate = eventStartDate.getValue().toString();
            String expiryDate = eventExpiryDate.getValue().toString();
            int quantity = Integer.parseInt(eventQuantity.getText());
            // create Ticket
            Ticket ticket = new Ticket(id, name, price, startDate, expiryDate, quantity);
            // add created ticket to the system
            companyController.addToTicketsList(ticket);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // validate entered details
    private boolean validation() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        Validate v = new Validate();
        boolean validate = true;
        // validate event name
        if(!v.fieldNotEmpty(eventName, eventNameLabelError) || !v.isString(eventName, eventNameLabelError) || v.ticketsExist(eventName, eventNameLabelError))
        {
            validate = false;
        }
        // validate price
        if(!v.fieldNotEmpty(eventPrice, eventPriceLabelError) || !v.hasOneComma(eventPrice, eventPriceLabelError) || !v.isDouble(eventPrice, eventPriceLabelError) || !v.isPositivePrice(eventPrice, eventPriceLabelError) || !v.hasTwoDecimals(eventPrice, eventPriceLabelError))
        {
            validate = false;
        }
        // validate eventStartDate
        if(!v.isDateFieldEmpty(eventStartDate, eventStartDateLabelError) || !v.isBeforeToday(eventStartDate, eventStartDateLabelError))
        {
            validate = false;
        }
        // validate eventExpiryDate
        if(!v.isDateFieldEmpty(eventExpiryDate, eventExpiryDateLabelError) || !v.isBeforeStartDate(eventStartDate, eventExpiryDate, eventExpiryDateLabelError))
        {
            validate = false;
        }
        // validate quantity
        if(!v.fieldNotEmpty(eventQuantity, eventQuantityLabelError) || !v.isNumber(eventQuantity, eventQuantityLabelError) || !v.isPositive(eventQuantity, eventQuantityLabelError))
        {
            validate = false;
        }
        return validate;
    }
    
    // replace comma with a dot
    public String priceFormat(String price)
    {
        String newPrice = "";
        if(price.contains(","))
        {
            newPrice = price.replace(",", ".");
        }
        else
        {
            newPrice = price;
        }
        return newPrice;
    }  
}
