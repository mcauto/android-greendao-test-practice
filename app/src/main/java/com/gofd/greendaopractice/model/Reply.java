package com.gofd.greendaopractice.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Reply
 * 댓글
 */
@Entity(nameInDb="reply")
public class Reply {
  public static final transient String JSON_FORMAT = "{\"content\":\"%s\", \"created\":%s}";

  @Id(autoincrement = true)
  private Long id;

  @Property(nameInDb = "content")   // 댓글 내용
  private String content;

  @Property(nameInDb = "created")
  private Date created;

  private long postId;

  @ToOne(joinProperty = "postId")
  @NotNull
  private Post post;

  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;

  /** Used for active entity operations. */
  @Generated(hash = 1650954370)
  private transient ReplyDao myDao;

  @Generated(hash = 724543437)
  public Reply(Long id, String content, Date created, long postId) {
      this.id = id;
      this.content = content;
      this.created = created;
      this.postId = postId;
  }

  @Generated(hash = 1831839081)
  public Reply() {
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getContent() {
      return this.content;
  }

  public void setContent(String content) {
      this.content = content;
  }

  public Date getCreated() {
      return this.created;
  }

  public void setCreated(Date created) {
      this.created = created;
  }

  public long getPostId() {
      return this.postId;
  }

  public void setPostId(long postId) {
      this.postId = postId;
  }

  @Generated(hash = 1690682906)
  private transient Long post__resolvedKey;

  /** To-one relationship, resolved on first access. */
  @Generated(hash = 1119914866)
  public Post getPost() {
      long __key = this.postId;
      if (post__resolvedKey == null || !post__resolvedKey.equals(__key)) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          PostDao targetDao = daoSession.getPostDao();
          Post postNew = targetDao.load(__key);
          synchronized (this) {
              post = postNew;
              post__resolvedKey = __key;
          }
      }
      return post;
  }

  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 197523274)
  public void setPost(@NotNull Post post) {
      if (post == null) {
          throw new DaoException(
                  "To-one property 'postId' has not-null constraint; cannot set to-one to null");
      }
      synchronized (this) {
          this.post = post;
          postId = post.getId();
          post__resolvedKey = postId;
      }
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 128553479)
  public void delete() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.delete(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 1942392019)
  public void refresh() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.refresh(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 713229351)
  public void update() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.update(this);
  }

  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1935013498)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getReplyDao() : null;
}
}