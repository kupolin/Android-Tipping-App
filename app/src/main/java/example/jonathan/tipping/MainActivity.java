/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Switch;
import android.widget.TextView;

import java.lang.String;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Queue;

public class MainActivity extends BaseActivity
{
    private static final boolean DEBUG = true;
    private static final String ACTIVITY = "MAIN_ACTIVITY";

    private boolean itemSelectCalled = false;

    //TODO: for each static variable used, losing 4 bytes of space of reference.
    // 4 bytes space of reference is NOTHING compare to the amount of bytes generated from the R static file.
    // not final for when system destroys this activity for memory.
    private static InputViews in = new InputViews();
    private static OutputViews out = new OutputViews();

    final static String swText = "swText";
    final static String swBool = "swBool";

    public static InputViews getInputViews() {return in;}
    private final int SETTING_REQ_CODE = 1;

    public static OutputViews getOutputViews(){return out;}
    public static void debugL(String msg)
    {
        Log.d(ACTIVITY, msg);
    }

    private void initData(ViewGroup root)
    {
        SharedPreferences dataGetter = getSharedPreferences(this.getClass().getSimpleName(), MODE_PRIVATE);

        //create a bfs
        Queue<ViewGroup> que = new ArrayDeque<>();
        que.add(root);

        while(!que.isEmpty())
        {
            ViewGroup v = que.remove();
            for (int i = 0; i < root.getChildCount(); i++)
            {
                if (v.getChildAt(i) instanceof ViewGroup)
                    que.add((ViewGroup) root.getChildAt(i));
                else if(v.getChildAt(i) instanceof TextView && !(v.getChildAt(i) instanceof Toolbar))
                {
                    int vId = v.getChildAt(i).getId();
              //      debugL(v.getChildAt(i).getClass().toString());
              //      debugL(((TextView)v.getChildAt(i)).getText().toString());
               //     debugL("VID: *** : " + v.getContext().getResources().getResourceEntryName(vId));

                    switch (((TextView)v.getChildAt(i)).getInputType())
                    {
                        //int #
                        case InputType.TYPE_CLASS_NUMBER:
                            MainActivity.in.tv_num_data.put(vId, dataGetter.getInt(Integer.toString(vId), MainActivity.in.tv_num_data.get(vId).intValue()));
                         //   MainActivity.in.tv_num_data.put(vId, dataGetter.getInt(Integer.toString(vId), -1));
                            break;

                        //decimal #
                        case InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER:
                            debugL("INITDATA " + "id: " + v.getContext().getResources().getResourceEntryName(vId) );
                            debugL("dataGetter: " + Double.longBitsToDouble(dataGetter.getLong(Integer.toString(vId),-1)));

                            MainActivity.in.tv_num_data.put(vId, Double.longBitsToDouble(dataGetter.getLong(Integer.toString(vId),
                                                                                         Double.doubleToLongBits(MainActivity.in.tv_num_data.get(vId).doubleValue()))));
                            break;

                        // textview default type is string.
                        default:
                            //check dataGetter.getString instead of boolean.
                            if(v.getChildAt(i) instanceof Switch)
                            {
                                MainActivity.in.tv_bool_data.put(vId, dataGetter.getBoolean(vId + MainActivity.swBool, MainActivity.in.tv_bool_data.get(vId)));
                                MainActivity.in.tv_str_data.put(vId, dataGetter.getString(vId + MainActivity.swText, MainActivity.in.tv_str_data.get(vId)));
                                //only need to set on first load. default will work when not load.
                                ((Switch)v.getChildAt(i)).setChecked(MainActivity.in.tv_bool_data.get(vId));
                            }
                           // MainActivity.in.tv_str_data.put(vId, dataGetter.getString(Integer.toString(v.getId()),""));
                    }
                }
            }
        }
    }

