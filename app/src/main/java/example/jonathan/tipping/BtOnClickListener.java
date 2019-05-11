/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

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
        tv_num_data.put(R.id.etTipPer, tv_num_data.get(v.getId()));

        Calc.getInstance().calc();

        // output
        ViewGroup root = ((Activity)v.getContext()).findViewById(R.id.main_view);
        MainActivity.getOutputViews().outputAllTextView(root);
    }
}