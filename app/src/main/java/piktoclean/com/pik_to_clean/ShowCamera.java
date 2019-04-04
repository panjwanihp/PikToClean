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
        for(Camera.Size sizes : size){
            m=sizes;

        }
        params.setPictureSize(m.width,m.height);
            cam.setDisplayOrientation(90);
            params.setRotation(90);
        params.setPictureSize(m.width,m.height);
        cam.setParameters(params);
        try {
            cam.setPreviewDisplay(holder);
            cam.startPreview();

        }catch (IOException e){
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
