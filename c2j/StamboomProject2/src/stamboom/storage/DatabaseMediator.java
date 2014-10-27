/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboom.storage;

import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Properties;
import stamboom.domain.Administratie;
import stamboom.domain.Geslacht;
import stamboom.domain.Gezin;
import stamboom.domain.Persoon;
import stamboom.util.StringUtilities;
import oracle.jdbc.pool.OracleDataSource;

public class DatabaseMediator implements IStorageMediator {

    private Properties props;
    private Connection conn;

    public DatabaseMediator(Properties properties) {
        props = properties;
        configure(props);
    }

    @Override
    public Administratie load() throws IOException {
        //todo opgave 4
//        Administratie admin = new Administratie();
//        try {
//            initConnection();
//            Statement stat = this.conn.createStatement();
//
//            ResultSet rs = stat.executeQuery("SELECT * FROM Personen");
//            ArrayList<Integer> oudersNummers = new ArrayList();
//            while (rs.next()) {
//                String[] datumdelen = rs.getString("Geboortedatum").split("-");
//                GregorianCalendar gebdat = new GregorianCalendar(new Integer(datumdelen[2]).intValue(), new Integer(datumdelen[1]).intValue() - 1, new Integer(datumdelen[0]).intValue());
//
//                String gebplaats = rs.getString("Geboorteplaats");
//                String achternaam = rs.getString("Achternaam");
//                String[] voornamen = rs.getString("Voornamen").split(" ");
//                String tussenvoegsel = rs.getString("Tussenvoegsel");
//                Geslacht geslacht;
//                if (rs.getString("Geslacht").equals("M")) {
//                    geslacht = Geslacht.MAN;
//                } else {
//                    geslacht = Geslacht.VROUW;
//                }
//                oudersNummers.add(Integer.valueOf(rs.getInt("Ouders")));
//                admin.addPersoon(geslacht, voornamen, achternaam, tussenvoegsel, gebdat, gebplaats, null);
//            }
//            rs = stat.executeQuery("SELECT * FROM Gezinnen");
//            while (rs.next()) {
//                int nrOuder1 = rs.getInt("Ouder1");
//                int nrOuder2 = rs.getInt("Ouder2");
//                String huwelijksdatum = rs.getString("Huwelijk");
//                String scheidingsdatum = rs.getString("Scheiding");
//                Persoon ouder1 = admin.getPersoon(nrOuder1);
//                Persoon ouder2 = admin.getPersoon(nrOuder2);
//                if (huwelijksdatum.isEmpty()) {
//                    admin.addOngehuwdGezin(ouder1, ouder2);
//                } else {
//                    Gezin huwelijk = admin.addHuwelijk(ouder1, ouder2, StringUtilities.datum(huwelijksdatum));
//                    if (!scheidingsdatum.isEmpty()) {
//                        admin.setScheiding(huwelijk, StringUtilities.datum(scheidingsdatum));
//                    }
//                }
//            }
//            for (int i = 0; i < oudersNummers.size(); i++) {
//                Gezin ouders = admin.getGezin(((Integer) oudersNummers.get(i)).intValue());
//                Iterator<Persoon> kinderen; 
//            }
//        } catch (SQLException exc) {
//            System.err.println(exc.getMessage());
//        } finally {
//            closeConnection();
//        }
//        return admin;
        return null;
    }

