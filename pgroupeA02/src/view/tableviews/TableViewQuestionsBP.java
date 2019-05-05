package view.tableviews;

import enumerations.Round;
import exceptions.NotEnoughAnswersException;
import exceptions.NotEnoughQuestionsException;
import exceptions.QuestionAlreadyPresentException;
import exceptions.StatementTooShortException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Deck;
import model.Party;
import model.Question;
import utilities.Serialization;
import view.AddQuestionGP;
import view.ProjSP;
import view.AlertError;

public class TableViewQuestionsBP extends TableViewIntrospectionBP<Question> {
	private AddQuestionGP addQuestionGP;
	private Deck deck;
	private Button btnHome;
	private Button btnDelete;
	private Button btnAdd;
	private Button btnTable;

	public TableViewQuestionsBP(Deck deck) {
		super(deck.getQuestions(), Question.class);
		this.deck = deck;

		// Adds the buttons to the HBox
		getBottomHB().getChildren().addAll(getBtnHome(), getBtnDelete(), getBtnAdd());
		setBottom(getBottomHB());
	}

	@SuppressWarnings("unchecked")
	@Override
	public TableView<Question> getTv() {
		TableView<Question> tv = super.getTv();
		tv.setEditable(true);

		ObservableList<Question> data = FXCollections.observableList(getList());
		tv.setItems(data);

		ObservableList<TableColumn<Question, ?>> columns = tv.getColumns();

		TableColumn<Question, Round> colRound = null;
		TableColumn<Question, String> colStatement = null;
		for (int i = 0; i <= columns.size() - 1; i++) {

			// For the Round column
			if (columns.get(i).getText().equals(Round.NAME)) {
				colRound = (TableColumn<Question, Round>) columns.get(i);
				handleRound(colRound, tv);
			}

			// For the statement column
			if (columns.get(i).getText().equals("Statement")) {
				colStatement = (TableColumn<Question, String>) columns.get(i);
				handleStatement(colStatement, tv);
			}

			// For the author column
			if (columns.get(i).getText().equals("Author")) {
				handleAuthor((TableColumn<Question, String>) columns.get(i), tv);
			}
		}

		// Sets the ordering of the values
		tv.getSortOrder().setAll(colRound, colStatement);

		return tv;
	}

