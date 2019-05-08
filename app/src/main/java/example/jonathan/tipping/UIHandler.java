package example.jonathan.tipping;

import android.annotation.SuppressLint;
import android.app.Service;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.security.DomainCombiner;
import java.util.ArrayList;
import java.util.Arrays;
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
    protected static final Calc c = new Calc();
    private static final EtEditorListener etEditorListener = new EtEditorListener();
    private static final EtOnFocusChangeListener etFocusListener = new EtOnFocusChangeListener();
    private static final SwListener swListener = new SwListener();
    private static final BtListener btListener = new BtListener();

    public static EtEditorListener getEditTextEditorListener() { return etEditorListener; }
    public static SwListener getSwitchListener() {
        return swListener;
    }
    public static BtListener getButtonListener() {
        return btListener;
    }
    public static EtOnFocusChangeListener getEditTextFocusListener(){ return etFocusListener; }

    private static class EtEditorListener implements TextView.OnEditorActionListener
    {
        private EtEditorListener(){}
        //i want to create a function for each edit text to call on to handle things for different activity.
        //this function should be passed in. function or object.
        //can follow softkeyboard style with interface which gets overloaded that gets created and used.

        // Because size is number, it will always pass double check. number subset check of double.
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d("ACTIVITY_MAIN", "EditTextListener OnEditorAction"  );
                switch(actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    Log.d("ACTIVITY_MAIN", "Action done"  );
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
                        StringBuilder s = new StringBuilder(etStr);
                        //to handle char $  s = s.deleteCharAt(0);

                    switch (v.getId())
                    {
                        case R.id.teBill:

                            //default local english for decimal comma.
                            if (!etStr.isEmpty())
                                MainActivity.et_strings.put(R.id.teBill, String.format(new Locale("en"), "%.2f", Double.parseDouble(s.toString())));

                            //for when there are more than one 0 at the start of the value. i.e. 00000.123 or 000213
                            // MainActivity.et_strings.get(R.id.etTipPer)= MainActivity.et_strings.get(R.id.teBill).replaceFirst("^0+?!$", "0");
                            v.setText(MainActivity.et_strings.get(R.id.teBill));
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
                                MainActivity.et_strings.put(R.id.etTipPer, etStr.replaceFirst("^0+(?!$)",""));
                            v.setText(MainActivity.et_strings.get(R.id.etTipPer));

                        case R.id.etSize:
                            //when a string input is 00000, set output to be previous value.
                            if(!etStr.matches("0*"))
                                MainActivity.et_strings.put(R.id.etSize, etStr.replaceFirst("^0+(?!$)",""));
                            v.setText(MainActivity.et_strings.get(R.id.etSize));

                            /*
                        case R.id.swSize:
                            et_strings.get(R.id.etSize)Num = s.toString();
                            v.set
                            */
                    }
                    c.calc((ViewGroup)v.getParent());
                    InputMethodManager im = (InputMethodManager) v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                    //cannot use HIDE_IMPLICIT_ONLY, because default keyboard service is opened.
                    //i.e. numeric keyboard overlayed over alpha keyboard.
                    im.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //on accept, focus view is not auto cleared.
                    //v.clearFocus();
                    return true;
            }
            return false;
        }
    }

    /*
    EditTextOnFocusChange Listener
     */

    private static class EtOnFocusChangeListener implements View.OnFocusChangeListener
    {
          /*
                       input:
                        Bill, TipPer, Size for et1, et2 depending on v.
                        if v is tipPer, et1 = Bill, et2 = TipPer. left to right.

                        Object[] - array of string values from et_strings.

                       output:
                        String to be saved.
                    */

        /*
        instead of ArrayList<EditText> in private resetEditText, use v.getParent() which returns ViewGroup
        viewgruop.getchild(index), will return view v.
        if (v instanceOf EditText)
            then do operations.

        Views are returned always in the same order need check below:
        check if returned children index matches with component tree in xml.

         */
        private String resetEditText(TextView v)
        {
            ViewGroup vg = (ViewGroup) v.getParent();

            for(int i = 0; i < vg.getChildCount(); i++)
            {
                //point of this method is to ALL OTHER edit text / text Views besides current editText.
                //reason for this is because when it is current view, just update the other edit text.
                // the current view just needs to be saved and replaced with empty string.

                //prepping for refactor for onfocuschange.
                if(vg.getChildAt(i) == v || !(vg.getChildAt(i) instanceof EditText))
                    continue;

                EditText et = (EditText) vg.getChildAt(i);
                String eleStr = et.getText().toString();
                String defaultEleStr = MainActivity.et_strings.get(et.getId());

                // case for when click one edit text then click another edit text
                // when such a case happens, the previous edit text would still be clear
                // the previous value has already been saved.

                if (et.getText().length() == 0)
                    et.setText(defaultEleStr);
                    // case when user type in edit text field i.e. 0021312, needs to be reparsed.
                    // only reparse if softkey ime action done was not clicked, since softkey reparses.

                else if(!eleStr.equals(defaultEleStr))
                {
                    // formatting output
                    // bill needs to be parsed as double,
                    // all others are in the form of int.
                    String s = et.getId()== R.id.teBill ? String.format(new Locale("en"), "%.2f", Double.parseDouble(eleStr))
                            : eleStr.replaceFirst("^0+(?!$)","");
                    if (et.getId() == R.id.etSize && s.matches("0"))
                        s = s.replaceFirst("0", "1");

                    //update output and defaultValue map
                    MainActivity.et_strings.put(et.getId(), s);
                    et.setText(s);
                }
            }
            return v.getText().toString();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            MainActivity.debugL("onSoftKeyboardShow run");
            //0th element string dummy node for case.
            // clear edittext when user onclick, and store current string as default.
            if (!(v instanceof EditText))
                throw new IllegalArgumentException("view must be an edit text");
            if (!hasFocus)
                return;
            EditText et = (EditText) v;
            switch (v.getId())
            {
                case R.id.teBill: //focus change listener for each case.
                    //corner case for 0000s
                    String s = resetEditText(et);
                    //TODO: refactor this with UIhandler.java edit text listener output format.
                    // need to update str arraylist for when it is the same view being refocused. i.e. when keyboard is always up.
                    // doesn't apply for when clicked done and refocus because a new runnable will be created in that instance.
                    //This saves on Open default value.
                    // need to reparse other strings edit text if user has typed something when keyboard is open.
                    MainActivity.et_strings.put(R.id.teBill, String.format(new Locale("en"), "%.2f", Double.parseDouble(s)));
                    MainActivity.debugL("RUN_ TEBILL ___ : " + MainActivity.et_strings.get(R.id.teBill));
                    break;

                case R.id.etTipPer:
                    MainActivity.debugL("before resetEdit " + MainActivity.et_strings.get(R.id.etTipPer));
                    s = resetEditText(et).replaceFirst("^0+(?!$)","");
                    MainActivity.et_strings.put(R.id.etTipPer, s);
                    // need to update str arraylist for when it is the same view being refocused. i.e. when keyboard is always up.
                    // doesn't apply for when clicked done and refocus because a new runnable will be created in that instance.

                    break;

                case R.id.etSize:
                    s = resetEditText(et).replaceFirst("^0+(?!$)","");
                    MainActivity.et_strings.put(R.id.etSize, s);
                    // need to update str arraylist for when it is the same view being refocused. i.e. when keyboard is always up.
                    // doesn't apply for when clicked done and refocus because a new runnable will be created in that instance.

                    Log.d("ACTIVITY_MAIN", "TEMP: " + et.getText().toString());
            }
            Log.d("ACTIVITY_MAIN", "*** softkeyboard ***  After cases: " + MainActivity.et_strings.get(R.id.etSize));
            MainActivity.debugL("Bill: " + MainActivity.et_strings.get(R.id.teBill));
            MainActivity.debugL("size:" + MainActivity.et_strings.get(R.id.etSize));
            MainActivity.debugL("tipPer Str: "  + MainActivity.et_strings.get(R.id.etTipPer));
            c.calc((ViewGroup)v.getParent());
            et.setText("");
        }
    }
    /*
    switch view handler
     */

    private static class SwListener implements CompoundButton.OnCheckedChangeListener
    {
        private SwListener() {}

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            /*
                case when user is in the editText and switch is switched off, the text view needs to update accordingly.
                when switching off, check if user has inputted in the size edit text.
                Do this by checking against et_strings hashmap with current edit text view.
             */
            EditText etSize = ((ViewGroup)buttonView.getParent()).findViewById(R.id.etSize);
            String etSizeStr = etSize.getText().toString();
            // checking if user is currently inputing inside edit text size and then switched the switch off.
            if (!MainActivity.et_strings.get(R.id.etSize).equals(etSizeStr));
                MainActivity.et_strings.put(R.id.etSize, etSizeStr);
            UIHandler.c.calc((ViewGroup) buttonView.getParent());
        }
    }

    /*
    button handler
     */
    private static class BtListener implements View.OnClickListener
    {
        private BtListener(){}

        @Override
        public void onClick(View v)
        {
            Button b = (Button) v;
            EditText etTipPer = ((ViewGroup)v.getParent()).findViewById(R.id.etTipPer);

            String bStr = b.getText().toString();

            //if v.getId is a tip percent id, then the following:


            // tipPer edit Text.set (the button string)
            // TODO when end with %, need to regex it out.
            etTipPer.setText(bStr);
            MainActivity.et_strings.put(etTipPer.getId(), bStr);

            //calculate and output textviews
            c.calc((ViewGroup)v.getParent());

        }
    }
}
