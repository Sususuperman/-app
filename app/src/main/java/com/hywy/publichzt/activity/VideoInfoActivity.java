package com.hywy.publichzt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.common.base.BaseToolbarActivity;
import com.cs.common.utils.FileUtils;
import com.hikvision.sdk.RealPlayManagerEx;
import com.hikvision.sdk.net.business.OnVMSNetSDKBusiness;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Video;
import com.hywy.publichzt.qiniu.MediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoInfoActivity extends BaseToolbarActivity {

    @Bind(R.id.river_name)
    TextView river_name;
    @Bind(R.id.tv_lat)
    TextView tv_lat;
    @Bind(R.id.tv_long)
    TextView tv_long;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.user_name)
    TextView tv_user;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_remark)
    TextView tv_remark;

    //七牛云播放器控件
    @Bind(R.id.video_texture_view)
    PLVideoTextureView videoView;
    @Bind(R.id.cover_stop_play)
    ImageButton stopPlayImage;
    @Bind(R.id.cover_image)
    ImageView coverImage;
    @Bind(R.id.loading_view)
    View loadingView;
    @Bind(R.id.full_screen_image)
    ImageButton fullScreenImage;
    @Bind(R.id.media_controller)
    MediaController mediaController;

    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;

    private Video video;

    String videoPath;
    String coverPath;

    private int mRotation = 0;
