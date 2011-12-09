package cin.ufpe;

import java.util.Comparator;

public class NameComparator implements Comparator<Friend> {
	public NameComparator(){
		super();
	}
	public int compare(Friend primeiro, Friend segundo) {
		String nomePrimeiro = primeiro.getName();
		String nomeSegundo = segundo.getName();		
		return nomePrimeiro.compareTo(nomeSegundo);
	}
}
