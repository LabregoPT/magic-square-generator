package ui;

import model.*;
import customExceptions.InvalidSizeException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Controller class for the user interface.
 * @author Jhon Edward Mora - A00355710
 * @version 1.0 - 2019
 *
 */
public class Controller {

    /**Field to input the size of the magic square.*/
    @FXML
    private TextField sizeTF;

    /**Initial position input ComboBox*/
    @FXML
    private ComboBox<String> initPosCB;

    /**ToggleGroup for the filling direction RadioButtons*/
    @FXML
    private ToggleGroup directionToggleGroup;
    
    /**First RadioButton for the filling direction.*/
    @FXML
    private RadioButton directionRB1;
    
    /**ScrollPane that contains the generated matrix.*/
    @FXML
    private ScrollPane gridContainer;
    
    /**Second RadioButton for the filling direction.*/
    @FXML
    private RadioButton directionRB2;
    
    /**Main button that controls interactions with the GUI*/
    @FXML
    private Button mainButton;
    
    /**Integer that holds the size of the square to be generated.*/
    private int squareSize;
    
    /**Character that holds the filling direction of the square.*/
    private char fillDirection;
    
    /**String that holds the initial position in the filling process.*/
    private String initialPosition;
    
    /**Relation with class MagicSquare.*/
    private MagicSquare ms;
    
    /**Matrix that will contain the labels representing the Magic Square.*/
    private Label[][] labelMatrix;

    /**
     * Handles the event in which a RadioButton with a direction is selected.
     * @param event The onAction ActionEvent.
     */
    @FXML
    void directionRBSelected(ActionEvent event) {
    	fillDirection = (char)directionToggleGroup.getSelectedToggle().getUserData();
    }
    
    /**
     * This method generates an instance of MagicSquare and one of GridPane with the MagicSquare's recently created instance's data, then fills the labelMatrix with each corresponding value from the matrix from the recently generated MagicSquare, assigns the value of the MagicConstant to the last row and column of the label matrix and finally sets the GridPane as the content for the ScrollPane.
     * @param event A Button ActionEvent
     */
    @FXML
    void fillSquare(ActionEvent event) {
    	try {
    		squareSize = Integer.parseInt(sizeTF.getText());
    		ms = new MagicSquare(squareSize, fillDirection, initialPosition);
    		showSquare();
    		mainButton.setText("Clear");
    		mainButton.setOnAction(new EventHandler<ActionEvent>() {
    			public void handle(ActionEvent ae){
    				clearContainer();
    			}
    		});
    	}catch(NumberFormatException e) {
    		Label err = new Label("Inputted size is not a number");
    		gridContainer.setContent(err);
    	}catch(InvalidSizeException e) {
    		Label err = new Label("Inputted size is invalid, " + e.getMessage());
    		gridContainer.setContent(err);
    	}catch(NullPointerException e) {
    		Label err = new Label ("There's some missing data required to generate a square");
    		gridContainer.setContent(err);
    	}
    	
    }

