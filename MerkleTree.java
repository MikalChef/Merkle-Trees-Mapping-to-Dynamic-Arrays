//TODO: Complete java docs and code in missing spots.

import java.util.*;

/**
 * This is the class representing the complete MerkleTree.
 */
public class MerkleTree {

    /**
     * the root node of the merkle tree.
     */
    public static MerkleTreeNode root;

    /**
     * the number of files in the merkle tree.
     */
    public static int numberOfFiles;

    /**
     * leaf nodes in the merkle tree.
     */
    public static ArrayList<MerkleTreeNode> leaves;

    {
    }


    /**
     * creates a merkle tree with the array of file names.
     *
     * @param files an array of file names to be in the merkletree.
     * @return the value of the root's hash.
     */
    public String constructMerkleTree(String[] files) {



        if (files == null) {
            throw new IllegalArgumentException();

        }

        //new array list to hold the leaf nodes.
        leaves = new ArrayList<>();
        //sets root node to null
        root = null;
        //length equals to the files in the array.
        numberOfFiles = files.length;

        //each file gets added to a new merkle tree node to be added to the leaves list.
        for (String file : files) {
            leaves.add(new MerkleTreeNode(null, null, null, file));
        }
        //using the list of leaf nodes to make the merkle tree.
        root = buildTree(leaves).get(0);

        //return the root hash of the merkle tree.

        return root.getStr();


        //Task 2: You are required to develop the code for the constructMerkleTree method.
        //Running time complexity of this method: O(n) where n is the number of files (size of the files array)
        //You can assume that the size of the files will be given as 2^n
        //throw IllegalArgumentException for invalid parameters

    }

    /**
     * Builds a merkle tree from the list of leaf nodes.
     *
     * @param children are at the bottom of the merkletree which are the leaf nodes to be used.
     * @return list of parent nodes being regarded as the root of the merkle tree.
     */
    private static ArrayList<MerkleTreeNode> buildTree(ArrayList<MerkleTreeNode> children) {

        // Create a new ArrayList to hold the parent nodes.
        ArrayList<MerkleTreeNode> parentNodes = new ArrayList<>();

        // If the size of the children list is 1, then we have reached the root of the tree and can return it
        if (children.size() == 1) {
            return children;
        }

        // Iterate over the list of children two at a time to create pairs of nodes
        for (int i = 0; i < children.size(); i = i + 2) {
            // Get the left and right child nodes
            MerkleTreeNode leftChild = children.get(i);
            MerkleTreeNode rightChild = children.get(i + 1);

            // Calculate the hash values of the left and right child nodes
            String leftChildStr = leftChild.getStr();
            String rightChildStr = rightChild.getStr();

            // Calculate the hash value of the parent node by concatenating the hash values of its children
            String parentStr = Hashing.cryptHash(leftChildStr + rightChildStr);

            // Create a new parent node with the left and right child nodes as its children and the calculated hash value as its value
            MerkleTreeNode parent = new MerkleTreeNode(null, leftChild, rightChild, parentStr);
            leftChild.setParent(parent);
            rightChild.setParent(parent);

            // Add the parent node to the list of parent nodes.
            parentNodes.add(parent);
        }

        // Recursively call the buildTree method with the list of parent nodes to continue building the tree.
        return buildTree(parentNodes);
    }

    /**
     * Recursively compares two Merkle trees represented by their appropriate ArrayLists of Merkle tree nodes.
     *
     * @param firstChildren  the ArrayList of Merkle tree nodes representing the first tree's children.
     * @param secondChildren the ArrayList of Merkle tree nodes representing the second tree's children.
     * @return true if the two trees are identical, false otherwise.
     */

