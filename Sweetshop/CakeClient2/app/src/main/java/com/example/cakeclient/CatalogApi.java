package com.example.cakeclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import android.text.TextUtils;
import android.util.Base64;

public class CatalogApi implements Parcelable {
    public int Id;
    public int IdUser;
    public String Title;
    public String Description;
    public String TypeCrust1;
    public String TypeCrust2;
    public String TypeCrust3;
    public String TypeFilling1;
    public String TypeFilling2;
    public String TypeFilling3;
    public String TypeCream1;
    public String TypeCream2;
    public String TypeCream3;
    public String TypeAddition1;
    public String TypeAddition2;
    public String TypeAddition3;
    public double Cost;
    public String Photo;

    public CatalogApi(int id, int idUser, String title, String description,

                      String typeCrust1,
                      String typeCrust2,
                      String typeCrust3,

                      String typeFilling1,
                      String typeFilling2,
                      String typeFilling3,

                      String typeCream1,
                      String typeCream2,
                      String typeCream3,

                      String typeAddition1,
                      String typeAddition2,
                      String typeAddition3,

                      double cost,
                      String Photo
    ) {
        Id = id;
        IdUser = idUser;
        Title = title;
        Description = description;
        TypeCrust1 = typeCrust1;
        TypeCrust2 = typeCrust2;
        TypeCrust3 = typeCrust3;
        TypeFilling1 = typeFilling1;
        TypeFilling2 = typeFilling2;
        TypeFilling3 = typeFilling3;
        TypeCream1 = typeCream1;
        TypeCream2 = typeCream2;
        TypeCream3 = typeCream3;
        TypeAddition1 = typeAddition1;
        TypeAddition2 = typeAddition2;
        TypeAddition3 = typeAddition3;
        Cost = cost;
        this.Photo = Photo;
    }

    protected CatalogApi(Parcel in) {
        Id = in.readInt();
        IdUser = in.readInt();
        Title = in.readString();
        Description = in.readString();
        TypeCrust1 = in.readString();
        TypeCrust2 = in.readString();
        TypeCrust3 = in.readString();
        TypeFilling1 = in.readString();
        TypeFilling2 = in.readString();
        TypeFilling3 = in.readString();
        TypeCream1 = in.readString();
        TypeCream2 = in.readString();
        TypeCream3 = in.readString();
        TypeAddition1 = in.readString();
        TypeAddition2 = in.readString();
        TypeAddition3 = in.readString();
        Cost = in.readDouble();
        Photo = in.readString();
    }

    public static final Creator<CatalogApi> CREATOR = new Creator<CatalogApi>() {
        @Override
        public CatalogApi createFromParcel(Parcel in) {
            return new CatalogApi(in);
        }

        @Override
        public CatalogApi[] newArray(int size) {
            return new CatalogApi[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTypeCrust1() {
        return TypeCrust1;
    }

    public void setTypeCrust1(String typeCrust1) {
        TypeCrust1 = typeCrust1;
    }

    public String getTypeCrust2() {
        return TypeCrust2;
    }

    public void setTypeCrust2(String typeCrust2) {
        TypeCrust2 = typeCrust2;
    }

    public String getTypeCrust3() {
        return TypeCrust3;
    }

    public void setTypeCrust3(String typeCrust3) {
        TypeCrust3 = typeCrust3;
    }

    public String getTypeFilling1() {
        return TypeFilling1;
    }

    public void setTypeFilling1(String typeFilling1) {
        TypeFilling1 = typeFilling1;
    }

    public String getTypeFilling2() {
        return TypeFilling2;
    }

    public void setTypeFilling2(String typeFilling2) {
        TypeFilling2 = typeFilling2;
    }

    public String getTypeFilling3() {
        return TypeFilling3;
    }

    public void setTypeFilling3(String typeFilling3) {
        TypeFilling3 = typeFilling3;
    }

    public String getTypeCream1() {
        return TypeCream1;
    }

    public void setTypeCream1(String typeCream1) {
        TypeCream1 = typeCream1;
    }

    public String getTypeCream2() {
        return TypeCream2;
    }

    public void setTypeCream2(String typeCream2) {
        TypeCream2 = typeCream2;
    }

    public String getTypeCream3() {
        return TypeCream3;
    }

    public void setTypeCream3(String typeCream3) {
        TypeCream3 = typeCream3;
    }

    public String getTypeAddition1() {
        return TypeAddition1;
    }

    public void setTypeAddition1(String typeAddition1) {
        TypeAddition1 = typeAddition1;
    }

    public String getTypeAddition2() {
        return TypeAddition2;
    }

    public void setTypeAddition2(String typeAddition2) {
        TypeAddition2 = typeAddition2;
    }

    public String getTypeAddition3() {
        return TypeAddition3;
    }

    public void setTypeAddition3(String typeAddition3) {
        TypeAddition3 = typeAddition3;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }
    public Bitmap getBitmapSource(){
        if(TextUtils.isEmpty(getPhoto())){
            return null;
        }
        else {
            byte[] array = Base64.decode(getPhoto(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(array, 0, array.length);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(Id);
        dest.writeInt(IdUser);
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeString(TypeCrust1);
        dest.writeString(TypeCrust2);
        dest.writeString(TypeCrust3);
        dest.writeString(TypeFilling1);
        dest.writeString(TypeFilling2);
        dest.writeString(TypeFilling3);
        dest.writeString(TypeCream1);
        dest.writeString(TypeCream2);
        dest.writeString(TypeCream3);
        dest.writeString(TypeAddition1);
        dest.writeString(TypeAddition2);
        dest.writeString(TypeAddition3);
        dest.writeDouble(Cost);
        dest.writeString(Photo);
    }
}
