import java.util.ArrayList;

public class CustomDriver {

	public static void main(String[] args) throws NoCardsRemainingException, CommunityCardsAlreadyDealtException {
		
		//Dealer dealer = new Dealer();
		int[] blinds = {1,2};
		Table table = new Table(9, blinds);
		
		Player p1 = new Player("a", 40000);

		table.addPlayer(p1);
		
		Card testCard1 = new Card(3,12); //Ac
		Card testCard2 = new Card(3,0); //2c
		
		ArrayList<Card> customComCards = new ArrayList<Card>();
		customComCards.add(new Card(0,1)); //3s
		customComCards.add(new Card(0,2)); //4s
		customComCards.add(new Card(0,3)); //5s
		customComCards.add(new Card(0,4)); //6s
		customComCards.add(new Card(0,5)); //7s`
		
		table.setComCards(customComCards);
		
		p1.dealCardToPlayer(testCard1);
		p1.dealCardToPlayer(testCard2);
		
		System.out.print("\nCommunity: " + customComCards.get(0).toString() + customComCards.get(1).toString() + customComCards.get(2).toString());
		System.out.print(" " + customComCards.get(3).toString());
		System.out.print(" " + customComCards.get(4).toString());
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
