package controllers;

import models.Agent;
import models.Company;
import models.Ticket;
import models.TicketSale;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Marcin Wisniewski
 */
public class CompanyController implements Initializable
{
    public Company company = new Company();
    // Agents section
    ObservableList<Agent> agentsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Agent> agentsList;
    @FXML private TableColumn<Agent, Integer> agentsListId;
    @FXML private TableColumn<Agent, String> agentsListName;
    @FXML private TableColumn<Agent, String> agentsListLocation;
    @FXML private TableColumn<Agent, String> agentsListPhone;
    @FXML private Label noAgentSelectedEdit;
    @FXML private Label noAgentSelectedDelete;
    @FXML private AnchorPane agentSalesAnchorPane;
    
    // tickets section
    ObservableList<Ticket> ticketsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Ticket> ticketsList;
    @FXML private TableColumn<Ticket, Integer> ticketsListId;
    @FXML private TableColumn<Ticket, String> ticketsListName;
    @FXML private TableColumn<Ticket, String> ticketsListPrice;
    @FXML private TableColumn<Ticket, String> ticketsListSaleDate;
    @FXML private TableColumn<Ticket, String> ticketsListExpiryDate;
    @FXML private TableColumn<Ticket, Integer> ticketsListQuantity;
    @FXML private Label noTicketSelectedEdit;
    @FXML private Label noTicketSelectedDelete;
    @FXML private Label noTicketSelectedDisplaySales;
    
    // tickets sales section
    ObservableList<TicketSale> ticketSalesObservableList = FXCollections.observableArrayList();
    @FXML public TableView<TicketSale> ticketsSalesList;
    @FXML private TableColumn<TicketSale, Integer> ticketSalesID;
    @FXML private TableColumn<TicketSale, String> ticketSaleEventName;
    @FXML private TableColumn<TicketSale, String> ticketSaleDate;
    @FXML private TableColumn<TicketSale, Integer> ticketQuantitySold;
    @FXML private Label reverseTicketSaleLabelError;
    @FXML private Button createTicketSale;
    @FXML private Button reverseTcketSale;
    
    private static final String NO_SELECTION_AGENT = "Please select an agent.";
    private static final String NO_SELECTION_TICKET = "Please select a ticket.";
    private static final String NO_SELECTION_TICKET_SALE = "Please select ticket sale.";
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // match columns with agent object attributes
        agentsListId.setCellValueFactory(new PropertyValueFactory<>("id"));
        agentsListName.setCellValueFactory(new PropertyValueFactory<>("name"));
        agentsListLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        agentsListPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        try {
            // add agents from file to the table
            agentsList.getItems().addAll(getAgents());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // match columns with tickets object attributes
        ticketsListId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketsListName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        ticketsListPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ticketsListSaleDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        ticketsListExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        ticketsListQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        agentSalesAnchorPane.setVisible(false);
        
        try {
            // add tickets from file to the table
            ticketsList.getItems().addAll(getTickets());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // set action to display ticketSales on agent clicked
        agentsList.setRowFactory(e-> showAgentSalesSection());
        
        // match columns with ticketSales object attributes
        ticketSalesID.setCellValueFactory(new PropertyValueFactory<>("saleID"));
        ticketSaleEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        ticketSaleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        ticketQuantitySold.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
    
    
    // AGENTS TAB
    
    // get agents
    public TableView<Agent> getAgentsList()
    {
        return agentsList;
    }
    
    // populate observeableList
    public ObservableList<Agent> getAgents() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<Agent> agentsAL = company.getAgentsList();
        agentsObservableList.addAll(agentsAL);
        return agentsObservableList;
    }
    
    // add agents to observeableList and save in file
    public void addToAgentsList(Agent agent) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(agent == null)
        {
            System.out.println("Agent is null");
            System.exit(-1);
        }
        agentsList.getItems().add(agent);
        agentsList.refresh();
        
        //save list of agents to file
        agentsObservableList.add(agent);
        ArrayList<Agent> newList = new ArrayList<>();
        for(Agent tmpAgent:agentsObservableList)
        {
            newList.add(tmpAgent);
        }
        
        company.saveListOfAgents(newList);
    }
    
    // populate agents table view
    public void setAgentsList(ArrayList<Agent> agents)
    {
        ObservableList<Agent> tmpList = FXCollections.observableArrayList();
        for(Agent a:agents)
        {
            tmpList.add(a);
        }
        agentsList.setItems(tmpList);
        agentsList.refresh();
    }
    
