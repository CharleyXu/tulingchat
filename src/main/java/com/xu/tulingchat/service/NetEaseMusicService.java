package com.xu.tulingchat.service;

import com.xu.tulingchat.entity.Music;
import com.xu.tulingchat.mapper.MusicMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class NetEaseMusicService {
	@Autowired
	private MusicMapper musicMapper;

	/**
	 * @return        {"id":67787,"name":"预感","score":0}
	 */
	public Music getMusicByCondition(String artist, String songName) {
		return musicMapper.findMusicByCondition(artist, songName);
	}

	/**
	 * @param artist 歌手名称
	 * @return        {"id":64048,"name":"完","score":0}
	 */
	public Music getMusic(String artist) {
		List<Music> listByArtist = musicMapper.findListByArtist(artist);
		if (listByArtist == null || listByArtist.isEmpty()) {
			return null;
		}
		int size = listByArtist.size();
		Random random = new Random();
		return listByArtist.get(random.nextInt(size));
	}

	/**
	 * 歌曲名称
	 *
	 * @return musicId      +  artistName
	 */
	public String[] getMusicIdBySongName(String songName) {
		List<String> musicIdList = musicMapper.findMusicBySongName(songName);
		if (null == musicIdList || musicIdList.isEmpty()) {
			return null;
		}
		Random random = new Random();
		String musicId = musicIdList.get(random.nextInt(musicIdList.size()));
		String artistName = musicMapper.findMusicBySongId(musicId);
		return new String[]{musicId, artistName};
	}

}
