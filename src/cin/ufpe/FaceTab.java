package cin.ufpe;

import java.util.ArrayList;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.widget.TabHost;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;


public class FaceTab extends TabActivity {
   
	private Facebook facebook = new Facebook("147547492015406");
    private AsyncFacebookRunner mAsyncRunner;
    private SharedPreferences mPrefs;
    private ArrayList<Friend> friends = new ArrayList<Friend>();

    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            accessFacebook();
            accessUserInfo();
            TabHost tabHost = getTabHost();
            Resources res = getResources();
            addFriendsTab(tabHost, res);
            addPostsTab(tabHost, res);
            addAboutTab(tabHost, res);
            tabHost.setCurrentTab(0);
    }

    public Facebook getFacebook() {
            return this.facebook;
    }

    public AsyncFacebookRunner getmAsyncRunner() {
            return this.mAsyncRunner;
    }

    public ArrayList<Friend> getFriends() {
            return this.friends;
    }

    private void accessUserInfo() {
            mAsyncRunner = new AsyncFacebookRunner(facebook);
            mAsyncRunner.request("me", new meRequestListener());
    }

    private void addAboutTab(TabHost tabHost, Resources res)throws NotFoundException {
            Intent intent = new Intent().setClass(this, About.class);
            TabHost.TabSpec spec = tabHost.newTabSpec("About").setIndicator("About",res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
            tabHost.addTab(spec);
    }

    private void addPostsTab(TabHost tabHost, Resources res)
                    throws NotFoundException {
            Intent intent = new Intent().setClass(this, Posts.class);
            TabHost.TabSpec spec = tabHost.newTabSpec("Posts").setIndicator("Posts",
                                 res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
        tabHost.addTab(spec);
    }

    private void addFriendsTab(TabHost tabHost, Resources res)
                    throws NotFoundException {
            Intent intent = new Intent().setClass(this, FriendsList.class);
            TabHost.TabSpec spec = tabHost.newTabSpec("Friends").setIndicator("Friends",
                                       res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
            tabHost.addTab(spec);
    }

    private void accessFacebook() {
            mPrefs = getPreferences(MODE_PRIVATE);
    String access_token = mPrefs.getString("access_token", null);
    long expires = mPrefs.getLong("access_expires", 0);
    if(access_token != null) {
        facebook.setAccessToken(access_token);
    }
    if(expires != 0) {
        facebook.setAccessExpires(expires);
    }
    if(!facebook.isSessionValid()) {
             facebook.authorize(this, new String[] {"read_stream","offline_access","publish_stream"},
               new DialogListener() {
             public void onComplete(Bundle values) {
                 SharedPreferences.Editor editor = mPrefs.edit();
                 editor.putString("access_token", facebook.getAccessToken());
                 editor.putLong("access_expires", facebook.getAccessExpires());
                 editor.commit();
             }
             public void onFacebookError(FacebookError error) {}
             public void onError(DialogError e) {}
             public void onCancel() {}
          });
    }
    }

public class meRequestListener implements RequestListener {
    public void onComplete(String response, Object state) {}
    public void onFacebookError(FacebookError e, Object state) {}
    public void onFileNotFoundException(FileNotFoundException e, Object state) {}
    public void onIOException(IOException e, Object state) {}
    public void onMalformedURLException(MalformedURLException e, Object state) {}
}

}