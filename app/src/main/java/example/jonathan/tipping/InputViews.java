/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
    TextViewParser
*/

package example.jonathan.tipping;


import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

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

    /*
        onCreate initialization check so it doesn't overwrite persistent data
        onCreate = true if called from activity onCreate() else false
     */
    void parseTextView(TextView v)
    {

        if (v.getText().toString().isEmpty() && !(v instanceof Switch))
            return;

 //       Log.d("MAIN_ACTIVITY", "INPUTPARSETEXTVIEW + " + v.getContext().getResources().getResourceEntryName(v.getId()) + ": " + v.getText().toString());
        switch (v.getInputType())
        {
            case InputType.TYPE_CLASS_NUMBER:
                int etNum = Integer.parseInt(v.getText().toString());
                // for edit text Number can only be unsigned ints.
                tv_num_data.put(v.getId(), etNum);
                break;

            case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:
                double etF =  Double.parseDouble(v.getText().toString());
                Log.d("MAIN_ACTIVITY", "InputViews case: " + etF);
                tv_num_data.put(v.getId(), etF);
                break;

            // TextView default type is string.
            default:
                // Switches in this projects are empty string.
                if(v instanceof Switch)
                {
                    tv_bool_data.put(v.getId(), ((Switch) v).isChecked());
                }
                tv_str_data.put(v.getId(), v.getText().toString());
        }
    }
}
