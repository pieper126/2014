package Automaton;

/**
 * Created by pieper126 on 03-03-15.
 */
public class DefinedLabel extends Label{

    public String definedLabel;

    public DefinedLabel(String definedLabel){
        this.definedLabel = definedLabel;
    }

    @Override
    public boolean equals(Object o) {
        return ((DefinedLabel)o).definedLabel.equals(this.definedLabel);
    }
}
