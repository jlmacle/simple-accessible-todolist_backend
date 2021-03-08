package jl.forthem.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ItemTest {
	private static Item item;
	private static Item item_equal_Compare_0;
	private static Item item_nonEqual_Compare_0;
	private static Item item_nonEqual_Compare_non_0;
	private static Item item_nonEqual_Compare_0_2;
	private static Item item_nonEqual_Compare_non_0_2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		item =  new Item(1,"An item",1 );
		item_equal_Compare_0 =  new Item(1,"An item",1 );
		item_nonEqual_Compare_0 =  new Item(2,"An item",1 );
		item_nonEqual_Compare_non_0 =  new Item(1,"Bn An other item",1 );
		item_nonEqual_Compare_0_2 =  new Item(1,"An item",2 );
		item_nonEqual_Compare_non_0_2 = new Item(1,"Cn An other item",1 );
	}

	
	@Test
	void equalsTest_Identical_Items() {
		assertTrue(item.equals(item_equal_Compare_0));
	}
	
	@Test
	void equalsTest_Different_Items_1()
	{
		assertFalse(item.equals(item_nonEqual_Compare_0));
	}
	
	@Test
	void equalsTest_Different_Items_2()
	{
		assertFalse(item.equals(item_nonEqual_Compare_non_0));
	}
	
	@Test
	void equalsTest_Different_Items_3()
	{
		assertFalse(item.equals(item_nonEqual_Compare_0_2));
	}
	
	//https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html
	//Requirement 1 for compareTo
	@Test
	void compareToTest_Requirement1()
	{	
		//Comparing items with different names
		int comparison1 = item.compareTo(item_nonEqual_Compare_non_0);
		int comparison2 = item_nonEqual_Compare_non_0.compareTo(item);
		boolean test = (comparison1*comparison2 <0);
		assertTrue(test);
	}
	
	//Requirement 2 for compareTo: transistive relationship
	@Test
	void compareTest_Requirement2() 
	{
		int x_to_y = item.compareTo(item_nonEqual_Compare_non_0);
		int y_to_z = item_nonEqual_Compare_non_0.compareTo(item_nonEqual_Compare_non_0_2);
		int x_to_z = item.compareTo(item_nonEqual_Compare_non_0_2);
		
		if (x_to_y * y_to_z >0 )
		{
			assertTrue(x_to_y * x_to_z >0);
		}
		else 
		{fail("The test of transitivity failed. x_to_y and y_to_z should have been of the same sign ");}
		
	}
	//Requirement 3 for compareTo	
	@Test
	void compareTest_Requirement3() 
	{
		
	}
	
	
}
