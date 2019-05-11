/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.util.Log;
import java.lang.String;
import java.util.ArrayDeque;
import java.util.Queue;

public class MainActivity extends AppCompatActivity
{
    private static final boolean DEBUG = true;
    private static final String ACTIVITY = "ACTIVITY_MAIN";

    private final static InputViews in = new InputViews();
    private final static OutputViews out = new OutputViews();

    public static InputViews getInputViews() {return in;}
    public static OutputViews getOutputViews(){return out;}

    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }

    /*
        Initialize all ui elements / construct listeners.
    */
    private void initialize()
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

        // root component view
        ViewGroup root = findViewById(R.id.main_view);
        // initialize all text views and puts them in a model.
        in.parseAllTextViews(root);
        initialize();

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