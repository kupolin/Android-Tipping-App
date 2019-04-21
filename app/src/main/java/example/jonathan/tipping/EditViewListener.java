package example.jonathan.tipping;

import android.text.TextWatcher;
import android.text.Editable;
import android.util.Log;

public class EditViewListener implements TextWatcher {
    private boolean state;
    private String str;
    public EditViewListener() {
        state = false;
        str = "";
    }



    @Override
    public void afterTextChanged(Editable s) {
        if(state) {
            state = false;
            return;
        }

        state = true;

        if(s.length() > 0 && s.charAt(0) == '$') {
            Log.d("EDITVIEWLISTENER", s.toString());
            str = s.toString();
        }
    }
    // check store character flag.

    // use switch statements based on flag. $, or default case: num - if(Character.isDigit())
    @Override
    public void beforeTextChanged(CharSequence s, int start,
                                  int count, int after) {
        //check for $ or no
        // set flag for it.

        // System.out.println(s.toString)
    }

    @Override
    public void onTextChanged(CharSequence s, int start,
                              int before, int count) {
        // if(s.length() != 0)
        //    field2.setText("");
    }

}
