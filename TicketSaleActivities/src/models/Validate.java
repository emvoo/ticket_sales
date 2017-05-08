package models;

import controllers.CompanyController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Marcin Wisniewski
 */
public class Validate {
    private static final String RED_FIELD = "-fx-background-color: rgba(255, 0, 0, .2); -fx-border-color: rgb(255,0,0);";
    private static final String FIELD_EMPTY = "This field is required.";
    private static final String NO_NUMBERS = "This field cannot contain digits.";
    private static final String NOT_NUMBERS = "This field can contain digits only.";
    private static final String PHONE_LENGTH = "Phone number must be 8-12 characters long.";
    private static final String EXISTS = "Details provided already exist in the system.";
    private static final String NEGATIVE = "Price cannot have negative value.";
    private static final String FORMAT = "Price can contain only one .(dot) or ,(comma).";
    private static final String DECIMALS = "Price has to contain 2 decimal places";
    private static final String BEFORE_TODAY = "Start date cannot be set before today.";
    private static final String BEFORE_START_DATE = "Sale cannot end before it begins.";
    private static final String NEGATIVE_QTY = "Number of available tickets must be grater than 0.";
    private static final String TOO_MANY = "Number of tickets left: ";
    
// checks if field is not empty
    public boolean fieldNotEmpty(TextField field, Label errorLabel)
    {
        if(field.getText().length() > 0)
        {
            reset(field, errorLabel);
            return true;
        }
        else
        {
            setError(field, errorLabel, FIELD_EMPTY);
            return false;
        }
    }
    
    // checks if characters entered are a type of String (do not contain digits))
    public boolean isString(TextField field, Label errorLabel)
    {
        if(field.getText().matches(".*\\d.*"))
        {
            setError(field, errorLabel, NO_NUMBERS);
            return false;
        }
        else
        {
            reset(field, errorLabel);
            return true;
        }
    }
    
    // checks if there are digits only 
    public boolean isPhoneNumber(TextField field, Label errorLabel)
    {
        try
        {
            reset(field, errorLabel);
            String phoneNumberStr = field.getText();
            String value = phoneNumberStr.replaceAll("[^0-9]","");
            Long.parseLong(value);
            return true;
        }
        catch(NumberFormatException e)
        {
            setError(field,errorLabel, NOT_NUMBERS);
            return false;
        }
    }
    
    // checks length of phone number provided
    // 8-12 characters allowed
    public boolean phoneLength(TextField field, Label label)
    {
        if((field.getText().length() < 8) || (field.getText().length() > 12))
        {
            setError(field, label, PHONE_LENGTH);
            return false;
        }
        reset(field, label);
        return true;
    }
    
