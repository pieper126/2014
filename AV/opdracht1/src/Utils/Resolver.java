/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.*;
import Utils.*;
import java.util.ArrayList;

/**
 *
 * @author stijn
 */
public class Resolver {

    /**
     * checks equality using Tablue prove
     * @param equation
     * @param equation1
     * @return it returns null if they are not equal, if they are it shows how they are equal
     */
    public static ArrayList<ArrayList<Node>> resolve(Equation equation, Equation equation1) {
//        ArrayList<ArrayList<Node>> returnValue = new ArrayList<>();
//
//        Node mainNode = equation.GetMainNode();
//        ArrayList<Node> collections = new ArrayList<>();
//
//        switch (mainNode.getClass().getTypeName()) {
//            case "Nodes.Negation":
//                Node child = ((Negation) mainNode).getSideA();
//
//                if (child.getClass().getTypeName().equals("Nodes.Negation")) {
//
//                    collections.add(((Negation) child).getSideA());
//
//                    returnValue = resolve(collections);
//
//                } else if (child.getClass().getTypeName().equals("Nodes.Disjunction")) {
//
//                    collections.add(new Negation(((Disjuction) child).getSideA()));
//                    collections.add(new Negation(((Disjuction) child).getSideB()));
//
//                    returnValue = resolve(collections);
//
//                } else if (child.getClass().getTypeName().equals("Nodes.Implication")) {
//
//                    collections.add(((Implication) child).getSideA());
//                    collections.add(new Negation(((Implication) child).getSideB()));
//
//                    returnValue = resolve(collections);
//
//                } else if (child.getClass().getTypeName().equals("Nodes.Conjunction")) {
//                    // split the equation into two collections
//                    ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
//                    ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();
//
//                    // because this is the first run we can clear the collection
//                    collections.clear();
//
//                    collectionA.add(new Negation(((Conjunction) child).getSideA()));
//                    collectionB.add(new Negation(((Conjunction) child).getSideB()));
//
//                    returnValue = resolve(collectionA);
//
//                    if (returnValue == null) {
//                        return null;
//                    }
//
//                    ArrayList<ArrayList<Node>> resolveB = resolve(collectionB);
//
//                    if (resolveB == null) {
//                        return null;
//                    }
//
//                    returnValue.addAll(resolveB);
//                }
//
//                break;
//            case "Nodes.Conjunction":
//                collections.add(((Conjunction) mainNode).getSideA());
//                collections.add(((Conjunction) mainNode).getSideB());
//
//                returnValue = resolve(collections);
//                break;
//            case "Nodes.Disjunction":
//                // split the equation into two collections
//                ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
//                ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();
//
//                // because this is the first run we can clear the collection
//                collections.clear();
//
//                collectionA.add(((Disjuction) mainNode).getSideA());
//                collectionB.add(((Disjuction) mainNode).getSideB());
//
//                returnValue = resolve(collectionA);
//
//                if (returnValue == null) {
//                    return null;
//                }
//
//                ArrayList<ArrayList<Node>> resolveB = resolve(collectionB);
//
//                if (resolveB == null) {
//                    return null;
//                }
//
//                returnValue.addAll(resolveB);
//
//                break;
//            case "Nodes.Implication":
//                // split the equation into two collections
//                collectionA = (ArrayList<Node>) collections.clone();
//                collectionB = (ArrayList<Node>) collections.clone();
//
//                // because this is the first run we can clear the collection
//                collections.clear();
//
//                collectionA.add(new Negation(((Implication) mainNode).getSideA()));
//                collectionB.add(((Implication) mainNode).getSideB());
//
//                returnValue = resolve(collectionA);
//
//                if (returnValue == null) {
//                    return null;
//                }
//
//                resolveB = resolve(collectionB);
//
//                if (resolveB == null) {
//                    return null;
//                }
//
//                returnValue.addAll(resolveB);
//
//                break;
//            case "Nodes.BiImplication":
//                Node sideA = new Implication(((BiImplication) mainNode).getSideA(), ((BiImplication) mainNode).getSideB());
//                Node sideB = new Implication(((BiImplication) mainNode).getSideB(), ((BiImplication) mainNode).getSideA());
//
//                collections.add(new Conjunction(sideA, sideB));
//
//                returnValue = resolve(collections);
//            default:
//                // geen tegen mogelijkheid meer mogelijk, dus is het niet meer nodig om deze te checken
//                return null;
//        }

        return resolve(createsNodeArrayListWithEnetries(new Negation(new Implication(equation.GetMainNode(), equation1.GetMainNode()))));
    }

    /**
     * 
     * @param currentCollection
     * @return 
     */
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

