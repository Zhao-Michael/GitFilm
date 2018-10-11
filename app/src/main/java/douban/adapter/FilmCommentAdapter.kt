package douban.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hedgehog.ratingbar.RatingBar
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import douban.FilmDetail
import douban.PopularComment
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import util.SetMargins
import util.dip2px
import util.inflate
import util.setImageUrl

class FilmCommentAdapter(context: Context, filmDetail: FilmDetail) : RecyclerView.Adapter<FilmCommentAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmDetail = filmDetail
    private val mListComment = mFilmDetail.popular_comments

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCommentAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_comment_cardview, parent)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListComment.size
    }

    override fun onBindViewHolder(holder: FilmCommentAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setComment(mListComment[pos])
        if (pos == itemCount - 1) {
            (holder.cardview.layoutParams as? ViewGroup.MarginLayoutParams)?.SetMargins(5, 5, 5, 5.dip2px())
        }
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val profile_image by lazy { mItemView.find<ImageView>(R.id.profile_image) }
        val text_name by lazy { mItemView.find<TextView>(R.id.text_name) }
        val text_date by lazy { mItemView.find<TextView>(R.id.text_date) }
        val text_comment by lazy { mItemView.find<TextView>(R.id.text_comment) }
        val ratingbar by lazy { mItemView.find<RatingBar>(R.id.ratingbar) }
        val text_like by lazy { mItemView.find<TextView>(R.id.text_like) }
        val image_like by lazy { mItemView.find<ImageView>(R.id.image_like) }

        fun setComment(comment: PopularComment) {
            profile_image.setImageUrl(comment.author.avatar)
            text_name.text = comment.author.name
            text_date.text = comment.created_at
            text_comment.text = comment.content
            text_like.text = comment.useful_count.toString()
            image_like.image = IconicsDrawable(image_like.context).icon(GoogleMaterial.Icon.gmd_thumb_up).color(BaseActivity.getPrimaryColor()).sizeDp(14)
            if (comment.rating.stars != null)
                ratingbar.setStar((comment.rating.stars.toInt() / 10.0).toFloat())
        }

    }

}