package view.tableviews;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class TableViewIntrospectionBP<T> extends BorderPane {
	private TableView<T> tv;
	private List<T> list;
	private Class<T> clType;

	public TableViewIntrospectionBP(List<T> list, Class<T> clType) {
		if (list.size() > 0)
			this.list = list;

		this.clType = clType;

		setCenter(getTv());
	}

	public TableView<T> getTv() {
		if (tv == null) {
			tv = new TableView<T>();
			tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			// List
			ObservableList<T> data = FXCollections.observableList(list);
			tv.setItems(data);

			Field[] fields = clType.getDeclaredFields();
			// Adds a column for every field declared by the class
			for (Field f : fields) {
				// Allows to remove static fields from the table
				if (!Modifier.isStatic(f.getModifiers())) {
					String fieldName = f.getName();
					fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

					// Creates and adds the columns
					TableColumn<T, String> col = new TableColumn<>(fieldName);
					col.setCellValueFactory(new PropertyValueFactory<T, String>(fieldName));

					tv.getColumns().add(col);
				}
			}

			// Auto resize the columns and disable the cols reordering
//			tv.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
//				@Override
//				public Boolean call(ResizeFeatures param) {
//					return true;
//				}
//			});

			// Auto resize the columns and disable the cols reordering
			tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		}

		return tv;
	}

	public List<T> getList() {
		return list;
	}
}

class AlertError extends Alert {
	public AlertError(String string) {
		super(AlertType.NONE, string, ButtonType.OK);
		initModality(Modality.WINDOW_MODAL);
		initStyle(StageStyle.UNIFIED);
		setHeaderText(string);
	}
}