    /**
     * Method used to clear the contents in the interface before generating a new magic square.
     */
    protected void clearContainer() {
		gridContainer.setContent(null);
		mainButton.setText("Fill");
		mainButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				fillSquare(ae);
			}
		});
	}

	/**
     * This method gets the data of the magic square located in <code>ms</code> and displays its content in MyLabel s contained within a GridPane, representing the magic square.
     */
    void showSquare() {
    	GridPane gp = new GridPane();
    	gp.setHgap(8);
    	gp.setVgap(8);
    	gp.add(new Label(), 0, 0, squareSize+1, squareSize+1);
    	int[][] magicSquare = ms.getMatrix();
    	labelMatrix = new Label[squareSize+1][squareSize+1];
    	for(int i = 0; i<squareSize+1; i++) {
    		for (int j = 0; j<squareSize+1; j++) {
    			MyLabel l = new MyLabel();
    			l.setFont(new Font("Arial", 16));
    			l.setTextFill(Color.BLACK);
    			l.setMaxWidth(Double.MAX_VALUE);
    			l.setAlignment(Pos.CENTER);
    			if(i<squareSize && j<squareSize) {
    				l.setText(magicSquare[j][i]+"");
    				l.setOnMouseClicked(new EventHandler<MouseEvent>() {
    					public void handle(MouseEvent arg0) {
							highlight(l);
						}
    				});
					l.setSearchedID(i, j);
    				gp.add(l, i, j);
    			}else {
    				l.setText(" = "+ms.getConstant());
    				l.setVisible(false);
    				gp.add(l, i, j);
    			}
    			labelMatrix[i][j] = l;
			}
    	}
    	gridContainer.setContent(gp);
    }
    
    /**
     * This method searches for each MyLabel object contained within <code>labelMatrix</code> that has its column and row id equals as the MyLabel that has been clicked, and then changes its <code>textFill</code> attribute to <code>Color.RED</code> while setting every other label's to <code>Color.BLACK</code>
     * @param l The MyLabel that has been clicked.
     */
	void highlight(MyLabel l) {
		int searchedI = l.getSearchedID()[0];
		int searchedJ = l.getSearchedID()[1];
		for (int i = 0; i < squareSize+1; i++) {
			for (int j = 0; j < squareSize+1; j++) {
				if(i==searchedI || j == searchedJ) {
					labelMatrix[i][j].setTextFill(Color.RED);
				}else {
    				labelMatrix[i][j].setTextFill(Color.BLACK);
    			}if(i == squareSize) {
    				labelMatrix[i][j].setVisible(false);
    			}if(j == squareSize) {
    				labelMatrix[i][j].setVisible(false);
    			}
			}
		}
    	labelMatrix[searchedI][squareSize].setVisible(true);
    	labelMatrix[squareSize][searchedJ].setVisible(true);
	}


	/**
     * A listener method designed to change the text and values of each RadioButton used in this program.
     * @param event A ComboBox ActionEvent listener.
     */
    @FXML
    void initPosChanged(ActionEvent event) {
    	directionRB1.setDisable(false);
    	directionRB2.setDisable(false);
    	String value = initPosCB.getValue();
    	initialPosition = value;
    	if(value.equals(MagicSquare.UP)) {
    		initialPosition = MagicSquare.UP;
    		directionRB1.setText("NL");
    		directionRB1.setUserData(MagicSquare.NL);
    		directionRB2.setText("NR");
    		directionRB2.setUserData(MagicSquare.NR);
    	}else if(value.equals(MagicSquare.LEFT)) {
    		initialPosition = MagicSquare.LEFT;
    		directionRB1.setText("NL");
    		directionRB1.setUserData(MagicSquare.NL);
    		directionRB2.setText("SL");
    		directionRB2.setUserData(MagicSquare.SL);
    	} else if(value.equals(MagicSquare.RIGHT)) {
    		initialPosition = MagicSquare.RIGHT;
    		directionRB1.setText("NR");
    		directionRB1.setUserData(MagicSquare.NR);
    		directionRB2.setText("SR");
    		directionRB2.setUserData(MagicSquare.SR);
    	}else if(value.equals(MagicSquare.DOWN)) {
    		initialPosition = MagicSquare.DOWN;
    		directionRB1.setText("SL");
    		directionRB1.setUserData(MagicSquare.SL);
    		directionRB2.setText("SR");
    		directionRB2.setUserData(MagicSquare.SR);
    	}
    }

    /**
     * Auxiliary class that only adds a new field in Label to keep two different values as an ID.
     * @author Jhon Edward Mora - Universidad ICESI.     *
     */
    private class MyLabel extends Label{

    	/**A two position array that holds the vertical and horizontal id of an instance of this Label*/
    	private int[] searchedID;
    	
    	/**
    	 * Sets i and j as the vertical and horizontal id of an instance of this Label.
    	 * @param i Vertical id (position in this case) of the Label.
    	 * @param j Horizontal id (position in this case) of the Label.
    	 */
    	public void setSearchedID(int i, int j) {
    		searchedID = new int[2];
    		searchedID[0] = i;
    		searchedID[1] = j;
    	}
    	
    	/**
    	 * Returns the array holding the id of an instance of this Label.
    	 * @return A two position array holding both the horizontal and vertical array of an instance of this Label.
    	 */
    	public int[] getSearchedID() {
    		return searchedID;
    	}
    }
}
