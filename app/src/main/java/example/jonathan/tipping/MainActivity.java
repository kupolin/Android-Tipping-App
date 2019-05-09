package example.jonathan.tipping;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;
import android.util.Log;
import java.lang.String;
import java.util.HashMap;



/*
OnFocusChangeListener can be used to detect view onFocus
onFocus happens before onClick, right when view is clicked.
After view is focused, and user clicks inside the view, onClick is then called.

OnFocus can only happen OnFocus first gains focus and last gains focus of a view.

This is used to replace SoftKeyboard.java because SoftKeyboard.java detects the blinker inside the editText.
 */

public class MainActivity extends AppCompatActivity
{
    private static final boolean DEBUG = true;
    private static final String ACTIVITY = "ACTIVITY_MAIN";
    static final String TE_BILL_KEY = "TE_BILL_KEY";

    // create a hashmap where key: string, value: string. key is variable name
    // billStr, tipPerStr, sizeStr are all EditText Strings
    static HashMap<Integer, Number> et_strings;

    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }

    // need to initialize default values to static strings (goto top of the
    // file) for when the app first loads and hit onCreate(). default string
    // value is null
    private void initDefaultValues(EditText teBill, EditText etTipPer, EditText etSize)
    {
        /*
        et_strings = new HashMap<>();
        et_strings.put(R.id.teBill, teBill.getText().toString());
        et_strings.put(R.id.etTipPer, etTipPer.getText().toString());
        et_strings.put(R.id.etSize, etSize.getText().toString());
        */
    }

    /*
        Initialize all ui elements / construct listeners.
    */
    private void initialize()
    {
        InputViews in = new InputViews();
        // parses all text views and puts them in a model.
        in.parseAllTextViews((ViewGroup)findViewById(R.id.rootViewGroup));

        //saving edit text fields.
        final EditText teBill = findViewById(R.id.teBill);
        final EditText etTipPer = findViewById(R.id.etTipPer);
        final EditText etSize = findViewById(R.id.etSize);

        final Switch swSize = findViewById(R.id.swSize);

        final Button btTip1 = findViewById(R.id.btTip1);
        final Button btTip2 = findViewById(R.id.btTip2);
        final Button btTip3 = findViewById(R.id.btTip3);

        //initialize
       // initDefaultValues(teBill, etTipPer, etSize);


        //initialize edit text listeners
        teBill.setOnEditorActionListener(UIHandler.getEditTextEditorListener());
        etTipPer.setOnEditorActionListener(UIHandler.getEditTextEditorListener());
        etSize.setOnEditorActionListener(UIHandler.getEditTextEditorListener());

        //set onfocus listener
        teBill.setOnFocusChangeListener(UIHandler.getEditTextFocusListener());
        etTipPer.setOnFocusChangeListener(UIHandler.getEditTextFocusListener());
        etSize.setOnFocusChangeListener(UIHandler.getEditTextFocusListener());

        //initialize switch listener
        swSize.setOnCheckedChangeListener(UIHandler.getSwitchListener());

    //initialize button listeners
        btTip1.setOnClickListener(UIHandler.getButtonListener());
        btTip2.setOnClickListener(UIHandler.getButtonListener());
        btTip3.setOnClickListener(UIHandler.getButtonListener());
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