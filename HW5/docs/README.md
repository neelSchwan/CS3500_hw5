**Overview**

* The Three Trios Game is a two-player card game.
* Players take turns placing cards on a
  customizable grid with the objective of controlling more
  cells than their opponent by the end of the game.
* Each card has unique attack values in the four directions
  (North, South, East, and West).
* When a card is placed adjacent to an opponent's card, a
  battle occurs.
* A battle is won in a certain direction when the opposite facing attack values
  of the battling cards is greater for one players' than for the others.
    * For example, if RED has a card with 8 on the east side, and BLUE places a card
      adjacent, with 6 on the west side, RED would flip BLUES card to REDs ownership.
* Apply this rule repetitively for all adjacent cards. (Combo Step).
* The game is over when all CARD_CELLS on the grid are filled.
* Score = number of cards a player owns + the number of
  cards in a player's hand
* A player wins if their score is higher than the other player.
* If there is a tie in the scores, there is no winner, and we return null.

***

**Extensibility**

* Our current scope primarily focuses on the core gameplay as described
  above,

***

**Quick Start**

* Someone can use the startGame(long seed)
  method to start the game.
* This method shuffles the deck based on a seed, and deals the cards to
  the players.

***

**Key Components**

* Model (drives the control-flow of the game)
    * starts game by receiving a seed number to shuffle deck
      and deals cards to players
    * place a card to a grid
    * determine is game over
    * determine which player won
* View (the view is driven)
    * render a current status of the grid and display

**Key Subcomponents**

* Grid
    * construct a grid according to given numbers of row and columns
    * set type of cells
    * set adjacent cells for each cell
    * calculate number of CARD_CELL in grid
* Cell
    * determine if the cell is occupied by another card
    * place a card to a CARD_CELL
* Card Config Reader
    * read config file and return as a list of cards.
* Grid Config Reader
  * read config file and return as Grid object