    // checks if agent name/phone number exists in the system already
    public boolean agentsExists(TextField field, Label label) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        reset(field, label);
        CompanyController companyController = new CompanyController();
        ArrayList<Agent> existingAgents = companyController.company.getAgentsList();
        Iterator it = existingAgents.iterator();
        while(it.hasNext())
        {
            Agent a = (Agent) it.next();
            if(a.getName().toLowerCase().equals(field.getText().toLowerCase()))
            {
                setError(field, label, EXISTS);
                return true;
            }
            if(a.getPhone().equals(field.getText()))
            {
                setError(field, label, EXISTS);
                return true;
            }
        }
        return false;
    }
    
    // checks if ticket exists in the system
    public boolean ticketsExist(TextField field, Label label) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        reset(field, label);
        CompanyController companyController = new CompanyController();
        ArrayList<Ticket> existingTickets = companyController.company.getTicketsList();
        Iterator it = existingTickets.iterator();
        while(it.hasNext())
        {
            Ticket t = (Ticket) it.next();
            if(t.getEventName().toLowerCase().equals(field.getText().toLowerCase()))
            {
                setError(field, label, EXISTS);
                return true;
            }
        }
        
        return false;
    }
    
    // checks if number is double
    public boolean isDouble(TextField field, Label label)
    {
        try
        {
            reset(field, label);
            String string = removeComma(field.getText());
            if(string.contains(","))
            {
                string = string.replace(",", "");
            }
            Double.parseDouble(string);
            return true;
        }
        catch(NumberFormatException e)
        {
            setError(field, label, NOT_NUMBERS);
            return false;
        }
    }
    
    // checks if passed value is positive
    public boolean isPositivePrice(TextField field, Label label)
    {
        try
        {
            reset(field, label);
            String string = removeComma(field.getText());
            Double price = Double.parseDouble(string);
            if(price >= 0)
            {
                return true;
            }
        }
        catch(NumberFormatException e)
        {
            setError(field,label, NEGATIVE);
            return false;
        }
        setError(field,label, NEGATIVE);
        return false;
    }
    
    // replaces comma with a dot
    private String removeComma(String string)
    {
        if(string.contains(","))
        {
            string = string.replace(",", "");
        }
        return string;
    }
    
    // checks if user entered one comma only
    public boolean hasOneComma(TextField field, Label label)
    {
        int times = 0;
        String string = field.getText();
        for(int i=0; i<string.length(); i++)
        {
            String c = string.substring(i, i+1);
            if(c.equals(".") || c.equals(","))
            {
                times++;
            }
        }
        if(times > 1)
        {
            setError(field, label, FORMAT);
            return false;
        }
        reset(field, label);
        return true;
    }
    
    // checks if there are 2 decimals 
    public boolean hasTwoDecimals(TextField field, Label label)
    {
        String string = field.getText();
        string = string.replace(".", ",");
        if(string.contains(","))
        {
            String[] splitter = string.split(",");
            if(splitter.length > 1)
            {
                if(splitter[1].length() != 2)
                {
                    setError(field, label, DECIMALS);
                    return false;
                }
            }
            else
            {
                setError(field, label, DECIMALS);
                return false;
            }
        }
        else
        {
            setError(field, label, DECIMALS);
            return false;
        }
        reset(field, label);
        return true;
    }
    
    // checks if date entered
    public boolean isDateFieldEmpty(DatePicker field, Label label)
    {
        
        if(field.getValue() == null)
        {
            setDateFieldError(field, label, FIELD_EMPTY);
            return false;
        }
        resetDatefield(field, label);
        return true;
    }
    
    // checks if selected date is before "today"
    public boolean isBeforeToday(DatePicker field, Label label)
    {
        if(field.getValue().isBefore(LocalDate.now()))
        {
            setDateFieldError(field, label, BEFORE_TODAY);
            return false;
        }
        resetDatefield(field, label);
        return true;
    }
    
    // checks if date is before start date
    public boolean isBeforeStartDate(DatePicker fieldStartDate,DatePicker fieldExpiryDate, Label label)
    {
        if(fieldExpiryDate.getValue().isBefore(fieldStartDate.getValue()))
        {
            setDateFieldError(fieldExpiryDate, label, BEFORE_START_DATE);
            return false;
        }
        resetDatefield(fieldExpiryDate, label);
        return true;
    }
    
    // checks if value is a number
    public boolean isNumber(TextField field, Label label)
    {
        try
        {
            reset(field, label);
            Integer.parseInt(field.getText());
            return true;
        }
        catch(NumberFormatException e)
        {
            setError(field, label, NOT_NUMBERS);
            return false;
        }
    }
    
    // cheks if number is positive
    public boolean isPositive(TextField field, Label label)
    {
        int quantity = Integer.parseInt(field.getText());
        if(quantity <= 0)
        {
            setError(field, label, NEGATIVE_QTY);
            return false;
        }
        reset(field, label);
        return true;
    }
    
    // set default field/label values
    public void reset(TextField field, Label label)
    {
        field.setStyle(null);
        label.setText("");
    }
    
    // reset date field error
    public void resetDatefield(DatePicker field, Label label)
    {
        field.setStyle(null);
        label.setText("");
    }
    
    // sets error on date fields
    private void setDateFieldError(DatePicker field, Label label, String message)
    {
        field.setStyle(RED_FIELD);
        label.setText(message);
    }
    
    // removes ChoiceBox errors
    private void resetChoiceBox(ChoiceBox box, Label label)
    {
        box.setStyle(null);
        label.setText("");
    }
    
    // sets errors on ChoiceBox
    private void setChoiceBoxError(ChoiceBox box, Label label, String message)
    {
        box.setStyle(RED_FIELD);
        label.setText(message);
    }
    
    // set field red and label message
    private void setError(TextField field, Label label, String message)
    {
        field.setStyle(RED_FIELD);
        label.setText(message);
    }
    
    // checks if ChoiceBox value selected
    public boolean isEventSelected(ChoiceBox box, Label label)
    {
        if(box.getSelectionModel().getSelectedItem() == null)
        {
            setChoiceBoxError(box, label, FIELD_EMPTY);
            return false;
        }
        resetChoiceBox(box, label);
        return true;
    }
    
    // checks if requested number of tickets is available
    public boolean ticketsAvailable(TextField field, Label label, Label availableQuantityLabel, Ticket ticket)
    {
        int availableTickets = Integer.parseInt(field.getText());
        if(availableTickets > 0)
        {
            int difference = ticket.getQuantity() - availableTickets;
            if(difference < 0)
            {
                availableQuantityLabel.setText(Integer.toString(ticket.getQuantity()));
                setError(field, label, TOO_MANY + ticket.getQuantity());
                return false;
            }            
        } 
        reset(field, label);
        return true;
    }
}
