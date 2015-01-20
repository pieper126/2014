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
     * checks equality using Tablue prover
     *
     * @param equation
     * @return it returns null if they are not equal, if they are it shows how
     * they are equal
     */
    public static ArrayList<ArrayList<Node>> resolve(Equation equation) {
        return resolve(createsNodeArrayListWithEnetries(new Negation(equation.GetMainNode())));
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

        printColletion(currentCollection);

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
                    // split the equation into two collections
                    ArrayList<Node> collectionA = (ArrayList<Node>) collections.clone();
                    ArrayList<Node> collectionB = (ArrayList<Node>) collections.clone();

                    // assign values
                    sideA = new Negation(((Conjunction) child).getSideA());
                    sideB = new Negation(((Conjunction) child).getSideB());

                    // checks if it isn't already in the collection
                    if (!checkCollection(collections, sideA, returnValue)) {
                        ArrayList<ArrayList<Node>> solutionOnCollectionA = new ArrayList<>();

                        collectionA.add(sideA);

                        solutionOnCollectionA = resolve(collectionA);

                        // checks if it was able to close the collection of side A
                        if (solutionOnCollectionA == null) {
                            return solutionOnCollectionA;
                        }

                        returnValue.addAll(solutionOnCollectionA);
                    }

                    // checks if it isn't already in the collection
                    if (!checkCollection(collections, sideB, returnValue)) {
                        ArrayList<ArrayList<Node>> solutionOnCollectionB = new ArrayList<>();

                        collectionB.add(sideB);

                        solutionOnCollectionB = resolve(collectionB);

                        // checks if it was able to close the collection of side B
                        if (solutionOnCollectionB == null) {
                            return solutionOnCollectionB;
                        }

                        returnValue.addAll(solutionOnCollectionB);
                    }

                    break;
                } else if (TypeNodeChecker.isBiImplication(child)) {
                    sideA = new Negation(new Implication(((BiImplication) child).getSideA(), ((BiImplication) child).getSideB()));
                    sideB = new Negation(new Implication(((BiImplication) child).getSideB(), ((BiImplication) child).getSideA()));

                    collections.add(new Disjuction(sideA, sideB));

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
                if (!checkCollection(collections, sideA, returnValue)) {
                    ArrayList<ArrayList<Node>> solutionOnCollectionA = new ArrayList<>();

                    collectionA.add(sideA);

                    solutionOnCollectionA = resolve(collectionA);

                    // checks if it was able to close the collection of side A
                    if (solutionOnCollectionA == null) {
                        return solutionOnCollectionA;
                    }

                    returnValue.addAll(solutionOnCollectionA);
                }

                // checks if it isn't already in the collection
                if (!checkCollection(collections, sideB, returnValue)) {
                    ArrayList<ArrayList<Node>> solutionOnCollectionB = new ArrayList<>();

                    collectionB.add(sideB);

                    solutionOnCollectionB = resolve(collectionB);

                    // checks if it was able to close the collection of side B
                    if (solutionOnCollectionB == null) {
                        return solutionOnCollectionB;
                    }

                    returnValue.addAll(solutionOnCollectionB);
                }

                break;
            case NodeTypenames.IMPLICATION:
                // split the equation into two collections
                collectionA = (ArrayList<Node>) collections.clone();
                collectionB = (ArrayList<Node>) collections.clone();

                // assign values
                sideA = new Negation(((Implication) mainNode).getSideA());
                sideB = ((Implication) mainNode).getSideB();

                // checks if it isn't already in the collection
                if (!checkCollection(collections, sideA, returnValue)) {
                    ArrayList<ArrayList<Node>> solutionOnCollectionA = new ArrayList<>();

                    collectionA.add(sideA);

                    solutionOnCollectionA = resolve(collectionA);

                    // checks if it was able to close the collection of side A
                    if (solutionOnCollectionA == null) {
                        return solutionOnCollectionA;
                    }

                    returnValue.addAll(solutionOnCollectionA);
                }

                // checks if it isn't already in the collection
                if (!checkCollection(collections, sideB, returnValue)) {
                    ArrayList<ArrayList<Node>> solutionOnCollectionB = new ArrayList<>();

                    collectionB.add(sideB);

                    solutionOnCollectionB = resolve(collectionB);

                    // checks if it was able to close the collection of side B
                    if (solutionOnCollectionB == null) {
                        return solutionOnCollectionB;
                    }

                    returnValue.addAll(solutionOnCollectionB);
                }

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
                if (TypeNodeChecker.isNegation(nodeToCheck)) {
                    if ((((Negation) nodeToCheck).getSideA()).equals(nodeUsedToCheck)) {
                        ArrayList<Node> returnValue = new ArrayList<>();
                        returnValue.add(nodeToCheck);
                        returnValue.add(nodeUsedToCheck);
                        System.out.println("contradiction: " + nodeToCheck + " + " + nodeUsedToCheck);
                        return returnValue;
                    }
                } else if (TypeNodeChecker.isNegation(nodeUsedToCheck)) {
                    if ((((Negation) nodeUsedToCheck).getSideA()).equals(nodeToCheck)) {
                        ArrayList<Node> returnValue = new ArrayList<>();
                        returnValue.add(nodeToCheck);
                        returnValue.add(nodeUsedToCheck);
                        System.out.println("contradiction: " + nodeToCheck + " + " + nodeUsedToCheck);
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
                System.out.println("");
                return true;
            }

            collections.add(side);
        }
        return false;
    }

    private static void printColletion(ArrayList<Node> collection) {
        System.out.print("new collection:");
        for (Node node : collection) {
            System.out.print(node.toString() + ",");
        }
        System.out.println("");
    }
}
