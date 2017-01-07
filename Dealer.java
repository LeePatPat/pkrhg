import java.util.ArrayList;
import java.util.Arrays;

public class Dealer {	
	private Deck deck;
	private int pot;
	private ArrayList<Card> comCards;

	/*
	 * This class deals with the dealing, handling of chips and hand analysis
	 */
	public Dealer(){
		deck = new Deck();
		comCards = new ArrayList<Card>();
	}

	/*
	 * The dealer deals cards to specific player
	 */
	public Card dealCard() throws NoCardsRemainingException{
		return deck.dealCard();
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
	 * Deals community cards
	 */
	public ArrayList<Card> dealComCards() throws NoCardsRemainingException{
		comCards.add(this.dealCard());
		comCards.add(this.dealCard());
		comCards.add(this.dealCard());
		comCards.add(this.dealCard());
		comCards.add(this.dealCard());
		return comCards;
	}

	/*
	 * Takes in players (and their cards) and the community cards,
	 * works out which player(s) has the best hand - reorders the player[] array
	 * to have the 
	 */
	public String[] analyseHand(ArrayList<Player> players, ArrayList<Card> communityCards){
		ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
		for(int i=0; i<players.size(); i++){
			ArrayList<Card> playerHand = new ArrayList<Card>(Arrays.asList(players.get(i).getHoleCards()));
			hands.add(handCombiner(playerHand, communityCards));
		}

		String[] playerHandRanks = new String[players.size()];
		for(int i=0; i<players.size(); i++){
			playerHandRanks[i] = handChecker(hands.get(i));
		}

		return playerHandRanks;
	}

	
	/*
	 * 
	 *		#### PRIVATE METHODS SECTION #### 
	 * 
	 */
		
	/*
	 * Private method to determine the strength of the hand
	 * Strengths: Straight flush, quads, full house, flush, straight, trips, 2pair, pair, high card
	 */
	private String handChecker(ArrayList<Card> hand){		
		HandAnalyser ha = new HandAnalyser();
		
		boolean flush = ha.flushCheck(hand);
		boolean straight = ha.straightCheck(hand);
		if(straight && flush && ha.straightFlushCheck(hand))
			return "straight flush";

		//if there's no flush/straight, check for Quads/boat etc
		if(flush) return "flush";
		
		else if(straight) return "straight";
		
		else{
			int[] valOcc = ha.valueOccurrence(hand);	//[quadsBool, tripsBool, pairCount]

			if(valOcc[0] == 1) return "quads";
			else if((valOcc[1] > 0 && valOcc[2] > 0) || valOcc[1] > 1) return "full house"; //trips+pair = full house (AAAKK23 = full house)
			else if(valOcc[1] > 0) return "trips";
			else if(valOcc[2] >= 2) return "two pair";
			else if(valOcc[2] == 1) return "pair";

			return "high card";
		}
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

		//sort the cards into decending order (using selection sort as it's nice and easy for now)
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
	
	/*
	 * Used for testing certain cases
	 */
	public void handTester(){
		ArrayList<Card> test = new ArrayList<Card>();
		test.add(new Card(12,3));
		test.add(new Card(10,2));
		test.add(new Card(10,1));
		test.add(new Card(10,0));
		test.add(new Card(8,1));
		test.add(new Card(8,0));
		test.add(new Card(4,2));
		
		System.out.println("RESULT OF HAND TESTER: " + this.handChecker(test));
	}

}
