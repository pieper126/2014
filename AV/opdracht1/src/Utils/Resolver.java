/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.*;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

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

        // sorts the collection by using the rules of importance
        currentCollection.sort(new TableauRuleComparator());

        Node mainNode = currentCollection.get(0);

        ArrayList<Node> collections = currentCollection;

        // stops the node from being checked twice
        collections.remove(mainNode);

        Node sideA = null;
        Node sideB = null;

        ArrayList<Node> contradiction;

        switch (mainNode.getClass().getTypeName()) {
            case "Nodes.Negation":
                Node child = ((Negation) mainNode).getSideA();

                if (child.getClass().getTypeName().equals("Nodes.Negation")) {
                    sideA = ((Negation) child).getSideA();

                    if (!collections.contains(sideA)) {

                        contradiction = checkContradiction(collections, createsNodeArrayListWithEnetries(sideA));

                        if (contradiction != null) {
                            returnValue.add(contradiction);
                        }

                        collections.add(sideA);
                    }

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Disjunction")) {
                    sideA = new Negation(((Disjuction) child).getSideA());
                    sideB = new Negation(((Disjuction) child).getSideB());

                    if (!collections.contains(sideA)) {

                        contradiction = checkContradiction(collections, createsNodeArrayListWithEnetries(sideA));

                        if (contradiction != null) {
                            returnValue.add(contradiction);
                        }

                        collections.add(sideA);
                    }

                    if (!collections.contains(sideB)) {

                        contradiction = checkContradiction(collections, createsNodeArrayListWithEnetries(sideB));

                        if (contradiction != null) {
                            returnValue.add(contradiction);
                        }

                        collections.add(sideB);
                    }

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals("Nodes.Implication")) {
                    sideA = ((Implication) child).getSideA();
                    sideB = new Negation(((Implication) child).getSideB());

                    if (!collections.contains(sideA)) {

                        contradiction = checkContradiction(collections, createsNodeArrayListWithEnetries(sideA));

                        if (contradiction != null) {
                            returnValue.add(contradiction);
                        }

                        collections.add(sideA);
                    }

                    if (!collections.contains(sideB)) {

                        contradiction = checkContradiction(collections, createsNodeArrayListWithEnetries(sideB));

                        if (contradiction != null) {
                            returnValue.add(contradiction);
                        }

                        collections.add(sideB);
                    }

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
                // to do add sideA and sideB structure
                collections.add(((Conjunction) mainNode).getSideA());
                collections.add(((Conjunction) mainNode).getSideB());

                returnValue = resolve(collections);
                break;
            case "Nodes.Disjunction":
                // to do add sideA and sideB structure
                collections.add(((Disjuction) mainNode).getSideA());
                collections.add(((Disjuction) mainNode).getSideB());

                returnValue = resolve(collections);
                break;
            case "Nodes.Implication":
                // to do add sideA and sideB structure
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
                // to do add sideA and sideB structure
                sideA = new Implication(((BiImplication) mainNode).getSideA(), ((BiImplication) mainNode).getSideB());
                sideB = new Implication(((BiImplication) mainNode).getSideB(), ((BiImplication) mainNode).getSideA());

                collections.add(new Conjunction(sideA, sideB));

                returnValue = resolve(collections);
            default:
                // there are no more posibilities to check
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

    private static ArrayList<Node> createsNodeArrayListWithEnetries(Node... ob) {
        ArrayList<Node> returnvalue = new ArrayList<>();

        for (Node node : ob) {
            returnvalue.add(node);
        }

        return returnvalue;
    }
}
