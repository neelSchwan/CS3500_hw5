package cs3500.threetrios.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that handles logic for reading / initializing a Grid from a config file.
 */
public class GridConfigReader {

  /**
   * Empty constructor for a grid reader since passing in args isn't necessary.
   */
  public GridConfigReader() {

  }

  /**
   * Method to read a Grid from a config / database file.
   * The Grid config in the database is defined as:
   * ROWS COLS
   * ROW_0
   * Each ROW_0, ROW_1, can have a C or an X, which defines a hole, or (empty) card cell.
   *
   * @param filename specified filepath to the database.
   * @return Grid object that is initialized with Cells specified in the database.
   */
  public Grid readGridFromFile(String filename) {
    // Validate the filename
    if (filename == null || filename.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be null or empty");
    }
    if (filename.isBlank()) {
      throw new IllegalArgumentException("File name cannot be blank");
    }

    File gridDb = new File(filename);
    try (BufferedReader gridDbReader = new BufferedReader(new FileReader(gridDb))) {
      return parseGridDb(gridDbReader);
    } catch (IOException e) {
      throw new RuntimeException("Issue when reading file: " + filename, e);
    }
  }

  private Grid parseGridDb(BufferedReader gridDbReader) throws IOException {
    int row = parseRowsAndCols(gridDbReader)[0]; // 0 index = row
    int col = parseRowsAndCols(gridDbReader)[1];// 1 index = col

    int totalCells = row * col;
    if (totalCells % 2 == 0) {
      throw new IOException("Invalid grid: The total number of cells must be odd, but found " + totalCells);
    }

    Grid gridFromFile = new Grid(row, col);

    String line;
    int currentRow = 0;
    while ((line = gridDbReader.readLine()) != null) {
      String[] gridParts = line.split("");

      if (gridParts.length != col) {
        throw new IOException("Grid data does not match the specified number of columns");
      }

      for (int currentCol = 0; currentCol < gridParts.length; currentCol++) {
        String cellValue = gridParts[currentCol];
        if (cellValue.equals("C")) {
          gridFromFile.setCell(currentRow, currentCol, new Cell(CellType.CARD_CELL));
        } else if (cellValue.equals("X")) {
          gridFromFile.setCell(currentRow, currentCol, new Cell(CellType.HOLE_CELL));
        }
      }
      currentRow++;
    }

    if (currentRow != row) {
      throw new IOException("Grid data does not match the specified number of rows");
    }

    return gridFromFile;
  }

  private int[] parseRowsAndCols(BufferedReader gridDbReader) throws IOException {
    String firstLine = gridDbReader.readLine();
    String[] rowsAndCols = firstLine.split(" ");
    int row = Integer.parseInt(rowsAndCols[0]);
    int col = Integer.parseInt(rowsAndCols[1]);

    return new int[]{row, col};
  }
}
