package example.jonathan.tipping;

import android.text.InputType;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;



public class InputViews {
    // sparsearray to save memory. O(h). else use hashmap.
    public final SparseArray<Number> tv_num_data = new SparseArray<>();
    public final SparseArray<String> tv_str_data = new SparseArray<>();

    // onCreate loader, for default values setting. Before user interaction.
    public void parseAllTextViews(ViewGroup root)
    {
        for(int i = 0; i < root.getChildCount(); i++)
        {
            View v = root.getChildAt(i);
            //skip all non text views
            if(!(v instanceof TextView))
                continue;

            parseTextView((TextView) v);
        }
    }

    public void parseTextView(TextView v)
    {
        switch (v.getInputType())
        {
            case InputType.TYPE_CLASS_NUMBER:
                parseNumberFromView((TextView)v);
                break;
            // textview default type is string.
            default:
                tv_str_data.put(v.getId(), ((TextView)v).getText().toString());
        }
    }

    // edit text is always a number.
    // if edit text: then need to find out if it is double or int.
    /*
    private void parseNumberFromView(TextView v) {
        double etNum = Double.parseDouble(((TextView) v).getText().toString());
        // integer check
        if ((etNum == (int)etNum) && !Double.isInfinite(etNum)) {
            et_data.put(v.getId(), (int) etNum);
        } else
    //parse user input double, need to be correct format.
            et_data.put(v.getId(), etNum);
    }
    */
    private void parseNumberFromView(TextView v) {
        Number etNum = v.getInputType() == InputType.TYPE_NUMBER_FLAG_DECIMAL
                     ? Double.parseDouble(v.getText().toString())
                     : Integer.parseInt(v.getText().toString());
        tv_num_data.put(v.getId(), etNum);
    }
}