        switch (mainNode.getClass().getTypeName()) {
            case NodeTypenames.NEGATION:
                Node child = ((Negation) mainNode).getSideA();

                if (child.getClass().getTypeName().equals(NodeTypenames.NEGATION)) {
                    sideA = ((Negation) child).getSideA();

                    // checks if it isn't already in the collection
                    if (checkCollection(collections, sideA, returnValue)) {
                        break;
                    }

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals(NodeTypenames.DISJUNCTION)) {
                    sideA = new Negation(((Disjuction) child).getSideA());
                    sideB = new Negation(((Disjuction) child).getSideB());

                    // checks if it isn't already in the collection
                    if (checkCollection(collections, sideA, returnValue)) {
                        break;
                    }

                    // checks if it isn't already in the collection
                    if (checkCollection(collections, sideB, returnValue)) {
                        break;
                    }

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals(NodeTypenames.IMPLICATION)) {
                    sideA = ((Implication) child).getSideA();
                    sideB = new Negation(((Implication) child).getSideB());

                    // checks if it isn't already in the collection
                    if (checkCollection(collections, sideA, returnValue)) {
                        break;
                    }

                    // checks if it isn't already in the collection
                    if (checkCollection(collections, sideB, returnValue)) {
                        break;
                    }

                    returnValue = resolve(collections);

                } else if (child.getClass().getTypeName().equals(NodeTypenames.CONJUNCTION)) {
                 // translate it to a better workable formula
                 sideA = new Negation(new Conjunction(new Implication(((BiImplication) mainNode).getSideA(), ((BiImplication) mainNode).getSideB()), new Implication(((BiImplication) mainNode).getSideB(), ((BiImplication) mainNode).getSideA())));

                collections.add(sideA);

                returnValue = resolve(collections);
                }

                break;
            case NodeTypenames.CONJUNCTION:
                sideA = ((Conjunction) mainNode).getSideA();
                sideB = ((Conjunction) mainNode).getSideB();

                // checks if it isn't already in the collection
                if (checkCollection(collections, sideA, returnValue)) {
                    break;
                }

                // checks if it isn't already in the collection
                if (checkCollection(collections, sideB, returnValue)) {
                    break;
                }

                returnValue = resolve(collections);
                break;
            case NodeTypenames.DISJUNCTION:
                // split the equation into two collections
                ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
                ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();

                // assign values
                sideA = ((Disjuction) mainNode).getSideA();
                sideB = ((Disjuction) mainNode).getSideB();

                // checks if it isn't already in the collection
                if (checkCollection(collections, sideA, returnValue)) {
                    break;
                }

                // checks if it isn't already in the collection
                if (checkCollection(collections, sideB, returnValue)) {
                    break;
                }

                returnValue = resolve(collectionA);

                // checks if it was able to close the collection of side A
                if (returnValue == null) {
                    return null;
                }

                ArrayList<ArrayList<Node>> resolveB = resolve(collectionB);

                // checks if it was able to close the collection of side B
                if (resolveB == null) {
                    return null;
                }

                returnValue.addAll(resolveB);
                break;
            case NodeTypenames.IMPLICATION:
                // split the equation into two collections
                collectionA = (ArrayList<Node>) collections.clone();
                collectionB = (ArrayList<Node>) collections.clone();

                // assign values
                sideA = new Negation(((Implication) mainNode).getSideA());
                sideB = ((Implication) mainNode).getSideB();

                // checks if it isn't already in the collection
                if (checkCollection(collections, sideA, returnValue)) {
                    break;
                }

                // checks if it isn't already in the collection
                if (checkCollection(collections, sideB, returnValue)) {
                    break;
                }

                returnValue = resolve(collectionA);

                // checks if it was able to close the collection of side A
                if (returnValue == null) {
                    return null;
                }

                resolveB = resolve(collectionB);

                // checks if it was able to close the collection of side B
                if (resolveB == null) {
                    return null;
                }

                returnValue.addAll(resolveB);
                break;
            case NodeTypenames.BIIMPLICATION:
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

    /**
     * checks the collection if there is a contradiction and acts accordingly
     *
     * @param collections
     * @param side
     * @param returnValue
     * @return if there is a contradiction it returns true, and adds the
     * contradiction to the given returnvalue.
     */
    private static boolean checkCollection(ArrayList<Node> collections, Node side, ArrayList<ArrayList<Node>> returnValue) {
        // checks if it isn't already in the collection
        if (!collections.contains(side)) {

            ArrayList<Node> contradiction = checkContradiction(collections, createsNodeArrayListWithEnetries(side));

            if (contradiction != null) {
                returnValue.add(contradiction);
                return true;
            }

            collections.add(side);
        }
        return false;
    }
}
