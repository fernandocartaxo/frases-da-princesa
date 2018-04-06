package com.ctx.frasesdaprincesa;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ctx.frasesdaprincesa.frase.FraseContent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements FraseFragment.OnListFragmentInteractionListener {

    public Drawable mImgCoroa = null;
    public Drawable mImgMediaPlayer = null;
    private AdView mAdViewRodape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdViewRodape = findViewById(R.id.adViewRodape);

        MobileAds.initialize(this, "ca-app-pub-1068712102629927~3612296121");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewRodape.loadAd(adRequest);

        mImgCoroa = getDrawable(R.drawable.ic_icons8_crown);
        mImgMediaPlayer = getDrawable(R.drawable.ic_if_circle_music_sound_audio_mp3_outline_double_note_outline_stroke_763451);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mImgMediaPlayer.setTint(getColor(R.color.colorAccent));
        }

        FraseContent.addItem(new FraseContent.Frase("1", 1, getString(R.string.levanta_a_cabeca_princesa), mImgCoroa, R.raw.levanta_a_cabeca_princesa));
        FraseContent.addItem(new FraseContent.Frase("2", 2, getString(R.string.o_que_nao_mata_fortalece), mImgMediaPlayer, R.raw.o_que_nao_mata_fortalece));
        FraseContent.addItem(new FraseContent.Frase("3", 3, getString(R.string.a_vinganca_nunca_e_plena), mImgMediaPlayer, R.raw.a_vinganca_nunca_eh_plena));
        FraseContent.addItem(new FraseContent.Frase("4", 4, getString(R.string.planta_florida), mImgMediaPlayer, R.raw.planta_florida));
    }

    @Override
    public void onListFragmentInteraction(FraseContent.Frase item) {}
}
