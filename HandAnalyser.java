import java.util.ArrayList;

/*
 * A private class that only the dealer can use.
 * The dealer uses this to analyse hands
 */
public class HandAnalyser{
	ArrayList<Card> hand;
	int flushSuitValue, straightHighValue;
	boolean wheel, straight, flush;

	public HandAnalyser(ArrayList<Card> hand){
		this.hand = hand;
		flushSuitValue = -1;
		straightHighValue = -1;
		wheel = false;
		flush = false;
		straight = false;
	}

	/*
	 * Private method to check if there is a straight flush present
	 */
	public boolean straightFlushCheck(ArrayList<Card> hand){
		if(!straight || !flush) return false;

		ArrayList<Card> flushOnlyHand = new ArrayList<Card>(); //separate the cards which are of the flush
		for(Card c : hand)
			if(c.getSuit() == flushSuitValue)
				flushOnlyHand.add(c);
		hand = flushOnlyHand;

		int correct = 0;
		if(!wheel){
			int i=0;
			while(i < hand.size()-5+1){
				if(hand.get(i).getValue() != hand.get(i+1).getValue()+1){
					i++;
					continue;
				}else if(hand.get(i).getValue() == hand.get(i+1).getValue()+1){		//[AQ987]65  A[Q9876]5   AQ[98765]
					int j=i+1;
					while(j < i+4){
						if(hand.get(j).getValue() != hand.get(j+1).getValue()+1) {
							correct=0;
							break;
						} else if(hand.get(j).getValue() == hand.get(j+1).getValue()+1) {
							correct++;
							j++;
							if(correct>=3){
								straightHighValue = hand.get(i).getValue();
								return true;
							}
						}
					}
				}
			}

		}else{ //if is wheel straight
			if(hand.get(0).getValue()==12 && hand.get(hand.size()-1).getValue()==0
					&& hand.get(hand.size()-2).getValue()==1 && hand.get(hand.size()-3).getValue()==2
					&& hand.get(hand.size()-4).getValue()==3)
				return true;
		}

		return false;
	}

	/*
	 * Private method to check the occurrence of quads, trips, and pairs
	 */
	public int[] valueOccurrence(ArrayList<Card> hand){
		int trips=0,pairCount=0;
		//0123456
		for(int i=0; i<hand.size()-1;){  //AAAA432   AAA9964
			int occ = 0;
			int currentVal = hand.get(i).getValue();

			for(int j=i+1; j < hand.size()-1;j++){ //loop through all elements after the current
				if(hand.get(j).getValue() == currentVal){
					occ++;
					if(occ==4){
						int[] quads = {1,0,0};
						return quads;
					}
				}else{
					i=j;
				}
			}

			if(occ==3) trips++;
			if(occ==2) pairCount++;
		}

		int[] occ = {0,trips, pairCount};

		return occ;
	}

	/*
	 * Private method to check if a straight is present
	 */
	public boolean straightCheck(ArrayList<Card> hand){
		for(int i=0; i<hand.size(); i++){	//remove any repetitive values in the hand
			Card current = hand.get(i);

			for(int j=i+1; j<hand.size(); j++)
				if(current.getValue()==hand.get(j).getValue())
					hand.remove(j);
		}

		if(hand.size() < 5){ //impossible to be a straight
			straight = false;
			return false;
		}

		int i = 0;
		int correct = 0;

		while(i < hand.size()-5+1){
			if(hand.get(i).getValue() != hand.get(i+1).getValue()+1){
				i++;
				continue;
			}else if(hand.get(i).getValue() == hand.get(i+1).getValue()+1){		//[AQ987]65  A[Q9876]5   AQ[98765]
				int j=i+1;
				while(j<i+4){
					if(hand.get(j).getValue() != hand.get(j+1).getValue()+1) {
						correct=0;
						break;
					} else if(hand.get(j).getValue() == hand.get(j+1).getValue()+1) {
						correct++;
						j++;
						if(correct>=3){
							straightHighValue = hand.get(i).getValue();
							return true;
						}
					}
				}
			}
		}

		return wheelCheck(hand); //not a normal high straight, check for a wheel.		
	}

	/*
	 * Private method to detect a wheel straight 5432A (where the ace is treated as a 1)
	 */
	public boolean wheelCheck(ArrayList<Card> hand){
		if(!(hand.get(0).getValue()==12) || !(hand.get(hand.size()-1).getValue()==0))//if A and 2 in hand
			return false;

		//we can do this because we have removed any duplicates in both straightFlushCheck and straightCheck
		//			3												4										5
		if(hand.get(hand.size()-2).getValue()==1 && hand.get(hand.size()-3).getValue()==2 && hand.get(hand.size()-4).getValue()==3)
			return true;

		return false;
	}

	/*
	 * Private method to check if a flush is present in the hand
	 */
	public boolean flushCheck(ArrayList<Card> hand){
		int s=0,h=0,d=0,c=0;
		for(Card card : hand){
			switch(card.getSuit()){
			case 0: s++;break;
			case 1: h++;break;
			case 2: d++;break;
			case 3: c++;break;
			default: System.err.println("Error: flushCheck() method has taken an invalid path");break;
			}
		}

		if(s>=5){
			flushSuitValue = 0; flush = true;
		} else if(h>=5){
			flushSuitValue = 1; flush = true;
		} else if(d>=5){
			flushSuitValue = 2; flush = true; return true;
		} else if(c>=5){
			flushSuitValue = 3; flush = true;
		}

		flush = false;

		return flush;
	}

}