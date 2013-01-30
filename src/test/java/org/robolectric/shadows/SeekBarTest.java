package org.robolectric.shadows;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.robolectric.TestRunners;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.widget.SeekBar;

import org.robolectric.Robolectric;
import org.robolectric.util.Transcript;

@RunWith(TestRunners.WithDefaults.class)
public class SeekBarTest {

	private SeekBar seekBar;
	private ShadowSeekBar shadow;
	private SeekBar.OnSeekBarChangeListener listener;
	private Transcript transcript;
	
	@Before
	public void setup() {
	    seekBar = new SeekBar(new Activity());
        shadow = Robolectric.shadowOf(seekBar);
        listener = new TestSeekBarChangedListener();
        transcript = new Transcript();
        seekBar.setOnSeekBarChangeListener(listener);	
	}
	
    @Test
    public void testOnSeekBarChangedListener() {
        assertThat(shadow.getOnSeekBarChangeListener(), sameInstance(listener));
        seekBar.setOnSeekBarChangeListener(null);
        assertThat(shadow.getOnSeekBarChangeListener(), nullValue());
    }

    @Test
    public void testOnChangeNotification() {
    	seekBar.setProgress(5);
    	transcript.assertEventsSoFar("onProgressChanged() - 5");
    }
    
    private class TestSeekBarChangedListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        	transcript.add("onProgressChanged() - " + progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
