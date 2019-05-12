/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

class InputViews {
    // sparsearray to save memory. O(h). else use hashmap.
    final SparseArray<Number> tv_num_data = new SparseArray<>();
    final SparseArray<String> tv_str_data = new SparseArray<>();
    final SparseBooleanArray tv_bool_data = new SparseBooleanArray();

    // onCreate loader, for default values setting. Before user interaction.
    void parseAllTextViews(ViewGroup root)
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

    void parseTextView(TextView v)
    {
        //if text view is empty string do not reparse the view input.
        if (v.getText().toString().isEmpty() && !(v instanceof Switch))
            return;

        Log.d("MAINACTIVITY", "PARSETEXTVIEW + " + v.getContext().getResources().getResourceEntryName(v.getId()) + ": " + v.getText().toString());
        switch (v.getInputType())
        {
            case InputType.TYPE_CLASS_NUMBER:
                int etNum = Integer.parseInt(v.getText().toString());
                //for edit text Number can only be unsigned ints.
                tv_num_data.put(v.getId(), etNum);
                break;

            case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:
                tv_num_data.put(v.getId(),
                                Double.parseDouble(v.getText().toString()));
                break;

            // textview default type is string. except widgets like switches
            default:
                if(v instanceof Switch)
                {
                    tv_bool_data.put(v.getId(), ((Switch) v).isChecked());
                }
                tv_str_data.put(v.getId(), v.getText().toString());
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
}
