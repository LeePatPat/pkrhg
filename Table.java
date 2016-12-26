import java.util.ArrayList;

public class Table {
	
	private ArrayList<Player> players;
	private ArrayList<Player> waitingList;
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
		tableSize = size;
		players = new ArrayList<Player>();
		waitingList = new ArrayList<Player>();
		dealer = new Dealer();
		this.blinds = blinds;	//not just sb/bb because you can have games like 1/1/2 etc
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
	
	public void dealHands() throws NoCardsRemainingException{
		for(Player p : players){
			dealer.dealCards(p);
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
	
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	/*
	 * Number of players currently sat at the table
	 */
	public int numPlayers(){
		return players.size();
	}
}
