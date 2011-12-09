package cin.ufpe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class Posts extends Activity {
    private Handler mHandler = new Handler();
    private FaceTab facetab;
    private TextView tv;

    public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     facetab = (FaceTab) this.getParent();
     setTextView();
     getFacebookPosts();
    }

    private void setTextView() {
            ScrollView s = new ScrollView(this);
    tv = new TextView(this);
    s.addView(tv);
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
                                    String posts = parsePosts();
                                    showPosts(posts);
                            } catch (JSONException e) {
                                    Log.e("Facebook","Formato não reconhecido: " + e.getMessage());
                            }
                    }
                            private void showPosts(String posts) {
                                    tv.setText(posts);
                            }
                    private String parsePosts() throws JSONException {
                            String r = "";
                            JSONArray js = new JSONObject(response).getJSONArray("data");
                            for (int i = 0; i < js.length(); i++) {
                                    if (js.getJSONObject(i).has("story")) {
                                            String s = (String) js.getJSONObject(i).get("story");
                                            String n = (String) js.getJSONObject(i).getJSONObject("from").get("name");
                                            r = r + " >>> "+s+" >>> "+n+" >>>\n\n\n";
                                    }
                            }
                            return r;
                            }
            });
    }
    public void onFacebookError(FacebookError e, Object state) {}
    public void onFileNotFoundException(FileNotFoundException e, Object state) {}
    public void onIOException(IOException e, Object state) {}
    public void onMalformedURLException(MalformedURLException e, Object state) {}
}
}
