package com.example.testptrace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testptrace.databinding.ActivityMainBinding;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'testptrace' library on application startup.
    static {
        System.loadLibrary("hook");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //通过执行su产生一个具有root权限的进程
        Process p = null;
        try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c", "cmd"});
            p = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("cmd" + "\n");//cmd命令可为空
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//然后，在向这个进程的写入要执行的命令，即可达到以root权限执行命令：
      ;

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'testptrace' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}