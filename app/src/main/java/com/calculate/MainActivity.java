package com.calculate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 位运算 JAVA版本
 */
public class MainActivity extends AppCompatActivity {
    private final static int  WITH = 1;
    private final static int  OR = 2;
    private final static int  NON = 3;
    private final static int  SHIFT_LEFT = 4;
    private final static int  SHIFT_RIGHT = 5;
    private final static int  NOT = 6;
    private EditText edA,edB,edC;
    private TextView tvA,tvB,tvC;
    private View flB,flB1,vA,vB,vC;
    private Toast toast;

    private int type;
    private boolean useLong = false;
    private float normal,small;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        normal = getResources().getDisplayMetrics().density*14;
        small = getResources().getDisplayMetrics().density*9;
        initView();
    }

    private void initView() {
        edA = findViewById(R.id.edA);
        edB = findViewById(R.id.edB);
        edC = findViewById(R.id.edC);
        tvA = findViewById(R.id.tvA);
        tvB = findViewById(R.id.tvB);
        tvC = findViewById(R.id.tvC);
        flB = findViewById(R.id.flB);
        flB1 = findViewById(R.id.flB1);
        vA = findViewById(R.id.vA);
        vB = findViewById(R.id.vB);
        vC = findViewById(R.id.vC);



        ((RadioGroup)findViewById(R.id.rgGroup)).setOnCheckedChangeListener((view,item)->{
            switch (item){
                case R.id.with:
                    type = WITH;
                    break;
                case R.id.or:
                    type = OR;
                    break;
                case R.id.non:
                    type = NON;
                    break;
                case R.id.shift_left:
                    type = SHIFT_LEFT;
                    break;
                case R.id.shift_right:
                    type = SHIFT_RIGHT;
                    break;
                case R.id.not:
                    type = NOT;
                    break;
            }

            if (item == R.id.non){
                flB.setVisibility(View.GONE);
                flB1.setVisibility(View.GONE);
            }else{
                flB.setVisibility(View.VISIBLE);
                flB1.setVisibility(View.VISIBLE);
            }
        });

        ((CheckBox)findViewById(R.id.cbType)).setOnCheckedChangeListener((view,use)->{
            useLong = use;
            if (use){
                tvA.getPaint().setTextSize(small);
                tvB.getPaint().setTextSize(small);
                tvC.getPaint().setTextSize(small);
                vA.setVisibility(View.GONE);
                vB.setVisibility(View.GONE);
                vC.setVisibility(View.GONE);
            }else{
                tvA.getPaint().setTextSize(normal);
                tvB.getPaint().setTextSize(normal);
                tvC.getPaint().setTextSize(normal);
                vA.setVisibility(View.VISIBLE);
                vB.setVisibility(View.VISIBLE);
                vC.setVisibility(View.VISIBLE);
            }
            tvA.setText("");
            tvB.setText("");
            tvC.setText("");
        });
    }

    private long getLong(String text){
        try {
            return Long.parseLong(text);
        }catch (Exception e){}
        return 0;
    }

    private int getInt(String text){
        try {
            return Integer.parseInt(text);
        }catch (Exception e){}
        return 0;
    }

    private String binaryString(long number){
        StringBuilder builder = new StringBuilder();
        for (int i = 63;i >= 0; i--){
            builder.append((number >> i) & 1);
        }
        return builder.toString();
    }

    private String binaryString(int number){
        StringBuilder builder = new StringBuilder();
        for (int i = 31;i >= 0; i--){
            builder.append((number >> i) & 1);
        }
        return builder.toString();
    }
    private void onToast(int id){
        toast.setText(id);
        toast.show();
    }

    public void onCalculate(View view){
        if (type == 0){
            onToast(R.string.toast_1);
            return;
        }

        String A = edA.getText().toString();
        if (A.isEmpty()){
            onToast(R.string.input_a);
            return;
        }

        String B = edB.getText().toString();
        if (type != NON && A.isEmpty()){
            onToast(R.string.input_b);
            return;
        }

        if (useLong){
            long valueA = getLong(A);
            long valueB = getLong(B);
            tvA.setText(binaryString(valueA));
            tvB.setText(binaryString(valueB));

            long valueC = 0;
            switch (type){
                case WITH:
                    valueC = valueA & valueB;
                    break;
                case OR:
                    valueC = valueA | valueB;
                    break;
                case NON:
                    valueC = ~valueA;
                    break;
                case SHIFT_LEFT:
                    valueC = valueA << valueB;
                    break;
                case SHIFT_RIGHT:
                    valueC = valueA >> valueB;
                    break;
                case NOT:
                    valueC = valueA ^ valueB;
                    break;
            }
            edC.setText(""+valueC);
            tvC.setText(binaryString(valueC));
        }else{
            int valueA = getInt(A);
            int valueB = getInt(B);
            tvA.setText(binaryString(valueA));
            tvB.setText(binaryString(valueB));

            int valueC = 0;
            switch (type){
                case WITH:
                    valueC = valueA & valueB;
                    break;
                case OR:
                    valueC = valueA | valueB;
                    break;
                case NON:
                    valueC = ~valueA;
                    break;
                case SHIFT_LEFT:
                    valueC = valueA << valueB;
                    break;
                case SHIFT_RIGHT:
                    valueC = valueA >> valueB;
                    break;
                case NOT:
                    valueC = valueA ^ valueB;
                    break;
            }
            edC.setText(""+valueC);
            tvC.setText(binaryString(valueC));
        }
    }

}