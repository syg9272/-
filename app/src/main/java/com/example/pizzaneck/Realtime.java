package com.example.pizzaneck;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import com.google.android.material.navigation.NavigationView;

public class Realtime extends AppCompatActivity implements AutoPermissionsListener {
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;

    CameraSurfaceView cameraView;

    private RealtimeDBHelper helper;
    private SQLiteDatabase db;

    //알림을 위한 진동, 소리
    Vibrator vibrator;
    SoundPool soundPool;
    int soundId, streamId;

    long startTime, endTime; //경고 시간 저장용 변수
    long durationTime; //나쁜 자세 지속 시간
    Button alarm;   //알람 테스트용 버튼. 개발 완료 후 삭제할 것

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime);
        setToolbar();

        //디비생성
        helper = new RealtimeDBHelper(Realtime.this, "Realtime.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //알림용
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.beep_once, 1); //현재는 beep_once, 설정에 따라 바뀔 수 있도록 구현하기


        FrameLayout previewFrame = findViewById(R.id.previewFrame);
        cameraView = new CameraSurfaceView(this);
        previewFrame.addView(cameraView);

//        Button button = findViewById(R.id.camera_btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                takePicture();
//            }
//        });
//        alarm=findViewById(R.id.alarmTest_btn);
//        alarm.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        startAlarm();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        endAlarm();
//                }
//                return false;
//            }
//        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void takePicture() {
        cameraView.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); //이미지 데이터 비트맵으로 만들기
                    String outUriStr = MediaStore.Images.Media.insertImage( //이미지를 추가하는 메소드
                            getContentResolver(),
                            bitmap,
                            "이미지 제목",
                            "이미지 내용");
                    if (outUriStr == null) {
                        Log.d("이미지 샘플", "이미지 추가 실패");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //카메라 미리보기를 구현하는 내부 클래스
    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        //서피스뷰 생성 시, 카메라 객체 참조 및 미리보기 화면으로 홀더 객체 생성
        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();
            setCameraOrientation();

            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //서피스뷰의 화면 크기 변동 시, 변경 시점에 미리보기 시작
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera.startPreview();
        }

        //서피스뷰가 없어질 시, 미리보기 중지
        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        //takePicture() 메소드 호출 및 사진 촬영
        public boolean capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
                return true;
            } else {
                return false;
            }
        }

        //카메라 미리보기 화면을 세로 모드로 설정
        public void setCameraOrientation() {
            if (camera == null) {
                return;
            }

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();

            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation + degrees + 360) % 360;
            }
            camera.setDisplayOrientation(result);
        }
    }

    //위험 권한 부여 시 필요한 콜백 메소드 3가지
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }


    /* 툴바 및 툴바기능 설정 함수.
     * onCreate에서 호출
     * 클래스 내 DrawerLayout drawerLayout; NavigationView navView; Toolbar toolbar; 선언 필요
     */
    protected void setToolbar() {
        //툴바 설정
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);    //기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true);      //뒤로가기 버튼 생성. 이 버튼을 메뉴바 버튼으로 사용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); //뒤로가기 버튼 아이콘 -> 메뉴 아이콘 변경


        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.home) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //액티비티 스택제거 -> 메인에서는 뒤로가기 누르면 이전 액티비티로 가지 않고 종료됨.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (id == R.id.realtime) {
                    Intent intent = new Intent(getApplicationContext(), Realtime.class);
                    startActivity(intent);
                } else if (id == R.id.gallery) {
                    Intent intent = new Intent(getApplicationContext(), Gallery.class);
                    startActivity(intent);

                } else if (id == R.id.stretching) {
                    Intent intent = new Intent(getApplicationContext(), Stretching.class);
                    startActivity(intent);
                } else if (id == R.id.graph) {
                    Intent intent = new Intent(getApplicationContext(), Graph.class);
                    startActivity(intent);
                }

                menuItem.setChecked(false);
                return true;
            }
        });
    }

    //툴바 우측에 버튼 생성 (설정버튼)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_option_btn, menu);
        return true;
    }

    //툴바에 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //네비게이션드로어
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //설정버튼
            case R.id.setting:
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //네비게이션 열려있을때 뒤로가기로 버튼 닫기
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 알람 시작
    protected void startAlarm(){
        startTime = System.currentTimeMillis();

        //설정에 따라 진동, 소리 울릴지 말지 구현해야함, 현재는 진동, 소리 둘다 울림
        if(true){
            vibrator.vibrate(new long[] {0, 500, 500}, 0); // {대기, 진동, 대기}, 무한반복(0)
            streamId = soundPool.play(soundId, 1.0F, 1.0F, 1, -1, 1.0F);
        }
    }

    // 알람 종료
    protected void endAlarm(){
        endTime = System.currentTimeMillis();
        durationTime = (endTime - startTime) / 1000;    //나쁜 자세 지속시간. 초단위
        helper.insertTime(db, durationTime);

        Toast.makeText(this, "지속시간 : "+Long.toString(durationTime)+"초", Toast.LENGTH_SHORT).show();   //지속시간 테스트용 토스트
        vibrator.cancel();  //진동 중지
        soundPool.stop(streamId);   //알림음 중지
    }
}


