package piktoclean.com.pik_to_clean;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.support.v4.content.res.ConfigurationHelper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by HarshPanjwani on 28-09-2018.
 */

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{
    private Camera cam;
    private SurfaceHolder holder;
    public ShowCamera(Context context, Camera cam) {
        super(context);
        this.cam=cam;
        holder=getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters params = cam.getParameters();
        List<Camera.Size>  size = params.getSupportedPictureSizes();
        Camera.Size m=size.get(0);
        Log.d("harshlk", "surfaceCreated: 1");
        for(Camera.Size sizes : size){
            Log.d("harshlk", "surfaceCreated: 2");
            m=sizes;

        }
        Log.d("harshlk", "surfaceCreated: 3");
        params.setPictureSize(m.width,m.height);
        Log.d("harshlk", "surfaceCreated: 4");
     //   if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
    //        Log.d("harshlk", "surfaceCreated: 5");
          //  params.set("orientation", "potrait");
           // Log.d("harshlk", "surfaceCreated: 6");
            cam.setDisplayOrientation(90);
           // Log.d("harshlk", "surfaceCreated: 7");
            params.setRotation(90);
  //      } else {
//            Log.d("harshlk", "surfaceCreated: 5");
//            params.set("orientation", "Landscape");
//            Log.d("harshlk", "surfaceCreated: 6");
//            cam.setDisplayOrientation(0);
//            Log.d("harshlk", "surfaceCreated: 7");
//            params.setRotation(0);
      //  }
        Log.d("harshlk", "surfaceCreated: 8");
        params.setPictureSize(m.width,m.height);
        Log.d("harshlk", "surfaceCreated: 9");
        cam.setParameters(params);
        try {
            Log.d("harshlk", "surfaceCreated: 10");
            cam.setPreviewDisplay(holder);
            Log.d("harshlk", "surfaceCreated: 11");
            cam.startPreview();

        }catch (IOException e){
            Log.d("harshlk", "surfaceCreated: 11");
            Log.d("harshlk", "surfaceCreated: "+e);

        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cam.stopPreview();
        cam.release();
    }
}
