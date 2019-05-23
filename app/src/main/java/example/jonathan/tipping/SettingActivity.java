package example.jonathan.tipping;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class SettingActivity extends BaseActivity
{


  private static final String ACTIVITY = "MAIN_ACTIVITY_SETTING";

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    Log.d(ACTIVITY, "Setting OnCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    // setting up to pass result back to previous activity.
    final Intent inRes = new Intent();
    setResult(RESULT_OK, inRes);

    // class will only ever be used inside onCreate. Learning some java yo.
    // for readability, can move this class outside or another .java file and then pass into constructor the intent.
    class EtListener implements TextView.OnEditorActionListener
    {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
      {
        switch (actionId)
        {
          case EditorInfo.IME_ACTION_DONE:
            //string name: setting_btStart, setting_btCenter, setting_btEnd, setting_etSize
            String str =v.getContext().getResources().getResourceEntryName(v.getId());
            inRes.putExtra(str, Integer.parseInt(v.getText().toString()));
            break;
          default:
        }
        //keyboard
        InputMethodManager im = (InputMethodManager) v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        //cannot use HIDE_IMPLICIT_ONLY, because default keyboard service is opened.
        //i.e. numeric keyboard overlay over alpha keyboard.
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
        return true;
      }
    }

    // The boring way to initialize.
    final EditText etStartTip = findViewById(R.id.setting_etStartTip);
    final EditText etCenterTip = findViewById(R.id.setting_etCenterTip);
    final EditText etEndTip = findViewById(R.id.setting_etEndTip);
    final EditText etSize = findViewById(R.id.setting_etSize);

    etStartTip.setOnEditorActionListener(new EtListener());
    etCenterTip.setOnEditorActionListener(new EtListener());
    etEndTip.setOnEditorActionListener(new EtListener());
    etSize.setOnEditorActionListener(new EtListener());

    // Need onfocuschangellistener to clear the text when edit text on focused. boring.

    //create a onclick increment/dec class.
    final Button btDec = findViewById(R.id.setting_btLt);
    final Button btInc = findViewById(R.id.setting_btGt);

    //set default reset button
    Button btReset = findViewById(R.id.setting_btReset);
    btReset.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        String startTipStr = getResources().getString(R.string.ten);
        String centerTipStr= getResources().getString(R.string.fifteen);
        String endTipStr = getResources().getString(R.string.twenty);
        String sizeStr = getResources().getString(R.string.sizeNum);

        etStartTip.setText(startTipStr);
        etCenterTip.setText(centerTipStr);
        etEndTip.setText(endTipStr);
        etSize.setText(sizeStr);

        // if OnEditorAction was called before this, this will over write the stored data. vice versa.
        inRes.putExtra(getResources().getResourceEntryName(etStartTip.getId()), Integer.parseInt(startTipStr));
        inRes.putExtra(getResources().getResourceEntryName(etCenterTip.getId()), Integer.parseInt(centerTipStr));
        inRes.putExtra(getResources().getResourceEntryName(etEndTip.getId()), Integer.parseInt(endTipStr));
        inRes.putExtra(getResources().getResourceEntryName(etSize.getId()), Integer.parseInt(sizeStr));
      }
    });

    setSupportActionBar((Toolbar)findViewById(R.id.setting_toolbar));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    Log.d("MAIN_ACTIVITY_SETTING", "Setting onCreateOptionsMenu");
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    return super.onCreateOptionsMenu(menu);
  }
}
