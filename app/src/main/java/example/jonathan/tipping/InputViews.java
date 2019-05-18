/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
    TextViewParser
*/

package example.jonathan.tipping;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

// InputViews depends on perseistaence dat on intialization;
class InputViews {
    // sparsearray to save memory. O(h). else use hashmap.
    final SparseArray<Number> tv_num_data = new SparseArray<>();
    final SparseArray<String> tv_str_data = new SparseArray<>();
    final SparseBooleanArray tv_bool_data = new SparseBooleanArray();

    // onCreate loader, for default values setting. Before user interaction.
       /*
        onCreate initialization check so it doesn't overwrite persistent data
        onCreate = true if called from activity onCreate() else false
     */
    void parseAllTextViews(ViewGroup root, boolean onCreate)
    {
        for(int i = 0; i < root.getChildCount(); i++)
        {
            View v = root.getChildAt(i);
            //skip all non text views
            if(!(v instanceof TextView))
                continue;

            parseTextView((TextView) v, onCreate);
        }
    }

    /*
        onCreate initialization check so it doesn't overwrite persistent data
        onCreate = true if called from activity onCreate() else false
     */
    void parseTextView(TextView v, boolean onCreate)
    {
     //    Log.d("MAIN_ACTIVITY", "PARSETEXTVIEW");
        //if text view is empty string do not reparse the view input.
        if (v.getText().toString().isEmpty() && !(v instanceof Switch))
            return;

    //    Log.d("MAIN_ACTIVITY", "PARSETEXTVIEW + " + v.getContext().getResources().getResourceEntryName(v.getId()) + ": " + v.getText().toString());
        Activity activity = (Activity)v.getContext();
        final SharedPreferences.Editor dataSetter = activity.getSharedPreferences(activity.getClass().getSimpleName(), MODE_PRIVATE).edit();
        switch (v.getInputType())
        {
            case InputType.TYPE_CLASS_NUMBER:
                int etNum = Integer.parseInt(v.getText().toString());
                // for edit text Number can only be unsigned ints.
                tv_num_data.put(v.getId(), etNum);
                if(onCreate)
                    dataSetter.putInt(Integer.toString(v.getId()),etNum);

                break;

            case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:
                double etF =  Double.parseDouble(v.getText().toString());
                Log.d("MAIN_ACTIVITY", "InputViews case: " + etF);
                tv_num_data.put(v.getId(), etF);
                if(onCreate)
                   dataSetter.putLong(Integer.toString(v.getId()), Double.doubleToLongBits(etF));
                break;

            // TextView default type is string.
            default:
                // Switches in this projects are empty string.
                if(v instanceof Switch)
                {
                    tv_bool_data.put(v.getId(), ((Switch) v).isChecked());
                    if(onCreate)
                        dataSetter.putBoolean(Integer.toString(v.getId()), ((Switch) v).isChecked());

                }
                else if(onCreate)
                    dataSetter.putString(Integer.toString(v.getId()), v.getText().toString());
                tv_str_data.put(v.getId(), v.getText().toString());
        }
        if(onCreate)
            dataSetter.apply();

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
