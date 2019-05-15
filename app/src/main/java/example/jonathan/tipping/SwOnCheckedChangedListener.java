/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class SwOnCheckedChangedListener implements CompoundButton.OnCheckedChangeListener {
    private static final SwOnCheckedChangedListener ourInstance = new SwOnCheckedChangedListener();

    public static SwOnCheckedChangedListener getInstance() {return ourInstance;}
    //save persist data loader?

    private SwOnCheckedChangedListener() {}

    //switch off means per person bill.
    //etSize needs to be recaptured when switch toggles for when user enters value.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        //input: parse switch
        MainActivity.getInputViews().parseTextView(buttonView);

        TextView etSize = ((Activity)buttonView.getContext()).findViewById(R.id.etSize);
        // do not reparse size view if text field is 0.
        if(!etSize.getText().toString().matches("0*"))
            MainActivity.getInputViews().parseTextView(
                            (TextView)((Activity)buttonView.getContext()).findViewById(R.id.etSize));
//how to know which activity is accessing the information.
        Calc.getInstance().calc(buttonView);
        //output
        ViewGroup root = ((Activity)buttonView.getContext()).findViewById(R.id.main_view);
        MainActivity.getOutputViews().outputAllTextView(root);
    }
}
