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
	 * Map the key to a value if the key exists. Map to null otherwise.
	 * 
	 * @param key
	 * @return Value mapped by key. Null if the key does not exist.
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
			} else {
				curr = curr.children.get(c);
			}
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
	 * Insert the remainder of a key while avoiding repetitive tests about the existence of the key.
	 * This method should be called internally by 'set' as soon as it encounters a non existing prefix.
	 * 
	 * @param n Node to append.
	 * @param suf Remainder of the original key.
	 * @return Inserted node.
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
}
