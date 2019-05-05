package example.jonathan.tipping;

import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

//output class takes in a view.
public class Calc {


/*
    input:
        bill: etBill input
        tip:  etTip input
        div: division of billTotal.
                tip is in % need to divide by 100 * size.
                it is always better to have as little division as possible due to significant
                for more accurate results.

                When performing a chain of calculations involving addition, subtraction,
                multiplication, and division, try to perform the multiplication and division
                operations first.

                When multiplying and dividing sets of numbers, try to arrange the multiplications
                so that they multiply large and small numbers together; likewise, try to
                divide numbers that have the same relative magnitudes.
 */
    private double calcTipNum(double bill, int tip, double div)
    {
        //tip is an int
        MainActivity.debugL("CalcTipNum " + bill);
        double result =  bill*tip/div;

        MainActivity.debugL("Bill: " + bill);
        MainActivity.debugL("div:" + div);
        MainActivity.debugL("Result:" + result);


        return result;
//        return bill * tip/100.00;
    }
/*
    Whenever subtracting two numbers with the same signs or adding two numbers
    with different signs, the accuracy of the result may be less than the precision
    available in the floating point format
 */
    private double calcTotal(double bill, double tip)
    {
        return bill + tip;
    }


    // make some override interface function that does all the output for the activity. calc needs to be an abstract class.
    //calc and setting textview tip
    //calc and setting textview total
    public void calc (ViewGroup v)
    {
        EditText etTipPer = v.findViewById(R.id.etTipPer);


        //etTipper view can be null because when clicking etBill, viewgroup is passed in.
        TextView billStr = v.findViewById(R.id.teBill);
        TextView tvTotalNum = v.findViewById(R.id.tvTotalNum);
        TextView tvTipNum = v.findViewById(R.id.tvTipNum);
        Switch sw = v.findViewById(R.id.swSize);

        TextView etSize = v.findViewById(R.id.etSize);

        int size_int = Integer.parseInt(etSize.getText().toString());

        Log.d("ACTIVITY_MAIN", "sw: " + sw.isChecked());
        //divisor for calcTipNum. switch on = per person.
        double div = sw.isChecked() ? 100.0 : (size_int * 100.0);
        //TODO: switch listener to do calc as well.
        //switch off is per person for size > 1.
        //switch on is one person.
        Log.d("ACTIVITY_MAIN", "size_int " + size_int);
        Log.d("ACTIVITY_MAIN", "tipPer in CALC " + MainActivity.tipPer);
        if (Looper.myLooper() == Looper.getMainLooper())
            MainActivity.debugL("MAINTHREADDDD CALC " + MainActivity.tipPer);
        else
            MainActivity.debugL("NOT MAIN THREAD CALC: " + MainActivity.tipPer);
        MainActivity.debugL("Before calctipNum " + MainActivity.billStr);
        MainActivity.debugL("Bill: " + MainActivity.billStr);
        MainActivity.debugL("div:" + div);
        MainActivity.debugL("tipPer:" + MainActivity.tipPer);
        double dTipNumResult = calcTipNum(Double.parseDouble(MainActivity.billStr),
                                              Integer.parseInt(MainActivity.tipPer),
                                              div);

        String strTipNumResult = Double.toString(dTipNumResult );
        tvTipNum.setText(strTipNumResult);

        String TotalNumResult = Double.toString(calcTotal(Double.parseDouble(MainActivity.billStr), dTipNumResult));
        tvTotalNum.setText(TotalNumResult);

    }
}


