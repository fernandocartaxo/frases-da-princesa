package com.ctx.frasesdaprincesa;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ctx.frasesdaprincesa.FraseFragment.OnListFragmentInteractionListener;
import com.ctx.frasesdaprincesa.frase.FraseContent;

import java.util.ArrayList;
import java.util.List;

public class MyFraseRecyclerViewAdapter extends RecyclerView.Adapter<MyFraseRecyclerViewAdapter.ViewHolder> {

    private final List<FraseContent.Frase> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final List<ViewHolder> mHolders = new ArrayList<>();
    private static final String TAG = "MyFraseRecyclerView";

    public MyFraseRecyclerViewAdapter(List<FraseContent.Frase> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_frase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mHolders.add(holder);
        final FraseContent.Frase frase = mValues.get(position);

        holder.mItem = frase;
        holder.mImgIcone.setImageDrawable(frase.icone);
        holder.mTxtResumo.setText(frase.resumo);
        holder.mMediaPlayer = MediaPlayer.create(holder.mView.getContext(), frase.audio);
        holder.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                holder.mSeekBar.setProgress(0);
                pause(holder);
            }
        });

        holder.mSeekBar.setMax(holder.mMediaPlayer.getDuration());
        holder.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pauseAll();
                holder.mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        holder.mImgPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mMediaPlayer.isPlaying()) {
                    pause(holder);
                } else {
                    start(holder);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void pauseAll() {
        for (ViewHolder h : mHolders) {
            if (h.mMediaPlayer.isPlaying()) pause(h);
        }
    }

    public void pause(ViewHolder holder) {
        holder.mMediaPlayer.pause();
        holder.mImgPlayStop.setImageDrawable(holder.mView.getContext().getDrawable(android.R.drawable.ic_media_play));
        if (holder.mSeekBarThread != null) holder.mSeekBarThread.interrupt();
    }

    public void start(final ViewHolder holder) {
        pauseAll();
        holder.mMediaPlayer.start();
        holder.mImgPlayStop.setImageDrawable(holder.mView.getContext().getDrawable(android.R.drawable.ic_media_pause));
        holder.mSeekBarThread = new Thread() {
            @Override
            public void run() {
                int currentPosition = 0;
                int total  = holder.mMediaPlayer.getDuration();

                while (holder.mSeekBar != null && holder.mMediaPlayer != null && currentPosition < total) {
                    currentPosition =  holder.mMediaPlayer.getCurrentPosition();
                    holder.mSeekBar.setProgress(currentPosition);
                    try {
                        if(!isInterrupted()) Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Log.v(TAG, "erro no Thread sleep" + e.getMessage());
                    }
                }
            }
        };
        holder.mSeekBarThread.start();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTxtResumo;
        public final ImageView mImgIcone;
        public final ImageView mImgPlayStop;
        public final SeekBar mSeekBar;
        public MediaPlayer mMediaPlayer;
        public FraseContent.Frase mItem;
        public Thread mSeekBarThread;
        public Integer ordem;

        public ViewHolder(final View view) {
            super(view);
            mView = view;
            mTxtResumo = view.findViewById(R.id.txtResumo);
            mImgIcone = view.findViewById(R.id.imgIcone);
            mImgPlayStop = view.findViewById(R.id.imgPlayPause);
            mSeekBar = view.findViewById(R.id.seekBar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtResumo.getText() + "'";
        }
    }
}
