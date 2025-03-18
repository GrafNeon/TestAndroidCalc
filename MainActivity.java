package com.example.fsfgandroid2;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView d;
    float r = 0;
    char o = 0;
    boolean nN = true, iD = false;
    final int gCL = 0xFFC8C8C8, oC = 0xFFFF9500, gCD = 0xFF646464, sBL = 0xFFD3D3D3, sBD = 0xFF4A4A4A;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);
        d = findViewById(R.id.display);
        setupBtns();
        updTheme();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, ins) -> {
            Insets sb = ins.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom);
            return ins;
        });
    }

    void setupBtns() {
        int[] nIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int id : nIds) findViewById(id).setOnClickListener(v -> {
            String c = d.getText().toString(), dig = ((Button)v).getText().toString();
            if (nN) { d.setText(dig); nN = false; }
            else if (c.length() < 15) d.setText(c + dig);
        });

        findViewById(R.id.btnC).setOnClickListener(v -> { d.setText("0"); r = 0; o = 0; nN = true; });
        findViewById(R.id.btnSign).setOnClickListener(v -> updDisp(-Float.parseFloat(d.getText().toString())));
        findViewById(R.id.btnPercent).setOnClickListener(v -> updDisp(Float.parseFloat(d.getText().toString()) / 100));
        findViewById(R.id.btnDot).setOnClickListener(v -> {
            String c = d.getText().toString();
            if (!c.contains(".") && c.length() < 15) d.setText(c + ".");
        });

        int[] opIds = {R.id.btnDivide, R.id.btnMultiply, R.id.btnMinus, R.id.btnPlus};
        for (int id : opIds) findViewById(id).setOnClickListener(v -> {
            r = Float.parseFloat(d.getText().toString());
            o = ((Button)v).getText().toString().charAt(0);
            nN = true;
        });

        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            float num = Float.parseFloat(d.getText().toString());
            switch (o) {
                case '+': r += num; break;
                case '-': r -= num; break;
                case '*': r *= num; break;
                case '/': if (num != 0) r /= num; break;
            }
            updDisp(r);
            o = 0; nN = true;
        });

        findViewById(R.id.btnTheme).setOnClickListener(v -> { iD = !iD; updTheme(); });
    }

    void updDisp(float v) {
        String t = String.format("%.2f", v);
        while (t.endsWith("0")) t = t.substring(0, t.length() - 1);
        if (t.endsWith(".")) t = t.substring(0, t.length() - 1);
        d.setText(t);
    }

    void updTheme() {
        Window w = getWindow();
        View m = findViewById(R.id.main);
        int[] gB = {R.id.btnC, R.id.btnSign, R.id.btnPercent, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn0, R.id.btnDot, R.id.btnTheme};
        int[] oB = {R.id.btnDivide, R.id.btnMultiply, R.id.btnMinus, R.id.btnPlus, R.id.btnEquals};

        if (iD) {
            m.setBackgroundColor(0xFF1E1E1E);
            w.setStatusBarColor(0xFF1E1E1E);
            w.getDecorView().setSystemUiVisibility(0);
            d.setTextColor(0xFFFFFFFF);
            for (int id : gB) setBtnTheme(id, sBD, gCD, 0xFFFFFFFF);
            for (int id : oB) setBtnTheme(id, sBD, oC, 0xFFFFFFFF);
        } else {
            m.setBackgroundColor(0xFFF5F5F5);
            w.setStatusBarColor(0xFFF5F5F5);
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            d.setTextColor(0xFF000000);
            for (int id : gB) setBtnTheme(id, sBL, gCL, 0xFF000000);
            for (int id : oB) setBtnTheme(id, sBL, oC, 0xFF000000);
        }
    }

    void setBtnTheme(int id, int bg, int tint, int textColor) {
        Button b = findViewById(id);
        View p = (View) b.getParent();
        if (p != null) p.setBackgroundColor(bg);
        b.setBackgroundResource(R.drawable.button_background);
        b.getBackground().setTint(tint);
        b.setTextColor(textColor);
    }
}
