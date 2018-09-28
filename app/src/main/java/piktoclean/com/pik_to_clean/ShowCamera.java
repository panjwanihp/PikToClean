package piktoclean.com.pik_to_clean;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
