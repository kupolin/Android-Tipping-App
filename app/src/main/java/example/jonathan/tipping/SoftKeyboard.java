/*
 * Author:Felipe Herranz (felhr85@gmail.com)
 * Contributors:Francesco Verheye (verheye.francesco@gmail.com)
 * 		Israel Dominguez (dominguez.israel@gmail.com)
 */
package example.jonathan.tipping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

// Observer Pattern? check later.
public class SoftKeyboard implements View.OnFocusChangeListener
{
    private static final int CLEAR_FOCUS = 0; //????

    private ViewGroup layout; // root layout.
    private int layoutBottom; // bottom layout height
    private InputMethodManager im; //input key manager
    private int[] coords;
    private boolean isKeyboardShow;
    private SoftKeyboardChangesThread softKeyboardThread; //
    private List<EditText> editTextList; // # of edit texts on the screen.

    private View tempView; // reference to a focused EditText

    public SoftKeyboard(ViewGroup layout, InputMethodManager im)
    {
        this.layout = layout;
        keyboardHideByDefault();
        initEditTexts(layout);
        this.im = im;
        this.coords = new int[2];
        this.isKeyboardShow = false;
        this.softKeyboardThread = new SoftKeyboardChangesThread();
        this.softKeyboardThread.start();
    }


    public void openSoftKeyboard()
    {
        if(!isKeyboardShow)
        {
            layoutBottom = getLayoutCoordinates();
            im.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
            softKeyboardThread.keyboardOpened();
            isKeyboardShow = true;
        }
    }

    public void closeSoftKeyboard()
    {
        Log.d("ACTIVITY_MAIN", "closeSoftKeyboard");
        Log.d("ACTIVITY_MAIN", "" + isKeyboardShow);

        //if(isKeyboardShow)
        if(!isKeyboardShow)
        {
            im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            isKeyboardShow = false;
        }
    }

    public void setSoftKeyboardCallback(SoftKeyboardChanged mCallback)
    {
        softKeyboardThread.setCallback(mCallback);
    }

    public void unRegisterSoftKeyboardCallback()
    {
        softKeyboardThread.stopThread();
    }
    /*
        based as on click for testing not on press before cursor shows.
     */
    public interface SoftKeyboardChanged
    {
        // cursor within edit box blinks away?
        public void onSoftKeyboardHide();
        // on click cursor within edit box
        public void onSoftKeyboardShow();
    }

    private int getLayoutCoordinates()
    {
        layout.getLocationOnScreen(coords);
        return coords[1] + layout.getHeight(); //index 0 is x 1 is y
    }

    private void keyboardHideByDefault()
    {
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
    }

    /*
     * InitEditTexts now handles EditTexts in nested views
     * Thanks to Francesco Verheye (verheye.francesco@gmail.com)
     */
    private void initEditTexts(ViewGroup viewgroup)
    {
        if(editTextList == null)
            editTextList = new ArrayList<EditText>();

        int childCount = viewgroup.getChildCount();
        for(int i=0; i<= childCount-1;i++)
        {
            View v = viewgroup.getChildAt(i);

            if(v instanceof ViewGroup)
            {
                initEditTexts((ViewGroup) v);
            }

            if(v instanceof EditText)
            {
                EditText editText = (EditText) v;
                editText.setOnFocusChangeListener(this);
                editText.setCursorVisible(true);
                editTextList.add(editText);
            }
        }
    }

    /*
     * OnFocusChange does update tempView correctly now when keyboard is still shown
     * Thanks to Israel Dominguez (dominguez.israel@gmail.com)
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if(hasFocus)
        {
            tempView = v;
            if(!isKeyboardShow)
            {
                layoutBottom = getLayoutCoordinates();
                softKeyboardThread.keyboardOpened();
                isKeyboardShow = true;
            }
        }
    }

    // This handler will clear focus of selected EditText
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message m)
        {
            switch(m.what)
            {
                case CLEAR_FOCUS:
                    if(tempView != null)
                    {
                        tempView.clearFocus();
                        tempView = null;
                    }
                    break;
            }
        }
    };

    private class SoftKeyboardChangesThread extends Thread
    {
        private AtomicBoolean started; //true when constructed
        private SoftKeyboardChanged mCallback;

        public SoftKeyboardChangesThread()
        {
            started = new AtomicBoolean(true);
        }

        public void setCallback(SoftKeyboardChanged mCallback)
        {
            this.mCallback = mCallback;
        }

        @Override
        public void run()
        {
            // acts as a looper
            while(started.get())
            {
                // Wait until keyboard is requested to open
                synchronized(this)
                {
                    try
                    {
                        wait(); // thread is waiting/sleeping
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                int currentBottomLocation = getLayoutCoordinates();

                // There is some lag between open soft-keyboard function and when it really appears.
                while(currentBottomLocation == layoutBottom && started.get())
                {
                    // acts as a looper until keyboard is at the default position.
                    currentBottomLocation = getLayoutCoordinates();
                }

                //Keyboard is already opened and at correct height. when is started ever false?
                //case when thread stops i.e. when app calls onDestroy need to stop and unregister thread.
                if(started.get())
                    mCallback.onSoftKeyboardShow(); //THIS IS ON BLINK.

                // When keyboard is opened from EditText, initial bottom location is greater than layoutBottom
                // and at some moment equals layoutBottom.
                // That broke the previous logic, so I added this new loop to handle this.

                // just check if the current view is an edit text in the above loop or just make the above >=????
                while(currentBottomLocation >= layoutBottom && started.get())
                {
                    currentBottomLocation = getLayoutCoordinates();
                }

                // the fact that you are running code from onSoftKeyboardShow() shows that the keyboard is ALREADY shown.
                // what about the earlier while statement wasn't the purpose of that to make SURE the keyboard is SHOWING?

                //there's GOTTA be a better way to do this instead of looping this in the background and eat up more resources.....
                // honestly just want to delete this. like wtf is this code.

                // Now Keyboard is shown, keep checking layout dimensions until keyboard is gone
                while(currentBottomLocation != layoutBottom && started.get())
                {
                    /*
                    // dont need this code.

                    synchronized(this)
                    {
                        try
                        {
                            //500 bug when moving between edittext boxes for saving values
                            wait(1);
                        } catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    */
                    currentBottomLocation = getLayoutCoordinates();
                }
                //bro this is blinker not keyboard.
                if(started.get())
                    mCallback.onSoftKeyboardHide();

                // if keyboard has been opened clicking and EditText.
                if(isKeyboardShow && started.get())
                    isKeyboardShow = false;

                // if an EditText is focused, remove its focus (on UI thread)

                // EDIT do not remove focus for numbered edit text
                /*
                if(started.get())
                    mHandler.obtainMessage(CLEAR_FOCUS).sendToTarget();
                */
            }
        }

        public void keyboardOpened()
        {
            synchronized(this)
            {
                notify();
            }
        }

        public void stopThread()
        {
            synchronized(this)
            {
                started.set(false);
                notify();
            }
        }

    }
}