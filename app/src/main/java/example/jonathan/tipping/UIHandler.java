package example.jonathan.tipping;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.security.DomainCombiner;
import java.util.Locale;

/*
    Initializer / listener in which parses the ui strings from user input.
    should be called in the oncreate section / initialize.

    attachment of  listeners is in Activity. this is the listeners?
 */

//Widget Listeners? need to design it as a abstract extends?
public class UIHandler {
    //design inner classes button/edittext/textview for listener. Inputs
    /*

    edit text box: handles doubles
    ints
    strings later?

    */
    private static final Calc c = new Calc();
    private static final EditTextListener editTextListener = new EditTextListener();
    private static final SwitchListener swListener = new SwitchListener();

    private static boolean sw_flag = false;

    public static EditTextListener getEditTextListener() {
        return editTextListener;
    }


    private static class EditTextListener implements TextView.OnEditorActionListener
    {
        private EditTextListener(){}
        //i want to create a function for each edit text to call on to handle things for different activity.
        //this function should be passed in. function or object.
        //can follow softkeyboard style with interface which gets overloaded that gets created and used.

        // Because size is number, it will always pass double check. number subset check of double.
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d("ACTIVITY_MAIN", "hi"  );
            switch(actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    Log.d("ACTIVITY_MAIN", "done"  );
                    /*
                        checks
                        *.*.+ - false
                        . invalid
                        nothing is invalid

                        formatting

                        .* = 0.any
                        0000 = 0.00
                       TODO: every thousand "," format
                    */
                        String etStr = v.getText().toString();
                    Log.d("ACTIVITY_MAIN", "etStr After"  );

                        /*
                        TODO: stream().filter(match regex).map(do something)
                        */

                        StringBuilder s = new StringBuilder(etStr);
                        //to handle char $  s = s.deleteCharAt(0);


                    //TODO: LEGAL
                        //&& check with regex statement if legal.
                        //if not then need to set value to default value.

                    switch (v.getId())
                    {
                        case R.id.teBill:

                            //only the edit text box have it.
                            if (!etStr.matches("(\\.)*"))
                                //default local english for bugs.

                            MainActivity.et_strings.put("billStr", String.format(new Locale("en"), "%.2f", Double.parseDouble(s.toString())));

                            //for when there are more than one 0 at the start of the value. i.e. 00000.123 or 000213
                            // MainActivity.et_strings.get("tipPerStr")= MainActivity.et_strings.get("billStr").replaceFirst("^0+(\\.)", "0.");

                            v.setText(MainActivity.et_strings.get("billStr"));
                            break;

                        case R.id.etTipPer:
                            /*
                              replacing leading 0 with negative lookahead. $ is end of line.
                              lookahead case:
                                    0000 becomes 0 because last character is 0 and not end of line.
                                else
                                    replaces all 0+ with empty string.
                            */
                            if(!etStr.isEmpty())
                                MainActivity.et_strings.put("tipPerStr", etStr.replaceFirst("^0+(?!$)",""));
                            v.setText(MainActivity.et_strings.get("tipPerStr"));

                        case R.id.etSize:
                            //when a string input is 00000, set output to be previous value.
                            if(!etStr.matches("0*"))
                                MainActivity.et_strings.put("sizeStr", etStr.replaceFirst("^0+(?!$)",""));
                            v.setText(MainActivity.et_strings.get("sizeStr"));

                            /*
                        case R.id.swSize:
                            et_strings.get("sizeStr")Num = s.toString();
                            v.set
                            */
                    }
                    c.calc((ViewGroup)v.getParent());
                    MainActivity.softKeyboard.closeSoftKeyboard();

                    //on accept, focus view is not auto cleared.
                    v.clearFocus();
                    return true;
            }
            return false;
        }
    }
    /*
    switch view handler
     */

    private static class SwitchListener implements CompoundButton.OnCheckedChangeListener
    {
        private SwitchListener()
        {}
        /*
        input:
            boolean isChecked == true if switch is on
        */
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            sw_flag = isChecked;
        }
    }
    /*
    text view handler.

     */


    /*
    button handler
     */

}
