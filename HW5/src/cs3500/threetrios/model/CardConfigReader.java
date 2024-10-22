package cs3500.threetrios.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles logic for reading card data from a file.
 */
public class CardConfigReader {

  public CardConfigReader() {

  }

  /**
   * Method to read cards given a card database.
   * The card database is defined as "CARD_NAME, NORTH, SOUTH, EAST, WEST"
   *
   * @param fileName specified filename to read.
   * @return list of valid cards that have been read from the file
   */
  public List<Card> readCards(String fileName) {
    List<Card> cardsFromFile = new ArrayList<>();
    if (fileName == null || fileName.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be null");
    }
    if (fileName.isBlank()) {
      throw new IllegalArgumentException("File name cannot be blank");
    }
    File cardDatabase = new File(fileName);
    try (BufferedReader cardDbReader = new BufferedReader(new FileReader(cardDatabase))) {
      String line;
      while ((line = cardDbReader.readLine()) != null) {
        String[] cardParts = line.split(" ");

        String cardName = cardParts[0];
        int northNum = Integer.parseInt(cardParts[1]);
        int southNum = Integer.parseInt(cardParts[2]);
        int eastNum = Integer.parseInt(cardParts[3]);
        int westNum = Integer.parseInt(cardParts[4]);

        cardsFromFile.add(new GameCard(cardName, northNum, southNum, eastNum, westNum));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return cardsFromFile;
  }

  public static void main(String[] args) {
    CardConfigReader reader = new CardConfigReader();
    System.out.println(reader.readCards("/Users/neelsawant/Desktop/CS3500_hw5/HW5/src/cs3500/threetrios/model/CardDb.txt"));
  }
}
