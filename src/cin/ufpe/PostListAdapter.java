package cin.ufpe;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PostListAdapter extends ArrayAdapter<Post>{
	
	private LayoutInflater inflater;
	private int viewResourceId;
	
	public PostListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		 inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         viewResourceId = textViewResourceId;
	}
	public void addAll(ArrayList<Post> posts){
		for(Post p: posts){
			add(p);
		}
	}
	
	// O método mais importante da classe.
	public View getView(int position, View convertView, ViewGroup parent){
		 convertView = inflater.inflate(viewResourceId, null);
		 TextView post = (TextView)convertView.findViewById(R.id.post);
		 TextView nomePost = (TextView)convertView.findViewById(R.id.nomePost);
		 post.setText( String.valueOf(getItem(position).getPost()));
		 nomePost.setText(String.valueOf(getItem(position).getNomePost()));		
		 Button botaoUp = (Button) convertView.findViewById(R.id.botaoUp);
		 botaoUp.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
								
			}
		});
		 return convertView;		
	}
}