//    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_FIT_PARENT; //default
    private TextView mStatInfoTextView;
    private boolean mIsLiveStreaming;

    private static final String TAG = VideoInfoActivity.class.getSimpleName();

    /**
     * 播放窗口1
     */
    private int PLAY_WINDOW_ONE = 1;
    /*取流url*/
    //TODO 该地址请自行从平台获取
    private String playUrl = "rtsp://218.77.102.231:554/pag://100.100.1.241:7302:f6f3616cd679455bba4299eed0915aba:0:MAIN:TCP?cnid=4&pnid=6";


    public static void startAction(Context context, Video video) {
        Intent intent = new Intent(context, VideoInfoActivity.class);
        intent.putExtra("video", video);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        init();
        initSurfaceView();
//        initNiuVideoPlayer();
    }


    private void init() {
        video = getIntent().getParcelableExtra("video");
        setTitleBulider(new Bulider().title(video.getREACH_NAME()).backicon(R.drawable.ic_arrow_back_white_24dp));

        river_name.setText(video.getREACH_NAME());
        tv_lat.setText(video.getLTTD());
        tv_long.setText(video.getLGTD());
        tv_address.setText(video.getVIDNM());
        tv_user.setText(video.getPEOPLE());
        tv_phone.setText(video.getTEL());
    }

    private void initSurfaceView() {
        //等待surfaceview创建完毕
        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                RealPlayManagerEx.getInstance().startRealPlay(PLAY_WINDOW_ONE, playUrl, surfaceView, new OnVMSNetSDKBusiness() {
                    @Override
                    public void onFailure() {
                        super.onFailure();
                        Toast.makeText(VideoInfoActivity.this, "播放失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStatusCallback(int status) {
                        super.onStatusCallback(status);
                    }

                    @Override
                    public void onSuccess(Object obj) {
                        super.onSuccess(obj);
                        Toast.makeText(VideoInfoActivity.this, "播放成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /***
     * 初始化七牛云播放器
     */
    private void initNiuVideoPlayer() {
        //        videoPath = video.getIPADDRESS();
        videoPath = "http://demo-videos.qnsdk.com/movies/Sunset.mp4";
        coverImage.setImageBitmap(FileUtils.getNetVideoBitmap(videoPath));
////        coverPath = FileUtils.getNetVideoBitmap(videoPath);
//
//        videoView.setAVOptions(createAVOptions());
//        videoView.setBufferingIndicator(loadingView);
//        videoView.setMediaController(mediaController);
//        videoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
//        videoView.setLooping(true);
//        videoView.setVideoPath(videoPath);
//        videoView.start();

        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1) == 1;


        videoView.setBufferingIndicator(loadingView);


        // If you want to fix display orientation such as landscape, you can use the code show as follow
        //
        // if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        //     videoView.setPreviewOrientation(0);
        // }
        // else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        //     videoView.setPreviewOrientation(270);
        // }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming ? 1 : 0);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        boolean disableLog = getIntent().getBooleanExtra("disable-log", false);
        options.setInteger(AVOptions.KEY_LOG_LEVEL, disableLog ? 5 : 0);
        boolean cache = getIntent().getBooleanExtra("cache", false);
        if (!mIsLiveStreaming) {
            int startPos = getIntent().getIntExtra("start-pos", 0);
            options.setInteger(AVOptions.KEY_START_POSITION, startPos * 1000);
        }
        videoView.setAVOptions(options);

        // You can mirror the display
        // videoView.setMirror(true);

        // You can also use a custom `MediaController` widget
        MediaController mediaController = new MediaController(this, !mIsLiveStreaming, mIsLiveStreaming);
        mediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        videoView.setMediaController(mediaController);

        videoView.setOnInfoListener(mOnInfoListener);
        videoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        videoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        videoView.setOnCompletionListener(mOnCompletionListener);
        videoView.setOnErrorListener(mOnErrorListener);
        //videoView.setOnPreparedListener(mOnPreparedListener);
        //videoView.setOnSeekCompleteListener(mOnSeekCompleteListener);

        videoView.setLooping(getIntent().getBooleanExtra("loop", false));

        videoView.setVideoPath(videoPath);


        videoView.setOnInfoListener(new PLOnInfoListener() {
            @Override
            public void onInfo(int i, int i1) {
                if (i == PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START) {
                    coverImage.setVisibility(View.GONE);
                    stopPlayImage.setVisibility(View.GONE);
                    mediaController.hide();
                }
            }
        });
    }

    @OnClick({R.id.cover_image, R.id.full_screen_image})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.cover_image:
                stopCurVideoView();
                startCurVideoView();
                break;
            case R.id.full_screen_image:
                if (videoView == null) {
                    return;
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }

    public void startCurVideoView() {
        videoView.setVideoPath(videoPath);
        videoView.start();
        loadingView.setVisibility(View.VISIBLE);
        stopPlayImage.setVisibility(View.GONE);
    }

    public void restartCurVideoView() {
        videoView.start();
        stopPlayImage.setVisibility(View.GONE);
    }

    public boolean isCurVideoPlaying() {
        return videoView.isPlaying();
    }

//    public boolean needBackstagePlay() {
//        return mCurViewHolder != null && BACKSTAGE_PLAY_TAG.equals(mCurViewHolder.videoView.getTag());
//    }

    public void pauseCurVideoView() {
        videoView.pause();
        loadingView.setVisibility(View.GONE);
    }

    private void resetConfig() {
        videoView.setRotation(0);
        videoView.setMirror(false);
        videoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
    }

    public void stopCurVideoView() {
        resetConfig();
        videoView.stopPlayback();
        loadingView.setVisibility(View.GONE);
        coverImage.setVisibility(View.VISIBLE);
        stopPlayImage.setVisibility(View.VISIBLE);
    }

    public AVOptions createAVOptions() {
        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        boolean disableLog = getIntent().getBooleanExtra("disable-log", false);
        options.setInteger(AVOptions.KEY_LOG_LEVEL, disableLog ? 5 : 0);
        boolean cache = getIntent().getBooleanExtra("cache", false);
        return options;
    }

    /***
     * 切换横竖屏
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            onPortraitChanged();
        } else {
//            onLandscapeChanged();
        }
    }

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Log.i(TAG, "Play Completed !");
            finish();
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int precent) {
            Log.i(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLOnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLOnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private MediaController.OnClickSpeedAdjustListener mOnClickSpeedAdjustListener = new MediaController.OnClickSpeedAdjustListener() {
        @Override
        public void onClickNormal() {
            // 0x0001/0x0001 = 2
            videoView.setPlaySpeed(0X00010001);
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            videoView.setPlaySpeed(0X00020001);
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            videoView.setPlaySpeed(0X00010002);
        }
    };
    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                    break;
                default:
                    break;
            }
            finish();
            return true;
        }
    };

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.i(TAG, "First audio render time: " + extra + "ms");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    Log.i(TAG, videoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                    break;
                default:
                    break;
            }
        }
    };


}
