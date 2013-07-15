package com.makewithmoto.makr.fragments;

import com.makewithmoto.example.R;
import com.makewithmoto.example.R.drawable;
import com.makewithmoto.example.R.id;
import com.makewithmoto.example.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

	private View v;
	private int count = 0;
	ImageView image;
	private int num;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		v = inflater.inflate(R.layout.fragment_image, container, false);
		image = (ImageView) v.findViewById(R.id.imageView1);
		image.setImageResource(R.drawable.up);

		return v;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (count % 2 == 0) {
				//	image.setImageResource(R.drawable.dog);
				} else {
				//	image.setImageResource(R.drawable.cat1);
				}
				count++;
			}
		});

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

		}
		return true;
	}

	public void up() {
		image.setImageResource(R.drawable.up);
	}

	public void down() {
		image.setImageResource(R.drawable.down);
	}

	public void resetGame() {
		num = 0;
		
	}

}
