package example.jonathan.tipping;

import android.app.Service;
import android.content.Context;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import android.util.Log;
import android.view.inputmethod.*;
import android.view.KeyEvent;
import android.text.TextWatcher;
import java.lang.StringBuilder;
import java.lang.String;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/*
    import android.view.View;
    import android.view.MotionEvent;
    import android.view.View.*;
    import android.content.Intent;
*/

/*
ANDROIDDDDDDD  I LOVE YOU!


 */
public class MainActivity extends AppCompatActivity
{
    private static final boolean DEBUG = true;
    public static SoftKeyboard softKeyboard;



    private static final String ACTIVITY = "ACTIVITY_MAIN";
    static final String TE_BILL_KEY = "TE_BILL_KEY";

    static String billStr = ""; //need to save original value from when keyboard is up.
    static String tipPer = "" ;
    static String size = "";

    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }
    // need to initialize default values to static strings (goto top of the
    // file) for when the app first loads and hit onCreate(). default string
    // value is null
    private void initDefaultValues(EditText teBill, EditText etTipPer, EditText etSize)
    {
        MainActivity.billStr = teBill.getText().toString();
        MainActivity.tipPer = etTipPer.getText().toString();
        MainActivity.size = etSize.getText().toString();
    }
    /*
        Initialize all ui elements / construct listeners.
    */
    private void initialize()
    {
        //saving edit text fields.
        final EditText teBill = findViewById(R.id.teBill);
        final EditText etTipPer = findViewById(R.id.etTipPer);
        final EditText etSize = findViewById(R.id.etSize);
        initDefaultValues(teBill, etTipPer, etSize);
        //keyboard open close for edittext
        ConstraintLayout mainLayout = findViewById(R.id.main_view); // You must use the layout root
        InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);

        softKeyboard = new SoftKeyboard(mainLayout, im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged()
        {
            //onclick in edit text cursor blink end
            @Override
            public void onSoftKeyboardHide()
            {
                // Log.d("ACTIVITY_MAIN", "KEYBOARD HIDE ET1: " );
            }

            //onclick in edit text cursor blink start.
            @Override
            public void onSoftKeyboardShow()
            {
                 Log.d("ACTIVITY_MAIN", "OnSoftKeyBoardShowStart:");
                final EditText v = (EditText)getCurrentFocus();

                //You have to move the portion of the background task that updates the UI onto
                // the main thread
                runOnUiThread(new Runnable()
                {
                    /*
                       input:
                        Bill, TipPer, Size for et1, et2 depending on v.
                        if v is tipPer, et1 = Bill, et2 = TipPer. left to right.

                       output:
                        String to be saved.
                    */
                    private String resetEditText(ArrayList<EditText> et, ArrayList<String> str)
                    {
                        if(et.size() == 0 || str.size() == 0)
                            throw new IllegalArgumentException();

                        EditText v = et.get(0);
                        for(int i = 1; i < et.size(); i++)
                        {
                            if(et.get(i) == v)
                                continue;

                            EditText ele = et.get(i);
                            // case for when click one edit text then click another edit text
                            // when such a case happens, the previous edit text would still be clear
                            // the previous value has already been saved. like magic.
                            if (ele.getText().length() == 0)
                                ele.setText(str.get(i-1));
                        }
                        return v.getText().toString();
                    }

                    @Override
                    public void run()
                    {
                        debugL("onSoftKeyboardShow run");
                        //0th element string dummy node for case.
                        ArrayList<EditText> et = new ArrayList<EditText>(Arrays.asList(v, teBill, etTipPer, etSize));
                        ArrayList<String> str = new ArrayList<>(Arrays.asList(MainActivity.billStr, MainActivity.tipPer, MainActivity.size));
                        // clear edittext when user onclick, and store current string as default.
                        switch (v.getId())
                        {
                            case R.id.teBill:
                                MainActivity.billStr = resetEditText(et, str);
                                break;

                            case R.id.etTipPer:
                                debugL("before resetEdit " + tipPer);
                                MainActivity.tipPer = resetEditText(et, str);


                                if (Looper.myLooper() == Looper.getMainLooper())
                                    debugL("MAINTHREADDDD onSoftKeyboard " + tipPer);
                                else
                                    debugL("NOT MAIN onSoftKeyboard " + tipPer);
                                break;

                            case R.id.etSize:
                                MainActivity.size = resetEditText(et,str);
                                Log.d("ACTIVITY_MAIN", "TEMP: " + v.getText().toString());

                        }
                        Log.d("ACTIVITY_MAIN", "*** softkeyboard ***  After cases: " + size);
                        MainActivity.debugL("Bill: " + MainActivity.billStr);
                        MainActivity.debugL("size:" + size);
                        MainActivity.debugL("tipPer:" + MainActivity.tipPer);
                        v.setText("");
                    }
                });
            }
        });

        teBill.setOnEditorActionListener(UIHandler.getEditTextListener());
        etTipPer.setOnEditorActionListener(UIHandler.getEditTextListener());
        etSize.setOnEditorActionListener(UIHandler.getEditTextListener());
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
       // Log.d("MAIN_ACTIVITY", "------------ initial values " + billStr + tipPer + size);
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
        softKeyboard.unRegisterSoftKeyboardCallback();
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