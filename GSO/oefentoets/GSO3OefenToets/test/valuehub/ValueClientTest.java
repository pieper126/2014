/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package valuehub;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 
 */
public class ValueClientTest {
    
    private ValueClient vc1,vc2;
    
    public ValueClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        vc1=new ValueClient();
        vc2=new ValueClient();
        ValueHub vh=ValueHub.getInstance();
        vh.addValueListener(vc1);
        vh.addValueListener(vc2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of waarden goed worden toegevoegd en gewijzigd.
     */
    @Test
    public void testSetValue() {
        
    }
    
    /**
     * Test of waarden worden doorgeven via de hub.
     */
    @Test
    public void testValueBroadCast() {

    }
    
}
