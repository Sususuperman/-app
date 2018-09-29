package me.iwf.photopicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by donglua on 15/6/30.
 */
public class Photo implements Parcelable {
  public static final int Enabled = 1;
  public static final int UnEnabled = 0;
  private int id;
  private String path;
  private int status;
  private String loadurl;
  public Photo(int id, String path) {
    this.id = id;
    this.path = path;
  }

  public String getLoadurl() {
    return loadurl;
  }

  public void setLoadurl(String loadurl) {
    this.loadurl = loadurl;
  }
  public Photo() {
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Photo)) return false;

    Photo photo = (Photo) o;

    return path .equals( photo.getPath());
  }

  @Override public int hashCode() {
    return id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.path);
    dest.writeInt(this.status);
  }

  protected Photo(Parcel in) {
    this.id = in.readInt();
    this.path = in.readString();
    this.status = in.readInt();
  }

  public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
    @Override
    public Photo createFromParcel(Parcel source) {
      return new Photo(source);
    }

    @Override
    public Photo[] newArray(int size) {
      return new Photo[size];
    }
  };
}
