package com.xu.tulingchat.util;

import com.xu.tulingchat.entity.Music;
import com.xu.tulingchat.entity.Page;
import com.xu.tulingchat.entity.PageRequest;
import com.xu.tulingchat.mapper.MusicMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicUtilTest {


	@Autowired
	private MusicMapper musicMapper;
	@Autowired
	private MusicUtil musicUtil;

	//分页测试
	@Test
	public void findByPage() {
		PageRequest request = new PageRequest();
		int pageSize = 20;//
		request.setStart(0);
		request.setSize(pageSize);
		request.setSorts(new PageRequest.Sort[]{new PageRequest.Sort("musicId", "asc")});
		List<Music> list = musicMapper.findByPage(request);
		Page page = new Page();
		page.setRows(list);
		int sum = musicMapper.countSize();//总记录数
		int pageNumbers = sum / pageSize + 1;//总页数
		page.setTotalPages(pageNumbers);
		page.setTotalRows(sum);
		System.out.println("page:\n" + page);
	}

	@Test
	public void findMusicByCondition() {
		String artist = "Eason";
		String name = "预感";
		//按照歌手名称+歌曲名称
		Music musicByCondition = musicMapper.findMusicByCondition(artist, name);
		System.out.println("findMusicByCondition \n" + musicByCondition);
		//按照歌曲名称
		List<String> musicBySongName = musicMapper.findMusicBySongName(name);
		Random random = new Random();
		String musicId = musicBySongName.get(random.nextInt(musicBySongName.size()));
		System.out.println("musicId:" + musicId);
		String musicBySongId = musicMapper.findMusicBySongId(musicId);
		System.out.println("musicBySongId:" + musicBySongId);
		//按照歌手名称
		List<Music> listByArtist = musicMapper.findListByArtist(artist);
		System.out.println("listByArtist:" + listByArtist);
	}

	@Test
	public void musicutilTest() {
		String songId = "http://music.163.com/song?id=29802464";
		musicUtil.uploadThumb(songId);
	}

}
