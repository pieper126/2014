/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.*;
import java.util.ArrayList;

/**
 *
 * @author stijn
 */
public class Resolver {

    public static ArrayList<ArrayList<Node>> resolve(Equation equation) {
        ArrayList<ArrayList<Node>> returnValue = new ArrayList<>();

        Node mainNode = equation.GetMainNode();
        ArrayList<Node> collections = new ArrayList<>();

        switch (mainNode.getClass().getTypeName()) {
            case "Nodes.Negation":
                Node child = ((Negation) mainNode).getSideA();

                if (child.getClass().getTypeName().equals("Nodes.Negation")) {

                    collections.add(((Negation) child).getSideA());

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Disjunction")) {

                    collections.add(new Negation(((Disjuction) child).getSideA()));
                    collections.add(new Negation(((Disjuction) child).getSideB()));

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Implication")) {

                    collections.add(((Implication) child).getSideA());
                    collections.add(new Negation(((Implication) child).getSideB()));

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Conjunction")) {
                    // split the equation into two collections
                    ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
                    ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();

                    // because this is the first run we can clear the collection
                    collections.clear();

                    collectionA.add(new Negation(((Implication) child).getSideA()));
                    collectionB.add(new Negation(((Implication) child).getSideB()));

                    returnValue = resolve(collectionA);

                    if (returnValue == null) {
                        return null;
                    }

                    returnValue.addAll(resolve(collectionB));
                }

                break;
            case "Nodes.Conjunction":
                collections.add(((Conjunction) mainNode).getSideA());
                collections.add(((Conjunction) mainNode).getSideB());

                returnValue = resolve(collections);
                break;
            case "Nodes.Disjunction":
                collections.add(((Disjuction) mainNode).getSideA());
                collections.add(((Disjuction) mainNode).getSideB());

                returnValue = resolve(collections);
                break;
            case "Nodes.Implication":
                // split the equation into two collections
                ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
                ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();

                // because this is the first run we can clear the collection
                collections.clear();

                collectionA.add(new Negation(((Implication) mainNode).getSideA()));
                collectionB.add(((Implication) mainNode).getSideB());

                returnValue = resolve(collectionA);

                if (returnValue == null) {
                    return null;
                }

                returnValue.addAll(resolve(collectionB));
                break;
            case "Nodes.BiImplication":
                Node sideA = new Implication(((BiImplication) mainNode).getSideA(), ((BiImplication) mainNode).getSideB());
                Node sideB = new Implication(((BiImplication) mainNode).getSideB(), ((BiImplication) mainNode).getSideA());

                collections.add(new Conjunction(sideA, sideB));

                returnValue = resolve(collections);
            default:
                // geen tegen mogelijkheid meer mogelijk, dus is het niet meer nodig om deze te checken
                return null;
        }

        return returnValue;
    }

    private static ArrayList<ArrayList<Node>> resolve(ArrayList<Node> currentCollection) {
        ArrayList<ArrayList<Node>> returnValue = new ArrayList<>();

        Node mainNode = null;
        
        for (Node node : currentCollection) {
            if (!node.getClass().getTypeName().equals("Nodes.AbstractVariable")) {
                if (node.getClass().getTypeName().equals("Nodes.Negation") && !((Negation)node).getSideA().getClass().getTypeName().equals("Nodes.AbstractVariable")) {
                    break;
                }

            }
        }
        
        ArrayList<Node> collections = new ArrayList<>();

        switch (mainNode.getClass().getTypeName()) {
            case "Nodes.Negation":
                Node child = ((Negation) mainNode).getSideA();

                if (child.getClass().getTypeName().equals("Nodes.Negation")) {

                    collections.add(((Negation) child).getSideA());

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Disjunction")) {

                    collections.add(new Negation(((Disjuction) child).getSideA()));
                    collections.add(new Negation(((Disjuction) child).getSideB()));

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Implication")) {

                    collections.add(((Implication) child).getSideA());
                    collections.add(new Negation(((Implication) child).getSideB()));

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Conjunction")) {
                    // split the equation into two collections
                    ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
                    ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();

                    // because this is the first run we can clear the collection
                    collections.clear();

                    collectionA.add(new Negation(((Implication) child).getSideA()));
                    collectionB.add(new Negation(((Implication) child).getSideB()));

                    returnValue = resolve(collectionA);

                    if (returnValue == null) {
                        return null;
                    }

                    returnValue.addAll(resolve(collectionB));
                }

                break;
            case "Nodes.Conjunction":
                collections.add(((Conjunction) mainNode).getSideA());
                collections.add(((Conjunction) mainNode).getSideB());

                returnValue = resolve(collections);
                break;
            case "Nodes.Disjunction":
                collections.add(((Disjuction) mainNode).getSideA());
                collections.add(((Disjuction) mainNode).getSideB());

                returnValue = resolve(collections);
                break;
            case "Nodes.Implication":
                // split the equation into two collections
                ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
                ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();

                // because this is the first run we can clear the collection
                collections.clear();

                collectionA.add(new Negation(((Implication) mainNode).getSideA()));
                collectionB.add(((Implication) mainNode).getSideB());

                returnValue = resolve(collectionA);

                if (returnValue == null) {
                    return null;
                }

                returnValue.addAll(resolve(collectionB));
                break;
            case "Nodes.BiImplication":
                Node sideA = new Implication(((BiImplication) mainNode).getSideA(), ((BiImplication) mainNode).getSideB());
                Node sideB = new Implication(((BiImplication) mainNode).getSideB(), ((BiImplication) mainNode).getSideA());

                collections.add(new Conjunction(sideA, sideB));

                returnValue = resolve(collections);
            default:
                // geen tegen mogelijkheid meer mogelijk, dus is het niet meer nodig om deze te checken
                return null;
        }

        return returnValue;
    }

    private static ArrayList<Node> checkContradiction(ArrayList<Node> collection, ArrayList<Node> toCheck) {
        for (Node nodeToCheck : toCheck) {
            for (Node nodeUsedToCheck : collection) {
                if (nodeToCheck.getClass().getTypeName().equals("Nodes.Negation")) {
                    if (((Negation) nodeToCheck).equals(nodeUsedToCheck)) {
                        ArrayList<Node> returnValue = new ArrayList<>();
                        returnValue.add(nodeToCheck);
                        returnValue.add(nodeUsedToCheck);
                        return returnValue;
                    }
                } else if (nodeUsedToCheck.getClass().getTypeName().equals("Nodes.Negation")) {
                    if (((Negation) nodeUsedToCheck).equals(nodeToCheck)) {
                        ArrayList<Node> returnValue = new ArrayList<>();
                        returnValue.add(nodeToCheck);
                        returnValue.add(nodeUsedToCheck);
                        return returnValue;
                    }
                }
            }
        }

        return null;
    }
}
