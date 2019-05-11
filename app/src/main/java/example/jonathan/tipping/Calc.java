/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.os.Looper;
import android.util.Log;

//controller that calculates the calctipNum, and calcTotal
public class Calc {
    private static final Calc ourInstance = new Calc();

    public static Calc getInstance() { return ourInstance; }

    private Calc() {}
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

    public void calc ()
    {
        InputViews in = MainActivity.getInputViews();
        //switch case for tip per person.
        //Switch sw = v.findViewById(R.id.swSize);

        //never gonna be null.

        int size_int = in.tv_num_data.get(R.id.etSize).intValue();

        Log.d("ACTIVITY_MAIN", "sw: " + in.tv_bool_data.get(R.id.swSize));
        //divisor for calcTipNum. switch on = per person.
        double div = in.tv_bool_data.get(R.id.swSize) ? 100.0 : (size_int * 100.0);

        //switch off is per person for size > 1.
        //switch on is one person.
        Log.d("ACTIVITY_MAIN", "size_int " + size_int);
        Log.d("ACTIVITY_MAIN", "tipPer in CALC " + in.tv_num_data.get(R.id.etTipPer));
        if (Looper.myLooper() == Looper.getMainLooper())
            MainActivity.debugL("MAINTHREADDDD CALC " + in.tv_num_data.get(R.id.etTipPer));
        else
            MainActivity.debugL("NOT MAIN THREAD CALC: " + in.tv_num_data.get(R.id.etTipPer));
        MainActivity.debugL("Before calctipNum " + in.tv_num_data.get(R.id.teBill));
        MainActivity.debugL("Bill: " + in.tv_num_data.get(R.id.teBill));
        MainActivity.debugL("div:" + div);
        MainActivity.debugL("tipPer:" + in.tv_num_data.get(R.id.etTipPer));

        double dBill = in.tv_num_data.get(R.id.teBill).doubleValue();

        double dTipNumResult = calcTipNum(dBill, in.tv_num_data.get(R.id.etTipPer).intValue(), div);
        double dTotal = calcTotal(dBill, dTipNumResult);

        //Total Bill per person if switch is off.
        if(!in.tv_bool_data.get(R.id.swSize))
            dTotal /= size_int;

        in.tv_num_data.put(R.id.tvTipNum, dTipNumResult);
        in.tv_num_data.put(R.id.tvTotalNum, dTotal);
    }
}


