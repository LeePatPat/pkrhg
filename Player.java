
public class Player {
	
	private String name;
	private int chips;
	private Card[] holeCards;
	private boolean hasCards;
	
	/*
	 * This class represents a player on the table
	 */
	public Player(String name, int chips){
		this.name = name;
		this.chips = chips;
		holeCards = new Card[2];
		holeCards[0]=null;holeCards[1]=null;
		hasCards = false;
	}
	
	/*
	 * Returns the player's name
	 */
	public String getName(){
		return name;
	}
	
	/*
	 * Returns whether or not the player has hole cards
	 */
	public boolean hasCards(){
		return hasCards;
	}
	
	/*
	 * Returns the player's chip count
	 */
	public int getChips(){
		return chips;
	}
	
	/*
	 * This method sets the player's hole cards to what the dealer has dealt
	 */
	public void dealToPlayer(Card card1, Card card2){
		this.holeCards[0] = card1;
		this.holeCards[1] = card2;
		hasCards = true;
		//System.out.println("Test: " + holeCards[0].toString() + holeCards[1].toString());
	}
	
	/*
	 * Gets the user's hole cards from them.
	 */
	public Card[] getHoleCards(){
		if(hasCards)
			return this.holeCards;
		System.err.println("Player ("+this.name+") has no hole cards.");
		return null;
	}
	
	/*
	 * Player has decided to fold their hand, setting the hole cards to null
	 */
	public void foldHoleCards(){
		this.holeCards[0] = null;
		this.holeCards[1] = null;
		hasCards=false;
	}
	
	/*
	 * How much this player will bet from their stack
	 * 
	 * returns true is the bet is valid
	 */
	public boolean makeBet(int bet){
		if(bet>chips)
			return false;
		chips -= bet;
		return true;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 * returns player's name and stack size
	 */
	public String toString(){
		return this.name + " (" + this.chips + ")";
	}
	
	/*
	 * returns player's name, stack size and current hand (mainly used for testing only)
	 */
	public String toStringWithHand(){
		Card[] cards = new Card[2];
		cards = this.getHoleCards();
		
		return this.name + " (" + this.chips + "): " + cards[0].toString() + cards[1].toString();
	}
}
