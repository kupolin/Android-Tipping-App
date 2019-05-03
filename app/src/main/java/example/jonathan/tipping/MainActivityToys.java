package example.jonathan.tipping;

import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class MainActivityToys {
/*
TODO: this is for when clicking the edit box, need to save default value and have keyboard popup.
Instad of having hte next button, change to accept and changes go through.
 */

//In initialize method:
    /*
     teBill.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(DEBUG)
                Log.d(ACTIVITY, "onEditorAction");
            Log.d(ACTIVITY, "Action id " + actionId);

            switch(actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    if (DEBUG)
                        Log.d(ACTIVITY, "IME_ACTION_DONE");


                        *.*.+ - false
                        .* = 0.any
                        0000 = 0.00
                        every thuosand "," format


                    String tmpStr = teBill.getText().toString();
                    Log.d(ACTIVITY, "82: " + tmpStr);

                        TODO: stream().filter(match regex).map(do something)



                        if(tmpStr.matches("(.*(\\.).*(\\.).*)+"))
                        {
                            teBill.setText(MainActivity.billStr);
                        }
                        else if(tmpStr.matches("(.*(\\.).*)"))
                        {
                            StringBuilder s = new StringBuilder(tmpStr);
                            s = s.deleteCharAt(0);
                            //String.Format() ?????????
                            MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                            teBill.setText(MainActivity.billStr);
                        }
                        // need other  cases
                        else
                        {
                            StringBuilder s = new StringBuilder(tmpStr.replaceAll(,""));
                            s = s.deleteCharAt(0);

                            MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                            teBill.setText(MainActivity.billStr);
                        }

                    Log.d(ACTIVITY, "110: ");
                    StringBuilder s = new StringBuilder(tmpStr);
                    //to handle char $  s = s.deleteCharAt(0);
                    Log.d(ACTIVITY, "113: ");
                    MainActivity.billStr = s.toString().format("%2f", Double.parseDouble(s.toString()));
                    // teBill.setText(MainActivity.billStr);
                    Log.d(ACTIVITY, "116: ");
                    calc();
                    Log.d(ACTIVITY, "117: ");
                    return true;
            }
            return false;
        }
    });
*/

    /*
            TODO: return false for unhandled event such as: MotionEvent.AMBIGUOUS_GESTURE
             https://developer.android.com/reference/android/view/MotionEvent
    */

    /*
        teBill.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(DEBUG)
                    Log.d(ACTIVITY, "text edit bill listener: onClick");
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        MainActivity.billStr = teBill.getText().toString();
                        Log.d(ACTIVITY, "BILL:" + MainActivity.billStr);
                        return true;

                    case MotionEvent.ACTION_UP:
                        MainActivity.super.findViewById(R.id.teBill).performClick();
                        return true;

                    default:
                }
                return false;
            }
        });

        teBill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performClick();
            }
        });









        teBill.addTextChangedListener(
                new TextWatcher(){
                    boolean state = false;
                    String str;

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(DEBUG)
                            Log.d(ACTIVITY, "afterTextChanged");

                        if(state) {
                            state = false;
                            return;
                        }

                        state = true;

                        if(s.length() > 0 && s.charAt(0) == '$') {
                            if(DEBUG)
                                Log.d(ACTIVITY, s.toString());
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
        );

    */
}
