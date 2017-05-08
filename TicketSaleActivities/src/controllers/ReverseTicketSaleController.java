package controllers;

import models.TicketSale;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marcin Wisniewski
 */
public class ReverseTicketSaleController {
    private CompanyController companyController;
    private TicketSale ticketSaleSelected;
    
    @FXML private AnchorPane revertTicketSaleAnchorPane;
    
    // constructor
    public ReverseTicketSaleController(CompanyController companyController, TicketSale ticketSale)
    {
        this.companyController = companyController;
        this.ticketSaleSelected = ticketSale;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ReverseTicketSaleGUI.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "ReverseTicketSaleGUI.fxml file not loaded properly.");
        }
    }
    
    // action events
    @FXML
    public void setRevertTicketSaleAnchorPane(AnchorPane revertTicketSaleGUI)
    {
        revertTicketSaleAnchorPane = revertTicketSaleGUI;
    }
    
    @FXML
    public AnchorPane getRevertTicketSaleAnchorPane()
    {
        return revertTicketSaleAnchorPane;
    }
    
    @FXML
    public void cancelSaleReverting(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void confirmSaleReverting(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        companyController.addQuantityBackOnSale(ticketSaleSelected);
        companyController.removeFromTicketsSalesList(ticketSaleSelected);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
