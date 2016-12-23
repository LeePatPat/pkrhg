public class Dealer {	
	private Deck deck;
	private int pot;
	
	/*
	 * This class deals with the dealing and handling of chips
	 */
	public Dealer(){
		deck = new Deck();
		shuffleDeck();
	}
	
	/*
	 * The dealer deals cards to specific player
	 */
	public void dealCard(Player p) throws NoCardsRemainingException{
		p.dealToPlayer(deck.dealCard(), deck.dealCard());
	}
	
	/*
	 * Returns number of chips in the pot
	 */
	public int getPotSize(){
		return pot;
	}
	
	/*
	 * Reset the pot to zero. Happens after a player(s) win the pot(s)
	 */
	public void resetPot(){
		pot=0;
	}
	
	/*
	 * After each betting round the bets from each player gets added to the pot
	 */
	public void addToPot(int bets){
		pot+=bets;
	}
	
	/*
	 * Dealer shuffles the deck for reuse
	 */
	public void shuffleDeck(){
		deck.shuffle();
	}
	
	/*
	 * Deals out the first 3 cards (flop)
	 */
	public Card[] dealFlop() throws NoCardsRemainingException{
		Card[] flop = new Card[3];
		flop[0] = deck.dealCard();
		flop[1] = deck.dealCard();
		flop[2] = deck.dealCard();
		return flop;
	}
	
	/*
	 * Deals out the turn/river
	 */
	public Card dealTurnOrRiver() throws NoCardsRemainingException{
		return deck.dealCard();
	}
}
