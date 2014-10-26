package stamboom.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Administratie implements Serializable{

    //************************datavelden*************************************
    private int nextGezinsNr;
    private int nextPersNr;
    
    private List<Persoon> personen;
    private List<Gezin> gezinnen;
    private transient ObservableList<Gezin> observableGezinnen;
    private transient ObservableList<Persoon> observablePersonen;

    //***********************constructoren***********************************
    /**
     * er wordt een administratie gecreeerd met 0 personen en dus 0 gezinnen
     * personen en gezinnen die in de toekomst zullen worden gecreeerd, worden
     * elk opvolgend genummerd vanaf 1
     */
    public Administratie() {
        //todo opgave 1
        personen = new ArrayList<>();
        gezinnen = new ArrayList<>();
        observableGezinnen = FXCollections.observableList(new ArrayList<Gezin>());
        observablePersonen = FXCollections.observableList(new ArrayList<Persoon>());
        nextGezinsNr = 1;
        nextPersNr = 1;
    }

    //**********************methoden****************************************
    /**
     * er wordt een persoon met een gegeven geslacht, met als voornamen vnamen,
     * achternaam anaam, tussenvoegsel tvoegsel, geboortedatum gebdat,
     * geboorteplaats gebplaats en een gegeven ouderlijk gezin gecreeerd; de persoon
     * krijgt een uniek nummer toegewezen de persoon is voortaan ook bij het
     * ouderlijk gezin bekend. Voor de voornamen, achternaam en gebplaats geldt
     * dat de eerste letter naar een hoofdletter en de resterende letters naar
     * een kleine letter zijn geconverteerd; het tussenvoegsel is zo nodig in
     * zijn geheel geconverteerd naar kleine letters; overbodige spaties zijn 
     * verwijderd
     *
     * @param geslacht
     * @param vnamen vnamen.length>0; alle strings zijn niet leeg
     * @param anaam niet leeg
     * @param tvoegsel
     * @param gebdat
     * @param gebplaats niet leeg
     * @param ouderlijkGezin mag de waarde null (=onbekend) hebben
     *
     * @return als de persoon al bekend was (op basis van combinatie van getNaam(),
     * geboorteplaats en geboortedatum), wordt er null geretourneerd, anders de 
     * nieuwe persoon
     */
    public Persoon addPersoon(Geslacht geslacht, String[] vnamen, String anaam,
            String tvoegsel, Calendar gebdat,
            String gebplaats, Gezin ouderlijkGezin) {

        if (vnamen.length == 0) {
            throw new IllegalArgumentException("ten minst 1 voornaam");
        }
        for (String voornaam : vnamen) {
            if (voornaam.trim().isEmpty()) {
                throw new IllegalArgumentException("lege voornaam is niet toegestaan");
            }
        }

        if (anaam.trim().isEmpty()) {
            throw new IllegalArgumentException("lege achternaam is niet toegestaan");
        }

        if (gebplaats.trim().isEmpty()) {
            throw new IllegalArgumentException("lege geboorteplaats is niet toegestaan");
        }

        String[] voornamen = new String[vnamen.length];
        for (int i = 0; i < vnamen.length; i++) {
            voornamen[i] = Character.toUpperCase(vnamen[i].trim().charAt(0)) + vnamen[i].trim().substring(1).toLowerCase();
        }
        
        Persoon returnValue = new Persoon(nextPersNr, voornamen, anaam, tvoegsel, gebdat, gebplaats, geslacht, ouderlijkGezin);
        
        //todo opgave 1
        for (Persoon persoon : personen) {
            if (persoon.getNaam().toLowerCase().equals(returnValue.getNaam().toLowerCase()) && persoon.getGebPlaats().toLowerCase().equals(gebplaats.toLowerCase()) && persoon.getGebDat().getTime().equals(gebdat.getTime())) {
                returnValue = null;
                return null;
            }
        }

        if (ouderlijkGezin != null) {
            ouderlijkGezin.breidUitMet(returnValue);
        }
        
        nextPersNr++;
        observablePersonen.add(returnValue);        
        
        return returnValue;
    }

    /**
     * er wordt, zo mogelijk (zie return) een (kinderloos) ongehuwd gezin met
     * ouder1 en ouder2 als ouders gecreeerd; de huwelijks- en scheidingsdatum
     * zijn onbekend (null); het gezin krijgt een uniek nummer toegewezen; dit
     * gezin wordt ook bij de afzonderlijke ouders geregistreerd;
     *
     * @param ouder1
     * @param ouder2 mag null zijn
     *
     * @return null als ouder1 = ouder2 of als de volgende voorwaarden worden
     * overtreden: 1) een van de ouders is op dit moment getrouwd 2) het koppel
     * uit een ongehuwd gezin kan niet tegelijkertijd als koppel bij een ander
     * ongehuwd gezin betrokken zijn anders het gewenste gezin
     */
    public Gezin addOngehuwdGezin(Persoon ouder1, Persoon ouder2) {
        if (ouder1 == ouder2) {
            return null;
        }

        Calendar nu = Calendar.getInstance();
        if (ouder1.isGetrouwdOp(nu) || (ouder2 != null
                && ouder2.isGetrouwdOp(nu))
                || ongehuwdGezinBestaat(ouder1, ouder2)) {
            return null;
        }

        Gezin gezin = new Gezin(nextGezinsNr, ouder1, ouder2);
        nextGezinsNr++;
        observableGezinnen.add(gezin);

        ouder1.wordtOuderIn(gezin);
        if (ouder2 != null) {
            ouder2.wordtOuderIn(gezin);
        }

        return gezin;
    }

    /**
     * Als het ouderlijk gezin van persoon nog onbekend is dan wordt persoon een
     * kind van ouderlijkGezin en tevens wordt persoon als kind in dat gezin
     * geregistreerd; <br>
     * Als de ouders bij aanroep al bekend zijn, verandert er
     * niets
     *
     * @param persoon
     * @param ouderlijkGezin
     */
    public void setOuders(Persoon persoon, Gezin ouderlijkGezin) {
        persoon.setOuders(ouderlijkGezin);
    }

    /**
     * als de ouders van dit gezin gehuwd zijn en nog niet gescheiden en datum
     * na de huwelijksdatum ligt, wordt dit de scheidingsdatum. Anders gebeurt
     * er niets.
     *
     * @param gezin
     * @param datum
     * @return true als scheiding geaccepteerd, anders false
     */
    public boolean setScheiding(Gezin gezin, Calendar datum) {
        return gezin.setScheiding(datum);
    }

    /**
     * registreert het huwelijk, mits gezin nog geen huwelijk is en beide ouders
     * op deze datum mogen trouwen (pas op: ook de toekomst kan hierbij een rol
     * spelen omdat toekomstige gezinnen eerder zijn geregisteerd)
     *
     * @param gezin
     * @param datum de huwelijksdatum
     * @return false als huwelijk niet mocht worden voltrokken, anders true
     */
    public boolean setHuwelijk(Gezin gezin, Calendar datum) {
        return gezin.setHuwelijk(datum);
    }

    /**
     *
     * @param ouder1
     * @param ouder2
     * @return true als dit koppel (ouder1,ouder2) al een ongehuwd gezin vormt
     */
    boolean ongehuwdGezinBestaat(Persoon ouder1, Persoon ouder2) {
        return ouder1.heeftOngehuwdGezinMet(ouder2) != null;
    }

    /**
     * als er al een ongehuwd gezin voor dit koppel bestaat, wordt het huwelijk
     * voltrokken, anders wordt er zo mogelijk (zie return) een (kinderloos)
     * gehuwd gezin met ouder1 en ouder2 als ouders gecreeerd; de
     * scheidingsdatum is onbekend (null); het gezin krijgt een uniek nummer
     * toegewezen; dit gezin wordt ook bij de afzonderlijke ouders
     * geregistreerd;
     *
     * @param ouder1
     * @param ouder2
     * @param huwdatum
     * @return null als ouder1 = ouder2 of als een van de ouders getrouwd is
     * anders het gehuwde gezin
     */
    public Gezin addHuwelijk(Persoon ouder1, Persoon ouder2, Calendar huwdatum) {
        //todo opgave 1
        if (ouder1 == ouder2) {
            return null;
        }
        
        if (ouder1.kanTrouwenOp(huwdatum) && ouder2.kanTrouwenOp(huwdatum)) {           
        
        for (Gezin gezin : gezinnen) {
            if ((gezin.getOuder1().equals(ouder1) || gezin.getOuder1().equals(ouder2)) && 
                (gezin.getOuder2().equals(ouder1) || gezin.getOuder2().equals(ouder2)) &&
                 gezin.isOngehuwd()) {
                gezin.setHuwelijk(huwdatum);
                return gezin;
            }
            else if ((gezin.getOuder1().equals(ouder1) || gezin.getOuder1().equals(ouder2)) || 
                (gezin.getOuder2().equals(ouder1) || gezin.getOuder2().equals(ouder2)) &&
                 !gezin.isOngehuwd() && gezin.getScheidingsdatum().before(huwdatum)) {
                gezin.setHuwelijk(huwdatum);
                return gezin;
            }
            else if((gezin.getOuder1().equals(ouder1) || gezin.getOuder1().equals(ouder2)) || 
                (gezin.getOuder2().equals(ouder1) || gezin.getOuder2().equals(ouder2)) &&
                 !gezin.isOngehuwd()) {
                return null;
            }
        }
        
        Gezin returnValue = new Gezin(nextGezinsNr, ouder1, ouder2);
        nextGezinsNr++;
        
        returnValue.setHuwelijk(huwdatum);
        
        // voegt gezin toe bij het desbetreffende pesoon
        ouder1.wordtOuderIn(returnValue);
        ouder2.wordtOuderIn(returnValue);
        
        // voegt het toe aan gezinnen
        observableGezinnen.add(returnValue);
        
        return returnValue;
        
        }
        else{
            return null;
        }
    }

    /**
     *
     * @return het aantal geregistreerde personen
     */
    public int aantalGeregistreerdePersonen() {
        return nextPersNr - 1;
    }

    /**
     *
     * @return het aantal geregistreerde gezinnen
     */
    public int aantalGeregistreerdeGezinnen() {
        return nextGezinsNr - 1;
    }

    /**
     *
     * @param nr
     * @return de persoon met nummer nr, als die niet bekend is wordt er null
     * geretourneerd
     */
    public Persoon getPersoon(int nr) {
        //todo opgave 1
        //aanname: er worden geen personen verwijderd
        for (Persoon persoon : personen) {
            if (persoon.getNr() == nr) {
                return persoon;
            }
        }
        
        return null;
    }

    /**
     * @param achternaam
     * @return alle personen met een achternaam gelijk aan de meegegeven
     * achternaam (ongeacht hoofd- en kleine letters)
     */
    public ArrayList<Persoon> getPersonenMetAchternaam(String achternaam) {
        ArrayList<Persoon> returnValue = new ArrayList();
        
        for (Persoon persoon : personen) {
            if (formatAchternaam(persoon.getAchternaam()).equals(formatAchternaam(achternaam))) {
                returnValue.add(persoon);
            }
        }
        return returnValue;
    }

    /**
     *
     * @return de geregistreerde personen
     */
    public ObservableList<Persoon> getPersonen() {
        // todo opgave 1        
        return FXCollections.unmodifiableObservableList(observablePersonen);
    }

    /**
     *
     * @param vnamen
     * @param anaam
     * @param tvoegsel
     * @param gebdat
     * @param gebplaats
     * @return de persoon met dezelfde initialen, tussenvoegsel, achternaam,
     * geboortedatum en -plaats mits bekend (ongeacht hoofd- en kleine letters),
     * anders null
     */
    public Persoon getPersoon(String[] vnamen, String anaam, String tvoegsel,
            Calendar gebdat, String gebplaats) {
        //todo opgave 1
        for (Persoon persoon: personen) {
            if (persoon.getInitialen().equals(convertToInitialen(vnamen)) && persoon.getTussenvoegsel().equals(tvoegsel) && persoon.getAchternaam().equals(formatAchternaam(anaam)) &&
                persoon.getGebDat().equals(gebdat) && persoon.getGebPlaats().equals(formatGeboortePlaats(gebplaats))) {
                return persoon;
            }
        }
        return null;
    }

    /**
     *
     * @return de geregistreerde gezinnen
     */
    public ObservableList<Gezin> getGezinnen() {
        return FXCollections.unmodifiableObservableList(observableGezinnen);      
    }

    /**
     *
     * @param gezinsNr
     * @return het gezin met nummer nr. Als dat niet bekend is wordt er null
     * geretourneerd
     */
    public Gezin getGezin(int gezinsNr) {
        // aanname: er worden geen gezinnen verwijderd
        if (1 <= gezinsNr && 1 <= gezinnen.size()) {
            return gezinnen.get(gezinsNr - 1);
        }
        return null;
    }
    
    private String convertToInitialen(String[] voornamen) {
        //todo opgave 1
        String returnValue = "";

        for (String voornaam : voornamen) {
            returnValue += voornaam.substring(0,1).toUpperCase() + '.';
        }

        return returnValue;
    }
    
    
    private String formatAchternaam(String achternaam) {
        String returnValue = achternaam.toLowerCase();
        return Character.toUpperCase(returnValue.trim().charAt(0)) + returnValue.trim().substring(1).toLowerCase();
    }
    
        private String formatGeboortePlaats(String geboorteplaats) {
        String returnValue = geboorteplaats.toLowerCase();
        return Character.toUpperCase(returnValue.charAt(0)) + returnValue.substring(1).toLowerCase();
    }
        
    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        observablePersonen = FXCollections.observableList(personen);
        observableGezinnen = FXCollections.observableList(gezinnen);
    }
}