    // get selected agent
    public Agent getSelectedAgent()
    {
        return agentsList.getSelectionModel().getSelectedItem();
    }
    
    // event actions
    @FXML
    private void addAgentAction(ActionEvent event) {
        AddAgentController addAgentController = new AddAgentController(this);
        AnchorPane addAgentAnchorPane = addAgentController.getAddAgentAnchorPane();
        StackPane addAgentGUIstackPane = new StackPane();
        addAgentGUIstackPane.getChildren().add(addAgentAnchorPane);        
        Scene addAgentGUIscene = new Scene(addAgentGUIstackPane);
        Stage addAgentGUIstage = new Stage();
        addAgentGUIstage.setScene(addAgentGUIscene);
        addAgentGUIstage.initModality(Modality.APPLICATION_MODAL);
        addAgentGUIstage.show();
    }
    
    @FXML
    private void editAgentAction(ActionEvent event)
    {
        clearErrors();
        Agent selected = agentsList.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            noAgentSelectedEdit.setText(NO_SELECTION_AGENT);
        }
        else
        {
            EditAgentController editAgentController = new EditAgentController(this, selected);
            AnchorPane editAgentAnchorPane = editAgentController.getEditAgentAnchorPane();
            StackPane editAgentGUIstackPane = new StackPane();
            editAgentGUIstackPane.getChildren().add(editAgentAnchorPane);
            Scene editAgentGUIscene = new Scene(editAgentGUIstackPane);
            Stage editAgentGUIstage = new Stage();
            editAgentGUIstage.setScene(editAgentGUIscene);
            editAgentGUIstage.initModality(Modality.APPLICATION_MODAL);
            editAgentGUIstage.show();
        }
    }
    
    @FXML
    private void deleteAgentAction(ActionEvent event)
    {
        clearErrors();
        Agent selected = agentsList.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            noAgentSelectedDelete.setText(NO_SELECTION_AGENT);
        }
        else
        {
            DeleteAgentController deleteAgentController = new DeleteAgentController(this, selected);
            AnchorPane deleteAgentAnchorPane = deleteAgentController.getDeleteAgentAnchorPane();
            StackPane deleteAgentGUIstackPane = new StackPane();
            deleteAgentGUIstackPane.getChildren().add(deleteAgentAnchorPane);
            Scene deleteAgentGUIscene = new Scene(deleteAgentGUIstackPane);
            Stage deleteAgentGUIstage = new Stage();
            deleteAgentGUIstage.setScene(deleteAgentGUIscene);
            deleteAgentGUIstage.initModality(Modality.APPLICATION_MODAL);
            deleteAgentGUIstage.show();
        }
    }
    
    // helper function to clear error labels
    private void clearErrors()
    {
        noAgentSelectedEdit.setText("");
        noAgentSelectedDelete.setText("");
        noTicketSelectedEdit.setText("");
        noTicketSelectedDelete.setText("");
        reverseTicketSaleLabelError.setText("");
        noTicketSelectedDisplaySales.setText("");
    }
    
    
    // TICKETS TAB
    
    // get tableView of tickets
    public TableView<Ticket> getTicketsList()
    {
        return ticketsList;
    }
    
    // get observeableList of Tickets
    public ObservableList<Ticket> getTickets() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<Ticket> ticketsAL = company.getTicketsList();
        ticketsObservableList.addAll(ticketsAL);
        return ticketsObservableList;
    }
    
    // action events
    @FXML
    private void createTicketAction(ActionEvent event)
    {
        AddTicketController addTicketController = new AddTicketController(this);
        AnchorPane addTicketAnchorPane = addTicketController.getAddTicketAnchorPane();
        StackPane addTicketGUIstackPane = new StackPane();
        addTicketGUIstackPane.getChildren().add(addTicketAnchorPane);
        Scene addTicketGUIscene = new Scene(addTicketGUIstackPane);
        Stage addAgentGUIstage = new Stage();
        addAgentGUIstage.setScene(addTicketGUIscene);
        addAgentGUIstage.initModality(Modality.APPLICATION_MODAL);
        addAgentGUIstage.show();
    }
    
    @FXML
    private void modifyTicketAction(ActionEvent event)
    {
        clearErrors();
        Ticket selected = ticketsList.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            noTicketSelectedEdit.setText(NO_SELECTION_TICKET);
        }
        else
        {
            EditTicketController editTicketController = new EditTicketController(this, selected);
            AnchorPane editTicketAnchorPane = editTicketController.getEditTicketAnchorPane();
            StackPane editTicketGUIstackPane = new StackPane();
            editTicketGUIstackPane.getChildren().add(editTicketAnchorPane);
            Scene editTicketGUIscene = new Scene(editTicketGUIstackPane);
            Stage editTicketGUIstage = new Stage();
            editTicketGUIstage.setScene(editTicketGUIscene);
            editTicketGUIstage.initModality(Modality.APPLICATION_MODAL);
            editTicketGUIstage.show();
        }
    }
    
    @FXML
    private void deleteSelectedTicketAction(ActionEvent event)
    {
        clearErrors();
        Ticket selected = ticketsList.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            noTicketSelectedDelete.setText(NO_SELECTION_TICKET);
        }
        else
        {
            DeleteTicketController deleteTicketController = new DeleteTicketController(this, selected);
            AnchorPane deleteTicketAnchorPane = deleteTicketController.getDeleteTicketAnchorPane();
            StackPane deleteTicketGUIstackPane = new StackPane();
            deleteTicketGUIstackPane.getChildren().add(deleteTicketAnchorPane);
            Scene deleteTicketGUIscene = new Scene(deleteTicketGUIstackPane);
            Stage deleteTicketGUIstage = new Stage();
            deleteTicketGUIstage.setScene(deleteTicketGUIscene);
            deleteTicketGUIstage.initModality(Modality.APPLICATION_MODAL);
            deleteTicketGUIstage.show();
        }
    }
    
    @FXML
    public void displayTicketSales(ActionEvent event)
    {
        clearErrors();
        Ticket selected = ticketsList.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            noTicketSelectedDisplaySales.setText(NO_SELECTION_TICKET);
        }
        else
        {
            DisplayTicketSalesController displayTicketSalesController = new DisplayTicketSalesController(this, selected);
            AnchorPane displayTicketSalesAnchorPane = displayTicketSalesController.getDisplayTicketSalesAnchorPane();
            StackPane displayTicketSalesGUIstackPane = new StackPane();
            displayTicketSalesGUIstackPane.getChildren().add(displayTicketSalesAnchorPane);
            Scene displayTicketSalesGUIscene = new Scene(displayTicketSalesGUIstackPane);
            Stage displayTicketSalesGUIstage = new Stage();
            displayTicketSalesGUIstage.setScene(displayTicketSalesGUIscene);
            displayTicketSalesGUIstage.initModality(Modality.APPLICATION_MODAL);
            displayTicketSalesGUIstage.show();
        }
    }
    
    // add to ticketsList
    public void addToTicketsList(Ticket ticket) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(ticket == null)
        {
            System.out.println("Ticket is null");
            System.exit(-1);
        }
        ticketsList.getItems().add(ticket);
        ticketsList.refresh();
        
        //save list of agents to file
        ticketsObservableList.add(ticket);
        ArrayList<Ticket> newList = new ArrayList<>();
        for(Ticket tmpTicket:ticketsObservableList)
        {
            newList.add(tmpTicket);
        }
        company.saveListOfTickets(newList);
    }
    
    // update TicketsList
    public void updateTicketsList(Ticket ticket) throws IOException
    {
        if(ticket == null)
        {
            System.out.println("Ticket is null");
            System.exit(-1);
        }
        ArrayList<Ticket> tmp = new ArrayList<>();
        for(Ticket t:ticketsObservableList)
        {
            if(t.getId() == ticket.getId())
            {
                t.setQuantity(ticket.getQuantity());
            }
            tmp.add(t);
        }
        company.saveListOfTickets(tmp);
        
    }
    
    // populates TableView of TicketsSales
    public void setTicketsList(ArrayList<Ticket> tickets)
    {
        ObservableList<Ticket> tmpList = FXCollections.observableArrayList();
        for(Ticket t:tickets)
        {
            tmpList.add(t);
        }
        ticketsList.setItems(tmpList);
    }
    
