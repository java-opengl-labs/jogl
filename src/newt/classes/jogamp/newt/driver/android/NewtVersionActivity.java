/**
 * Copyright 2011 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JogAmp Community OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of JogAmp Community.
 */
package jogamp.newt.driver.android;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Display;
import com.jogamp.newt.Screen;
import com.jogamp.newt.ScreenMode;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.ScreenModeListener;
import com.jogamp.newt.opengl.GLWindow;
import jogamp.newt.driver.android.test.GearsGL2ES1;
import com.jogamp.opengl.util.Animator;

import jogamp.newt.driver.android.AndroidWindow;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

public class NewtVersionActivity extends NewtBaseActivity {
   GLWindow glWindow = null;
   Animator animator = null;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       System.setProperty("nativewindow.debug", "all");
       System.setProperty("jogl.debug", "all");
       System.setProperty("newt.debug", "all");
       System.setProperty("jogamp.debug.JNILibLoader", "true");
       System.setProperty("jogamp.debug.NativeLibrary", "true");
       // System.setProperty("jogamp.debug.NativeLibrary.Lookup", "true");
       
       // create GLWindow (-> incl. underlying NEWT Display, Screen & Window)
       GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GLES1));
       glWindow = GLWindow.create(caps);
       setContentView(glWindow);
       
       glWindow.addGLEventListener(new GearsGL2ES1(1));
       glWindow.getWindow().getScreen().addScreenModeListener(new ScreenModeListener() {
        public void screenModeChangeNotify(ScreenMode sm) { }
        public void screenModeChanged(ScreenMode sm, boolean success) {
            System.err.println("ScreenMode Changed: "+sm);
        }
       });
       glWindow.setVisible(true);
       animator = new Animator(glWindow);
       animator.setUpdateFPSFrames(60, System.err);
       
       Log.d(MD.TAG, "onCreate - X");
   }
   
   @Override
   public void onResume() {
     super.onResume();
     if(null != animator) {
         animator.start();
     }
   }

   @Override
   public void onPause() {
     super.onPause();
     if(null != animator) {
         animator.pause();
     }
   }

   @Override
   public void onDestroy() {
     super.onDestroy(); 
     if(null != animator) {
         animator.stop();
     }
     if(null != glWindow) {
         glWindow.destroy();
     }
   }   
}
