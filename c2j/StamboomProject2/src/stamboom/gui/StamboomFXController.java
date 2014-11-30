/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboom.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stamboom.controller.StamboomController;
import stamboom.domain.Geslacht;
import stamboom.domain.Gezin;
import stamboom.domain.Persoon;
import stamboom.util.StringUtilities;

/**
 *
 * @author frankpeeters
 */
public class StamboomFXController extends StamboomController implements Initializable {

    //MENUs en TABs
    @FXML MenuBar menuBar;
    @FXML MenuItem miNew;
    @FXML MenuItem miOpen;
    @FXML MenuItem miSave;
    @FXML CheckMenuItem cmDatabase;
    @FXML MenuItem miClose;
    @FXML Tab tabPersoon;
    @FXML Tab tabGezin;
    @FXML Tab tabPersoonInvoer;
    @FXML Tab tabGezinInvoer;

    //PERSOON
    @FXML ComboBox cbPersonen;
    @FXML TextField tfPersoonNr;
    @FXML TextField tfVoornamen;
    @FXML TextField tfTussenvoegsel;
    @FXML TextField tfAchternaam;
    @FXML TextField tfGeslacht;
    @FXML TextField tfGebDatum;
    @FXML TextField tfGebPlaats;
    @FXML ComboBox cbOuderlijkGezin;
    @FXML ListView lvAlsOuderBetrokkenBij;
    @FXML Button btStamboom;

    //INVOER GEZIN
    @FXML ComboBox cbOuder1Invoer;
    @FXML ComboBox cbOuder2Invoer;
    @FXML TextField tfHuwelijkInvoer;
    @FXML TextField tfScheidingInvoer;
    @FXML Button btOKGezinInvoer;
    @FXML Button btCancelGezinInvoer;
    
    //Gezin
    @FXML TextField tbNr;
    @FXML TextField tbDatum;
    
    @FXML ComboBox cbGezinnen;
    @FXML TextField tfGezinNr;
    @FXML TextField tfOuder1;
    @FXML TextField tfOuder2;
    @FXML TextField tfHuwDatum;
    @FXML TextField tfScheidingsdatum;
    @FXML ListView lvKinderen;
    @FXML TextField tfVoornamenInvoer;
    @FXML TextField tfTusenvoegselInvoer;
    @FXML TextField tfAchternaamInvoer;
    @FXML ComboBox cbGeslachtInvoer;
    @FXML TextField tfGebDatumInvoer;
    @FXML TextField tfGebPlaatsInvoer;
    @FXML ComboBox cbOuderlijkGezinInvoer;
    @FXML Button btOKPersoonInvoer;
    @FXML Button btCancelPersoonInvoer;

