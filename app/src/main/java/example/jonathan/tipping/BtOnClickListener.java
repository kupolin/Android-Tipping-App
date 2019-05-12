/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class BtOnClickListener implements View.OnClickListener
{
    private static final BtOnClickListener ourInstance = new BtOnClickListener();

    public static BtOnClickListener getInstance() {
        return ourInstance;
    }

    private BtOnClickListener() {}

    @Override
    public void onClick(View v)
    {
        // get button tip %
        SparseArray<Number> tv_num_data = MainActivity.getInputViews().tv_num_data;

        /*
            need to reparse etTipPer view if < and > button. -1 negative check lowest is 0.
            when any button is clicked, etTipPer needs to be reparse for case: cursor inside
            etTipPer EditText
        */
        //reparse etTipPer for when cursor is inside it.
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
                //tip%10 15 20 button.
                //tv_num_data.put(R.id.etTipPer, tv_num_data.get(v.getId()));
        }
        //bt >, <, 10%, 15%, 20%
        tv_num_data.put(id, tv_num_data.get(v.getId()).intValue());

        //calculate tip total and bill total
        Calc.getInstance().calc();

        // output
        ViewGroup root = ((Activity)v.getContext()).findViewById(R.id.main_view);
        MainActivity.getOutputViews().outputAllTextView(root);
    }
}
