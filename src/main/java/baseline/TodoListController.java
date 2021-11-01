package baseline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class TodoListController {

    @FXML
    private Button addButton;

    @FXML
    private MenuItem clear;

    @FXML
    private MenuItem completeItem;

    @FXML
    private MenuItem delete;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField dueDateTextField;

    @FXML
    private MenuItem incompleteItem;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveToFile;

    @FXML
    private ListView<?> taskList;

    @FXML
    private TextField titleTextField;

    @FXML
    void addTask(ActionEvent event) {
        // This method should add the task into the list
        // Adds the title, due date, and the description
        // after the task has been added the text field should refresh and be blank to enter new information
    }

    @FXML
    void clearList(ActionEvent event) {
        // Clears the current list
    }

    @FXML
    void deleteTask(ActionEvent event) {
        // This method will delete a specific task from the list
        // choose which task to remove
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

}