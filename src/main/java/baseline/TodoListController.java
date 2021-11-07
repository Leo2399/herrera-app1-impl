package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.lang.invoke.StringConcatException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TodoListController implements Initializable {

    @FXML
    private MenuItem completeItem;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker dueDate;

    @FXML
    private MenuItem incompleteItem;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveToFile;

    @FXML
    private TextField titleTextField;

    @FXML
    private TableView<Events> itemList;

    @FXML
    private TableColumn<Events, String> titleCol;

    @FXML
    private TableColumn<Events, String> descriptionCol;

    @FXML
    private TableColumn<Events, LocalDate> dateCol;

    @FXML
    private Label label;

    @FXML
    private TableColumn<Events, String> statusCol;

    private static int itemCount = 0;
    private String string = "Item: ";

    private final ObservableList <Events> list = FXCollections.observableArrayList();

    @FXML
    void addTask(ActionEvent event) {
        // This method should add the task into the list

        // Counter to keep track of the number if items
        itemCount++;
        label.setText(string + itemCount);

        // Add date to the observable list and then into the TableView
        list.add(new Events(titleTextField.getText(), descriptionTextField.getText(), dueDate.getValue()));
        itemList.setItems(list);

        // Allow editing by double-clicking
        itemList.setEditable(true);

        // Add to the Title column
        titleCol.setCellValueFactory(param -> param.getValue().titleProperty());

        // Add to the Description column and allow editing
        descriptionCol.setCellValueFactory(param -> param.getValue().descriptionProperty());
        descriptionCol.setCellFactory(edit -> new WrapAndEditCell());
        descriptionCol.setOnEditCommit(event1 -> event1.getTableView().getItems().get(event1.getTablePosition().getRow()).setDescription(event1.getNewValue()));

        // Add to the Due Date column
        dateCol.setCellValueFactory(param -> param.getValue().dueDateProperty());

        // Add initial incomplete status to the Status column than can be changed to complete
        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Complete")));
        statusCol.setCellValueFactory(param -> param.getValue().statusProperty());

        // Clear the text fields and reset the date picker
        titleTextField.clear();
        descriptionTextField.clear();
        dueDate.setValue(null);
    }

    @FXML
    void clearList(ActionEvent event) {
        // Clears the current list

        // Gets all the items currently in the list and removes them all
        itemList.getItems().removeAll(list);

        // Reset the counter to zero
        itemCount = 0;
        label.setText(string+itemCount);
    }

    @FXML
    void deleteTask(ActionEvent event) {
        // This method will delete a specific task from the list

        // Delete the highlighted item
        itemList.getItems().removeAll(itemList.getSelectionModel().getSelectedItem());

        // Keep track of remaining items in the list
        itemCount--;
        if(itemCount<0){
            itemCount=0;
        }
        label.setText(string+itemCount);
    }

    @FXML
    void openExistingFile(ActionEvent event) {
        // Will open a file with existing items in a list
        // Choose the file you want to open
    }

    @FXML
    void viewCompleted(ActionEvent event) {
        // Display only the completed tasks in the list
    }

    @FXML
    void viewIncompleted(ActionEvent event) {
        // Display only the incomplete tasks in the list
    }

    @FXML
    void writeToFile(ActionEvent event) {
        // Save the current list into a text file
        // Open a new file writer and write the current list into the text file
    }

    private void textLimiter(final TextField tf, final int maxLength){
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(tf.getText().length()>maxLength){
                String s = tf.getText().substring(0,maxLength);
                tf.setText(s);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        label.setText(string+itemCount);

        // Limit characters for the description to 256
        textLimiter(descriptionTextField, 256);
    }

}