package jl.forthem.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemTest {
	private static Item item;
	private static Item item_identical;
	private static Item item_different1;
	private static Item item_different2;
	private static Item item_different3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		item =  new Item(1,"An item",1 );
		item_identical =  new Item(1,"An item",1 );
		item_different1 =  new Item(2,"An item",1 );
		item_different2 =  new Item(1,"An other item",1 );
		item_different3 =  new Item(1,"An item",2 );
	}

	
	@Test
	void equalsTest_Identical_Items() {
		assertTrue(item.equals(item_identical));
	}
	
	@Test
	void equalsTest_Different_Items_1()
	{
		assertFalse(item.equals(item_different1));
	}
	
	@Test
	void equalsTest_Different_Items_2()
	{
		assertFalse(item.equals(item_different2));
	}
	
	@Test
	void equalsTest_Different_Items_3()
	{
		assertFalse(item.equals(item_different3));
	}
	
	//https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html
	//Requirement 1 for compareTo
	@Test
	void compareToTest_Requirement1()
	{	
		//Comparing items with different names
		int comparison1 = item.compareTo(item_different2);
		int comparison2 = item_different2.compareTo(item);
		boolean test = (comparison1*comparison2 <0);
		assertTrue(test);
	}
	
	//Requirement 2 for compareTo
	
	
	//Requirement 3 for compareTo
	
	@Test
	void compareToTest_Same()
	{
		assertEquals(item.compareTo(item_identical),0);
	}
	
	@Test
	void compareToTest_Same2()
	{	// same name. should return true.
		assertEquals(item.compareTo(item_different1),0);
	}
	
	
	
	
}
