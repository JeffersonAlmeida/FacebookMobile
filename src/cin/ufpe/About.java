package cin.ufpe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       TextView tv = new TextView(this);
       tv.setText("By Jefferson Almeida, CIn-UFPE.");
       setContentView(tv);
   }
}