	public void handleRound(TableColumn<Question, Round> col, TableView<Question> tv) {
		col.setCellFactory(ComboBoxTableCell.<Question, Round>forTableColumn(Round.values()));
		col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question, Round>>() {

			@Override
			public void handle(CellEditEvent<Question, Round> event) {
				// Gets the old question
				Question oldValue = event.getRowValue().clone();
				// Gets the new question
				Question newValue = event.getRowValue().clone();
				newValue.setRound(event.getNewValue());
				Deck testDeck = deck.clone();

				try {
					// Tries to update the deck
					if (testDeck.update(oldValue, newValue)) {
						// Verify the question can be used in a party
						@SuppressWarnings("unused")
						Party deckTester = new Party(testDeck);

						// Updates the view
						event.getRowValue().setRound(event.getNewValue());
						getTv().sort();

						// Updates the deck file
						deck.update(oldValue, newValue);
						Serialization.updateDeckFile(deck, Deck.FILE_NAME);
					}

				} catch (NotEnoughQuestionsException e) {
					AlertError error = new AlertError("You can't do that, there's not enough questions for the "
							+ event.getOldValue().toString().toLowerCase().replace('_', ' ') + ".");
					error.show();
					tv.refresh();
					e.printStackTrace();

				} catch (QuestionAlreadyPresentException e) {
					tv.refresh();
					e.printStackTrace();

				} catch (NotEnoughAnswersException e) {
					tv.refresh();
					e.printStackTrace();
				}
			}
		});
	}

	private void handleStatement(TableColumn<Question, String> col, TableView<Question> tv) {
		// Sorts by statement order

		col.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
		col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question, String>>() {

			@Override
			public void handle(CellEditEvent<Question, String> event) {
				// Gets the old question
				Question oldValue = event.getRowValue().clone();
				// Gets the new question
				Question newValue = event.getRowValue().clone();

				Deck testDeck = deck.clone();

				try {
					// Tries to update the new value
					newValue.setStatement(event.getNewValue());

					// Tries to update the deck
					if (testDeck.update(oldValue, newValue)) {
						// Updates the view
						event.getRowValue().setStatement(event.getNewValue());
						getTv().sort();

						// Updates the model
						deck.update(oldValue, newValue);
						Serialization.updateDeckFile(deck, Deck.FILE_NAME);
					}
					tv.refresh();

				} catch (StatementTooShortException e) {
					AlertError error = new AlertError("The given statement is too short, it must be at least "
							+ Question.STATEMENT_MIN_LENGTH + " characters long.");
					error.show();
					tv.refresh();
					e.printStackTrace();

				} catch (QuestionAlreadyPresentException e) {
					AlertError error = new AlertError("The question \"" + event.getNewValue() + "\" already exists.");
					error.show();
					tv.refresh();
					e.printStackTrace();

				} catch (NotEnoughAnswersException e) {
					tv.refresh();
					e.printStackTrace();
				}
			}
		});
	}

	private void handleAuthor(TableColumn<Question, String> col, TableView<Question> tv) {
		col.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
		col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question, String>>() {

			@Override
			public void handle(CellEditEvent<Question, String> event) {
				// Gets the old question
				Question oldValue = event.getRowValue().clone();
				// Gets the new question
				Question newValue = event.getRowValue().clone();

				Deck testDeck = deck.clone();

				try {
					// Tries to update the new value
					newValue.setAuthor(event.getNewValue());

					// Tries to update the deck
					if (testDeck.update(oldValue, newValue)) {
						event.getRowValue().setAuthor(event.getNewValue());

						deck.update(oldValue, newValue);
						Serialization.updateDeckFile(deck, Deck.FILE_NAME);
					}
					tv.refresh();

				} catch (QuestionAlreadyPresentException e) {
					tv.refresh();
					e.printStackTrace();

				} catch (NotEnoughAnswersException e) {
					tv.refresh();
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * If null instantiate btnHome, then returns it. Defines it's action when using
	 * it.
	 * 
	 * @return The "home" Button object.
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

	public Button getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new Button("Delete");
			btnDelete.getStyleClass().add("button-medium");

			btnDelete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Removes from model
					deck.removeAll(getTv().getSelectionModel().getSelectedItems());
					Serialization.updateDeckFile(deck, Deck.FILE_NAME);

					// Removes from view
					getTv().getItems().removeAll(getTv().getSelectionModel().getSelectedItems());
					getTv().refresh();
				}
			});
		}
		return btnDelete;
	}

	public Button getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new Button("Add");
			btnAdd.getStyleClass().add("button-medium");

			addQuestionGP = new AddQuestionGP();
			addQuestionGP.setId("addQuestionPane");
			addQuestionGP.setMinSize(570, 305);

			btnAdd.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Ajout
					setCenter(addQuestionGP);
					getBottomHB().getChildren().removeAll(getBtnAdd(), getBtnDelete());
					getBottomHB().getChildren().add(getBtnTable());
				}
			});
		}
		return btnAdd;
	}

	/**
	 * If null instantiate btnTable, then returns it. Defines it's action when using
	 * it.
	 * 
	 * @return The "table" Button object.
	 */
	public Button getBtnTable() {
		if (btnTable == null) {
			btnTable = new Button("Table");
			btnTable.getStyleClass().add("button-medium");

			btnTable.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					getBottomHB().getChildren().addAll(getBtnDelete(), getBtnAdd());
					getBottomHB().getChildren().remove(btnTable);
					setCenter(getTv());
				}
			});
		}

		return btnTable;
	}

	/**
	 * Returns the deck.
	 * 
	 * @return the Deck object.
	 */
	public Deck getDeck() {
		return deck;
	}
}
