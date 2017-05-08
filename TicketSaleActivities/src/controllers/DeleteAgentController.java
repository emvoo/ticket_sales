package controllers;

import models.Agent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class DeleteAgentController {
    private CompanyController companyController;
    private Agent agent;
    private ArrayList<Agent> agentsList;
    @FXML private AnchorPane deleteAgentAnchorPane;
    
    // constructor
    public DeleteAgentController(CompanyController companyController, Agent agent)
    {
        this.agent = agent;
        this.companyController = companyController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DeleteAgentGUI.fxml"));
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage() + "DeleteAgentGUI.fxml file not loaded properly.");
        }
    }
    
    // action events
    @FXML
    public void setDeleteAgentAnchorPane(AnchorPane deleteAgentGUI)
    {
        deleteAgentAnchorPane = deleteAgentGUI;
    }
    
    @FXML
    public AnchorPane getDeleteAgentAnchorPane()
    {
        return deleteAgentAnchorPane;
    }
    
    @FXML
    public void cancelAgentDeletion(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void confirmAgentDeletion(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        agentsList = companyController.company.getAgentsList();
        Iterator it = agentsList.iterator();
        while(it.hasNext())
        {
            Agent a = (Agent) it.next();
            if(a.getId() == agent.getId())
            {
                it.remove();
            }
        }
        companyController.setAgentsList(agentsList);
        companyController.company.saveListOfAgents(agentsList);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
