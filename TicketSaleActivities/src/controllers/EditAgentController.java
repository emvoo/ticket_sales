package controllers;

import models.Agent;
import models.Validate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class EditAgentController implements Initializable {
    private CompanyController companyController;
    private Agent agent;
    private ArrayList<Agent> agentsList;
    @FXML AnchorPane editAgentAnchorPane;
    @FXML private CheckBox activeAgentCheckbox;
    // text fields
    @FXML private TextField agentID;
    @FXML private TextField agentName;
    @FXML private TextField agentLocation;
    @FXML private TextField agentPhoneNo;
    // labels
    @FXML private Label nameErrorLabel;
    @FXML private Label locationErrorLabel;
    @FXML private Label phoneErrorLabel;
    
    // constructor
    public EditAgentController(CompanyController companyController, Agent agent)
    {
        this.companyController = companyController;
        this.agent = agent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditAgentGUI.fxml"));
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage() + "EditAgentGUI.fxml file not loaded properly");
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        agentID.setText(Integer.toString(agent.getId()));
        agentName.setText(agent.getName());
        agentLocation.setText(agent.getLocation());
        agentPhoneNo.setText(agent.getPhone());
        activeAgentCheckbox.setSelected(agent.isActive());
    }
    
    // action events
    @FXML
    public void setEditAgentAnchorPane(AnchorPane editAgentGUI)
    {
        editAgentAnchorPane = editAgentGUI;
    }
    
    @FXML
    public AnchorPane getEditAgentAnchorPane()
    {
        return editAgentAnchorPane;
    }
    
    @FXML
    public void cancelEditingAgentAction(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void editAgentAction(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(validation())
        {
            updateDetails();
            saveNewDetails();
            companyController.company.saveListOfAgents(agentsList);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
    
    // validation function
    private boolean validation() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        Validate v = new Validate();
        boolean validate = true;
        // check name field
        if(!agentName.getText().equals(agent.getName()))
        {
            if(!v.fieldNotEmpty(agentName, nameErrorLabel) || !v.isString(agentName, nameErrorLabel) || v.agentsExists(agentName, nameErrorLabel))
            {
                validate = false;
            }
        }
        // check location field
        if(!agentLocation.getText().equals(agent.getLocation()))
        {
            if(!v.fieldNotEmpty(agentLocation, locationErrorLabel) || !v.isString(agentLocation, locationErrorLabel))
            {
                validate = false;
            }
        }
        // check phone field
        if(!agentPhoneNo.getText().equals(agent.getPhone()))
        {
            if(!v.fieldNotEmpty(agentPhoneNo, phoneErrorLabel) || !v.isPhoneNumber(agentPhoneNo, phoneErrorLabel) || !v.phoneLength(agentPhoneNo, phoneErrorLabel) || v.agentsExists(agentPhoneNo, phoneErrorLabel))
            {
                validate = false;
            }
        }
        return validate;
    }
    
    // update details
    private void updateDetails() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        agentsList = companyController.company.getAgentsList();
        int id = agent.getId();
        for(Agent a:agentsList)
        {
            if(a.getId() == id)
            {
                if(!agentName.getText().equals(a.getName()))
                {
                    a.setName(agentName.getText());
                }
                if(!agentLocation.getText().equals(a.getLocation()))
                {
                    a.setLocation(agentLocation.getText());
                }
                if(!agentPhoneNo.getText().equals(a.getPhone()))
                {
                    a.setPhone(agentPhoneNo.getText().replaceAll("[^0-9]", ""));
                }
                if(activeAgentCheckbox.isSelected() != a.isActive())
                {
                    a.setActive(activeAgentCheckbox.isSelected());
                }
                agent = a;
            }
        }
    }
    
    // saves new details
    private void saveNewDetails()
    {
        companyController.setAgentsList(agentsList);
    }
}
