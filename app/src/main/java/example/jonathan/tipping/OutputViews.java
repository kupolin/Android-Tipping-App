package example.jonathan.tipping;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class OutputViews {
    public OutputViews(){}

    //output single view
    //if current view is empty string take care of it.
    public void outputNumTextView(TextView v, SparseArray<Number> tv_data)
    {
        //is V a num text view.
        Number num = tv_data.get(v.getId());
        String out = num.toString();
        //if cases such as check for empty string.

            // case int:
            out = num.doubleValue() == num.intValue()
                ? out.replaceFirst("^0+(?!$)", "")
                : String.format(new Locale("en"), "%.2f", out);

        v.setText(out);
    }

    public void outputAllNumTextView(ViewGroup v, SparseArray<Number> tv_data)
    {
        //output form view group.

    }

    //output to all textviews
    public void outputStrTextView(SparseArray<String> str)
    {}
}
