import java.util.ArrayList;
import java.util.List;

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
	public void dealCards(Player p) throws NoCardsRemainingException{
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
	
	/*
	 * Takes in players (and their cards) and the community cards,
	 * works out which player(s) has the best hand - reorders the player[] array
	 * to have the 
	 */
	public Player[] analyseHand(Player[] players, Card[] communityCards){
		ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
		
		for(int i=0; i<players.length; i++){
			hands.add(handCombiner(players[i].getHoleCards(), communityCards));
		}
		
		handChecker(hands);
		
		return null;
	}
	
	/*
	 * Combines the player's hand with the community cards
	 * and puts them in order
	 * for ease of use when finding the strongest hand
	 */
	private ArrayList<Card> handCombiner(Card[] playerHand, Card[] comCards){
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(playerHand[0]);
		hand.add(playerHand[1]);	//hole cards
		hand.add(comCards[0]); 
		hand.add(comCards[1]);
		hand.add(comCards[2]);		//community cards
		hand.add(comCards[3]);
		hand.add(comCards[4]);
		
		return hand;
	}
	
	/*
	 * Private method to determine the strength of the hand
	 */
	private int handChecker(ArrayList<ArrayList<Card>> hand){
		
		return 0;
		
	}
}
