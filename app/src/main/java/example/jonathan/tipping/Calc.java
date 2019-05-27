/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.app.Application;


//controller that calculates the calctipNum, and calcTotal
class Calc extends Application
{
    private static final Calc ourInstance = new Calc();

    static Calc getInstance() { return ourInstance;}

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
        double result =  bill*tip/div;
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

    // 1 if switch is true, else sizeNum.
    private void baseCalc(int sizeNum, double div)
    {
        InputViews in = MainActivity.getInputViews();
        //switch off is per person for size > 1.
        //switch on is one person.

        double dBill = in.tv_num_data.get(R.id.teBill).doubleValue();
        double dTipNumResult = calcTipNum(dBill, in.tv_num_data.get(R.id.etTipPer).intValue(), div);
        double dTotal = calcTotal(sizeNum > 1 ? dBill/sizeNum : dBill, dTipNumResult);

        // store in ram
        in.tv_num_data.put(R.id.tvTipNum, dTipNumResult);
        in.tv_num_data.put(R.id.tvTotalNum, dTotal);
    }
    void calc(boolean isChecked)
    {
        int size_int = isChecked ? GlobalApplication.getAppContext().getResources().getInteger(R.integer.sizeNum)
                                 : MainActivity.getInputViews().tv_num_data.get(R.id.etSize).intValue();
        double div = isChecked ? 100.0 : (size_int * 100.0);
        baseCalc(size_int, div);
    }
    void calc ()
    {
        calc(MainActivity.getInputViews().tv_bool_data.get(R.id.swSize));
    }
}


