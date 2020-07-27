package com.sagesurfer.nami.callbacks;

import org.json.JSONObject;



/*
 * OAuth call back to get token after successful authorization
 */

public interface TokenCallbacks {
    void tokenSuccessCallback(JSONObject jsonObject);

    void tokenFailCallback(JSONObject jsonObject);
}
