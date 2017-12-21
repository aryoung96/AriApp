package com.example.kimaryeong.ariapp.fregment;

import android.Manifest;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kimaryeong.ariapp.R;
import com.example.kimaryeong.ariapp.SmsActivity;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class MicFragment extends Fragment implements TextToSpeech.OnInitListener {

    protected ImageButton ivMic;
    protected TextToSpeech tts;
    protected ArrayList<String> arName, arPhoneNum, arSMS;
    protected final int nNameSize = 3;
    protected static final int RECOG_CODE = 1234;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RECOG_CODE) {
                ArrayList<String> arStr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String sRecog = arStr.get(0);
                Toast.makeText(getActivity(), sRecog, Toast.LENGTH_LONG).show();
                int nPos = arName.indexOf(sRecog);
                if (nPos == -1) {
                    tts.speak(sRecog + "는 없는 이름입니다", TextToSpeech.QUEUE_FLUSH, null, null);
                } else if (nPos == 4) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), SmsActivity.class);
                    startActivity(intent);
                } else {
                    String sPhoneNum = arPhoneNum.get(nPos);
                    Toast.makeText(getActivity(), sRecog, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sPhoneNum));
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                }

            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mic, container, false);
        ivMic = (ImageButton) view.findViewById(R.id.ivMic);
        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 인식중.");
                startActivityForResult(intent, RECOG_CODE);
            }
        });

        tts = new TextToSpeech(getActivity(),this);

        arName = new ArrayList<String>(nNameSize);
        arPhoneNum = new ArrayList<String>(nNameSize);
        arName.add("가나다");
        arPhoneNum.add("01012345678");
        arName.add("문자");

        return view;
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(0.5f);
            tts.setSpeechRate(1.0f);
        }
    }
}

