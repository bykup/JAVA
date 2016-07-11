package com.asuper.byku.dialogswithfragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Byku on 27.05.2016.
 */
public class MyDialogFragment extends DialogFragment implements TextView.OnEditorActionListener{
    private EditText mEditText;
    //created an interface that will give us access to method onFinishUserDialog in MainActivity
    public interface UserNameListener{
        void onFinishUserDialog(String user);
    }
    //empty constructor for MyDialogFragment is mandarory
    public MyDialogFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //we inflate current view in current viewGroup with fragmet_username layout
        View view = inflater.inflate(R.layout.fragment_username,container);
        //we save pointer to username in EditText object
        mEditText = (EditText) view.findViewById(R.id.username);

        //set this instance as callback for editor action
        /**
         * Special listener that is called when an action is performed on the text view.  This will
         * be called when an enter key is pressed or when an action supplied to the IME is selected by the
         * user. Enter will not allow new line, but holding alt will(but not in this instace)
         */
        mEditText.setOnEditorActionListener(this);
        //obvious
        mEditText.requestFocus();
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //getDialog().setTitle("Please enter username");
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        //Return input text to activity, throwing Activity to interface UserNameListener
        //that allows us access to onFinishUserDialog
        UserNameListener activity = (UserNameListener) getActivity();
        //MainActivity activity = (MainActivity) getActivity();
        activity.onFinishUserDialog(mEditText.getText().toString());
        this.dismiss();
        return true;
    }
}