    //opgave 4
    private boolean withDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboboxes();
        withDatabase = false;
    }

    private void initComboboxes() {
        //todo opgave 3
        ObservableList<Persoon> personen = getAdministratie().getPersonen();
        this.cbPersonen.setItems(personen);
        ObservableList<Gezin> gezinnen = getAdministratie().getGezinnen();
        this.cbGezinnen.setItems(gezinnen);
        this.cbGeslachtInvoer.getItems().setAll((Object[])Geslacht.values());
        
        this.cbOuder1Invoer.setItems(personen);
        this.cbOuder2Invoer.setItems(personen);
        this.cbOuderlijkGezin.setItems(gezinnen);
        cbOuderlijkGezinInvoer.setItems(gezinnen);
    }

    public void selectPersoon(Event evt) {
        Persoon persoon = (Persoon) cbPersonen.getSelectionModel().getSelectedItem();
        showPersoon(persoon);
    }

    private void showPersoon(Persoon persoon) {
        if (persoon == null) {
            clearTabPersoon();
        } else {
            tfPersoonNr.setText(persoon.getNr() + "");
            tfVoornamen.setText(persoon.getVoornamen());
            tfTussenvoegsel.setText(persoon.getTussenvoegsel());
            tfAchternaam.setText(persoon.getAchternaam());
            tfGeslacht.setText(persoon.getGeslacht().toString());
            tfGebDatum.setText(StringUtilities.datumString(persoon.getGebDat()));
            tfGebPlaats.setText(persoon.getGebPlaats());
            if (persoon.getOuderlijkGezin() != null) {
                cbOuderlijkGezin.getSelectionModel().select(persoon.getOuderlijkGezin());
            } else {
                cbOuderlijkGezin.getSelectionModel().clearSelection();
            }

            //todo opgave 3
            lvAlsOuderBetrokkenBij.setItems(persoon.getAlsOuderBetrokkenIn());
        }
    }

    public void setOuders(Event evt) {
        if (tfPersoonNr.getText().isEmpty()) {
            return;
        }
        Gezin ouderlijkGezin = (Gezin) cbOuderlijkGezin.getSelectionModel().getSelectedItem();
        if (ouderlijkGezin == null) {
            return;
        }

        int nr = Integer.parseInt(tfPersoonNr.getText());
        Persoon p = getAdministratie().getPersoon(nr);
        getAdministratie().setOuders(p, ouderlijkGezin);
    }

    public void selectGezin(Event evt) {
        // todo opgave 3
        Gezin gezin = (Gezin) cbGezinnen.getSelectionModel().getSelectedItem();
        showGezin(gezin);
    }

    private void showGezin(Gezin gezin) {
        // todo opgave 3
        if (gezin == null) {
            clearTabGezin();
        } else {
            tfGezinNr.setText(Integer.toString(gezin.getNr()));
            tfOuder1.setText(gezin.getOuder1().toString());
            
            if (gezin.getOuder2() == null) {
                tfOuder2.setText("");
            } else{
                tfOuder2.setText(gezin.getOuder2().toString());
            }
            
            if (gezin.isOngehuwd()) {
                tfHuwDatum.setText("");
            } else {
                tfHuwDatum.setText(gezin.getHuwelijksdatum().toString());
            }
            
            if (gezin.getScheidingsdatum() == null) {
                tfScheidingsdatum.setText("");
            } else {
                tfScheidingsdatum.setText(gezin.getScheidingsdatum().toString());
            }
            
            lvKinderen.setItems(gezin.getKinderen());
        }     
    }

    public void setHuwdatum(Event evt) {
        // todo opgave 3
        try {
            Calendar nieuwehuwelijksdatum = StringUtilities.datum(tfHuwDatum.getText());
            int nr = Integer.parseInt(tfGezinNr.getText());
            Gezin gezin = getAdministratie().getGezin(nr);
            boolean setScheiding = getAdministratie().setHuwelijk(gezin, nieuwehuwelijksdatum);
            
            if (!setScheiding) {
                showDialog("Warning", "huwelijksdatum is niet goed!");
            }
        } catch (IllegalArgumentException e) {
            showDialog("Warning", "crash!");
        }
    }

    public void setScheidingsdatum(Event evt) {
        // todo opgave 3
        try {
            Calendar nieuweScheidingsDatum = StringUtilities.datum(tfScheidingsdatum.getText());
            int nr = Integer.parseInt(tfGezinNr.getText());
            Gezin gezin = getAdministratie().getGezin(nr);
            boolean setScheiding = getAdministratie().setScheiding(gezin, nieuweScheidingsDatum);

            if (!setScheiding) {
                showDialog("Warning", "scheidingsdatum is niet goed!");
            }
        } catch (IllegalArgumentException e) {
            showDialog("Warning", "crash!");
        }

    }

    public void cancelPersoonInvoer(Event evt) {
        // todo opgave 3
        clearTabPersoon();
    }

    public void okPersoonInvoer(Event evt) {
        // todo opgave 3
        if (tfVoornamenInvoer.getText() == null || "".equals(tfVoornamenInvoer.getText())){
            showDialog("Warning", "voer voornamen in");
            return;
        }
        
        if (tfTusenvoegselInvoer.getText() == null || "".equals(tfTusenvoegselInvoer.getText())){
            showDialog("Warning", "voer tussenvoegsel(s) in");
            return;
        }
        
        if (tfAchternaamInvoer.getText() == null || "".equals(tfAchternaamInvoer.getText())){
            showDialog("Warning", "voer achternaam in");
            return;
        }
        
        if (tfGebDatumInvoer.getText() == null || "".equals(tfGebDatumInvoer.getText())){
            showDialog("Warning", "voer geboortedatum in");
            return;
        }
        
        if (tfGebPlaatsInvoer.getText() == null || "".equals(tfGebPlaatsInvoer.getText())){
            showDialog("Warning", "voer geboorteplaats in");
            return;
        }
        
        if (cbGeslachtInvoer.getSelectionModel().isEmpty()) {
            showDialog("Warning", "kies een geslacht!");
            return;
        }
        
        String[] voornamen = tfVoornamenInvoer.getText().split(" ");
        
        if (cbOuderlijkGezinInvoer == null) {
            getAdministratie().addPersoon((Geslacht)cbGeslachtInvoer.getSelectionModel().getSelectedItem(), voornamen, tfAchternaamInvoer.getText(), tfTusenvoegselInvoer.getText(), StringUtilities.datum(tfGebDatumInvoer.getText()), tfGebPlaatsInvoer.getText(), null);
        } else {
            getAdministratie().addPersoon((Geslacht)cbGeslachtInvoer.getSelectionModel().getSelectedItem(), voornamen, tfAchternaamInvoer.getText(), tfTusenvoegselInvoer.getText(), StringUtilities.datum(tfGebDatumInvoer.getText()), tfGebPlaatsInvoer.getText(), (Gezin)cbOuderlijkGezinInvoer.getSelectionModel().getSelectedItem());
        }
        
        clearTabPersoonInvoer();
    }

    public void okGezinInvoer(Event evt) {
        Persoon ouder1 = (Persoon) cbOuder1Invoer.getSelectionModel().getSelectedItem();
        if (ouder1 == null) {
            showDialog("Warning", "eerste ouder is niet ingevoerd");
            return;
        }
        Persoon ouder2 = (Persoon) cbOuder2Invoer.getSelectionModel().getSelectedItem();
        Calendar huwdatum;
        try {
            huwdatum = StringUtilities.datum(tfHuwelijkInvoer.getText());
        } catch (IllegalArgumentException exc) {
            showDialog("Warning", "huwelijksdatum :" + exc.getMessage());
            return;
        }
        Gezin g;
        if (huwdatum != null) {
            g = getAdministratie().addHuwelijk(ouder1, ouder2, huwdatum);
            if (g == null) {
                showDialog("Warning", "Invoer huwelijk is niet geaccepteerd");
            } else {
                Calendar scheidingsdatum;
                try {
                    scheidingsdatum = StringUtilities.datum(tfScheidingInvoer.getText());
                    getAdministratie().setScheiding(g, scheidingsdatum);
                } catch (IllegalArgumentException exc) {
                    showDialog("Warning", "scheidingsdatum :" + exc.getMessage());
                }
            }
        } else {
            g = getAdministratie().addOngehuwdGezin(ouder1, ouder2);
            if (g == null) {
                showDialog("Warning", "Invoer ongehuwd gezin is niet geaccepteerd");
            }
        }

        clearTabGezinInvoer();
    }

    public void cancelGezinInvoer(Event evt) {
        clearTabGezinInvoer();
    }

    
    public void showStamboom(Event evt) {
        // todo opgave 3
        Persoon prs = (Persoon)cbPersonen.getSelectionModel().getSelectedItem();
        
        showDialog("", prs.stamboomAlsString());
    }

    public void createEmptyStamboom(Event evt) {
        this.clearAdministratie();
        clearTabs();
        initComboboxes();
    }

    
    public void openStamboom(Event evt) {
        // todo opgave 3
        try {
            if (withDatabase) {
                loadFromDatabase();
            } else {
                FileChooser filechooser = new FileChooser();
                File file = filechooser.showOpenDialog(getStage());

                if (file != null) {
                    deserialize(file);
                }
            }
        } catch (IOException e) {
            showDialog("exception", e.getMessage());
        } 
    }

    
    public void saveStamboom(Event evt) {
        // todo opgave 3
        try {
            if (withDatabase) {
                saveToDatabase();
            } else {
                FileChooser filechooser = new FileChooser();
                File file = filechooser.showSaveDialog(getStage());

                if (file != null) {
                    serialize(file);
                }
            }
        } catch (IOException e) {
            showDialog("exception", e.getMessage());
        }
    }

    
    public void closeApplication(Event evt) {
        if (withDatabase) {          
            try {
                saveToDatabase();
            } catch (IOException ex) {
                showDialog("warning", "wasn't able to save to database");
            }
        } else {
            saveStamboom(evt);
        }
        getStage().close();
    }

   
    public void configureStorage(Event evt) {
        withDatabase = cmDatabase.isSelected();
        showDialog("notification", cmDatabase.isSelected() ? "now using database" : "now using storage");
    }

 
    public void selectTab(Event evt) {
        Object source = evt.getSource();
        if (source == tabPersoon) {
            clearTabPersoon();
        } else if (source == tabGezin) {
            clearTabGezin();
        } else if (source == tabPersoonInvoer) {
            clearTabPersoonInvoer();
        } else if (source == tabGezinInvoer) {
            clearTabGezinInvoer();
        }
    }

    private void clearTabs() {
        clearTabPersoon();
        clearTabPersoonInvoer();
        clearTabGezin();
        clearTabGezinInvoer();
    }

    
    private void clearTabPersoonInvoer() {
        //todo opgave 3
        tfVoornamenInvoer.clear();
        tfTusenvoegselInvoer.clear();
        tfAchternaamInvoer.clear();
        cbGeslachtInvoer.getSelectionModel().clearSelection();
        tfGebDatumInvoer.clear();
        tfGebPlaatsInvoer.clear();
        cbOuderlijkGezinInvoer.getSelectionModel().clearSelection();  
    }

    
    private void clearTabGezinInvoer() {
        //todo opgave 3
        cbOuder1Invoer.getSelectionModel().clearSelection();
        cbOuder2Invoer.getSelectionModel().clearSelection();
        tfHuwelijkInvoer.clear();
        tfScheidingInvoer.clear();

    }

    private void clearTabPersoon() {
        cbPersonen.getSelectionModel().clearSelection();
        tfPersoonNr.clear();
        tfVoornamen.clear();
        tfTussenvoegsel.clear();
        tfAchternaam.clear();
        tfGeslacht.clear();
        tfGebDatum.clear();
        tfGebPlaats.clear();
        cbOuderlijkGezin.getSelectionModel().clearSelection();
        lvAlsOuderBetrokkenBij.setItems(FXCollections.emptyObservableList());
        
    }

    
    private void clearTabGezin() {
        // todo opgave 3
        tfGezinNr.clear();
        tfOuder1.clear();
        tfOuder2.clear();
        tfHuwDatum.clear();
        tfScheidingsdatum.clear();
        lvKinderen.getSelectionModel().clearSelection();       
    }

    private void showDialog(String type, String message) {
        Stage myDialog = new Dialog(getStage(), type, message);
        myDialog.show();
    }

    private Stage getStage() {
        return (Stage) menuBar.getScene().getWindow();
    }

}
