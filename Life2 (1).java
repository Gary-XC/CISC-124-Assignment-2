package gameoflife;
import java.util.Random;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Life2 {
	
    public static int numRows(boolean [][] cells){
        return cells.length;
    }

    public static int numCols(boolean [][] cells){
        return cells[0].length;
    }

    public static boolean isValid(boolean [][] cells, int row, int col){
        if (row < 0 || row >= numRows(cells)){
            return false;
        }
        else return col >= 0 && col < numCols(cells);
    }

    public static boolean [][] clone(boolean [][] cells){
        int rows = numRows(cells);
        int col = numCols(cells);

        boolean [][] copy = new boolean[rows][col];

        for (int r = 0; r < rows; r++){
            for (int c = 0; c < col; c++){
                copy[r][c] = cells[r][c];
            }
        }

        return copy;

    }

    public static void printCells(boolean [][] cells){
        for (boolean[] cell : cells) {
            for (int c = 0; c < cells[0].length; c++) {
                if (cell[c]) {
                    System.out.print('#');
                } else {
                    System.out.print('-');
                }

            }
            System.out.println();
        }

    }

    public static boolean [][] neighborhood(boolean [][] cells, int row, int col){
        if (!isValid(cells, row, col)){
            throw new IllegalArgumentException("You must input a valid cell, row and col.");
        }

        boolean [][] nHood = {{false, false, false},
                             {false, false, false},
                             {false, false, false}};

        int left = col - 1;
        int top = row - 1;

        for (int r = 0; r < 3; r++){
            int cellsRow = top + r;

            for (int c = 0; c < 3; c++){
                int cellsCol = left + c;

                if (isValid(cells, cellsRow, cellsCol)){
                    nHood[r][c] = cells[cellsRow][cellsCol];
                }
            }
        }
        return nHood;
    }

    public static boolean isAlive(boolean [][] cells, int row, int col){
        if (!isValid(cells, row, col)){
            throw new IllegalArgumentException();
        }

        return cells[row][col];

    }

    public static int numAlive(boolean [][] cells){
        int nAlive = 0;
        for (boolean[] cell : cells) {
            for (int c = 0; c < cells[0].length; c++) {
                if (cell[c]) {
                    nAlive++;
                }
            }
        }

        return nAlive;
	
    }
    
    
    public static boolean isBorn(boolean[][] cells, int row, int col) {
        if (!isValid(cells, row, col)){
            throw new IllegalArgumentException("You must input a valid cell, row and col.");
        }

        boolean[][] arr = neighborhood(cells, row, col);
        int numOfCells = numAlive(arr);
        boolean status = cells[row][col];

        return !status && numOfCells == 3;

    }

    public static boolean survives(boolean[][] cells, int row, int col) {
        if (!isValid(cells, row, col)){
            throw new IllegalArgumentException("You must input a valid cell, row and col.");
        }

        boolean [][] arr = neighborhood(cells, row, col);
        int numOfCells = numAlive(arr);
        boolean status = cells[row][col];

        return status && numOfCells == 3 || numOfCells == 4;

    }

    public static void evolve(boolean [][] cells) {
        boolean [][] copy = clone(cells);

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                boolean val;
                if (!copy[r][c]) {
                    val = isBorn(copy, r, c);
                    cells[r][c] = val;
                }
                else {
                    val = survives(copy, r, c);
                    cells[r][c] = val;
                }
            }
        }
    }
    
    public static void randomize(boolean [][] cells) {
    	Random rand = new Random();
    	int upperBound = 2;
    	for	(int r = 0; r < cells.length; r++) {
    		for (int c = 0; c < cells[0].length; c++) {
    			int randomInt = rand.nextInt(upperBound);
    			if(randomInt == 1) {
    				cells[r][c] = true;
    			}
    			else {
    				cells[r][c] = false;
    			}
    		}
    	}
    	
    }
    
    
    public static boolean insert(boolean [][] pattern, int row, int col, boolean [][] cells) {
    	if(row > cells.length || col > cells[0].length || row < 0 || col < 0) {
    		throw new IllegalArgumentException("You must input a valid cell, parrtern, row and col.");
    	}
    	
    	int lowerLimit = col + pattern[0].length;
    	int rightLimit = row + pattern.length;
    	
    	int rowCount = 0;
    	int colCount = 0;
    	
    	
    	for (int r = row; r <= rightLimit - 1; r++) {
    		for (int c = col; c <= lowerLimit - 1; c++) {
    			cells[r][c] = pattern[rowCount][colCount];
    			colCount++;
    			
    		}
    		rowCount++;
    		colCount = 0;
    	}
    	
    	boolean status = false;
    	
    	if(lowerLimit >= cells[0].length || rightLimit >= cells.length) {
    		status = false;
    	}
    	
    	else {
    		status = true;
    	}
    	
    	return status;
    	
    }
    
    
    public static boolean[][] read(String filename) {
        try {
            Path path = FileSystems.getDefault().getPath("patterns", filename);
            List<String> lines = Files.readAllLines(path);
            
            String strArr [] = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
            	strArr[i] = lines.get(i);
            }
            
            boolean [][] readCells = new boolean[strArr.length][strArr[0].length()];
            
            for(int i = 0; i < strArr.length; i++) {
            	char[] cellRows = strArr[i].toCharArray();
            	for (int j = 0; j < cellRows.length; j++) {
            		if(cellRows[j] == '#') {
            			readCells[i][j] = true;
            		}
            		else {
            			readCells[i][j] = false;
            		}
            	}
            	
            }
            
            return readCells;


        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    	
		
    

}
