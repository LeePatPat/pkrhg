
public class Card {
	
	String[] suitString = {"Spades","Hearts","Diamonds","Clubs"};
	String[] valueString = {"Two","Three","Four","Five","Six",
							"Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
	
	private int suit;
	private int value;
	
	public Card(int suit, int value){
		this.suit = suit;
		this.value = value;
	}
	
	/*
	 * Return the card's suit
	 */
	public int getSuit(){
		return suit;
	}
	
	/*
	 * Return the card's value
	 */
	public int getValue(){
		return value;
	}
	
	/*
	 * Return the full name of card
	 */
	@Override
	public String toString(){
		return valueString[value] + " of " + suitString[suit];
	}
}
