import java.util.ArrayList;
import java.util.Random;

public class Deck {

	private ArrayList<Card> deck;

	public Deck(){
		deck = new ArrayList<Card>();
		createDeck();
	}

	/*
	 * Returns the number of cards remaining in the deck
	 */
	public int cardsRemaining(){
		return deck.size();
	}

	/*
	 * Returns a card from the top of the deck
	 */
	public Card dealCard() throws NoCardsRemainingException{
		if(deck.size()==0)
			throw new NoCardsRemainingException("No Cards Remaining in Deck");
		Random rng = new Random();
		int i = rng.nextInt(deck.size());
		return deck.remove(i);
	}


	/*
	 * Retrieve specific cards (WARNING: SHOULD ONLY EVER BE USED FOR DEBUGGING)
	 */
	public Card getSpecificCard(int value, int suit){
		int i=0;
		for(Card card : deck){
			if(card.getValue()==value && card.getSuit()==suit){
				return deck.remove(i);
			}
			i++;
		}
		
		return null;
	}
	
	/*
	 * Quick private method to create deck with cards in order
	 */
	private void createDeck(){
		for (int suit=0; suit<=3; suit++)
			for (int value = 0; value <= 12; value++)
				deck.add(new Card(suit, value)); //e.g. (0,12) = Ace of Spades
	}
}
