package view.tableviews;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import model.User;
import model.UserManagerSingleton;
import view.ProjSP;

public class TableViewScoresBP extends TableViewIntrospectionBP<User> {
	private Button btnHome;

	/**
	 * Constructor. Instantiates this object using it's super constructor with the
	 * UserManagerSingleton instance and adds the button Home to the bottomHB.
	 * 
	 * @param deck the Deck object containing the List of questions.
	 */
	public TableViewScoresBP() {
		super(UserManagerSingleton.getInstance().getUsersAsList(), User.class);

		// Adds the buttons to the HBox
		getBottomHB().getChildren().addAll(getBtnHome());
		setBottom(getBottomHB());
	}

	@SuppressWarnings("unchecked")
	public TableView<User> getTv() {
		TableView<User> tv = super.getTv();
		tv.setId("scores-table");

		ObservableList<TableColumn<User, ?>> columns = tv.getColumns();

		TableColumn<User, Integer> colPartiesWon = null;
		TableColumn<User, Double> colTotalEarnings = null;
		TableColumn<User, Boolean> colAdmin = null;
		TableColumn<User, String> colPassword = null;
		TableColumn<User, String> colEmail = null;
		for (TableColumn<User, ?> col : columns) {
			// Hide the admin
			if (col.getText().equals("Admin")) {
				colAdmin = (TableColumn<User, Boolean>) col;
			} else

			// Hide the password
			if (col.getText().equals("Password")) {
				colPassword = (TableColumn<User, String>) col;
			} else

			// Hide the email
			if (col.getText().equals("Email")) {
				colEmail = (TableColumn<User, String>) col;
			} else

			// Get the partiesWon col
			if (col.getText().equals("PartiesWon")) {
				colPartiesWon = (TableColumn<User, Integer>) col;
				colPartiesWon.setSortType(SortType.DESCENDING);
				colPartiesWon.setSortNode(new Group());
			} else

			// Get the totalEarnings col
			if (col.getText().equals("TotalEarningsWon")) {
				colTotalEarnings = (TableColumn<User, Double>) col;
				colTotalEarnings.setSortType(SortType.DESCENDING);
				colTotalEarnings.setSortNode(new Group());
			}
		}

		// Removes the columns
		columns.removeAll(colAdmin, colPassword, colEmail);

		// Columns size
		for (TableColumn<User, ?> col : columns) {
			col.minWidthProperty().bind(tv.widthProperty().divide((double) columns.size()));
		}

		// Sets the ordering of the values
		if (colPartiesWon != null & colTotalEarnings != null)
			tv.getSortOrder().setAll(colTotalEarnings, colPartiesWon);

		tv.setEditable(false);
		return tv;
	}

	/**
	 * If null instantiates btnHome, then returns it. This button allows to get back
	 * to the home pane.
	 * 
	 * @return btnHome, the "home" Button object.
	 */
	public Button getBtnHome() {
		if (btnHome == null) {
			btnHome = new Button("Home");
			btnHome.getStyleClass().add("button-medium");

			btnHome.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setVisible(false);
					((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);
				}
			});
		}
		return btnHome;
	}

	/**
	 * Sets the list with the new users list using the UserManagerSingleton instance
	 * and then refreshes the tv items.
	 */
	public void refreshUsersList() {
		setList(UserManagerSingleton.getInstance().getUsersAsList());
		refreshTvItems();
	}
}