    private static boolean compareTrees(ArrayList<MerkleTreeNode> firstChildren,
                                        ArrayList<MerkleTreeNode> secondChildren) {
        if (firstChildren.size() == 1) {
            return firstChildren.get(0).getStr().equals(secondChildren.get(0).getStr());
        }

        ArrayList<MerkleTreeNode> firstParents = new ArrayList<>();
        ArrayList<MerkleTreeNode> secondParents = new ArrayList<>();

        for (int i = 0; i < firstChildren.size(); i = i + 2) {
            MerkleTreeNode firstLeftChild = firstChildren.get(i);
            MerkleTreeNode firstRightChild = firstChildren.get(i + 1);

            MerkleTreeNode secondLeftChild = secondChildren.get(i);
            MerkleTreeNode secondRightChild = secondChildren.get(i + 1);

            String firstParentString = Hashing.cryptHash(firstLeftChild.getStr() + firstRightChild.getStr());
            String secondParentString = Hashing.cryptHash(secondLeftChild.getStr() + secondRightChild.getStr());

            if (!firstParentString.equals(secondParentString)) {
                return false;
            }

            MerkleTreeNode firstParent = new MerkleTreeNode(null, firstLeftChild, firstRightChild, firstParentString);
            firstLeftChild.setParent(firstParent);
            firstRightChild.setParent(firstParent);
            firstParents.add(firstParent);

            MerkleTreeNode secondParent = new MerkleTreeNode(null, secondLeftChild, secondRightChild, secondParentString);
            secondLeftChild.setParent(secondParent);
            secondRightChild.setParent(secondParent);
            secondParents.add(secondParent);

        }

        return compareTrees(firstParents, secondParents);
    }

    /**
     * Verifies the integrity of a file by comparing its root value and leaves with the stored root value and leaves.
     * in the Merkle Tree.
     *
     * @param rootValue the root value of the Merkle Tree to be compared with the root value of the file's Merkle Tree.
     * @param fileIndex the index of the file in the leaves list to be compared with the retrieved leaves from the Merkle Tree.
     * @param file      the name of the file to be verified.
     * @return true if the file's root value and leaves match the stored root value and leaves in the Merkle Tree, false otherwise.
     * @throws IllegalArgumentException if the provided file index is out of bounds or the file name is invalid or null.
     */

    public static boolean verifyIntegrity(String rootValue, int fileIndex, String file) {

        if(rootValue==null || fileIndex<0 || fileIndex>= numberOfFiles|| file == null){
            throw new IllegalArgumentException();
        }
        if (!leaves.get(fileIndex).getStr().equals(file)) {
            return false;
        }

        if (!root.getStr().equals(rootValue)) {
            return false;
        }

        ArrayList<MerkleTreeNode> retrievedLeaves = getLeaves();
        return compareTrees(retrievedLeaves, leaves);

        //Task 3: You are required to develop the code for the verifyIntegrity method
        //Running time complexity of this method: O(n)
        //throw IllegalArgumentException for invalid parameters

    }

