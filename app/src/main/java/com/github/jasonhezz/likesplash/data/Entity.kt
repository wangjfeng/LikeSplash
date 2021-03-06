package com.github.jasonhezz.likesplash.data

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


/**
 * Created by JasonHezz on 2017/7/11.
 */
//@Parcelize not work under lolipop will throw install_failed_uid_changed
@SuppressLint("ParcelCreator")
@Parcelize
data class Photo(var id: String,
    var created_at: String? = null,
    var updated_at: String? = null,
    var width: Int,
    var height: Int,
    var color: String,
    val downloads: Int? = 0,
    var likes: Int? = 0,
    val views: Int? = 0,
    val liked_by_user: Boolean? = false,
    val description: String? = null,
    val exif: Exif? = null,
    val location: Location? = null,
    val current_user_collections: List<Collection>? = null,
    val urls: Urls? = null,
    val categories: List<Categories>? = null,
    val links: PhotoLinks? = null,
    val story: Story? = null,
    var tags: List<Tag>? = null,
    val relatedCollections: RelatedCollections? = null,
    var user: User?) : Serializable, Parcelable

data class CoverPhoto(var id: String?,
    var width: Int?,
    var height: Int?,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    var color: String?,
    var likes: Int?,
    var liked_by_user: Boolean?,
    var description: String?,
    var user: User?,
    var urls: Urls?,
    var links: PhotoLinks?,
    var categories: List<Categories>?)

@SuppressLint("ParcelCreator")
@Parcelize
data class Location(
    val country: String? = null,
    val city: String? = null,
    val name: String? = null,
    val position: Position? = null,
    val title: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Position(
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RelatedCollections(
    val total: Int? = null,
    val type: String? = null,
    val results: List<Collection>? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Story(
    val description: String? = null,
    val title: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Exif(
    val exposureTime: String? = null,
    val aperture: String? = null,
    val focalLength: String? = null,
    val iso: Int? = null,
    val model: String? = null,
    val make: String? = null
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class User(var id: String? = null,
    var updated_at: String? = null,
    var username: String? = null,
    var name: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var twitter_username: String? = null,
    var portfolio_url: String? = null,
    var bio: String? = null,
    val followedByUser: Boolean? = false,
    var location: String? = null,
    var total_likes: Int? = 0,
    var total_photos: Int? = 0,
    var total_collections: Int? = 0,
    var profile_image: ProfileImage? = null,
    var links: UserLinks? = null) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Tag(val title: String? = null,
    val url: String? = null, val description: String? = null) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Me(var id: String? = null,
    var username: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var portfolio_url: String? = null,
    var bio: String? = null,
    var location: String?,
    var total_likes: Int = 0,
    var total_photos: Int = 0,
    var total_collections: Int = 0,
    var followed_by_user: Boolean = false,
    var downloads: Int = 0,
    var uploads_remaining: Int = 0,
    var instagram_username: String? = null,
    var email: String? = null,
    var links: UserLinks? = null) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProfileImage(var small: String?,
    var medium: String?,
    var large: String) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Urls(var raw: String?,
    var full: String?,
    var regular: String?,
    var small: String?,
    var thumb: String?,
    var custom: String?) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PhotoLinks(var self: String? = null,
    var html: String? = null,
    var download: String? = null,
    var download_location: String? = null) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CollectionLinks(var self: String?,
    var html: String?,
    var photos: String?,
    var related: String?) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Categories(var id: Int?,
    var title: String?,
    var photo_count: Int?,
    var links: PhotoLinks) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Collection(var id: Int?,
    val featured: Boolean? = null,
    var title: String? = null,
    var description: String? = null,
    var published_at: String? = null,
    var updated_at: String? = null,
    var curated: Boolean? = null,
    var total_photos: Int? = null,
    var private: Boolean? = null,
    var share_key: String? = null,
    var cover_photo: Photo? = null,
    var preview_photos: List<Photo>? = null,
    var user: User? = null,
    var links: CollectionLinks? = null,
    val tags: List<Tag>? = null) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AccessToken(var access_token: String?,
    var token_type: String?,
    var scope: String?,
    var created_at: String?) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class DownLoadLink(var url: String) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class UserLinks(var self: String?,
    var html: String?,
    var photos: String?,
    var likes: String?,
    var portfolio: String?,
    var following: String?,
    var followers: String?) : Parcelable


data class TotalStats(var photos: Int?,
    var download: Int?,
    var views: Int?,
    var likes: Int?,
    var photographers: Int?,
    var pixels: Int?,
    var downloads_per_second: Int,
    var views_per_second: Int,
    var developers: Int?,
    var applications: Int?,
    var requests: Int)

data class MonthStats(var photos: Int?,
    var download: Int?,
    var views: Int?,
    var likes: Int?,
    var new_photos: Int?,
    var new_photographers: Int?,
    var new_pixels: Int?,
    var new_developers: Int?,
    var new_applications: Int?,
    var new_requests: Int?)

data class TrendingFeed(var next_page: String?, var photos: List<Photo>?)

data class SearchPhotoResult(var total: Int?, var total_pages: Int?, var results: List<Photo>?)

data class ExplorePhoto(val name: String? = null, val descriptionFragment: String? = null,
    val related: List<Tag>? = null)


data class ExploreCollection(val name: String? = null, val descriptionFragment: String? = null,
    val collections: List<Collection>? = null)