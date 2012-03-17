/*
 * Copyright (C) 2012 Simon Davie (nexx@nexxdesign.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.nexxdesign.FFCFix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.graphics.PixelFormat;
import android.hardware.Camera;

import android.os.SystemProperties;

import android.util.Log;

/**
 *
 * @author simon davie
 * Using Gingerbread camera libraries with the Camera HAL has a significant bug
 * where accessing the front-facing camera module before the rear, following a
 * reboot causes a system error. Mediaserver crashes due to this.
 *
 * Accessing the rear camera first works around the issue. This app opens then
 * closes the rear camera on boot, then exits. This allows features like Face
 * Unlock to function after a reboot and also ensures the camera app doesn't
 * FC if the FFC was the last camera used prior to the reboot.
 *
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "FFCFix";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "FFC Crash Fix - Simon Davie <nexx@nexxdesign.co.uk>");

        Log.i(TAG, "-- Opening rear camera");
        Camera camera = Camera.open();

        Log.i(TAG, "-- Setting parameters");
        Camera.Parameters parameters = camera.getParameters();
        camera.setParameters(parameters);

        Log.i(TAG, "-- Releasing rear camera");
        camera.release();

        Log.i(TAG, "-- Complete");
    }
}
