/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/

package example.jonathan.tipping;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


//TODO: pre 0000. regex: ^0+?!$, ""
public class SettingActivity extends BaseActivity
{
  private final Intent inRes = new Intent();
  private static final String ACTIVITY = "MAIN_ACTIVITY_SETTING";

  // class will only ever be used inside onCreate. Learning some java yo.
  // for readability, can move this class outside or another .java file and then pass into constructor the intent.
  private class EtListener implements TextView.OnEditorActionListener
  {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
      MainActivity.debugL("OnSettingEDITOR ACTION!");
      //keyboard
      InputMethodManager im = (InputMethodManager) v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
      //cannot use HIDE_IMPLICIT_ONLY, because default keyboard service is opened.
      //i.e. numeric keyboard overlay over alpha keyboard.
      im.hideSoftInputFromWindow(v.getWindowToken(), 0);
      v.clearFocus();
      return true;
    }
  }

  private class EtFocusChangeListener implements View.OnFocusChangeListener
  {
    private CharSequence tempStr;

    //when changing focus, hasFocus is False, the currentView is clearFocus().
    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
      MainActivity.debugL("TESTING : " + hasFocus);
      TextView tv = (TextView) v;
      if (hasFocus)
      {
        tempStr = tv.getText();
        tv.setText("");
        return;
      }

      MainActivity.debugL("HELLO");
      if ("".contentEquals(tv.getText()))
        tv.setText(this.tempStr);
      saveData((TextView) v);
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    Log.d(ACTIVITY, "Setting OnCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    // setting up intent to pass result back to previous activity.
    setResult(RESULT_OK, inRes);

    //initialize.
    final EditText etTip1 = findViewById(R.id.setting_etTip1);
    final EditText etTip2 = findViewById(R.id.setting_etTip2);
    final EditText etTip3 = findViewById(R.id.setting_etTip3);
    final EditText etSize = findViewById(R.id.setting_etSize);

    class BtClickListener implements View.OnClickListener
    {
      @Override
      public void onClick(View v)
      {

        EditText size = findViewById(R.id.setting_etSize);
        int sizeNum = "".contentEquals(etSize.getText())
          ? 2
          : Integer.parseInt(etSize.getText().toString());
        switch (v.getId())
        {
          case R.id.setting_btGt:
            int maxNum = 0;

            // if Value is max_length 4. maxNum is 9999
            for (int i = 0; i < getResources().getInteger(R.integer.max_length); i++)
              maxNum = maxNum * 10 + 9;

            if (sizeNum < maxNum)
            {
              etSize.setText(String.valueOf(++sizeNum));
              saveData(etSize);
            }
            size.setSelection(size.getText().toString().length());
            break;

          case R.id.setting_btLt:
            if (sizeNum > 1)
            {
              etSize.setText(String.valueOf(--sizeNum));
              saveData(etSize);
            }
            size.setSelection(size.getText().toString().length());
            break;

          case R.id.setting_btReset:
            String etTip1Str = String.valueOf(getResources().getInteger(R.integer.ten));
            String etTip2Str = String.valueOf(getResources().getInteger(R.integer.fifteen));
            String etTip3Str = String.valueOf(getResources().getInteger(R.integer.twenty));
            String etSizeStr = String.valueOf(getResources().getInteger(R.integer.sizeNum));

            //need to set first before saveDate because saveData saves current displayed view text.
            etTip1.setText(etTip1Str);
            etTip2.setText(etTip2Str);
            etTip3.setText(etTip3Str);
            etSize.setText(etSizeStr);

            // if OnEditorAction was called before this, this will over write the stored data. vice versa.
            saveData(etTip1);
            saveData(etTip2);
            saveData(etTip3);
            saveData(etSize);
            break;
          default:
        }
      }
    }

    // if instrumentation class in internal attach method EVER becomes null for some reason.
    if (getIntent() != null)
    {
      etTip1.setText(getIntent().getCharSequenceExtra(Integer.toString(R.id.btTip1)));
      etTip2.setText(getIntent().getCharSequenceExtra(Integer.toString(R.id.btTip2)));
      etTip3.setText(getIntent().getCharSequenceExtra(Integer.toString(R.id.btTip3)));
      etSize.setText(getIntent().getCharSequenceExtra(Integer.toString(R.id.etSize)));
    }

    etTip1.setOnEditorActionListener(new EtListener());
    etTip2.setOnEditorActionListener(new EtListener());
    etTip3.setOnEditorActionListener(new EtListener());
    etSize.setOnEditorActionListener(new EtListener());

    /*
    TextWatchChange + timer 0.5 seconds if user has not entered a text,then save to shared pref.
    If Timer is going to be implemented, a background thread should be done for when force close
    happens, background thread will finish storing data.
     */
    // Need onfocuschangellistener to clear the text when edit text on focused.
    etTip1.setOnFocusChangeListener(new EtFocusChangeListener());
    etTip2.setOnFocusChangeListener(new EtFocusChangeListener());
    etTip3.setOnFocusChangeListener(new EtFocusChangeListener());
    etSize.setOnFocusChangeListener(new EtFocusChangeListener());


    //create a onclick increment/dec class.
    final Button btDec = findViewById(R.id.setting_btLt);
    final Button btInc = findViewById(R.id.setting_btGt);
    final Button btReset = findViewById(R.id.setting_btReset);

    btInc.setOnClickListener(new BtClickListener());
    btDec.setOnClickListener(new BtClickListener());
    //set default reset button
    btReset.setOnClickListener(new BtClickListener());

    setSupportActionBar((Toolbar) findViewById(R.id.setting_toolbar));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    Log.d("MAIN_ACTIVITY_SETTING", "Setting onCreateOptionsMenu");
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);
    return super.onCreateOptionsMenu(menu);
  }


  //TODO: shared preference string id should be unique id string name with activity tag.
  // Then getter use string.replaceFirst(activity_name_, "");

  // pass data to previous activity, and persistent storage
  private void saveData(TextView v)
  {
    MainActivity.debugL("SAVEDATA");
    //MainActivity.debugL("currentFocus: " + getResources().getResourceEntryName(getCurrentFocus().getId()));

    int out = Integer.parseInt(v.getText().toString());

    SharedPreferences.Editor pref = getSharedPreferences(MainActivity.class.getSimpleName(), MODE_PRIVATE).edit();
    switch (v.getId())
    {
      case R.id.setting_etTip1:
        this.inRes.putExtra(Integer.toString(R.id.btTip1), out);
        pref.putInt(Integer.toString(R.id.btTip1), out);
        break;

      case R.id.setting_etTip2:
        this.inRes.putExtra(Integer.toString(R.id.btTip2), out);
        pref.putInt(Integer.toString(R.id.btTip2), out);
        break;

      case R.id.setting_etTip3:
        this.inRes.putExtra(Integer.toString(R.id.btTip3), out);
        pref.putInt(Integer.toString(R.id.btTip3), out);
        break;

      case R.id.setting_etSize:
        this.inRes.putExtra(Integer.toString(R.id.etSize), out);
        pref.putInt(Integer.toString(R.id.etSize), out);
        break;

      default:
        throw new IllegalArgumentException("SaveData: Invalid View. Not defined in Switch statement");
    }
    pref.apply();
  }

  // bundle method to return data to previous activity. stores everything important. 3 button + size edit text. store into intent and sharredPref.

  // Store current view:
  //cases:
  //  editor accept
  //  onFocusChange: when false need to store. // onFocusChange reminds me of fork. :(

  // when onFocusChange second call (false) is not called:
  //cases:
  // menu back press
  // hardware back button
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
        if (getCurrentFocus() instanceof EditText)
          getCurrentFocus().clearFocus();
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public static void checkThread()
  {
    Thread t = Thread.currentThread();

    long l = t.getId();
    String name = t.getName();
    long p = t.getPriority();
    String gname = t.getThreadGroup().getName();
    MainActivity.debugL(name + ":(id)" + l + ":(priority)" + p + ":(group)" + gname);
  }
  @Override
  public void onDestroy()
  {
    MainActivity.debugL("SETTING DEBUG ON DESTROY ");
    super.onDestroy();
  }

  /*
  super.onBackPressed() calls finish. read source code. intent data processing from synchronize this i believe.
   */
  @Override
  public void onBackPressed()
  {
    MainActivity.debugL("onBackPressed");
    if (getCurrentFocus() instanceof EditText)
      getCurrentFocus().clearFocus();
    super.onBackPressed();
  }

}
