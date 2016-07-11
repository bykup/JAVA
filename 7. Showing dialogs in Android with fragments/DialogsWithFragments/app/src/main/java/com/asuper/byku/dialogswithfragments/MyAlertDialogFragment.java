package com.asuper.byku.dialogswithfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;


/**
 * Created by Byku on 27.05.2016.
 */
public class MyAlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.stat_notify_error).setTitle("Alert dialog fragment example").setMessage("This is a message").setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getActivity(),"Pressed OK",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getActivity(),"CANCEL",Toast.LENGTH_SHORT).show();
            }
        }).setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"NEUTRAL",Toast.LENGTH_SHORT).show();
            }
        }).create();
    }
}
