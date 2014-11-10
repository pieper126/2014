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
public class ValueHub {
    
    private final ArrayList<IValueListener> listeners;
    private final ArrayList<NamedValue> values;
    private static ValueHub instance = null;
    
    private ValueHub() {
        listeners=new ArrayList<>();
        values=new ArrayList<>();
    }
    
    public static ValueHub getInstance() {
        if (instance==null) instance=new ValueHub();
        return instance;
    }
    
    public void addValueListener(IValueListener vl) {
        listeners.add(vl);
        for(NamedValue nv:values) vl.valueChanged(nv);
    }
    
    private NamedValue findNamedValue(String name) {
        NamedValue nv=null;
        for(int i=0;nv==null&&i!=values.size();i++) {
            if (values.get(i).name.equals(name)) nv=values.get(i);
        }
        return nv;
    }
    
    public void setValue(NamedValue nv) {
        NamedValue old=findNamedValue(nv.name);
        if (old==null) values.add(nv);
        else old.value=nv.value;
        for(IValueListener vl:listeners) vl.valueChanged(nv);
    }
    
}
