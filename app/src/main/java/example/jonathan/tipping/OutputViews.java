/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

class OutputViews {

    // output single view
    // if current view is empty string take care of it.
    // need boolean so it doesn't restore the same thing on onCreate.
    void  outputTextView(TextView v)
    {
        String out;
        SparseArray<Number> in = MainActivity.getInputViews().tv_num_data;

        // persistent data setter
        Activity activity = (Activity)v.getContext();
        final SharedPreferences.Editor dataSetter = activity.getSharedPreferences(activity.getClass().getSimpleName(), MODE_PRIVATE).edit();

        switch (v.getInputType())
        {
            //int #
            case InputType.TYPE_CLASS_NUMBER:
                dataSetter.putInt(Integer.toString(v.getId()),in.get(v.getId()).intValue());
                out = in.get(v.getId()).toString();
                MainActivity.debugL(v.getContext().getResources().getResourceEntryName(v.getId()) + " : " + out);
                break;

            //decimal #
            case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:
                dataSetter.putLong(Integer.toString(v.getId()), Double.doubleToLongBits(in.get(v.getId()).doubleValue()));
                out = String.format(new Locale("en"), "%.2f", in.get(v.getId()).doubleValue());
                break;

            // textview default type is string.
            default:
                if(v instanceof Switch)
                {
                    //each unique id can only store one data type.
                    dataSetter.putBoolean(v.getId() + MainActivity.swBool, MainActivity.getInputViews().tv_bool_data.get(v.getId()));
                    dataSetter.putString(v.getId() + MainActivity.swText, MainActivity.getInputViews().tv_str_data.get(v.getId()));
                }
                else
                    dataSetter.putString(Integer.toString(v.getId()), MainActivity.getInputViews().tv_str_data.get(v.getId()));
                out = MainActivity.getInputViews().tv_str_data.get(v.getId());
        }
        dataSetter.apply();
        v.setText(out);

        // put cursor at end of text in edit text
        if(v instanceof EditText)
            ((EditText)v).setSelection(out.length());

        //if cases such as check for empty string.
        /*
            // case int:
            out = num.doubleValue() == num.intValue()
                ? out.replaceFirst("^0+(?!$)", "")
                : String.format(new Locale("en"), "%.2f", out);
        */
    }

    //TODO: dfs or bfs traversal for all ViewGroup nodes
    void outputAllTextView(ViewGroup root)
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
