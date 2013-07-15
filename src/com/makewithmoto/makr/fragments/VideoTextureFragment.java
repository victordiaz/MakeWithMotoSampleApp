package com.makewithmoto.makr.fragments;

import java.io.IOException;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;

import com.makewithmoto.example.R;

@SuppressLint("NewApi")
public class VideoTextureFragment extends Fragment {

	private View v;
	private TextureView mVideoView;
	Vector<VideoListener> listeners = new Vector<VideoListener>();
	private MediaPlayer mMediaPlayer;
	Runnable r;
	protected Handler handler;

	public interface VideoListener {

		public void onReady(boolean ready);

		public void onFinish(boolean finished);

		public void onTimeUpdate(int ms, int totalDuration);
	}

	/**
	 * Called when the activity is first created.
	 * 
	 * @return
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		v = inflater.inflate(R.layout.fragment_videoplayer, container, false);

		mVideoView = (TextureView) v.findViewById(R.id.video_view);
		Log.d("mm", "onCreateView");

		handler = new Handler();

		return v;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("mm", "onActivityCreated");

		for (VideoListener l : listeners) {
			l.onReady(true);
		}

	}

	public void loadExternalVideo(String videoFile) {
		String path = Environment.getExternalStorageDirectory()
				+ "/arprototype/video";
		loadVideo(path);
	}

	public void loadResourceVideo(String videoFile) {
		String path = "android.resource://" + getActivity().getPackageName()
				+ videoFile;
		loadVideo(path);
	}

	public void loadVideo(final String path) {
		/*
		 * Alternatively,for streaming media you can use
		 * mVideoView.setVideoURI(Uri.parse(URLstring));
		 */

		mVideoView.setSurfaceTextureListener(new SurfaceTextureListener() {

			@Override
			public void onSurfaceTextureUpdated(SurfaceTexture surface) {

			}

			@Override
			public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
					int width, int height) {

			};

			@Override
			public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

				return false;
			}

			@Override
			public void onSurfaceTextureAvailable(SurfaceTexture surface,
					int width, int height) {

				Surface s = new Surface(surface);

				try {
					mMediaPlayer = new MediaPlayer();
					mMediaPlayer.setDataSource(path);
					mMediaPlayer.setSurface(s);

					mMediaPlayer
							.setOnPreparedListener(new OnPreparedListener() {

								private MediaPlayer mp_;

								@Override
								public void onPrepared(MediaPlayer mp) {

									// MediaController mediaController = new
									// MediaController(
									// getActivity());
									// mVideoView.setMediaController(mediaController);

									// mVideoView.requestFocus();
									// mVideoView.setKeepScreenOn(true);

									// mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
									// mMediaPlayer.start();
									// mp_.start();

									// mp_ = mp;
									// mp_.setLooping(true);

									// mVideoView.animate().rotation(200).alpha((float)
									// 0.5)
									// .scaleX(0.2f).scaleY(0.2f).setDuration(2000);

									r = new Runnable() {
										@Override
										public void run() {
											for (VideoListener l : listeners) {
												l.onTimeUpdate(mp_
														.getCurrentPosition(),
														mp_.getDuration());
											}
											handler.postDelayed(this, 1000);
										}
									};

									handler.post(r);
								}
							});

					mMediaPlayer
							.setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer mp) {

								}
							});

					mMediaPlayer.prepare();
					mMediaPlayer.start();

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	public void setVolume(float volume) {
		if (mMediaPlayer != null) {
			mMediaPlayer.setVolume(volume, volume);

		}
	}

	public void setLoop(boolean b) {
		mMediaPlayer.setLooping(b);
	}

	public void close() {
		handler.removeCallbacks(r);
		// mVideoView.stopPlayback();

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

		}
		return true;
	}

	public void addListener(VideoListener videoListener) {
		listeners.add(videoListener);
	}

	public void removeListener(VideoListener videoListener) {
		listeners.remove(videoListener);
	}

	public void seekTo(int ms) {
		mMediaPlayer.seekTo(ms);
	}
}
