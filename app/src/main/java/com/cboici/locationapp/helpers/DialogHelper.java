package com.cboici.locationapp.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.cboici.locationapp.interfaces.IDialogCallback;

public class DialogHelper {

    public static void showAlert(String message, String title, Context context, final IDialogCallback callback) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context).setMessage(message).setTitle(title).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if(callback != null)
                    callback.executeCallback();
            }
        });

        dialog.show();
    }
}
