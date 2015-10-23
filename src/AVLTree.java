/**
 * Description : a AVL tree used as key-value set
 * Author : Linghan Cheung
 * email : 2661301@qq.com
 * License : GNU/GPL
 * Cautions : When you initialize a AVLTree, you should specify the key type and the value type
 * 				like new AVLTree<Iteger, String>(Comparator), the key must be unique. You need to
 * 				define a class which implements  Comparator to determine the order of key type.
 * 			  
 * 			  It is OK to modify this code for your own intent.
 */
import java.util.*;

public class AVLTree<T, V> 
{
	
	private Node<T, V> tree;    // reference of root node
	private Comparator<T> comp;    // Comparator to compare elements of nodes
	
	public AVLTree(Comparator<T> comp) {
		super();
		this.comp = comp;
		this.tree = null;
	}

	/*********************************** insert operation *******************************************************/
	public void insert(T t, V v)
	{
		tree = insertTree(tree, t, v);    // insert to a subtree and return the reference of this subtree
										// this operation is the marvelous point of the algorithm
	}
	
	private Node<T, V> insertTree(Node<T, V> subTree, T t, V v)    // called by public void insert(T t)
	{													// insert to a subtree and return the reference of this subtree
		Node<T, V> ansNode = null;
		if (subTree == null)
		{
			ansNode = new Node<T, V>(t, v);
			ansNode.leftChild = null;
			ansNode.rightChild = null;
			ansNode.height = 0;    // null tree's height is -1
		}
		else if (comp.compare(t, subTree.t) < 0)    // insert to the leftSubTree of subTree
		{
			subTree.leftChild = insertTree(subTree.leftChild, t, v);
			if (getHeight(subTree.leftChild) - getHeight(subTree.rightChild) == 2)    // subtree is the minimum unbalanced subTree
			{
				// singleLeftRotate or doubleLeftRightRorate
				if (getHeight(subTree.leftChild.leftChild) > getHeight(subTree.leftChild.rightChild))
				{
					ansNode = singleLeftRotate(subTree);
				}
				else
				{
					ansNode = doubleLeftRightRotate(subTree);
				}
			}
			else
			{
				// Only the change of structure of tree causes the change of it's height
				subTree.height = 1 + (getHeight(subTree.leftChild) >= getHeight(subTree.rightChild)
						? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
				ansNode = subTree;
			}

		}
		else if (comp.compare(t, subTree.t) > 0)    // insert to the rightSubTree of subTree
		{
			subTree.rightChild = insertTree(subTree.rightChild, t, v);
			if (getHeight(subTree.rightChild) - getHeight(subTree.leftChild) == 2)    // subtree is the minimum unbalanced subTree
			{
				// singleLeftRotate or doubleLeftRightRorate
				if (getHeight(subTree.rightChild.rightChild) > getHeight(subTree.rightChild.leftChild))
				{
					ansNode = singleRightRotate(subTree);
				}
				else
				{
					ansNode = doubleRightLeftRotate(subTree);
				}
			}
			else
			{
				// Only the change of structure of tree causes the change of it's height
				subTree.height = 1 + (getHeight(subTree.leftChild) > getHeight(subTree.rightChild)
						? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
				ansNode = subTree;
			}
		}
		return ansNode;
	}
	/*********************************** insert operation *******************************************************/
	
	/*********************************** rotation operation *******************************************************/
	private Node<T, V> singleLeftRotate(Node<T, V> subTree)
	{
		// rotate
		Node<T, V> ansNode = subTree.leftChild;
		subTree.leftChild = ansNode.rightChild;
		ansNode.rightChild = subTree;
		
		// update height of rotated nodes
		// Only the change of structure of tree causes the change of it's height
		subTree.height = 1 + (getHeight(subTree.leftChild) >= getHeight(subTree.rightChild) 
				? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
		
		ansNode.height = 1 + (getHeight(ansNode.leftChild) >= getHeight(ansNode.rightChild)
				? getHeight(ansNode.leftChild) : getHeight(ansNode.rightChild));
		
		return ansNode;
	}
	
	private Node<T, V> singleRightRotate(Node<T, V> subTree)
	{
		// rotate
		Node<T, V> ansNode = subTree.rightChild;
		subTree.rightChild = ansNode.leftChild;
		ansNode.leftChild = subTree;
		
		// update height of rotated nodes
		// Only the change of structure of tree causes the change of it's height
		subTree.height = 1 + (getHeight(subTree.leftChild) >= getHeight(subTree.rightChild)
				? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
		
		ansNode.height = 1 + (getHeight(ansNode.leftChild) >= getHeight(ansNode.rightChild)
				? getHeight(ansNode.leftChild) : getHeight(ansNode.rightChild));
		
		return ansNode;
		
	}
	
	private Node<T, V> doubleLeftRightRotate(Node<T, V> subTree)
	{
		
		subTree.leftChild = singleRightRotate(subTree.leftChild);
		Node<T, V> ansNode = singleLeftRotate(subTree);
		return ansNode;
	}
	
	private Node<T, V> doubleRightLeftRotate(Node<T, V> subTree)
	{
		subTree.rightChild = singleLeftRotate(subTree.rightChild);
		Node<T, V> ansNode = singleRightRotate(subTree);
		return ansNode;
	}
	/*********************************** rotation operation *******************************************************/
	
	/*********************************** delete T operation *******************************************************/
	public void delete(T delT)
	{
		tree = deleteTree(delT, tree);
	}
	
	private Node<T, V> deleteTree(T delT, Node<T, V> subTree)
	{
		Node<T, V> ansNode = null;
		
		if (subTree == null)
		{
			System.err.println(delT + "doesn't exist.");
			System.err.flush();
			ansNode = subTree;
		}
		else if (comp.compare(delT, subTree.t) < 0)    // delT maybe in the leftSubTree of subTree 
		{
			subTree.leftChild = deleteTree(delT, subTree.leftChild);    // the height of leftSubTree of subTree may decrease
																		// once leftSubTree was return, it is balanced
			
			if (getHeight(subTree.rightChild) - getHeight(subTree.leftChild) >= 2)    // subtree is the minimum unbalanced subTree
			{
				// singleRightRotate or doubleRightLeftRorate
				if (getHeight(subTree.rightChild.rightChild) >= getHeight(subTree.rightChild.leftChild))
				{
					ansNode = singleRightRotate(subTree);
				}
				else
				{
					ansNode = doubleRightLeftRotate(subTree);
				}
			}
			else    // subTree is balanced, but need to update its height
			{
				// Only the change of structure of tree causes the change of it's height
				subTree.height = 1 + (getHeight(subTree.leftChild) >= getHeight(subTree.rightChild)
						? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
				ansNode = subTree;
			}
		}
		else if (comp.compare(delT, subTree.t) > 0)    // delT maybe in the rightSubTree of subTree 
		{
			subTree.rightChild = deleteTree(delT, subTree.rightChild);    // the height of rightSubTree of subTree may decrease
																		  // once rightSubTree was return, it is balanced
			
			if (getHeight(subTree.leftChild) - getHeight(subTree.rightChild) >= 2)    // subtree is the minimum unbalanced subTree
			{
				// singleLeftRotate or doubleLeftRightRotate
				if (getHeight(subTree.leftChild.leftChild) >= getHeight(subTree.leftChild.rightChild))
				{
					ansNode = singleLeftRotate(subTree);
				}
				else
				{
					ansNode = doubleLeftRightRotate(subTree);
				}
			}
			else
			{
				// Only the change of structure of tree causes the change of it's height
				subTree.height = 1 + (getHeight(subTree.leftChild) >= getHeight(subTree.rightChild)
						? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
				ansNode = subTree;
			}
		}
		else if (comp.compare(delT, subTree.t) == 0)
		{
			if (subTree.leftChild == null && subTree.rightChild == null)
			{
				ansNode = null;    // once null is returned, subTree will be recycled anytime
			}
			else if (subTree.leftChild != null && subTree.rightChild != null)
			{
				subTree.t = getMin(subTree.rightChild).t;
				subTree.v = getMin(subTree.rightChild).v;
				subTree.rightChild = deleteTree(subTree.t, subTree.rightChild);    // the height of rightSubTree of subTree may decrease
				  																   // once rightSubTree was return, it is balanced
				if (getHeight(subTree.leftChild) - getHeight(subTree.rightChild) >= 2)    // subtree is the minimum unbalanced subTree
				{
					// singleLeftRotate or doubleLeftRightRotate
					if (getHeight(subTree.leftChild.leftChild) >= getHeight(subTree.leftChild.rightChild))
					{
						ansNode = singleLeftRotate(subTree);
					}
					else
					{
						ansNode = doubleLeftRightRotate(subTree);
					}
				}
				else
				{
					// Only the change of structure of tree causes the change of it's height
					subTree.height = 1 + (getHeight(subTree.leftChild) >= getHeight(subTree.rightChild)
							? getHeight(subTree.leftChild) : getHeight(subTree.rightChild));
					ansNode = subTree;
				}
			}
			else
			{
				if (subTree.leftChild != null && subTree.rightChild == null)
				{
					ansNode = subTree.leftChild;    // once returned, subTree will be recycled anytime
					subTree.leftChild = null;
				}
				else if (subTree.leftChild == null && subTree.rightChild != null)
				{
					ansNode = subTree.rightChild;
					subTree.rightChild = null;
				}
			}
		}
		
		return ansNode;
	}
	/*********************************** delete T operation *******************************************************/
	
	/*********************************** get V operation *******************************************************/
	public V getValue(T t)
	{
		return getValueTree(t, tree);
	}
	
	private V getValueTree(T t, Node<T, V> subTree)
	{
		V ansV = null;
		
		if (subTree == null)
		{
//			System.err.println("Key " + t + " does not exist.");
//			System.err.flush();
			ansV = null;
		}
		else if (comp.compare(t, subTree.t) == 0)
		{
			ansV = subTree.v;
		}
		else if (comp.compare(t, subTree.t) < 0)
		{
			ansV = getValueTree(t, subTree.leftChild);
		}
		else if (comp.compare(t, subTree.t) > 0)
		{
			ansV = getValueTree(t, subTree.rightChild);
		}
		
		return ansV;
	}
	/*********************************** get V operation *******************************************************/
	
	/***************** tree node *******************/
	private class Node<T, V>
	{
		T t;
		V v;
		Node<T, V> leftChild;
		Node<T, V> rightChild;
		int height;
		
		public Node(T t, V v) 
		{
			super();
			this.t = t;
			this.v = v;
		}
	}
	/***************** tree node *******************/
	
	/*****************************utility function*****************************************/
	private int getHeight(Node<T, V> subTree)
	{
		if (subTree == null)    // height of empty tree is -1
		{
			return -1;
		}
		else
		{
			return subTree.height;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		// preroot to show the structure of this tree
		
		traverse(tree);
		
		
		return "-- Pre-root traverse of the current tree.";
	}
	
	private void traverse(Node<T, V> subTree)
	{
		if (subTree == null)
		{
			return;
		}
		else
		{
			System.out.print(subTree.t + " ");
			traverse(subTree.leftChild);
			traverse(subTree.rightChild);
		}
	}
	
	private Node<T, V> getMin(Node<T, V> subTree)
	{
		Node<T, V> ansNode = null;
		
		if (subTree == null)
		{
			System.err.println("In getMin(Node<T, V> subTree), subTree should not be null.");
			System.err.flush();
		}
		else
		{
			if (subTree.leftChild == null)
			{
				ansNode = subTree;
			}
			else
			{
				ansNode = getMin(subTree.leftChild);
			}
		}
		
		return ansNode;
	}
	
	/*****************************utility function*****************************************/
}

