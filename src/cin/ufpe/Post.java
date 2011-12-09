package cin.ufpe;

public class Post {
	
	private String nomePost; // nome associado ao post
	private String post; // o proprio post
	public String getNomePost() {
		return nomePost;
	}
	public void setNomePost(String nomePost) {
		this.nomePost = nomePost;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return "Post [nomePost=" + nomePost + ", post=" + post + "]";
	}
	public Post(String nomePost, String post) {
		super();
		this.nomePost = nomePost;
		this.post = post;
	}
	public Post(){
		super();
	}

}
