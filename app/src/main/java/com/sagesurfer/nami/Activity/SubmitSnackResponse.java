package com.sagesurfer.nami.Activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.sagesurfer.nami.constant.Warnings;
import com.sagesurfer.nami.snack.ShowSnack;
import com.sagesurfer.nami.snack.ShowToast;


public class SubmitSnackResponse {
    //1: Success
    //2: Failed
    //11:Internal Error
    //12:Network Error
    //13:Authentication Error
    public static void showSnack(int status, String message, Button button, Context _context) {
        switch (status) {
            case 1:
                ShowToast.successful(message, _context);
                break;
            case 2:
                ShowSnack.buttonWarning(button, message, _context);
                break;
            case 11:
                ShowSnack.buttonWarning(button, Warnings.INTERNAL_ERROR_OCCURRED, _context);
                break;
            case 12:
                ShowSnack.buttonWarning(button, Warnings.NETWORK_ERROR_OCCURRED, _context);
                break;
            case 13:
                ShowSnack.buttonWarning(button, Warnings.AUTHENTICATION_FAILED, _context);
                break;
            case 20:
                ShowSnack.buttonWarning(button, message, _context);
                break;
            case 21:
                ShowSnack.buttonWarning(button, message, _context);
                break;
        }
    }


    public static void showSnack(int status, String message, View view, Context _context) {
        switch (status) {
            case 1:
                ShowToast.successful(message, _context);
                break;
            case 2:
                ShowSnack.viewWarning(view, message, _context);
                break;
            case 11:
                ShowSnack.viewWarning(view, Warnings.INTERNAL_ERROR_OCCURRED, _context);
                break;
            case 12:
                ShowSnack.viewWarning(view, Warnings.NETWORK_ERROR_OCCURRED, _context);
                break;
            case 13:
                ShowSnack.viewWarning(view, Warnings.AUTHENTICATION_FAILED, _context);
                break;
        }
    }

    public static void showSnack(int status, String message, Context _context) {
        switch (status) {
            case 1:
                ShowToast.successful(message, _context);
                break;
            case 2:
                ShowToast.failed(message, _context);
                break;
            case 11:
                ShowToast.internalErrorOccurred(_context);
                break;
            case 12:
                ShowToast.networkError(_context);
                break;
            case 13:
                ShowToast.authenticationFailed(_context);
                break;
        }
    }
}
