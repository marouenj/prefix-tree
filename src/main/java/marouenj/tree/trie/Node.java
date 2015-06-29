package marouenj.tree.trie;

import java.util.TreeMap;

public class Node<A> {
	
	Character key;
	A val;
	TreeMap<Character, Node<A>> children;
	
	public Node(Character key) {
		this.key = key;
		this.val = null;
		this.children = new TreeMap<>();
	}
}
