import java.util.ArrayList;

public class Table {
	
	private ArrayList<Player> players;
	private ArrayList<Player> waitingList;
	private Dealer dealer;
	private int tableSize;
	
	/*
	 * This class represents the table. I.e. Everything combined (1 dealer, many players, 1 deck,
	 * 										 many cards, pot, chips).
	 * Param: size - number of players allowed to sit at the table and play
	 */
	public Table(int size){
		tableSize = size;
		players = new ArrayList<Player>();
		waitingList = new ArrayList<Player>();
		dealer = new Dealer();
	}
	
	/*
	 * Adds a player to the table
	 */
	public void addPlayerToTable(Player p){
		if(players.size() == this.tableSize){
			System.out.println("No more room on the table. Player " + p.getName() + " has been added to the waiting list.");
			waitingList.add(p);
			return;
		}
		players.add(p);
	}
	
	/*
	 * Removes a player from the table
	 */
	public void removePlayerFromTable(Player p){
		if(!players.contains(p)){
			System.out.println("Player is not sitting at the table!");
			return;
		}
		players.remove(p);
	}
	
	
}
