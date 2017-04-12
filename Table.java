import java.util.ArrayList;

public class Table {
	
	private ArrayList<Player> players;
	private ArrayList<Player> waitingList;
	private ArrayList<Card> comCards;
	private Dealer dealer;
	private int tableSize;
	private int blinds[];
	
	/*
	 * This class represents the table. I.e. Everything combined (1 dealer, many players, 1 deck,
	 * 										 many cards, pot, chips).
	 * Param: size - number of players allowed to sit at the table and play
	 * 		  blinds - the blind levels for the table
	 */
	public Table(int size, int[] blinds){
		comCards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		waitingList = new ArrayList<Player>();
		dealer = new Dealer();
		tableSize = size;
		this.blinds = blinds;	//not just sb/bb because you can have games like 1/1/2 etc
	}
	
	public void setComCards(ArrayList<Card> cards){
		this.comCards.clear();
		this.comCards = dealer.setComCards(cards);
//		System.out.println(comCards.toString());
	}
	
	/*
	 * Used for debugging only ty
	 */
	public void givePlayerCard(Card card, Player player){
		player.dealCardToPlayer(card);
	}
	
	/*
	 * SHOULD ONLY BE USED FOR DEBUGGING
	 */
	public Card getSpecificCard(int value, int suit){
		return dealer.getSpecificCard(value, suit);
	}
	
	/*
	 * The dealer deals the community cards
	 */
	public ArrayList<Card> dealComCards() throws NoCardsRemainingException{
		comCards = dealer.dealComCards();
		return dealer.getCommunityCards();
	}

	/*
	 * Returns the current community cards
	 */
	public ArrayList<Card> getComCards(){
		return comCards;
	}
	
	/*
	 * Adds a player to the table
	 */
	public void addPlayer(Player p){
		if(players.size() == this.tableSize){
			System.out.println("No more room on the table. Player " + p.getName() + " has been added to the waiting list.");
			waitingList.add(p);
			return;
		}
		players.add(p);
	}
	
	/*
	 * Deals two hole cards to each player at the table
	 */
	public void dealHands() throws NoCardsRemainingException{
		for(Player p : players){
			p.dealToPlayer(dealer.dealCard(), dealer.dealCard());
		}
		System.out.println("Cards Dealt!");
	}
	
	/*
	 * Removes a player from the table
	 */
	public void removePlayer(Player p){
		if(!players.contains(p)){
			System.out.println("Player is not sitting at the table!");
			return;
		}
		players.remove(p);
		
		if(waitingList.size() > 0)	//if there's a waiting list, add the first player on it
			players.add(waitingList.get(0));
	}
	
	/*
	 * Returns the list of players at the table
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}

	/*
	 * Number of players currently sat at the table
	 */
	public int numPlayers(){
		return players.size();
	}
	
	/*
	 * Tells the dealer to analyse the hand
	 */
	public String[] analyseHand(ArrayList<Player> players, ArrayList<Card> commCards){
		return dealer.analyseHand(players, this.getComCards());
	}
}
