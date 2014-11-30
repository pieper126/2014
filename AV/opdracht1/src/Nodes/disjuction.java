package Nodes;

public class disjuction extends BinaryNode {

//    public disjuction(Node sideA, Node SideB) {
//        String Equation = "|(" + sideA.toString() + "," + SideB.toString() + ")";
//        super(new Equation(this.Equation), sideA, SideB);
//    }
    
    public disjuction(String currentEquation, Node sideA, Node sideB){
        super(new Equation(currentEquation), sideA, sideB);
    }
    
}