package controllers;

import models.Agent;
import models.Validate;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Marcin Wisniewski
 */
public class AddAgentController {
    private CompanyController companyController;
    @FXML private AnchorPane addAgentAnchorPane;
    @FXML private CheckBox activeAgentCheckbox;
    // text fields
    @FXML private TextField agentName;
    @FXML private TextField agentLocation;
    @FXML private TextField agentPhoneNo;
    // labels
    @FXML private Label nameErrorLabel;
    @FXML private Label locationErrorLabel;
    @FXML private Label phoneErrorLabel;
    
    
    public AddAgentController(CompanyController companyController)
    {
        this.companyController = companyController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddAgentGUI.fxml"));
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch(IOException e)
        {
            System.out.println("AddAgentGUI.fxml file not loaded properly");
        }
    }
    
    // set AnchorPane
    @FXML public void setAddAgentAnchorPane(AnchorPane addAgentGUI)
    {
        addAgentAnchorPane = addAgentGUI;
    }
    
    // get AnchorPane
    @FXML public AnchorPane getAddAgentAnchorPane()
    {
        return addAgentAnchorPane;
    }
    
    // cancel action and close the window
    @FXML
    public void cancelAddingAgentAction(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    // confirm adding Agent and close when done
    @FXML
    public void addAgentAction(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        // validate entered data
        if(validation())
        {
            // allocate id to new agent
            int id = companyController.company.assignAgentID();
            // set data required for agent creation
            String name = agentName.getText();
            String location = agentLocation.getText();
            String phone = agentPhoneNo.getText().replaceAll("[^0-9]", "");
            boolean active = activeAgentCheckbox.isSelected();
            // create agent
            Agent newAgent = new Agent(id, name, location, phone, active);
            // add agent to the system
            companyController.addToAgentsList(newAgent);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    // validate entered details
    private boolean validation() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        Validate v = new Validate();
        boolean validate = true;
        // validate name
        if(!v.fieldNotEmpty(agentName, nameErrorLabel) || !v.isString(agentName,nameErrorLabel) || v.agentsExists(agentName, nameErrorLabel))
        {
            validate = false;
        }
        // validate location
        if(!v.fieldNotEmpty(agentLocation, locationErrorLabel) || !v.isString(agentLocation, locationErrorLabel))
        {
            validate = false;
        }
        // validate phone number
        if(!v.fieldNotEmpty(agentPhoneNo, phoneErrorLabel) || !v.isPhoneNumber(agentPhoneNo, phoneErrorLabel) || !v.phoneLength(agentPhoneNo, phoneErrorLabel) || v.agentsExists(agentPhoneNo, phoneErrorLabel))
        {
            validate = false;
        }
        return validate;
    }
}
