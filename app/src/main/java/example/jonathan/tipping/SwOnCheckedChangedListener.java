/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.CompoundButton;

public class SwOnCheckedChangedListener implements CompoundButton.OnCheckedChangeListener {
    private static final SwOnCheckedChangedListener ourInstance = new SwOnCheckedChangedListener();

    public static SwOnCheckedChangedListener getInstance() {return ourInstance;}

    private SwOnCheckedChangedListener() {}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        MainActivity.getInputViews().parseTextView(buttonView);
        Calc.getInstance().calc();

        ViewGroup root = ((Activity)buttonView.getContext()).findViewById(R.id.main_view);
        MainActivity.getOutputViews().outputAllTextView(root);
    }
}
