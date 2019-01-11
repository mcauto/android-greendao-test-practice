package com.gofd.greendaopractice;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.gofd.greendaopractice.main.MainActivity;
import com.gofd.greendaopractice.model.DaoMaster;
import com.gofd.greendaopractice.model.DaoSession;
import com.gofd.greendaopractice.model.Post;
import com.gofd.greendaopractice.model.PostDao;
import com.gofd.greendaopractice.model.Reply;
import com.gofd.greendaopractice.model.ReplyDao;
import com.gofd.greendaopractice.utility.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sqlcipher.database.SQLiteConstraintException;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * GreenDAO Test
 * ORM 사용을 위한 Function TEST
 * GreenDAO는 SQLite를 쉽게 사용해주는 ORM tool
 * ORM 사용법을 이해할 수 있음
 */
public class GreenDaoTest extends ActivityInstrumentationTestCase2<MainActivity> {
  private static final String TAG = GreenDaoTest.class.getName();

  public GreenDaoTest() {
    super(MainActivity.class);
  }

  private DaoMaster mDaoMaster;
  private PostDao postDao;
  private ReplyDao replyDao;
  private Calendar c;
  private Gson gson;

  // Function Test에 필요한 object들 initialize
  @Override
  protected void setUp() throws Exception {
    super.setUp();

    QueryBuilder.LOG_SQL = true;
    QueryBuilder.LOG_VALUES = true;
    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.getActivity(), Constants.ENCRYPTED ? Constants.DATABASE_NAME + "_test_encrypted" : Constants.DATABASE_NAME+"_test");  // 데이터베이스 암호화
    Database db = Constants.ENCRYPTED ? helper.getEncryptedWritableDb("imdeo123!!") : helper.getWritableDb();

    mDaoMaster = new DaoMaster(db);
    assertNotNull(mDaoMaster.getDatabase());

    DaoSession daoSession = mDaoMaster.newSession();
    assertNotNull(daoSession);

    postDao = daoSession.getPostDao();
    assertNotNull(postDao);

    replyDao = daoSession.getReplyDao();
    assertNotNull(replyDao);

    c = Calendar.getInstance();
    assertNotNull(c);

    gson = new GsonBuilder()
            .setDateFormat(Constants.DATE_FORMAT)
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .create();
    assertNotNull(gson);
  }

  // Post 기본 Insert Test
  public void testPostInsert() {
    String title = "title_test";
    Date created = c.getTime();

    Post post = new Post();
    // 중복 삽입 테스트 SQLiteConstraintException 발생
    // Unique pk error 발생
    // post.setId((long)1);
    post.setTitle(title);
    post.setCreated(created);
    long postId = 0;
    try {
      postId = postDao.insert(post);
    } catch (SQLiteConstraintException de) {
      fail();
    }
    finally{
      assertTrue(postId>0);
    }
  }

  // Post JSON을 이용한 Insert 테스트
  public void testPostInsertByJson() {
    Post post = new Post();
    String title = "title_json_test";

    Date date = c.getTime();

    // "" ${DATE} "" 이런식으로 채워서 나옴; 조금 이상함
    // DateDeserializer의 Unit Test를 참고
    String sDate = gson.toJson(date);

    // {"title":"title_test", "created":"2019-01-11 14:25:45"}
    String postForm = String.format(post.JSON_FORMAT, title, sDate);
    Log.d(TAG, "testPostInsert: " + postForm);

    // json deserialize
    post = gson.fromJson(postForm, post.getClass());

    // insert post
    long postId = postDao.insert(post);
    Log.i(TAG, "testPostInsert(): postId:" + postId);

    // insert check
    post = postDao.load(postId);
    Log.i(TAG, "testPostInsert: " + gson.toJson(post));

    assertTrue(postId > 0);
  }

  // Post Select 테스트
  public void testPostSelectAll() {
    List<Post> postList =postDao.loadAll();
    Log.i(TAG, "testPostSelect()::postList:" + gson.toJson(postList));
    assertNotNull(postList);
    assertTrue(postList.size()>0);
  }

  // Post 불러오기 (댓글 포함, 강제 호출)
  public void testPostSelectAllwithReplies(){
    List<Post> postList =postDao.loadAll();
    for (int i= 0; i<postList.size();i++){
      Post post = postList.get(i);
      try {
        post.getReplies();
      } catch (Exception e){
        fail();
      }
    }
    Log.i(TAG, "testPostSelect()::postList:" + gson.toJson(postList));
    assertNotNull(postList);
    assertTrue(postList.size()>0);
  }

  // Post Update 테스트
  public void testPostUpdate() {
    // 글의 제목과 날짜를 수정
    String title = "title_update_test";
    Date date = c.getTime();

    Post origin = postDao.loadAll().get(0);
    origin.setTitle(title);
    origin.setCreated(date);

    try {
      postDao.update(origin);
    } catch (Exception e){
      Log.e(TAG, "testPostUpdate: ", e);
      assertNull(e);
    }
  }

  // Post DeleteAll 테스트
  public void testPostDeleteAll() {
    postDao.deleteAll();
    List<Post> postList =postDao.loadAll();
    Log.i(TAG, "testPostDeleteAll()::postList.toString():\n" + postList.toString());
    assertNotNull(postList);
    assertEquals(0, postList.size());
  }

  // Reply Insert 테스트
  public void testReplyInsert() {
    Post post = postDao.loadAll().get(0);
    Reply reply =new Reply();

    reply.setContent("댓글 달기 테스트");
    reply.setCreated(c.getTime());
    reply.setPostId(post.getId());
    reply.setPost(post);

    long replyId = 0;
    try {
      replyId = replyDao.insert(reply);
    } catch (Exception e){
      fail(e.getMessage());
    } finally{
      assertTrue(replyId>0);
    }
  }

  // Reply Json Insert 테스트
  public void testReplyInsertJson(){
    Reply reply = new Reply();
    String content = "json_insert_test";
    String sDate = gson.toJson(c.getTime());
    String json = String.format(Reply.JSON_FORMAT, content, sDate);

    reply = gson.fromJson(json, Reply.class);
    long replyId = 0;
    try {
      replyId = replyDao.insert(reply);
    } catch (Exception e){
      fail(e.getMessage());
    } finally{
      assertTrue(replyId != 0);
    }
    Log.i(TAG, "testReplyInsertJson: "+ json);
    Log.i(TAG, "testReplyInsertJson: "+ reply);
  }
  // Reply Select 테스트
  public void testReplySelect() {
    List<Reply> list = null;
    try {
      list = replyDao.loadAll();
    } catch (Exception e){
      fail();
    } finally {
      assertNotNull(list);
      Log.i(TAG, "testReplySelect: "+ gson.toJson(list));
    }
  }

  // Reply Update 테스트
  public void testReplyUpdate() {
    String content = "update test";
    Date d = c.getTime();
    Reply reply = replyDao.loadAll().get(0);
    Log.i(TAG, "testReplyUpdate: "+ reply);
    reply.setContent(content);
    reply.setCreated(d);
    try {
      replyDao.update(reply);
    } catch (Exception e){
      fail(e.getMessage());
    }
  }

  // Reply Delete All 테스트
  public void testReplyDeleteAll() {
    try {
      replyDao.deleteAll();
    } catch (Exception e){
      fail(e.getMessage());
    }
  }

  @Override
  protected void tearDown() {
    mDaoMaster.getDatabase().close();
  }
}