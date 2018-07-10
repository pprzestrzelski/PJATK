package a_Introductory;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}
	
	/*
	 * Methods assertEauls were ambiguous (missed autoboxing to Integer):
	 * https://stackoverflow.com/questions/1811103/java-junit-the-method-x-is-ambiguous-for-type-y
	 * Last methods in both testAdd and testSub were wrongly written. Not this member was tested (x instead of y)
	 * */
	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);
		
		assertEquals((Integer)4, res1.x);
		assertEquals((Integer)(-21), res1.y);
		assertEquals((Integer)(-3), res2.x);
		assertEquals((Integer)(12), res2.y);
	}
	
	// Contained wrong test values
	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);
		
		assertEquals((Integer)(10), res1.x);
		assertEquals((Integer)(39), res1.y);
		assertEquals((Integer)(17), res2.x);
		assertEquals((Integer)(6), res2.y);
	}

}
