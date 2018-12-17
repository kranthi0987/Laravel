package com.sanjay.laravel.models.userModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSuccessResponse extends BaseObservable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_phone_number")
    @Expose
    private String userPhoneNumber;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("user_other_details")
    @Expose
    private String userOtherDetails;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @BindingAdapter({"android:profileImage"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
        // If you consider Picasso, follow the below
        // Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }

    @Bindable
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    @Bindable
    public String getAddressId() {
        return addressId;
    }
    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        notifyPropertyChanged(BR.userPhoneNumber);
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
        notifyPropertyChanged(BR.addressId);
    }

    @Bindable
    public String getUserAvatar() {
        return userAvatar;
    }
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        notifyPropertyChanged(BR.userAvatar);
    }

    @Bindable
    public String getUserOtherDetails() {
        return userOtherDetails;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserOtherDetails(String userOtherDetails) {
        this.userOtherDetails = userOtherDetails;
        notifyPropertyChanged(BR.userOtherDetails);
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

}