package org.pokenet.client.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 * Loads, manages, and handles audio
 * 
 * @author Ryan
 * @author ZombieBear
 * 
 */
public class SoundManager {
	private HashMap<String, Music> channels;
	private HashMap<String, String> fileList;
	private ArrayList<String> channelList;
	private boolean muted = false;

	private final String audioPath = "res/music/";

	public SoundManager() {
		channels = new HashMap<String, Music>();
		channelList = new ArrayList<String>();
		fileList = new HashMap<String, String>();
		loadFileList();
	}

	// load the index file for audio samples
	private void loadFileList() {
		try {
			Scanner reader = new Scanner(new File(audioPath+"index.txt"));

			String f = null;
			while (reader.hasNext()) {
				f = reader.nextLine();
				String[] addFile = f.split(":", 2);
				fileList.put(addFile[0], addFile[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playChannel(String key, String channel) {
		if (!channels.containsKey(channel)) {
			channels.put(channel, null);
			channelList.add(channel);
		}
		Music currChannel = channels.get(channel);
		try {
			currChannel = new Music(audioPath + fileList.get(key), true);
			System.out.println("MAGIC 2");
			channels.put(channel, currChannel);
			channels.get(channel).setVolume(1);
			channels.get(channel).play();

			if (muted)
				muteAll();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void playChannel(String key, String channel, boolean loop) {
		if (!channels.containsKey(channel)) {
			channels.put(channel, null);
			channelList.add(channel);
		}
		System.out.println("MAGIC");

		Music currChannel = channels.get(channel);
		try {
			currChannel = new Music(audioPath + fileList.get(key), true);

			channels.put(channel, currChannel);
			channels.get(channel).setVolume(1);
			channels.get(channel).loop();

			if (muted)
				muteAll();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	// stops the current channel
	public void stopChannel(String channel) {
		if (channels.containsKey(channel) && (channels.get(channel) != null)) {
			channels.get(channel).stop();
		}
	}

	// pauses the current channel
	public void pauseChannel(String channel) {
		if (channels.containsKey(channel) && (channels.get(channel) != null)) {
			channels.get(channel).pause();
		}
	}

	// resumes a previously paused channel
	public void resumeChannel(String channel) {
		if (channels.containsKey(channel) && (channels.get(channel) != null)) {
			channels.get(channel).setVolume(1);
			channels.get(channel).resume();

			if (muted)
				muteAll();
		}
	}

	// creates a channel
	public void createChannel(String channel) {
		if (!channels.containsKey(channel)) {
			channels.put(channel, null);
			channelList.add(channel);

			if (muted)
				muteAll();
		}
	}

	public void muteAll() {
		for (String t : channelList) {
			channels.get(t).setVolume(0);
		}
		mute(true);
	}

	public void unmuteAll() {
		for (String t : channelList) {
			channels.get(t).setVolume(1);
		}
		mute(false);
	}

	public void mute(boolean m) {
		muted = m;
	}

	public boolean isMuted() {
		return muted;
	}
}
