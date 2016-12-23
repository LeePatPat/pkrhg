
public class Deck {
	
	private Card[] deck;
	private int numCardsDealt;
	private final int DECK_SIZE = 52;
	
	public Deck(){
		deck = new Card[DECK_SIZE];
		createDeck();
		numCardsDealt = 0; //keeps track of the number of cards remaining in the deck
	}
	
	/*
	 * Shuffles the deck to be used in dealing the cards
	 * When this method is called, it also resets the deck to having 52 cards
	 * Todo: replace with true randomness system
	 */
	public void shuffle(){
		for (int i= deck.length-1; i>0; i--){
			int rng = (int)(Math.random()*(i+1));
			Card temp = deck[i];
			deck[i] = deck[rng];
			deck[rng] = temp;
		}
		numCardsDealt = 0;
	}
	
	/*
	 * Returns the number of cards remaining in the deck
	 */
	public int cardsRemaining(){
		return deck.length - numCardsDealt;
	}
	
	/*
	 * Returns a card from the top of the deck
	 */
	public Card dealCard() throws NoCardsRemainingException{
		if(numCardsDealt == deck.length)
			throw new NoCardsRemainingException("No cards remaining in deck");
		numCardsDealt++;
		return deck[numCardsDealt - 1];
	}
	
	
	/*
	 * Quick private method to create deck with cards in order
	 */
	private void createDeck(){
		int deckCount = 0; //tracks the number of cards created
		for (int suit=0; suit<=3; suit++)
			for (int value = 0; value <= 12; value++){
				deck[deckCount] = new Card(suit, value); //e.g. (0,12) = Ace of Spades
				deckCount++;
			}
	}
}
