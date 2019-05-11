package example.jonathan.tipping;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EtOnFocusChangeListener implements View.OnFocusChangeListener
{
    private static final EtOnFocusChangeListener ourInstance = new EtOnFocusChangeListener();

    public static EtOnFocusChangeListener getInstance() { return ourInstance; }

    public void onFocusChange(View v, boolean hasFocus) {
        MainActivity.debugL("onSoftKeyboardShow run");
        //0th element string dummy node for case.
        // clear edittext when user onclick, and store current string as default.
        if (!(v instanceof EditText))
            throw new IllegalArgumentException("view must be an edit text");

        // view has just been clicked and onFocus. Set string to "" so User can type.
        if(hasFocus)
            ((TextView)v).setText("");
        else
        {
            MainActivity.getInputViews().parseTextView((TextView) v);
            Calc.getInstance().calc();
            MainActivity.getOutputViews().outputTextView((TextView) v);
        }
    }
}
