import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Dealer {	
	private Deck deck;
	private int pot;
	private ArrayList<Card> comCards;
	
	/*
	 * This class deals with the dealing, handling of chips and hand analysis
	 */
	public Dealer(){
		deck = new Deck();
		shuffleDeck();
		comCards = new ArrayList<Card>();
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
	 * Also removes the community cards
	 */
	public void resetHand(){
		comCards.clear();
		pot=0;
	}
	
	/*
	 * After each betting round the bets from each player gets added to the pot
	 */
	public void addToPot(int bets){
		pot+=bets;
	}
	
	/*
	 * Returns the current community cards
	 */
	public ArrayList<Card> getCommunityCards(){
		return comCards;
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
		flop[0] = deck.dealCard(); comCards.add(flop[0]);
		flop[1] = deck.dealCard(); comCards.add(flop[1]);
		flop[2] = deck.dealCard(); comCards.add(flop[2]);
		return flop;
	}
	
	/*
	 * Deals out the turn/river
	 */
	public Card dealTurnOrRiver() throws NoCardsRemainingException, CommunityCardsAlreadyDealtException{
		if(comCards.size()!=3 || comCards.size()!=4){
			//throw new CommunityCardsAlreadyDealtException("Community cards cannot be dealt");
		}
		
		comCards.add(deck.dealCard());
		return comCards.get(comCards.size()-1);
	}
	
	/*
	 * Takes in players (and their cards) and the community cards,
	 * works out which player(s) has the best hand - reorders the player[] array
	 * to have the 
	 */
	public String[] analyseHand(ArrayList<Player> players, ArrayList<Card> communityCards){
		ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
		for(int i=0; i<players.size(); i++){
			Card[] test = players.get(i).getHoleCards();
			ArrayList<Card> playerHand = new ArrayList<Card>(Arrays.asList(players.get(i).getHoleCards()));
			hands.add(handCombiner(playerHand, communityCards));
		}
		
		String[] playerHandRanks = new String[players.size()];
		for(int i=0; i<players.size(); i++){						//playerHandRanks[i] = handChecker(hands.get(i));  //add back after testing hand combiner
			playerHandRanks[i] = "";
			for(int j=0; j<7; j++){
				playerHandRanks[i] += hands.get(i).get(j).toString();
			}
		}
		
		return playerHandRanks;
	}
	
	/*
	 * Private method to determine the strength of the hand
	 */
	private String handChecker(ArrayList<Card> hand){
		return "mad hand";
	}
	
	/*
	 * Combines the player's hand with the community cards
	 * and puts them in order
	 * for ease of use when finding the strongest hand
	 */
	private ArrayList<Card> handCombiner(ArrayList<Card> playerHand, ArrayList<Card> comCards){
		playerHand.addAll(comCards); //combine both arlists

		Card[] hand = new Card[playerHand.size()];
		hand = playerHand.toArray(hand);
		
		//sort the cards into decending order (using insertion sort as it works best for small lists)
		int i, j, first;
		Card temp;
		for(i = hand.length-1; i>0; i--){
			first = 0;
			for(j=1; j<=i; j++)
 				if(hand[j].getValue() < hand[first].getValue())
					first = j;
			temp = hand[first];
			hand[first] = hand[i];
			hand[i] = temp;
		}
		
		ArrayList<Card> handtest = new ArrayList<Card>(Arrays.asList(hand));	
		return handtest;
	}
}
