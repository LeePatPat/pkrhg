
public class Card {
	
	String[] suitString = {"s","h","d","c"};
	String[] valueString = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	
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
		return valueString[value] + suitString[suit];
	}
	
	/*
	 * Compares this card object to another card
	 * return	1 - this > c
	 * return	0 - this = c
	 * return  -1 - this < c
	 */
	public int compareTo(Card c){
		if(this.getValue() > c.getValue())
			return 1;
		else if(this.getValue() == c.getValue())
			return 0;
		
		return -1; //this.value < c.value
	}
}
