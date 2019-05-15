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
import android.widget.TextView;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

class OutputViews {

    // output single view
    // if current view is empty string take care of it.
    void  outputTextView(TextView v)
    {
        String out;
        // setting up persistent data loader;
        Activity activity = (Activity)v.getContext();
        final SharedPreferences.Editor dataSetter = activity.getSharedPreferences(activity.getClass().getSimpleName(), MODE_PRIVATE).edit();

        SparseArray<Number> in = MainActivity.getInputViews().tv_num_data;
        switch (v.getInputType())
        {
            //int #
            case InputType.TYPE_CLASS_NUMBER:

                dataSetter.putInt(Integer.toString(v.getId()),  in.get(v.getId()).intValue());
                out = in.get(v.getId()).toString();
                break;

            //decimal #

            case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:

              //  Log.d("MAINACTIVITY", "view : " + String.format("{0:00}", out));
                dataSetter.putFloat(Integer.toString(v.getId()),  in.get(v.getId()).floatValue());
                out = String.format(new Locale("en"), "%.2f", in.get(v.getId()).doubleValue());
                break;

            // textview default type is string.
            default:
                dataSetter.putString(Integer.toString(v.getId()), MainActivity.getInputViews().tv_str_data.get(v.getId()));
                out = MainActivity.getInputViews().tv_str_data.get(v.getId());
        }

        dataSetter.apply();
        v.setText(out);

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
