/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.app.Service;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class EtEditorListener implements TextView.OnEditorActionListener
{
    private static final EtEditorListener ourInstance = new EtEditorListener();

    public static EtEditorListener getInstance() { return ourInstance; }

    private EtEditorListener() { }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        Log.d("ACTIVITY_MAIN", "EditTextListener OnEditorAction");
        switch (actionId)
        {
            case EditorInfo.IME_ACTION_DONE:
                Log.d("ACTIVITY_MAIN", "Action done");
                MainActivity.getInputViews().parseTextView(v);
                MainActivity.getOutputViews().outputTextView(v);
                break;

            default:

            //calculator
            Calc.getInstance().calc();

            //keyboard
            InputMethodManager im = (InputMethodManager) v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
            //cannot use HIDE_IMPLICIT_ONLY, because default keyboard service is opened.
            //i.e. numeric keyboard overlay over alpha keyboard.
            im.hideSoftInputFromWindow(v.getWindowToken(), 0);

            //on accept, focus view is not auto cleared.
            //v.clearFocus();
            return true;
        }
        return false;
    }
}
