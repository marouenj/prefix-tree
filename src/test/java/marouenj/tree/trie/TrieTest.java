package marouenj.tree.trie;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TrieTest {
	
	@Test
	public void insertionWorksAsExpected() {
		Trie<Integer> tree = new Trie<>();
		
		Integer val = 1;
		
		// key not existing (empty tree)
		Assert.assertEquals(tree.get("trie"), null);
		
		// key first insertion (empty tree)
		tree.set("insert", val);
		Assert.assertEquals(tree.get("insert"), val++);

		// key update
		tree.set("insert", val);
		Assert.assertEquals(tree.get("insert"), val++);

		// key not existing
		Assert.assertEquals(tree.get("trie"), null);
		
		// key first insertion (key such that an already inserted key is a prefix)
		tree.set("insertion", val);
		Assert.assertEquals(tree.get("insertion"), val++);
		
		// key update (key such that an already inserted key is a prefix)
		tree.set("insertion", val);
		Assert.assertEquals(tree.get("insertion"), val++);
		
		// key not existing (key that is a prefix of an already inserted key)
		Assert.assertEquals(tree.get("ins"), null);
		
		// key first insertion (key that is a prefix of an already inserted key)
		tree.set("ins", val);
		Assert.assertEquals(tree.get("ins"), val++);
		
		// key update (key that is a prefix of an already inserted key)
		tree.set("ins", val);
		Assert.assertEquals(tree.get("ins"), val++);
	}
}
