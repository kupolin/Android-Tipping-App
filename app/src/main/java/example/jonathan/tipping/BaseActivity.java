/*
    @Author: Jonathan Lin (jonathan.lin108@gmail.com)
*/
package example.jonathan.tipping;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class BaseActivity extends AppCompatActivity
{
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu)
  {
    Log.d("MAIN_ACTIVITY", "onPrepareOptionsMenu");
    return super.onPrepareOptionsMenu(menu);
  }
}
