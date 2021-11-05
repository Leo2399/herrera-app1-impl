package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

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

    private static int itemCount = 0;

    private final ObservableList <Events> list = FXCollections.observableArrayList();

    @FXML
    void addTask(ActionEvent event) {
        // This method should add the task into the list

        // Counter to keep track of the number if items
        itemCount++;
        label.setText("Number of items in list: " + itemCount);

        // Add date to the observable list and then into the TableView
        list.add(new Events(titleTextField.getText(), descriptionTextField.getText(), dueDate.getValue()));
        itemList.setItems(list);

        // Add to the Title column
        titleCol.setCellValueFactory(param -> param.getValue().titleProperty());

        // Add to the Description column
        descriptionCol.setCellValueFactory(param -> param.getValue().descriptionProperty());

        // Add to the Due Date column
        dateCol.setCellValueFactory(param -> param.getValue().dueDateProperty());

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
        label.setText("Number of items in the list: "+itemCount);
    }

    @FXML
    void deleteTask(ActionEvent event) {
        // This method will delete a specific task from the list

        // Delete the highlighted item
        int selected = itemList.getSelectionModel().getSelectedIndex();

        if(selected != -1){
            final int newSelectedIdx = (selected == itemList.getItems().size() - 1)
                    ? selected - 1
                    : selected;

            itemList.getItems().remove(selected);
            itemList.getSelectionModel().select(newSelectedIdx);
        }

        // Decrements to keep track of remaining items in the list
        itemCount--;
        label.setText("Number of items: "+itemCount);
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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        label.setText("Number of items in the list: "+itemCount);
    }

}