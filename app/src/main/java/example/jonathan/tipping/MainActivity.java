/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.util.Log;
import java.lang.String;
import java.util.ArrayDeque;
import java.util.Queue;


// TODO: change in out to stack variables.
// TODO: minimize static variables. Static fields are only used if you want persistent variables used across full runtime among ALL activities.
// TODO: IF static variable is associated to ACTIVITY life cycle, then MAKE IT LOCAL.
// TODO: when activity calls onDestroy(): class loaders are NOT unloaded. Thus, static data is NOT collected.

public class MainActivity extends AppCompatActivity
{
    private static final boolean DEBUG = true;
    private static final String ACTIVITY = "ACTIVITY_MAIN";

    //TODO: redo make it local because these depends on activity lifecycle.
    private final static InputViews in = new InputViews();
    private final static OutputViews out = new OutputViews();

    /*
    SETTER
    // MY_PREFS_NAME - a static String variable like:
//public static final String MY_PREFS_NAME = "MyPrefsFile";
SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
 editor.putString("name", "Elena");
 editor.putInt("idName", 12);
 editor.apply();
     */
/*
GETTER
SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
String restoredText = prefs.getString("text", null);
if (restoredText != null) {
  String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
  int idName = prefs.getInt("idName", 0); //0 is the default value.
}
 */

    public static InputViews getInputViews() {return in;}
    public static OutputViews getOutputViews(){return out;}

    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }

    /*
        Initialize all ui elements / construct listeners.
    */
    private void initListeners(SharedPreferences dataGetter, SharedPreferences.Editor dataSetter)
    {
        // root component view
        ViewGroup root = findViewById(R.id.main_view);

        // bfs all children nodes of vg.
        Queue<ViewGroup> que = new ArrayDeque<>();
        que.add(root);

        while(que.peek() != null)
        {
            ViewGroup vg = que.remove();
            for(int i = 0; i < vg.getChildCount(); i++)
            {
                View v = vg.getChildAt(i);
                if(v instanceof ViewGroup)
                    que.add((ViewGroup) v);

                else if(v instanceof EditText)
                {
                    ((EditText)v).setOnEditorActionListener(EtEditorListener.getInstance());
                    v.setOnFocusChangeListener(EtOnFocusChangeListener.getInstance());
                }
                else if(v instanceof Switch)
                {
                    ((Switch) v).setOnCheckedChangeListener(SwOnCheckedChangedListener.getInstance());
                }
                else if(v instanceof Button)
                {
                    v.setOnClickListener(BtOnClickListener.getInstance());
                }
                else
                {
                    if(v instanceof TextView)
                        continue;
                    Log.d("MAINACTVITY", "!!! Initialize v  was not found: " + v.getClass().toString());
                   // throw new IllegalArgumentException();
                }
            }
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(DEBUG)
            Log.d(ACTIVITY, "onStart");
    }

    public void onResume()
    {
        super.onResume();
        //saving edit text fields.
       // Log.d("MAIN_ACTIVITY", "------------ initial values " + et_strings.get(R.id.teBill) + et_strings.get(R.id.etTipPer) + size);
        //keyboard open close for edittext
        if(DEBUG)
            Log.d(ACTIVITY, "onResume");
    }

    public void onPause()
    {
        super.onPause();
        if(DEBUG)
            Log.d(ACTIVITY,"onPause");
    }

    public void onStop()
    {
        super.onStop();
        if(DEBUG)
            Log.d(ACTIVITY, "onStop");
    }
    public void onDestroy()
    {
        super.onDestroy();
        if(DEBUG)
            Log.d(ACTIVITY,"onDestroy");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(DEBUG)
            Log.d(ACTIVITY, "OnCreate");

        super.onCreate(savedInstanceState);

        // dataLoader(savedInstanceState);

        // set the user interface layout for this activity
        // the layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.activity_main);

        //init data loaders
        // instantiated here because persistent data loader is tied to this activity
        final SharedPreferences dataLoaderGetter = getSharedPreferences(MainActivity.class.getSimpleName(), MODE_PRIVATE);
        final SharedPreferences.Editor dataLoaderSetter = getSharedPreferences(MainActivity.class.getSimpleName(), MODE_PRIVATE).edit();

        // root component view
        ViewGroup root = findViewById(R.id.main_view);
        // initialize all text views and puts them in a model.
        in.parseAllTextViews(root);
        //Calc.getInstance().calc(v);
        out.outputAllTextView(root);
        initListeners(dataLoaderGetter, dataLoaderSetter);
    }

    // This callback is called only when there is a saved instance that is previously saved by using
    // onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the one used in onCreate().

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(DEBUG)
            Log.d(ACTIVITY,"onRestoreInstanceState");
       // teBill.setText(savedInstanceState.getString(TE_BILL_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(DEBUG)
            Log.d(ACTIVITY, "onSaveInstanceState");
        // outState.putString(GAME_STATE_KEY, gameState);
      //  outState.putString(TE_BILL_KEY, teBill.getText().toString());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}