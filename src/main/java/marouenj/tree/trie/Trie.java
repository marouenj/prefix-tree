package marouenj.tree.trie;

/**
 * A prefix tree that maps a string key to a generic value.
 * Null Values are not accepted, as null is reserved to denote a non-existing key.
 * @author marouenj
 *
 * @param <A> The generic type of the values
 */
public class Trie<A> {
	
	private Node<A> root;
	
	public Trie() {
		this.root = new Node<A>(null);
	}
	
	/**
	 * Checks the existence of a key.
	 * Returns true whether the node is intermediary (non-valued) or valued.
	 * 
	 * @param key
	 * @return True if the key exists.
	 */
	public boolean exist(String key) {
		if (key == null || key == "") {
			return false;
		}
		
		Node<A> curr = root;
		
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			
			if (curr == null || !curr.children.containsKey(c)) {
				return false;
			}
			
			curr = curr.children.get(c);
		}
		
		return true;
	}

	/**
	 * Maps the key to a value if the key exists.
	 * 
	 * @param key
	 * @return The value mapped by key. Null if the key does not exist.
	 */
	public A get(String key) {
		if (key == null || key == "") {
			return null;
		}
		
		Node<A> curr = root;
		
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if (curr == null || !curr.children.containsKey(c)) {
				return null;
			}
			
			curr = curr.children.get(c);
		}
		
		return curr.val;
	}
	
	/**
	 * Inserts a node with the specified key/value pair.
	 * If the Node exists, the value is updated.
	 * 
	 * @param key
	 * @param val
	 * @return True if the operation succeeded. False otherwise.
	 */
	public boolean set(String key, A val) {
		if (key == null || key == "" || val == null) {
			return false;
		}
		
		Node<A> curr = root;
		
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if (curr.children.containsKey(c)) {
				curr = curr.children.get(c);
			} else {
				String suffix = key.substring(i);
				curr = Trie.setSuffix(curr, suffix);
				break;
			}
		}
		
		curr.val = val;
		
		return true;
	}
	
	/**
	 * Inserts the remainder of a key while avoiding repetitive tests about the existence of the key.
	 * This method should be called internally by 'set' as soon as it encounters a non existing prefix.
	 * 
	 * @param n The node to append.
	 * @param suf The remainder of the original key.
	 * @return The inserted node.
	 */
	private static<A> Node<A> setSuffix(Node<A> n, String suf) {
		for (int i = 0; i < suf.length(); i++) {
			char c = suf.charAt(i);
			Node<A> child = new Node<A>(c);
			n.children.put(c, child);
			n = child;
		}
		
		return n;
	}
	
	/**
	 * Upon deleting a key, subsequent calls to 'get' for this 'key' will return null (key non existing).
	 * The node holding the key as well as others node may be deleted in the process if they don't make sense to still be in the tree.
	 * 
	 * @param key
	 * @return True if deletion occurred.
	 */
	public boolean del(String key) {
		if (key == null || key == "") { // invalid key
			return false;
		}
		
		Node<A> curr = root;
		
		Character nodeToDetach = null; // track the first non-'valued' node
		Node<A> detachFrom = null; // get the parent

		// exclude the last char
		for (int i = 0; i < key.length()-1; i++) {
			char c = key.charAt(i);
			if (curr == null || !curr.children.containsKey(c)) { // key not exist
				return false;
			}
			
			Node<A> prev = curr;
			curr = curr.children.get(c);
			
			if (curr.children.size() > 1 || curr.val != null) { // this node is not candidate for deletion
				detachFrom = null;
			} else if (detachFrom == null) {
				detachFrom = prev;
				nodeToDetach = curr.key;
			}
		}
		
		// the last char
		char c = key.charAt(key.length()-1);
		if (curr == null || !curr.children.containsKey(c)) { // key not exist
			return false;
		}
		
		Node<A> prev = curr;
		curr = curr.children.get(c);
		
		if (curr.children.size() > 0) { // this node is not candidate for deletion
			detachFrom = null;
		} else if (detachFrom == null) {
			detachFrom = prev;
			nodeToDetach = curr.key;
		}
		
		if (detachFrom != null) {
			detachFrom.children.remove(nodeToDetach);
		} else {
			curr.val = null;
		}
		
		return true;
	}
}
