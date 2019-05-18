/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;

//TODO: need persist data here.
public class BtOnClickListener implements View.OnClickListener
{
    private static final BtOnClickListener ourInstance = new BtOnClickListener();
    private SharedPreferences.Editor Context;

    public static BtOnClickListener getInstance() {
        return ourInstance;
    }

    private BtOnClickListener() {}

    @Override
    public void onClick(View v)
    {
        Log.d("MAINACTIVITY", "OnClickListener");
        SparseArray<Number> tv_num_data = MainActivity.getInputViews().tv_num_data;

        /*
            need to reparse etTipPer view if < and > button. -1 negative check lowest is 0.
            when any button is clicked, etTipPer needs to be reparse for case: cursor inside
            etTipPer EditText
        */

        EditText etTipPer = ((Activity)v.getContext()).findViewById(R.id.etTipPer);
        MainActivity.getInputViews().parseTextView(etTipPer);

        Log.d("MAINACTIVITY","BtOnClickListener: " + (v.getContext()).getResources().getResourceEntryName(v.getId()));
        int id = R.id.etTipPer;
        int val;
        switch(v.getId())
        {
            case R.id.btTipGt:
                tv_num_data.put(v.getId(), tv_num_data.get(R.id.etTipPer).intValue()+1);
                break;
            case R.id.btTipLt:
                val =  tv_num_data.get(R.id.etTipPer).intValue()-1;
                tv_num_data.put(v.getId(), val < 0 ? 0 : val);
                break;

            case R.id.btSizeGt:
                id = R.id.etSize;
                tv_num_data.put(v.getId(), tv_num_data.get(R.id.etSize).intValue()+1);
                break;

            case R.id.btSizeLt:
                id = R.id.etSize;
                val = tv_num_data.get(R.id.etSize).intValue()-1;
                tv_num_data.put(v.getId(), val < 1 ? 1 : val);
                break;
            default:
        }

        // update data etTipPer or etSize for when bt >, <, 10%, 15%, 20%
        tv_num_data.put(id, tv_num_data.get(v.getId()).intValue());

        Activity activity = (Activity)v.getContext();
        final SharedPreferences.Editor dataSetter = activity.getSharedPreferences(activity.getClass().getSimpleName(), MODE_PRIVATE).edit();
        dataSetter.putInt(Integer.toString(id), tv_num_data.get(v.getId()).intValue());
        dataSetter.apply();

        //calculate tip total and bill total
        Calc.getInstance().calc(v);

        // output
        ViewGroup root = ((Activity)v.getContext()).findViewById(R.id.main_view);
        MainActivity.getOutputViews().outputAllTextView(root);
    }
}
