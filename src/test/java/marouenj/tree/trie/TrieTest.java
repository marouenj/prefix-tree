package marouenj.tree.trie;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TrieTest {
	
	private static Trie<Integer> TREE;
	
	@Test
	public void insertionWorksAsExpected() {
		TREE = new Trie<>();
		
		Integer val = 1;
		
		// key not existing (empty tree)
		Assert.assertEquals(TREE.get("trie"), null);
		
		// key first insertion (empty tree)
		TREE.set("insert", val);
		Assert.assertEquals(TREE.get("insert"), val++);

		// key update
		TREE.set("insert", val);
		Assert.assertEquals(TREE.get("insert"), val++);

		// key not existing
		Assert.assertEquals(TREE.get("trie"), null);
		
		// key first insertion (key such that an already inserted key is a prefix)
		TREE.set("insertion", val);
		Assert.assertEquals(TREE.get("insertion"), val++);
		
		// key update (key such that an already inserted key is a prefix)
		TREE.set("insertion", val);
		Assert.assertEquals(TREE.get("insertion"), val++);
		
		// key not existing (key that is a prefix of an already inserted key)
		Assert.assertEquals(TREE.get("ins"), null);
		
		// key first insertion (key that is a prefix of an already inserted key)
		TREE.set("ins", val);
		Assert.assertEquals(TREE.get("ins"), val++);
		
		// key update (key that is a prefix of an already inserted key)
		TREE.set("ins", val);
		Assert.assertEquals(TREE.get("ins"), val++);
	}
	
	@Test
	public void deleteTheOnlyExistingKey() {
		TREE = new Trie<>();
		
		TREE.set("abc", 1);
		Assert.assertEquals(TREE.get("abc"), new Integer(1));
		
		TREE.del("abc");
		Assert.assertEquals(TREE.exist("a")  , false);
		Assert.assertEquals(TREE.exist("ab") , false);
		Assert.assertEquals(TREE.exist("abc"), false);
	}
	
	@Test
	public void deleteKeyWithPrefixAsAnotherKey() {
		// 1
		TREE = new Trie<>();
		
		TREE.set("abc", 100);
		Assert.assertEquals(TREE.get("abc"), new Integer(100));
		
		TREE.set("abc123", 200);
		Assert.assertEquals(TREE.get("abc123"), new Integer(200));
		
		TREE.del("abc123");
		Assert.assertEquals(TREE.exist("a")  , true);
		Assert.assertEquals(TREE.exist("ab") , true);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc1")  , false);
		Assert.assertEquals(TREE.exist("abc12") , false);
		Assert.assertEquals(TREE.exist("abc123"), false);
		
		// 2
		TREE = new Trie<>();
		
		TREE.set("a"  , 100);
		TREE.set("ab" , 200);
		TREE.set("abc", 300);
		Assert.assertEquals(TREE.get("a")  , new Integer(100));
		Assert.assertEquals(TREE.get("ab") , new Integer(200));
		Assert.assertEquals(TREE.get("abc"), new Integer(300));
		
		TREE.set("abc1", 400);
		Assert.assertEquals(TREE.get("abc1"), new Integer(400));
		
		TREE.del("abc1");
		Assert.assertEquals(TREE.exist("a")  , true);
		Assert.assertEquals(TREE.exist("ab") , true);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc1")  , false);
		
		// 3
		TREE = new Trie<>();
		
		TREE.set("a"   , 100);
		TREE.set("ab"  , 200);
		TREE.set("abc" , 300);
		TREE.set("abcd", 400);
		Assert.assertEquals(TREE.get("a")   , new Integer(100));
		Assert.assertEquals(TREE.get("ab")  , new Integer(200));
		Assert.assertEquals(TREE.get("abc") , new Integer(300));
		Assert.assertEquals(TREE.get("abcd"), new Integer(400));
		
		TREE.set("abc123", 500);
		Assert.assertEquals(TREE.get("abc123"), new Integer(500));
		
		TREE.del("abc123");
		Assert.assertEquals(TREE.exist("a")   , true);
		Assert.assertEquals(TREE.exist("ab")  , true);
		Assert.assertEquals(TREE.exist("abc") , true);
		Assert.assertEquals(TREE.exist("abcd"), true);
		Assert.assertEquals(TREE.exist("abc1")  , false);
		Assert.assertEquals(TREE.exist("abc12") , false);
		Assert.assertEquals(TREE.exist("abc123"), false);
	}
	
	@Test
	public void deleteKeyThatIsPrefixToAnotherKey() {
		// 1
		TREE = new Trie<>();
		
		TREE.set("abc", 100);
		Assert.assertEquals(TREE.get("abc"), new Integer(100));
		
		TREE.set("abc123", 200);
		Assert.assertEquals(TREE.get("abc123"), new Integer(200));
		
		TREE.del("abc");
		Assert.assertEquals(TREE.exist("a")  , true);
		Assert.assertEquals(TREE.exist("ab") , true);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc1")  , true);
		Assert.assertEquals(TREE.exist("abc12") , true);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.get("abc")   , null);
		Assert.assertEquals(TREE.get("abc123"), new Integer(200));
	}
}
