import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToe {
	private List<List<Integer>> xPositions = new ArrayList<>();
	private List<List<Integer>> oPositions = new ArrayList<>();
	
	public List<List<Integer>> getXPositions() {
        return xPositions;
    }

    public List<List<Integer>> getOPositions() {
        return oPositions;
    }

	public void writeX(int coordX, int coordY) {
		assertIsValidPosition(coordX, coordY);
		assertIsAvailablePosition(coordX, coordY);
		
		if (xPositions.size() > oPositions.size()) {
			throw new RuntimeException("Ahora sigue O!");
		}
		
		xPositions.add(Arrays.asList(coordX, coordY));
	}

	public void writeO(int coordX, int coordY) {
		assertIsValidPosition(coordX, coordY);
		
		if (xPositions.isEmpty()) {
			throw new RuntimeException("X comienza el juego!");
		}
		
		if (xPositions.size() - oPositions.size() != 1) {
			throw new RuntimeException("Ahora sigue X!");
		}
		
		assertIsAvailablePosition(coordX, coordY);
		
		oPositions.add(Arrays.asList(coordX, coordY));
	}
	
	private void assertIsValidPosition(int coordX, int coordY) {
		if (coordX < 0 || coordX > 2 || coordY < 0 || coordY > 2) {
			throw new RuntimeException("Fuera del tablero...");
		}
	}
	
	private void assertIsAvailablePosition(int coordX, int coordY) {
		if (oPositions.contains(Arrays.asList(coordX, coordY)) || xPositions.contains(Arrays.asList(coordX, coordY))) {
			throw new RuntimeException("Posicion ocupada");
		}
	}
}