    /**
     * Recursively retrieves all the leaf nodes from a given list of children of the Merkle tree root node.
     *
     * @param children The list of child nodes of the Merkle tree root node.
     * @return An ArrayList containing all the leaf nodes in the Merkle tree.
     */
    private static ArrayList<MerkleTreeNode> getLeavesFromRoot(ArrayList<MerkleTreeNode> children) {
        if (children.size() == numberOfFiles) {
            return children;
        }

        ArrayList<MerkleTreeNode> nodes = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            MerkleTreeNode node = children.get(i);
            MerkleTreeNode leftNode = node.getLeft();
            MerkleTreeNode rightNode = node.getRight();

            if (leftNode != null) {
                nodes.add(leftNode);
            }
            if (rightNode != null) {
                nodes.add(rightNode);
            }
        }
        return getLeavesFromRoot(nodes);
    }

    /**
     * Retrieves all the leaf nodes in the Merkle tree by calling the recursive helper method getLeavesFromRoot().
     *
     * @return An ArrayList containing all the leaf nodes in the Merkle tree.
     */

    private static ArrayList<MerkleTreeNode> getLeaves() {
        ArrayList<MerkleTreeNode> children = new ArrayList<>();
        children.add(root);
        return getLeavesFromRoot(children);
    }

    /**
     * Swaps the position of two files in the Merkle tree.
     *
     * @param fileIndex1 the index of the first file to be swapped.
     * @param fileIndex2 the index of the second file to be swapped.
     * @return the new root hash after the swap.
     */

    public String swapFile(int fileIndex1, int fileIndex2) {

        if(fileIndex1 < 0 || fileIndex1 >= numberOfFiles || fileIndex2 < 0 || fileIndex2 >= numberOfFiles ) {

            throw new IllegalArgumentException();

        }

        MerkleTreeNode firstNode = leaves.get(fileIndex1);
        leaves.set(fileIndex1, leaves.get(fileIndex2));
        leaves.set(fileIndex2, firstNode);

        root = buildTree(leaves).get(0);
        return root.getStr();


        //Task 4: You are required to develop the code for the swapFile method.
        //Running time complexity of this method: O(n)
        //throw IllegalArgumentException for invalid parameters

    }

    /**
     * Converts the current Merkle Tree into a dynamic Merkle Tree representation.
     * Each node of the Merkle Tree is stored in a level order traversal format, where null is used as a limit.
     * to indicate the end of a level.
     *
     * @return an ArrayList of strings representing the dynamic Merkle Tree.
     */
    public static ArrayList<String> convertToDynamic() {


        //create an empty arraylist to store the dynamic merkle tree.
        ArrayList<String> dynamicMerkleTree = new ArrayList<>();

        //if the root node of the merkle tree is null, return null.
        if (root == null) {
            return null;
        }

        //if the root node doesnt have any children, add its string value to the dynamic merkle tree.
        if (root.getLeft() == null & root.getRight() == null) {
            dynamicMerkleTree.add(root.getStr());
        }

        //initialize a queue to perform depth first traversal of the merkle tree.
        Queue<MerkleTreeNode> merkleTreeNodes = new LinkedList<>();
        merkleTreeNodes.add(root);
        merkleTreeNodes.add(null);


        //while the queue is not empty, iterate through the nodes in the Merkle Tree.
        while (!merkleTreeNodes.isEmpty()) {
            //remove the first node from the queue.
            MerkleTreeNode child = merkleTreeNodes.poll();
            //if the node is not null, add its string value to the dynamic merkle tree
            if (child != null) {
                dynamicMerkleTree.add(child.getStr());
            } else {
                //if the node is null, add a null marker to the queue to mark the end of a level.
                if (!merkleTreeNodes.isEmpty()) {
                    merkleTreeNodes.add(null);
                }
            }


            //if a node has a left child, add it to the queue.
            if (child != null && child.getLeft() != null) {
                merkleTreeNodes.add(child.getLeft());
            }


            //if a node has a right child, add it to the queue.
            if (child != null && child.getRight() != null) {
                merkleTreeNodes.add(child.getRight());
            }

        }


        //Return the Dynamic Merkle Tree.
        return dynamicMerkleTree;


    }

    /**
     * Verifies the integrity of a dynamic Merkle Tree by comparing it with the current state of the tree.
     *
     * @param rootValue     The root value of the Merkle Tree to be verified.
     * @param fileIndex     The index of the file to be verified.
     * @param file          The file to be verified.
     * @param dynamicMerkle The dynamic Merkle Tree to be compared with the current state of the tree.
     * @return True if the dynamic Merkle Tree matches the current state of the tree, false otherwise.
     */
    public static boolean verifyIntegrityDynamic(String rootValue, int fileIndex, String file, ArrayList<String> dynamicMerkle) {

        if(rootValue == null || fileIndex < 0 || fileIndex>=numberOfFiles||file==null||dynamicMerkle==null ){
            throw new IllegalArgumentException();
        }
        ArrayList<String> currentDynamicTree = convertToDynamic();
        if (currentDynamicTree.size() != dynamicMerkle.size()) {
            return false;
        }

        if (!dynamicMerkle.get(0).equals(rootValue)) {
            return false;
        }

        String fileFromCurrentTree = currentDynamicTree.get(leaves.size() - 1 + fileIndex);

        if (!fileFromCurrentTree.equals(file)) {
            return false;
        }

        for (int i = 0; i < dynamicMerkle.size(); i++) {
            if (!currentDynamicTree.get(i).equals(dynamicMerkle.get(i))) {
                return false;
            }
        }

        return true;


    }
}
