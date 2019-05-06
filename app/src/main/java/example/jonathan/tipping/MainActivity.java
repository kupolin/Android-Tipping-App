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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
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

    // create a hashmap where key: string, value: string. key is variable name
    // billStr, tipPerStr, sizeStr are all EditText Strings
    static LinkedHashMap<String, String> et_strings;
    /*
    static String billStr = ""; //need to save original value from when keyboard is up.
    static String tipPer = "" ;
    static String size = "";
    */
    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }
    // need to initialize default values to static strings (goto top of the
    // file) for when the app first loads and hit onCreate(). default string
    // value is null
    private void initDefaultValues(EditText teBill, EditText etTipPer, EditText etSize)
    {
        et_strings = new LinkedHashMap<>();
        et_strings.put("billStr", teBill.getText().toString());
        et_strings.put("tipPerStr", etTipPer.getText().toString());
        et_strings.put("sizeStr", etSize.getText().toString());
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

                        Object[] - array of string values from et_strings.

                       output:
                        String to be saved.
                    */
                    private String resetEditText(ArrayList<EditText> et, Object [] str)
                    {
                        if(et.size() == 0 || str.length == 0)
                            throw new IllegalArgumentException();

                        EditText v = et.get(0);
                        for(int i = 1; i < et.size(); i++)
                        {
                            if(et.get(i) == v)
                                continue;

                            EditText ele = et.get(i);
                            String eleStr = ele.getText().toString();
                            // case for when click one edit text then click another edit text
                            // when such a case happens, the previous edit text would still be clear
                            // the previous value has already been saved. like magic.
                            if (ele.getText().length() == 0)
                                ele.setText((String)str[i-1]);
                            // case when user type in edit text field i.e. 0021312, needs to be reparsed.
                            // only reparse if softkey ime action done was not clicked, since softkey reparses.

                            else if(!eleStr.equals(str[i-1]))
                            {
                                // bill needs to be parsed as double,
                                // all others are in the form of int.
                                String s = ele.getId()== R.id.teBill ? String.format(new Locale("en"), "%.2f", Double.parseDouble(eleStr))
                                                : eleStr.replaceFirst("^0+(?!$)","");
                                if (ele.getId() == R.id.etSize)
                                    s = s.replaceFirst("0", "1");
                                ele.setText(s);
                                // update the stupid hashmap.
                            }
                        }
                        return v.getText().toString();
                    }

                    @Override
                    public void run()
                    {
                        debugL("onSoftKeyboardShow run");
                        //0th element string dummy node for case.
                        ArrayList<EditText> et = new ArrayList<EditText>(Arrays.asList(v, teBill, etTipPer, etSize));
                        Object [] str_array = et_strings.values().toArray();
                        // clear edittext when user onclick, and store current string as default.
                        switch (v.getId())
                        {
                            case R.id.teBill:
                                //corner case for 0000s
                                String s = resetEditText(et,str_array);
                                //TODO: refactor this with UIhandler.java edit text listener output format.
                                // need to update str arraylist for when it is the same view being refocused. i.e. when keyboard is always up.
                                // doesn't apply for when clicked done and refocus because a new runnable will be created in that instance.
                                //This saves on Open default value.
                                // need to reparse other strings edit text if user has typed something when keyboard is open.
                                MainActivity.et_strings.put("billStr", String.format(new Locale("en"), "%.2f", Double.parseDouble(s)));
                                debugL("RUN_ TEBILL ___ : " + MainActivity.et_strings.get("billStr"));




                                break;

                            case R.id.etTipPer:
                                debugL("before resetEdit " + et_strings.get("tipPerStr"));
                                s = resetEditText(et, str_array).replaceFirst("^0+(?!$)","");
                                MainActivity.et_strings.put("tipPerStr", s);
                                // need to update str arraylist for when it is the same view being refocused. i.e. when keyboard is always up.
                                // doesn't apply for when clicked done and refocus because a new runnable will be created in that instance.


                                if (Looper.myLooper() == Looper.getMainLooper())
                                    debugL("MAINTHREADDDD onSoftKeyboard " + et_strings.get("tipPerStr"));
                                else
                                    debugL("NOT MAIN onSoftKeyboard " + et_strings.get("tipPerStr"));
                                break;

                            case R.id.etSize:
                                s = resetEditText(et,str_array).replaceFirst("^0+(?!$)","");
                                et_strings.put("sizeStr", s);
                                // need to update str arraylist for when it is the same view being refocused. i.e. when keyboard is always up.
                                // doesn't apply for when clicked done and refocus because a new runnable will be created in that instance.

                                Log.d("ACTIVITY_MAIN", "TEMP: " + v.getText().toString());

                        }
                        Log.d("ACTIVITY_MAIN", "*** softkeyboard ***  After cases: " + et_strings.get("sizeStr"));
                        MainActivity.debugL("Bill: " + MainActivity.et_strings.get("billStr"));
                        MainActivity.debugL("size:" + et_strings.get("sizeStr"));
                        MainActivity.debugL("tipPer Str: "  + et_strings.get("tipPerStr"));
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
       // Log.d("MAIN_ACTIVITY", "------------ initial values " + et_strings.get("billStr") + et_strings.get("tipPerStr") + size);
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