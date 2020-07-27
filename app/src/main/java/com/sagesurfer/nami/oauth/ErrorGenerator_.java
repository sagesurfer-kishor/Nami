package com.sagesurfer.nami.oauth;


import com.sagesurfer.nami.constant.Warnings;



/*
* This class is having error code for oauth based on return status
*/

class ErrorGenerator_ {
    static String errorAuthorization(int status) {
        switch (status) {
            case 2:
                return Warnings.ERROR_AUTHORIZE_JSON;
            case 11:
                return Warnings.ERROR_INTERNAL_JSON;
            case 12:
                return Warnings.ERROR_NETWORK_JSON;
            default:
                return Warnings.ERROR_INTERNAL_JSON;
        }
    }
}
