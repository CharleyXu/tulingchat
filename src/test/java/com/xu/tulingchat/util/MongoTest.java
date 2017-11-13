package com.xu.tulingchat.util;

import com.xu.tulingchat.entity.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void test() {
		Comment comment = new Comment();
		comment.setSendMessage("1");
		comment.setReplyMessage("2");
		comment.setCreateTime(DateUtil.dateToString(new Date(), DateUtil.yyyyMMddHHmm));
		mongoTemplate.save(comment);
	}

	@Test
	public void selectTest() {
		Query query = new Query();
		query.addCriteria(Criteria.where("sendMessage").is("1"));
		List<Comment> comments = mongoTemplate.find(query, Comment.class);
		System.out.println("comments:" + comments);
	}

	@Test
	public void select() {
//		Criteria criteria = new Criteria() {
//			@Override
//			public DBObject getCriteriaObject() {
//				DBObject obj = new BasicDBObject();
//				obj.put("$where", "this.groupNum > this.joinedNum");
//				return obj;
//			}
//		};
		Query query = new Query();
		query.addCriteria(Criteria.where("replyMessage").is("2"));
		query.with(new Sort(Sort.Direction.DESC, "createTime"));
		List<Comment> comments = mongoTemplate.find(query, Comment.class);
		System.out.println(comments);
	}
}
