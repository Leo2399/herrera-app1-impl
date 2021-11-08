package baseline;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocalDateTableCell extends TableCell<Events, LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    private final DatePicker datePicker;

    public LocalDateTableCell(TableColumn<Events, LocalDate> dateTableColumn){
        this.datePicker = new DatePicker();
        this.datePicker.setConverter(new StringConverter<>() {

            static final String pattern = "YYYY-MM-dd";
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateTimeFormatter.format(object);
                }
                return " ";
            }

            @Override
            public LocalDate fromString(String string) {

                if (string != null && string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                }else{
                    return LocalDate.parse(" ");
                }
            }
        });
        this.datePicker.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(Boolean.TRUE.equals(newValue)){
                final TableView<Events> tableView = getTableView();
                tableView.getSelectionModel().select(getTableRow().getIndex());
                tableView.edit(tableView.getSelectionModel().getSelectedIndex(), dateTableColumn);
            }
        });
        this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(isEditing()){
                commitEdit(newValue);
            }
        });
        editableProperty().bind(dateTableColumn.editableProperty());

        contentDisplayProperty().bind(Bindings.when(editableProperty()).then(ContentDisplay.GRAPHIC_ONLY).otherwise(ContentDisplay.TEXT_ONLY));
    }

    @Override
    protected void updateItem(LocalDate item, boolean empty){
        super.updateItem(item,empty);

        if(empty){
            setText(" ");
            setGraphic(null);
        }else{
            this.datePicker.setValue(item);
            setGraphic(this.datePicker);
            if(item==null){
                setText(" ");
            }else{
                setText(formatter.format(item));
            }
        }
    }
}
