package UI;

import Automaton.Automaton;
import Exceptions.InCorrectFormatException;
import Parser.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button DFAButton;

    @FXML
    private TextArea TaInput;

    @FXML
    private TextArea TaOutput;

    @FXML
    private Button TestStringButton;

    @FXML
    private Button ParseButton;

    @FXML
    private Button RegExParseButton;

    @FXML
    private Button NFAButton;

    @FXML
    private Button showWordsButton;

    private Automaton automaton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert TestStringButton != null : "fx:id=\"TestStringButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert NFAButton != null : "fx:id=\"NFAButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert TaInput != null : "fx:id=\"TaInput\" was not injected: check your FXML file 'sample.fxml'.";
        assert DFAButton != null : "fx:id=\"DFAButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert RegExParseButton != null : "fx:id=\"RegExParseButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert TaOutput != null : "fx:id=\"TaOutput\" was not injected: check your FXML file 'sample.fxml'.";
        assert ParseButton != null : "fx:id=\"ParseButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert showWordsButton != null : "fx:id=\"showWordsButton\" was not injected: check your FXML file 'sample.fxml'.";

        automaton = null;

        InitializingButtons();
    }

    private void InitializingButtons() {
        initializeDFAButton();
        initializeTestStringButton();
        initializeParseButton();
        initializeRegExParseButton();
        initializeShowWordsButton();
        initializNFAButton();
    }

    private void initializeDFAButton() {
        // define action of DFAButton
        DFAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (automaton == null) {
                    try {
                        automaton = Parser.parse(TaInput.getText());
                        TaOutput.setText(TaOutput.getText() + "\n Derministic Finite Automata: " + automaton.isDeterministicFiniteAutomata());
                    } catch (InCorrectFormatException e) {
                        String errorMessage = "the format was incorrect \n:";
                        errorMessage += e.getMessage();
                        TaOutput.setText(errorMessage);
                    }
                } else {
                    TaOutput.setText(TaOutput.getText() + "\n Derministic Finite Automata: " + automaton.isDeterministicFiniteAutomata());
                }
            }
        });
    }

    private void initializeTestStringButton() {
        TestStringButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (automaton != null) {
                    try {
                        TaOutput.setText(TaOutput.getText() + "\n was the input accepted: " + automaton.isStringAccepted(TaInput.getText()));
                    } catch (Exception e) {
                        String errorMessage = "the input string was incorrect \n:";
                        errorMessage += e.getMessage();
                        TaOutput.setText(errorMessage);
                    }
                } else {
                    TaOutput.setText(TaOutput.getText() + "\n first use Parse to create a automaton");
                }
            }
        });
    }

    private void initializeParseButton() {
        ParseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    automaton = Parser.parse(TaInput.getText());
                    TaOutput.setText(TaOutput.getText() + "\n input had been parsed ");
                } catch (Exception e) {
                    String errorMessage = "the input string was incorrect \n:";
                    errorMessage += e.getMessage();
                    TaOutput.setText(errorMessage);
                }
            }
        });
    }

    private void initializeRegExParseButton(){
        RegExParseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    automaton = RegExParser.Parse(TaInput.getText());

                    if (automaton == null) {
                        TaOutput.setText(TaOutput.getText() + "\n the input string was incorrect ");
                    } else {
                        TaOutput.setText(TaOutput.getText() + "\n input had been parsed ");
                    }
                } catch (Exception e) {
                    String errorMessage = "the input string was incorrect \n:";
                    errorMessage += e.getMessage();
                    TaOutput.setText(errorMessage);
                }
            }
        });
    }

    private void initializeShowWordsButton(){
        showWordsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                throw new UnsupportedOperationException("");
            }
        });
    }

    private void initializNFAButton(){
        NFAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (automaton != null) {
                    try {
                        TaOutput.setText(TaOutput.getText() + "\n was the input accepted: " + automaton.isFinite());
                    } catch (Exception e) {
                        String errorMessage = "the input string was incorrect \n:";
                        errorMessage += e.getMessage();
                        TaOutput.setText(errorMessage);
                    }
                } else {
                    TaOutput.setText(TaOutput.getText() + "\n first use Parse to create a automaton");
                }
            }
        });
    }
}
