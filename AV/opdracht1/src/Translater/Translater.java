package Translater;

import Nodes.*;
import java.util.ArrayList;
import java.util.List;

public class Translater {
    
    String trimmedEquation;
    String CurrentEquation;
    String Length;
    
    
    public List<Node> TranslateEquationStringToNodes(String equation){
        trimmedEquation = equation.replaceAll("\\s+", "");
        
        ArrayList<Node> nodes = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            
        }

        
        return null;
    }
    
    public Equation StringToEquation(String equation){
        return new Equation(equation);
    }
    
    public static Node Parse(String equation){
        String trimmedEquation = equation.replaceAll("\\s+", "");
        return Parser.Parse(trimmedEquation);
    }
}