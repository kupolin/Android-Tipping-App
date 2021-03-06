/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.app.Activity;
import android.app.Service;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
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
        //some error check for return false?
        switch (actionId)
        {
            case EditorInfo.IME_ACTION_DONE:
                MainActivity.getInputViews().parseTextView(v);
                Calc.getInstance().calc();
                // find root of activity component tree.
                ViewGroup root = ((Activity)v.getContext()).findViewById(R.id.main_view);
                // update views this view + calculated views
                MainActivity.getOutputViews().outputAllTextView(root);
                break;
            default:
        }
        //keyboard
        InputMethodManager im = (InputMethodManager) v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        //cannot use HIDE_IMPLICIT_ONLY, because default keyboard service is opened.
        //i.e. numeric keyboard overlay over alpha keyboard.
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
        v.clearFocus();
        return true;
    }
}