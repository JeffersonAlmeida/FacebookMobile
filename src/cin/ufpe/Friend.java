package cin.ufpe;

	public class Friend {
	    	
		private String name;
    	private String id;
    	private int rank = 0;
	
	    public Friend(String n, String i, int r) {
		    setName(n);
		    setId(i);
		    setRank(r);
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}
	    
	}