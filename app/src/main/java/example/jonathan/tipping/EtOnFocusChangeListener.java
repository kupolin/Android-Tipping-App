/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EtOnFocusChangeListener implements View.OnFocusChangeListener
{
    private static final EtOnFocusChangeListener ourInstance = new EtOnFocusChangeListener();

    public static EtOnFocusChangeListener getInstance() { return ourInstance; }

    public void onFocusChange(View v, boolean hasFocus) {
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
            // MainActivity.getOutputViews().outputTextView((TextView) v);

            // activity root component tree
            ViewGroup root = ((Activity)v.getContext()).findViewById(R.id.main_view);
            // update views.
            MainActivity.getOutputViews().outputAllTextView(root);
        }
    }
}
