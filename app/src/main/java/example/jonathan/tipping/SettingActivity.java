package example.jonathan.tipping;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class SettingActivity extends BaseActivity
{
  private static final String ACTIVITY = "MAIN_ACTIVITY";

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    Log.d(ACTIVITY, "OnCreate");
    // store into ram/savedInstanceState for performance when it matters i.e. huge data set.
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_setting);
    setSupportActionBar((Toolbar)findViewById(R.id.setting_toolbar));


/*
    ImageButton im = findViewById(R.id.imageButton);
    im.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Toast.makeText(v.getContext(),"HEYYYYY",Toast.LENGTH_SHORT);
      }
    });
    */
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    Log.d("MAIN_ACTIVITY_SETTING", "onCreateOptionsMenu");
    ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    return super.onCreateOptionsMenu(menu);
  }

}
