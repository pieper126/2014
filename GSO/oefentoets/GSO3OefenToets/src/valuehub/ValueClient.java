/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package valuehub;

import java.util.ArrayList;

/**
 *
 * @author M. Franssen
 */
public class ValueClient implements IValueListener {
    
    private final ArrayList<IValueChangedListener> listeners;
    private final ArrayList<NamedValue> values;
    private ValueHub hub;

    public ValueClient() {
        listeners=new ArrayList<>();
        values=new ArrayList<>();
        hub=null;
    }
    
    public void registerWithHub(ValueHub vh) {
        hub=vh;
        hub.addValueListener(this);
        for(NamedValue nv:values) hub.setValue(nv);
    }
    
    // Deze methode is opzettelijk fout: hij houdt geen rekening met de naam van 
    // de waarde waarin je geinteresseerd bent, maar geeft voor alle veranderingen
    // een melding. Ik wilde niet overbodig veel tijd besteden aan deze opzet.
    // Bij het maken van de toets zal dit geen problemen opleveren.
    public void addListener(IValueChangedListener vcl,String name) {
        listeners.add(vcl);
    }
    
    public void removeListener(IValueChangedListener vcl) {
        listeners.remove(vcl);
    }
    
    private NamedValue findNamedValue(String name) {
        NamedValue nv=null;
        for(int i=0;nv==null&&i!=values.size();i++) {
            if (values.get(i).name.equals(name)) nv=values.get(i);
        }
        return nv;
    }
    
    @Override
    public void valueChanged(NamedValue nv) {
        NamedValue old=findNamedValue(nv.name);
        if ((old==null)||(!old.value.equals(nv.value))) {
            if (old==null) values.add(nv);
            else old.value=nv.value;
            for(IValueChangedListener vcl:listeners) vcl.valueChanged(nv.name, nv.value);
        }
    }
    
    public void setValue(String name,String value) {
        NamedValue nv=findNamedValue(name);
        if (nv==null) {
            nv=new NamedValue();
            values.add(nv);
        }
        nv.name=name;
        nv.value=value;
        if (hub!=null) hub.setValue(nv);
        else for(IValueChangedListener vcl:listeners) vcl.valueChanged(nv.name, nv.value);
    }
    
    public String getValue(String name) {
        NamedValue nv=findNamedValue(name);
        if (nv!=null) return nv.value;
        else return null;
    }
    
}
