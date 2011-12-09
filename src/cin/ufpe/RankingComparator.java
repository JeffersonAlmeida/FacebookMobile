package cin.ufpe;

import java.util.Comparator;

public class RankingComparator implements Comparator<Friend>{
	public RankingComparator() {
		super();
	}
	public int compare(Friend primeiro, Friend segundo) {
		int rankPrimeiro = primeiro.getRank();
		int rankSegundo = segundo.getRank();
		if(rankPrimeiro > rankSegundo){
			return 1;
		}else if (rankPrimeiro < rankSegundo){
			return -1;
		}else{
			return 0;
		}
	}
}
