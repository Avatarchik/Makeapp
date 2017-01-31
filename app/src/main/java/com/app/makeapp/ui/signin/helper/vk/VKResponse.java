package com.app.makeapp.ui.signin.helper.vk;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;

/**
 * Created by gleb on 20.12.16.
 */

public interface VKResponse {
    void onVKResult(VKAccessToken res);
    void onVKError(VKError error);
}