//    public void 

    
    
    // TICKETS SALES SECTION
    
    // action events
    @FXML
    public void createTicketSaleAction(ActionEvent event)
    {
        CreateTicketSaleController createTicketSaleController = new CreateTicketSaleController(this);
        AnchorPane createTicketSaleAnchorPane = createTicketSaleController.getTicketSaleAnchorPane();
        StackPane createTicketSaleGUIstackPane = new StackPane();
        createTicketSaleGUIstackPane.getChildren().add(createTicketSaleAnchorPane);        
        Scene createTicketSaleGUIscene = new Scene(createTicketSaleGUIstackPane);
        Stage createTicketSaleGUIstage = new Stage();
        createTicketSaleGUIstage.setScene(createTicketSaleGUIscene);
        createTicketSaleGUIstage.initModality(Modality.APPLICATION_MODAL);
        createTicketSaleGUIstage.show();
    }
    
    @FXML
    public void reverseTicketSaleAction(ActionEvent event)
    {
        clearErrors();
        TicketSale selected = ticketsSalesList.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            reverseTicketSaleLabelError.setText(NO_SELECTION_TICKET_SALE);
        }
        else
        {
            ReverseTicketSaleController reverseTicketSaleController = new ReverseTicketSaleController(this, selected);
            AnchorPane reverseTicketSaleAnchorPane = reverseTicketSaleController.getRevertTicketSaleAnchorPane();
            StackPane revertTicketSaleGUIstackPane = new StackPane();
            revertTicketSaleGUIstackPane.getChildren().add(reverseTicketSaleAnchorPane);
            Scene revertTicketSaleGUIscene = new Scene(revertTicketSaleGUIstackPane);
            Stage revertTicketSaleGUIstage = new Stage();
            revertTicketSaleGUIstage.setScene(revertTicketSaleGUIscene);
            revertTicketSaleGUIstage.initModality(Modality.APPLICATION_MODAL);
            revertTicketSaleGUIstage.show();
        }
    }
    
    // shows sales based on agent
    public TableRow<Agent> showAgentSalesSection()
    {
        TableRow<Agent> row = new TableRow<>();        
        row.setOnMouseClicked(event -> 
            {
                if(!row.isEmpty())
                {
                    clearTicketsSalesList();
                    agentSalesAnchorPane.setVisible(true);
                    Agent selected = agentsList.getSelectionModel().getSelectedItem();
                    try {
                        if(!selected.isActive())
                        {
                            reverseTcketSale.setDisable(true);
                            createTicketSale.setDisable(true);
                        }
                        else
                        {
                            reverseTcketSale.setDisable(false);
                            createTicketSale.setDisable(false);
                        }
                        ticketsSalesList.getItems().addAll(getTicketsSales(selected));
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    agentSalesAnchorPane.setVisible(false);
                }
            }
        );
        return row;
    }
    
    // clears TableView of ticketSale
    private void clearTicketsSalesList()
    {
        ticketsSalesList.getItems().clear();
        ticketSalesObservableList.clear();
    }
    
    // returns ObserveableList of TicketSale
    public ObservableList<TicketSale> getTicketsSales(Agent agent) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<TicketSale> allTicketsSales = company.getTicketsSalesList();
        ArrayList<TicketSale> ticketsSalePerAgent = new ArrayList<>();
        for(TicketSale ts:allTicketsSales)
        {
            if(ts.getAgentID() == agent.getId())
            {
                ticketsSalePerAgent.add(ts);
            }
        }
        ticketSalesObservableList.addAll(ticketsSalePerAgent);
        return ticketSalesObservableList;
    }
    
    // add to TableView of ticketSales
    public void addToTicketsSalesList(TicketSale ticketSale) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(ticketSale == null)
        {
            System.out.println("TicketSale is null");
            System.exit(-1);
        }
        ticketsSalesList.getItems().add(ticketSale);
        ticketsSalesList.refresh();
        
        // save list of tickets sales to file
        ticketSalesObservableList.add(ticketSale);
        ArrayList<TicketSale> allTicketsSales = company.getTicketsSalesList();
        allTicketsSales.add(ticketSale);
        company.saveListOfTicketsSales(allTicketsSales);
    }
    
    // remove from TableView of ticketSales
    public void removeFromTicketsSalesList(TicketSale ticketSale) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if(ticketSale == null)
        {
            System.out.println("TicketSale is null");
            System.exit(-1);
        }
        ticketsSalesList.getItems().remove(ticketSale);
        ticketsSalesList.refresh();
        
        ticketSalesObservableList.remove(ticketSale);
        ArrayList<TicketSale> allTicketsSales = company.getTicketsSalesList();
        for(TicketSale ts:allTicketsSales)
        {
            if(ts.getSaleID() == ticketSale.getSaleID())
            {
                allTicketsSales.remove(ts);
                break;
            }
        }
        company.saveListOfTicketsSales(allTicketsSales);
    }
    
    // return quantity of removed ticketSale
    public void addQuantityBackOnSale(TicketSale ticketSale) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        for(Ticket t:company.getTicketsList())
        {
            if(t.getEventName().equals(ticketSale.getEventName()))
            {
                t.increaseQuantity(ticketSale.getQuantity());
                updateTicketsList(t);
                setTicketsList(company.getTicketsList());
                break;
            }
        }
    }
}