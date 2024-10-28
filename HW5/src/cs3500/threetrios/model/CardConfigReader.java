package cs3500.threetrios.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that handles logic for reading card data from a file.
 */
public class CardConfigReader {

  /**
   * Empty constructor since configuration readers don't need arguments.
   */
  public CardConfigReader() {

  }

  /**
   * Method to read cards given a card database.
   * The card database is defined as "CARD_NAME, NORTH, SOUTH, EAST, WEST"
   *
   * @param filename specified filename to read.
   * @return list of valid cards that have been read from the file
   */
  public List<Card> readCards(String filename) {
    List<Card> cardsFromFile = new ArrayList<>();
    if (filename == null || filename.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be null");
    }
    if (filename.isBlank()) {
      throw new IllegalArgumentException("File name cannot be blank");
    }
    File cardDatabase = new File(filename);
    try (BufferedReader cardDbReader = new BufferedReader(new FileReader(cardDatabase))) {
      if (!cardDbReader.ready()) {
        throw new IllegalStateException("File must have some valid data.");
      }
      parseCardDb(cardDbReader, cardsFromFile);
    } catch (IOException e) {
      throw new RuntimeException("Issue when reading file: " + filename, e);
    }
    return cardsFromFile;
  }

  /**
   * Helper method that parses the cardDb.
   *
   * @param cardDbReader BufferedReader for handling cardDb file reading.
   * @param finalCards   Final list of cards created from parsing the file.
   * @throws IOException throws an IOException if there is some issue when reading the file.
   */
  private void parseCardDb(BufferedReader cardDbReader, List<Card> finalCards) throws IOException {
    String line;
    Set<String> nameSet = new HashSet<>();
    boolean hasDuplicates = false;
    while ((line = cardDbReader.readLine()) != null) {
      String[] cardParts = line.split(" ");
      if (cardParts.length != 5) {
        throw new IllegalArgumentException("Invalid card format: " + line);
      }

      String cardName = cardParts[0];
      if (nameSet.add(cardName)) {  // trim() removes any extra spaces around the name
        System.out.println("Duplicate name found: " + line);
        hasDuplicates = true;
      }
      int northNum = parseAttackValue(cardParts[1]);
      int southNum = parseAttackValue(cardParts[2]);
      int eastNum = parseAttackValue(cardParts[3]);
      int westNum = parseAttackValue(cardParts[4]);
      finalCards.add(new GameCard(cardName, northNum, southNum, eastNum, westNum));
    }
    if(hasDuplicates) {
      throw new IllegalStateException("Duplicate name found, edit config!");
    }
  }

  /**
   * Helper method to parse each string in the cardDb file as an integer.
   *
   * @param value String value from the cardDb to parse.
   * @return Integer that is parsed from the given value, if value is 'A,' attack value is 10.
   */
  private int parseAttackValue(String value) {
    int parsedValue;
    if (value.equalsIgnoreCase("A")) {
      return 10;
    }
    try {
      parsedValue = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Illegal attack value for card: " + value);
    }

    if (parsedValue < 1 || parsedValue > 9) {
      throw new IllegalArgumentException("Attack value must be between 1 and 9");
    }
    return parsedValue;
  }
}
