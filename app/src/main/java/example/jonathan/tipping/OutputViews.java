/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class OutputViews {

    // output single view
    // if current view is empty string take care of it.
    public void  outputTextView(TextView v)
    {
        String out;
        switch (v.getInputType())
        {
            //int #
            case InputType.TYPE_CLASS_NUMBER:
                out = MainActivity.getInputViews().tv_num_data.get(v.getId()).toString();
                break;

            //decimal #

            case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:
                out = MainActivity.getInputViews().tv_num_data.get(v.getId()).toString();
                Log.d("MAINACTIVITY", "view : " + v.getContext().getResources().getResourceEntryName(v.getId()) + out);
              //  Log.d("MAINACTIVITY", "view : " + String.format("{0:00}", out));
                out = String.format(new Locale("en"), "%.2f", MainActivity.getInputViews().tv_num_data.get(v.getId()).doubleValue());
                break;

            // textview default type is string.
            default:
                out = MainActivity.getInputViews().tv_str_data.get(v.getId());
        }
        v.setText(out);

        //if cases such as check for empty string.
        /*
            // case int:
            out = num.doubleValue() == num.intValue()
                ? out.replaceFirst("^0+(?!$)", "")
                : String.format(new Locale("en"), "%.2f", out);
        */
    }

    public void outputAllTextView(ViewGroup root)
    {
        //output form view group.
        for(int i = 0; i < root.getChildCount(); i++)
        {
            View v = root.getChildAt(i);
            //skip all non text views
            if(!(v instanceof TextView))
                continue;

            outputTextView((TextView) v);
        }
    }
}
