
public class Main {

	public static void main(String[] args) throws NoCardsRemainingException, CommunityCardsAlreadyDealtException {
		
		Dealer dealer = new Dealer();
		int[] blinds = {1,2};
		Table table = new Table(9, blinds);
		
		Player p1 = new Player("a", 4000);
		Player p2 = new Player("b", 7500);
		Player p3 = new Player("c", 3123);
		Player p4 = new Player("d", 5500);
		Player p5 = new Player("e", 10250);
		Player p6 = new Player("f", 5200);
		Player p7 = new Player("g", 300);
		Player p8 = new Player("h", 750);
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
		
		Card[] flop = dealer.dealFlop();
		Card turn = dealer.dealTurnOrRiver();
		Card river = dealer.dealTurnOrRiver();
		
		System.out.print("\nCommunity: " + flop[0].toString() + flop[1].toString() + flop[2].toString());
		System.out.print(" " + turn.toString());
		System.out.print(" " + river.toString());
		System.out.println();
		
		for(int i=0; i<table.getPlayers().size(); i++){
			System.out.println(table.getPlayers().get(i).toStringWithHand());// + "    (" + handRank[i] + ")" );
		}
		
//		String[] handRank = dealer.analyseHand(table.getPlayers(), dealer.getCommunityCards());
//		
//		for(int i=0; i<table.getPlayers().size(); i++){
//			System.out.println(table.getPlayers().get(i).toStringWithHand() + "    (" + handRank[i] + ")" );
//		}
		
		
		
		
	}

}
