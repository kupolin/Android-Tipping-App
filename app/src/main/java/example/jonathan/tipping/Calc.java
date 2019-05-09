package example.jonathan.tipping;

import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

import static java.lang.Integer.parseInt;

//controller that calculates the calctipNum, and calcTotal
public class Calc {
/*
    input:
        bill: etBill input
        tip:  etTip input
        div: division of billTotal.
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

    public void calc (ViewGroup v)
    {
        //etTipper view can be null because when clicking etBill, viewgroup is passed in.
        TextView tvTotalNum = v.findViewById(R.id.tvTotalNum);
        TextView tvTipNum = v.findViewById(R.id.tvTipNum);

        //switch case for tip per person.
        Switch sw = v.findViewById(R.id.swSize);

        //never gonna be null.
        int size_int = parseInt(MainActivity.et_strings.get(R.id.etSize));

        Log.d("ACTIVITY_MAIN", "sw: " + sw.isChecked());
        //divisor for calcTipNum. switch on = per person.
        double div = sw.isChecked() ? 100.0 : (size_int * 100.0);
        //TODO: switch listener to do calc as well.
        //switch off is per person for size > 1.
        //switch on is one person.
        Log.d("ACTIVITY_MAIN", "size_int " + size_int);
        Log.d("ACTIVITY_MAIN", "tipPer in CALC " + MainActivity.et_strings.get(R.id.etTipPer));
        if (Looper.myLooper() == Looper.getMainLooper())
            MainActivity.debugL("MAINTHREADDDD CALC " + MainActivity.et_strings.get(R.id.etTipPer));
        else
            MainActivity.debugL("NOT MAIN THREAD CALC: " + MainActivity.et_strings.get(R.id.etTipPer));
        MainActivity.debugL("Before calctipNum " + MainActivity.et_strings.get(R.id.teBill));
        MainActivity.debugL("Bill: " + MainActivity.et_strings.get(R.id.teBill));
        MainActivity.debugL("div:" + div);
        MainActivity.debugL("tipPer:" + MainActivity.et_strings.get(R.id.etTipPer));

        double dBill = Double.parseDouble(MainActivity.et_strings.get(R.id.teBill));
        double dTipNumResult = calcTipNum(dBill, Integer.parseInt(MainActivity.et_strings.get(R.id.etTipPer)), div);
        double dTotal = calcTotal(dBill, dTipNumResult);
        //Total Bill per person if switch is off.
        if(!sw.isChecked())
            dTotal /= size_int;

        String strTipNumResult = Double.toString(dTipNumResult );
        String totalNumResult = Double.toString(dTotal);

        // outputs two section of view.
        tvTipNum.setText(String.format(new Locale("en"), "%.2f", Double.parseDouble(strTipNumResult)));
        tvTotalNum.setText(String.format(new Locale("en"), "%.2f", Double.parseDouble(totalNumResult)));
    }
}


