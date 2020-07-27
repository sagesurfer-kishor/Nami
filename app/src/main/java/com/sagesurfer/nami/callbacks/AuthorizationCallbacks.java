package com.sagesurfer.nami.callbacks;

import org.json.JSONObject;



/*
 * OAuth call back after authorization
 */

public interface AuthorizationCallbacks {

    void authorizationSuccessCallback(JSONObject jsonObject);

    void authorizationFailCallback(JSONObject jsonObject);
}
