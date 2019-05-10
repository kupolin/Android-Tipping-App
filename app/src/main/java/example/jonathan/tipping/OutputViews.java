package example.jonathan.tipping;

import android.text.InputType;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class OutputViews {
    private InputViews in;
    public OutputViews(InputViews in){this.in = in;}

    // output single view
    // if current view is empty string take care of it.
    public void outputTextView(TextView v)
    {
        // is V a num text view.
        switch (v.getInputType())
        {
            case InputType.TYPE_CLASS_NUMBER:
                String out = in.tv_num_data.get(v.getId()).toString();

                // tv_data if type int, then there is no formatting.
                out = v.getInputType() == InputType.TYPE_NUMBER_FLAG_DECIMAL
                        ? String.format(new Locale("en"), "%.2f", out)
                        : out;
                v.setText(out);
                break;

            // textview default type is string.
            default:
                v.setText(in.tv_str_data.get(v.getId()));
        }

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

    //output to all textviews
    public void outputStrTextView(SparseArray<String> str)
    {}
}
