package nfu.csie.newdrflower.model;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nfu.csie.newdrflower.controller.PicPreviewActivity;

import static android.content.Context.CAMERA_SERVICE;
import static android.os.Looper.getMainLooper;
import static java.lang.Thread.sleep;

/**
 * Created by barry on 2017/5/22.
 */


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class EnableCamera{

    private final int REQUEST_CAMERA_PERMISSION = 0;


    private TextureView mTextureView;
    private Activity mActivity;
    private ImageReader mImageReader;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private String mCameraId;
    private Size mPreviewSize;
    private int mWidth;
    private int mHeight;
    private Bitmap PicBit;
    byte[] picdata;
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    };

    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler,mainHandler;
    private static SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }
    private CameraCaptureSession mSession;
    private CaptureRequest.Builder mBuilder;
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }
    };




    public EnableCamera(TextureView mTextureView, Activity mActivity){
        this.mTextureView = mTextureView;
        this.mActivity = mActivity;
        initVIew();
        Log.d("text2","enablecamera");
    }

    private void initVIew() {
        mCameraManager = (CameraManager) mActivity.getSystemService(CAMERA_SERVICE);
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                mWidth = width;
                mHeight = height;
                getCameraId();
                openCamera();
                initCamera2();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
    }

    private void getCameraId() {
        try {
            for (String cameraId : mCameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Size getPreferredPreviewSize(Size[] sizes, int width, int height) {
        List<Size> collectorSizes = new ArrayList<>();
        for (Size option : sizes) {
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    collectorSizes.add(option);
                }
            } else {
                if (option.getHeight() > width && option.getWidth() > height) {
                    collectorSizes.add(option);
                }
            }
        }
        if (collectorSizes.size() > 0) {
            return Collections.min(collectorSizes, new Comparator<Size>() {
                @Override
                public int compare(Size s1, Size s2) {
                    return Long.signum(s1.getWidth() * s1.getHeight() - s2.getWidth() * s2.getHeight());
                }
            });
        }
        return sizes[0];
    }

    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CameraNew", "Lacking privileges to access camera service, please request permission first.");
            ActivityCompat.requestPermissions(mActivity, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CAMERA_PERMISSION);
        }

        try {
            mCameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, null);
            startBackgroundThread();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void initCamera2(){
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG,1);
        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener(){
            @Override
            public void onImageAvailable(ImageReader reader) {
                mCameraDevice.close();
                // 拿到拍照照片數據
                Image image = reader.acquireNextImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);//由緩沖區存入字節數組
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (bitmap != null) {
                    PicBit = bitmap;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    PicBit.compress(Bitmap.CompressFormat.JPEG, 50, stream );

                    picdata = stream.toByteArray();

                    Intent it = new Intent(mActivity, PicPreviewActivity.class);
                    Bundle bData = new Bundle();
                    bData.putByteArray("pic", picdata);
                    it.putExtras(bData);
                    mActivity.startActivity(it);
                    mActivity.finish();

                }
            }
        },mainHandler);
    }

    public byte[] getPicdata(){
        return  picdata;
    }

    public void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        mainHandler = new Handler(getMainLooper());
    }
    public void stopBackgroundThread() {
        if (mBackgroundThread != null) {
            mBackgroundThread.quitSafely();
            try {
                mBackgroundThread.join();
                mBackgroundThread = null;
                mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCameraPreview() {
        try {
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            int deviceOrientation = mActivity.getWindowManager().getDefaultDisplay().getOrientation();
            int totalRotation = sensorToDeviceRotation(characteristics, deviceOrientation);
            boolean swapRotation = totalRotation == 90 || totalRotation == 270;
            int rotatedWidth = mWidth;
            int rotatedHeight = mHeight;
            if (swapRotation) {
                rotatedWidth = mHeight;
                rotatedHeight = mWidth;
            }
            mPreviewSize = getPreferredPreviewSize(map.getOutputSizes(SurfaceTexture.class), rotatedWidth, rotatedHeight);
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Log.e("CameraActivity", "OptimalSize width: " + mPreviewSize.getWidth() + " height: " + mPreviewSize.getHeight());
            Surface surface = new Surface(texture);
            mBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mBuilder.addTarget(surface);
            mCameraDevice.createCaptureSession(Arrays.asList(surface,mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    if (null == mCameraDevice) {
                        return;
                    }
                    mSession = cameraCaptureSession;
                    mBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                    try {
                        mSession.setRepeatingRequest(mBuilder.build(), mSessionCaptureCallback, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(mActivity, "Camera configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private static int sensorToDeviceRotation(CameraCharacteristics characteristics, int deviceOrientation) {
        int sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        deviceOrientation = ORIENTATIONS.get(deviceOrientation);
        return (sensorOrientation + deviceOrientation + 360) % 360;
    }


    public void takepic(){
        try{
                mBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                mBuilder.addTarget(mImageReader.getSurface());
                // 自動對焦
                mBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                // 自動曝光
                mBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                // 獲取手機方向
                int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
                // 根據設備方向計算設置照片的方向
                mBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
                //拍照
                CaptureRequest mCaptureRequest = mBuilder.build();
                mSession.capture(mCaptureRequest, null, mBackgroundHandler);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        childHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//後攝像頭
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG,1);
        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { //可以在這裡處理拍照得到的臨時照片 例如，寫入本地
            @Override
            public void onImageAvailable(ImageReader reader) {
                mCameraDevice.close();
                // 拿到拍照照片數據
                Image image = reader.acquireNextImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);//由緩沖區存入字節數組

                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inJustDecodeBounds = true;
                //final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                int iw = option.outWidth;
                int ih = option.outHeight;
                int scaleFactor = Math.min(iw/maxwidth, ih/maxheight);
                option.inJustDecodeBounds = false;
                option.inSampleSize = scaleFactor;
                option.inPurgeable = true;
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,option);
                Matrix matrix = new Matrix();
                matrix.setRotate(90);
                bm = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);
                if (bm != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, stream );
                    picdata = stream.toByteArray();
                }
            }
        }, mainHandler);
        //獲取攝像頭管理
        mCameraManager = (CameraManager) mActivity.getSystemService(CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打開攝像頭
            mCameraManager.openCamera(mCameraID, stateCallback, mainHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.d("text2","initcamera2");
    }*/




    /*private Camera mCamera;
    private SurfaceHolder mSurfHolder;
    private Activity mActivity;
    private SurfaceView sfv;

    public EnableCamera(Context context,SurfaceView sfv){
        super();
        this.sfv = sfv;
        mSurfHolder = sfv.getHolder();
        mSurfHolder.addCallback(this);
        mSurfHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void set(Activity activity,Camera camera){
        mActivity = activity;
        mCamera = camera;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        try{
            mCamera.setPreviewDisplay(mSurfHolder);
            Camera.CameraInfo camInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(0, camInfo);

            int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch(rotation){
                case Surface.ROTATION_0:
                    degrees = 0; break;
                case Surface.ROTATION_90:
                    degrees = 90; break;
                case Surface.ROTATION_180:
                    degrees = 180; break;
                case Surface.ROTATION_270:
                    degrees = 270; break;
            }

            int result;
            result = (camInfo.orientation - degrees + 360) % 360;
            mCamera.setDisplayOrientation(result);
            mCamera.startPreview();
            Camera.Parameters camParas = mCamera.getParameters();
            if(camParas.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_AUTO) || camParas.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_MACRO))
                ;//mCamera.autoFocus(onCamAutoFocus);
            else
                Toast.makeText(sfv.getContext(), "照相機不支援自動對焦!", Toast.LENGTH_SHORT).show();

        }catch ( Exception e){
            Toast.makeText(sfv.getContext(), "照相機起始錯誤！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    public Camera.AutoFocusCallback onCamAutoFocus = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            Toast.makeText(sfv.getContext(),"自動對焦", Toast.LENGTH_SHORT).show();

        }
    };*/
}
