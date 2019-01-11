package com.gofd.greendaopractice.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


/**
 * Post
 * 게시물 Model
 * {
    "id":0,
    "title":"",
    "created":"",
    "replies":[]
  }
 */
@Entity(nameInDb="posts")
public class Post {              // 객체 하나가 하나의 Row
  public static final transient String JSON_FORMAT = "{\"title\":\"%s\", \"created\":%s}";

  @Id(autoincrement = true)      // AUTO_INCREASE
  private Long id;              // id는 Long으로

  @Property(nameInDb = "title")    // Column 이름
  private String title;

  @Property(nameInDb = "created") // 생성 일자
  private Date created;

  @ToMany(referencedJoinProperty = "postId")  // 일대다 관계 Join
  @OrderBy("created ASC")                      // 오름차순 정렬
  private List<Reply> replies;                // 댓글

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 572315894)
private transient PostDao myDao;

@Generated(hash = 892110335)
public Post(Long id, String title, Date created) {
    this.id = id;
    this.title = title;
    this.created = created;
}

@Generated(hash = 1782702645)
public Post() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitle() {
    return this.title;
}

public void setTitle(String title) {
    this.title = title;
}

public Date getCreated() {
    return this.created;
}

public void setCreated(Date created) {
    this.created = created;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 61012110)
public List<Reply> getReplies() {
    if (replies == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ReplyDao targetDao = daoSession.getReplyDao();
        List<Reply> repliesNew = targetDao._queryPost_Replies(id);
        synchronized (this) {
            if (replies == null) {
                replies = repliesNew;
            }
        }
    }
    return replies;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 2101789245)
public synchronized void resetReplies() {
    replies = null;
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
@Generated(hash = 1915117241)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getPostDao() : null;
}
}