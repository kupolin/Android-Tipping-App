package example.jonathan.tipping;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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
                        every thousand "," format
                    */
                        String etStr = v.getText().toString();
                    Log.d("ACTIVITY_MAIN", "etStr After"  );

                        /*
                        TODO: stream().filter(match regex).map(do something)
                        */

                        //invalid corner case method for bill et because only bill et has .
                    /*
                        if(etStr.matches("(.*(\\.).*(\\.)[(\\.).*)]+)*") || etStr.matches("(\\.)"))
                        {
                            switch (v.getId())
                            {
                                case R.id.teBill:
                                    v.setText(MainActivity.billStr);
                                    break;
                            }
                            return true;
                        }
*/

                        /*
                        else if(etStr.matches("(.*(\\.).*)"))
                            {
                            StringBuilder s = new StringBuilder(etStr);
                            s = s.deleteCharAt(0);
                            //String.Format() ?????????
                            MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                            teBill.setText(MainActivity.billStr);
                        }
                        // need other  cases
                        else
                        {
                            StringBuilder s = new StringBuilder(etStr.replaceAll(,""));
                            s = s.deleteCharAt(0);

                            MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                            teBill.setText(MainActivity.billStr);
                        }
                        */



                        /*

                          String etStr = v.getText().toString();


                        StringBuilder s = new StringBuilder(etStr);
                        //  to handle char $  s = s.deleteCharAt(0);
                        MainActivity.tipPer = s.toString().forDouble.parseDouble(s.toString()));



                        // calc();
                         */
                        StringBuilder s = new StringBuilder(etStr);
                        //to handle char $  s = s.deleteCharAt(0);




                    //TODO: LEGAL
                        //&& check with regex statement if legal.
                        //if not then need to set value to default value.

                        if(v.getId() == R.id.teBill) {
                            //only the edit text box have it.
                            if(!etStr.matches("(\\.)*"))
                                MainActivity.billStr = s.toString().format("%.2f", Double.parseDouble(s.toString()));
                            v.setText(MainActivity.billStr);
                        }
                        else if(v.getId() == R.id.etTipPer) {
                            MainActivity.tipPer = s.toString();
                            v.setText(MainActivity.tipPer);
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
    text view handler.

     */


    /*
    button handler
     */

}
