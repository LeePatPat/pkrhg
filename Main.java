
public class Main {

	public static void main(String[] args) throws NoCardsRemainingException {
		
		Player player = new Player("Lee", 4000);
		Deck deck = new Deck();
		
		deck.shuffle();
		
		player.dealToPlayer(deck.dealCard(), deck.dealCard());
		
		Card[] playersCards = new Card[2];
		playersCards = player.getHoleCards();
		
		System.out.println(player.getName() + " has the " + playersCards[0].toString() + " and the " + playersCards[1].toString());
	}

}
