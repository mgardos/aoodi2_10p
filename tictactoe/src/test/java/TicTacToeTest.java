import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

public class TicTacToeTest {

	@Test
	public void test01() {
		TicTacToe ticTacToe = new TicTacToe();
		
		assertTrue(ticTacToe.getXPositions().isEmpty());
		assertTrue(ticTacToe.getOPositions().isEmpty());
	}
	
	@Test
	public void test02() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(0, 0);
		
		assertTrue(ticTacToe.getOPositions().isEmpty());
		assertTrue(ticTacToe.getXPositions().contains(Arrays.asList(0, 0)));
	}
	
	@Test
	public void test03() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(0, 0);
		ticTacToe.writeO(1, 1);
		
		assertTrue(ticTacToe.getXPositions().contains(Arrays.asList(0, 0)));
		assertTrue(ticTacToe.getOPositions().contains(Arrays.asList(1, 1)));
	}
	
	@Test
	public void test04() {
		TicTacToe ticTacToe = new TicTacToe();
		
		try {
			ticTacToe.writeX(0, 0);
			ticTacToe.writeO(0, 0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Posicion ocupada");
			assertTrue(ticTacToe.getOPositions().isEmpty());
		}
	}
	
	@Test
	public void test05() {
		TicTacToe ticTacToe = new TicTacToe();
		
		try {
			ticTacToe.writeO(0, 0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "X comienza el juego!");
			assertTrue(ticTacToe.getOPositions().isEmpty());
		}
	}
	
	@Test
	public void test06() {
		TicTacToe ticTacToe = new TicTacToe();
		
		try {
			ticTacToe.writeX(3, 0);
			fail();
		} catch (RuntimeException e) {
			assertEquals(e.getMessage(), "Fuera del tablero...");
			assertTrue(ticTacToe.getXPositions().isEmpty());
		}
	}
	
	@Test
	public void test07() {
		TicTacToe ticTacToe = new TicTacToe();
		
		try {
			ticTacToe.writeX(-1, 0);
			fail();
		} catch (RuntimeException e) {
			assertEquals(e.getMessage(), "Fuera del tablero...");
			assertTrue(ticTacToe.getXPositions().isEmpty());
		}
	}
	
	@Test
	public void test08() {
		TicTacToe ticTacToe = new TicTacToe();
		
		try {
			ticTacToe.writeX(0, -1);
			fail();
		} catch (RuntimeException e) {
			assertEquals(e.getMessage(), "Fuera del tablero...");
			assertTrue(ticTacToe.getXPositions().isEmpty());
		}
	}
	
	@Test
	public void test09() {
		TicTacToe ticTacToe = new TicTacToe();
		
		try {
			ticTacToe.writeX(0, 3);
			fail();
		} catch (RuntimeException e) {
			assertEquals(e.getMessage(), "Fuera del tablero...");
			assertTrue(ticTacToe.getXPositions().isEmpty());
		}
	}
	
	@Test
	public void test10() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(1, 1);
		
		Stream<Integer[]> invalidPositions = Stream.of(new Integer[]{0, -1}, new Integer[]{0, 3}, new Integer[]{-1, 0}, new Integer[]{0, 3});
		invalidPositions.forEach(position -> {
			try {
				ticTacToe.writeO(position[0], position[1]);
				fail();
			} catch (RuntimeException e) {
				assertEquals(e.getMessage(), "Fuera del tablero...");
				assertEquals(ticTacToe.getXPositions().size(), 1);
				assertTrue(ticTacToe.getOPositions().isEmpty());
			}
		});
	}
	
	@Test
	public void test11() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(1, 0);
		ticTacToe.writeO(0, 1);
		ticTacToe.writeX(1, 1);
		
		try {
			ticTacToe.writeO(1, 0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Posicion ocupada");
			assertEquals(ticTacToe.getXPositions().size(), 2);
			assertEquals(ticTacToe.getOPositions().size(), 1);
		}
	}
	
	@Test
	public void test12() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(1, 0);
		ticTacToe.writeO(0, 1);
		
		try {
			ticTacToe.writeX(0, 1);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Posicion ocupada");
			assertEquals(ticTacToe.getXPositions().size(), 1);
			assertEquals(ticTacToe.getOPositions().size(), 1);
		}
	}
	
	@Test
	public void test13() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(1, 0);
		ticTacToe.writeO(0, 1);
		
		try {
			ticTacToe.writeX(1, 0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Posicion ocupada");
			assertEquals(ticTacToe.getXPositions().size(), 1);
			assertEquals(ticTacToe.getOPositions().size(), 1);
		}
	}
	
	@Test
	public void test14() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(1, 0);
		
		try {
			ticTacToe.writeX(0, 1);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Ahora sigue O!");
			assertEquals(ticTacToe.getXPositions().size(), 1);
		}
	}
	
	@Test
	public void test15() {
		TicTacToe ticTacToe = new TicTacToe();
		ticTacToe.writeX(1, 0);
		ticTacToe.writeO(0, 1);
		
		try {
			ticTacToe.writeO(1, 1);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Ahora sigue X!");
			assertEquals(ticTacToe.getOPositions().size(), 1);
		}
	}
}
