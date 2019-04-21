package example.jonathan.tipping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.*;
import android.util.Log;
import android.view.inputmethod.*;
import android.view.KeyEvent;
import android.text.TextWatcher;
import java.lang.StringBuilder;
import java.lang.String;

/*
    import android.view.View;
    import android.view.MotionEvent;
    import android.view.View.*;
    import android.content.Intent;
*/
public class MainActivity extends AppCompatActivity {
    private static final boolean DEBUG = true;
    private static final String ACTIVITY = "MainActivity.java";

    static final String TE_BILL_KEY = "TE_BILL_KEY";
    static String billStr;
    static String tipPer;
    // static final string GAME_STATE_KEY = "fff";

    EditText teBill;
    EditText etTipPer;
    TextView tvTotalNum;
    TextView tvTipNum;

    // some transient state for the activity instance
    // String gameState;

    /*
    loads saved data from savedInstanceState for when system destroys the activity
    TODO: persist data loader
    input: savedInstanceState
     */
    private void dataLoader(Bundle savedInstanceState)
    {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy


        // recovering the instance state
        //   if (savedInstanceState != null) {
        //        gameState = savedInstanceState.getString(GAME_STATE_KEY);
        //   }

    }

    /*
    Initialize all ui elements / construct listeners.

    */
    private void initialize()
    {
        tvTotalNum = findViewById(R.id.tvTotalNum);

        teBill = findViewById(R.id.teBill);
        /*
        TODO: (v, actionId, event) -> {codeblock from if(DEBUG)? } ????does that really work????
        public boolean onEditorAction delete line.
         */
        teBill.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(DEBUG)
                    Log.d(ACTIVITY, "onEditorAction");
                Log.d(ACTIVITY, "Action id " + actionId);

                switch(actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        if (DEBUG)
                            Log.d(ACTIVITY, "IME_ACTION_DONE");
                        /*
                        *.*.+ - false
                        .* = 0.any
                        0000 = 0.00
                        every thuosand "," format
                         */

                        String tmpStr = teBill.getText().toString();
                        Log.d(ACTIVITY, "82: " + tmpStr);
                        /*
                        TODO: stream().filter(match regex).map(do something)
                        */

                        /*
                        if(tmpStr.matches("(.*(\\.).*(\\.).*)+"))
                        {
                            teBill.setText(MainActivity.billStr);
                        }
                        else if(tmpStr.matches("(.*(\\.).*)"))
                        {
                            StringBuilder s = new StringBuilder(tmpStr);
                            s = s.deleteCharAt(0);
                            //String.Format() ?????????
                            MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                            teBill.setText(MainActivity.billStr);
                        }
                        // need other  cases
                        else
                        {
                            StringBuilder s = new StringBuilder(tmpStr.replaceAll(,""));
                            s = s.deleteCharAt(0);

                            MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                            teBill.setText(MainActivity.billStr);
                        }
                        */
                        Log.d(ACTIVITY, "110: ");
                        StringBuilder s = new StringBuilder(tmpStr);
                        //to handle char $  s = s.deleteCharAt(0);
                        Log.d(ACTIVITY, "113: ");
                        MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                        // teBill.setText(MainActivity.billStr);
                        Log.d(ACTIVITY, "116: ");
                        calc();
                        Log.d(ACTIVITY, "117: ");
                        return true;
                }
                return false;
            }
        });

        /*
            TODO: return false for unhandled event such as: MotionEvent.AMBIGUOUS_GESTURE
             https://developer.android.com/reference/android/view/MotionEvent
         */
        /*
        teBill.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(DEBUG)
                    Log.d(ACTIVITY, "text edit bill listener: onClick");
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        MainActivity.billStr = teBill.getText().toString();
                        Log.d(ACTIVITY, "BILL:" + MainActivity.billStr);
                        return true;

                    case MotionEvent.ACTION_UP:
                        MainActivity.super.findViewById(R.id.teBill).performClick();
                        return true;

                    default:
                }
                return false;
            }
        });

        teBill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performClick();
            }
        });
        */








        /*
        THE STRUGGLE IS SOOOOOO REAL!!
         */
        /*
        teBill.addTextChangedListener(
                new TextWatcher(){
                    boolean state = false;
                    String str;

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(DEBUG)
                            Log.d(ACTIVITY, "afterTextChanged");

                        if(state) {
                            state = false;
                            return;
                        }

                        state = true;

                        if(s.length() > 0 && s.charAt(0) == '$') {
                            if(DEBUG)
                                Log.d(ACTIVITY, s.toString());
                            str = s.toString();
                        }
                    }
                    // check store character flag.

                    // use switch statements based on flag. $, or default case: num - if(Character.isDigit())
                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                        //check for $ or no
                        // set flag for it.

                        // System.out.println(s.toString)
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // if(s.length() != 0)
                        //    field2.setText("");
                    }
                }
        );
        */


        etTipPer = findViewById(R.id.etTipPer);
        etTipPer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*
                    if(DEBUG)
                        Log.d(ACTIVITY, "onEditorAction");
                    Log.d(ACTIVITY, "Action id " + actionId);
                 */
                switch(actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        if (DEBUG)
                            Log.d(ACTIVITY, "IME_ACTION_DONE");


                        String tmpStr = etTipPer.getText().toString();


                        StringBuilder s = new StringBuilder(tmpStr);
                        //  to handle char $  s = s.deleteCharAt(0);
                        MainActivity.tipPer = s.toString().format("%2f", Double.parseDouble(s.toString()));
                        //  etTipPer.setText(MainActivity.tipPer);
                        calc();

                        /*
                            call method to display total/ tip
                         */
                        return true;
                }
                return false;
            }
        });


    }

    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }


    private void calc ()
    {
        Log.d(ACTIVITY, "256");
        if(etTipPer == null)
            debugL("264: etTipper NULL ");
//FEEEEELS BADDDD MANNNNNN
        tvTotalNum = findViewById(R.id.tvTotalNum);
        tvTipNum = findViewById(R.id.tvTipNum);
/*
TODO: broke because tipPerString = not parsed from tvTipPer and stored as static field.
 */

        if(tvTotalNum == null)
            debugL("265 tipPer NULL ");

        debugL("267: TIPPER!!!!!!PLZ :)"+ MainActivity.tipPer);
        // debugL(Double.toString((double)Double.parseDouble(MainActivity.billStr)));

        if(etTipPer != null) {
            String TipNumResult = Double.toString((double) Calc.calcTipNum((double) Double.parseDouble(MainActivity.billStr), (double) Double.parseDouble(etTipPer.getText().toString())));
            tvTipNum.setText(TipNumResult);

            String TotalNumResult = Double.toString((double)Calc.calcTotal((double)Double.parseDouble(MainActivity.billStr), (double)Double.parseDouble(TipNumResult)));
            tvTotalNum.setText(TotalNumResult);
        }






        // etTipPer.setText(MainActivity.tipPer);
        // etTipPer.setText(MainActivity.tipPer);
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
        dataLoader(savedInstanceState);
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
        teBill.setText(savedInstanceState.getString(TE_BILL_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(DEBUG)
            Log.d(ACTIVITY, "onSaveInstanceState");
        // outState.putString(GAME_STATE_KEY, gameState);
        outState.putString(TE_BILL_KEY, teBill.getText().toString());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}