    @Override
    public void save(Administratie admin) throws IOException {
        //todo opgave 4  
        
        ListIterator<Gezin> gezinnen = admin.getGezinnen().listIterator();
        ListIterator<Persoon> personen = admin.getPersonen().listIterator();
        
        try {
            initConnection();
            dropTables();
            createTables();
            
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement("INSERT INTO PERSONEN VALUES (?,?,?,?,?,?,?,?)");
            
            while (personen.hasNext()) {
                Persoon persoon = personen.next();
                
                ps.setInt(1, persoon.getNr());
                ps.setString(2, persoon.getVoornamen());
                ps.setString(3, persoon.getTussenvoegsel());
                ps.setString(4, persoon.getAchternaam());
                
                if (persoon.getGeslacht() == Geslacht.MAN ) {
                     ps.setString(5, "M");
                } else {
                    ps.setString(5, "V");
                }

                ps.setString(6, StringUtilities.datumString(persoon.getGebDat()));
                ps.setString(7, persoon.getGebPlaats());
            }
            
            ps.executeQuery();
            
            ps = (PreparedStatement) conn.prepareStatement("INSERT INTO GEZINEN VALUES (?,?,?,?,?)");
            
            while (gezinnen.hasNext()) {
                Gezin gezin = gezinnen.next();
                
                ps.setInt(1, gezin.getNr());
                ps.setInt(2, gezin.getOuder1().getNr());
                
                if (gezin.getOuder2() == null) {
                    ps.setNull(3, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(3, gezin.getOuder2().getNr());
                }
                
                if (gezin.getHuwelijksdatum() == null) {
                    ps.setNull(4, java.sql.Types.VARCHAR);
                } else {
                    ps.setString(4, StringUtilities.datumString(gezin.getHuwelijksdatum()));
                }
                
                if (gezin.getScheidingsdatum() == null) {
                    ps.setNull(5, java.sql.Types.VARCHAR);
                } else {
                    ps.setString(5, StringUtilities.datumString(gezin.getScheidingsdatum()));
                }
                
                ps.executeUpdate();
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        
    }

    @Override
    public final boolean configure(Properties props) {
        
        this.props = props;

        try {
            initConnection();
            return isCorrectlyConfigured();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            this.props = null;
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Properties config() {
        return props;
    }

    @Override
    public boolean isCorrectlyConfigured() {
        if (props == null) {
            return false;
        }
        if (!props.containsKey("driver")) {
            return false;
        }
        if (!props.containsKey("url")) {
            return false;
        }
        if (!props.containsKey("username")) {
            return false;
        }
        if (!props.containsKey("password")) {
            return false;
        }
        return true;
    }

    private void initConnection() throws SQLException {
        //opgave 4
        

        
        String driver = props.getProperty("driver");
        
        if (driver != null) {
            System.setProperty("jdbc:oracle:thin:", driver);
        }
        
        String url = props.getProperty("driver") + props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        
//        String url = "jdbc:oracle:thin:192.168.15.50:1521:fhictora";
//        String username =  "dbi295793";
//        String password = "IUSCsQWJ11";
//        
        OracleDataSource ods = new OracleDataSource();
        
        ods.setURL(url);
        ods.setUser(username);
        ods.setPassword(password);
        ods.setServiceName("fhictora");
        ods.setDescription("fhictora");
        
        System.out.println(ods.getServiceName() + ods.getURL() + ":  :" +ods.getDescription());
        conn = ods.getConnection();
        
        if (conn == null) {
            throw new SQLException();
        }
    }

    private void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void dropTables(){
        try {
            Statement statement = conn.createStatement();
            
            statement.executeUpdate("DROP TABLE PERSOON CASCADE CONSTRAINTS");
            statement.executeUpdate("DROP TABLE GEZIN CASCADE CONSTRAINTS");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void createTables(){
        try {
            Statement statement = conn.createStatement();
            
            statement.executeUpdate("ALTER TABLE PERSONEN\n"
                    + "ADD CONSTRAINT PERSONEN_FK1 FOREIGN KEY\n"
                    + "(\n"
                    + "  OUDERS \n"
                    + ")\n"
                    + "REFERENCES GEZINNEN\n"
                    + "(\n"
                    + "  GEZINSNUMMER \n"
                    + ")\n"
                    + "ON DELETE CASCADE ENABLECREATE TABLE PERSONEN \n"
                    + "(\n"
                    + "  PERSOONSNUMMER NUMBER(30, 0) NOT NULL \n"
                    + ", ACHTERNAAM VARCHAR2(20 BYTE) NOT NULL \n"
                    + ", VOORNAMEN VARCHAR2(20 BYTE) NOT NULL \n"
                    + ", TUSSENVOEGSEL VARCHAR2(20 BYTE) NOT NULL \n"
                    + ", GEBOORTEDATUM VARCHAR2(20 BYTE) NOT NULL \n"
                    + ", GEBOORTEPLAATS VARCHAR2(20 BYTE) NOT NULL \n"
                    + ", GESLACHT VARCHAR2(20 BYTE) NOT NULL \n"
                    + ", OUDERS NUMBER(30, 0) \n"
                    + ", CONSTRAINT TABLE1_PK PRIMARY KEY \n"
                    + "  (\n"
                    + "    PERSOONSNUMMER \n"
                    + "  )\n"
                    + "  USING INDEX \n"
                    + "  (\n"
                    + "      CREATE UNIQUE INDEX TABLE1_PK ON PERSONEN (PERSOONSNUMMER ASC) \n"
                    + "      LOGGING \n"
                    + "      TABLESPACE USERS \n"
                    + "      PCTFREE 10 \n"
                    + "      INITRANS 2 \n"
                    + "      STORAGE \n"
                    + "      ( \n"
                    + "        INITIAL 65536 \n"
                    + "        NEXT 1048576 \n"
                    + "        MINEXTENTS 1 \n"
                    + "        MAXEXTENTS UNLIMITED \n"
                    + "        BUFFER_POOL DEFAULT \n"
                    + "      ) \n"
                    + "      NOPARALLEL \n"
                    + "  )\n"
                    + "  ENABLE \n"
                    + ") \n"
                    + "LOGGING \n"
                    + "TABLESPACE USERS \n"
                    + "PCTFREE 10 \n"
                    + "INITRANS 1 \n"
                    + "STORAGE \n"
                    + "( \n"
                    + "  INITIAL 65536 \n"
                    + "  NEXT 1048576 \n"
                    + "  MINEXTENTS 1 \n"
                    + "  MAXEXTENTS UNLIMITED \n"
                    + "  BUFFER_POOL DEFAULT \n"
                    + ") \n"
                    + "NOCOMPRESS");
            statement.executeUpdate("  CREATE TABLE \"DBI295793\".\"GEZINNEN\" \n"
                    + "   (	\"GEZINSNUMMER\" NUMBER(30,0) NOT NULL ENABLE, \n"
                    + "	\"OUDER1\" NUMBER(30,0) NOT NULL ENABLE, \n"
                    + "	\"OUDER2\" NUMBER(30,0), \n"
                    + "	\"HUWELIJKSDATUM\" VARCHAR2(20 BYTE), \n"
                    + "	\"SCHEIDINGDATUM\" VARCHAR2(20 BYTE), \n"
                    + "	 CONSTRAINT \"GEZINNEN_PK\" PRIMARY KEY (\"GEZINSNUMMER\")\n"
                    + "  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS \n"
                    + "  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645\n"
                    + "  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)\n"
                    + "  TABLESPACE \"USERS\"  ENABLE, \n"
                    + "	 CONSTRAINT \"GEZINNEN_FK1\" FOREIGN KEY (\"OUDER2\")\n"
                    + "	  REFERENCES \"DBI295793\".\"PERSONEN\" (\"PERSOONSNUMMER\") ON DELETE CASCADE ENABLE, \n"
                    + "	 CONSTRAINT \"OUDER1_FK\" FOREIGN KEY (\"OUDER1\")\n"
                    + "	  REFERENCES \"DBI295793\".\"PERSONEN\" (\"PERSOONSNUMMER\") ON DELETE CASCADE ENABLE\n"
                    + "   ) SEGMENT CREATION IMMEDIATE \n"
                    + "  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING\n"
                    + "  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645\n"
                    + "  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)\n"
                    + "  TABLESPACE \"USERS\" ;");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

