package com.asuper.byku.dialogswithfragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
//we implement the interface that allows MyDialogFragment to access this class onFinishUserDialog method
public class MainActivity extends AppCompatActivity implements MyDialogFragment.UserNameListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFinishUserDialog(String user){
        Toast.makeText(this, "Hello, " + user, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        //close existing dialog fragments
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        //It's almost always empty! O.o Don't know when it isn't.
        if(frag != null){
            //start a series of edit operations on the fragments associated with this fragment
            manager.beginTransaction().remove(frag).commit();
            Log.i("Info ","OnClick: If: " + manager.beginTransaction().isEmpty());
        }
        switch(view.getId()){
            case R.id.showCustomFragment:
                MyDialogFragment editNameDialog = new MyDialogFragment();
                /**
                 * @param manager 	FragmentManager: The FragmentManager this fragment will be added to.
                 * @param tag 	String: The tag for this fragment, as per FragmentTransaction.add.
                 * @return void
                 */
                editNameDialog.show(manager,"fragment_edit_name");
                break;
            case R.id.showAlertDialogFragment:
                MyAlertDialogFragment alertDialogFragment = new MyAlertDialogFragment();
                alertDialogFragment.show(manager,"fragment_edit_name");
        }
    }
}
//1.Using dialogs in Android with DialogFragments
/**
 * 1.1. Using dialogs in Android
 *      Dialog - a fragment floating on top of its activity's window
 *
 * 1.2. Using existing dialogs
 *      DialogFragment - can implement onCreateDialog method and return existing dialog.
 *      Dialog class - base class for implementing dialog
 *      Subclasses:
 *      AlertDialog,
 *      ProgressDialog,
 *      DatePickerDialog,
 *      TimePickerDialog
 *      ProgressDialog(ProgressDialog.open())
 *
 * 1.3. Custom layout for your DialogFragment
 *      - create layout file for the dialog
 *      - can be inflated and returns via onCreateView() method of the fragment
 *
 * 1.4. Communication to the activity via the DialogFragment
 *      Good practice - fragment defines an interface which the activity must implement so that
 *      fragment can call back into the activity without knowing the implementation of the activity
 */
//2.Exercise; using the DialogFragments class
/**
 * 2.1. Following exercise demostrates the usage of the DialogFragment dialog in an activity with an
 *      existing dialog and with a custom layout for the dialog.
 *
 * 2.2. Create project and layout files
 *      Creating layout.
 */