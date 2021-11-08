package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class TodoListController implements Initializable {

    @FXML
    private TextField descriptionTextField;

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
    private TableColumn<Events, String> statusCol;

    @FXML
    private ComboBox<String> filterBox;

    @FXML
    private MenuBar fileMenu;

    private final ObservableList <Events> list = FXCollections.observableArrayList();
    private final Desktop desktop = Desktop.getDesktop();
    private FileChooser fileChooser = new FileChooser();

    @FXML
    void addTask(ActionEvent event) {
        // This method should add the task into the list

        // Add date to the observable list and then into the TableView
        list.add(new Events(titleTextField.getText(), descriptionTextField.getText()));
        itemList.setItems(list);

        // Allow editing by double-clicking
        itemList.setEditable(true);

        if(titleTextField.getText().equals("") || descriptionTextField.getText().equals("")){
            itemList.getItems().clear();

            titleTextField.setText("Enter title");
            titleTextField.selectAll();
            titleTextField.requestFocus();

            descriptionTextField.setText("Enter description");
            descriptionTextField.selectAll();
            descriptionTextField.requestFocus();
        }

        // Add to the Title column
        titleCol.setCellValueFactory(param -> param.getValue().titleProperty());

        // Add to the Description column and allow editing
        descriptionCol.setCellValueFactory(param -> param.getValue().descriptionProperty());
        descriptionCol.setCellFactory(edit -> new WrapAndEditCell());

        // Add to the Due Date column
        dateCol.setCellValueFactory(param -> param.getValue().dueDateProperty());
        dateCol.setEditable(true);
        dateCol.setCellFactory(LocalDateTableCell::new);

        // Add initial incomplete status to the Status column than can be changed to complete
        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Complete")));
        statusCol.setCellValueFactory(param -> param.getValue().statusProperty());

        // Clear the text fields and reset the date picker
        titleTextField.clear();
        descriptionTextField.clear();
    }

    @FXML
    void clearList(ActionEvent event) {
        // Clears the current list
        // Gets all the items currently in the list and removes them all
        itemList.getItems().removeAll(list);
    }

    @FXML
    void deleteTask(ActionEvent event) {
        // This method will delete a specific task from the list
        // Delete the highlighted item
        itemList.getItems().removeAll(itemList.getSelectionModel().getSelectedItem());
    }

    @FXML
    void filterList(ActionEvent event) {
        // Filters between complete and incomplete items
        FilteredList<Events> selected = new FilteredList<>(list, i -> Objects.equals(i.getStatus(), filterBox.getSelectionModel().getSelectedItem()));
        itemList.setItems(selected);

        // Resets list to show all items
        if(selected.isEmpty()){
            itemList.setItems(list);
        }
    }

    @FXML
    void openExistingFile(ActionEvent event) throws IOException {
        // Will open a file with existing items in a list
        // Choose the file you want to open
        Stage newStage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(newStage);
        desktop.open(file);
    }

    @FXML
    void writeToFile(ActionEvent event) throws IOException {
        // Save the current list into a text file
        // Open a new file writer and write the current list into the text file
        Stage newStage = new Stage();

        // Set the title
        fileChooser.setTitle("Save list");

        // Set the extension type to txt
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text file","*.txt"));

        // Checks for empty table to make sure one isn't saved
        if(list.isEmpty()){
            newStage.initOwner(this.fileMenu.getScene().getWindow());
            Alert empty = new Alert(Alert.AlertType.ERROR, "Empty table", ButtonType.OK);
            empty.setContentText("You have nothing");
        }else{
            File file = fileChooser.showSaveDialog(newStage);
            if(file!=null){
                save(itemList.getItems(), file);
            }
        }
    }

    private void save(ObservableList<Events> e, File file) throws IOException {

        // Helper method used to write the data from the table into the text file
            try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {

                for (Events events : e) {
                    output.write(events.toString());
                    output.newLine();
                }

            } catch(IOException ex){
            Alert ioAlert = new Alert(Alert.AlertType.ERROR,"No file", ButtonType.OK);
            ioAlert.setContentText("File could not be created");
            ioAlert.showAndWait();
            if(ioAlert.getResult()==ButtonType.OK){
                ioAlert.close();
            }
        }
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
        // Limit characters for the description to 256
        textLimiter(descriptionTextField, 256);

        // Filter
        filterBox.getItems().addAll("All", "Complete", "Incomplete");

        // Set initial directory
        fileChooser.setInitialDirectory(new File("C:\\temp"));

    }

}