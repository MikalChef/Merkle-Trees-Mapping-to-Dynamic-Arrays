//TODO: Complete java docs and code in missing spots.

/**
 * The class contains information about the node's parent, left child, right child, and the string value it holds.
 */
public class MerkleTreeNode {

    /**
     * This is the parent Node.
     */
    private MerkleTreeNode parent;

    /**
     * this is the left child Node.
     */
    private MerkleTreeNode left;

    /**
     * this is the right child Node.
     */
    private MerkleTreeNode right;

    /**
     * This is str representing the file.
     */
    private String str;

    /**
     * Constructs a new MerkleTreeNode with null parent, left, right and str values.
     */
    public MerkleTreeNode() {
        this.parent = null;
        this.left = null;
        this.right = null;
        this.str = null;

    }


    /**
     * Constructs a Merkle Tree node with the given parameters.
     *
     * @param parent the parent node of this node.
     * @param left   the left child node of this node.
     * @param right  the right child node of this node.
     * @param str    the string value of this node.
     */
    public MerkleTreeNode(MerkleTreeNode parent, MerkleTreeNode left, MerkleTreeNode right, String str) {

        this.parent = parent;
        this.right = right;
        this.left = left;
        this.str = str;

    }

    /**
     * Returns the parent node of this node.
     *
     * @return the parent node of this node.
     */
    public MerkleTreeNode getParent() {
        return parent;
        //return null;

    }

    /**
     * Returns the left child node of this Merkle tree node.
     *
     * @return the left child node of this Merkle tree node.
     */

    public MerkleTreeNode getLeft() {
        return left;
        //return null;
    }

    /**
     * Returns the right child node of this Merkle tree node.
     *
     * @return the right child node of this Merkle tree node.
     */

    public MerkleTreeNode getRight() {
        return right;
        //return null;
    }

    /**
     * Returns the string value stored in this Merkle tree node.
     *
     * @return the string value stored in this Merkle tree node.
     */

    public String getStr() {
        return str;
        //return "";
    }

    /**
     * Sets the parent node of this Merkle tree node.
     *
     * @param parent the Merkle tree node to set as parent
     * @throws IllegalArgumentException if the parameter is null.
     */

    public void setParent(MerkleTreeNode parent) {

        if (parent == null) {
            throw new IllegalArgumentException("Must insert something");
        } else {
            this.parent = parent;
        }
        //throw IllegalArgumentException for invalid parameters
    }


    /**
     * Sets the left child node of this Merkle tree node.
     *
     * @param left the Merkle tree node to set as the left child of this node.
     * @throws IllegalArgumentException if the given left node is null.
     */

    public void setLeft(MerkleTreeNode left) {
        if (left == null) {
            throw new IllegalArgumentException("Must insert something");
            //throw IllegalArgumentException for invalid parameters
        } else {
            this.left = left;
        }
    }

    /**
     * Sets the right child node of this Merkle tree node.
     *
     * @param right the Merkle tree node to be set as the right child.
     * @throws IllegalArgumentException if the input right node is null.
     */
    public void setRight(MerkleTreeNode right) {
        if (right == null) {
            throw new IllegalArgumentException("Must insert something");
        } else {
            this.left = left;
        }
        //throw IllegalArgumentException for invalid parameters
    }

    /**
     * Sets the string value of the MerkleTreeNode.
     *
     * @param str the new string value to set.
     * @throws IllegalArgumentException if the input string is null.
     */
    public void setStr(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Must insert a string");

        } else {
            this.str = str;
        }
    }
    //throw IllegalArgumentException for invalid parameters
}
        
