package example.jonathan.tipping;

public class Calc {
    public static double calcTipNum(double bill, double tip)
    {

        double result = bill * tip/100.00;


        MainActivity.debugL("Bill: " + bill);
        MainActivity.debugL("Tip:" + tip);
        MainActivity.debugL("Result:" + result);


        return result;
//        return bill * tip/100.00;
    }

    public static double calcTotal(double bill, double tip)
    {
        return bill + tip;
    }
}
