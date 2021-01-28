package net.mikaelzero.second;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.mikaelzero.interfaces.Router;

/**
 * @Author: MikaelZero
 * @CreateDate: 1/28/21 4:00 PM
 * @Description:
 */
@Router(path = "/second")
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
