import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws NoCardsRemainingException, CommunityCardsAlreadyDealtException {
		
		//Dealer dealer = new Dealer();
		int[] blinds = {1,2};
		Table table = new Table(9, blinds);
		
		Player p1 = new Player("a", 40000);
		Player p2 = new Player("b", 75000);
		Player p3 = new Player("c", 31230);
		Player p4 = new Player("d", 55000);
		Player p5 = new Player("e", 10250);
		Player p6 = new Player("f", 52000);
		Player p7 = new Player("g", 30000);
		Player p8 = new Player("h", 75000);
		Player p9 = new Player("i", 11250);
		
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
		
		
		
		ArrayList<Card> cc = table.dealComCards();
		System.out.print("\nCommunity: " + cc.get(0).toString() + cc.get(1).toString() + cc.get(2).toString());
		System.out.print(" " + cc.get(3).toString());
		System.out.print(" " + cc.get(4).toString());
		System.out.println();
		
		String[] handRanks = table.analyseHand(table.getPlayers(), table.getComCards());
		
		for(int i=0; i<table.getPlayers().size(); i++){
			System.out.println(table.getPlayers().get(i).toStringWithHand()
					+ "  ("
					+ handRanks[i]
					+ ")"
					);
		}
		
		System.out.println("\nDone");
	}

}
