//package view.tableviews;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.util.converter.IntegerStringConverter;
//import model.Earning;
//import view.ProjSP;
//
//public class TableViewEarningsBP extends TableViewIntrospectionBP<Integer> {
//	private Earning earning;
//	private Button btnProfile;
//
//	public TableViewEarningsBP(Earning earning) {
//		super(earning.getAmounts(), Integer.class);
//		this.earning = earning;
//
//		// Adds the "profile" button to the bottom of the page
//		getBottomHB().getChildren().add(getBtnProfile());
//	}
//
//	@SuppressWarnings("unchecked")
//	public TableView<Integer> getTv() {
//		TableView<Integer> tv = super.getTv();
//		tv.setEditable(true);
//
//		ObservableList<Integer> data = FXCollections.observableList(getList());
//		tv.setItems(data);
//
//		ObservableList<TableColumn<Integer, ?>> columns = tv.getColumns();
//
//		for (int i = 0; i <= columns.size() - 1; i++) {
//			// For the value column
//			if (columns.get(i).getText().equals("Value")) {
//				System.out.println("hey");
//				TableColumn<Integer, Integer> col = (TableColumn<Integer, Integer>) columns.get(i);
////				col.setCellValueFactory(new SimpleIntegerProperty().asObject());
//				col.setCellFactory(TextFieldTableCell.<Integer, Integer>forTableColumn(new IntegerStringConverter()));
//			}
//		}
//
//		return tv;
//	}
//
//	public Button getBtnProfile() {
//		if (btnProfile == null) {
//			btnProfile = new Button("Profile");
//			btnProfile.getStyleClass().add("button-medium");
//
//			// Hides this pane and shows the profile pane
//			btnProfile.setOnAction(new EventHandler<ActionEvent>() {
//				@Override
//				public void handle(ActionEvent event) {
//					((ProjSP) getParent().getParent()).getHomeGridPane().setVisible(true);
//					setVisible(false);
//				}
//			});
//		}
//
//		return btnProfile;
//	}
//
//}
