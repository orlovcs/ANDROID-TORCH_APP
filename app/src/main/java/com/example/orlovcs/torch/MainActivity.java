package com.example.orlovcs.torch;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.Camera.Parameters;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.security.Policy;
import android.hardware.camera2.*;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button on;
    Button off;
    TextView t;
    private Context context;
    private Camera camera;
    CameraManager cameraManager;
    String cameraId;
    Boolean flashUnit = false;
    Integer unitNum = 0;



    public void torchToggle(boolean b){

        Log.d("debug", "torchToggle reached");

            if (b == true && flashUnit == true && cameraId != null){

//turn flash on

                try {
                    Log.d("debug", "torchToggle: on");
                    cameraManager.setTorchMode(cameraId, true);

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }


            }else if (b == false && flashUnit == true && cameraId != null){

//turn flash off
                try {
                    Log.d("debug", "torchToggle: off");
                    cameraManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        on = findViewById(R.id.button);
        off = findViewById(R.id.button2);
        t = findViewById(R.id.textView);

        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {

            for (String cID : cameraManager.getCameraIdList()) {
                CameraCharacteristics cg = cameraManager.getCameraCharacteristics(cID);
                if (cg.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true){
                    flashUnit = true;
                    cameraId = cID;
                    break;
                }
                unitNum++;
            }
          //  cameraId = cameraManager.getCameraIdList()[0];


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        if (flashUnit == true){
            t.setText("Flash Unit found at unit #" + unitNum+ ".");
        }else{
            t.setText("Flash Unit not found.");
        }


        on.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               torchToggle(true);
            }
        });

        off.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                torchToggle(false);
            }
        });

    }


}
