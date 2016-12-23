
public class Player {
	
	private String name;
	private int chips;
	private Card[] holeCards;
	
	/*
	 * This class represents a player on the table
	 */
	public Player(String name, int chips){
		this.name = name;
		this.chips = chips;
		holeCards = new Card[2];
		holeCards[0]=null;holeCards[1]=null;
	}
	
	/*
	 * Returns the player's name
	 */
	public String getName(){
		return name;
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
	}
	
	/*
	 * Gets the user's hole cards from them.
	 */
	public Card[] getHoleCards(){
		return this.holeCards;
	}
	
	/*
	 * Player has decided to fold their hand, setting the hole cards to null
	 */
	public void foldHoleCards(){
		this.holeCards[0] = null;
		this.holeCards[1] = null;
	}
	
	/*
	 * How much this player will bet from their stack
	 */
	public boolean makeBet(int bet){
		if(bet>chips)
			return false;
		chips -= bet;
		return true;
	}
}
