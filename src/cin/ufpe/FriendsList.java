package cin.ufpe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;

import cin.ufpe.*;

public class FriendsList extends ListActivity {
       private Handler mHandler = new Handler();
       private FriendsListAdapter amigos;
       private FaceTab facetab;

   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       facetab = (FaceTab) this.getParent();
       setListDataSource();
       getFacebookFriends();
   }

   public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       facetab.getFacebook().authorizeCallback(requestCode, resultCode, data);
   }

   private void setListDataSource() {
               ListView lv = getListView();
               amigos = new FriendsListAdapter(this,R.layout.list_item);
               lv.setAdapter(amigos);
               lv.setTextFilterEnabled(true);
       }

       private void getFacebookFriends() {
       facetab.getmAsyncRunner().request("me/friends", new meFriendsListener());
       }

   public class FriendsListAdapter extends ArrayAdapter<Friend> {
       private LayoutInflater mInflater;
       private int mViewResourceId;
       public FriendsListAdapter(Context ctx, int viewResourceId) {
               super(ctx,viewResourceId);
               mInflater = (LayoutInflater)ctx.getSystemService(
                               Context.LAYOUT_INFLATER_SERVICE);
               mViewResourceId = viewResourceId;
       }
       public void addAll(ArrayList<Friend> friends) {
                       for (Friend f : friends) {add(f);}
               }
               public View getView(int position, View convertView, ViewGroup parent) {
               convertView = mInflater.inflate(mViewResourceId, null);
               TextView iv = (TextView)convertView.findViewById(R.id.tvv);
               iv.setText(getItem(position).getName());
               TextView tvv = (TextView)convertView.findViewById(R.id.idv);
               tvv.setText(String.valueOf(getItem(position).getRank()));
               return convertView;
       }
   }

       public class meFriendsListener implements RequestListener {
       public void onComplete(final String response, Object state) {
               mHandler.post(new Runnable() {
                       public void run() {
                               try {
                               updateListDataSourceWithFriends(response);
                               sortListDataSource();
                               imprimeDataSource();
                   } catch (JSONException e) {
                       Log.e("Facebook","Formato não reconhecido: " + e.getMessage());
                   }
               }
               private void sortListDataSource() {
            	       Comparator<Friend> nomeComparator = new NameComparator();
            	   	   amigos.sort(nomeComparator);
               }
               private void imprimeDataSource() {
            	   int i = 0 ;
            	   while(i< amigos.getCount()){
            		   Friend amigo = amigos.getItem(i);
            		   System.out.println("amigo = " + amigo.getName());
            		   i++;
            	   }
               }
               private void updateListDataSourceWithFriends(final String response) throws JSONException {
                       String r,id;
                       JSONArray js;
                       ArrayList<Friend> friends = facetab.getFriends();
                       js = new JSONObject(response).getJSONArray("data");
                       for (int i = 0; i < js.length(); i++) {
                       r = ((String) js.getJSONObject(i).get("name"));
                       id = ((String) js.getJSONObject(i).get("id"));                       
                       friends.add(new Friend(r,id,0));
               }
               amigos.addAll(friends);
               }
            });
       }
       public void onFacebookError(FacebookError e, final Object state) {}
       public void onFileNotFoundException(FileNotFoundException e,
               final Object state) {}
       public void onIOException(IOException e, final Object state) {}
       public void onMalformedURLException(MalformedURLException e,
               final Object state) {}
   }
}
