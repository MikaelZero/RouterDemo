package net.mikaelzero.routerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.mikaelzero.interfaces.Router;
import net.mikaelzero.router.IUriAnnotationInit;
import net.mikaelzero.router.UriHandler;

import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        findViewById(R.id.tv).setOnClickListener(v -> {
            startActivity(UriHandler.createIntent(this, "/second"));
        });
    }

    void init() {
        try {
            Class<?> clazz = Class.forName("net.mikaelzero.router.generated.UriAnnotationInitImpl");
            IUriAnnotationInit annotationInit = (IUriAnnotationInit) clazz.newInstance();
            annotationInit.init(new UriHandler());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}