    /*
        Initialize all ui elements / construct listeners.
    */
    private void initListeners()
    {
        // root component view
        ViewGroup root = findViewById(R.id.main_view);

        // bfs all children nodes of vg.
        Queue<ViewGroup> que = new ArrayDeque<>();
        que.add(root);

        while(que.peek() != null)
        {
            ViewGroup vg = que.remove();
            for(int i = 0; i < vg.getChildCount(); i++)
            {
                View v = vg.getChildAt(i);
                if(v instanceof ViewGroup)
                    que.add((ViewGroup) v);

                else if(v instanceof EditText)
                {
                    ((EditText)v).setOnEditorActionListener(EtEditorListener.getInstance());
                    v.setOnFocusChangeListener(EtOnFocusChangeListener.getInstance());
                }
                else if(v instanceof Switch)
                {
                    ((Switch) v).setOnCheckedChangeListener(SwOnCheckedChangedListener.getInstance());
                }
                else if(v instanceof Button)
                {
                    v.setOnClickListener(BtOnClickListener.getInstance());
                }
                else
                {
                    if(v instanceof TextView)
                        continue;
                    Log.d("MAINACTVITY", "!!! Initialize v  was not found: " + v.getClass().toString());
                   // throw new IllegalArgumentException();
                }
            }
        }
        Log.d("MAIN_ACTIVITY", "INITLISTERNES");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(DEBUG)
            Log.d(ACTIVITY, "onStart");
    }

    public void onResume()
    {
        super.onResume();
        //saving edit text fields.
       // Log.d("MAIN_ACTIVITY", "------------ initial values " + et_strings.get(R.id.teBill) + et_strings.get(R.id.etTipPer) + size);
        //keyboard open close for edittext
        if(DEBUG)
            Log.d(ACTIVITY, "onResume");
    }

    public void onPause()
    {
        super.onPause();
        if(DEBUG)
            Log.d(ACTIVITY,"onPause");
    }

    public void onStop()
    {
        super.onStop();
        if(DEBUG)
            Log.d(ACTIVITY, "onStop");
    }
    public void onDestroy()
    {
        super.onDestroy();
        if(DEBUG)
            Log.d(ACTIVITY,"onDestroy");
        MainActivity.in = null;
        MainActivity.out = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(DEBUG)
            Log.d(ACTIVITY, "OnCreate");
        // store into ram/savedInstanceState for performance when it matters i.e. huge data set.
        super.onCreate(savedInstanceState);
        Log.d("ACTIVITY_MAIN", "209");

        // dataLoader(savedInstanceState);

        // set the user interface layout for this activity
        setContentView(R.layout.activity_main);

        if(MainActivity.in == null)
            MainActivity.in = new InputViews();
        if(MainActivity.out == null)
            MainActivity.out = new OutputViews();

        // root component view
        ViewGroup root = findViewById(R.id.main_view);
        // initialize all text views and puts them in a model.
        in.parseAllTextViews(root);
        // load persistent data after view load.
        initData(root);
        Calc.getInstance().calc();
        //TODO: load from saved instance
        /*
                if(savedInstanceState != null)
                initData(root);
            else
             in.parseAllTextViews(root);
         */

        Log.d("ACTIVITY_MAIN", "236");
        out.outputAllTextView(root);
        Log.d("ACTIVITY_MAIN", "238");
        initListeners();

        /*
        Setting up a custom toolbar:
        create a toolbar. actionbar.setTitle("") to get rid of default app title.
        create a TextView inside the toolbar as placement for a title.
        Left of TextView, create image views to support left icon buttons.

        right icon buttons can still be loaded with the standard menu.
         */

        // setup menu
        // Since custom toolbar is not used, and main activity is the only one that needs a left icon,
        // home button hack will be used to simulate a button click left icon.
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
    }
/**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.d("MAIN_ACTIVITY_BASE", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Locate MenuItem with ShareActionProvider

        // Setting up for top right drop down menu share instead of a picker.
        /*
        MenuItem item = menu.findItem(R.id.menu_share);
        // Fetch and store ShareActionProvider.
        ShareActionProvider shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_INTENT, "test");

        shareActionProvider.setShareIntent(shareIntent);
        */

