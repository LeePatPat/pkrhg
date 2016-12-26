
public class Main {

	public static void main(String[] args) throws NoCardsRemainingException {
		
		Dealer dealer = new Dealer();
		int[] blinds = {1,2};
		Table table = new Table(9, blinds);
		
		Player p1 = new Player("Gibb", 4000);
		Player p2 = new Player("Lee", 7500);
		Player p3 = new Player("Bangus", 3123);
		Player p4 = new Player("AJ", 5500);
		Player p5 = new Player("Soane", 10250);
		Player p6 = new Player("Lee's da", 5200);
		Player p7 = new Player("Whitey", 300);
		Player p8 = new Player("Cookie", 750);
		Player p9 = new Player("Amirovski", 11250);
		
		table.addPlayer(p1);
		table.addPlayer(p2);
		table.addPlayer(p3);
		table.addPlayer(p4);
		table.addPlayer(p5);
		table.addPlayer(p6);
		table.addPlayer(p7);
		table.addPlayer(p8);
		table.addPlayer(p9);
		//table.addPlayer(p3); //test validation of table
		
		table.dealHands();
		
		Card[] flop = dealer.dealFlop();
		Card turn = dealer.dealTurnOrRiver();
		Card river = dealer.dealTurnOrRiver(); //for testing purposes the 
		
		for(Player p : table.getPlayers()){
			System.out.println(p.toStringWithHand());
		}
		
		System.out.print("Community: " + flop[0].toString() + flop[1].toString() + flop[2].toString());
		System.out.print(" " + turn.toString());
		System.out.print(" " + river.toString());
		
		
	}

}
