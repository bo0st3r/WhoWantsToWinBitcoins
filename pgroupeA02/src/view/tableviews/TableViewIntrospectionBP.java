package view.tableviews;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TableViewIntrospectionBP<T> extends BorderPane {
	private TableView<T> tv;
	private List<T> list;
	private Class<T> clType;
	private HBox bottomHB;

	/**
	 * Constructor. Sets the list and clType fields using the given param. Sets the
	 * pane style, set the TableView as center and a HBox as the bottom.
	 * 
	 * @param list   the list used for the TableView.
	 * @param clType the class of the objects contained by the list.
	 */
	public TableViewIntrospectionBP(List<T> list, Class<T> clType) {
		if (list.size() > 0)
			this.list = list;

		this.clType = clType;
		getStyleClass().add("table-resize");

		setCenter(getTv());
		setBottom(getBottomHB());
	}

	/**
	 * Returns the tv field. If null instantiates it and, using introspection, gets
	 * a TableView showing every non-static declared fields from the clType Class.
	 * Columns resize policy is unconstrained.
	 * 
	 * 
	 * @return the TableView object using the list objects as generic type.
	 */
	public TableView<T> getTv() {
		if (tv == null) {
			tv = new TableView<T>();

			// List
			ObservableList<T> data = FXCollections.observableList(list);
			tv.setItems(data);

			Field[] fields = clType.getDeclaredFields();
			// Adds a column for every field declared by the class
			for (Field f : fields) {
				// Allows to remove static fields from the table
				if (!Modifier.isStatic(f.getModifiers())) {
					f.setAccessible(true);

					String fieldName = f.getName();
					fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

					// Creates and adds the columns
					TableColumn<T, String> col = new TableColumn<>(fieldName);
					col.setCellValueFactory(new PropertyValueFactory<T, String>(fieldName));

					tv.getColumns().add(col);
				}
			}

			// Auto resize the columns and disable the cols reordering
			tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		}

		return tv;
	}

	/**
	 * Returns bottomHB, if null instantiates it and sets it's style.
	 * 
	 * @return bottomHB, a HBox object.
	 */
	public HBox getBottomHB() {
		if (bottomHB == null) {
			bottomHB = new HBox();
			bottomHB.setSpacing(25);
			bottomHB.setAlignment(Pos.CENTER);
			bottomHB.setId("tableview-bottom-hb");
		}
		return bottomHB;
	}

	/**
	 * Returns the list field.
	 * 
	 * @return list, a generic List.
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * Sets a new list.
	 * 
	 * @param list the new list.
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * Refreshes the tv items list.
	 */
	public void refreshTvItems() {
		ObservableList<T> data = FXCollections.observableList(list);
		tv.setItems(data);
	}
}
