package example.jonathan.tipping;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

//output class takes in a view.
public class Calc {



    private double calcTipNum(double bill, double tip)
    {

        double result = bill * tip/100.00;


        MainActivity.debugL("Bill: " + bill);
        MainActivity.debugL("Tip:" + tip);
        MainActivity.debugL("Result:" + result);


        return result;
//        return bill * tip/100.00;
    }

    private double calcTotal(double bill, double tip)
    {
        return bill + tip;
    }


    // make some override interface function that does all the output for the activity. calc needs to be an abstract class.
    //calc and setting textview tip
    //calc and setting textview total
    public void calc (ViewGroup v)
    {
        TextView tvTotalNum = v.findViewById(R.id.tvTotalNum);
        TextView tvTipNum = v.findViewById(R.id.tvTipNum);
        EditText etTipPer = v.findViewById(R.id.etTipPer);
/*
TODO: broke because tipPerString = not parsed from tvTipPer and stored as static field.
 */


        if(etTipPer != null) {
            double dTipNumResult = calcTipNum((double) Double.parseDouble(MainActivity.billStr), (double) Double.parseDouble(etTipPer.getText().toString()));
            String strTipNumResult = Double.toString(dTipNumResult );
            tvTipNum.setText(strTipNumResult);

            String TotalNumResult = Double.toString((double)calcTotal((double)Double.parseDouble(MainActivity.billStr), dTipNumResult));
            tvTotalNum.setText(TotalNumResult);
        }
    }

}


