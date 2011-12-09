package cin.ufpe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cin.ufpe.FriendsList.FriendsListAdapter;

import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

public class PostList extends ListActivity {
	
    private Handler mHandler = new Handler();
    private FaceTab facetab;
    private TextView tv;
    
    private Button botaoUp;
    private Button botaoDown;
    private TextView post;
    private TextView nomePost;      
    private PostListAdapter postList;

    public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     facetab = (FaceTab) this.getParent();
	     botaoUp = (Button) findViewById(R.id.botaoUp);	     
         botaoDown = (Button) findViewById(R.id.botaoDown);            
         post = (TextView) findViewById(R.id.post);
         nomePost = (TextView) findViewById(R.id.nomePost);
	     setListDataSource();
	     getFacebookPosts();
    }

    // um dos métodos mais importantes.
    private void setListDataSource() {
    		ListView listView = getListView();
    		postList = new PostListAdapter(this, R.layout.post);
    		listView.setAdapter(postList);
    		listView.setTextFilterEnabled(true);
	}   
    
	private void setTextView() {    	
            ScrollView s = new ScrollView(this);	        
            tv = new TextView(this);
			setContentView(s);			 
    }

private void getFacebookPosts() {
     facetab.getmAsyncRunner().request("me/feed", new PostsListener());
}

public class PostsListener implements RequestListener {
    public void onComplete(final String response, Object state) {
            mHandler.post(new Runnable() {
                    public void run() {
                            try {
                            	    updateListDataSourceWithPosts(response);
                            } catch (JSONException e) {
                                    Log.e("Facebook","Formato não reconhecido: " + e.getMessage());
                            }
                    }
                    private void updateListDataSourceWithPosts(String response) throws JSONException {                    	  
                    	   ArrayList<Post> posts = facetab.getPosts();           	  
                           JSONArray jason = new JSONObject(response).getJSONArray("data");
                           for (int i = 0; i < jason.length(); i++) {
                                   if (jason.getJSONObject(i).has("story")) {
                                           String post = (String) jason.getJSONObject(i).get("story");
                                           String nomePost = (String) jason.getJSONObject(i).getJSONObject("from").get("name");
                                           Post p = new Post(nomePost, post);
                                           posts.add(p);
                                   }
                           }
                           postList.addAll(posts);						
					}
            });
    }
    public void onFacebookError(FacebookError e, Object state) {}
    public void onFileNotFoundException(FileNotFoundException e, Object state) {}
    public void onIOException(IOException e, Object state) {}
    public void onMalformedURLException(MalformedURLException e, Object state) {}
}
}