        // Set up home button to be a setting button activity instead.
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_preferences);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(this.itemSelectCalled)
            return super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            // bundle size num.
            case android.R.id.home:
                this.itemSelectCalled = true;
                final Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra(Integer.toString(R.id.btTip1), ((TextView)findViewById(R.id.btTip1)).getText());
                intent.putExtra(Integer.toString(R.id.btTip2), ((TextView)findViewById(R.id.btTip2)).getText());
                intent.putExtra(Integer.toString(R.id.btTip3), ((TextView)findViewById(R.id.btTip3)).getText());
                intent.putExtra(Integer.toString(R.id.etSize), ((TextView)findViewById(R.id.etSize)).getText());

                startActivityForResult(intent, SETTING_REQ_CODE);
                return true;

            /*
            switch on/off:
            Tipping
            Bill: %100.00
            Tip %10.00 (10%)
            Total: %110.00
            Party Size: 3
            Personal Tip: $3.33
            Personal Total: $36.67
             */
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                // grab location / date time in subject.
                // date picker meh.
                String billStr = ((TextView)findViewById(R.id.teBill)).getText().toString();
                String tipPerStr = ((TextView)findViewById(R.id.etTipPer)).getText().toString();
                String tipTotalStr = ((TextView)findViewById(R.id.tvTipNum)).getText().toString();
                String billTotalStr = ((TextView)findViewById(R.id.tvTotalNum)).getText().toString();
                //Total will change so need to get from in.[]
                String str = "Tipping \n Bill: $" + billStr + "\n Tip: $" + tipTotalStr + " (" + tipPerStr + "%)" + "\n Total: $" + billTotalStr + "\n";
                //String str = String.format(new Locale("en"), "Tipping \n Bill: $%s \n Tip: $%s (%s%) \n Total: $%s \n", billStr, tipTotalStr, tipPerStr, billTotalStr);
                ((TextView)findViewById(R.id.tvTotalNum)).getText().toString();
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Tipping");
                sharingIntent.putExtra(Intent.EXTRA_TITLE, "Tipping");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, str);
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, ((TextView)findViewById(R.id.tvTotalNum)).getText().toString());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.send_intent_title)));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        this.itemSelectCalled = false;
        if (data == null)
            return;

        // for future more req_code i.e. using fragments or going back to this activity from another.
        switch (requestCode)
        {
            case SETTING_REQ_CODE:
                if(resultCode == RESULT_OK)
                {
                    in.tv_num_data.put(R.id.btTip1,
                      data.getIntExtra(Integer.toString(R.id.btTip1),
                        in.tv_num_data.get(R.id.btTip1).intValue()));
                    in.tv_num_data.put(R.id.btTip2,
                      data.getIntExtra(Integer.toString(R.id.btTip2),
                        in.tv_num_data.get(R.id.btTip2).intValue()));

                    in.tv_num_data.put(R.id.btTip3,
                      data.getIntExtra(Integer.toString(R.id.btTip3),
                        in.tv_num_data.get(R.id.btTip3).intValue()));

                    in.tv_num_data.put(R.id.etSize,
                      data.getIntExtra(Integer.toString(R.id.etSize),
                        in.tv_num_data.get(R.id.etSize).intValue()));
                }

                Calc.getInstance().calc();
                out.outputAllTextView((ViewGroup)findViewById(R.id.main_view));
                break;
            default:
        }
    }

    // Case when back button is pressed when edit text have changed a value inside. Need to store to Share Preference.
    @Override
    public void onBackPressed()
    {
        //get current focusable.
        super.onBackPressed();

        // only if view is edit text will it changed. shouldn't ever be the case if getCurrentFocus is != null.
        // in case of new feature for robustness like a new widget that can be focusable.
        if (!(getCurrentFocus() instanceof EditText))
            return;

        EditText et = (EditText)getCurrentFocus();
        SharedPreferences.Editor pref = getSharedPreferences(getClass().getSimpleName(), MODE_PRIVATE).edit();
        //can only be two types. number or TYPE_NUMBER_FLAG_DECIMAL

        if(et.getInputType() == InputType.TYPE_CLASS_NUMBER)
        {
            MainActivity.debugL("NUMBER");
            pref.putInt(Integer.toString(et.getId()), Integer.parseInt(et.getText().toString()));
        }
        else
        {
            MainActivity.debugL("DOUBLE");
            pref.putLong(Integer.toString(et.getId()), Double.doubleToLongBits(Double.parseDouble(et.getText().toString())));
        }
        pref.apply();
    }

    // This callback is called only when there is a saved instance that is previously saved by using
    // onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the one used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(DEBUG)
            Log.d(ACTIVITY,"onRestoreInstanceState");
       // teBill.setText(savedInstanceState.getString(TE_BILL_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(DEBUG)
            Log.d(ACTIVITY, "onSaveInstanceState");
        // outState.putString(GAME_STATE_KEY, gameState);
        //  outState.putString(TE_BILL_KEY, teBill.getText().toString());
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}